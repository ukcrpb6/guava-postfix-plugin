package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Postfix template for guava {@link com.google.common.base.Preconditions#checkArgument(boolean)}.
 *
 * @author Bob Browning
 */
public class CheckArgumentPostfixTemplate extends PostfixTemplate {

  @NonNls
  private static final String CHECK_ARGUMENT_METHOD = "checkArgument";

  @NonNls
  private static final String DESCRIPTION = "Checks that the boolean is true";

  @NonNls
  private static final String EXAMPLE = "Preconditions.checkArgument(expr);";

  public CheckArgumentPostfixTemplate() {
    super(CHECK_ARGUMENT_METHOD, DESCRIPTION, EXAMPLE);
  }

  @Override
  public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
    PsiExpression expr = getTopmostExpression(context);
    return expr != null && PostfixTemplatesUtils.isBoolean(expr.getType());
  }

  @Override
  public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
    PsiExpression expression = getTopmostExpression(context);
    if (expression == null) {
      PostfixTemplatesUtils.showErrorHint(context.getProject(), editor);
      return;
    }
    surroundExpressionAndShortenStatic(expression, GuavaClassNames.PRECONDITIONS, CHECK_ARGUMENT_METHOD);
  }

}
