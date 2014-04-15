package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.codeInsight.template.postfix.util.Aliases;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.generation.surroundWith.JavaCheckNotNullSurrounder;

/**
 * Add postfix completion for guava checkNotNull.
 *
 * @author Bob Browning
 */
@Aliases(value = ".cnn")
public class CheckNotNullPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String POSTFIX_COMMAND = "checkNotNull";

  @NonNls
  private static final String DESCRIPTION = "Checks that the value is not null";

  @NonNls
  private static final String EXAMPLE = "Preconditions.checkNotNull(expr)";

  public CheckNotNullPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    PostfixTemplatesUtils.apply(new JavaCheckNotNullSurrounder(), expr.getProject(), editor, expr);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return PostfixTemplatesUtils.isNotPrimitiveTypeExpression(expr);
      }
    };
  }
}
