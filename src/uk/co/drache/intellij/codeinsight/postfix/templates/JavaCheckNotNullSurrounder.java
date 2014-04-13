package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.generation.surroundWith.JavaExpressionSurrounder;
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

/**
 * @author Bob Browning
 */
public class JavaCheckNotNullSurrounder extends JavaExpressionSurrounder {

  @NonNls
  private static final String EXPANSION_EXPRESSION = "checkNotNull(expr)";

  @NonNls
  private static final String GUAVA_PRECONDITIONS = "com.google.common.base.Preconditions";

  @NonNls
  private static final String GUAVA_PRECONDITIONS_CHECK_NOT_NULL = "checkNotNull";

  @Override
  public boolean isApplicable(PsiExpression expr) {
    return PostfixTemplatesUtils.isNotPrimitiveTypeExpression(expr);
  }

  @Override
  public TextRange surroundExpression(Project project, Editor editor, PsiExpression expr)
      throws IncorrectOperationException {
    PsiManager manager = expr.getManager();
    PsiElementFactory factory = JavaPsiFacade.getInstance(manager.getProject()).getElementFactory();
    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);

    ImportUtils.addStaticImport(GUAVA_PRECONDITIONS, GUAVA_PRECONDITIONS_CHECK_NOT_NULL, expr);

    PsiMethodCallExpression checkNotNullStatement = (PsiMethodCallExpression)
        ((PsiExpressionStatement) factory.createStatementFromText(EXPANSION_EXPRESSION, null)).getExpression();

    checkNotNullStatement = (PsiMethodCallExpression) codeStyleManager.reformat(checkNotNullStatement);
    checkNotNullStatement.getArgumentList().getExpressions()[0].replace(expr);
    expr = (PsiExpression) IntroduceVariableBase.replace(expr, checkNotNullStatement, project);
    int offset = expr.getTextRange().getEndOffset();
    return new TextRange(offset, offset);
  }

  @Override
  public String getTemplateDescription() {
    return EXPANSION_EXPRESSION;
  }
}
