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
package uk.co.drache.intellij.codeinsight.postfix.templates.surround;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NOT_PRIMITIVE;
import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.PRECONDITIONS;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isAnnotatedNullable;

/**
 * Postfix template for guava {@code com.google.common.base.Preconditions#checkNotNull(Object)}.
 *
 * @author Bob Browning
 */
public class CheckNotNullPostfixTemplate extends RichChooserStringBasedPostfixTemplate {

  public static final Condition<PsiElement> IS_NON_NULL_OBJECT = new Condition<PsiElement>() {
    @Override
    public boolean value(PsiElement element) {
      return IS_NOT_PRIMITIVE.value(element) && !isAnnotatedNullable(element);
    }
  };

  public CheckNotNullPostfixTemplate() {
    this("checkNotNull");
  }

  public CheckNotNullPostfixTemplate(@NotNull String alias) {
    super(alias, "Preconditions.checkNotNull(expr)", JAVA_PSI_INFO, IS_NON_NULL_OBJECT);
  }

  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    return getStaticMethodPrefix(PRECONDITIONS, "checkNotNull", element) + "($expr$)$END$";
  }

}
