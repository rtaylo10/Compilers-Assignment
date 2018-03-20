package symtable;

import absyn.*;
import java.util.*;

public class SymTable{
	final static int MAXSIZE = 19937;
	final static int SHIFT = 4;

	public SymTable parent;
	public Hashtable<Integer, Sym> vals;
	public int scope;

	public SymTable(){
		this.parent = null;
		vals = new Hashtable<Integer, Sym>();
	}

	public SymTable(SymTable parent){
		this.parent = parent;
		vals = new Hashtable<Integer, Sym>();
	}

	public void generate(Object o){

	}

	public int insert(Absyn o){
		createSymFromExp(o);
		return 0;
	}

	private int insertParams(VarDecList o){
		createSymFromExp(o);
		return 0;
	}

	private int insertCompound(CompoundExp o){
		createSymFromExp(o);
		return 0;
	}

	private int insertIntoTable(Hashtable<Integer, Sym> table, Sym s){
		Random rand = new Random(); 
		int keyValue = rand.nextInt(MAXSIZE);
		int finalValue;
		finalValue = this.hash(keyValue);
		table.put(finalValue, s);
		return finalValue;
	}

	public int lookup(Object o){
		return 0;
	}

	public void delete(Object o){

	}

	public int hash(int key){
		int temp = 0;
		int i = 0;
		while(vals.get(temp) != null){
			temp = ((temp << SHIFT) + key) % MAXSIZE;
			i++;
		}
		return temp;
	}

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

	public void createSymFromExp( Var tree ) {
		if( tree instanceof IndexVar )
			createSymFromExp( (IndexVar)tree );
		else if( tree instanceof SimpleVar )
			createSymFromExp( (SimpleVar)tree );
		else {
			System.err.println( "Illegal expression at line " + tree.pos  );
		}
	}

	public void createSymFromExp( Exp tree ) {
		if( tree instanceof NilExp )
			createSymFromExp( (NilExp)tree );
		else if( tree instanceof VarExp )
			createSymFromExp( (VarExp)tree );
		else if( tree instanceof IntExp )
			createSymFromExp( (IntExp)tree );
		else if( tree instanceof CallExp )
			createSymFromExp( (CallExp)tree );
		else if( tree instanceof OpExp )
			createSymFromExp( (OpExp)tree );
		else if( tree instanceof AssignExp )
			createSymFromExp( (AssignExp)tree );
		else if( tree instanceof IfExp )
			createSymFromExp( (IfExp)tree );
		else if( tree instanceof WhileExp )
			createSymFromExp( (WhileExp)tree );
		else if( tree instanceof ReturnExp )
			createSymFromExp( (ReturnExp)tree );
		else if( tree instanceof CompoundExp )
			createSymFromExp( (CompoundExp)tree );
		else {
			
			System.err.println( "Illegal expression at line " + tree.pos  );
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
			System.err.println( "Illegal type name at position " + tree.pos  );
			return "ERR";
		}
	}

  /* Variables */
	public void createSymFromExp( SimpleVar tree ) {
		// System.out.println( "SimpleVar: " + tree.name); 
	}

	public void createSymFromExp( IndexVar tree) {
		createSymFromExp(tree.index); 
	}

  /* Expressions */
	public void createSymFromExp( NilExp tree) {
	}
	public void createSymFromExp( VarExp tree) {
		if (tree.variable == (null)) {
			System.err.println ( "ERROR INVALID VAREXP");
		}
		else {
			createSymFromExp(tree.variable);
		}
	}
	public void createSymFromExp( IntExp tree) {

	}
	public void createSymFromExp( CallExp tree) {
		createSymFromExp(tree.args);
	}
	public void createSymFromExp( OpExp tree) {
		createSymFromExp( tree.left );
		createSymFromExp( tree.right ); 
	}
	public void createSymFromExp( AssignExp tree) {
		createSymFromExp(tree.lhs);
		createSymFromExp(tree.rhs);
	}
	public void createSymFromExp( IfExp tree) {
		createSymFromExp(tree.test);
		createSymFromExp(tree.then); 
		createSymFromExp(tree.els);
	}
	public void createSymFromExp( WhileExp tree) {
		createSymFromExp(tree.test);
		createSymFromExp(tree.body); 
	}
	public void createSymFromExp( ReturnExp tree) {
		createSymFromExp(tree.exp);
	}
	public void createSymFromExp( CompoundExp tree) {
		createSymFromExp(tree.decs);
		createSymFromExp(tree.exps);
	}
	public void createSymFromExp( FunctionDec tree) {
		//Create the function's block
		SymTable child = new SymTable(this);
		child.insertParams(tree.params);
		child.insertCompound(tree.body);

		//Adds the function's block to the function's symbol and inserts the whole thing into the symbol table
		FunSym s = new FunSym(tree.func, child);
		insertIntoTable(vals, s);
	}
	public void createSymFromExp( SimpleDec tree) {
		SimpleVarSym s = new SimpleVarSym(tree.name, createSymFromExp(tree.typ));
		insertIntoTable(vals, s);
	}
	public void createSymFromExp( ArrayDec tree) {
		ArrayVarSym s = new ArrayVarSym(tree.name, createSymFromExp(tree.typ), tree.size.value);
		insertIntoTable(vals, s);
	}
}