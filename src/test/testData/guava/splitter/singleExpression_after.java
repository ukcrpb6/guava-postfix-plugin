import com.google.common.base.Splitter;

class Simple {

  public void method(String arg) {
      Splitter.on(',').split(arg)<caret>;
  }
}