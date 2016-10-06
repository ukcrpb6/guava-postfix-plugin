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
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.drache.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;
import uk.co.drache.intellij.codeinsight.postfix.settings.GuavaPostfixProjectSettings;

import java.util.LinkedList;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NOT_PRIMITIVE;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isAnnotatedNullable;
import static uk.co.drache.intellij.codeinsight.postfix.utils.JavaClassName.OBJECTS;

/**
 * Postfix template for java 7 {@code java.util.requireNonNull(Object)}.
 */
public class RequireNonNullPostfixTemplate extends RichChooserStringBasedPostfixTemplate {

  private static final Condition<PsiElement> IS_NON_NULL_OBJECT = new Condition<PsiElement>() {
    @Override
    public boolean value(PsiElement element) {
      return IS_NOT_PRIMITIVE.value(element)
             && !isAnnotatedNullable(element);
    }
  };

  public RequireNonNullPostfixTemplate() {
    this("requireNonNull");
  }

  public RequireNonNullPostfixTemplate(@NotNull String alias) {
    super(alias, "Objects.requireNonNull(expr)", new Condition<PsiElement>() {
      @Override
      public boolean value(PsiElement element) {
        return PsiUtil.isLanguageLevel7OrHigher(element) && IS_NON_NULL_OBJECT.value(element);
      }
    });
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
  public String getTemplateString(@NotNull PsiElement element) {
    if (isSuggestMessageForNullityCheck(element) && element instanceof PsiReferenceExpression) {
      return getStaticMethodPrefix(OBJECTS, "requireNonNull", element) + "($expr$, \"$var$\")$END$";
    }
    return getStaticMethodPrefix(OBJECTS, "requireNonNull", element) + "($expr$)$END$";
  }

  @Override
  protected void setVariables(@NotNull Template template, @NotNull PsiElement element) {
    if (isSuggestMessageForNullityCheck(element) && element instanceof PsiReferenceExpression) {
      final String exprText = element.getText();

      LinkedList<String> lookupSuffixes = getLookupSuffixes(element);
      if (settings(element).isSuggestNoPrefixForCheckNotNull()) {
        lookupSuffixes.addFirst("");
      }

      // prepend expression text, create set to remove duplicates
      ImmutableSet<String> strings = FluentIterable.from(lookupSuffixes)
          .transform(new Function<String, String>() {
            @Override
            public String apply(String s) {
              return Strings.isNullOrEmpty(s) ? exprText : exprText + " " + s;
            }
          }).toSet();

      Expression expr;
      if (strings.size() > 1) {
        // need to perform lookup expression

        // convert to lookup elements
        final LookupElement[] lookupElements = FluentIterable.from(strings).
            transform(new Function<String, LookupElement>() {
              @Override
              public LookupElement apply(String s) {
                return lookupElement(s);
              }
            }).toArray(LookupElement.class);

        final TextResult result = new TextResult(exprText);

        expr = new Expression() {
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
      } else {
        // automatically use only message, or fallback to empty suffix if no messages present
        expr = new TextExpression(Iterables.getFirst(strings, ""));
      }

      template.addVariable("var", expr, true);
    }
  }

  private LinkedList<String> getLookupSuffixes(@NotNull PsiElement element) {
    GuavaPostfixProjectSettings settings = GuavaPostfixProjectSettings.getInstance(element.getProject());
    return Lists.newLinkedList(settings.getSuggestionMessagesForCheckNotNull());
  }

  private boolean isSuggestMessageForNullityCheck(@NotNull PsiElement element) {
    return settings(element).isSuggestMessageForCheckNotNull();
  }

  private GuavaPostfixProjectSettings settings(@NotNull PsiElement element) {
    return GuavaPostfixProjectSettings.getInstance(element.getProject());
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
