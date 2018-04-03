package assembly;

public class declaration {
    public String varName;
    public static int memLoc;
    public declaration (String varName) {
        this.varName = varName;
        //increment memlock
        System.out.println( "added " + varName + " at location" + memLoc);
        memLoc = memLoc+4;
        
    }
}
