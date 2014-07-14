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
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.templates.ForIndexedPostfixTemplate.IS_NUMBER_OR_ARRAY_OR_ITERABLE;
import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.PRECONDITIONS;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.getExpressionNumberOrArrayOrIterableBound;

/**
 * Postfix template for guava {@code com.google.common.base.Preconditions#checkElementIndex(int, int)}.
 *
 * @author Bob Browning
 */
public class CheckElementIndexPostfixTemplate extends RichStringBasedPostfixTemplate {

  @NonNls
  private static final String EXAMPLE = "Preconditions.checkElementIndex(index, size)";

  public CheckElementIndexPostfixTemplate() {
    super("checkElementIndex", EXAMPLE, JAVA_PSI_INFO, IS_NUMBER_OR_ARRAY_OR_ITERABLE);
  }

  @Override
  public void setVariables(@NotNull Template template, @NotNull PsiElement element) {
    TextExpression index = new TextExpression("0");
    template.addVariable("index", index, index, true);
  }

  @Override
  public final String getTemplateString(@NotNull PsiElement element) {
    PsiExpression expr = (PsiExpression) element;
    String bound = getExpressionNumberOrArrayOrIterableBound(expr);
    if (bound == null) {
      return null;
    }

    return getStringTemplate(expr).replace("$bound$", bound);
  }

  @NotNull
  protected String getStringTemplate(@NotNull PsiElement context) {
    return getStaticMethodPrefix(PRECONDITIONS, "checkElementIndex", context) + "($index$, $bound$);$END$";
  }

  @Override
  protected boolean shouldAddExpressionToContext() {
    return false;
  }

  @Override
  protected boolean shouldUseStaticImportIfPossible() {
    return true;
  }
}
