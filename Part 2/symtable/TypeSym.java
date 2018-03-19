package symTable;

abstract public class TypeSym extends Sym {
	public String typeName;

	public TypeSym(String name, String typeName){
		this.name = name;
		this.typeName = typeName;
	}
}