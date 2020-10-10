import static com.google.common.base.Preconditions.checkPositionIndex;

class Simple {

  public void method(int[] arg) {
      checkPositionIndex(0, arg.length);<caret>
  }
}