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
package uk.co.drache.intellij.codeinsight.generation.surroundWith;

import com.intellij.codeInsight.generation.surroundWith.JavaExpressionSurrounder;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NonNls;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.emptyRangeAt;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Surround with {@link com.google.common.base.Preconditions#checkNotNull(Object)}.
 *
 * @author Bob Browning
 */
public class JavaCheckNotNullSurrounder extends JavaExpressionSurrounder {

  @NonNls
  private static final String DESCRIPTION = "Checks that the value is not null";

  @NonNls
  private static final String CHECK_NOT_NULL_METHOD = "checkNotNull";

  @Override
  public boolean isApplicable(PsiExpression expr) {
    return PostfixTemplatesUtils.isNotPrimitiveTypeExpression(expr);
  }

  @Override
  public TextRange surroundExpression(Project project, Editor editor, PsiExpression expr)
      throws IncorrectOperationException {
    PsiElement element = surroundExpressionAndShortenStatic(expr, GuavaClassNames.PRECONDITIONS, CHECK_NOT_NULL_METHOD);
    return emptyRangeAt(element.getTextRange().getEndOffset());
  }

  @Override
  public String getTemplateDescription() {
    return DESCRIPTION;
  }
}
