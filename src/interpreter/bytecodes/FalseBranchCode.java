package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * FALSEBRANCH: get the most current value in the runstack. If it's false, then branch to a LABEL,
 * otherwise, execute the next ByteCode.
 *
 */
public class FalseBranchCode extends ByteCode {
    private String falseBranchCodeArg; // there is one arg for this bytecode
    private int gotoAddress;
    private String name="FalseBranch";

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        falseBranchCodeArg = byteCodeArgs.get(0); //only has 1 arg, the label id
    }
    
    /*Pop the top of the runStack, if zero, then branch to <label>, else exceute next bytecode*/
    @Override
    public void execute(VirtualMachine vm) {
        if(vm.pop() == 0) {
            vm.setPC(gotoAddress-1); //pc set to the address of <label>
        }
    }

    @Override
    public void setNumMemoryAddress(int address) {
        gotoAddress = address;
    }

    @Override
    public String getFirstArg() {
        return falseBranchCodeArg;
    }
    /* For dumping/printint out bytecode and its arg*/
    @Override
    public String toString() { 
        return "FALSEBRANCH " + falseBranchCodeArg;
    }


    @Override
    public String getName() {
        return name;
    }
}
