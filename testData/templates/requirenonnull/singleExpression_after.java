import java.lang.String;

import static java.util.Objects.requireNonNull;

class Simple {

  public void method(String arg) {
      requireNonNull(arg)<caret>;
  }
}
