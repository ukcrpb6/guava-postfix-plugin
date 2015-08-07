package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.swing.*;

/**
 * Settings panel for plugin.
 *
 * @author Bob Browning
 */
public class GuavaPostfixProjectSettingsConfigurable
    implements SearchableConfigurable,
               Configurable.NoScroll {

  private final GuavaPostfixProjectSettings settings;

  private JCheckBox useStaticImportIfPossible;

  private JPanel panel;

  private JPanel checkNotNullPanel;

  private MessageEntryPanel checkNotNullMessageEntryPanel;

  public GuavaPostfixProjectSettingsConfigurable(@NotNull final Project project) {
    this.settings = GuavaPostfixProjectSettings.getInstance(project);
  }

  /**
   * Set a {@link JCheckBox} form component state.
   *
   * @param box   The checkbox
   * @param value The new state
   */
  private static void setValue(final JCheckBox box, final boolean value) {
    box.setSelected(value);
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "Guava Postfix Completion";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    checkNotNullMessageEntryPanel = new MessageEntryPanel();
    checkNotNullPanel.add(checkNotNullMessageEntryPanel.getComponent());
    return panel;
  }

  @Override
  public boolean isModified() {
    return settings.isUseStaticImportIfPossible() != useStaticImportIfPossible.isSelected()
           || settings.isSuggestMessageForCheckNotNull() != checkNotNullMessageEntryPanel.isSelected()
           || !Iterables.elementsEqual(getMessageEntryValuesFrom(checkNotNullMessageEntryPanel.getItems()),
                                       settings.getSuggestionMessagesForCheckNotNull());
  }

  @Override
  public void apply() throws ConfigurationException {
    if (!isModified()) {
      return;
    }

    settings.setUseStaticImportIfPossible(useStaticImportIfPossible.isSelected());
    settings.setSuggestMessageForCheckNotNull(checkNotNullMessageEntryPanel.isSelected());

    settings.setSuggestionMessagesForCheckNotNull(getMessageEntryValuesFrom(checkNotNullMessageEntryPanel.getItems()));
  }

  @Override
  public void reset() {
    setValue(useStaticImportIfPossible, settings.isUseStaticImportIfPossible());

    resetCheckNotNullMessageTable();
  }

  private void resetCheckNotNullMessageTable() {
    checkNotNullMessageEntryPanel.setSelected(settings.isSuggestMessageForCheckNotNull());
    ImmutableList<MessageEntry> messageEntries = FluentIterable.from(settings.getSuggestionMessagesForCheckNotNull())
        .transform(new Function<String, MessageEntry>() {
          @Override
          public MessageEntry apply(String s) {
            return new MutableMessageEntry(s);
          }
        }).toList();

    checkNotNullMessageEntryPanel.setItems(Lists.newArrayList(messageEntries));
  }

  @Override
  public void disposeUIResources() {
    checkNotNullMessageEntryPanel.dispose();
    checkNotNullMessageEntryPanel = null;
  }

  private ImmutableList<String> getMessageEntryValuesFrom(List<MessageEntry> model) {
    return FluentIterable.from(model).transform(
        new Function<MessageEntry, String>() {
          @Override
          public String apply(MessageEntry messageEntry) {
            return messageEntry.getText();
          }
        }).toList();
  }

  @NotNull
  @Override
  public String getId() {
    return "postfix.templates.guava";
  }

  @Nullable
  @Override
  public Runnable enableSearch(String s) {
    return null;
  }
}
