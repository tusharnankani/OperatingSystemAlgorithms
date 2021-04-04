import java.io.*;
import java.util.*;

class FIFO {
    // hardcoded values if the user does not want to enter the values;
    int[] pageNos = new int[] { 7, 0, 2, 0, 5, 9, 4, 7, 8, 2 };
    int pages = pageNos.length;
    int noOfFrames = 3;
    int[] PR = new int[] { -1, -1, -1 };
    float hits = 0, miss = 0;
    Scanner s = new Scanner(System.in);
    
    void welcomeMessage() {
        processInput();
    }
    
    void processInput() {
        // re-initialising the data if user wants to enter the data;
        pageNos = new int[100];
        System.out.print("Enter the number of pages: ");
        pages = s.nextInt();
        for(int i = 0; i < pages; ++i) {
            System.out.print("Enter page " + (i + 1) + ": ");
            pageNos[i] = s.nextInt();
        }
        System.out.print("\n\nEnter Frame Size: ");
        noOfFrames = s.nextInt();
        PR = new int[noOfFrames];
        for(int i = 0; i < noOfFrames; ++i) {
            PR[i] = -1;
        }
        FIFOPR();
    }
    
    // FIFO Page Replacement
    void FIFOPR() {
        System.out.println("\n\t\t   Frame Blocks \t\t    HIT / MISS\n");
        int ptr = 0;
        for(int i = 0; i < pages; ++i) {
            boolean ok = false;
            for(int j = 0; j < noOfFrames; ++j) {
                // System.out.println(pageNos[i] + " ~ " + PR[j]);
                if(pageNos[i] == PR[j]) {
                    ok = true;
                    hits += 1;
                    break;
                }
            }
            if(!ok) {
                PR[ptr] = pageNos[i];
                ptr += 1;
                ptr %= noOfFrames;
                miss += 1;
            }
            printCurrent(ok);
        }
        System.out.println("\n\nTotal Number of Hits: " + hits);
        System.out.println("Total Number of Miss: " + miss);
        float ratio = (hits / (hits + miss)) * 100;
        System.out.println("\nHit Ratio: " + ratio + "%");
        System.out.println("Miss Ratio: " + (100 - ratio) + "%");
    }

    void printCurrent(boolean ok) {
        System.out.print("|\t");
        for(int i = 0; i < noOfFrames; ++i) {
            System.out.print((PR[i] >= 0) ? PR[i] : "-");
            System.out.print("\t|\t");
        }
        System.out.println((ok) ? "Hit" : "Miss");
    }
}

class PageReplacementFIFO {
    public static void main(String args[]) throws IOException
    {
        FIFO m = new FIFO();
        m.welcomeMessage();
    }
}