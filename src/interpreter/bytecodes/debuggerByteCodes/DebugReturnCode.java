package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ReturnCode;
import interpreter.debugger.DebugVM;
import interpreter.debugger.ui.DebugUI;


/*
 * Removes all values except the Return Value from the runStack 
 * that is contained in the current FunctionEnv Record
 * Removes the current FunctionEnvRecord
 */

public class DebugReturnCode extends ReturnCode {
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm);
        DebugVM dvm = (DebugVM) vm;
        // if trace is on, print the exit function info after a Return bc
        if(dvm.isTrace()) {
            DebugUI.trace(true);
        }
        dvm.popFunction();
      
    }
}
