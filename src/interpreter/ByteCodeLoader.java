package interpreter;
import interpreter.bytecodes.ByteCode;
import java.io.*;
import java.util.*;
/**
 * Reads in the next bytecode from the bytecodeFile
 * Creates an instance of the class corresponding to the bytecode string and add to Program
 * After all the bytecodes are loaded, the symbolic addresses are resolved.
 * 
 */
public class ByteCodeLoader {
    private BufferedReader byteCodeFile;
    private String currentLine;
    
    /* Non-Default Constructor: gets ready to read the bytecode file*/
    public ByteCodeLoader(String programFile) throws IOException {  
        byteCodeFile = new BufferedReader(new FileReader(programFile));     
    }
    
    /* Get instance of each bytecode and add it to Program*/
    public Program loadCodes() throws IOException{
        //Create an array list that stores the arguments of a bytecode
        ArrayList<String> byteCode_Args=new ArrayList<String>();
        //Instantiate a program class to load byteCodes from a file
        Program program= new Program();
        // Set currentLine to be the first line of the byteCode File
        currentLine=byteCodeFile.readLine();
        
        while(currentLine != null) { 
            StringTokenizer st = new StringTokenizer(currentLine);// set up a string tokenizer to go through the current line
            String codeString = st.nextToken(); //get first token from the currentLine ex. "HALT"

            try {
                // Get the class for the bytecode and then build a new instance 
                ByteCode bytecode = (ByteCode)(Class.forName("interpreter.bytecodes." + getCode(codeString)).newInstance());
                //check if there are args accompanied with the bytecode
                while(st.hasMoreTokens()) {
                    // add the nextToken which is an arg to the arrayList
                    byteCode_Args.add(st.nextToken());
                }
                
                
                   
                
                bytecode.init(byteCode_Args); // pass the arraylist of arguments to the bytecode object
                
                byteCode_Args.clear(); //empties the arrayList of arguments if there were args
                program.loadsByteCode(bytecode); //add bytecode objects to a program 
                
                
            }catch(Exception e) { //if instance of byteCode class cannot be created, print error and continue processing
                System.out.println("BCL" +e);
            }
            currentLine = byteCodeFile.readLine(); //get the next line from the file
            
        }
        program.resolveAddresses(); //at the end of loading all bytecode objects into program,resolve addresses of specific bytecode instances
        return program;
    }
    /* Get byteCode class name from CodeTable(HashMap) based on the bytecode string*/
    private String getCode(String codeString) {
        return CodeTable.get(codeString);
    }
    
}

   
        

        
   

    