
package interpreter.bytecodes;
import interpreter.VirtualMachine;
import java.util.*;
/**
 *
 * Parent Abstract class that all the concrete ByteCodes inherit
 */
public abstract class ByteCode {
    
    /* ByteCodeLoader.java stores arguments of the bytecode in an ArrayList*/
    public abstract void  init(ArrayList<String> argsList);
    
    /* Executes the specific instructions of the bytecode in the VM*/
    public abstract void execute(VirtualMachine vm);
    
    /* Return the first arg of the bytecode, if none return null */
    public abstract String getFirstArg();
    
    /* FalseBranch,Goto, Call bytecodes need to record numerical(code)address of their respective labels */
    public abstract void setNumMemoryAddress(int address);
    
    /* Returns the name of the bytecode string*/
    public abstract String getName();
    
    /* Returns the bytecode information that is printed when DUMP is ON*/
    @Override
    public abstract String toString(); //Overrides Object.toString()
    
   
}


