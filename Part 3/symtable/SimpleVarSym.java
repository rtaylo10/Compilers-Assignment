package symtable;

public class SimpleVarSym extends VarSym {
	public SimpleVarSym(String name, String type, int scope, VarSym child){
		super(name, type, scope, child);
	}
}