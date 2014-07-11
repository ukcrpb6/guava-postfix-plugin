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

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isArray;
import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isIterable;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isIterator;

/**
 * Postfix template for guava {@link com.google.common.base.Splitter}.
 *
 * @author Bob Browning
 */
public class JoinerPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String DESCRIPTION = "Joins pieces of text (specified as an array, "
                                            + "Iterable, varargs or even a Map) with a separator";

  @NonNls
  private static final String EXAMPLE = "Joiner.on(',').join(parts)";

  @NonNls
  private static final String POSTFIX_COMMAND = "join";

  public JoinerPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    Project project = expr.getProject();
    Document document = editor.getDocument();

    document.deleteString(expr.getTextRange().getStartOffset(), expr.getTextRange().getEndOffset());
    TemplateManager manager = TemplateManager.getInstance(project);

    Template template = manager.createTemplate("", "");
    template.setToIndent(true);
    template.setToShortenLongNames(true);
    template.setToReformat(true);

    template.addTextSegment(GuavaClassNames.JOINER + ".on(");
    template.addVariable("separator", new TextExpression("','"), true);
    template.addTextSegment(").join(");
    template.addTextSegment(expr.getText());
    template.addTextSegment(")");
    template.addEndVariable();

    manager.startTemplate(editor, template);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return expr != null && (isIterator(expr.getType()) ||
                                isArray(expr.getType()) ||
                                isIterable(expr.getType()));
      }
    };
  }
}
