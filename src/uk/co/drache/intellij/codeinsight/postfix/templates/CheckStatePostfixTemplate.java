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
 * Add postfix completion for guava check argument.
 *
 * @author Bob Browning
 */
@Aliases(value = ".cs")
public class CheckStatePostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected CheckStatePostfixTemplate() {
    super("checkstate", "Checks some state of the object, not dependent on the method arguments", "checkState(expr)");
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    if (!PostfixTemplatesUtils.isBoolean(expr.getType())) {
      return;
    }
    TextRange range = GuavaPostfixTemplatesUtils.checkStateStatement(expr.getProject(), editor, expr);
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
        return PostfixTemplatesUtils.isBoolean(expr.getType());
      }
    };
  }
}
