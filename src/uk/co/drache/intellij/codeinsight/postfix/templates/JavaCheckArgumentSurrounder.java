package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionStatement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.refactoring.introduceVariable.IntroduceVariableBase;
import com.intellij.util.IncorrectOperationException;
import com.siyeh.ig.psiutils.ImportUtils;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Surround with {@link com.google.common.base.Preconditions#checkArgument(boolean)}.
 *
 * @author Bob Browning
 */
public class JavaCheckArgumentSurrounder extends JavaCheckBaseSurrounder {

  @NonNls
  private static final String GUAVA_PRECONDITIONS_CHECK_ARGUMENT = "checkArgument";

  public JavaCheckArgumentSurrounder() {
    super(GUAVA_PRECONDITIONS_CHECK_ARGUMENT);
  }

  @Override
  public boolean isApplicable(PsiExpression expr) {
    return PostfixTemplatesUtils.isBoolean(expr.getType());
  }

}
