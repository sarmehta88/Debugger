package interpreter;
import java.util.HashMap;
/**
 * ByteCodeLoader calls the CodeTable to get a bytecode class in order to create an instance.
 * Contains a HashMap with keys being the bytecode strings and values being the name
 * of the corresponding class. ie Key is "LIT", Value is "LitCode"
 */
public class CodeTable {
    
    private static HashMap<String,String> byteCodesMap = new HashMap<String,String>(); //only 1 Codetable can exist,hence static keyword
    
   
    /*Initialize the HashMap by adding each the byteCode key, byteCode Class value, includes Debugger Bytecodes*/
    public static void init() {
        byteCodesMap.put("HALT", "HaltCode");
        byteCodesMap.put("POP", "PopCode");
        byteCodesMap.put("FALSEBRANCH", "FalseBranchCode");
        byteCodesMap.put("GOTO", "GotoCode");
        byteCodesMap.put("STORE", "StoreCode");
        byteCodesMap.put("LOAD", "LoadCode");
        byteCodesMap.put("LIT", "LitCode");
        byteCodesMap.put("ARGS", "ArgsCode");
        byteCodesMap.put("CALL", "CallCode");
        byteCodesMap.put("RETURN", "ReturnCode");
        byteCodesMap.put("BOP", "BopCode");
        byteCodesMap.put("READ", "ReadCode");
        byteCodesMap.put("WRITE", "WriteCode");
        byteCodesMap.put("LABEL", "LabelCode");
        byteCodesMap.put("DUMP", "DumpCode");
        
    }
    //Debugger bytecodes
    public static void debuginit() {
        byteCodesMap.put("HALT", "HaltCode");
        byteCodesMap.put("FALSEBRANCH", "FalseBranchCode");
        byteCodesMap.put("GOTO", "GotoCode");
        byteCodesMap.put("STORE", "StoreCode");
        byteCodesMap.put("LOAD", "LoadCode");
        byteCodesMap.put("ARGS", "ArgsCode");
        byteCodesMap.put("BOP", "BopCode");
        byteCodesMap.put("READ", "ReadCode");
        byteCodesMap.put("WRITE", "WriteCode");
        byteCodesMap.put("LABEL", "LabelCode");
        byteCodesMap.put("DUMP", "DumpCode");
        byteCodesMap.put("POP", "debuggerByteCodes.DebugPopCode");
        byteCodesMap.put("LIT", "debuggerByteCodes.DebugLitCode");
        byteCodesMap.put("CALL", "debuggerByteCodes.DebugCallCode");
        byteCodesMap.put("RETURN", "debuggerByteCodes.DebugReturnCode");
        byteCodesMap.put("FORMAL", "debuggerByteCodes.FormalCode");
        byteCodesMap.put("FUNCTION", "debuggerByteCodes.FunctionCode");
        byteCodesMap.put("LINE", "debuggerByteCodes.LineCode");
    }
    
    /* Returns the value(Class Name) to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * Used by the ByteCodeLoader to get class Name of each bytecode from the file
     */
    public static String get(String code){
        return byteCodesMap.get(code);
    }
}
