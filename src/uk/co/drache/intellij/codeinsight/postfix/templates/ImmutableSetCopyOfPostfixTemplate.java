package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NonNls;

/**
 * @author Bob Browning
 */
public class ImmutableSetCopyOfPostfixTemplate extends ImmutableBaseCopyOfPostfixTemplate {

  @NonNls
  private static final String DESCRIPTION = "Wraps as an immutable set containing the given elements";

  @NonNls
  private static final String EXAMPLE = "ImmutableSet.copyOf(Collection|Array|Iterator|Iterable)";

  @NonNls
  private static final String POSTFIX_COMMAND = "immutableSetCopyOf";

  public ImmutableSetCopyOfPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected String getImmutableCollectionImplType() {
    return "com.google.common.collect.ImmutableSet";
  }


}
