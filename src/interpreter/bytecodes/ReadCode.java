package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * Reads an integer; prompt the user for input and put the
 * the value just read on top of the stack.
 *
 */
public class ReadCode extends ByteCode {
    private String name="Read";
    private int input;
    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        //READ doesn't have any args
    }

    @Override
    public void execute(VirtualMachine vm) {
        System.out.print("Enter an Integer "); //Prompt user to enter an Integer
        try{
            Scanner sc=new Scanner(System.in);
            input=sc.nextInt();
           System.out.print(input+"\n");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        vm.push(input); //push number onto runstack
    }

    @Override
    public String toString() {
        return "READ";
    }

    @Override
    public String getFirstArg() {
        return "";
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //READ doesn't use this
    }

    @Override
    public String getName() {
        return name;
    }
}
