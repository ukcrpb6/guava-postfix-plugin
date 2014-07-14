package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatePsiInfo;
import com.intellij.codeInsight.template.postfix.templates.StringBasedPostfixTemplate;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName;
import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils;

/**
 * @author Bob Browning
 */
public abstract class RichStringBasedPostfixTemplate extends StringBasedPostfixTemplate {

  protected RichStringBasedPostfixTemplate(@NotNull String name,
                                           @NotNull String example,
                                           @NotNull PostfixTemplatePsiInfo psiInfo,
                                           @NotNull Condition<PsiElement> typeChecker) {
    super(name, example, psiInfo, typeChecker);
  }

  @Override
  public Template createTemplate(TemplateManager manager, String templateString) {
    Template template = super.createTemplate(manager, templateString);
    template.setValue(Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE, shouldUseStaticImportIfPossible());
    return template;
  }

  /**
   * Whether to override the default settings and use static import if possible.
   */
  protected boolean shouldUseStaticImportIfPossible() {
    return false;
  }

  protected String getStaticMethodPrefix(@NotNull GuavaClassName className,
                                         @NotNull String methodName,
                                         @NotNull PsiElement context) {
    return getStaticMethodPrefix(className.getClassName(), methodName, context);
  }

  protected String getStaticMethodPrefix(@NotNull String className,
                                         @NotNull String methodName,
                                         @NotNull PsiElement context) {
    if (shouldUseStaticImportIfPossible()) {
      return className + "." + methodName;
    } else {
      return GuavaPostfixTemplatesUtils.getStaticMethodPrefix(className, methodName, context);
    }
  }
}
