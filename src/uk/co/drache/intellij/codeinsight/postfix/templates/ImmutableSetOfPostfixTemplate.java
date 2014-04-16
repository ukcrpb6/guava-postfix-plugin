package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NonNls;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

/**
 * Postfix template for guava {@link com.google.common.collect.ImmutableSet#of(Object)}.
 *
 * @author Bob Browning
 */
public class ImmutableSetOfPostfixTemplate extends ImmutableBaseOfPostfixTemplate {

  @NonNls
  private static final String DESCRIPTION = "Wraps as an immutable set singleton";

  @NonNls
  private static final String EXAMPLE = "ImmutableSet.of(Object)";

  @NonNls
  private static final String POSTFIX_COMMAND = "immutableSetOf";

  public ImmutableSetOfPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected String getImmutableCollectionImplType() {
    return GuavaClassNames.IMMUTABLE_SET;
  }

}
