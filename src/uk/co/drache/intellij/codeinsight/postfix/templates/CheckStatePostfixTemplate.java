package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.util.Aliases;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.GUAVA_PRECONDITIONS;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.createStatement;
import static uk.co.drache.intellij.codeinsight.postfix.utils.ImportUtils.addStaticImport;

/**
 * Add postfix completion for guava check argument.
 *
 * @author Bob Browning
 */
@Aliases(value = ".cs")
public class CheckStatePostfixTemplate extends PostfixTemplate {

  public static final String CHECK_STATE_METHOD = "checkState";

  protected CheckStatePostfixTemplate() {
    super("checkstate", "Checks some state of the object, not dependent on the method arguments", "checkState(expr)");
  }

  @Override
  public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
    PsiExpression expr = getTopmostExpression(context);
    return expr != null && PostfixTemplatesUtils.isBoolean(expr.getType());
  }

  @Override
  public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
    boolean withImport = addStaticImport(GUAVA_PRECONDITIONS, CHECK_STATE_METHOD, context);
    createStatement(context, editor,
                    (withImport ? "" : GUAVA_PRECONDITIONS + ".") + CHECK_STATE_METHOD + "(", ")", 0);
  }

}
