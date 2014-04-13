package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.showErrorHint;

/**
 * Collection of helper methods for surrounding an expression with guava preconditions.
 *
 * @author Bob Browning
 */
public class GuavaPostfixTemplatesUtils {

  @Nullable
  public static TextRange checkArgumentStatement(@NotNull Project project,
                                                 @NotNull Editor editor,
                                                 @NotNull PsiExpression expr) {
    return surroundWith(project, editor, expr, new JavaCheckArgumentSurrounder());
  }

  @Nullable
  public static TextRange checkNotNullStatement(@NotNull Project project,
                                                @NotNull Editor editor,
                                                @NotNull PsiExpression expr) {
    return surroundWith(project, editor, expr, new JavaCheckNotNullSurrounder());
  }

  @Nullable
  public static TextRange checkStateStatement(@NotNull Project project,
                                              @NotNull Editor editor,
                                              @NotNull PsiExpression expr) {
    return surroundWith(project, editor, expr, new JavaCheckStateSurrounder());
  }

  private static TextRange surroundWith(@NotNull Project project,
                                        @NotNull Editor editor,
                                        @NotNull PsiExpression expr,
                                        @NotNull JavaCheckBaseSurrounder surrounder) {
    PsiElement[] elements = {expr};
    if (surrounder.isApplicable(elements)) {
      return surrounder.surroundElements(project, editor, elements);
    } else {
      showErrorHint(project, editor);
    }
    return null;
  }
}
