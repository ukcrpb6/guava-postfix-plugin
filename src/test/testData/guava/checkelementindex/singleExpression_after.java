import static com.google.common.base.Preconditions.checkElementIndex;

class Simple {

  public void method(int[] arg) {
      checkElementIndex(0, arg.length);<caret>
  }
}