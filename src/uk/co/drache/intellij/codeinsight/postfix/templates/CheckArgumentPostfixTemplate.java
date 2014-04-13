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
@Aliases(value = ".ca")
public class CheckArgumentPostfixTemplate extends PostfixTemplate {

  public static final String CHECK_ARGUMENT_METHOD = "checkArgument";

  protected CheckArgumentPostfixTemplate() {
    super("checkargument", "Checks that the boolean is true", "checkArgument(expr);");
  }

  @Override
  public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
    PsiExpression expr = getTopmostExpression(context);
    return expr != null && PostfixTemplatesUtils.isBoolean(expr.getType());
  }

  @Override
  public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
    boolean withImport = addStaticImport(GUAVA_PRECONDITIONS, CHECK_ARGUMENT_METHOD, context);
    createStatement(context, editor,
                    (withImport ? "" : GUAVA_PRECONDITIONS + ".") + CHECK_ARGUMENT_METHOD + "(", ")", 0);
  }

}
