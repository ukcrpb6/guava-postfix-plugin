import com.google.common.base.Optional;

import javax.annotation.Nonnull;

class Simple {

  public void method(@Nullable String arg) {
      Optional.fromNullable(arg)<caret>;
  }
}