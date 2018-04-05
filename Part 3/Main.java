
import java.io.*;
import absyn.*;
import symtable.*;
import assembly.*;

class Main {

    static public void main(String argv[]) {
    /* Start the parser */
        try {
            boolean showA = false, showS = false, showC = false;
            if (argv.length >= 2){
                for (int i = 1; i < argv.length; i++){
                    if (argv[i].equals("-a")){
                        showA = true;
                    }else if (argv[i].equals("-s")){
                        showS = true;
                    }else if (argv[i].equals("-c")){
                        showC = true;
                    }
                }
            }

            parser p = new parser(new Lexer(new FileReader(argv[0])));
            if (showA == true){
                p.SHOW_TREE = true;
            }
            Object result = p.parse().value;
            Assembler a = new Assembler();
            SymTable s = new SymTable(a);
            if (showS == true){
                s.SHOW_TABLE = true;
            }
            if (showC == true){
                a.SHOW_ASSEMBLER = true;
            }
            a.symbols = s;
            
            s.insert((Absyn)result);
        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}