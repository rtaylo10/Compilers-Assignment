package absyn;

public class IfExp extends Exp {
  public Exp test;
  public Exp then;
  public Exp els;
  public IfExp( int pos, Exp test, Exp then, Exp els) {
    this.pos = pos;
    this.test = test;
    this.then = then;
    this.els = els;
  }
}
