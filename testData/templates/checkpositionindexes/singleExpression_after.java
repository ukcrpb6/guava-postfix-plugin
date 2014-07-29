import static com.google.common.base.Preconditions.checkPositionIndexes;

class Simple {

  public void method(int[] arg) {
      checkPositionIndexes(0, arg.length, arg.length);<caret>
  }
}