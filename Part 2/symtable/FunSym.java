package symtable;

abstract public class FunSym extends Sym {
	public SymTable child;
	public FunSym(String name, SymTable child){
		this.name = name;
		this.child = child;
	}
}