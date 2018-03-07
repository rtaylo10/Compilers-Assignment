package absyn;

public class ExpList extends Absyn{
  public Exp head;
  public ExpList tail;
  public ExpList( int pos, Exp head, ExpList tail) {
    this.pos = pos;
    this.head = head;
    this.tail = tail;
  }
}
