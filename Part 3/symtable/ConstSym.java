package symtable;

public class ConstSym extends Sym {
	public String value;

	public ConstSym(String name, String value){
		this.name = name;
		this.value = value;
	}
}