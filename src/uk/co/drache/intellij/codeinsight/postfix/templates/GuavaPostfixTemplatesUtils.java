package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.generation.surroundWith.JavaExpressionSurrounder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.showErrorHint;

/**
 * @author Bob Browning
 */
public class GuavaPostfixTemplatesUtils {

  @Nullable
  public static TextRange checkNotNullStatement(@NotNull Project project,
                                                @NotNull Editor editor,
                                                @NotNull PsiExpression expr) {
    JavaExpressionSurrounder surrounder = new JavaCheckNotNullSurrounder();
    PsiElement[] elements = {expr};
    if (surrounder.isApplicable(elements)) {
      return surrounder.surroundElements(project, editor, elements);
    } else {
      showErrorHint(project, editor);
    }
    return null;
  }

}
