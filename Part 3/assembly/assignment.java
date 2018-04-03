package assembly;

public class assignment {
    public String variableAss;
    public String op;
    public int a;
    public int b;
    public String variableOp1;
    public String variableOp2;
    public int control = 0;
// for op, send in values, mul, div, add, min
    public assignment(String assign, int a, int b, String op) {
        this.a = a;
        this.b = b;
        this.variableAss = assign;
        this.op = op;
        control = 1;
        System.out.println("you have added " + variableAss +  " = " +this.a  + ' ' + this.op + " " + this.b );
    }
    public assignment(String assign, String a, int b, String op) {
        this.variableOp1 = a;
        this.b = b;
        this.variableAss = assign;
        this.op = op;
        control = 2;
        System.out.println("you have added " + variableAss +  " = " +this.variableOp1  + ' ' + this.op + " " + this.b );
    }
    public assignment(String assign, int a, String b, String op) {
        this.a = a;
        this.variableOp2 = b;
        this.variableAss = assign;
        this.op = op;
        control = 3;
        System.out.println("you have added " + variableAss +  " = " +this.a  + ' ' + this.op + " " + this.variableOp2 );
    }
    public assignment(String assign, String a, String b, String op) {
        this.variableOp1 = a;
        this.variableOp2 = b;
        this.variableAss = assign;
        this.op = op;
        control = 4;
        System.out.println("you have added " + variableAss +  " = " +this.variableOp1  + ' ' + this.op + " " + this.variableOp2 );
    }
}
