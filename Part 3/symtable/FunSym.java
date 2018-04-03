package symtable;

public class FunSym extends Sym {
	public int scope;
	public String type;
	public FunSym next;

	public FunSym(String name, String type, int scope){
		this.name = name;
		this.scope = scope;
		this.type = type;
		this.next = null;
	}

	public FunSym(String name, String type, int scope, FunSym next){
		this.name = name;
		this.scope = scope;
		this.type = type;
		this.next = next;
	}
}