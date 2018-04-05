package assembly;

public class Assignment {
    public String variableAss;
    /* op is an int representing an operation:
    PLUS  = 0, MINUS = 1, MUL = 2, DIV = 3, EQ = 4, NE = 5, LT = 6,
    LE = 7, GT = 8, GE = 9 */
    public int op;
    public int a;
    public int b;
    public String variableOp1;
    public String variableOp2;
    public int control = 0;
// for op, send in values, mul, div, add, min
    public Assignment(String assign, int a, int b, int op) {
        this.a = a;
        this.b = b;
        this.variableAss = assign;
        this.op = op;
        control = 1;
    }
    public Assignment(String assign, String a, int b, int op) {
        this.variableOp1 = a;
        this.b = b;
        this.variableAss = assign;
        this.op = op;
        control = 2;
    }
    public Assignment(String assign, int a, String b, int op) {
        this.a = a;
        this.variableOp2 = b;
        this.variableAss = assign;
        this.op = op;
        control = 3;
    }
    public Assignment(String assign, String a, String b, int op) {
        this.variableOp1 = a;
        this.variableOp2 = b;
        this.variableAss = assign;
        this.op = op;
        control = 4;
    }
    public Assignment(String assign, String a) {
        this.variableOp1 = a;
        this.variableAss = assign;
        control = 5;
    }
    public Assignment(String assign, int a) {
        Assembler temp = new Assembler();
        Declaration oldDec = temp.getDec(assign);
        int memLoc = oldDec.memoryLocation;
        int fp = oldDec.getmemLoc();

        this.a = a;
        this.variableAss = assign;
        control = 6;
        System.out.println("* evaluating " + variableAss +  " = " +this.a );
        System.out.println("* loading " + variableAss);
    }
}
