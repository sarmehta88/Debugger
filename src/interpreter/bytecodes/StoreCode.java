package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * STORE: pops the top of the stack and stores this value into the offset
 * of the current frame.
 *
 */
public class StoreCode extends ByteCode {
    private ArrayList<String> storeCodeArgs = new ArrayList<String>();
    private int storeVal;
    private String name="Store";

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        for(String bca: byteCodeArgs) { //Store has 2 args: ie. STORE 2 i
            storeCodeArgs.add(bca);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        int offset = Integer.parseInt(storeCodeArgs.get(0)); //offset n is from the start of current frame
        storeVal = vm.peek();
        vm.store(offset); //replaces the item at offset position with the item at top of stack
        
    }

    @Override
    public String toString() {
        return "STORE " + storeCodeArgs.get(0) + " " + storeCodeArgs.get(1) + "\t" + storeCodeArgs.get(1) + " = " + storeVal;
    }

    @Override
    public String getFirstArg() {
        return storeCodeArgs.get(0);
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //Store does not need this method
    }

    @Override
    public String getName() {
        return name;
    }
}
