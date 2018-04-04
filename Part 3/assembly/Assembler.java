package assembly;

import absyn.*;
import symtable.*;
import java.util.*;
import java.io.*;

public class Assembler {

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

    public void addAssign(){

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


    }
    public void addFunction(){

    }
    public void addIf(){

    }
    public void addWhile(){

    }
}
