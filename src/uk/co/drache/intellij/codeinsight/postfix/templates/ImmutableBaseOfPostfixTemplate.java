package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.ExpressionPostfixTemplateWithChooser;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiExpression;

import org.jetbrains.annotations.NotNull;

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.surroundExpressionAndShortenStatic;

/**
 * Postfix template for guava immutable .of methods.
 *
 * @author Bob Browning
 */
public abstract class ImmutableBaseOfPostfixTemplate extends ExpressionPostfixTemplateWithChooser {

  protected ImmutableBaseOfPostfixTemplate(@NotNull String name,
                                           @NotNull String description,
                                           @NotNull String example) {
    super(name, description, example);
  }

  protected abstract String getImmutableCollectionImplType();

  @Override
  protected void doIt(@NotNull Editor editor, @NotNull PsiExpression expr) {
    surroundExpressionAndShortenStatic(expr, getImmutableCollectionImplType(), "of", false);
  }

}
