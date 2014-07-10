import static com.google.common.base.Preconditions.checkState;

class Simple {

  public void method(boolean arg) {
      checkState(arg)<caret>;
  }
}