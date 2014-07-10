package templates.immutablelistcopyof;

import com.google.common.collect.ImmutableList;

class Simple {

  public void method(Object[] arg) {
      ImmutableList.copyOf(arg)<caret>;
  }
}