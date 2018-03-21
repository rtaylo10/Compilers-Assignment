package symtable;

public class SimpleVarSym extends VarSym {
	public SimpleVarSym(String name, String type, int scope, VarSym child){
		this.name = name;
		this.type = type;
		this.scope = scope;
		this.child = child;
	}
}