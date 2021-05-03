import java.io.*;
import java.util.*;

class DSP_Algorithms {
    // hardcoded values if the user does not want to enter the values;
    int[] requestQueue = new int[] { 90, 120, 30, 40, 115, 130, 110, 80, 190, 25 };
    int globalCurrentHead = 86;
    int queueLength = requestQueue.length;
    int[] orderOfProcessing = new int[queueLength];
    int totalHeadMovement = 0;
    Scanner s = new Scanner(System.in);
    
    
    void processInput() {
        System.out.println("\n\t\t Welcome to Disk Scheduling Policies \n");

        System.out.print("Do you want to input the values? [0/1]: ");
        if(s.nextInt() == 1) {
            System.out.print("\nEnter the length of the request queue: ");
            queueLength = s.nextInt();
            
            int[] requestQueue = new int[queueLength];
            System.out.println("\nEnter the request queue: ");
            for(int i = 0; i < queueLength; ++i) {
                System.out.println("\nEnter Element " + (i + 1) + ": ");
                requestQueue[i] = s.nextInt();
            }
            
            System.out.println("\nEnter the current head location: ");
            globalCurrentHead = s.nextInt();
        }
        
        while(true) {
            System.out.println("\n\nEnter the policy you want to execute? ");
            System.out.println("[1] FCFS (First Come First Serve)");
            System.out.println("[2] SSTF (Shortest Seek Time First)");
            System.out.println("[3] SCAN ");
            System.out.println("[4] C-SCAN ");
            System.out.println("[5] LOOK ");
            System.out.println("[6] C-LOOK ");
            System.out.println("[7] Exit ");
            System.out.print("Enter your choice? [1-7]: ");
            switch(s.nextInt()) {
                case 1: {
                    FCFS();
                    break;
                }
                case 2: {
                    SSTF();
                    break;
                }
                case 3: {
                    SCAN();
                    break;
                }
                case 4: {
                    CSCAN();
                    break;
                }
                case 5: {
                    LOOK();
                    break;
                }
                case 6: {
                    CLOOK();
                    break;
                }
                case 7: {
                    return;
                }
                default:
                    System.out.println("Wrong Choice, Please enter a number between 1 and 5. ");
            }
        }
    }
    
    void printAverageSeekTime(int totalHeadMovement, int totalLength) { 
        System.out.println("\n\n\tAverage Seek Time: " + (totalHeadMovement) + " / " + (totalLength) + " = " + ((double)totalHeadMovement / (double)totalLength));
    }
    
    void FCFS() {
        int[] orderOfProcessing = new int[requestQueue.length];
        int totalHeadMovement = 0;
        int currentHead = globalCurrentHead;
        
        System.out.println();
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\t\t|Output for FCFS Disk Scheduling Policy|");
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\n\tHead currently at: " + currentHead);
        
        System.out.println("\n     Iteration \t Current Head \t Disk Movement \t Total Disk Movement");
        System.out.println("------------------------------------------------------------------------");
        
        for(int i = 0; i < requestQueue.length; ++i) {
            int currentDiskMovement = Math.abs(currentHead - requestQueue[i]);
            totalHeadMovement += currentDiskMovement;
            orderOfProcessing[i] = requestQueue[i];
            currentHead = requestQueue[i];
            
            System.out.println( "\t" + (i + 1) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
        }
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("\n\tTotal Head Movement: " + totalHeadMovement);
        System.out.print("\n\tOrder of Processing: " + (orderOfProcessing[0]));
        for(int i = 1; i < orderOfProcessing.length; ++i) {
            System.out.print( " -> " + orderOfProcessing[i]);
        }
        printAverageSeekTime(totalHeadMovement, requestQueue.length);
        System.out.println();
    }
    
    void SSTF() {
        int[] orderOfProcessing = new int[requestQueue.length];
        int totalHeadMovement = 0;
        int currentHead = globalCurrentHead;
        
        System.out.println();
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\t\t|Output for SSTF Disk Scheduling Policy|");
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\n\tHead currently at: " + currentHead);
        
        System.out.println("\n     Iteration \t Current Head \t Disk Movement \t Total Disk Movement");
        System.out.println("------------------------------------------------------------------------");
        
        boolean[] traversed = new boolean[requestQueue.length]; 
        for(int i = 0; i < requestQueue.length; ++i) {
            
            int minimumDifference = 1000000, index = -1;
            
            for(int j = 0; j < requestQueue.length; ++j) {
                
                if(currentHead != requestQueue[j] && !traversed[j]) {
                    int currentDifference = Math.abs(currentHead - requestQueue[j]);
                    
                    if(currentDifference < minimumDifference) {
                        minimumDifference = currentDifference;
                        index = j;
                    }
                }
            }
            
            totalHeadMovement += minimumDifference;
            orderOfProcessing[i] = requestQueue[index];
            currentHead = requestQueue[index];
            traversed[index] = true;
            
            System.out.println( "\t" + (i + 1) +"\t\t" + currentHead + "\t\t" + minimumDifference + "\t\t" + totalHeadMovement);
        }
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("\n\tTotal Head Movement: " + totalHeadMovement);
        System.out.print("\n\tOrder of Processing: " + (orderOfProcessing[0]));
        for(int i = 1; i < orderOfProcessing.length; ++i) {
            System.out.print( " -> " + orderOfProcessing[i]);
        }
        printAverageSeekTime(totalHeadMovement, requestQueue.length);
        System.out.println();
    }
    
    void SCAN() {
        // "requestQueue.length + 1" since, one extra end will be added to the queue;
        int[] orderOfProcessing = new int[requestQueue.length + 1];
        int totalHeadMovement = 0;
        int currentHead = globalCurrentHead;
        
        ArrayList<Integer> firstHalf = new ArrayList<Integer>();
        ArrayList<Integer> secondHalf = new ArrayList<Integer>();
        
        for(int i = 0; i < requestQueue.length; ++i) {
            if(currentHead > requestQueue[i])
            firstHalf.add(requestQueue[i]);
            else
            secondHalf.add(requestQueue[i]);
        }
        Collections.sort(firstHalf);   
        Collections.sort(secondHalf);   
        System.out.print(firstHalf);
        System.out.println(secondHalf);

        System.out.print("\nTraverse to the inner track or the outer track? \n [0] Inner Track\n [1] Outer Track \n Your Choice? ");
        int outer = s.nextInt();
        
        System.out.println();
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\t\t|Output for SCAN Disk Scheduling Policy|");
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\n\tHead currently at: " + currentHead);
        
        System.out.println("\n     Iteration \t Current Head \t Disk Movement \t Total Disk Movement");
        System.out.println("------------------------------------------------------------------------");
        
        int j = 0;
        // inner track first
        if(outer == 0) {
            for(int i = firstHalf.size() - 1; i >= 0; --i) {
                
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            // going to 0;
            totalHeadMovement += (currentHead - 0);
            orderOfProcessing[j] = 0;
            System.out.println( "\t" + (++j) +"\t\t" + 0 + "\t\t" + currentHead + "\t\t" + totalHeadMovement);
            currentHead = 0;
            
            for(int i = 0; i < secondHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        else {
            for(int i = 0; i < secondHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            // going to 199;
            totalHeadMovement += Math.abs(currentHead - 199);
            orderOfProcessing[j] = 199;
            System.out.println( "\t" + (++j) +"\t\t" + 199 + "\t\t" + Math.abs(currentHead - 199) + "\t\t" + totalHeadMovement);
            currentHead = 199;
            
            for(int i = firstHalf.size() - 1; i >= 0; --i) {
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("\n\tTotal Head Movement: " + totalHeadMovement);
        System.out.print("\n\tOrder of Processing: " + (orderOfProcessing[0]));
        for(int i = 1; i < orderOfProcessing.length; ++i) {
            System.out.print( " -> " + orderOfProcessing[i]);
        }
        printAverageSeekTime(totalHeadMovement, requestQueue.length);
        System.out.println();
    }
    
    void CSCAN() {
        // "requestQueue.length + 2" since, both the extra ends will be added to the queue;
        int[] orderOfProcessing = new int[requestQueue.length + 2];
        int totalHeadMovement = 0;
        int currentHead = globalCurrentHead;
        
        ArrayList<Integer> firstHalf = new ArrayList<Integer>();
        ArrayList<Integer> secondHalf = new ArrayList<Integer>();
        
        for(int i = 0; i < requestQueue.length; ++i) {
            if(currentHead > requestQueue[i])
            firstHalf.add(requestQueue[i]);
            else
            secondHalf.add(requestQueue[i]);
        }
        Collections.sort(firstHalf);   
        Collections.sort(secondHalf);   
        System.out.print(firstHalf);
        System.out.println(secondHalf);
        
        System.out.print("\nTraverse to the inner track or the outer track? \n [0] Inner Track\n [1] Outer Track \n Your Choice? ");
        int outer = s.nextInt();
        
        System.out.println();
        System.out.println("\t\t+----------------------------------------+");
        System.out.println("\t\t|Output for C-SCAN Disk Scheduling Policy|");
        System.out.println("\t\t+----------------------------------------+");
        System.out.println("\n\tHead currently at: " + currentHead);
        
        System.out.println("\n     Iteration \t Current Head \t Disk Movement \t Total Disk Movement");
        System.out.println("------------------------------------------------------------------------");
        
        int j = 0;
        // Inner Track First
        if(outer == 0) {
            for(int i = firstHalf.size() - 1; i >= 0; --i) {
                
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            // going to 0;
            totalHeadMovement += (currentHead - 0);
            orderOfProcessing[j] = 0;
            System.out.println( "\t" + (++j) +"\t\t" + 0 + "\t\t" + (currentHead - 0) + "\t\t" + totalHeadMovement);
            currentHead = 0;

            // going to 199;
            totalHeadMovement += Math.abs(currentHead - 199);
            orderOfProcessing[j] = 199;
            System.out.println( "\t" + (++j) +"\t\t" + 199 + "\t\t" + 199 + "\t\t" + totalHeadMovement);
            currentHead = 199;
            
            for(int i = secondHalf.size() - 1; i >= 0; --i)  {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }

        // Outer Track First;
        else {
            for(int i = 0; i < secondHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            // going to 199;
            totalHeadMovement += Math.abs(currentHead - 199);
            orderOfProcessing[j] = 199;
            System.out.println( "\t" + (++j) +"\t\t" + 199 + "\t\t" + Math.abs(currentHead - 199) + "\t\t" + totalHeadMovement);
            currentHead = 199;
            
            // going to 0;
            totalHeadMovement += Math.abs(currentHead - 0);
            orderOfProcessing[j] = 0;
            System.out.println( "\t" + (++j) +"\t\t" + 0 + "\t\t" + 199 + "\t\t" + totalHeadMovement);
            currentHead = 0;
            
            for(int i = 0; i < firstHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("\n\tTotal Head Movement: " + totalHeadMovement);
        System.out.print("\n\tOrder of Processing: " + (orderOfProcessing[0]));
        for(int i = 1; i < orderOfProcessing.length; ++i) {
            System.out.print( " -> " + orderOfProcessing[i]);
        }
        printAverageSeekTime(totalHeadMovement, requestQueue.length);
        System.out.println();
    }
    
    void LOOK() {
        // "requestQueue.length" since, no extra ends will be added to the queue;
        int[] orderOfProcessing = new int[requestQueue.length];
        int totalHeadMovement = 0;
        int currentHead = globalCurrentHead;
        
        ArrayList<Integer> firstHalf = new ArrayList<Integer>();
        ArrayList<Integer> secondHalf = new ArrayList<Integer>();
        
        for(int i = 0; i < requestQueue.length; ++i) {
            if(currentHead > requestQueue[i])
            firstHalf.add(requestQueue[i]);
            else
            secondHalf.add(requestQueue[i]);
        }
        Collections.sort(firstHalf);   
        Collections.sort(secondHalf);   
        System.out.print(firstHalf);
        System.out.println(secondHalf);
        
        System.out.print("\nTraverse to the inner track or the outer track? \n [0] Inner Track\n [1] Outer Track \n Your Choice? ");
        int outer = s.nextInt();
        
        System.out.println();
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\t\t|Output for LOOK Disk Scheduling Policy|");
        System.out.println("\t\t+--------------------------------------+");
        System.out.println("\n\tHead currently at: " + currentHead);
        
        System.out.println("\n     Iteration \t Current Head \t Disk Movement \t Total Disk Movement");
        System.out.println("------------------------------------------------------------------------");
        
        int j = 0;
        // inner track first
        if(outer == 0) {
            for(int i = firstHalf.size() - 1; i >= 0; --i) {
                
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            for(int i = 0; i < secondHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        else {
            for(int i = 0; i < secondHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            for(int i = firstHalf.size() - 1; i >= 0; --i) {
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("\n\tTotal Head Movement: " + totalHeadMovement);
        System.out.print("\n\tOrder of Processing: " + (orderOfProcessing[0]));
        for(int i = 1; i < orderOfProcessing.length; ++i) {
            System.out.print( " -> " + orderOfProcessing[i]);
        }
        printAverageSeekTime(totalHeadMovement, requestQueue.length);
        System.out.println();   
    }
    
    void CLOOK() {
        // "requestQueue.length" since, no extra ends will be added to the queue;
        int[] orderOfProcessing = new int[requestQueue.length];
        int totalHeadMovement = 0;
        int currentHead = globalCurrentHead;
        
        ArrayList<Integer> firstHalf = new ArrayList<Integer>();
        ArrayList<Integer> secondHalf = new ArrayList<Integer>();
        
        for(int i = 0; i < requestQueue.length; ++i) {
            if(currentHead > requestQueue[i])
            firstHalf.add(requestQueue[i]);
            else
            secondHalf.add(requestQueue[i]);
        }
        Collections.sort(firstHalf);   
        Collections.sort(secondHalf);   
        System.out.print(firstHalf);
        System.out.println(secondHalf);
        
        System.out.print("\nTraverse to the inner track or the outer track? \n [0] Inner Track\n [1] Outer Track \n Your Choice? ");
        int outer = s.nextInt();
        
        System.out.println();
        System.out.println("\t\t+----------------------------------------+");
        System.out.println("\t\t|Output for C-LOOK Disk Scheduling Policy|");
        System.out.println("\t\t+----------------------------------------+");
        System.out.println("\n\tHead currently at: " + currentHead);
        
        System.out.println("\n     Iteration \t Current Head \t Disk Movement \t Total Disk Movement");
        System.out.println("------------------------------------------------------------------------");
        
        int j = 0;
        // inner track first
        if(outer == 0) {
            for(int i = firstHalf.size() - 1; i >= 0; --i) {
                
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }

            
            for(int i = secondHalf.size() - 1; i >= 0; i--) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        else {
            for(int i = 0; i < secondHalf.size(); i++) {
                int currentDiskMovement = Math.abs(currentHead - secondHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = secondHalf.get(i);
                currentHead = secondHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
            
            for(int i = 0; i < firstHalf.size(); ++i) {
                int currentDiskMovement = Math.abs(currentHead - firstHalf.get(i));
                totalHeadMovement += currentDiskMovement;
                orderOfProcessing[j] = firstHalf.get(i);
                currentHead = firstHalf.get(i);
                
                System.out.println( "\t" + (++j) +"\t\t" + currentHead + "\t\t" + currentDiskMovement + "\t\t" + totalHeadMovement);
            }
        }
        System.out.println("------------------------------------------------------------------------");
        
        System.out.println("\n\tTotal Head Movement: " + totalHeadMovement);
        System.out.print("\n\tOrder of Processing: " + (orderOfProcessing[0]));
        for(int i = 1; i < orderOfProcessing.length; ++i) {
            System.out.print( " -> " + orderOfProcessing[i]);
        }
        printAverageSeekTime(totalHeadMovement, requestQueue.length);
        System.out.println();
    }
    
}

class DiskSchedulingPolicy {
    public static void main(String args[]) throws IOException
    {
        DSP_Algorithms dsp = new DSP_Algorithms();
        dsp.processInput();
    }
}