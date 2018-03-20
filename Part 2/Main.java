
import java.io.*;
import absyn.*;
import symtable.*;

class Main {
    static public void main(String argv[]) {
    /* Start the parser */
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            parser.SHOW_TREE = true;
            Object result = p.parse().value;
            SymTable s = new SymTable();
            s.insert((Absyn)result);
        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}