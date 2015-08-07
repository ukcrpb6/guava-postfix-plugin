package uk.co.drache.intellij.codeinsight.postfix.settings;

/**
 * @author Bob Browning
 */
public interface MessageEntry {
  String getText();
  String getDisplayText();
  void setText(String text);
}
