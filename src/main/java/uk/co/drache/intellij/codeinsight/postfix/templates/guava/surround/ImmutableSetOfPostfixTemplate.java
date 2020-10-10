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
package uk.co.drache.intellij.codeinsight.postfix.templates.guava.surround;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NON_VOID;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.IMMUTABLE_SET;

/**
 * Postfix template for guava {@code com.google.common.collect.ImmutableSet#of(Object)}.
 *
 * @author Bob Browning
 */
public class ImmutableSetOfPostfixTemplate extends ImmutableBasePostfixTemplate {

  public ImmutableSetOfPostfixTemplate() {
    super("immutableSetOf", "ImmutableSet.of(Object)", IMMUTABLE_SET, "of", IS_NON_VOID);
  }

}
