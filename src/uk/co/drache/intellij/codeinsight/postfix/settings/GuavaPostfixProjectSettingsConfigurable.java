package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.swing.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Settings panel for plugin.
 */
public class GuavaPostfixProjectSettingsConfigurable
    implements Configurable,
               Configurable.NoScroll {

  private final GuavaPostfixProjectSettings settings;
  @NotNull
  private final Project project;

  private MessageEntryPanel checkNotNullMessageEntryPanel;

  private JPanel checkNotNullTabPanel;
  private JPanel rootPanel;

  private JCheckBox useStaticImportIfPossible;

  /**
   * Constructor for creating a new configuration panel.
   *
   * @param project the project
   */
  public GuavaPostfixProjectSettingsConfigurable(@NotNull Project project) {
    this.project = checkNotNull(project, "project must not be null");

    settings = GuavaPostfixProjectSettings.getInstance(project);
  }

  @Override
  public void apply() throws ConfigurationException {
    if (!isModified()) {
      return;
    }

    settings.setUseStaticImportIfPossible(useStaticImportIfPossible.isSelected());

    ImmutableList<String> entries = getMessageEntryValuesFrom(checkNotNullMessageEntryPanel.getItems());
    if (checkNotNullMessageEntryPanel.isMessageSuggestionEnabled() && !entries.isEmpty()) {
      settings.setSuggestMessageForCheckNotNull(true);
      settings.setSuggestNoPrefixForCheckNotNull(checkNotNullMessageEntryPanel.isIncludeNoSuffixSuggestion());
      settings.setSuggestionMessagesForCheckNotNull(entries);
    } else {
      settings.setSuggestMessageForCheckNotNull(false);
      settings.setSuggestNoPrefixForCheckNotNull(true);
      settings.setSuggestionMessagesForCheckNotNull(entries);
    }
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    checkNotNullMessageEntryPanel = new MessageEntryPanel(project);
    checkNotNullTabPanel.add(checkNotNullMessageEntryPanel);
    return rootPanel;
  }

  @Override
  public void disposeUIResources() {
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

  @Override
  public boolean isModified() {
    return settings.isUseStaticImportIfPossible() != useStaticImportIfPossible.isSelected()
           || settings.isSuggestMessageForCheckNotNull() != checkNotNullMessageEntryPanel.isMessageSuggestionEnabled()
           || settings.isSuggestNoPrefixForCheckNotNull() != checkNotNullMessageEntryPanel.isIncludeNoSuffixSuggestion()
           || !Iterables.elementsEqual(getMessageEntryValuesFrom(checkNotNullMessageEntryPanel.getItems()),
                                       settings.getSuggestionMessagesForCheckNotNull());
  }

  @Override
  public void reset() {
    setValue(useStaticImportIfPossible, settings.isUseStaticImportIfPossible());

    resetCheckNotNullMessageTable();
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

  private void resetCheckNotNullMessageTable() {
    checkNotNullMessageEntryPanel.setMessageSuggestionEnabled(settings.isSuggestMessageForCheckNotNull());
    checkNotNullMessageEntryPanel.setNoSuffixSuggestionEnabled(settings.isSuggestNoPrefixForCheckNotNull());

    ImmutableList<MessageEntry> messageEntries = FluentIterable.from(settings.getSuggestionMessagesForCheckNotNull())
        .transform(new Function<String, MessageEntry>() {
          @Override
          public MessageEntry apply(String s) {
            return MessageEntry.of(s);
          }
        }).toList();

    checkNotNullMessageEntryPanel.setItems(Lists.newArrayList(messageEntries));
  }

  /**
   * Set a {@link JCheckBox} form component state.
   *
   * @param box   The checkbox
   * @param value The new state
   */
  private static void setValue(JCheckBox box, boolean value) {
    box.setSelected(value);
  }
}
