package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * LIT loads a literal value onto the top of the runStack.
 * Initializes a variable to the value
 */
public class LitCode extends ByteCode {
    private ArrayList<String> litCodeArgs = new ArrayList<String>();
    protected String var=""; //variable that is initialized
    private int value;
    private String name="Lit";
    
/*LitCode can have 1 or 2 args  ie. LIT 0 m or LIT 3*/
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        for(String bca: byteCodeArgs) {
            litCodeArgs.add(bca);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        if(litCodeArgs.size() == 2) {
            var = litCodeArgs.get(1); //get the 2nd arg, which is the variable that is initialized to the value
        }
        else {
            var = "";
        }
        value = Integer.parseInt(litCodeArgs.get(0));
        vm.push(value);//push the value on top of runStack's current frame
    }
    /*Used when DUMP is on to print the ByteCode information*/
    @Override
    public String toString() {
        if(litCodeArgs.size() == 1) {
            return "LIT " + litCodeArgs.get(0);
        }
        else { //assume the var declaration is int ie int m
            return "LIT " + litCodeArgs.get(0) + " " + litCodeArgs.get(1) + "\t\tint " + litCodeArgs.get(1);
        }
    }

    @Override
    public String getFirstArg() {
        return litCodeArgs.get(0);
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //this method is not used by LIT
    }

    @Override
    public String getName() {
        return name;
    }
}

