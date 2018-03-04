/*
  File Name: cminus.flex
  JFlex specification for the C- language
*/
   
import java.util.ArrayList;

%%
   
%class Lexer
%type Token
%line
%column
    
%eofval{
  System.out.println("*** end of file reached");
  return null;
%eofval};

%{
  /*Stack to keep track of all of the opening/closing symbols*/
  private static ArrayList<String> symbolStack = new ArrayList<String>();

  /*Returns the opening symbol that matches with the closing symbol given*/
  private String getMatchingOpenSymbol(String closeSymbol){
    String symbolName = "";
    switch (closeSymbol) {
      case ")": symbolName = "(";
        break;
      case "]": symbolName = "[";
        break;
      case "}": symbolName = "{";
        break;
      case "*/": symbolName = "/*";
        break;
    }
    return symbolName;
  }

  /*When given an open symbol, adds it to the symbol stack*/
  private void addOpenSymbol(String symbol){
    symbolStack.add(symbol);
  }

  /*When given a closing symbol, checks if the corresponding opening symbol is on the symbol stack.
  If it is, removes that symbol from the stack and returns a "success"*/
  private int checkCloseSymbol(String symbol){
    String symbolName;
    symbolName = getMatchingOpenSymbol(symbol);
    if (symbolStack.size() == 0){
      return 2;
    }
    if (symbolStack.get(symbolStack.size()-1).equals(symbolName)){
      symbolStack.remove(symbolStack.size()-1);
      return 0;
    }else{
      return 1;
    }
  }
  private int isInComment(){
    if (symbolStack.size() == 0){
      return 0;
    }
    if (symbolStack.get(symbolStack.size()-1).equals("/*")){
      return 1;
    }else{
      return 0;
    }
  }
%};
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n

/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]
   
/* A digit is a number between zero and nine.  */
digit = [0-9]
   
/* A letter is an upper or lowercase character from a-Z */
letter = [a-zA-Z]

specialSymbol = "+"|"-"|"*"|"/"|"<"|"<="|">"|">="|"=="|"!="|"="|";"|";"|","
openSymbol = "("|"["|"{"|"/*"
closeSymbol = ")"|"]"|"}"|"*/"

%%
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */
   
"else"            { if (isInComment() == 0){return new Token(Token.ELSE, yytext(), yyline, yycolumn);}}
"if"              { if (isInComment() == 0){return new Token(Token.IF, yytext(), yyline, yycolumn);}}
"int"             { if (isInComment() == 0){return new Token(Token.INT, yytext(), yyline, yycolumn);}}
"return"          { if (isInComment() == 0){return new Token(Token.RETURN, yytext(), yyline, yycolumn);}}
"void"            { if (isInComment() == 0){return new Token(Token.VOID, yytext(), yyline, yycolumn);}}
"while"           { if (isInComment() == 0){return new Token(Token.WHILE, yytext(), yyline, yycolumn);}}
{openSymbol}      { if (isInComment() == 0){addOpenSymbol(yytext());
                      return new Token(Token.SPECIAL, yytext(), yyline, yycolumn);
                  }}
{closeSymbol}     { int isError = checkCloseSymbol(yytext());
                    if (isInComment() == 0 || yytext().equals("/*")){ if (isError == 0){
                      return new Token(Token.SPECIAL, yytext(), yyline, yycolumn);
                    }else if (isError == 1){
                      System.err.println(getMatchingOpenSymbol(yytext()) + " expected at line " + yyline);
                      return new Token(Token.ERROR, yytext(), yyline, yycolumn);
                    }else if (isError == 2){
                      System.err.println(yytext() + " unexpected at line " + yyline);
                      return new Token(Token.ERROR, yytext(), yyline, yycolumn);
                    }
                  }}
{specialSymbol}   { if (isInComment() == 0){return new Token(Token.SPECIAL, yytext(), yyline, yycolumn);}}
{letter}{letter}* { if (isInComment() == 0){return new Token(Token.ID, yytext(), yyline, yycolumn);}}
{digit}{digit}*   { if (isInComment() == 0){return new Token(Token.NUM, yytext(), yyline, yycolumn);}}

{WhiteSpace}*     {/* Skip Whitespace*/}
.                 { return new Token(Token.UNKNOWN, yytext(), yyline, yycolumn);}
  
