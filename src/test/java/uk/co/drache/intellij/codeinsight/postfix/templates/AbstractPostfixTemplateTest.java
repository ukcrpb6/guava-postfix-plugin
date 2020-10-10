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

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import java.util.ArrayList;
import java.util.List;
import uk.co.drache.intellij.codeinsight.postfix.templates.utils.JvmCodeStubs;

/** Abstract class for {@link PostfixTemplate} test cases. */
public abstract class AbstractPostfixTemplateTest extends LightJavaCodeInsightFixtureTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    @SuppressWarnings("UnstableApiUsage")
    ImmutableList<Class<?>> classes =
        ImmutableList.of(
            ArrayList.class,
            Boolean.class,
            Byte.class,
            CharSequence.class,
            ImmutableList.class,
            ImmutableSet.class,
            Iterable.class,
            Joiner.class,
            List.class,
            Optional.class,
            Preconditions.class,
            Splitter.class,
            String.class,
            TypeToken.class);

    // Add stub classes to mock JVM
    for (Class<?> aClass : classes) {
      myFixture.addClass(JvmCodeStubs.getStubForTopLevelClass(aClass));
    }

    myFixture.addClass(
        "package java.util;\n"
            + "public class Objects {"
            + "public static <T> T requireNonNull(T obj, String message) {}"
            + "}");
    myFixture.addClass("package javax.annotation;\n" + "public @interface Nullable {}");
    myFixture.addClass("package javax.annotation;\n" + "public @interface Nonnull {}");
  }

  protected void doTest() {
    myFixture.configureByFile(getTestName(true) + ".java");
    myFixture.type('\t');
    myFixture.checkResultByFile(getTestName(true) + "_after.java", true);
  }
}
