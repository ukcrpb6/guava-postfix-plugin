/*
 * Copyright (C) 2014 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.drache.intellij.codeinsight.postfix.internal;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.codeInsight.completion.JavaCompletionContributor;
import com.intellij.codeInsight.template.postfix.templates.PostfixLiveTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Key;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.util.PsiTreeUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Copy of {@link com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider}.
 */
public abstract class PatchedJavaPostfixTemplateProvider implements PostfixTemplateProvider {

  public static final Key<SmartPsiElementPointer<PsiElement>> ADDED_SEMICOLON = Key.create("postfix_added_semicolon");

  private final Set<PostfixTemplate> templates;

  protected PatchedJavaPostfixTemplateProvider(Set<PostfixTemplate> templates) {
    this.templates = templates;
  }

  @NotNull
  @Override
  public Set<PostfixTemplate> getTemplates() {
    return templates;
  }

  @Override
  public boolean isTerminalSymbol(char currentChar) {
    return currentChar == '.' || currentChar == '!';
  }

  @Override
  public void preExpand(@NotNull final PsiFile file, @NotNull final Editor editor) {
    ApplicationManager.getApplication().assertIsDispatchThread();

    file.putUserData(ADDED_SEMICOLON, null);
    if (isSemicolonNeeded(file, editor)) {
      ApplicationManager.getApplication().runWriteAction(new Runnable() {
        @Override
        public void run() {
          CommandProcessor.getInstance().runUndoTransparentAction(new Runnable() {
            public void run() {
              Document document = file.getViewProvider().getDocument();
              assert document != null;
              EditorModificationUtil.insertStringAtCaret(editor, ";", false, false);
              PsiDocumentManager.getInstance(file.getProject()).commitDocument(document);
              PsiElement at = file.findElementAt(editor.getCaretModel().getOffset());
              if (at != null && at.getNode().getElementType() == JavaTokenType.SEMICOLON) {
                file.putUserData(ADDED_SEMICOLON,
                                 SmartPointerManager.getInstance(file.getProject()).createSmartPsiElementPointer(at));
              }
            }
          });
        }
      });
    }
  }

  @Override
  public void afterExpand(@NotNull final PsiFile file, @NotNull final Editor editor) {
    final SmartPsiElementPointer<PsiElement> pointer = file.getUserData(ADDED_SEMICOLON);
    if (pointer != null) {
      final PsiElement addedSemicolon = pointer.getElement();
      file.putUserData(ADDED_SEMICOLON, null);
      if (addedSemicolon != null && addedSemicolon.isValid()) {
        CommandProcessor.getInstance().runUndoTransparentAction(new Runnable() {
          @Override
          public void run() {
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
              public void run() {
                addedSemicolon.delete();
              }
            });
          }
        });
      }
    }
  }

  @NotNull
  @Override
  public PsiFile preCheck(final @NotNull PsiFile copyFile, final @NotNull Editor realEditor, final int currentOffset) {
    return ApplicationManager.getApplication().runReadAction(new Computable<PsiFile>() {
      @Override
      public PsiFile compute() {
        Document document = copyFile.getViewProvider().getDocument();
        assert document != null;
        CharSequence sequence = document.getCharsSequence();
        StringBuilder fileContentWithSemicolon = new StringBuilder(sequence);
        if (isSemicolonNeeded(copyFile, realEditor)) {
          fileContentWithSemicolon.insert(currentOffset, ';');
          return PostfixLiveTemplate.copyFile(copyFile, fileContentWithSemicolon);
        }

        return copyFile;
      }
    });
  }

  private static boolean isSemicolonNeeded(@NotNull PsiFile file, @NotNull Editor editor) {
    return semicolonNeeded(editor, file, CompletionInitializationContext.calcStartOffset(editor));
  }

  /**
   * Modified version copied from {@link JavaCompletionContributor#semicolonNeeded(Editor, PsiFile, int)}. This version
   * returns false when COLON is detected to correctly support the ternary operator in postfix completion.
   */
  private static boolean semicolonNeeded(@NotNull Editor editor, @NotNull PsiFile file, int startOffset) {
    final PsiJavaCodeReferenceElement ref =
        PsiTreeUtil.findElementOfClassAtOffset(file, startOffset, PsiJavaCodeReferenceElement.class, false);
    if (ref != null && !(ref instanceof PsiReferenceExpression)) {
      if (ref.getParent() instanceof PsiTypeElement) {
        return true;
      }
    }

    HighlighterIterator iterator = ((EditorEx) editor).getHighlighter().createIterator(startOffset);
    if (iterator.atEnd()) {
      return false;
    }

    if (iterator.getTokenType() == JavaTokenType.IDENTIFIER) {
      iterator.advance();
    }

    while (!iterator.atEnd() && ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(iterator.getTokenType())) {
      iterator.advance();
    }

    if (!iterator.atEnd()
        && (iterator.getTokenType() == JavaTokenType.LPARENTH || iterator.getTokenType() == JavaTokenType.COLON)) {
      return false; // modified to return false
    }

    while (!iterator.atEnd() && ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(iterator.getTokenType())) {
      iterator.advance();
    }

    if (iterator.atEnd() || iterator.getTokenType() != JavaTokenType.IDENTIFIER) {
      return false;
    }
    iterator.advance();

    while (!iterator.atEnd() && ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(iterator.getTokenType())) {
      iterator.advance();
    }
    if (iterator.atEnd()) {
      return false;
    }

    return iterator.getTokenType() == JavaTokenType.EQ || iterator.getTokenType() == JavaTokenType.LPARENTH;
  }

}
