package symtable;

public class SimpleVarSym extends VarSym {
	public SimpleVarSym(String name, String type, int scope, VarSym child, int memLoc){
		super(name, type, scope, child, memLoc);
	}
}