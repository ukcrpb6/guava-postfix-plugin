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

import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaClassName.IMMUTABLE_SET;
import static uk.co.drache.intellij.codeinsight.postfix.utils.GuavaPostfixTemplatesUtils.IS_NON_PRIMITIVE_ARRAY_OR_ITERABLE_OR_ITERATOR;

/**
 * Postfix template for guava {@code com.google.common.collect.ImmutableSet#copyOf(java.util.Collection)}.
 *
 * @author Bob Browning
 */
public class ImmutableSetCopyOfPostfixTemplate extends ImmutableBasePostfixTemplate {

  public ImmutableSetCopyOfPostfixTemplate() {
    super("immutableSetCopyOf", "ImmutableSet.copyOf(Collection|Array|Iterator|Iterable)", IMMUTABLE_SET, "copyOf",
          IS_NON_PRIMITIVE_ARRAY_OR_ITERABLE_OR_ITERATOR);
  }

}
