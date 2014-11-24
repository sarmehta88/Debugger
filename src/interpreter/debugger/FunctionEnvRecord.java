package interpreter.debugger;

import java.util.Iterator;
import java.util.Set;


/**
 * Contains information for each frame of the environment stack
 * Stores variables and their offset into a Symbol Table
 */
public class FunctionEnvRecord {

    private Table table;
    private int startLine=0;
    private int endLine=0;
    private int currentLine=0;
    private String name="-";//name has not been initialized by Function bytecode

    /*
     * Creates a new function record
     * beginScope() creates a new HashMap to store variables and links to their offsets
     */
    public FunctionEnvRecord() {
        table = new Table();
        table.beginScope();
    }

    /*
     * Enters a variable and its offest into the Table HashMap
     * @param id The variable's name
     * @param offset The offset of the variable in the runtime stack
     */
    public void enter(String id, int offset) {
        table.put(id, offset);     
    }
    
    /*
     * Sets the line at which the function starts
     */
    public void setStartLine(int line) {
        startLine = line;
    }

    /*
     * Sets the line at which the function ends
     */
    public void setEndLine(int line) {
        endLine = line;
    }

    /*
     * Sets the line at which the function is currently executing
     */
    public void setCurrentLine(int line) {
        currentLine = line;
    }

    /*
     * Sets the name of the function
     */
    public void setName(String funcName) {
        name = funcName;
    }

    /*
     * Pops the given number of entries from the record
     * Used when ending a scope
     */
    public void pop(int numOfPops) {
        table.popValues(numOfPops);           
    }
    
    /*
     * Returns the name of the function
     */
    public String getName() {
        return name;
    }

    /*
     * Returns the line number at which the function starts
     */
    public int getStartLine() {
        return startLine;
    }

    /*
     * Returns the line number at which the function ends
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * Returns the line that is currently being executed in the function
     */
    public int getCurrentLine() {
        return currentLine;
    }

    /*
     * Returns a set of the variable names stored in the function record
     * 
     */
    public Set<String> getVariables() {
        return table.keys();
    }

    /*
     * Returns the offset (in the runtime stack) at which the given variable is stored
     * Offest is the most recently entered ie. Enter("a",3) and then Enter ("a",5)
     * Method will return 5
     */
    public int getVariableOffset(String var) {
        return (Integer)table.get(var);
    }
    
    /*
     * Prints information about the whole stack 
     * (<stack var/offeset>,functionName,startLine,endLine,currentLine)
     */
    public void print(){
        System.out.print("(");
        //print all the items in symbols hashmap
        System.out.print("<");
        Iterator it3 = table.keys().iterator();
        while (it3.hasNext()) {
            Object key=it3.next();
            Object value= table.get(key.toString());
            System.out.print(key.toString() +"/"+value);
            if(it3.hasNext()){
                 System.out.print(",");
             }
        }    
        System.out.print(">,");
        //Print function Name,StartLine,EndLine,CurrentLine
        //If Function bytecode is not read, then print name, startLine,endLine as "-" to mean null
        if(name.equals("-")){
            System.out.print("-,-,-");
        }else{
            System.out.print(name+","+startLine+","+endLine+",");
        }
        //If Line bytecode is not read, then print name, currentLine as "-" to mean null
        if(currentLine==0){
            System.out.print("-)\n");
        }else{
            System.out.print(currentLine+")\n");
        }
    }
}
