package symtable;

public class ArrayVarSym extends VarSym {
	public String size;

	public ArrayVarSym(String name, String type, String size, int scope, VarSym child){
		super(name, type, scope, child);
		this.size = size;
	}
}