
import java.io.*;

class Main {
    static public void main(String argv[]) {
    /* Start the parser */
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            parser.SHOW_TREE = true;
            Object result = p.parse().value;
            symTable s = new symTable();
            symTable.generate(result);
        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}