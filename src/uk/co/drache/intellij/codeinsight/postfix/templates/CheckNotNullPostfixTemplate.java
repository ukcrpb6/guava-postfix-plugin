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

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NOT_PRIMITIVE;
import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.PRECONDITIONS;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isTopmostExpression;

/**
 * Postfix template for guava {@code com.google.common.base.Preconditions#checkNotNull(Object)}.
 *
 * @author Bob Browning
 */
public class CheckNotNullPostfixTemplate extends StringBasedJavaPostfixTemplateWithChooser {

  public CheckNotNullPostfixTemplate() {
    super("checkNotNull", "Preconditions.checkNotNull(expr)", JAVA_PSI_INFO, IS_NOT_PRIMITIVE);
  }

  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    return getStaticMethodPrefix(PRECONDITIONS, "checkNotNull", element) +
           (isTopmostExpression(element) ? "($expr$);$END$" : "($expr$)$END$");
  }

  @Override
  protected boolean shouldUseStaticImportIfPossible() {
    return true;
  }
}
