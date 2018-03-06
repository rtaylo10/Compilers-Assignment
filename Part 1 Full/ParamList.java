package absyn;

public class ParamListExp extends Exp {
  public String value;
  public String type;
  public int size;
  public ArrayExp( int pos, String value, int size) {
  	/*If size is null, it should throw an error*/
    this.pos = pos;
    this.value = value;
    this.size = size;
  }
}