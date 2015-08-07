package uk.co.drache.intellij.codeinsight.postfix.settings;

/**
 * @author Bob Browning
 */
public class DefaultMessageEntry extends ImmutableMessageEntry {

  public DefaultMessageEntry() {
    super("", "<no suffix>");
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof DefaultMessageEntry;
  }

  @Override
  public int hashCode() {
    return DefaultMessageEntry.class.hashCode();
  }

}
