package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * ARGS n is used before calling a function.
 */
public class ArgsCode extends ByteCode {
    private int numOfArgs; 
    private String name= "Args";  
    
    //records the number of Arguments before a function call
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        numOfArgs = Integer.parseInt(byteCodeArgs.get(0));
    }
    //Starts a new frame for the function call
    @Override
    public void execute(VirtualMachine vm) {
        int offset = vm.getStackSize() - numOfArgs;
        //vm.setNumArgs(numArgs);
        vm.newFrameAt(offset); //set new frame pointer at the offset, will be used by vm.dump() to print frames
    }

    @Override
    public String toString() {
        return "ARGS " + numOfArgs;
    }

    @Override
    public String getFirstArg() {
        return Integer.toString(numOfArgs);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //ArgsCode does not need this method
    }
}
