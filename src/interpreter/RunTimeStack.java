package interpreter;

import java.util.*;

/*
 * Records the values in the runStack Vector
 * Stores the indices(offset) of the runstack where a new frame begins in the framePointers stack
 *
 */
public class RunTimeStack {
    private Stack<Integer> framePointers;
    private ArrayList<Integer> runStack;

    public RunTimeStack() {
        framePointers = new Stack<Integer>();
        runStack = new ArrayList<Integer>(); //similar to Vector
        framePointers.add(0);
    }

    public void dump() {
        
        //Make a copy of FramePointers so that original is not modified
       Stack<Integer> fpCopy = (Stack<Integer>) framePointers.clone();
       int [] frameIndex= new int[getFrameSize()]; // keeps order of fp 
       for(int i=getFrameSize()-1; i>=0;i--){ //copy stack into array keeping order the firest fp the first element in the array
            frameIndex[i]=fpCopy.pop();    
       }
        //Print the runStack based on the frameIndex if runStack is not empty
       if(!runStack.isEmpty()){
           //set the boundaries to print the different frames of the runStack
           int last;
           for(int i=0;i<getFrameSize();i++){
               //get first and second fp
               int first=frameIndex[i];
               if(i<getFrameSize()-1){ //get the next element if there is another fpIndex
                    last=frameIndex[i+1];
               }else{
                   last=size(); //if there is only one fp, then last is size of runStack
               }
               if(first==last){
                   System.out.print("[ ]");
               }else{
                    //print first the first runStack element
                    System.out.print("["+ runStack.get(first));
                    //Print the stack from the first inclusive to last exclusive fp boundary
                    for(int j=first+1;j<last;j++){
                        System.out.print(","+runStack.get(j));
                    }
                    System.out.print("] ");
               }
           }
       }else{
           System.out.println("[ ]"); //the runstack is empty
            
       }            
   } 
    /*Return the top item item on runStack*/
    public int peek() {
        return runStack.get(size()-1);
    }
    /*Remove and return the top item item on runStack*/
    public int pop() {
         return runStack.remove(size()-1);
    }
    /*Return the top item item on runStack*/
    public int push(int i) {
        runStack.add(i);
        return i;     
    }
    /*Return the top Frame offset*/
    public int peekFramePointer(){
        return framePointers.peek();
    }
    /* Start new frame*/
    public void newFrameAt(int offset) {
        framePointers.push(offset);
    }
    /* Remove all items in the current frame and push the RETURN value on top of current frame*/
    public void popFrame() {
        int temp = peek();// get the top item of runStack
        int poptilIndex = framePointers.pop(); 
        //keep popping the runtime stack until reach the start frame boundary
        while(size()-1 >= poptilIndex) {
            if(!runStack.isEmpty()) {
                pop();
            }
        }
        push(temp);// push the top of runtimeStack so it will be only item in current frame
    }
    /* Store stack value as a variable*/
    public int store(int offset) {
        //pop the last item in runstack
        runStack.set(offset, pop()); //replaces the item at offset position with the last item
        return offset;
    }
    /*Load variables onto runstack*/
    public int load(int offset) {
        //get index of start of current frame and add offset
        int var = runStack.get(framePointers.peek() + offset); //load from offset in current frame to runstack
        runStack.add(var); //add that var to the top/end of stack
        return offset;
    }

    public int size() {
        return runStack.size();
    }

    public int getElementAt(int element) {
        return runStack.get(element);
    }
    
    public int[] getCurrentFrameVals(){
        int size= size()-framePointers.peek(); //get size of the currentFrame
        int counter=framePointers.peek();
        int[] currFrame= new int[size];
        for(int i=0;i<size;i++){ //store the values of the currentFrame in currFrame[]
            currFrame[i]=getElementAt(counter);
            counter++;
        }
        return currFrame;     
    }
    public int getFrameSize(){
        return framePointers.size();
    }
    
}