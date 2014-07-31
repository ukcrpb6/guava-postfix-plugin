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
package uk.co.drache.intellij.codeinsight.postfix;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.util.containers.ContainerUtil;

import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.templates.CheckArgumentPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.CheckElementIndexPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.CheckNotNullPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.CheckPositionIndexPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.CheckPositionIndexesPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.CheckStatePostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.FluentIterablePostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.ImmutableListCopyOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.ImmutableListOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.ImmutableSetCopyOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.ImmutableSetOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.JoinerPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.OptionalPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.SplitterPostfixTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Postfix template provider for extension point.
 *
 * @author Bob Browning
 */
public class GuavaPostfixTemplateProvider extends JavaPostfixTemplateProvider {

  private final HashSet<PostfixTemplate> templates;

  public GuavaPostfixTemplateProvider() {
    templates = ContainerUtil.newHashSet(
        new CheckArgumentPostfixTemplate(),
        new CheckElementIndexPostfixTemplate(),
        new CheckNotNullPostfixTemplate(),
        new CheckNotNullPostfixTemplate("cnn"),
        new CheckPositionIndexPostfixTemplate(),
        new CheckPositionIndexesPostfixTemplate(),
        new CheckStatePostfixTemplate(),
        new FluentIterablePostfixTemplate(),
        new ImmutableListCopyOfPostfixTemplate(),
        new ImmutableListOfPostfixTemplate(),
        new ImmutableSetCopyOfPostfixTemplate(),
        new ImmutableSetOfPostfixTemplate(),
        new JoinerPostfixTemplate(),
        new SplitterPostfixTemplate(),
        new OptionalPostfixTemplate()
    );
  }

  @NotNull
  @Override
  public Set<PostfixTemplate> getTemplates() {
    return templates;
  }
}
