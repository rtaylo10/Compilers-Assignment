package assembly;

import absyn.*;
import symtable.*;
import java.util.*;
import java.io.*;

public class Assembler {

    public static boolean SHOW_ASSEMBLER = false;

	/*Stores the symbol table created from part 2 in here, that way you can work on this part without worrying about the symbol table functions getting in the way*/
	public SymTable symbols;

	public Assembler(){
		symbols = null;
	}
	public Assembler(SymTable s){
		symbols = s;
	}
    public static int instructionTracker = 0;
    public void output() {
        System.out.println("output statement");
    }
    public void input() {
        System.out.println("input statement");
    }
    /*Previously in finale.java*/
    public void end() {
        System.out.println(instructionTracker + ":  HALT    0,0,0");
    }
    /*Previously in prelude.java*/
    public void start() {
        System.out.println("0:  LD 6,0(0)");
        System.out.println("1:  LDA 5,0(6)");
        System.out.println("2:  ST 0,0(0)");
        instructionTracker = 3;
    }

    public void printMessage(String s){
        if (SHOW_ASSEMBLER == true){
            System.out.println(s);
        }
    }

    public void addAssign(AssignExp tree){
        Assignment assign = null;
    	if (tree.lhs instanceof VarExp && tree.rhs instanceof OpExp){
            String varAssign = ((VarExp)(tree.lhs)).variable.name;
            if (((OpExp)(tree.rhs)).left instanceof VarExp){
                String var1 = ((VarExp)(((OpExp)(tree.rhs)).left)).variable.name;
                if ((((OpExp)(tree.rhs)).right) instanceof VarExp){
                    String var2 = ((VarExp)(((OpExp)(tree.rhs)).right)).variable.name;
                    assign = new Assignment(varAssign, var1, var2, ((OpExp)(tree.rhs)).op);

                    String message = "you have added " + assign.variableAss +  " = " +assign.variableOp1  + ' ' + assign.op + " " + assign.variableOp2;
                    printMessage(message);
                }else if ((((OpExp)(tree.rhs)).right) instanceof IntExp){
                    int var2 = Integer.parseInt(((IntExp)(((OpExp)(tree.rhs)).left)).value);
                    assign = new Assignment(varAssign, var1, var2, ((OpExp)(tree.rhs)).op);

                    String message = "you have added " + assign.variableAss +  " = " + assign.variableOp1  + ' ' + assign.op + " " + assign.b;
                    printMessage(message);
                }
            }else if (((OpExp)(tree.rhs)).left instanceof IntExp){
                int var1 = Integer.parseInt(((IntExp)(((OpExp)(tree.rhs)).left)).value);
                if ((((OpExp)(tree.rhs)).right) instanceof VarExp){
                    String var2 = ((VarExp)(((OpExp)(tree.rhs)).right)).variable.name;
                    assign = new Assignment(varAssign, var1, var2, ((OpExp)(tree.rhs)).op);

                    String message = "you have added " + assign.variableAss +  " = " + assign.a  + ' ' + assign.op + " " + assign.variableOp2;
                    printMessage(message);
                }else if ((((OpExp)(tree.rhs)).right) instanceof IntExp){
                    int var2 = Integer.parseInt(((IntExp)(((OpExp)(tree.rhs)).left)).value);
                    assign = new Assignment(varAssign, var1, var2, ((OpExp)(tree.rhs)).op);

                    String message = "you have added " + assign.variableAss +  " = " + assign.a  + ' ' + assign.op + " " + assign.b;
                    printMessage(message);
                }
            }
        }else if (tree.lhs instanceof VarExp && tree.rhs instanceof VarExp){
            String varLeft = ((VarExp)(tree.lhs)).variable.name;
            String varRight = ((VarExp)(tree.rhs)).variable.name;
            assign = new Assignment(varLeft, varRight);

            String message = "you have added " + assign.variableAss +  " = " + assign.variableOp1;
            printMessage(message);
        }else if (tree.lhs instanceof VarExp && tree.rhs instanceof IntExp){
            String varLeft = ((VarExp)(tree.lhs)).variable.name;
            String varRight = ((IntExp)(tree.rhs)).value;
            assign = new Assignment(varLeft, varRight);

            String message = "you have added " + assign.variableAss +  " = " + assign.variableOp1;
            printMessage(message);
        }

        if (assign != null){
            Assignment a = assign;
            /*Do your thing here*/
        }
    }

    public String getDecType(Declaration d){
    	/*Will return either INT or VOID*/
    	return symbols.getDeepestType(d.varName);
    }

    public boolean decIsArray(Declaration d){
    	/*A declaration's corresponding symbol type is either "ArrayVariable", "Constant", "Function", "SimpleVariable", "Type" or "UND" (for undefined)*/
    	/*This function checks if it is an array, but you can modify it to look for any of the other values.*/
    	/*Also, I'm pretty sure that only ArrayVariable, Function and SimpleVariable are actually put into the symbol table*/
    	if (symbols.getSymType(symbols.lookupDeepestVal(d.varName)).equals("ArrayVariable")){
    		return true;
    	}else{
    		return false;
    	}
    }

    public boolean decIsSimpleVar(Declaration d){
    	/*This function checks if it is an non-array variable*/
    	if (symbols.getSymType(symbols.lookupDeepestVal(d.varName)).equals("SimpleVariable")){
    		return true;
    	}else{
    		return false;
    	}
    }

    public void addDeclaration(Declaration d){
    	/*Declaration has been created, now do what you want with it (At the moment this is called every time a VarDec has been found in the tree)*/
        String message = "added " + d.varName + " at location" + d.memLoc;
        printMessage(message);

    }
    public void addFunction(){

    }
    public void addIf(){

    }
    public void addWhile(){

    }
}
