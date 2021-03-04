import java.io.*;
import java.util.*;

class MemoryBlock {
    int[] memory = new int[] { 100, 300, 40, 50, 150, 240, 200, 400};
    boolean[] free = new boolean[] { false, true, false, true, false, true, false, true };
    int[] processNumber = new int[] { 1, 2, 3, 4 };
    int divs = memory.length;
    int processSize; 
    Scanner s = new Scanner(System.in);
    
    void processInput() {
        System.out.print("Enter the size of the process that needs to be added (in KB): ");
        processSize = s.nextInt();
        firstFit();
    }

    void firstFit() {
        int ans = -1;
        for(int i = 0; i < divs; i++) {
            if(free[i] && processSize <= memory[i]) {
                ans = i;
                break;
            }
        }
        printTable(ans);
    }

    void bestFit() {
        int ans = -1, curr = 1000000;
        for(int i = 0; i < divs; i++) {
            if(free[i] && processSize <= memory[i]) {
                if(memory[i] - processSize < curr) {
                    curr = memory[i] - processSize;
                    ans = i;
                }
            }
        }
        printTable(ans);
    }

    void worstFit() {
        int ans = -1, curr = 0;
        for(int i = 0; i < divs; i++) {
            if(free[i] && processSize <= memory[i]) {
                if(memory[i] - processSize > curr) {
                    curr = memory[i] - processSize;
                    ans = i;
                }
            }
        }
        printTable(ans);
    }
    
    void printTable(int pos) {
        System.out.print("No. \tMemory \tStatus \tProcess \n");
        int j = 1, ok = 0;
        for (int i = 0; i < divs; i++) {
            if(i == pos) {
                System.out.print(i + 1 + " \t " +  processSize + " \t " + "NF \t " + "Process " + (processNumber.length + 1) + "\n");
                if(memory[i] - processSize != 0) {
                    System.out.print(i + 2 + " \t " + (memory[i] - processSize) + " \t " + "F \t ");
                    ok = 1;
                }
            }
            else {
                System.out.print((i + 1 + ok) + " \t " +  memory[i] + " \t " + ((free[i]) ? "F" : "NF \t " + "Process " + j++));
            }
            System.out.println(' ');
        }
    }
}

class MemoryAllocation {
    public static void main(String args[]) throws IOException
    {
        MemoryBlock m = new MemoryBlock();
        m.processInput();
    }
}