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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Project settings for the plugin.
 *
 * @author Bob Browning
 */
@State(
    name = "GuavaPostfixProjectSettings",
    additionalExportFile = "guava_postfix_project_settings",
    storages = {@Storage(StoragePathMacros.WORKSPACE_FILE), @Storage("guava_postfix_settings.xml")})
public class GuavaPostfixProjectSettings
    implements PersistentStateComponent<GuavaPostfixProjectSettings> {

  private boolean useStaticImportIfPossible = true;
  private boolean suggestMessageForCheckNotNull;
  private boolean suggestNoPrefixForCheckNotNull = true;
  private List<String> suggestionMessagesForCheckNotNull = Lists.newArrayList();

  @NotNull
  public static GuavaPostfixProjectSettings getInstance(@NotNull Project project) {
    GuavaPostfixProjectSettings service =
        ServiceManager.getService(project, GuavaPostfixProjectSettings.class);
    return service == null ? new GuavaPostfixProjectSettings() : service;
  }

  @Nullable
  @Override
  public GuavaPostfixProjectSettings getState() {
    return this;
  }

  /** Whether static import should be used if possible. Defaults to true. */
  public boolean isUseStaticImportIfPossible() {
    return useStaticImportIfPossible;
  }

  /**
   * Set whether static import should be used if possible. Defaults to true.
   *
   * @param newValue The new setting
   */
  public void setUseStaticImportIfPossible(boolean newValue) {
    useStaticImportIfPossible = newValue;
  }

  /** Whether message suggestion should be used for nullity check. Defaults to false. */
  public boolean isSuggestMessageForCheckNotNull() {
    return suggestMessageForCheckNotNull;
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
   * Whether no prefix option should be provided.
   *
   * @return true if no prefix option should be provided
   */
  public boolean isSuggestNoPrefixForCheckNotNull() {
    return suggestNoPrefixForCheckNotNull;
  }

  /**
   * Set whether no prefix option should be provided.
   *
   * @param suggestNoPrefixForCheckNotNull true if no prefix option
   */
  public void setSuggestNoPrefixForCheckNotNull(boolean suggestNoPrefixForCheckNotNull) {
    this.suggestNoPrefixForCheckNotNull = suggestNoPrefixForCheckNotNull;
  }

  @Override
  public void loadState(@NotNull GuavaPostfixProjectSettings state) {
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
   * Set message suggestions.
   *
   * @param messages new list of message suggestions
   */
  public void setSuggestionMessagesForCheckNotNull(List<String> messages) {
    checkNotNull(messages, "messages must not be null");
    suggestionMessagesForCheckNotNull = Lists.newArrayList(messages);
  }
}
