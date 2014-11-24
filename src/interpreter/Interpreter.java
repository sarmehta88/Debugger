package interpreter;

import java.io.*;
import interpreter.debugger.Debugger;

/**
 * Interpreter class initializes the CodeTable
 * Load the bytecodes from file
 * Run the virtual machine which executes each bytecode from a given file
 */
public class Interpreter {
    protected ByteCodeLoader bcl;

    public Interpreter(String codeFile) {
        if(codeFile.contains("debug")){
            CodeTable.debuginit();
        }else{
            CodeTable.init();
        }
        

        try {
            bcl = new ByteCodeLoader(codeFile);
           
        } catch (IOException e) {
            System.out.println("**** " + e);
        }
    }

    void run() throws IOException {
        Program program = bcl.loadCodes();
        VirtualMachine vm;

        vm = new VirtualMachine(program);
        vm.setIsRunning(true);
        vm.executeProgram();
    }

    public static void main(String args[]) {
        if(args.length == 0) {
            System.out.println("*** Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(1);
        }
       
       if (args[0].equals("-d")){
           // create instance of debugger
            Debugger debugger = new Debugger(args[1]); //pass file name without the extension
            try {
                debugger.run();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
       } else{
            Interpreter interpreter = new Interpreter(args[0]);

            try {
                interpreter.run();
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}

