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
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.CheckArgumentPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.CheckElementIndexPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.CheckPositionIndexPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.CheckPositionIndexesPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.CheckStatePostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.CheckNotNullPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.FluentIterablePostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.ImmutableListCopyOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.ImmutableListOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.ImmutableSetCopyOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.ImmutableSetOfPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.JoinerPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.OptionalPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround.SplitterPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.templates.java.surround.RequireNonNullPostfixTemplate;

/**
 * Postfix template provider for extension point.
 *
 * @author Bob Browning
 */
public class GuavaPostfixTemplateProvider extends JavaPostfixTemplateProvider {

  private final HashSet<PostfixTemplate> templates;

  public GuavaPostfixTemplateProvider() {
    templates =
        ContainerUtil.newHashSet(
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
            new OptionalPostfixTemplate(),
            // java 7+
            new RequireNonNullPostfixTemplate(),
            new RequireNonNullPostfixTemplate("rnn"));
  }

  @NotNull
  @Override
  public Set<PostfixTemplate> getTemplates() {
    return templates;
  }
}
