package uk.co.drache.intellij.codeinsight.postfix.templates;

import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base test class for PostfixTemplates.
 *
 * @author Bob Browning
 */
public abstract class BasePostfixTemplateTest extends AbstractPostfixTemplateTest {

  private final String dataPath;

  public BasePostfixTemplateTest(@NotNull String dataPath) {
    this.dataPath = checkNotNull(dataPath);
  }

  @Override
  protected String getTestDataPath() {
    return dataPath;
  }

  public void testSingleExpression() {
    doTest();
  }

  public void testVoidExpression() {
    doTest();
  }

}
