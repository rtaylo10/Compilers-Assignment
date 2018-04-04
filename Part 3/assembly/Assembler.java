package assembly;

public class Assembler {

	/*Stores the symbol table created from part 2 in here, that way you can work on this part without worrying about the symbol table functions getting in the way*/
	public SymTable symbols;
	public Assembler(SymTable s){
		symbols = s;
	}

    public void output() {
        System.out.println("output statement");
    }
    public void input() {
        System.out.println("input statement");
    }
    /*Previously in finale.java*/
    public void end() {
        System.out.println("This is the end of the assembly code");
    }
    /*Previously in prelude.java*/
    public void start() {
        System.out.println("This is the start of the assembly code");
    }

    public void addAssign(){

    }

    public String getDecType(Declaration d){
    	/*Will return either INT or VOID*/
    	return getDeepestType(d.name);
    }

    public boolean decIsArray(Declaration d){
    	/*A declaration's corresponding symbol type is either "ArrayVariable", "Constant", "Function", "SimpleVariable", "Type" or "UND" (for undefined)*/
    	/*This function checks if it is an array, but you can modify it to look for any of the other values.*/
    	/*Also, I'm pretty sure that only ArrayVariable, Function and SimpleVariable are actually put into the symbol table*/
    	if (getSymType(lookupDeepestVal(d.name)).equals("ArrayVariable")){
    		return true;
    	}else{
    		return false;
    	}
    }

    public boolean decIsSimpleVar(Declaration d){
    	/*This function checks if it is an non-array variable*/
    	if (getSymType(lookupDeepestVal(d.name)).equals("SimpleVariable")){
    		return true;
    	}else{
    		return false;
    	}
    	return ;
    }

    public void addDeclaration(Declaration d){
    	/*Declaration has been created, now do what you want with it (At the moment this is called every time a VarDec has been found in the tree)*/


    }
    public void addFunction(){

    }
    public void addIf(IfExp e){

    }
    public void addWhile(){

    }
}
