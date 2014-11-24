package interpreter.debugger;

import interpreter.Program;
import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public class DebugVM extends VirtualMachine {
    private Stack<FunctionEnvRecord> environmentStack;
    private ArrayList<SourceLine> sourceLinesTracker;
    private boolean isQuit= false;
    private String stepMethod="";
    private int envSize=0;
    private boolean trace=false;
    
   
    

    public DebugVM(Program program, ArrayList<SourceLine> sourceLinesArray) {
        super(program); //call VM constructor to initialize datafields, and program
        
        environmentStack = new Stack<FunctionEnvRecord>();
        sourceLinesTracker = sourceLinesArray; //stores each sourcecode line
        isRunning = false; //inherited from VM
        pc = 0; //inherited from VM
        //envSize = environmentStack.size();
        
        //push a dummy environment stack to start displaying source Code from beginning
        environmentStack.push(new FunctionEnvRecord());
        
        
        
    }

    @Override
    public void executeProgram() {
        // Get the environment stack size before CALL bytecode adds a new env record
        envSize = environmentStack.size();
        //if DVM off, control back to UI
        while(isRunning) {
            ByteCode code = program.getByteCode(pc);
            code.execute(this);
            if(code.getName().equals("Args")){
                //if Args bytecode is executed, save the items in the current frame, which are args for the function call
                currFrame=getCurrentFrameVals();
            }
            
            // check if the current bytecode is Halt, if it is, end program immediately
            if(code.getName().equals("Halt")){
                isQuit=true;
                System.exit(0);
                //break;
            }
            //Complete the step function if UI sets it
            if(stepMethod.equals("over")||stepMethod.equals("out")||stepMethod.equals("in")){
                stepFunctions();
            }
           
           //To display the current function properly, check if Function and Formal code
            //come after Line Code and get their info to print out
            if(getNextByteCodeName().equals("Function")){
                
                pc++;
                code=program.getByteCode(pc);
                code.execute(this);
            }
            if(getNextByteCodeName().equals("Formal")){
                
                pc++;
                code=program.getByteCode(pc);
                code.execute(this);
            }
            
            pc++;
            
        }
            
            
    }

   
    public String getByteCodeName() {
        return program.getByteCode(pc).getName();
    }

    public String getNextByteCodeName() {
        return program.getByteCode(pc+1).getName();
    }
    
    /* Create and Add a new FunctionEncRecord to the environmentStack*/
    public void beginFuncScope() {
        FunctionEnvRecord function = new FunctionEnvRecord(); //beginScope of function
        environmentStack.push(function); 
    }
    
    /* Get current functionEnvRecord and enter variable name and offset
     * Offset refers to position in runtimeStack
     */
    public void storeVarOffset(String var, int offset) {
        environmentStack.peek().enter(var, offset); 
    }

    /* Set the function name, start and end line number in the current FunctionEnv Record*/
    public void setFunctionParams(String funcName, int startLine, int endLine) {
        environmentStack.peek().setName(funcName);
        environmentStack.peek().setStartLine(startLine);
        environmentStack.peek().setEndLine(endLine);
    }
    /*Set the current Line Number in the most current function environment record*/
    public void setCurrentLine(int lineNum) {
        environmentStack.peek().setCurrentLine(lineNum);
    }
    /* Removes n Symbols(var name, offset) from the current functionEnvRecord*/
    public void popSymbol(int n) {
        environmentStack.peek().pop(n);
    }
    /* Remove the most current functionEnv Record*/
    public void popFunction() {
        environmentStack.pop();
    }
    /* Returns the source code at a line number*/
    public String getSourceCodeLine(int line) {
        return sourceLinesTracker.get(line).getSourceLine();
    }

    /* Returns the number of lines of source code*/
    public int getSourceCodeSize() {
        return sourceLinesTracker.size();
    }
    /* Checks the program if the LINE bytecode was read at a source code's line number*/
    public boolean isLegalBreakPoint(int lineNum) {
        return program.legalBreakPoints.contains(lineNum);
    }
    /* Set Break Point at a line number*/
    public void setBreakPoint(int line, boolean status) {
        if(status){
            sourceLinesTracker.get(line).setBreakPoint(true);
        }else{
            sourceLinesTracker.get(line).setBreakPoint(false);
        }
    }
    /*Check is there is a break point at a line number*/
    public boolean isBreakPointSet(int line) {
        if(line>getSourceCodeSize() || line<0){
            System.out.println("Not a valid line number");
            return false;
        }else{
            return sourceLinesTracker.get(line).isBreakPointSet();
            
        }
    }
    
    public String getFunctionName() {
        return environmentStack.peek().getName();
    }

    public int getStartLine() {
        return environmentStack.peek().getStartLine();
    }

    public int getEndLine() {
        return environmentStack.peek().getEndLine();
    }

    public int getCurrentLine() {
        return environmentStack.peek().getCurrentLine();
    }
    /*Returns a Set of variables contained in the current functionEnvRecord*/
    public Set<String> getVariables() {
        return environmentStack.peek().getVariables();
    }
    
    
   /*Calculates and returns the offset of the variable stored in the runtimestack*/
    public int getOffset(String varName) {
        return environmentStack.peek().getVariableOffset(varName) + peekFramePointer();
    }
    /* SETS UI ON or OFF*/
    public void setIsQuit(boolean quit) {
        isQuit = quit;
    }
    /* tells whether UI is on or off*/
    public boolean isQuit() {
        return isQuit;
    }
    /* Sets the step method as over,in, or out*/
    public void setStepStatus(String status){
        stepMethod=status;
    }
    /* Stepping over,out, or in a function sets the DVM off and returns control back to UI*/
    public void stepFunctions(){
        //  For Step Over: stop excuting bcs if 1. dvm executed a LINE bc without entering a function
        // 2. dvm executed a RETURN bc 3. dvm just popped a function( Envsize decreased)
        if(stepMethod.equals("over") && ((getByteCodeName().equals("Line") && envSize == currentEnvSize()) || envSize > currentEnvSize())) {
           isRunning = false;
        }
        //For Step out of current function: return to UI and displayCurrent function 
        //when current entry is popped(after a return statement)
        // Cannot step out of intrinsic function
        else if(stepMethod.equals("out") && (envSize > currentEnvSize() && getCurrentLine() > 0)) {
            isRunning = false;
        }   
        //For Step in current function: return to UI and displayCurrent function
        // when 1. hit a new line or 2. push a new entry in the environment stack
        else if(stepMethod.equals("in") && (getByteCodeName().equals("Line") || envSize < currentEnvSize())){
            //if execute Call, then keep executing bcs till you hit a Function bc to displayCurrent Function properly
           if(envSize<currentEnvSize()){
               while(!getByteCodeName().equals("Function")){
                pc++;
                ByteCode bc= program.getByteCode(pc);
                bc.execute(this);
                
                }
           }
            isRunning=false;
        }
       
    }
    
    /* Returns the number of function environment records*/
    public int currentEnvSize() {
        return environmentStack.size();
    }

    public void setTrace(boolean status){
        trace=status;
    }
     
    public boolean isTrace(){
        return trace;
    }
    /*Returns the environement Stack of all the functions*/
    public Stack<FunctionEnvRecord> getEnvStack() {
        return environmentStack;
    }

}
