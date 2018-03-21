package symtable;

abstract public class VarSym extends Sym {
	public String type;
	public int scope;
	public VarSym child;
}