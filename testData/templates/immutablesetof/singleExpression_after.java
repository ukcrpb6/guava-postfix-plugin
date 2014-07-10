package templates.immutablesetof;

import com.google.common.collect.ImmutableSet;

class Simple {

  public void method(Object arg) {
      ImmutableSet.of(arg)<caret>;
  }
}