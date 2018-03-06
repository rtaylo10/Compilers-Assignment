package absyn;

public class VarDecList{
  public VarDec head;
  public VarDecList tail;
  public VarDecList( int pos, VarDec head, VarDecList tail) {
    this.pos = pos;
    this.head = head;
    this.tail = tail;
  }
}
