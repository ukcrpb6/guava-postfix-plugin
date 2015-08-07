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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceExpression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.settings.GuavaPostfixProjectSettings;

import java.util.LinkedList;
import java.util.List;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NOT_PRIMITIVE;
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
    super(alias, "Preconditions.checkNotNull(expr)", IS_NON_NULL_OBJECT);
  }

  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    if (isSuggestMessageForNullityCheck(element) && element instanceof PsiReferenceExpression) {
      return getStaticMethodPrefix(PRECONDITIONS, "checkNotNull", element) + "($expr$, \"$var$\")$END$";
    }
    return getStaticMethodPrefix(PRECONDITIONS, "checkNotNull", element) + "($expr$)$END$";
  }

  private boolean isSuggestMessageForNullityCheck(@NotNull PsiElement element) {
    return GuavaPostfixProjectSettings.getInstance(element.getProject()).isSuggestMessageForCheckNotNull();
  }

  private LinkedList<String> getLookupSuffixes(@NotNull PsiElement element) {
    GuavaPostfixProjectSettings settings = GuavaPostfixProjectSettings.getInstance(element.getProject());
    return Lists.newLinkedList(settings.getSuggestionMessagesForCheckNotNull());
  }

  /**
   * Create a new instance of a code template for the current postfix template.
   *
   * @param project        The current project
   * @param manager        The template manager
   * @param templateString The template string
   */
  protected Template createTemplate(Project project, TemplateManager manager, String templateString) {
    Template template = manager.createTemplate("", "", templateString);
    template.setToReformat(shouldReformat());
    template.setValue(Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE, shouldUseStaticImportIfPossible(project));

    return template;
  }

  @Override
  protected void setVariables(@NotNull Template template, @NotNull final PsiElement element) {
    if (isSuggestMessageForNullityCheck(element) && element instanceof PsiReferenceExpression) {
      final String exprText = element.getText();

      LinkedList<String> lookupSuffixes = getLookupSuffixes(element);
      lookupSuffixes.addFirst("");

      // prepend expression text, create set to remove duplicates
      ImmutableSet<String> strings = FluentIterable.from(lookupSuffixes)
          .transform(new Function<String, String>() {
            @Override
            public String apply(String s) {
              return Strings.isNullOrEmpty(s) ? exprText : exprText + " " + s;
            }
          }).toSet();

      // convert to lookup elements
      final LookupElement[] lookupElements = FluentIterable.from(strings).
          transform(new Function<String, LookupElement>() {
            @Override
            public LookupElement apply(String s) {
              return lookupElement(s);
            }
          }).toArray(LookupElement.class);

      final TextResult result = new TextResult(exprText);

      Expression expr = new Expression() {
        @Nullable
        @Override
        public Result calculateResult(ExpressionContext expressionContext) {
          return result;
        }

        @Nullable
        @Override
        public Result calculateQuickResult(ExpressionContext expressionContext) {
          return result;
        }

        @Nullable
        @Override
        public LookupElement[] calculateLookupItems(ExpressionContext expressionContext) {
          return lookupElements;
        }
      };

      template.addVariable("var", expr, true);
    }
  }

  @NotNull
  private LookupElement lookupElement(final String text) {
    return new LookupElement() {
      @NotNull
      @Override
      public String getLookupString() {
        return text;
      }
    };
  }
}
