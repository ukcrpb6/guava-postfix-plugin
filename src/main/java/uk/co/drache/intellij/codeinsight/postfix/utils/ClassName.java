package uk.co.drache.intellij.codeinsight.postfix.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * TODO: DESCRIBE PURPOSE OF CLASS
 */
public interface ClassName {
  String getClassName();

  PsiClass getPsiClass(PsiElement context);

  String getQualifiedStaticMethodName(String methodName);
}
