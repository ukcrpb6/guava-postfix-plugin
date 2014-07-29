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

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Configurable project settings for plugin.
 *
 * @author Bob Browning
 */
public class GuavaPostfixProjectSettingsConfigurable implements Configurable {

  private JComponent component;
  private GuavaPostfixProjectSettingsPanel panel;

  public GuavaPostfixProjectSettingsConfigurable(@NotNull Project project) {
    panel = new GuavaPostfixProjectSettingsPanel(project);
    component = panel.getPanel();
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
    return component;
  }

  @Override
  public boolean isModified() {
    return panel.isModified();
  }

  @Override
  public void apply() throws ConfigurationException {
    panel.apply();
  }

  @Override
  public void reset() {
    panel.reset();
  }

  @Override
  public void disposeUIResources() {
    panel = null;
    component = null;
  }
}
