package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * GOTO jumps to a LABEL.
 * Records the Memory address of the Label where PC has to point to
 */
public class GotoCode extends ByteCode {
    private String gotoCodeArg;
    private int gotoAddress;
    private String name="Goto";

    //Only 1 arg: the Label <id>
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        gotoCodeArg = byteCodeArgs.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setPC(gotoAddress-1);//set pc to be right before the address of <label>
    }
    //Program.java stores the address of the label
    @Override
    public void setNumMemoryAddress(int address) {
        gotoAddress = address;
    }

    @Override
    public String getFirstArg() {
        return gotoCodeArg;
    }

    @Override
    public String toString() {
        return "GOTO " + gotoCodeArg;
    }

     @Override
    public String getName() {
        return name;
    }
}
