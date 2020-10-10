import com.google.common.base.Optional;

import javax.annotation.Nonnull;

class Simple {

  public void method(@Nonnull String arg) {
      Optional.of(arg)<caret>;
  }
}