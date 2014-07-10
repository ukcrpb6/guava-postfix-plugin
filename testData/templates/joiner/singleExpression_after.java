import com.google.common.base.Joiner;

class Simple {

  public void method(int[] arg) {
      Joiner.on(',').join(arg)<caret>;
  }
}