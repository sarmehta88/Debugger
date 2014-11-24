package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * RETURN: exits from the current function and goes back to the saved pc address
 *
 */
public class ReturnCode extends ByteCode {
    private String returnCodeArg= new String();
    private String bcName="Return";
    private int returnVal;

    //ReturnCode Can have 0 or 1 args
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        if(!byteCodeArgs.isEmpty()) {
            returnCodeArg = byteCodeArgs.get(0);
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setPC(vm.getReturnAddress()); //Set the pc to address of the last CALL function
        vm.popFrame(); //removes all items in current frame except return value  
        returnVal=vm.peek();//get the ReturnValue from the top of the stack
    }
    /* Prints information when dump is on*/
    @Override
    public String toString() {
        //extract the label in the return arg ie RETURN f<<2>>
        String res[]=returnCodeArg.split("<<"); // split the return id so that the base id is retrieved only
        
        if(returnCodeArg.isEmpty()) {
            return "RETURN";
        }
        else {
            return "RETURN " + returnCodeArg+ "\texit "+ res[0]+": "+returnVal;
        }
    }
    
    @Override
    public String getFirstArg() {
        return returnCodeArg;
    }

    @Override
    public String getName() {
        return bcName;
    }

    @Override
    public void setNumMemoryAddress(int address) {
       //Return bytecode does not use this method
    }
}

