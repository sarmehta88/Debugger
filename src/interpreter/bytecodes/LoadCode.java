package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * LOAD pushes the value located at the offset of the runstack to
 * top of the stack in order to initialize the variable; ie LOAD 3 j 
 *
 */
public class LoadCode extends ByteCode {
    private String name="Load";
    private ArrayList<String> loadCodeArgs = new ArrayList<String>();

    /*Store the 2 args for LOAD bytecode in arrayList*/
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        for(String bca: byteCodeArgs) {
            loadCodeArgs.add(bca);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        int offset = Integer.parseInt(getFirstArg()); //offset is the first arg
        vm.load(offset); //pushes offset value to the top of the stack
    }

    @Override
    public String toString() {
        return "LOAD " + loadCodeArgs.get(0) + " " + loadCodeArgs.get(1) + "\t<load " + loadCodeArgs.get(1)+">";
    }
    
    @Override
    public String getFirstArg() {
        return loadCodeArgs.get(0);
    }

    @Override
    public void setNumMemoryAddress(int address) {
        
    }

    @Override
    public String getName() {
        return name;
    }
}

