package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NonNls;

/**
 * @author Bob Browning
 */
public class ImmutableListOfPostfixTemplate extends ImmutableBaseOfPostfixTemplate {

  @NonNls
  private static final String DESCRIPTION = "Wraps as an immutable list singleton";

  @NonNls
  private static final String EXAMPLE = "ImmutableList.of(Object)";

  @NonNls
  private static final String POSTFIX_COMMAND = "immutableListOf";

  public ImmutableListOfPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected String getImmutableCollectionImplType() {
    return "com.google.common.collect.ImmutableList";
  }

}
