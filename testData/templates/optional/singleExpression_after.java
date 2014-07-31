import com.google.common.base.Optional;

import javax.annotation.Nullable;

class Simple {

  public void method(@Nullable String arg) {
      Optional.fromNullable(arg)<caret>;
  }
}