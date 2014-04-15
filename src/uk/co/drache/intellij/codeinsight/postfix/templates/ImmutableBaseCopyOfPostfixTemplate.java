package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionStatement;

import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.PostfixTemplatesUtils.isIterable;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isCollection;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isIterator;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.isObjectArrayTypeExpression;

/**
 * @author Bob Browning
 */
public abstract class ImmutableBaseCopyOfPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected ImmutableBaseCopyOfPostfixTemplate(@NotNull String name,
                                               @NotNull String description,
                                               @NotNull String example) {
    super(name, description, example);
  }

  protected abstract String getImmutableCollectionImplType();

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

    template.addTextSegment(getImmutableCollectionImplType() + ".copyOf");
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
        return expr != null && (
            isObjectArrayTypeExpression(expr.getType()) ||
            isCollection(expr.getType()) ||
            isIterable(expr.getType()) ||
            isIterator(expr.getType())
        );
      }
    };
  }
}
