package uk.co.drache.intellij.codeinsight.generation.surroundWith;

import com.intellij.codeInsight.generation.surroundWith.JavaExpressionSurrounder;
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
 * @author Bob Browning
 */
public abstract class JavaCheckBaseSurrounder extends JavaExpressionSurrounder {

  @NonNls
  private static final String GUAVA_PRECONDITIONS = "com.google.common.base.Preconditions";

  private final String methodName;

  protected JavaCheckBaseSurrounder(@NotNull String methodName) {
    this.methodName = methodName;
  }

  @Override
  public TextRange surroundExpression(Project project, Editor editor, PsiExpression expr)
      throws IncorrectOperationException {
    PsiManager manager = expr.getManager();
    PsiElementFactory factory = JavaPsiFacade.getInstance(manager.getProject()).getElementFactory();
    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);

    ImportUtils.addStaticImport(GUAVA_PRECONDITIONS, methodName, expr);

    PsiMethodCallExpression checkStatement = (PsiMethodCallExpression)
        ((PsiExpressionStatement) factory.createStatementFromText(getExpansionExpression(), null)).getExpression();

    checkStatement = (PsiMethodCallExpression) codeStyleManager.reformat(checkStatement);
    checkStatement.getArgumentList().getExpressions()[0].replace(expr);
    expr = (PsiExpression) IntroduceVariableBase.replace(expr, checkStatement, project);
    int offset = expr.getTextRange().getEndOffset();
    return new TextRange(offset, offset);
  }

  @Override
  public String getTemplateDescription() {
    return getExpansionExpression();
  }

  private String getExpansionExpression() {
    return methodName + "(expr)";
  }
}
