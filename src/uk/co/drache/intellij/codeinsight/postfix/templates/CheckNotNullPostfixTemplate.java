package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.codeInsight.template.postfix.util.Aliases;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;

/**
 * Add postfix completion for guava checkNotNull.
 *
 * @author Bob Browning
 */
@Aliases(value = ".cnn")
public class CheckNotNullPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected CheckNotNullPostfixTemplate() {
    super("checknotnull", "Checks expression to be not null", "checkNotNull(expr)");
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    if (!PostfixTemplatesUtils.isNotPrimitiveTypeExpression(expr)) {
      return;
    }

    TextRange range = GuavaPostfixTemplatesUtils.checkNotNullStatement(expr.getProject(), editor, expr);
    if (range != null) {
      editor.getCaretModel().moveToOffset(range.getStartOffset());
    }
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
