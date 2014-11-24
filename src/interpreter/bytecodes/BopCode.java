package interpreter.bytecodes;

import interpreter.VirtualMachine;
import java.util.*;

/*
 * BOP pops the top 2 levels of the stack and perform an indicated
 * binary operation. 
 * Ex. <second level>+<top level>
 *
 */
public class BopCode extends ByteCode {
    private String bopCodeArg;
    private String name="Bop";

    @Override
    public void init(ArrayList<String> byteCodeArgs) {
        bopCodeArg = byteCodeArgs.get(0); //BOP only has 1 arg ie BOP +
    }
    // Perform the arithmetic operation on the second level and top level runstack items
    @Override
    public void execute(VirtualMachine vm) {
        int first = vm.pop(); //pop top most item of stack
        int second = vm.pop(); //pop second top most item
        int result = -1000; //error code -1000
        //push result on top of runstack
        switch (bopCodeArg) {
            case "+":
                result = second + first;
                vm.push(result);
                break;
            case "-":
                result = second - first;
                vm.push(result);
                break;
            case "/":
                result = second / first;
                vm.push(result); //assume no divide by 0
                break;
            case "*":
                result = second * first;
                vm.push(result);
                break;
            case "==":
                if(second == first) {
                    vm.push(1); //1 for true
                }
                else {
                    vm.push(0); //0 for false
                }
                break;
            case "!=":
                if(second != first) {
                    vm.push(1);
                }
                else {
                    vm.push(0);
                }
                break;
            case "<=":
                if(second <= first) {
                    vm.push(1);
                }
                else {
                    vm.push(0);
                }
                break;
            case ">":
                if(second > first) {
                    vm.push(1);
                }
                else {
                    vm.push(0);
                }
                break;
            case ">=":
                if(second >= first) {
                    vm.push(1);
                }
                else {
                    vm.push(0);
                }
                break;
            case "<":
                if(second < first) {
                    vm.push(1);
                }
                else {
                    vm.push(0);
                }
                break;
            case "|":
                if(second > 0 || first > 0) { //if either boolean variables are true
                    vm.push(1); //push true
                }
                else {
                    vm.push(0); //otherwise push false
                }
                break;
            case "&":
                if(second > 0 && first > 0) { //if both boolean variables are true
                    vm.push(1);
                }
                else {
                    vm.push(0);
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "BOP " + bopCodeArg;
    }

    @Override
    public String getFirstArg() {
        return bopCodeArg;
    }

    @Override
    public void setNumMemoryAddress(int address) {
        //BOP doesn't use this
    }

    @Override
    public String getName() {
        return name;
    }
}
