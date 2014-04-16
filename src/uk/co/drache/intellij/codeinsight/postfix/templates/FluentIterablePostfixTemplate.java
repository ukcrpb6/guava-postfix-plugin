package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Postfix template for guava {@link com.google.common.collect.FluentIterable#from(Iterable)}.
 *
 * @author Bob Browning
 */
public class FluentIterablePostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String POSTFIX_COMMAND = "fluentIterable";

  @NonNls
  private static final String DESCRIPTION = "Creates a fluent iterable that wraps iterable, "
                                            + "or iterable itself if it is already a FluentIterable";

  @NonNls
  private static final String EXAMPLE = "FluentIterable.from(iterable)";

  public FluentIterablePostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    surroundExpressionAndShortenStatic(expr, GuavaClassNames.FLUENT_ITERABLE, "from", false);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return expr != null && PostfixTemplatesUtils.isIterable(expr.getType());
      }
    };
  }
}
