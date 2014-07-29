package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Settings panel for plugin.
 *
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

  private final GuavaPostfixProjectSettings settings;

  private JCheckBox useStaticImportIfPossible;

  private JPanel panel;

  public GuavaPostfixProjectSettingsPanel(@NotNull Project project) {
    this.settings = GuavaPostfixProjectSettings.getInstance(project);
    reset();
  }

  /**
   * Returns the wired configuration settings panel.
   */
  @Nullable
  public JComponent getPanel() {
    return panel;
  }

  /**
   * Returns true if the settings have been modified.
   */
  public boolean isModified() {
    return settings.isUseStaticImportIfPossible() != useStaticImportIfPossible.isSelected();
  }

  /**
   * Applies the current state to the configured project settings, if modified.
   */
  public void apply() {
    if (!isModified()) {
      return;
    }

    settings.setUseStaticImportIfPossible(useStaticImportIfPossible.isSelected());
  }

  /**
   * Resets the current state to the configured project settings.
   */
  public void reset() {
    setValue(useStaticImportIfPossible, settings.isUseStaticImportIfPossible());
  }

}
