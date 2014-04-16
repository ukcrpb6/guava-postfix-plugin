package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Trinity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

import static com.intellij.codeInsight.template.Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE;
import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isArray;
import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isIterable;
import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isNumber;
import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.showErrorHint;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isCollection;

/**
 * Postfix template for guava {@link com.google.common.base.Preconditions#checkPositionIndexes(int, int, int)}.
 *
 * @author Bob Browning
 */
public class CheckPositionIndexesPostfixTemplate extends PostfixTemplate {

  @NonNls
  private static final String CHECK_POSITION_INDEXES_METHOD = "checkPositionIndexes";

  @NonNls
  private static final String DESCRIPTION =
      "Checks that [start, end) is a valid sub range of a list, string, or array with the specified size.";

  @NonNls
  private static final String EXAMPLE = "Preconditions.checkPositionIndexes(start, end, size)";

  public CheckPositionIndexesPostfixTemplate() {
    super(CHECK_POSITION_INDEXES_METHOD, DESCRIPTION, EXAMPLE);
  }

  @Override
  public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
    PsiExpression expr = getTopmostExpression(context);
    return expr != null && (isNumber(expr.getType()) ||
                            isArray(expr.getType()) ||
                            isIterable(expr.getType()));
  }

  @Override
  public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
    PsiExpression expr = getTopmostExpression(context);
    if (expr == null) {
      showErrorHint(context.getProject(), editor);
      return;
    }

    Trinity<String, String, String> bounds = calculateBounds(expr);
    if (bounds == null) {
      showErrorHint(context.getProject(), editor);
      return;
    }

    Project project = context.getProject();
    Document document = editor.getDocument();

    document.deleteString(expr.getTextRange().getStartOffset(), expr.getTextRange().getEndOffset());
    TemplateManager manager = TemplateManager.getInstance(project);

    Template template = manager.createTemplate("", "");
    template.setValue(USE_STATIC_IMPORT_IF_POSSIBLE, true);
    template.setToIndent(true);
    template.setToShortenLongNames(true);
    template.setToReformat(true);

    template.addTextSegment(GuavaClassNames.PRECONDITIONS + "." + CHECK_POSITION_INDEXES_METHOD + "(");
    template.addVariable("start", new TextExpression(bounds.first), true);
    template.addTextSegment(", ");
    template.addVariable("end", new TextExpression(bounds.second), true);
    template.addTextSegment(", " + bounds.third + ")");
    template.addEndVariable();

    manager.startTemplate(editor, template);
  }

  @Nullable
  protected static String getExpressionBound(@NotNull PsiExpression expr) {
    PsiType type = expr.getType();
    if (isNumber(type)) {
      return expr.getText();
    } else if (isArray(type)) {
      return expr.getText() + ".length";
    } else if (isCollection(type)) {
      return expr.getText() + ".size()";
    } else if (isIterable(type)) {
      return GuavaClassNames.ITERABLES + ".size(" + expr.getText() + ")";
    }
    return null;
  }

  @Nullable
  protected Trinity<String, String, String> calculateBounds(@NotNull PsiExpression expression) {
    String bound = getExpressionBound(expression);
    return bound != null ? Trinity.create("0", bound, bound) : null;
  }

}
