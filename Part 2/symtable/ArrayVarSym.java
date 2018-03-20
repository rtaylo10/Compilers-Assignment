package symtable;

public class ArrayVarSym extends Sym {
	public String type;
	public String size;

	public ArrayVarSym(String name, String type, String size){
		this.name = name;
		this.type = type;
		this.size = size;
	}
}