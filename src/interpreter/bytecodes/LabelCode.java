package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * LABEL is a target for branches, used by GOTO, FALSEBRANCH, and CALL to jump to <label id>
 */
public class LabelCode extends ByteCode {
    private String labelCodeArg;
    private String name= "Label";

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        labelCodeArg = byteCodeArgs.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        //nothing to execute
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //Label does not need this method
    }

    @Override
    public String getFirstArg() {
        return labelCodeArg;
    }

    @Override
    public String toString() {
        return "LABEL " + labelCodeArg;
    }

    @Override
    public String getName() {
        return name;
    }

    
}
