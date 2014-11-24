package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.PopCode;
import interpreter.debugger.DebugVM;

public class DebugPopCode extends PopCode {
    /* removes n values from runtimeStack and n Symbols from current functionEnvRecord*/
    
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        DebugVM dvm = (DebugVM) vm;

        dvm.popSymbol(popCodeArg);
    }
}
