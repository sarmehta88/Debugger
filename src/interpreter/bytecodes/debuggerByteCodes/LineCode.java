package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVM;
import java.util.ArrayList;

public class LineCode extends ByteCode {
    int lineNumber=-2;
    private String name="Line";

    @Override
    public void init(ArrayList<String> lineCodeArgs) {
        lineNumber = Integer.parseInt(lineCodeArgs.get(0));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebugVM dvm = (DebugVM) vm;

        dvm.setCurrentLine(lineNumber);
        
        //if there is a breakpoint turn off/pause the DebuggerVM
        //in order to give control back to DebugUI
        if(lineNumber > 0) {
            if(dvm.isBreakPointSet(lineNumber)) {
                dvm.setIsRunning(false); 
            }
        }
    }

    @Override
    public String toString() {
        return "LINE " + lineNumber;
    }

    @Override
    public String getFirstArg() {
        return Integer.toString(lineNumber);
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //do not use this method
    }

    @Override
    public String getName() {
        return name;
    }
}
