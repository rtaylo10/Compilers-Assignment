package absyn;

public class AssignExp extends Exp {
  public Var lhs;
  public Exp rhs;
  public AssignExp( int pos, Var lhs, Exp rhs) {
    this.pos = pos;
    this.value = lhs;
    this.size = rhs;
  }
}
