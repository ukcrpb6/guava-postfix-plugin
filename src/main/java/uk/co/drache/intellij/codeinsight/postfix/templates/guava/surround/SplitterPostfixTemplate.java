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
package uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.SPLITTER;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.IS_CHAR_SEQUENCE;

/**
 * Postfix template for guava {@code com.google.common.base.Splitter}.
 *
 * @author Bob Browning
 */
public class SplitterPostfixTemplate extends RichChooserStringBasedPostfixTemplate {

  public SplitterPostfixTemplate() {
    super("split", "Splitter.on(',').split(sequence)", IS_CHAR_SEQUENCE);
  }

  @Override
  public void setVariables(@NotNull Template template, @NotNull PsiElement element) {
    template.addVariable("on", new TextExpression("','"), true);
  }

  @Override
  public final String getTemplateString(@NotNull PsiElement element) {
    return getStaticMethodPrefix(SPLITTER, "on", element) + "($on$).split($expr$)$END$";
  }

  @Override
  protected boolean shouldUseStaticImportIfPossible(@NotNull Project project) {
    return false;
  }
}
