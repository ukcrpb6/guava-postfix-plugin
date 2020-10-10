package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Objects;

import org.jetbrains.annotations.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A checkNotNull precondition message prefix.
 */
class MessageEntry {

  static final String PENDING_MESSAGE = "";

  /**
   * Creates a new message prefix.
   *
   * @param message the message prefix
   * @return the message entry
   */
  public static MessageEntry of(String message) {
    return new MessageEntry(message);
  }

  /**
   * Creates a new pending message prefix.
   *
   * @return the message entry
   */
  public static MessageEntry pendingMessage() {
    return new MessageEntry(PENDING_MESSAGE);
  }

  private String text;

  private MessageEntry(String text) {
    this.text = checkNotNull(text, "text must not be null");
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageEntry that = (MessageEntry) o;
    return Objects.equal(text, that.text);
  }

  public String getDisplayText() {
    return text;
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(text);
  }

  public boolean isPending() {
    return PENDING_MESSAGE.equalsIgnoreCase(text.trim());
  }

  public void setText(String text) {
    this.text = checkNotNull(text);
  }
}
