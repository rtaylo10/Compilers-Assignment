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
        Assembler temp = new Assembler();
        Declaration oldDec = temp.getDec(assign);
        int memLoc = oldDec.memoryLocation;
        int fp = oldDec.getmemLoc();
        int offset = fp - memLoc;
        int instruction = temp.instructionTracker;
        int c;
        this.variableAss = assign;
        switch (op) {
            case 0: c = a+b;
                    break;
            case 1: c = a-b;
                    break;
            case 2: c = a*b;
                    break;
            case 3: c = Math.round(a/b);
                    break;
            default: System.out.println("unsupported operation.");
                    c=0;
                    break;

        }
        System.out.println("* evaluating " + variableAss +  " = " +this.a );
        System.out.println("* loading " + variableAss);
        System.out.println(instruction + ": LDA " + "0,-" + offset + "(5)" );
        instruction++;
        System.out.println(instruction + ": LDC " + "1," +  c + "(0)" );
        instruction++;
        System.out.println(instruction + ": ST  0,0(1)");
        instruction++;
        temp.instructionTracker = instruction;
        this.op = op;
        control = 1;
    }
    public Assignment(String assign, String a, int b, int op) {
        Assembler temp = new Assembler();
        int instruction = temp.instructionTracker;
        Declaration assignDec = temp.getDec(assign);
        Declaration aDec = temp.getDec(a);
        int fp = aDec.getmemLoc();
        int memAssign = assignDec.memoryLocation;
        int memA = aDec.memoryLocation;
        int offsetAssign = fp-memAssign;
        int offsetA = fp - memA;
        //load address assign. load a into 1, b into 2, decide what math, load result into 3, St 3 to 0
        System.out.println("* loading "+  assign);
        System.out.println(instruction + ": LDA  0,-" + offsetAssign + "(5)");
        instruction++;
        System.out.println("* Loading " + a);
        System.out.println(instruction + ": LD  1,-" + offsetA +"(5)");
        instruction++;
        System.out.println("* Loading " + b);
        System.out.println(instruction + ": LDC  2," + b +"(2)");
        instruction++;
        //Decide what op to do
        String opCode;
        switch (op) {
            case 0: opCode = "ADD";
                break;
            case 1: opCode = "SUB";
                break;
            case 2: opCode = "MUL";
                break;
            case 3: opCode = "DIV";
                break;
            default: System.out.println("unsupported operation. Defaulting to add");
                opCode="ADD";
                break;
        }
        System.out.println(instruction +":  " + opCode + "  3,1,2");
        instruction++;
        System.out.println(instruction +":  ST  3,0(0)");
        instruction++;
        temp.instructionTracker = instruction;
    }
    public Assignment(String assign, int a, String b, int op) {
        Assembler temp = new Assembler();
        int instruction = temp.instructionTracker;
        Declaration assignDec = temp.getDec(assign);
        Declaration bDec = temp.getDec(b);
        int fp = bDec.getmemLoc();
        int memAssign = assignDec.memoryLocation;
        int memB = bDec.memoryLocation;
        int offsetAssign = fp-memAssign;
        int offsetB = fp-memB;
        //load address assign. load a into 1, b into 2, decide what math, load result into 3, St 3 to 0
        System.out.println("* loading "+  assign);
        System.out.println(instruction + ": LDA  0,-" + offsetAssign + "(5)");
        instruction++;
        System.out.println("* Loading " + a);
        System.out.println(instruction + ": LDC  1," + a + "(1)" );
        instruction++;
        System.out.println("* Loading " + b);
        System.out.println(instruction + ": LD  2,-" + offsetB +"(5)");
        instruction++;
        //Decide what op to do
        String opCode;
        switch (op) {
            case 0: opCode = "ADD";
                break;
            case 1: opCode = "SUB";
                break;
            case 2: opCode = "MUL";
                break;
            case 3: opCode = "DIV";
                break;
            default: System.out.println("unsupported operation. Defaulting to add");
                opCode="ADD";
                break;
        }
        System.out.println(instruction +":  " + opCode + "  3,1,2");
        instruction++;
        System.out.println(instruction +":  ST  3,0(0)");
        instruction++;
        temp.instructionTracker = instruction;
    }
    public Assignment(String assign, String a, String b, int op) {
        Assembler temp = new Assembler();
        int instruction = temp.instructionTracker;
        Declaration assignDec = temp.getDec(assign);
        Declaration aDec = temp.getDec(a);
        Declaration bDec = temp.getDec(b);
        int fp = aDec.getmemLoc();
        int memAssign = assignDec.memoryLocation;
        int memA = aDec.memoryLocation;
        int memB = bDec.memoryLocation;
        int offsetAssign = fp-memAssign;
        int offsetA = fp - memA;
        int offsetB = fp-memB;
        //load address assign. load a into 1, b into 2, decide what math, load result into 3, St 3 to 0
        System.out.println("* loading "+  assign);
        System.out.println(instruction + ": LDA  0,-" + offsetAssign + "(5)");
        instruction++;
        System.out.println("* Loading " + a);
        System.out.println(instruction + ": LD  1,-" + offsetA +"(5)");
        instruction++;
        System.out.println("* Loading " + b);
        System.out.println(instruction + ": LD  2,-" + offsetB +"(5)");
        instruction++;
        //Decide what op to do
        String opCode;
        switch (op) {
            case 0: opCode = "ADD";
                break;
            case 1: opCode = "SUB";
                break;
            case 2: opCode = "MUL";
                break;
            case 3: opCode = "DIV";
                break;
            default: System.out.println("unsupported operation. Defaulting to add");
                opCode="ADD";
                break;
        }
        System.out.println(instruction +":  " + opCode + "  3,1,2");
        instruction++;
        System.out.println(instruction +":  ST  3,0(0)");
        instruction++;
        temp.instructionTracker = instruction;
        control = 4;
    }
    public Assignment(String assign, String a) {
        Assembler temp = new Assembler();
        int instruction = temp.instructionTracker;
        Declaration assignDec = temp.getDec(assign);
        Declaration aDec = temp.getDec(a);
        int fp = aDec.getmemLoc();
        int memAssign = assignDec.memoryLocation;
        int memA = aDec.memoryLocation;
        int offsetAssign = fp-memAssign;
        int offsetA = fp - memA;
        System.out.println("* evalutating "+  assign + " = " + a);
        this.variableOp1 = a;
        this.variableAss = assign;
        System.out.println("* Loading " + assign);
        System.out.println(instruction + ": LDA  0,-" + offsetAssign + "(5)");
        instruction++;
        System.out.println("* Loading " + a);
        System.out.println(instruction + ": LD  1,-" + offsetA +"(5)");
        instruction++;
        System.out.println(instruction + ": ST  0,0(1)");
        instruction++;
        temp.instructionTracker = instruction;

        control = 5;

    }
    public Assignment(String assign, int a) {
        Assembler temp = new Assembler();
        Declaration oldDec = temp.getDec(assign);
        int memLoc = oldDec.memoryLocation;
        int fp = oldDec.getmemLoc();
        int offset = fp - memLoc;
        int instruction = temp.instructionTracker;

        this.a = a;
        this.variableAss = assign;
        control = 6;
        System.out.println("* evaluating " + variableAss +  " = " +this.a );
        System.out.println("* loading " + variableAss);
        System.out.println(instruction + ": LDA " + "0,-" + offset + "(5)" );
        instruction++;
        System.out.println(instruction + ": LDC " + "1," +  a + "(0)" );
        instruction++;
        System.out.println(instruction + ": ST  0,0(1)");
        instruction++;
        temp.instructionTracker = instruction;

    }
}
