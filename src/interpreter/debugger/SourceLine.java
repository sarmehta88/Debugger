package interpreter.debugger;

public class SourceLine {
    private String sourceLine="";
    private boolean isBreakPointSet= false;

    public SourceLine(String sourceLine) {
        this.sourceLine = sourceLine;
    }

    public String getSourceLine() {
        return sourceLine;
    }

    /* Returns whether a breakpoint is at the line*/
    public boolean isBreakPointSet() {
        return isBreakPointSet;
    }

    public void setBreakPoint(boolean status) {
        isBreakPointSet = status;
    }
}
