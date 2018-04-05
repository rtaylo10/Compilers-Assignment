package assembly;

public class Declaration {
    public String varName;
    public static int memLoc = 0;
    public int memoryLocation;
    public Declaration (String varName) {
        this.varName = varName;
        this.memoryLocation = memLoc;
        //increment memlock
        memLoc = memLoc+1;
        
    }
    public int getmemLoc() {
        return memLoc;
    }
}
