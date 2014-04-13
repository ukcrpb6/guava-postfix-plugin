package uk.co.drache.intellij.codeinsight.postfix.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStaticStatement;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiJavaFile;

/**
 * @author Bob Browning
 */
public class ImportUtils {

  public static boolean hasExactImportStatic(String qualifierClass, String memberName, PsiElement context) {
    final PsiFile file = context.getContainingFile();
    if (!(file instanceof PsiJavaFile)) {
      return false;
    }
    final PsiJavaFile javaFile = (PsiJavaFile) file;
    final PsiImportList importList = javaFile.getImportList();
    if (importList == null) {
      return false;
    }
    final PsiImportStaticStatement[] importStaticStatements = importList.getImportStaticStatements();
    for (PsiImportStaticStatement importStaticStatement : importStaticStatements) {
      if (importStaticStatement.isOnDemand()) {
        continue;
      }
      final String name = importStaticStatement.getReferenceName();
      if (!memberName.equals(name)) {
        continue;
      }
      final PsiJavaCodeReferenceElement importReference = importStaticStatement.getImportReference();
      if (importReference == null) {
        continue;
      }
      final PsiElement qualifier = importReference.getQualifier();
      if (qualifier == null) {
        continue;
      }
      final String qualifierText = qualifier.getText();
      if (qualifierClass.equals(qualifierText)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Adds a static import.
   *
   * @param qualifierClass The class to import from
   * @param memberName     The class member to import
   * @param context        The context to be imported into
   * @return returns true if the import is statically imported
   */
  public static boolean addStaticImport(String qualifierClass, String memberName, PsiElement context) {
    return hasExactImportStatic(qualifierClass, memberName, context) ||
           com.siyeh.ig.psiutils.ImportUtils.addStaticImport(qualifierClass, memberName, context);
  }
}
