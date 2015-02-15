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

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NON_VOID;
import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NOT_PRIMITIVE;
import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.OPTIONAL;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.IS_NON_PRIMITIVE_ARRAY_OR_ITERABLE_OR_ITERATOR;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isAnnotatedNullable;

/**
 * Postfix template for guava {@code com.google.common.base.Optional#fromNullable(Object)}.
 *
 * @author Bob Browning
 */
public class OptionalPostfixTemplate extends RichChooserStringBasedPostfixTemplate {

  public OptionalPostfixTemplate() {
    this("optional");
  }

  public OptionalPostfixTemplate(String key) {
    super(key, "Optional.of(Object)", JAVA_PSI_INFO, IS_NOT_PRIMITIVE);
  }

  @Nullable
  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    if (isAnnotatedNullable(element)) {
      return getStaticMethodPrefix(OPTIONAL, "fromNullable", element) + "($expr$)$END$";
    } else {
      return getStaticMethodPrefix(OPTIONAL, "of", element) + "($expr$)$END$";
    }
  }

  @Override
  protected boolean shouldUseStaticImportIfPossible(@NotNull Project project) {
    return false;
  }

}
