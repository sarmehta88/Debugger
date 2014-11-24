package interpreter.debugger;

import java.util.HashMap;
import java.util.Set;
/*
 * Binder objects groups 3 fields
 * 1) the value(offset) of variable
 * 2) the next variable name in the current scope
 * 3) the next link of a previous Binder for the same variable ID
 */

class Binder {
    private Object value;
    private String prevtop;   // prior symbol in same scope
    private Binder tail;      // prior binder for same variable ID
                              // restore this when closing scope
    Binder(Object v, String p, Binder t) {
        value = v;
        prevtop = p;
        tail = t;
    }
    /* Accessors to the value, prevTop, and tail*/
    Object getValue() {
        return value;
    }

    String getPrevtop() {
        return prevtop;
    }

    Binder getTail() {
        return tail;
    }
}

/*
 * Keeps track of variables' information entered/removed in a functionEnvRecord
 * Record variable names and links to their offset
 * Also links a variable name to the previous variable entered in the current scope
 */
public class Table {
    private HashMap<String, Binder> symbols;
    private String top; //last variable added to a functionEnvRecord

    /**
    * Creates a new Table for each functionEnvRecord
    */
    public void beginScope() {
        symbols = new HashMap<String, Binder>();
        top = null;
    }
    
   /*
    * Gets the offset(Integer) associated with the specified variable in the Table.
    */
    public Object get(String key) {
	    int e = (Integer) symbols.get(key).getValue();
	    return e;
    }

   /*
    * Puts the specified offset of variable into the Table
    * Maintain the list of symbols in the current scope (top)
    * Add to list of symbols in prior scope with the same string identifier
    */
    public void put(String key, int value) {
        symbols.put(key, new Binder(value, top, symbols.get(key)));
        top = key;
    }
    
    /*
    * Removes a specified number of 
    * similar to endscope() which is controlled by POP bytecode
    */
    
    public void popValues(int numOfPops) {
	for (int i = 0; i < numOfPops; i++) {
            Binder e = symbols.get(top);
            //if the var is linked to more than 1 offset
            // remove link to most current offset
            if (e.getTail()!=null)
               symbols.put(top,e.getTail());
	   else
               symbols.remove(top);
            
	   top = e.getPrevtop(); //top is the previous Variable in the scope
        }
  }
    
    /**
     * @return a set of the Table's variable names
     */
    public Set<String> keys() {
        return symbols.keySet();
    }
}
