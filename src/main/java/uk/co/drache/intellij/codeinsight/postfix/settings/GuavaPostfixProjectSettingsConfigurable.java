package uk.co.drache.intellij.codeinsight.postfix.settings;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.Configurable.NoScroll;
import com.intellij.openapi.project.Project;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Settings panel for plugin. */
public class GuavaPostfixProjectSettingsConfigurable implements Configurable, NoScroll {

  private final GuavaPostfixProjectSettings settings;
  @NotNull private final Project project;

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

  /**
   * Set a {@link JCheckBox} form component state.
   *
   * @param box The checkbox
   * @param value The new state
   */
  private static void setValue(JCheckBox box, boolean value) {
    box.setSelected(value);
  }

  @Override
  public void apply() {
    if (!isModified()) {
      return;
    }

    settings.setUseStaticImportIfPossible(useStaticImportIfPossible.isSelected());

    List<String> entries = getMessageEntryValuesFrom(checkNotNullMessageEntryPanel.getItems());
    settings.setSuggestionMessagesForCheckNotNull(entries);

    if (checkNotNullMessageEntryPanel.isMessageSuggestionEnabled() && !entries.isEmpty()) {
      settings.setSuggestMessageForCheckNotNull(true);
      settings.setSuggestNoPrefixForCheckNotNull(
          checkNotNullMessageEntryPanel.isIncludeNoSuffixSuggestion());
    } else {
      settings.setSuggestMessageForCheckNotNull(false);
      settings.setSuggestNoPrefixForCheckNotNull(true);
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
  public void disposeUIResources() {}

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
        || settings.isSuggestMessageForCheckNotNull()
            != checkNotNullMessageEntryPanel.isMessageSuggestionEnabled()
        || settings.isSuggestNoPrefixForCheckNotNull()
            != checkNotNullMessageEntryPanel.isIncludeNoSuffixSuggestion()
        || !Iterables.elementsEqual(
            getMessageEntryValuesFrom(checkNotNullMessageEntryPanel.getItems()),
            settings.getSuggestionMessagesForCheckNotNull());
  }

  @Override
  public void reset() {
    setValue(useStaticImportIfPossible, settings.isUseStaticImportIfPossible());

    resetCheckNotNullMessageTable();
  }

  private List<String> getMessageEntryValuesFrom(List<MessageEntry> model) {
    return model.stream().map(MessageEntry::getText).collect(Collectors.toList());
  }

  private void resetCheckNotNullMessageTable() {
    checkNotNullMessageEntryPanel.setMessageSuggestionEnabled(
        settings.isSuggestMessageForCheckNotNull());
    checkNotNullMessageEntryPanel.setNoSuffixSuggestionEnabled(
        settings.isSuggestNoPrefixForCheckNotNull());

    List<MessageEntry> messageEntries =
        settings.getSuggestionMessagesForCheckNotNull().stream()
            .map(MessageEntry::of)
            .collect(Collectors.toList());

    checkNotNullMessageEntryPanel.setItems(Lists.newArrayList(messageEntries));
  }
}
