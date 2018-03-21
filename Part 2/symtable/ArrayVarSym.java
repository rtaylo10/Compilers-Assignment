package symtable;

public class ArrayVarSym extends VarSym {
	public String size;

	public ArrayVarSym(String name, String type, String size, int scope, VarSym child){
		this.name = name;
		this.type = type;
		this.size = size;
		this.child = child;
	}
}