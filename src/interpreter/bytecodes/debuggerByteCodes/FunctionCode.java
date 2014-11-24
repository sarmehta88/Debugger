package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVM;
import interpreter.debugger.ui.DebugUI;
import java.util.ArrayList;

public class FunctionCode extends ByteCode {
    String functionName="";
    int startLine=-2;
    int endLine=-2;
    String name="Function";

    @Override
    public void init(ArrayList<String> functionCodeArgs) {
        functionName = functionCodeArgs.get(0);
        startLine = Integer.parseInt(functionCodeArgs.get(1));
        endLine = Integer.parseInt(functionCodeArgs.get(2));
    }

    @Override
    public void execute(VirtualMachine vm) {
        DebugVM dvm = (DebugVM) vm;

        dvm.setFunctionParams(functionName, startLine, endLine); // Tell DVM to store the current function name, start, end line number 
        // if trace is on, print the entry function info after a Function bc is executed
        if(dvm.isTrace()) {
            DebugUI.trace(false);
        }
    }

    @Override
    public String toString() {
        return "FUNCTION " + functionName + " " + startLine + " " + endLine;
    }

    @Override
    public String getFirstArg() {
        return functionName;
    }

    @Override
    public void setNumMemoryAddress(int address) {
        // do not use this method
    }

    @Override
    public String getName() {
        return name;
    }
}
