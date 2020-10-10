package templates.immutablesetcopyof;

import com.google.common.collect.ImmutableSet;

class Simple {

  public void method(Object[] arg) {
      ImmutableSet.copyOf(arg)<caret>;
  }
}