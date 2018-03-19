package symTable;

abstract public class VarSym extends Sym {
	public TypeSym type;
	public int scope;

	public VarSym(String name, TypeSym type, int scope){
		this.name = name;
		this.type = type;
		this.scope = scope;
	}
}