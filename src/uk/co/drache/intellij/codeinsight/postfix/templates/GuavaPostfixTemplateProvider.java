package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.util.containers.ContainerUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Bob Browning
 */
public class GuavaPostfixTemplateProvider extends JavaPostfixTemplateProvider {

  @NotNull
  @Override
  public Set<PostfixTemplate> getTemplates() {
    return ContainerUtil.newHashSet(
        new CheckArgumentPostfixTemplate(),
        new CheckElementIndexPostfixTemplate(),
        new CheckNotNullPostfixTemplate(),
        new CheckPositionIndexPostfixTemplate(),
        new CheckPositionIndexesPostfixTemplate(),
        new CheckStatePostfixTemplate(),
        new FluentIterablePostfixTemplate(),
        new ImmutableListCopyOfPostfixTemplate(),
        new ImmutableListOfPostfixTemplate(),
        new ImmutableSetCopyOfPostfixTemplate(),
        new ImmutableSetOfPostfixTemplate(),
        new JoinerPostfixTemplate(),
        new SplitterPostfixTemplate()
    );
  }

}
