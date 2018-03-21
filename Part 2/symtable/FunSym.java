package symtable;

public class FunSym extends Sym {
	public int scope;
	public String type;

	public FunSym(String name, String type, int scope){
		this.name = name;
		this.scope = scope;
		this.type = type;
	}
}