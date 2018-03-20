package absyn;

abstract public class Absyn {
  public int pos;

  final static int SPACES = 4;

  static private void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ ) System.out.print( " " );
  }

  static public void showTree( ExpList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    } 
  }

  static public void showTree( DecList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    } 
  }

  static public void showTree( VarDecList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    } 
  }

  static public void showTree( Var tree, int spaces ) {
    if( tree instanceof IndexVar )
      showTree( (IndexVar)tree, spaces );
    else if( tree instanceof SimpleVar )
      showTree( (SimpleVar)tree, spaces );
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }

  static public void showTree( Exp tree, int spaces ) {
    if( tree instanceof NilExp )
      showTree( (NilExp)tree, spaces );
    else if( tree instanceof VarExp )
      showTree( (VarExp)tree, spaces );
    else if( tree instanceof IntExp )
      showTree( (IntExp)tree, spaces );
    else if( tree instanceof CallExp )
      showTree( (CallExp)tree, spaces );
    else if( tree instanceof OpExp )
      showTree( (OpExp)tree, spaces );
    else if( tree instanceof AssignExp )
      showTree( (AssignExp)tree, spaces );
    else if( tree instanceof IfExp )
      showTree( (IfExp)tree, spaces );
    else if( tree instanceof WhileExp )
      showTree( (WhileExp)tree, spaces );
    else if( tree instanceof ReturnExp )
      showTree( (ReturnExp)tree, spaces );
    else if( tree instanceof CompoundExp )
      showTree( (CompoundExp)tree, spaces );
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }

  static public void showTree( Dec tree, int spaces ) {
    if( tree instanceof FunctionDec ){
      showTree( (FunctionDec)tree, spaces );
    }else if( tree instanceof VarDec ){
      showTree( (VarDec)tree, spaces );
    }else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }

  static public void showTree( VarDec tree, int spaces) {
    if( tree instanceof SimpleDec ){
      showTree( (SimpleDec)tree, spaces );
    }else if( tree instanceof ArrayDec ){
      showTree( (ArrayDec)tree, spaces );
    }else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }

  static public void showTree( NameTy tree) {
    if (tree.typ == NameTy.INT){
      System.out.print( "INT" ); 
    }else if (tree.typ == NameTy.VOID){
      System.out.print( "VOID" ); 
    }
  }

  /* Variables */
  static public void showTree( SimpleVar tree, int spaces) {
    indent( spaces );
    System.out.println( "SimpleVar: " + tree.name); 
  }

  static public void showTree( IndexVar tree, int spaces) {
    indent( spaces );
    System.out.println( "IndexVar: " + tree.name);
    spaces+= SPACES;
    showTree(tree.index, spaces); 
  }

  /* Expressions */
  static public void showTree( NilExp tree, int spaces) {
    indent( spaces );
    System.out.println( "NilExp: "); 
  }
  static public void showTree( VarExp tree, int spaces) {
    indent( spaces );
    System.out.println( "VarExp: "); 
    spaces+= SPACES;
    if (tree.variable == (null)) {
        System.out.println ( "ERROR INVALID VAREXP");
    }
    else {
      showTree(tree.variable, spaces);
    }
  }
  static public void showTree( IntExp tree, int spaces) {
    indent( spaces );
    System.out.println( "IntExp: " + tree.value); 
  }
  static public void showTree( CallExp tree, int spaces) {
    indent( spaces );
    System.out.println( "CallExp: " + tree.func); 
    spaces+= SPACES;
    showTree(tree.args, spaces);
  }
  static public void showTree( OpExp tree, int spaces) {
    indent( spaces );
    System.out.print( "OpExp: ");
    switch (tree.op){
      case 0:
        System.out.println("+");
        break;
      case 1:
        System.out.println("-");
        break;
      case 2:
        System.out.println("*");
        break;
      case 3:
        System.out.println("/");
        break;
      case 4:
        System.out.println("=");
        break;
      case 5:
        System.out.println("!=");
        break;
      case 6:
        System.out.println("<");
        break;
      case 7:
        System.out.println("<=");
        break;
      case 8:
        System.out.println(">");
        break;
      case 9:
        System.out.println(">=");
        break;
      default:
        System.out.println( "Unrecognized operator at line " + tree.pos);
    }
    spaces+= SPACES;
    showTree( tree.left, spaces );
    showTree( tree.right, spaces ); 
  }
  static public void showTree( AssignExp tree, int spaces) {
    indent( spaces );
    System.out.println( "AssignExp: "); 
    spaces += SPACES;
    showTree(tree.lhs, spaces);
    showTree(tree.rhs, spaces);
  }
  static public void showTree( IfExp tree, int spaces) {
    indent( spaces );
    System.out.println( "IfExp: "); 
    spaces += SPACES;
    showTree(tree.test, spaces);
    showTree(tree.then, spaces); 
    showTree(tree.els, spaces);
  }
  static public void showTree( WhileExp tree, int spaces) {
    indent( spaces );
    System.out.println( "WhileExp: "); 
    spaces += SPACES;
    showTree(tree.test, spaces);
    showTree(tree.body, spaces); 
  }
  static public void showTree( ReturnExp tree, int spaces) {
    indent( spaces );
    System.out.println( "ReturnExp: "); 
    spaces += SPACES;
    showTree(tree.exp, spaces);
  }
  static public void showTree( CompoundExp tree, int spaces) {
    indent( spaces );
    System.out.println( "CompoundExp: "); 
    spaces += SPACES;
    showTree(tree.decs, spaces);
    showTree(tree.exps, spaces);
  }
  static public void showTree( FunctionDec tree, int spaces) {
    indent( spaces );
    System.out.println( "FunctionDec: " + tree.func);
    spaces += SPACES;
    showTree(tree.params, spaces);
    showTree(tree.body, spaces);
  }
  static public void showTree( SimpleDec tree, int spaces) {
    indent( spaces );
    System.out.print( "SimpleDec: ");
    showTree(tree.typ);
    System.out.println(" " + tree.name); 
  }
  static public void showTree( ArrayDec tree, int spaces) {
    indent( spaces );
    System.out.print( "ArrayDec: ");
    showTree(tree.typ);
    System.out.println(" " + tree.name);
    if (tree.size != null){
      spaces += SPACES;
      showTree(tree.size, spaces);
    }
  }
}
