package interpreter;

import interpreter.bytecodes.ByteCode;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * Holds the ByteCodes from ByteCodeLoader and resolves <Label> addresses
 */
public class Program {
    private ArrayList<ByteCode> byteCodeList;
    public ArrayList<Integer> legalBreakPoints; //stores the Line bc args
    private HashMap<String, Integer> recordAddress;
    private int counter = 0; //tracks the numeric memory address of the Label bytecodes
    
    /* Primary Constructor: initializes*/
    public Program() {
        byteCodeList = new ArrayList<ByteCode>();
        legalBreakPoints = new ArrayList<Integer>();
        recordAddress = new HashMap<String, Integer>(); //key is byteCode string, value is address of the Label in program's ArrayList
    }

    public void loadsByteCode(ByteCode bytecode) {
        
        byteCodeList.add(bytecode); //add byteCode to program ArrayList
    
        if(bytecode.getName().equals("Label")) { 
            //Get arg of LABEL bytecode and put in HashMap along with address at which 
            //label is located in byteCodeList
            recordAddress.put(bytecode.getFirstArg(), counter); 
        }
        //Breakpoint are set when LINE bc is read, store the LINE arg, which is the line number
        if(bytecode.getName().equals("Line") && Integer.parseInt(bytecode.getFirstArg()) > 0) {
            legalBreakPoints.add(Integer.parseInt(bytecode.getFirstArg()));
        }
        counter++; //increment current index of the byteCodeList
    }
/* Convert any symbolic addresses to their address in code memory(position in byteCodeList)*/
    public void resolveAddresses() {
        for(ByteCode bc: byteCodeList) { //find the FalseBranch,Goto, Call bytecode loaded in the program
            if(bc.getName().equals("FalseBranch") || bc.getName().equals("Goto") || bc.getName().equals("Call")) {
                //set the symbolic address to be address in code memory by getting the index of the branch Label in recordAddress 
                bc.setNumMemoryAddress(recordAddress.get(bc.getFirstArg())); //get and set code memory address for FalseBranch, Goto, Call bcs
            }
        }
    }
    /* 
     * Return the ByteCode object at a certain index,memory address in the Program. 
     * VM uses this method to set the pc of each bytecode
     */
    public ByteCode getByteCode(int index) {
        return byteCodeList.get(index);
    }
    
    
}