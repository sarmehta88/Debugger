package interpreter.debugger.ui;

import interpreter.debugger.DebugVM;
import interpreter.debugger.FunctionEnvRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;


public class DebugUI {
    
    public static DebugVM dvm;
    

    public static void display(DebugVM debug) {
        dvm = debug;
        
        // First show the user the List of Debug Commands
        System.out.println("Enter ? for list of Debug commands");
        //Print the sourceCode for user to insert breakpoints
         displayCurrFunction();

        while(!dvm.isQuit() && !dvm.isRunning()) { //vm is off, control goes to UI
            
            
            //Store the command args 
            ArrayList<Integer> commandArgs = new ArrayList<Integer>();
         
            try {
                System.out.println("\nEnter a command, then press Enter");
                System.out.print(">> ");
                //Get the user's command
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String comInfo=input.readLine().toLowerCase();
                
                // Separate the command Name with the command Args
                StringTokenizer commandLine = new StringTokenizer(comInfo);
                String commandName = commandLine.nextToken();
                while(commandLine.hasMoreTokens()) {
                        commandArgs.add(Integer.parseInt(commandLine.nextToken())); //add args to ArrayList
                    }
                
                switch(commandName){
                    case "?":     help();
                                    
                                  break;
                    case "sbrk":  setBreakPoints(commandArgs);
                                  break;
                    case "cbrk":  clearBreakPoints(commandArgs);
                                  break;
                    case "lbrk":  listBreakPoints();
                                  break;
                    case "dispf": displayCurrFunction();
                                  break;
                    case "disps": displayCallStack();
                                  break;
                    case "cont": continueExec();
                                  //Print the sourceCode for user to insert breakpoints
                                  displayCurrFunction();
                                  break;
                    case "over":  stepOver();
                                  displayCurrFunction();
                                  break;
                    case "out":   stepOut();
                                  displayCurrFunction();
                                  break;
                    case "in":    stepIn();
                                  displayCurrFunction();
                                  break; 
                    case "ton":  dvm.setTrace(true);
                                  break;
                    case "toff": dvm.setTrace(false);
                                  break;
                    case "quit":  quit();
                                  break;
                    case "dispv": displayVar();
                                  break;
                    default: System.out.println("Invalid Command!!");      
                }
                         
            }catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static void continueExec(){
        dvm.setIsRunning(true); //turn DVM on
        dvm.executeProgram();
    }
    
    public static void quit(){
        dvm.setIsRunning(false);//turn vm off
        dvm.setIsQuit(true); //turn UI off
        System.out.println("****Execution Halted****");
    }
    
    public static void help(){
        System.out.println ("List of Debugger Commands:\n" +
        "Command symbol \t\t command description\n"+"? \t\t\t Displays list of Debug commands\n"+
        "sbrk line#  \t\t Sets the breakpoint(s), separate line# with spaces\n"+
        "cbrk line#  \t\t Clears the breakpoint(s), separate line# with spaces OR no line# to clear all\n"+
        "lbrk \t\t\t List the breakpoints\n"+ 
        "out \t\t\t Steps out of the function\n"+
        "in \t\t\t Steps into the function\n"+
        "over \t\t\t Steps over of the function\n"+
        "ton \t\t\t Trace is on\n"+
        "toff \t\t\t Trace is off\n"+ 
        "dispf \t\t\t Displays the current function\n"+
        "disps \t\t\t Displays the call stack\n"+
        "dispv \t\t\t Displays the variables\n"+
        "cont \t\t\t Continue Execution\n"+
        "quit \t\t\t Quits Execution\n");
    }
    /* Step over the next line, but does not step into any function on the line*/
    public static void stepOver(){
        
        dvm.setIsRunning(true); //first turn VM on
        dvm.setStepStatus("over");// tell dvm to step over
        dvm.executeProgram(); //start executing bytecodes
        dvm.setStepStatus(""); //clear the step status 
    }
    
    /* Steps out of the current function; break when current entry is popped*/
    public static void stepOut(){
        
        dvm.setIsRunning(true); //first turn VM on
        dvm.setStepStatus("out");// tell dvm to step over
        dvm.executeProgram(); //start executing bytecodes
        dvm.setStepStatus(""); //clear the step status 
       
    }
    /* Step into the function on the current line*/
    public static void stepIn(){
        
        dvm.setIsRunning(true); //first turn VM on
        dvm.setStepStatus("in");// tell dvm to step over
        dvm.executeProgram(); //start executing bytecodes
        dvm.setStepStatus(""); //clear the step status 
       
    }
    
    public static void setBreakPoints(ArrayList<Integer> args){
            
            for(int lineNum: args) {
                //Check the boundaries of the line numbers to be set as breakpoints
                if(lineNum <= dvm.getSourceCodeSize() && lineNum > 0) {
                    
                   // if breakpoint can be set at the line number, then set bp to true
                    if(dvm.isLegalBreakPoint(lineNum)) {
                        dvm.setBreakPoint(lineNum, true);
                        System.out.println("Set breakpoint at line: "+ lineNum);
                    }
                    else {
                        System.out.println("Error: Can't set breakpoint at line " + lineNum );
                    }
                }else {
                    System.out.println("Error: Line Number: " + lineNum + " does not exist.");
                }
            }
        
        if(args.isEmpty()) {
            System.out.println("BREAKPOINT ERROR: No Line Numbers were entered");
        }
    }
            
      public static void clearBreakPoints(ArrayList<Integer> args){
          //If no arguments, then clear all breakpoints
          if(args.isEmpty()) {
            for(int i =1; i < dvm.getSourceCodeSize(); i++) {
                dvm.setBreakPoint(i,false);
               
            }
            System.out.println("Clearing all breakpoints");
          }else {
            for(int lineNumber: args) {
                if(dvm.isBreakPointSet(lineNumber)){
                    dvm.setBreakPoint(lineNumber,false);
                    System.out.println("Clear breakpoint at line: "+ lineNumber);
                }else{
                    System.out.println("No breakpoint was set at line "+ lineNumber);
                }
                
            }
        }
       // printSourceLines();
    }
           
    public static void displayCurrFunction() {
        int start = dvm.getStartLine();
        int end = dvm.getEndLine();
        int current = dvm.getCurrentLine();
        String functionName = dvm.getFunctionName();
        System.out.println();
        // Dont print current function if it Intrinsic
        if(start > 0 && end > 0 && current > 0) {
            System.out.println("Current function: "+functionName);

            for(int lineNumber = start; lineNumber <= end; lineNumber++) {
                if(dvm.isBreakPointSet(lineNumber)) {
                    System.out.print("* ");
                }
                else {
                    System.out.print("  ");
                }
    
                System.out.print(lineNumber + ".) "+ dvm.getSourceCodeLine(lineNumber));
                //Print an arrow on line that is about to be executed by debugger
                if(lineNumber == current) {
                    
                    System.out.print(" <----");
                }
               
                System.out.print("\n");
            } 
        } //For Intrinsic Function: ****FuncName****
        else if(start < 0 && end < 0) {
            System.out.println("***** " + functionName + " *****");
        }else { //before entering a function, print the whole program with arrow at current line to be executed
            
            System.out.println("About to enter a function");

            for(int lineNumber = 1; lineNumber < dvm.getSourceCodeSize(); lineNumber++) {
                if(dvm.isBreakPointSet(lineNumber)) {
                    System.out.print("* ");
                }
                else {
                    System.out.print("  ");
                }

                System.out.print(lineNumber + ".) " + dvm.getSourceCodeLine(lineNumber));

                if(lineNumber == current) {
                    System.out.print(" <----");
                }

                System.out.print("\n");
            
            }
        }
    }
    
    public static void displayCallStack(){
        //make a copy of the env stack to print out without modifying the original
        Stack<FunctionEnvRecord> envCopy = (Stack<FunctionEnvRecord>) dvm.getEnvStack().clone();
       
        //print from the most current function on top to the least current function on bottom along with the line number
        for(int i = 0; i < dvm.currentEnvSize(); i++) {
            System.out.println(envCopy.peek().getName() + ": " + envCopy.peek().getCurrentLine());
            envCopy.pop();
        }
    }
    public static void displayVar() {
        if(!dvm.getVariables().isEmpty()) { //if Set of vars in current function is not empty
            for(String varName: dvm.getVariables()) { //get value of variable from runStack
                System.out.println(varName + " = " + dvm.getElementAt(dvm.getOffset(varName)));
            }
        }
        else {
            System.out.println("No variables initialized in current function.");
        }
    }
/* Trace is called by the Function or Return bytecode if user sets trace status to ON*/
     public static void trace(boolean isReturn) {
        //Print out the spaces before the function info(2*n) spaces for n levels in the environment stack
        for(int n = 1; n < dvm.currentEnvSize(); n++) {
            System.out.print("  ");
        }
        //if dvm executes a RETURN, then print the exit info of the current function the dvm is executing
        if(isReturn) {
           //print the current function name and the top value of runstack
             System.out.print("exit: " + dvm.getFunctionName() + ": " + dvm.peek());
        }
        //if dvm executes a FUNCTION, print the function name and the args in parenthesis
        else {
            String args = "";
            //Store the first arg in the string if there is one
            if(dvm.peekFramePointer()<dvm.getStackSize()){
                args += dvm.getElementAt(dvm.peekFramePointer());
            }
            //collect all the other arg after a function call: from the framePointer index+1 to end of runstack
            for(int argNo = dvm.peekFramePointer()+1; argNo < dvm.getStackSize(); argNo++) {
                args += ","; //separate args by comma
                args += dvm.getElementAt(argNo);

            }

            System.out.print(dvm.getFunctionName() + "(" + args + ")");
        }

        System.out.println();
    }
     
    public static void listBreakPoints() {
        
        for(int lineNumber = 1; lineNumber <dvm.getSourceCodeSize(); lineNumber++) {
            if(dvm.isBreakPointSet(lineNumber)) { //check each line for a breakpoint
                System.out.println("Breakpoint set on line: " + lineNumber);
            }
        }
    }
}
