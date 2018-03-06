package absyn;

public class DecList {
  public Dec head;
  public DecList tail;
  public DecList( int pos, Dec head, DecList tail) {
    this.pos = pos;
    this.head = head;
    this.tail = tail;
  }
}
