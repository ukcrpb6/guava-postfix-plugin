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

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.IS_NON_PRIMITIVE_ARRAY_OR_ITERABLE_OR_ITERATOR;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isTopmostExpression;

/**
 * Postfix template for guava immutable .copyOf and .of methods.
 *
 * @author Bob Browning
 */
public abstract class ImmutableBasePostfixTemplate extends StringBasedJavaPostfixTemplateWithChooser {

  private final GuavaClassName className;
  private final String methodName;

  protected ImmutableBasePostfixTemplate(@NotNull String key, @NotNull String example,
                                         @NotNull GuavaClassName className,
                                         @NotNull String methodName) {
    super(key, example, JAVA_PSI_INFO, IS_NON_PRIMITIVE_ARRAY_OR_ITERABLE_OR_ITERATOR);
    this.className = className;
    this.methodName = methodName;
  }

  @Nullable
  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    return getStaticMethodPrefix(className, methodName, element) +
           (isTopmostExpression(element) ? "($expr$);$END$" : "($expr$)$END$");
  }


  @Override
  protected boolean shouldUseStaticImportIfPossible(@NotNull Project project) {
    return false;
  }
}
