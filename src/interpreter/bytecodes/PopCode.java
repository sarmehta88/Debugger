package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;


/*
 * POP removes n levels of the runtime stack.
 *
 */
public class PopCode extends ByteCode {
    protected int popCodeArg=-1; 
    private String name="Pop";

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        popCodeArg = Integer.parseInt(byteCodeArgs.get(0)); //POP has 1 arg
        
    }

    @Override
    public void execute(VirtualMachine vm) {
        for(int i = 0; i < popCodeArg; i++) {
            vm.pop(); //pop n levels of runstack
        }
    }

    @Override
    public String toString() {
        return "POP " + popCodeArg;
    }

    @Override
    public String getFirstArg() {
        return ""; //POP doesn't use this
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //POP doesn't use this
    }

    @Override
    public String getName() {
        return name;
    }
}
