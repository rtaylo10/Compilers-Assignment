
import java.io.*;
import absyn.*;
import symtable.*;

class Main {
    static public void main(String argv[]) {
    /* Start the parser */
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            parser.SHOW_TREE = false;
            Object result = p.parse().value;
            SymTable s = new SymTable();
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