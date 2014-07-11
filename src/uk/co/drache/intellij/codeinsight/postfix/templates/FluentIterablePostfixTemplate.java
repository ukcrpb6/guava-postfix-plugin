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
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Postfix template for guava {@link com.google.common.collect.FluentIterable#from(Iterable)}.
 *
 * @author Bob Browning
 */
public class FluentIterablePostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String POSTFIX_COMMAND = "fluentIterable";

  @NonNls
  private static final String DESCRIPTION = "Creates a fluent iterable that wraps iterable, "
                                            + "or iterable itself if it is already a FluentIterable";

  @NonNls
  private static final String EXAMPLE = "FluentIterable.from(iterable)";

  public FluentIterablePostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    surroundExpressionAndShortenStatic(expr, GuavaClassNames.FLUENT_ITERABLE, "from", false);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return expr != null && PostfixTemplatesUtils.isIterable(expr.getType());
      }
    };
  }
}
