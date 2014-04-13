package uk.co.drache.intellij.codeinsight.generation.surroundWith;

import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;

/**
 * Surround with {@link com.google.common.base.Preconditions#checkNotNull(Object)}.
 *
 * @author Bob Browning
 */
public class JavaCheckNotNullSurrounder extends JavaCheckBaseSurrounder {

  @NonNls
  private static final String GUAVA_PRECONDITIONS_CHECK_NOT_NULL = "checkNotNull";

  public JavaCheckNotNullSurrounder() {
    super(GUAVA_PRECONDITIONS_CHECK_NOT_NULL);
  }

  @Override
  public boolean isApplicable(PsiExpression expr) {
    return PostfixTemplatesUtils.isNotPrimitiveTypeExpression(expr);
  }

}
