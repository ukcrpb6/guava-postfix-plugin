package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;

/**
 * Surround with {@link com.google.common.base.Preconditions#checkState(boolean)}.
 *
 * @author Bob Browning
 */
public class JavaCheckStateSurrounder extends JavaCheckBaseSurrounder {

  @NonNls
  private static final String GUAVA_PRECONDITIONS_CHECK_STATE = "checkState";

  public JavaCheckStateSurrounder() {
    super(GUAVA_PRECONDITIONS_CHECK_STATE);
  }

  @Override
  public boolean isApplicable(PsiExpression expr) {
    return PostfixTemplatesUtils.isBoolean(expr.getType());
  }

}
