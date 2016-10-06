package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.intellij.openapi.project.Project;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.TableView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ElementProducer;
import com.intellij.util.ui.ListTableModel;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import static com.google.common.base.Preconditions.checkNotNull;

import static uk.co.drache.intellij.codeinsight.postfix.settings.MessageEntry.PENDING_MESSAGE;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

/**
 * UI component for checkNotNull messages.
 */
public class MessageEntryPanel extends JPanel {
  /**
   * Table column info for a {@link MessageEntry}.
   */
  private static class MessageEntryColumn extends ColumnInfo<MessageEntry, String> {

    private MessageEntryColumn() {
      super("Message suffix");
    }

    @Nullable
    @Override
    public TableCellEditor getEditor(MessageEntry messageEntry) {
      return MessageEntryCellEditor.of(messageEntry);
    }

    @Nullable
    @Override
    public String valueOf(MessageEntry messageEntry) {
      return messageEntry.getText();
    }

    @Override
    public boolean isCellEditable(MessageEntry messageEntry) {
      return true;
    }

    @Override
    public void setValue(MessageEntry messageEntry, String value) {
      messageEntry.setText(value);
    }

    /**
     * Cell editor for {@link MessageEntry}.
     */
    private static class MessageEntryCellEditor extends DefaultCellEditor {
      public static MessageEntryCellEditor of(MessageEntry messageEntry) {
        JTextField textField = new PlaceholderTextField(messageEntry.getText(), "<enter message>");
        return new MessageEntryCellEditor(textField);
      }

      private final InputVerifier inputVerifier;

      MessageEntryCellEditor(JTextField textField) {
        super(textField);
        inputVerifier = new MessageEntryInputVerifier();
        getComponent().setInputVerifier(inputVerifier);
      }

      @Override
      public boolean stopCellEditing() {
        return inputVerifier.shouldYieldFocus(getComponent()) && super.stopCellEditing();
      }

      @Override
      public JTextField getComponent() {
        return (JTextField) super.getComponent();
      }
    }
  }

  /**
   * Creator of new {@link MessageEntry} elements for {@link TableView} model.
   */
  private static class MessageEntryElementProducer implements ElementProducer<MessageEntry> {
    @Override
    public MessageEntry createElement() {
      return MessageEntry.pendingMessage();
    }

    @Override
    public boolean canCreateElement() {
      return true;
    }
  }

  /**
   * Input verifier for {@link MessageEntry}.
   */
  private static class MessageEntryInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
      if (input instanceof JTextField) {
        String value = ((JTextField) input).getText();
        if (!Strings.isNullOrEmpty(value) && !value.equals(PENDING_MESSAGE)) {
          return true;
        }
      }
      return false;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
      boolean valid = verify(input);
      if (!valid) {
        JOptionPane.showMessageDialog(
            null, "Message must not be blank!", "Invalid input", JOptionPane.ERROR_MESSAGE);
      }
      return valid;
    }
  }

  /**
   * A {@link JTextField} that displays a placeholder.
   */
  private static class PlaceholderTextField extends JTextField {
    private final String placeholderText;

    PlaceholderTextField(String text, String placeholderText) {
      super(text);
      this.placeholderText = checkNotNull(placeholderText, "placeholderText must not be null");
    }

    @Override
    protected void paintComponent(Graphics pG) {
      super.paintComponent(pG);

      if (placeholderText.isEmpty() || !getText().isEmpty()) {
        return;
      }

      Graphics2D g = (Graphics2D) pG;
      g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
      g.setColor(getDisabledTextColor());
      g.drawString(placeholderText, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
    }
  }

  private ListTableModel<MessageEntry> model;
  private TableView<MessageEntry> table;

  private JPanel contentPanel;
  private JCheckBox enableMessageSuggestion;
  private JCheckBox includeNoSuffixSuggestion;

  @SuppressWarnings("unused")
  private JPanel tablePanel;

  MessageEntryPanel(final Project project) {
    super(new GridLayoutManager(1, 1));
    enableMessageSuggestion.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        table.setEnabled(enableMessageSuggestion.isSelected());
        includeNoSuffixSuggestion.setEnabled(enableMessageSuggestion.isSelected());
        if (!enableMessageSuggestion.isSelected()) {
          includeNoSuffixSuggestion.setSelected(true);
        } else {
          includeNoSuffixSuggestion.setSelected(
              GuavaPostfixProjectSettings.getInstance(project).isSuggestNoPrefixForCheckNotNull()
          );
        }
      }
    });

    GridConstraints constraints = new GridConstraints();
    constraints.setFill(GridConstraints.FILL_BOTH);
    add(contentPanel, constraints);
  }

  /**
   * The message entries.
   *
   * @return the message entry list
   */
  ImmutableList<MessageEntry> getItems() {
    return FluentIterable.from(model.getItems())
        .filter(MessageEntry.class)
        .filter(new Predicate<MessageEntry>() {
          @Override
          public boolean apply(MessageEntry messageEntry) {
            return !messageEntry.isPending();
          }
        })
        .toList();
  }

  /**
   * Returns true if no suffix is enabled.
   *
   * @return the no suffix enablement state
   */
  boolean isIncludeNoSuffixSuggestion() {
    return includeNoSuffixSuggestion.isSelected();
  }

  /**
   * Returns true if message suggestion is enabled.
   *
   * @return the message suggestion enablement state
   */
  boolean isMessageSuggestionEnabled() {
    return enableMessageSuggestion.isSelected();
  }

  /**
   * Set the table message entries.
   *
   * @param entries the message entries
   */
  void setItems(List<MessageEntry> entries) {
    ArrayList<MessageEntry> answer = Lists.newArrayListWithExpectedSize(entries.size());
    answer.addAll(
        FluentIterable.from(entries).filter(new Predicate<MessageEntry>() {
          @Override
          public boolean apply(@Nullable MessageEntry messageEntry) {
            return !(messageEntry != null && messageEntry.isPending());
          }
        }).toList());
    model.setItems(answer);
  }

  /**
   * Set the message suggestion enabled state.
   *
   * @param state the new state
   */
  void setMessageSuggestionEnabled(boolean state) {
    enableMessageSuggestion.setSelected(state);
    table.setEnabled(state);
  }

  /**
   * Set the no suffix suggestion enabled state.
   *
   * @param state the new state
   */
  void setNoSuffixSuggestionEnabled(boolean state) {
    if (enableMessageSuggestion.isSelected()) {
      includeNoSuffixSuggestion.setSelected(state);
      includeNoSuffixSuggestion.setEnabled(true);
    } else {
      includeNoSuffixSuggestion.setSelected(true);
      includeNoSuffixSuggestion.setEnabled(false);
    }
  }

  private void createUIComponents() {
    ColumnInfo<MessageEntry, String> columnInfo = new MessageEntryColumn();
    model = new ListTableModel<MessageEntry>(new ColumnInfo[]{columnInfo}, Lists.<MessageEntry>newArrayList(), 0);
    table = new TableView<MessageEntry>(model);

    ToolbarDecorator decorator = ToolbarDecorator.createDecorator(table, new MessageEntryElementProducer());
    tablePanel = decorator.createPanel();
  }
}
