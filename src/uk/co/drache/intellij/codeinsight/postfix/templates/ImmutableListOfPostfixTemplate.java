package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NonNls;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

/**
 * Postfix template for guava {@link com.google.common.collect.ImmutableList#of(Object)}.
 *
 * @author Bob Browning
 */
public class ImmutableListOfPostfixTemplate extends ImmutableBaseOfPostfixTemplate {

  @NonNls
  private static final String DESCRIPTION = "Wraps as an immutable list singleton";

  @NonNls
  private static final String EXAMPLE = "ImmutableList.of(element)";

  @NonNls
  private static final String POSTFIX_COMMAND = "immutableListOf";

  public ImmutableListOfPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected String getImmutableCollectionImplType() {
    return GuavaClassNames.IMMUTABLE_LIST;
  }

}
