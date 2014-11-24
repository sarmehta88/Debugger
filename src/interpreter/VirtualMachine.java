package interpreter;

import interpreter.bytecodes.ByteCode;
import java.util.*;

/*
 * VirtualMachine calls the processes of the RunTimeStack 
 * Calls each bytecode to executes its instructions thru the VM, which handles the Runstack and FramePointers
 *
 */
public class VirtualMachine {
    protected RunTimeStack runStack = new RunTimeStack();
    protected int pc = 0; //the first bytecode is at pc 0 
    private Stack<Integer> returnAddress = new Stack(); //used to store PC after a function ends
    protected boolean isRunning = false;
    protected Program program;
    private boolean dumpStatus = false;
    protected int[] currFrame;

    public VirtualMachine(Program program) {
        this.program = program; //program loaded to VM
        
    }
    /*Executes the bytecode instructions*/
    public void executeProgram() {
         
        while(isRunning) {
            ByteCode code = program.getByteCode(pc); //pc similar to line number in program
            
            code.execute(this); //execute the bytecode, pass vm to bytecode
            
            if(code.getName().equals("Args")){
                //if Args bytecode is executed, save the items in the current frame, which are args for the function call
                currFrame=getCurrentFrameVals();
            }
            
            //if dump is on after Dump bytecode has been read, then print bc and dump the stack
            if(dumpStatus == true && !code.getName().equals("Dump")&& !code.getName().equals("Halt")) {
                System.out.print(code); //Call the bytecode toString and print any declarations, ie int m
                if(code.getName().equals("Call")){ //For the Call bytecode, print the rest of the Call's <baseid>
                //print the rest of the Call declaration ie.f(2,3)- the current frame if currFrame is not Empty
                    if(currFrame.length>0){
                        System.out.print(currFrame[0]);
                        for(int i=1;i<currFrame.length;i++){
                            System.out.print(","+currFrame[i]);
                        }
                    }
                    System.out.print(")"); //end the currentFrame
                    
                }
                System.out.println();
                runStack.dump();
                System.out.println(); 
            }
            pc++;
        }
    }
    public int peek() {
        return runStack.peek();
    }

    public int pop() {
        return runStack.pop();
    }

    public int push(int i) {
        return runStack.push(i);
    }

    public int peekFramePointer() {
        return runStack.peekFramePointer();
    }
    public void newFrameAt(int offset) {
        runStack.newFrameAt(offset);
    }

    public void popFrame() {
        runStack.popFrame();
    }

    public int store(int offset) {
        return runStack.store(offset);
    }

    public int load(int offset) {
        return runStack.load(offset);
    }

    public int getStackSize() {
        return runStack.size();
    }

    public void setReturnAddress() {
        returnAddress.push(pc);
    }

    public int getReturnAddress() {
        return returnAddress.pop();
    }

    public void setPC(int pc) {
        this.pc = pc;
    }

    public int getPC() {
        return pc;
    }

    public void setDumpStatus(boolean dumpStatus) {
        this.dumpStatus = dumpStatus;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public int getElementAt(int element) {
        return runStack.getElementAt(element);
    }
    
    public int[] getCurrentFrameVals(){
        return runStack.getCurrentFrameVals();
    }

    
}