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

import com.intellij.codeInsight.NullableNotNullManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatePsiInfo;
import com.intellij.codeInspection.inferNullity.NullityInferrer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.util.PsiTreeUtil;
import com.siyeh.ig.psiutils.ParenthesesUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.internal.StringBasedJavaPostfixTemplateWithChooser;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NON_VOID;
import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.JAVA_PSI_INFO;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.OPTIONAL;

/**
 * Postfix template for guava {@code com.google.common.base.Optional#fromNullable(Object)}.
 *
 * @author Bob Browning
 */
public class OptionalPostfixTemplate extends StringBasedJavaPostfixTemplateWithChooser {

  public OptionalPostfixTemplate() {
    this("optional");
  }

  public OptionalPostfixTemplate(String key) {
    super(key, "Optional.of(Object)", JAVA_PSI_INFO, IS_NON_VOID);
  }

  @Nullable
  @Override
  public String getTemplateString(@NotNull PsiElement element) {
    if (isAnnotatedNullable(element)) {
      return getStaticMethodPrefix(OPTIONAL, "fromNullable", element) + "($expr$)$EOS$";
    } else {
      return getStaticMethodPrefix(OPTIONAL, "of", element) + "($expr$)$EOS$";
    }
  }

  @Override
  protected boolean shouldUseStaticImportIfPossible(@NotNull Project project) {
    return false;
  }

  private static boolean isAnnotatedNullable(PsiElement element) {
    PsiExpression expression;
    if (element instanceof PsiExpression) {
      expression = (PsiExpression) element;
    } else {
      expression = PsiTreeUtil.getParentOfType(element, PsiExpression.class, true);
      if (expression == null) {
        return false;
      }
    }
    expression = ParenthesesUtils.stripParentheses(expression);
    if (!(expression instanceof PsiReferenceExpression)) {
      return false;
    }
    final PsiReferenceExpression referenceExpression = (PsiReferenceExpression) expression;
    final PsiElement target = referenceExpression.resolve();
    if (!(target instanceof PsiModifierListOwner)) {
      return false;
    }
    final PsiModifierListOwner modifierListOwner = (PsiModifierListOwner) target;
    return NullableNotNullManager.isNullable(modifierListOwner);
  }
}
