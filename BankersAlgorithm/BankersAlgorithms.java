import java.io.*;

class Bankers {
    int allocation[][] = {{0, 1, 0},
                        {2, 0, 0},
                        {3, 0, 2},
                        {2, 1, 1},
                        {0, 0, 2}};
    int max[][] = {{7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {2, 2, 2},
                {4, 3, 3}};
    int available[] = {3, 3, 2}, totalInstances[] = {7, 2, 6};
    int numberOfResources = 3, numberOfProcesses = 5;
    // request is the need matrix: need = max - allocated;
    int request[][] = new int[numberOfProcesses][numberOfResources];
    boolean done[] = new boolean[numberOfProcesses]; 
    int cnt = 0;

    void printMatrix(int[][] arr) {
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; i < arr[0].length; j++) {
                System.out.print( arr[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    void computeRequests() {
        System.out.println("\nThe Max Matrix is: ");
        for(int i = 0; i < allocation.length; ++i) {
            for(int j = 0; j < allocation[i].length; ++j) {
                System.out.print( max[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("\nThe Allocation Matrix is: ");
        for(int i = 0; i < allocation.length; ++i) {
            for(int j = 0; j < allocation[i].length; ++j) {
                System.out.print( allocation[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("\nThe calculated need matrix is: ");
        for(int i = 0; i < allocation.length; ++i) {
            for(int j = 0; j < allocation[i].length; ++j) {
                request[i][j] = max[i][j] - allocation[i][j];
                System.out.print( request[i][j] + " ");
            }
            System.out.println("");
        }
    }

    void iteratingProcesses() {
        computeRequests();
        int prev = -1;
        while(prev != cnt && cnt != numberOfProcesses) {
            prev = cnt;
            for(int i = 0; i < request.length; ++i) {
                if(!done[i]) {
                    boolean ok = true;
                    for(int j = 0; j < request[0].length; ++j) {
                        if(request[i][j] > available[j]) {
                            ok = false;
                            break;
                        }
                    }
    
                    if(ok) {
                        System.out.println("\n\nProcess "+ (i + 1) +" can be allocated the resources...");
					    System.out.println("Process Terminated. Releasing resources...");
                        System.out.print("Process " + (i + 1) + ": ");
                        for(int j = 0; j < available.length; ++j) {
                            System.out.print(available[j] + " ");
                        }
                        System.out.print(" + ");
                        for(int j = 0; j < allocation[0].length; ++j) {
                            System.out.print(allocation[i][j] + " ");
                        }
                        
                        System.out.print(" = ");
                        for(int j = 0; j < request[0].length; ++j) {
                            available[j] += allocation[i][j];
                            System.out.print(available[j] + " ");
                        }
                        System.out.println("");
                        
                        done[i] = true;
                        cnt += 1;
                    }
                }
            }
            if(prev == cnt) {
                System.out.println("\nNone of the remaining processes can be allocated the required resources. \nTerminating iteration...");
            }
        }

        if(cnt == numberOfProcesses) {
            System.out.println("All processes terminated. \nSafe");
        } else {
            System.out.println("\nUnSafe");
        }
    }

}

class BankersAlgorithm {
    public static void main(String args[]) throws IOException
    {
        Bankers b = new Bankers();
        b.iteratingProcesses();

    }
}