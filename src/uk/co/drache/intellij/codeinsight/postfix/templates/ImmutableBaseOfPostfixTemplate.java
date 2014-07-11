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
package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Postfix template for guava immutable .of methods.
 *
 * @author Bob Browning
 */
public abstract class ImmutableBaseOfPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected ImmutableBaseOfPostfixTemplate(@NotNull String name,
                                           @NotNull String description,
                                           @NotNull String example) {
    super(name, description, example);
  }

  protected abstract String getImmutableCollectionImplType();

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    surroundExpressionAndShortenStatic(expr, getImmutableCollectionImplType(), "of", false);
  }

  @Override
  public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
    return super.isApplicable(context, copyDocument, newOffset);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expression) {
        return expression != null && expression.getType() != null;
      }
    };
  }
}
