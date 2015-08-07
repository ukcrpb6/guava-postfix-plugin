package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Objects;

import org.jetbrains.annotations.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Mutable implementation of {@link MessageEntry}.
 *
 * @author Bob Browning
 */
public class MutableMessageEntry implements MessageEntry {

  private static final String PENDING_MESSAGE = "<insert message>";

  private String text;

  public MutableMessageEntry() {
    this(PENDING_MESSAGE);
  }

  public MutableMessageEntry(String text) {
    this.text = checkNotNull(text);
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public String getDisplayText() {
    return getText();
  }

  @Override
  public void setText(String text) {
    this.text = checkNotNull(text);
  }

  public boolean isPending() {
    return PENDING_MESSAGE.equalsIgnoreCase(text);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MutableMessageEntry that = (MutableMessageEntry) o;
    return Objects.equal(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(text);
  }
}
