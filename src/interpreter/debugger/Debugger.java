package interpreter.debugger;


import interpreter.*;
import interpreter.debugger.ui.DebugUI;
import java.io.IOException;
import java.util.ArrayList;

public class Debugger extends Interpreter {
    private ArrayList<SourceLine> sourceLinesTracker;
    //private ByteCodeLoader bcl;
    

    /**
     *
     * @param codeFile
     */
    public Debugger(final String codeFile) { //passes the file name without extension
        super(codeFile+".x.debug.cod");
        //Interpreter parent class initializes code table and creates instance of ByteCodeLoader
        try {
            sourceLinesTracker = LineReader.read(codeFile+".x"); //reads each line of code and create sourceLines objects
            System.out.println("****Debugging "+ codeFile+".x****");
            
        }
        catch(Exception e) {
            System.out.println("SourceCodeError:"+e);
        }
    }

    public void run() throws IOException {
        
        Program program = bcl.loadCodes(); //load the bytecodes
        DebugVM dvm = new DebugVM(program, sourceLinesTracker); //create the Debug Virtual Machine
        DebugUI.display(dvm); //Run the UI to get user's input
        
    }
}
