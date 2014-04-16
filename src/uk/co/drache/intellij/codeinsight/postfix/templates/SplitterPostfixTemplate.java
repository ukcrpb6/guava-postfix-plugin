package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;
import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils;

/**
 * Postfix template for guava {@link com.google.common.base.Splitter}.
 *
 * @author Bob Browning
 */
public class SplitterPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String DESCRIPTION = "Extracts non-overlapping substrings from an input string, "
                                            + "typically by recognizing appearances of a separator sequence";

  @NonNls
  private static final String EXAMPLE = "Splitter.on(',').split(sequence)";

  @NonNls
  private static final String POSTFIX_COMMAND = "split";

  public SplitterPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    Project project = expr.getProject();
    Document document = editor.getDocument();

    document.deleteString(expr.getTextRange().getStartOffset(), expr.getTextRange().getEndOffset());
    TemplateManager manager = TemplateManager.getInstance(project);

    Template template = manager.createTemplate("", "");
    template.setToIndent(true);
    template.setToShortenLongNames(true);
    template.setToReformat(true);

    template.addTextSegment(GuavaClassNames.SPLITTER + ".on(");
    template.addVariable("separator", new TextExpression("','"), true);
    template.addTextSegment(").split(");
    template.addTextSegment(expr.getText());
    template.addTextSegment(")");
    template.addEndVariable();

    manager.startTemplate(editor, template);
  }

  @NotNull
  @Override
  protected Condition<PsiExpression> getTypeCondition() {
    return new Condition<PsiExpression>() {
      @Override
      public boolean value(PsiExpression expr) {
        return expr != null && GuavaPostfixTemplatesUtils.isCharSequence(expr.getType());
      }
    };
  }

}
