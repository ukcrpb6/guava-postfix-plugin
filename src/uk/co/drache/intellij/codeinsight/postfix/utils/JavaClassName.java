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
 * Collection of static strings representing java class names.
 *
 * @author Bob Browning
 */
public enum JavaClassName implements ClassName {

  /**
   * java.util.Objects.
   */
  OBJECTS("java.util.Objects");

  private final String fqClassName;

  JavaClassName(String fqClassName) {
    this.fqClassName = fqClassName;
  }

  @Override
  public String getClassName() {
    return fqClassName;
  }

  @Override
  public PsiClass getPsiClass(PsiElement context) {
    return ClassUtils.findClass(fqClassName, context);
  }

  @Override
  public String getQualifiedStaticMethodName(String methodName) {
    return fqClassName + "." + methodName;
  }
}
