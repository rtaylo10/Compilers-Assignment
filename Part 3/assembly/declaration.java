package assembly;

public class Declaration {
    public String varName;
    public static int memLoc;
    public Declaration (String varName) {
        this.varName = varName;
        //increment memlock
        memLoc = memLoc+4;
        
    }
}
