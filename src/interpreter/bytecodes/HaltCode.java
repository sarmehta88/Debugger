package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;
//import interpreter.debugger.ui.DebugUI;

/*
 * HALT: stops the execution of the program.
 *
 */

public class HaltCode extends ByteCode {
    private String name= "Halt";
    
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        //HALT does not have any args
        
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setIsRunning(false); //Tell VM to stop executing more bytecodes
        System.out.println("****Execution Halted****");
    }

    @Override
    public String toString() {
        return "HALT";
    }

    @Override
    public String getFirstArg() {
        return "";
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //HALT doesn't use this
    }

    @Override
    public String getName() {
        return name;
    }
}
