package uk.co.drache.intellij.codeinsight.postfix.templates;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bob Browning
 */
public class GuavaPostfixTemplateProvider extends JavaPostfixTemplateProvider {

  @NotNull
  @Override
  public Set<PostfixTemplate> getTemplates() {
    Set<PostfixTemplate> templates = new HashSet<PostfixTemplate>(12);
    templates.add(new CheckArgumentPostfixTemplate());
    templates.add(new CheckElementIndexPostfixTemplate());
    templates.add(new CheckNotNullPostfixTemplate());
    templates.add(new CheckPositionIndexPostfixTemplate());
    templates.add(new CheckPositionIndexesPostfixTemplate());
    templates.add(new CheckStatePostfixTemplate());
    templates.add(new FluentIterablePostfixTemplate());
    templates.add(new ImmutableListCopyOfPostfixTemplate());
    templates.add(new ImmutableListOfPostfixTemplate());
    templates.add(new ImmutableSetCopyOfPostfixTemplate());
    templates.add(new ImmutableSetOfPostfixTemplate());
    templates.add(new JoinerPostfixTemplate());
    templates.add(new SplitterPostfixTemplate());
    return Collections.unmodifiableSet(templates);
  }

}
