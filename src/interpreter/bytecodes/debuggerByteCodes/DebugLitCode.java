package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.LitCode;
import interpreter.debugger.DebugVM;

public class DebugLitCode extends LitCode {
    /* Tell DVM to enter the variable and offset into the functionEnvRecord*/
    @Override
    public void execute(VirtualMachine vm) {
        super.execute(vm); //push the value of LIT into runStack, store variable name if there is one
        DebugVM dvm = (DebugVM) vm;

        if(!var.equals("")) {
            dvm.storeVarOffset(var, dvm.getStackSize()-1); //offset is current size of runstack-1
        }
    }
}
