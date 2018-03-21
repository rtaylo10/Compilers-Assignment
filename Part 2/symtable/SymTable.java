package symtable;

import absyn.*;
import java.util.*;

public class SymTable{
	final static int MAXSIZE = 19937;
	final static int SHIFT = 4;
	final static int SPACES = 4;

	public static boolean SHOW_TABLE = false;

	public Hashtable<Integer, Sym> vals;
	public int scope = 0;

	public SymTable(){
		vals = new Hashtable<Integer, Sym>();
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
		if (s instanceof VarSym){
			VarSym varS = (VarSym)s;
			while(varS.child != null){
				if (varS.child.scope >= scope){
					varS.child = null;
				}
			}
			if (varS.scope >= scope){
				delete(varS.name);
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
							printError("adding two variables of the same name in the same scope");
						}
					}else{
						printError("adding a non-variable with the same name as a variable in a higher scope");
					}
				}else{
					printError("adding a variable with the same name as a non-variable");
				}
			}
		}else{
			printError("adding two variables of the same name in the same scope");
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
		return "";
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
			printError("Error - Invalid symbol type");
			return "";
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
			return "";
		}
	}

	public void printTable(){
		Enumeration<Sym> e;
		for(e = vals.elements(); e.hasMoreElements();){
			Sym temp = lookupDeepestVal(e.nextElement().name);
			String indentation = getSpaces(temp);
			String symType = getSymType(temp);
			String varType = getVarType(temp);
			System.out.println(indentation + varType + " " + symType + ": " + temp.name);
		}
	}

	public void printError(String e){
		System.err.println(e);
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
			System.out.println("Exiting Block " + blockName);
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
			System.err.println( "Illegal expression at line " + tree.pos  );
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
			System.err.println( "Illegal expression at line " + tree.pos  );
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
			System.err.println( "Illegal expression at line " + tree.pos  );
			return "ERR";
		}
	}

	public void createSymFromExp( Dec tree ) {
		if( tree instanceof FunctionDec ){
			createSymFromExp( (FunctionDec)tree );
		}else if( tree instanceof VarDec ){
			createSymFromExp( (VarDec)tree );
		}else {
			System.err.println( "Illegal expression at line " + tree.pos  );
		}
	}

	public void createSymFromExp( VarDec tree ) {
		if( tree instanceof SimpleDec ){
			createSymFromExp( (SimpleDec)tree );
		}else if( tree instanceof ArrayDec ){
			createSymFromExp( (ArrayDec)tree );
		}else {
			
			System.err.println( "Illegal expression at line " + tree.pos  );
		}
	}

	public String createSymFromExp( NameTy tree ) {
		if (tree.typ == NameTy.INT){
			return "INT"; 
		}else if (tree.typ == NameTy.VOID){
			return "VOID";
		}else{
			printError( "Illegal type name at position " + tree.pos  );
			return "ERR";
		}
	}

  /* Variables */
	public String createSymFromExp( SimpleVar tree ) {
		return getDeepestType(tree.name);
	}

	public String createSymFromExp( IndexVar tree) {
		String type = createSymFromExp(tree.index); 
		if (type.equals("INT")){
			//Good
		}else{
			printError("Error at line " + tree.pos + " - Array index call uses a type other than int");
		}
		return getDeepestType(tree.name);
	}

  /* Expressions */
	public String createSymFromExp( NilExp tree) {
		return "NULL";
	}
	public String createSymFromExp( VarExp tree) {
		if (tree.variable == (null)) {
			printError( "ERROR INVALID VAREXP");
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

		return getDeepestType(tree.func);
	}
	public String createSymFromExp( OpExp tree) {
		String type1 = createSymFromExp( tree.left );
		String type2 = createSymFromExp( tree.right ); 
		if (type1.equals(type2)){
			return type1;
		}else{
			printError("Error at line " + tree.pos + " - Performing operation on two variables of different types");
			return "ERR";
		}
	}
	public String createSymFromExp( AssignExp tree) {
		String type1 = createSymFromExp(tree.lhs);
		String type2 = createSymFromExp(tree.rhs);
		if (type1.equals(type2)){
			return type1;
		}else{
			printError("Error at line " + tree.pos + " - Assigning a variable to a value of a different type");
			return "ERR";
		}
	}
	public String createSymFromExp( IfExp tree) {
		startBlock("If Test Condition");
		createSymFromExp(tree.test);
		endBlock("If Test Condition"); 

		startBlock("If Body");
		createSymFromExp(tree.then);
		endBlock("If Body");

		startBlock("Else Body");
		createSymFromExp(tree.els);
		endBlock("Else Body");

		return "";
	}
	public String createSymFromExp( WhileExp tree) {
		startBlock("While Test Condition");
		createSymFromExp(tree.test);
		endBlock("While Test Condition");

		startBlock("While Body");
		createSymFromExp(tree.body);
		endBlock("While Body"); 

		return "";
	}
	public String createSymFromExp( ReturnExp tree) {
		return createSymFromExp(tree.exp);
	}
	public String createSymFromExp( CompoundExp tree) {
		createSymFromExp(tree.decs);
		createSymFromExp(tree.exps);

		return "";
	}

	/* Declarations */
	public void createSymFromExp( FunctionDec tree) {
		FunSym s = new FunSym(tree.func, createSymFromExp(tree.result), this.scope);
		insertIntoTable(vals, s);
		
		startBlock("Function: " + tree.func);

		insertParams(vals, tree.params);
		insertCompound(vals, tree.body);

		endBlock("Function: " + tree.func);
	}
	public void createSymFromExp( SimpleDec tree) {
		SimpleVarSym s = new SimpleVarSym(tree.name, createSymFromExp(tree.typ), this.scope, null);
		insertIntoTable(vals, s);
	}
	public void createSymFromExp( ArrayDec tree) {
		ArrayVarSym s = new ArrayVarSym(tree.name, createSymFromExp(tree.typ), tree.size.value, this.scope, null);
		insertIntoTable(vals, s);
	}
}