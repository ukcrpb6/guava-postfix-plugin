package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Bob Browning
 */
public class GuavaPostfixProjectSettingsPanel {

  /**
   * Set a {@link JCheckBox} form component state.
   *
   * @param box   The checkbox
   * @param value The new state
   */
  private static void setValue(final JCheckBox box, final boolean value) {
    box.setSelected(value);
  }

  private final Project project;
  private final GuavaPostfixProjectSettings settings;

  private JCheckBox useStaticImportIfPossible;

  private JPanel panel;

  public GuavaPostfixProjectSettingsPanel(@NotNull Project project) {
    this.project = project;
    this.settings = GuavaPostfixProjectSettings.getInstance(project);
    reset();
  }

  @Nullable
  public JComponent getPanel() {
    return panel;
  }

  public boolean isModified() {
    return settings.isUseStaticImportIfPossible() != useStaticImportIfPossible.isSelected();
  }

  public void apply() {
    if (!isModified()) {
      return;
    }

    settings.setUseStaticImportIfPossible(useStaticImportIfPossible.isSelected());
  }

  public void reset() {
    setValue(useStaticImportIfPossible, settings.isUseStaticImportIfPossible());
  }

}
