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
package uk.co.drache.intellij.codeinsight.postfix.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.siyeh.ig.psiutils.ClassUtils;

/**
 * Collection of static strings representing guava class names.
 *
 * @author Bob Browning
 */
public enum GuavaClassName {
  /**
   * {@link com.google.common.collect.FluentIterable}.
   */
  FLUENT_ITERABLE("com.google.common.collect.FluentIterable"),

  /**
   * {@link com.google.common.collect.ImmutableList}.
   */
  IMMUTABLE_LIST("com.google.common.collect.ImmutableList"),

  /**
   * {@link com.google.common.collect.ImmutableSet}.
   */
  IMMUTABLE_SET("com.google.common.collect.ImmutableSet"),

  /**
   * {@link com.google.common.collect.Iterables}.
   */
  ITERABLES("com.google.common.collect.Iterables"),

  /**
   * {@link com.google.common.base.Joiner}.
   */
  JOINER("com.google.common.base.Joiner"),

  /**
   * {@link com.google.common.base.Splitter}.
   */
  SPLITTER("com.google.common.base.Splitter"),

  /**
   * {@link com.google.common.base.Preconditions}.
   */
  PRECONDITIONS("com.google.common.base.Preconditions"),

  /**
   * {@link com.google.common.base.Optional}.
   */
  OPTIONAL("com.google.common.base.Optional");

  private final String fqClassName;

  GuavaClassName(String fqClassName) {
    this.fqClassName = fqClassName;
  }

  public String getClassName() {
    return fqClassName;
  }

  public PsiClass getPsiClass(PsiElement context) {
    return ClassUtils.findClass(fqClassName, context);
  }

  public String getQualifiedStaticMethodName(String methodName) {
    return fqClassName + "." + methodName;
  }
}
