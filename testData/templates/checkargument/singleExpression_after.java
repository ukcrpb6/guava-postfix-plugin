import static com.google.common.base.Preconditions.checkArgument;

class Simple {

  public void method(boolean arg) {
      checkArgument(arg);<caret>
  }
}