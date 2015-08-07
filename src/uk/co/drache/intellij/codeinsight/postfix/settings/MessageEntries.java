package uk.co.drache.intellij.codeinsight.postfix.settings;

/**
 * @author Bob Browning
 */
public final class MessageEntries {

  public static DefaultMessageEntry defaultInstance() {
    return new DefaultMessageEntry();
  }

  public static MutableMessageEntry newMutableInstance() {
    return new MutableMessageEntry();
  }

  private MessageEntries() {
  }
}
