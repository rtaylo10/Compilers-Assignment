package absyn;

public class ExpList {
  public Exp head;
  public ExpList tail;
  public ExpList( int pos, Exp head, ExpList tail) {
    this.pos = pos;
    this.value = head;
    this.size = tail;
  }
}
