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
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.internal.RichTopmostStringBasedPostfixTemplate;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_BOOLEAN;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.PRECONDITIONS;

/**
 * Postfix template for guava {@code com.google.common.base.Preconditions#checkArgument(boolean)}.
 *
 * @author Bob Browning
 */
public class CheckArgumentPostfixTemplate extends RichTopmostStringBasedPostfixTemplate {

  public CheckArgumentPostfixTemplate() {
    super("checkArgument", "Preconditions.checkArgument(expr)", IS_BOOLEAN);
  }

  @Nullable
  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    return getStaticMethodPrefix(PRECONDITIONS, "checkArgument", element) + "($expr$);$END$";
  }

}
