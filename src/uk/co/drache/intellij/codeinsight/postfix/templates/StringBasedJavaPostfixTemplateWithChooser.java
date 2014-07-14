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

import uk.co.drache.intellij.codeinsight.postfix.settings.GuavaPostfixProjectSettings;
import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName;
import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils;

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
  protected void doIt(@NotNull Editor editor, @NotNull PsiElement context) {
    Project project = context.getProject();
    Document document = editor.getDocument();
    document.deleteString(context.getTextRange().getStartOffset(), context.getTextRange().getEndOffset());
    TemplateManager manager = TemplateManager.getInstance(project);

    String templateString = getTemplateString(context);
    if (templateString == null) {
      PostfixTemplatesUtils.showErrorHint(context.getProject(), editor);
      return;
    }

    Template template = createTemplate(manager, templateString);

    if (shouldUseStaticImportIfPossible(project)) {
      template.setValue(Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE, true);
    }

    if (shouldAddExpressionToContext()) {
      template.addVariable("expr", new TextExpression(context.getText()), false);
    }

    setVariables(template, context);
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

  protected boolean shouldUseStaticImportIfPossible(@NotNull Project project) {
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
