package interpreter.bytecodes;

import interpreter.VirtualMachine;


import java.util.*;

/*
 * WRITE prints the value on top of the stack 
 *
 */
public class WriteCode extends ByteCode {
    private String name="Write";
    
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        //no args
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.println(vm.peek()); //outputs the last item on runstack
        
    }

    @Override
    public String toString() {
        return "WRITE";
    }

    @Override
    public String getFirstArg() {
        return "";
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //WRITE doesn't use this
    }

    @Override
    public String getName() {
        return name;
    }
}
