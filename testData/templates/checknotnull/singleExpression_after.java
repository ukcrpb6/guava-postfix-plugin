import java.lang.String;

import static com.google.common.base.Preconditions.checkNotNull;

class Simple {

  public void method(String arg) {
      checkNotNull(arg)<caret>;
  }
}