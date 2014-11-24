package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.CallCode;
import interpreter.debugger.DebugVM;

public class DebugCallCode extends CallCode {
    
    /* Create a new function scope*/
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm); //make pc jump to Call's label id address and save return address
        DebugVM dvm = (DebugVM) vm; 

        dvm.beginFuncScope(); 
    }
}
