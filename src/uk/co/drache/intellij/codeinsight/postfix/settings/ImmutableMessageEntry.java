package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Objects;

import org.jetbrains.annotations.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Bob Browning
 */
public class ImmutableMessageEntry implements MessageEntry {

  private final String text;
  private final String displayText;

  public static ImmutableMessageEntry of(String text) {
    return new ImmutableMessageEntry(text, text);
  }

  public static ImmutableMessageEntry copyOf(MessageEntry original) {
    return new ImmutableMessageEntry(original.getText(), original.getDisplayText());
  }

  ImmutableMessageEntry(String text, String displayText) {
    this.displayText = checkNotNull(displayText);
    this.text = checkNotNull(text);
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public String getDisplayText() {
    return displayText;
  }

  @Override
  public void setText(@Nullable String text) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImmutableMessageEntry that = (ImmutableMessageEntry) o;
    return Objects.equal(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(text);
  }
}
