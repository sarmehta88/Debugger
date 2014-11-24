package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * CALL transfers control to the indicated function.
 * ARGS bytecode is called before the Call bytecode
 */
public class CallCode extends ByteCode {
    private int gotoAddress;
    private String callCodeArg; //only has one arg
    private String bcName= "Call";
   

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        callCodeArg = byteCodeArgs.get(0);
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setReturnAddress(); //save Call's pc so that when functions ends, pc can go back to where it left off in program
        vm.setPC(gotoAddress-1); //sets new pc to the label addr where function is located
    }
    // Program.java sets the numerical(code) address of the label that CALL jumps to
    @Override
    public void setNumMemoryAddress(int address) {
        gotoAddress = address;
    }

  
    @Override
    public String getFirstArg() {
        return callCodeArg;
    }


    @Override
    public String getName() {
        return bcName;
    }
    /*Prints the details of the Call when dump is on*/
    @Override
    public String toString() {
        String res[]=callCodeArg.split("<<"); // split the call id so that the <base id> is retrieved only, ie. f<<2>>, res[0] will be f
        return "CALL " + callCodeArg+ "\t"+res[0]+"(";
    }

   
}
