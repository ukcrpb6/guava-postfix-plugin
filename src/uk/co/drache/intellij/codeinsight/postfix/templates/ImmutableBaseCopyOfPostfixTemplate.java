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
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isIterable;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isCollection;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isIterator;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isObjectArrayTypeExpression;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Postfix template for guava immutable .copyOf methods.
 *
 * @author Bob Browning
 */
public abstract class ImmutableBaseCopyOfPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected ImmutableBaseCopyOfPostfixTemplate(@NotNull String name,
                                               @NotNull String description,
                                               @NotNull String example) {
    super(name, description, example);
  }

  protected abstract String getImmutableCollectionImplType();

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    surroundExpressionAndShortenStatic(expr, getImmutableCollectionImplType(), "copyOf", false);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return expr != null && (
            isObjectArrayTypeExpression(expr.getType()) ||
            isCollection(expr.getType()) ||
            isIterable(expr.getType()) ||
            isIterator(expr.getType())
        );
      }
    };
  }
}
