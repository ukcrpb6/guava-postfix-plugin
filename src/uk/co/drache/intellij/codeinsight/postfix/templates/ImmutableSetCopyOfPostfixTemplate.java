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
package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NonNls;

import uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassNames;

/**
 * Postfix template for guava {@link com.google.common.collect.ImmutableSet#copyOf(java.util.Collection)}.
 *
 * @author Bob Browning
 */
public class ImmutableSetCopyOfPostfixTemplate extends ImmutableBaseCopyOfPostfixTemplate {

  @NonNls
  private static final String DESCRIPTION = "Wraps as an immutable set containing the given elements";

  @NonNls
  private static final String EXAMPLE = "ImmutableSet.copyOf(Collection|Array|Iterator|Iterable)";

  @NonNls
  private static final String POSTFIX_COMMAND = "immutableSetCopyOf";

  public ImmutableSetCopyOfPostfixTemplate() {
    super(POSTFIX_COMMAND, DESCRIPTION, EXAMPLE);
  }

  @Override
  protected String getImmutableCollectionImplType() {
    return GuavaClassNames.IMMUTABLE_SET;
  }


}
