package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * DUMP is used by Virtual Machine to print out the bytecode information to the console
 */
public class DumpCode extends ByteCode {
    private String dumpArg= "OFF"; //default DUMP is off until VM sends request to turn it on
    private String name= "Dump";

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        dumpArg = byteCodeArgs.get(0); //arg can be either "ON" or "OFF"
    }

    @Override
    public void execute(VirtualMachine vm) {
        if(dumpArg.equals("ON")) {
            vm.setDumpStatus(true); //Set VM dump ON
        }
        else {
            vm.setDumpStatus(false);// Turn OFF VM dump
        }
    }

    @Override
    public String getFirstArg() {
        return dumpArg;
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //DUMP does not use this method
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return ""; //DUMP bytecode is not printed when Dump is on
        
    }
}
