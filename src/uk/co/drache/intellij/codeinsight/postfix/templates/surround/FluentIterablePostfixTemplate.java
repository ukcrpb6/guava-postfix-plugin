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

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.FLUENT_ITERABLE;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.IS_ITERABLE;

/**
 * Postfix template for guava {@code com.google.common.collect.FluentIterable#from(Iterable)}.
 *
 * @author Bob Browning
 */
public class FluentIterablePostfixTemplate extends RichChooserStringBasedPostfixTemplate {

  @NonNls
  private static final String FQ_METHOD_FROM = FLUENT_ITERABLE.getQualifiedStaticMethodName("from");

  public FluentIterablePostfixTemplate() {
    super("fluentIterable", "FluentIterable.from(iterable)", IS_ITERABLE);
  }

  @Nullable
  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    return FQ_METHOD_FROM + "($expr$)$END$";
  }

  @Override
  protected boolean shouldUseStaticImportIfPossible(Project project) {
    return false;
  }
}
