
import java.io.*;
import absyn.*;
import symtable.*;
import assembly.*;

class Main {
    static public void main(String argv[]) {
    /* Start the parser */
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            parser.SHOW_TREE = false;
            Object result = p.parse().value;
            Assembler a = new Assembler();
            SymTable s = new SymTable(a);
            a.symbols = s;
            if (argv.length == 2){
                if (argv[1].equals("-s")){
                    s.SHOW_TABLE = true;
                }
            }
            
            s.insert((Absyn)result);
        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}