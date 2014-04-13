package uk.co.drache.intellij.codeinsight.postfix.utils;

import com.intellij.psi.CommonClassNames;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.InheritanceUtil;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * Collection of helper methods for surrounding an expression with guava preconditions.
 *
 * @author Bob Browning
 */
public class GuavaPostfixTemplatesUtils {

  @Contract("null -> false")
  public static boolean isCollection(@Nullable PsiType type) {
    return type != null && InheritanceUtil.isInheritor(type, CommonClassNames.JAVA_UTIL_COLLECTION);
  }

}
