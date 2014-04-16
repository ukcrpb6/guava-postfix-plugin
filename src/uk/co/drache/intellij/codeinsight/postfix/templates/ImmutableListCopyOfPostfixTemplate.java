package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NonNls;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

/**
 * Postfix template for guava {@link com.google.common.collect.ImmutableList#copyOf(java.util.Collection)}.
 *
 * @author Bob Browning
 */
public class ImmutableListCopyOfPostfixTemplate extends ImmutableBaseCopyOfPostfixTemplate {

  @NonNls
  private static final String DESCRIPTION = "Wraps as an immutable list containing the given elements, in order";

  @NonNls
  private static final String EXAMPLE = "ImmutableList.copyOf(Collection|Array|Iterator|Iterable)";

  @NonNls
  private static final String POSTFIX_COMMAND = "immutableListCopyOf";

  public ImmutableListCopyOfPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected String getImmutableCollectionImplType() {
    return GuavaClassNames.IMMUTABLE_LIST;
  }

}
