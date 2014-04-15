package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.google.common.collect.FluentIterable;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionStatement;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Bob Browning
 */
public class FluentIterablePostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  @NonNls
  private static final String FLUENT_ITERABLE_CLASS = "com.google.common.collect.FluentIterable";

  @NonNls
  private static final String FLUENT_ITERABLE_FACTORY_METHOD = "from";

  @NonNls
  private static final String POSTFIX_COMMAND = "fluentIterable";

  @NonNls
  private static final String DESCRIPTION = "";

  @NonNls
  private static final String EXAMPLE = "FluentIterable.from(iterable)";

  public FluentIterablePostfixTemplate() {
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

    template.addTextSegment(FLUENT_ITERABLE_CLASS + "." + FLUENT_ITERABLE_FACTORY_METHOD);
    template.addTextSegment("(");
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
        return expr != null && PostfixTemplatesUtils.isIterable(expr.getType());
      }
    };
  }
}
