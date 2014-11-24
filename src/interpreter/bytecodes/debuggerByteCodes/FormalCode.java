package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVM;
import java.util.ArrayList;

public class FormalCode extends ByteCode {
    private String id= "";
    private int offset=0;
    private String name= "Formal";

    @Override
    public void init(ArrayList<String> formalCodeArgs) {
        id = formalCodeArgs.get(0);
        offset = Integer.parseInt(formalCodeArgs.get(1));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebugVM dvm = (DebugVM) vm;

        dvm.storeVarOffset(id, offset); //offset in new frame
    }

    @Override
    public String toString() {
        return "FORMAL " + id + " " + offset;
    }

    @Override
    public String getFirstArg() {
        return id;
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //this method doesn't use this
    }

    @Override
    public String getName() {
        return name;
    }
}
