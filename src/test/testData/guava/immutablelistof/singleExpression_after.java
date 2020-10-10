package templates.immutablelistof;

import com.google.common.collect.ImmutableList;

class Simple {

  public void method(Object arg) {
      ImmutableList.of(arg)<caret>;
  }
}