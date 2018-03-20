package symtable;

public class SimpleVarSym extends Sym {
	public String type;

	public SimpleVarSym(String name, String type){
		this.name = name;
		this.type = type;
	}
}