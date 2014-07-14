package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatePsiInfo;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatesUtils;
import com.intellij.codeInsight.template.postfix.templates.TypedPostfixTemplate;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import uk.co.drache.intellij.codeinsight.postfix.settings.GuavaPostfixProjectSettings;
import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName;
import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils;

/**
 * @author Bob Browning
 */
public abstract class RichStringBasedPostfixTemplate extends TypedPostfixTemplate {

  protected RichStringBasedPostfixTemplate(@NotNull String name,
                                           @NotNull String example,
                                           @NotNull PostfixTemplatePsiInfo psiInfo,
                                           @NotNull Condition<PsiElement> typeChecker) {
    super(name, example, psiInfo, typeChecker);
  }

  @Override
  public final void expand(@NotNull PsiElement context, @NotNull Editor editor) {
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

    Template template = createTemplate(project, manager, templateString);

    if (shouldAddExpressionToContext()) {
      template.addVariable("expr", new TextExpression(expr.getText()), false);
    }

    setVariables(template, expr);
    manager.startTemplate(editor, template);
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

  public Template createTemplate(Project project, TemplateManager manager, String templateString) {
    Template template = manager.createTemplate("", "", templateString);
    template.setToReformat(shouldReformat());
    template.setValue(Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE, shouldUseStaticImportIfPossible(project));
    return template;
  }

  /**
   * Whether to override the default settings and use static import if possible.
   *
   * @param project The current project
   */
  protected boolean shouldUseStaticImportIfPossible(Project project) {
    return GuavaPostfixProjectSettings.getInstance(project).isUseStaticImportIfPossible();
  }

  protected String getStaticMethodPrefix(@NotNull GuavaClassName className,
                                         @NotNull String methodName,
                                         @NotNull PsiElement context) {
    return getStaticMethodPrefix(className.getClassName(), methodName, context);
  }

  protected String getStaticMethodPrefix(@NotNull String className,
                                         @NotNull String methodName,
                                         @NotNull PsiElement context) {
    if (shouldUseStaticImportIfPossible(context.getProject())) {
      return className + "." + methodName;
    } else {
      return GuavaPostfixTemplatesUtils.getStaticMethodPrefix(className, methodName, context);
    }
  }
}
