package uk.co.drache.intellij.codeinsight.postfix.utils;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.CommonClassNames;
import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.InheritanceUtil;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.siyeh.ig.PsiReplacementUtil.replaceExpressionAndShorten;
import static uk.co.drache.intellij.codeinsight.postfix.utils.ImportUtils.addStaticImport;

/**
 * Collection of helper methods for surrounding an expression with guava preconditions.
 *
 * @author Bob Browning
 */
public class GuavaPostfixTemplatesUtils {

  @NonNls
  private static final String JAVA_LANG_CHAR_SEQUENCE = "java.lang.CharSequence";

  @Contract("null -> false")
  public static boolean isCollection(@Nullable PsiType type) {
    return type != null && InheritanceUtil.isInheritor(type, CommonClassNames.JAVA_UTIL_COLLECTION);
  }

  @Contract("null -> false")
  public static boolean isIterator(@Nullable PsiType type) {
    return type != null && InheritanceUtil.isInheritor(type, CommonClassNames.JAVA_UTIL_ITERATOR);
  }


  @Contract("null -> false")
  public static boolean isCharSequence(@Nullable PsiType type) {
    return type != null && InheritanceUtil.isInheritor(type, JAVA_LANG_CHAR_SEQUENCE);
  }

  @Contract("null -> false")
  public static boolean isObjectArrayTypeExpression(@Nullable PsiType type) {
    return type instanceof PsiArrayType &&
           !(((PsiArrayType) type).getComponentType() instanceof PsiPrimitiveType);
  }

  public static TextRange emptyRangeAt(int offset) {
    return TextRange.EMPTY_RANGE.shiftRight(offset);
  }

  /**
   * Surrounds and replaces expression using static imported method.
   *
   * @param expression     The expression to be surrounded
   * @param qualifierClass The class to import the static method from
   * @param staticMethod   The static method to import
   */
  public static PsiElement surroundExpressionAndShortenStatic(@NotNull PsiExpression expression,
                                                              @NotNull @NonNls String qualifierClass,
                                                              @NotNull @NonNls String staticMethod) {
    return surroundExpressionAndShortenStatic(expression, qualifierClass, staticMethod, true);
  }

  /**
   * Surrounds and replaces expression using static imported method.
   *
   * @param expression      The expression to be surrounded
   * @param qualifierClass  The class to import the static method from
   * @param staticMethod    The static method to use
   * @param tryStaticImport If true then static import will be added if possible
   */
  public static PsiElement surroundExpressionAndShortenStatic(@NotNull PsiExpression expression,
                                                              @NotNull @NonNls String qualifierClass,
                                                              @NotNull @NonNls String staticMethod,
                                                              boolean tryStaticImport) {
    StringBuilder builder = new StringBuilder();
    boolean requiresQualifierClass = !tryStaticImport;
    if (tryStaticImport) {
      requiresQualifierClass = !addStaticImport(qualifierClass, staticMethod, expression);
    }
    if (requiresQualifierClass) {
      builder.append(qualifierClass).append(".");
    }
    builder.append(staticMethod).append("(").append(expression.getText()).append(")");
    return replaceExpressionAndShorten(expression, builder.toString());
  }

}
