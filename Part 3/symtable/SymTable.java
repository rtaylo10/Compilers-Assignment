package symtable;

import absyn.*;
import assembly.*;
import java.util.*;

public class SymTable{
	final static int MAXSIZE = 19937;
	final static int SHIFT = 4;
	final static int SPACES = 4;

	public static boolean SHOW_TABLE = false;

	public Hashtable<Integer, Sym> vals;
	public int scope = 0;
	public FunSym currentFunction;
	public Assembler a;

	public SymTable(){
		vals = new Hashtable<Integer, Sym>();
	}

	public SymTable(Assembler a){
		vals = new Hashtable<Integer, Sym>();
		this.a = a;
	}

	public void generate(Object o){

	}

	public void delete(String name){
		int key = lookupKey(name);
		vals.remove(key);
	}

	//Removes all values up to (and including) the specified scope for the specified name
	public void rippleDelete(String name, int scope){
		Sym s = lookupVal(name);
		if (s != null){
			if (s instanceof VarSym){
				VarSym template = (VarSym)s;
				VarSym varS = new VarSym(template.name, template.type, template.scope, template.child, template.memLoc);
				while(varS.child != null){
					if (varS.child.scope >= scope){
						varS.child = null;
					}else{
						varS = varS.child;
					}
				}
				if (varS.scope >= scope){
					delete(varS.name);
				}
			}
		}
	}

	//Removes all symbols up to the specified scope for the entire tree
	public void cleanTableToScope(int scope){
		Enumeration<Sym> e;
		for(e = vals.elements(); e.hasMoreElements();){
			rippleDelete(e.nextElement().name, scope);
		}
	}


	//Insert functions
	//Takes the head of an AST and recursively inserts all of the relevant data into our hash table
	public void insert(Absyn o){
		FunSym out = null;
		FunSym in = null;
		if(!valExists("output")){
			out = new FunSym("output", "VOID", 0);
		}
		if(!valExists("input")){
			in = new FunSym("input", "INT", 0);
		}
		insertIntoTable(vals, out);
		insertIntoTable(vals, in);
		createSymFromExp(o);
	}

	//similar to insert, but with a casted value
	private void insertParams(Hashtable<Integer, Sym> table, VarDecList o){
		createSymFromExp(o);
	}

	//similar to above
	private void insertCompound(Hashtable<Integer, Sym> table, CompoundExp o){
		createSymFromExp(o);
	}

	//Checks if the desired symbol is valid for insertion, then inserts it into the deepest point for that value in the hashmap
	private void insertIntoTable(Hashtable<Integer, Sym> table, Sym s){
		if (!valExistsInScope(s.name, this.scope)){
			if (!valExists(s.name)){
				Random rand = new Random(); 
				int keyValue = rand.nextInt(MAXSIZE);
				int finalValue;
				finalValue = this.hash(keyValue);
				table.put(finalValue, s);
			}else{
				Sym temp = lookupVal(s.name);
				if (temp instanceof VarSym){
					if (s instanceof VarSym){
						if (((VarSym)temp).scope != ((VarSym)s).scope){
							((VarSym)temp).child = ((VarSym)s);
						}else{
							printError("Multiple variable declarations of " + s.name + " in the same scope");
						}
					}else{
						printError("Attempting to declare a function " + s.name + " with the same name as an existing variable");
					}
				}else{
					printError("Attempting to declare a variable " + s.name + " with the same name as an existing function");
				}
			}
		}else{
			printError("Multiple variable declarations of " + s.name + " in the same scope");
		}
	}


	//Lookup Functions
	//helper function for lookupKey
	private int lookupInKeyArray(String name, ArrayList<Integer> keyArray){
		for (int i = 0; i < keyArray.size(); i++){
			if (vals.get(keyArray.get(i)).name.equals(name)){
				return keyArray.get(i);
			}
		}
		return -1;
	}

	//returns the key of a symbol of a specified name
	public int lookupKey(String name){
		ArrayList<Integer> symbols = new ArrayList<Integer>(vals.keySet());
		int key = lookupInKeyArray(name, symbols);
		return key;
	}

	//returns the top-level symbol with the specified name
	public Sym lookupVal(String name){
		int key = lookupKey(name);
		if (key != -1){
			Sym s = vals.get(key);
			return s;
		}else{
			return null;
		}
	}

	//returns the deepest symbol with the specified name
	public Sym lookupDeepestVal(String name){
		int key = lookupKey(name);
		if (key != -1){
			Sym s = vals.get(key);
			if (s instanceof VarSym){
				while(((VarSym)s).child != null){
					s = ((VarSym)s).child;
				}
			}
			return s;
		}else{
			return null;
		}
	}

	public String getDeepestType(String name){
		Sym s = lookupDeepestVal(name);
		if (s instanceof VarSym){
			return ((VarSym)s).type;
		}else if (s instanceof FunSym){
			return ((FunSym)s).type;
		}
		return "UND";
	}

	public String getCurrentFunctionName(){
		if (currentFunction != null){
			while(currentFunction.next != null){
				currentFunction = currentFunction.next;
			}
			return currentFunction.name;
		}
		return "";
	}

	public String getCurrentReturnType(){
		if (currentFunction != null){
			while(currentFunction.next != null){
				currentFunction = currentFunction.next;
			}
			return currentFunction.type;
		}
		return "UND";
	}

	public void increaseCurrentFunction(String name){
		FunSym func = (FunSym)lookupDeepestVal(name);
		if (currentFunction == null){
			currentFunction = new FunSym(func.name, func.type, func.scope);
		}else{
			currentFunction.next = new FunSym(func.name, func.type, func.scope);
		}
	}

	public void decreaseCurrentFunction(){
		if (currentFunction != null){
			if (currentFunction.next != null){
				FunSym temp1 = currentFunction;
				while(temp1.next != null){
					currentFunction = currentFunction.next;
					temp1 = currentFunction.next;
				}
				currentFunction.next = null;
			}else{
				currentFunction = null;
			}
		}
	}

	//When given the name of a symbol, returns whether or not it is in the table
	public boolean valExists(String name){
		Sym s = lookupVal(name);
		if (s != null){
			return true;
		}
		return false;
	}

	//When given the name of a symbol, returns whether or not it is in the table at a specified scope (depth)
	public boolean valExistsInScope(String name, int scope){
		Sym s = lookupDeepestVal(name);
		if (s != null){
			if (s instanceof VarSym){
				if (((VarSym)s).scope == scope){
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}


	//Hashing function
	//When given a desired key, returns an actual key that isn't already taken in the hashtable (vals)
	public int hash(int key){
		int temp = 0;
		int i = 0;
		while(vals.get(temp) != null){
			temp = ((temp << SHIFT) + key) % MAXSIZE;
			i++;
		}
		return temp;
	}


	//Printing Functions
	public String getSpaces(){
		String spaces = "";
		for (int i = 0; i < scope; i++){
			for (int j = 0; j < SPACES; j++){
				spaces = spaces + " ";
			}
		}
		return spaces;
	}

	public String getSpaces(Sym s){
		int nSpaces = 0;
		String spaces = "";
		if (s instanceof VarSym){
			nSpaces = ((VarSym)s).scope;
		}else if (s instanceof FunSym){
			nSpaces = ((FunSym)s).scope;
		}else{
			nSpaces = 0;
		}
		for (int i = 0; i < nSpaces; i++){
			for (int j = 0; j < SPACES; j++){
				spaces = spaces + " ";
			}
		}
		return spaces;
	}

	public String getSymType(Sym s){
		if(s instanceof ArrayVarSym){
			return "ArrayVariable";
		}else if(s instanceof ConstSym){
			return "Constant";
		}else if(s instanceof FunSym){
			return "Function";
		}else if(s instanceof SimpleVarSym){
			return "SimpleVariable";
		}else if(s instanceof TypeSym){
			return "Type";
		}else{
			return "UND";
		}
	}

	public String getVarType(Sym s){
		if(s instanceof ArrayVarSym){
			return ((ArrayVarSym)s).type;
		}else if(s instanceof FunSym){
			return ((FunSym)s).type;
		}else if(s instanceof SimpleVarSym){
			return ((SimpleVarSym)s).type;
		}else{
			return "UND";
		}
	}

	public void printTable(){
		Enumeration<Sym> e;
		for(e = vals.elements(); e.hasMoreElements();){
			String name = e.nextElement().name;
			Sym temp = lookupDeepestVal(name);
			int key = lookupKey(name);
			String indentation = getSpaces(temp);
			String symType = getSymType(temp);
			String varType = getVarType(temp);
			System.out.println(indentation + /*key + " - " +*/ varType + " " + symType + ": " + name);
		}
	}

	public void printError(String e){
		System.err.println("Error - " + e);
	}

	public void printError(String e, int pos){
		System.err.println("Error: Line " + pos + " - " + e);
	}

	public void startBlock(){
		if (SHOW_TABLE == true){
			printTable();
		}
		this.scope = this.scope + 1;
		if (SHOW_TABLE == true){
			System.out.println("Entering Block");
		}
	}

	public void startBlock(String blockName){
		if (SHOW_TABLE == true){
			printTable();
		}
		this.scope = this.scope + 1;
		if (SHOW_TABLE == true){
			System.out.println("Entering Block " + blockName);
		}
	}

	public void endBlock(){
		if (SHOW_TABLE == true){
			printTable();
			System.out.println("Exiting Block");
		}
		cleanTableToScope(this.scope);
		this.scope = this.scope - 1;
	}

	public void endBlock(String blockName){
		if (SHOW_TABLE == true){
			printTable();
			System.out.println("Exiting Block " + blockName + " at scope " + this.scope);
		}
		cleanTableToScope(this.scope);
		this.scope = this.scope - 1;
	}


	//Recursive functions to traverse the AST and add its values to the hash table
	public void createSymFromExp( Absyn tree ) {
		if( tree instanceof Var )
			createSymFromExp( (Var)tree );
		else if( tree instanceof Exp )
			createSymFromExp( (Exp)tree );
		else if( tree instanceof Dec )
			createSymFromExp( (Dec)tree );
		else if( tree instanceof DecList )
			createSymFromExp( (DecList)tree );
		else if( tree instanceof VarDecList )
			createSymFromExp( (VarDecList)tree );
		else if( tree instanceof ExpList )
			createSymFromExp( (ExpList)tree );
		else {
			printError( "Illegal Absyn type in AST", tree.pos);
		}
	}

	public void createSymFromExp( ExpList tree ) {
		while( tree != null ) {
			createSymFromExp( tree.head );
			tree = tree.tail;
		} 
	}

	public void createSymFromExp( DecList tree ) {
		while( tree != null ) {
			createSymFromExp( tree.head );
			tree = tree.tail;
		} 
	}

	public void createSymFromExp( VarDecList tree ) {
		while( tree != null ) {
			createSymFromExp( tree.head );
			tree = tree.tail;
		} 
	}

	public String createSymFromExp( Var tree ) {
		if( tree instanceof IndexVar )
			return createSymFromExp( (IndexVar)tree );
		else if( tree instanceof SimpleVar )
			return createSymFromExp( (SimpleVar)tree );
		else {
			printError( "Illegal Var type in AST", tree.pos);
			return "ERR";
		}
	}

	public String createSymFromExp( Exp tree ) {
		if( tree instanceof NilExp )
			return createSymFromExp( (NilExp)tree );
		else if( tree instanceof VarExp )
			return createSymFromExp( (VarExp)tree );
		else if( tree instanceof IntExp )
			return createSymFromExp( (IntExp)tree );
		else if( tree instanceof CallExp )
			return createSymFromExp( (CallExp)tree );
		else if( tree instanceof OpExp )
			return createSymFromExp( (OpExp)tree );
		else if( tree instanceof AssignExp )
			return createSymFromExp( (AssignExp)tree );
		else if( tree instanceof IfExp )
			return createSymFromExp( (IfExp)tree );
		else if( tree instanceof WhileExp )
			return createSymFromExp( (WhileExp)tree );
		else if( tree instanceof ReturnExp )
			return createSymFromExp( (ReturnExp)tree );
		else if( tree instanceof CompoundExp )
			return createSymFromExp( (CompoundExp)tree );
		else {
			printError( "Illegal Exp type in AST", tree.pos);
			return "ERR";
		}
	}

	public void createSymFromExp( Dec tree ) {
		if( tree instanceof FunctionDec ){
			createSymFromExp( (FunctionDec)tree );
		}else if( tree instanceof VarDec ){
			createSymFromExp( (VarDec)tree );
		}else {
			printError( "Illegal Dec type in AST", tree.pos);
		}
	}

	public void createSymFromExp( VarDec tree ) {
		if( tree instanceof SimpleDec ){
			createSymFromExp( (SimpleDec)tree );
		}else if( tree instanceof ArrayDec ){
			createSymFromExp( (ArrayDec)tree );
		}else {
			
			printError( "Illegal VarDec type in AST", tree.pos);
		}
	}

	public String createSymFromExp( NameTy tree ) {
		if (tree.typ == NameTy.INT){
			return "INT"; 
		}else if (tree.typ == NameTy.VOID){
			return "VOID";
		}else{
			printError( "Illegal NameTy type in AST", tree.pos);
			return "ERR";
		}
	}

  /* Variables */
	public String createSymFromExp( SimpleVar tree ) {
		String type = getDeepestType(tree.name);
		if (type.equals("UND")){
			printError("Variable " + tree.name + " is not defined", tree.pos);
		}

		return type;
	}

	public String createSymFromExp( IndexVar tree) {
		String indexType = createSymFromExp(tree.index); 
		String type = getDeepestType(tree.name);
		if (indexType.equals("INT")){
			//Good
		}else{
			printError("Array index call uses a type other than int", tree.pos);
		}
		if (type.equals("UND")){
			printError("Variable " + tree.name + " is not defined", tree.pos);
		}
		return type;
	}

  	/* Expressions */
	public String createSymFromExp( NilExp tree) {
		return "NULL";
	}
	public String createSymFromExp( VarExp tree) {
		if (tree.variable == (null)) {
			printError( "VarExp does not have a valid variable type", tree.pos);
			return "NULL";
		} else {
			return createSymFromExp(tree.variable);
		}
	}
	public String createSymFromExp( IntExp tree) {
		return "INT";
	}
	public String createSymFromExp( CallExp tree) {
		createSymFromExp(tree.args);
		String type = getDeepestType(tree.func);
		if (type.equals("UND")){
			printError( "Function not defined", tree.pos);
		}
		return type;
	}
	public String createSymFromExp( OpExp tree) {
		String type1 = createSymFromExp( tree.left );
		String type2 = createSymFromExp( tree.right ); 
		if (type1.equals(type2)){
			return type1;
		}else{
			printError(type1 + " cannot be converted to " + type2, tree.pos);
			return "ERR";
		}
	}
	public String createSymFromExp( AssignExp tree) {
		String type1 = createSymFromExp(tree.lhs);
		String type2 = createSymFromExp(tree.rhs);
		if (type1.equals(type2)){
			return type1;
		}else{
			printError(type1 + " cannot be converted to " + type2, tree.pos);
			return "ERR";
		}
	}
	public String createSymFromExp( IfExp tree) {
		String type = createSymFromExp(tree.test);
		if (!type.equals("INT")){
			printError("Test condition of If statement must be an int", tree.pos);
		}

		startBlock("If");
		createSymFromExp(tree.then);
		endBlock("If");

		if (tree.els != null){
			if (!(tree.els instanceof NilExp)){
				startBlock("Else");
				createSymFromExp(tree.els);
				endBlock("Else");
			}
		}

		return "VOID";
	}
	public String createSymFromExp( WhileExp tree) {
		String type = createSymFromExp(tree.test);
		if (!type.equals("INT")){
			printError("Test condition of While statement must be an int", tree.pos);
		}

		startBlock("While");
		createSymFromExp(tree.body);
		endBlock("While"); 

		return "VOID";
	}
	public String createSymFromExp( ReturnExp tree) {
		String type = createSymFromExp(tree.exp);
		if (type.equals(getCurrentReturnType())){
			return type;
		}
		printError("Variable of type " + type + " cannot be returned by function " + getCurrentFunctionName(), tree.pos);
		return type;
	}
	public String createSymFromExp( CompoundExp tree) {
		createSymFromExp(tree.decs);
		createSymFromExp(tree.exps);

		return "VOID";
	}

	/* Declarations */
	public void createSymFromExp( FunctionDec tree) {
		FunSym s = new FunSym(tree.func, createSymFromExp(tree.result), this.scope);
		insertIntoTable(vals, s);
		
		startBlock("Function: " + tree.func);
		increaseCurrentFunction(tree.func);

		insertParams(vals, tree.params);
		insertCompound(vals, tree.body);

		endBlock("Function: " + tree.func);
		decreaseCurrentFunction();
	}
	public void createSymFromExp( SimpleDec tree) {
		Declaration d = new Declaration(tree.name);
		SimpleVarSym s = new SimpleVarSym(tree.name, createSymFromExp(tree.typ), this.scope, null, d.memLoc);
		
		insertIntoTable(vals, s);
		a.addDeclaration(d);
	}
	public void createSymFromExp( ArrayDec tree) {
		ArrayVarSym s;
		Declaration d = new Declaration(tree.name);
		if (tree.size != null){
			s = new ArrayVarSym(tree.name, createSymFromExp(tree.typ), tree.size.value, this.scope, null, d.memLoc);
		}else{
			s = new ArrayVarSym(tree.name, createSymFromExp(tree.typ), "", this.scope, null, d.memLoc);
		}

		insertIntoTable(vals, s);
		a.addDeclaration(d);
	}
}