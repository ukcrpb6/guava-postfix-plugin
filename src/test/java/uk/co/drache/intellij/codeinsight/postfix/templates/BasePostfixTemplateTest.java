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

import org.jetbrains.annotations.NotNull;

/**
 * Base test class for PostfixTemplates.
 *
 * @author Bob Browning
 */
public abstract class BasePostfixTemplateTest extends AbstractPostfixTemplateTest {

  private final String testName;

  public BasePostfixTemplateTest(@NotNull String testName) {
    this.testName = testName;
  }

  /**
   * @return path to test data file directory relative to working directory in the run configuration
   *     for this test.
   */
  @Override
  protected String getTestDataPath() {
    return "src/test/testData/" + testName;
  }

  public void testSingleExpression() {
    doTest();
  }

  public void testVoidExpression() {
    doTest();
  }
}
