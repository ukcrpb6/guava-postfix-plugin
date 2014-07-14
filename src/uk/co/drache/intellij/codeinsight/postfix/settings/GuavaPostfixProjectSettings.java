package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.ExportableComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author Bob Browning
 */
@State(
    name = "ScalaProjectSettings",
    storages = {
        @Storage(file = "$WORKSPACE_FILE$"),
        @Storage(file = "$PROJECT_CONFIG_DIR$/guava_postfix_settings.xml", scheme = StorageScheme.DIRECTORY_BASED)
    }
)
public class GuavaPostfixProjectSettings
    implements PersistentStateComponent<GuavaPostfixProjectSettings>, ExportableComponent {

  public static GuavaPostfixProjectSettings getInstance(@NotNull Project project) {
    GuavaPostfixProjectSettings service = ServiceManager.getService(project, GuavaPostfixProjectSettings.class);
    return service == null ? new GuavaPostfixProjectSettings() : service;
  }

  private boolean useStaticImportIfPossible = true;

  @NotNull
  @Override
  public File[] getExportFiles() {
    return new File[]{
        PathManager.getOptionsFile("guava_postfix_project_settings")
    };
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Guava Postfix Settings";
  }

  @Nullable
  @Override
  public GuavaPostfixProjectSettings getState() {
    return this;
  }

  @Override
  public void loadState(GuavaPostfixProjectSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  /**
   * Whether static import should be used if possible. Defaults to true.
   */
  public boolean isUseStaticImportIfPossible() {
    return useStaticImportIfPossible;
  }

  /**
   * Set whether static import should be used if possible. Defaults to true.
   *
   * @param newValue The new setting
   */
  public void setUseStaticImportIfPossible(boolean newValue) {
    this.useStaticImportIfPossible = newValue;
  }

}
