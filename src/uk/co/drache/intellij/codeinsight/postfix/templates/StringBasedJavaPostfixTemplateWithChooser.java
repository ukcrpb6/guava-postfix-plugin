package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateWithChooser;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatePsiInfo;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Bob Browning
 */
public abstract class StringBasedJavaPostfixTemplateWithChooser extends JavaPostfixTemplateWithChooser {

  protected final PostfixTemplatePsiInfo myPsiInfo;
  protected final Condition<PsiElement> myTypeChecker;

  protected StringBasedJavaPostfixTemplateWithChooser(@NotNull String name,
                                                      @NotNull String example,
                                                      @NotNull PostfixTemplatePsiInfo psiInfo,
                                                      @NotNull Condition<PsiElement> typeChecker) {
    super(name, example);
    this.myPsiInfo = psiInfo;
    this.myTypeChecker = typeChecker;
  }

  @Override
  public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
    PsiElement topmostExpression = myPsiInfo.getTopmostExpression(context);
    return topmostExpression != null && myTypeChecker.value(topmostExpression);
  }

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiElement context) {
    PsiElement expr = myPsiInfo.getTopmostExpression(context);
    assert expr != null;
    Project project = context.getProject();
    Document document = editor.getDocument();
    PsiElement elementForRemoving = shouldRemoveParent() ? expr.getParent() : expr;
    document.deleteString(elementForRemoving.getTextRange().getStartOffset(),
                          elementForRemoving.getTextRange().getEndOffset());
    TemplateManager manager = TemplateManager.getInstance(project);

    String templateString = getTemplateString(expr);
    if (templateString == null) {
      PostfixTemplatesUtils.showErrorHint(expr.getProject(), editor);
      return;
    }

    Template template = createTemplate(manager, templateString);

    if (shouldAddExpressionToContext()) {
      template.addVariable("expr", new TextExpression(expr.getText()), false);
    }

    setVariables(template, expr);
    manager.startTemplate(editor, template);
  }

  public Template createTemplate(TemplateManager manager, String templateString) {
    Template template = manager.createTemplate("", "", templateString);
    template.setToReformat(shouldReformat());
    return template;
  }

  public void setVariables(@NotNull Template template, @NotNull PsiElement element) {
  }

  @Nullable
  public abstract String getTemplateString(@NotNull PsiElement element);

  protected boolean shouldAddExpressionToContext() {
    return true;
  }

  protected boolean shouldReformat() {
    return true;
  }

  protected boolean shouldRemoveParent() {
    return true;
  }

}
