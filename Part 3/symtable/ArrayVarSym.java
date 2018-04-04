package symtable;

public class ArrayVarSym extends VarSym {
	public String size;

	public ArrayVarSym(String name, String type, String size, int scope, VarSym child, int memLoc){
		super(name, type, scope, child, memLoc);
		this.size = size;
	}
}