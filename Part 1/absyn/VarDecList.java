package absyn;

public class VarDecList extends Absyn {
  public VarDec head;
  public VarDecList tail;
  public VarDecList( int pos, VarDec head, VarDecList tail) {
    this.pos = pos;
    this.head = head;
    this.tail = tail;
  }
}
