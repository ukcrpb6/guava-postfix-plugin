/*
 * Copyright (C) 2014 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.drache.intellij.codeinsight.postfix.settings;

import com.google.common.collect.Lists;

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
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Project settings for the plugin.
 *
 * @author Bob Browning
 */
@State(
    name = "GuavaPostfixProjectSettings",
    storages = {
        @Storage(file = "$WORKSPACE_FILE$"),
        @Storage(file = "$PROJECT_CONFIG_DIR$/guava_postfix_settings.xml", scheme = StorageScheme.DIRECTORY_BASED)
    }
)
public class GuavaPostfixProjectSettings
    implements PersistentStateComponent<GuavaPostfixProjectSettings>, ExportableComponent {

  @NotNull
  public static GuavaPostfixProjectSettings getInstance(@NotNull Project project) {
    GuavaPostfixProjectSettings service = ServiceManager.getService(project, GuavaPostfixProjectSettings.class);
    return service == null ? new GuavaPostfixProjectSettings() : service;
  }

  private boolean useStaticImportIfPossible = true;

  private boolean suggestMessageForCheckNotNull;

  private boolean suggestNoPrefixForCheckNotNull = true;

  private List<String> suggestionMessagesForCheckNotNull = Lists.newArrayList();

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

  /**
   * Whether static import should be used if possible. Defaults to true.
   */
  public boolean isUseStaticImportIfPossible() {
    return useStaticImportIfPossible;
  }

  /**
   * Whether message suggestion should be used for nullity check. Defaults to false.
   */
  public boolean isSuggestMessageForCheckNotNull() {
    return suggestMessageForCheckNotNull;
  }

  /**
   * Whether no prefix option should be provided.
   *
   * @return true if no prefix option should be provided
   */
  public boolean isSuggestNoPrefixForCheckNotNull() {
    return suggestNoPrefixForCheckNotNull;
  }

  @Override
  public void loadState(GuavaPostfixProjectSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  /**
   * Message suggestions.
   *
   * @return list of message suggestions
   */
  public List<String> getSuggestionMessagesForCheckNotNull() {
    return suggestionMessagesForCheckNotNull;
  }

  /**
   * Sets whether message suggestion should be used for nullity check. Defaults to false.
   *
   * @param newValue The new setting
   */
  public void setSuggestMessageForCheckNotNull(boolean newValue) {
    suggestMessageForCheckNotNull = newValue;
  }

  /**
   * Set whether no prefix option should be provided.
   *
   * @param suggestNoPrefixForCheckNotNull true if no prefix option
   */
  public void setSuggestNoPrefixForCheckNotNull(boolean suggestNoPrefixForCheckNotNull) {
    this.suggestNoPrefixForCheckNotNull = suggestNoPrefixForCheckNotNull;
  }

  /**
   * Set message sugestions.
   *
   * @param messages new list of message suggestions
   */
  public void setSuggestionMessagesForCheckNotNull(List<String> messages) {
    checkNotNull(messages, "messages must not be null");
    suggestionMessagesForCheckNotNull = Lists.newArrayList(messages);
  }

  /**
   * Set whether static import should be used if possible. Defaults to true.
   *
   * @param newValue The new setting
   */
  public void setUseStaticImportIfPossible(boolean newValue) {
    useStaticImportIfPossible = newValue;
  }
}
