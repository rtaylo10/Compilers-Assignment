package symtable;

public class VarSym extends Sym {
	public String type;
	public int scope;
	public VarSym child;
	public VarSym(String name, String type, int scope, VarSym child){
		this.name = name;
		this.type = type;
		this.scope = scope;
		this.child = child;
	}
}