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

import uk.co.drache.intellij.codeinsight.generation.surroundWith.JavaCheckNotNullSurrounder;

/**
 * Postfix template for guava {@link com.google.common.base.Preconditions#checkNotNull(Object)}.
 *
 * @author Bob Browning
 */
public class CheckNotNullPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String CHECK_NOT_NULL_METHOD = "checkNotNull";

  @NonNls
  private static final String DESCRIPTION = "Checks that the value is not null";

  @NonNls
  private static final String EXAMPLE = "Preconditions.checkNotNull(expr)";

  public CheckNotNullPostfixTemplate() {
    super(CHECK_NOT_NULL_METHOD, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    PostfixTemplatesUtils.apply(new JavaCheckNotNullSurrounder(), expr.getProject(), editor, expr);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return PostfixTemplatesUtils.isNotPrimitiveTypeExpression(expr);
      }
    };
  }
}
