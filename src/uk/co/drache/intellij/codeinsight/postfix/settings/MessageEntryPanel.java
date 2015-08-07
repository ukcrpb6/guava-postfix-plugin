package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import com.intellij.openapi.Disposable;
import com.intellij.ui.CommonActionsPanel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ElementProducer;
import com.intellij.util.ui.ListTableModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

/**
 * UI component for checkNotNull messages.
 *
 * @author Bob Browning
 */
public class MessageEntryPanel implements Disposable {

  /**
   * Table column info for a {@link MessageEntry}.
   */
  private static class MessageEntryColumn extends ColumnInfo<MessageEntry, String> {

    public MessageEntryColumn() {
      super("Message suffix");
    }

    @Nullable
    @Override
    public TableCellEditor getEditor(MessageEntry messageEntry) {
      return new MessageEntryCellEditor(messageEntry);
    }

    @Nullable
    @Override
    public String valueOf(MessageEntry messageEntry) {
      return messageEntry.getDisplayText();
    }

    @Override
    public boolean isCellEditable(MessageEntry messageEntry) {
      return messageEntry instanceof MutableMessageEntry;
    }

    @Override
    public void setValue(MessageEntry messageEntry, String value) {
      messageEntry.setText(value);
    }

    /**
     * Cell editor for {@link MessageEntry}.
     */
    private static class MessageEntryCellEditor extends DefaultCellEditor {

      private final InputVerifier inputVerifier;

      public MessageEntryCellEditor(MessageEntry messageEntry) {
        super(new JTextField(messageEntry.getDisplayText()));
        this.inputVerifier = new MessageEntryInputVerifier();
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

    /**
     * Input verifier for {@link MessageEntry}.
     */
    private static class MessageEntryInputVerifier extends InputVerifier {

      @Override
      public boolean verify(JComponent input) {
        return input instanceof JTextField && !Strings.isNullOrEmpty(((JTextField) input).getText());
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
  }

  private final ListTableModel<MessageEntry> model;
  private JPanel panel;
  private JCheckBox checkBox;

  private TableView<MessageEntry> table;

  public MessageEntryPanel() {
    ColumnInfo<MessageEntry, String> columnInfo = new MessageEntryColumn();
    model = new ListTableModel<MessageEntry>(new ColumnInfo[]{columnInfo},
                                             Lists.<MessageEntry>newArrayList(MessageEntries.defaultInstance()), 0);
    table = new TableView<MessageEntry>(model);

  }

  @NotNull
  synchronized JPanel getComponent() {
    checkBox.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        table.setEnabled(checkBox.isSelected());
      }
    });
    panel.add(table, BorderLayout.CENTER);
    ElementProducer<MessageEntry> elementProducer = new ElementProducer<MessageEntry>() {
      @Override
      public MessageEntry createElement() {
        return MessageEntries.newMutableInstance();
      }

      @Override
      public boolean canCreateElement() {
        return true;
      }
    };
    ToolbarDecorator decorator = ToolbarDecorator
        .createDecorator(table, elementProducer)
        .disableUpDownActions();
    panel.add(decorator.createPanel(), BorderLayout.SOUTH);
    return panel;
  }

  @Override
  public void dispose() {
    table = null;
  }

  protected boolean isSelected() {
    return checkBox.isSelected();
  }

  protected void setSelected(boolean state) {
    checkBox.setSelected(state);
    table.setEnabled(state);
  }

  protected void setItems(List<MessageEntry> entries) {
    ArrayList<MessageEntry> answer = Lists.newArrayListWithExpectedSize(entries.size() + 1);
    answer.add(MessageEntries.defaultInstance());
    answer.addAll(
        FluentIterable.from(entries).filter(new Predicate<MessageEntry>() {
          @Override
          public boolean apply(@Nullable MessageEntry messageEntry) {
            return !(messageEntry instanceof MutableMessageEntry && ((MutableMessageEntry) messageEntry).isPending());
          }
        }).toList());
    model.setItems(answer);
  }

  protected List<MessageEntry> getItems() {
    return Lists.<MessageEntry>newArrayList(
        FluentIterable.from(model.getItems())
            .filter(MutableMessageEntry.class)
            .filter(new Predicate<MutableMessageEntry>() {
              @Override
              public boolean apply(MutableMessageEntry messageEntry) {
                return !messageEntry.isPending();
              }
            })
    );
  }
}
