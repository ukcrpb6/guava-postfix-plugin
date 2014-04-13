package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.codeInsight.template.postfix.util.Aliases;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.generation.surroundWith.JavaCheckNotNullSurrounder;

/**
 * Add postfix completion for guava checkNotNull.
 *
 * @author Bob Browning
 */
@Aliases(value = ".cnn")
public class CheckNotNullPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected CheckNotNullPostfixTemplate() {
    super("checknotnull", "Checks that the value is not null", "checkNotNull(expr)");
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
