package uk.co.drache.intellij.codeinsight.generation.surroundWith;

import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;

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
