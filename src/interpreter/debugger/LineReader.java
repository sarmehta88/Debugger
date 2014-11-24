package interpreter.debugger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Read each line of the code ".x" file
 * Create an array of SourceLine objects
 */
public class LineReader { 
    public static ArrayList<SourceLine> read(String sourceFile) throws IOException {
        
        BufferedReader file = new BufferedReader(new FileReader(sourceFile));
        ArrayList<SourceLine> sourceLines = new ArrayList<SourceLine>();
        
        String currentLine = file.readLine(); //get current Line

        // Create extra source line for the intrinsic function line ie, LINE -1
        sourceLines.add(new SourceLine(""));

        while(currentLine != null) {
            sourceLines.add(new SourceLine(currentLine)); //add a sourceLine object to array,
            currentLine = file.readLine();
        }

        return sourceLines;
    }
}
