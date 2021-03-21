import java.io.*;
import java.util.*;

class FirstComeFirstServe {
    // hardcoded values if the user does not want to enter the values;
    float[] burstTime = new float[] { 24, 3, 3 };
    int processes = 3;
    Scanner s = new Scanner(System.in); 
    float avgTAT = 0, avgWT = 0, WT = 0, TAT = 0;

    void processInput() {
        System.out.print("\nEnter the number of the process that needs to be added (in KB): ");
        processes = s.nextInt();
        burstTime = new float[100];
        for(int i = 0; i < processes; ++i) {
            System.out.print("Enter the burst time of P" + (i + 1) + " (in s): ");
            burstTime[i] = s.nextInt();
        }
    }

    void sortBurstTime() {
        
    }

    void TWTime() {
        processInput();
        ganttChart();
        // Printing Time for each process in a tabular format;
        System.out.println("\n+------------------------------------------------------+");
        System.out.println("| Process \t Turn Around Time\t Waiting time  |");
        for(int i = 0; i < processes; ++i) {
            System.out.print("|   P" + (i + 1) + "\t\t     " + TAT + "s");
            if(i < processes - 1) {
                TAT += burstTime[i];
                avgTAT += TAT;
            }
            WT += burstTime[i];
            avgWT += WT;
            System.out.println("\t\t    " + WT + "s      |");
        }
        System.out.println("+------------------------------------------------------+");
        
        // Printing Average;
        System.out.println("+----------------------------------+");
        System.out.format("| Average Turn Around Time: %.2fs |\n", (avgTAT / processes));
        System.out.format("| Average Waiting Time:     %.2fs |\n", (avgWT / processes));
        System.out.println("+----------------------------------+");
        
    }

    void ganttChart() {
        System.out.println("\n\t\t Gantt Chart ");
        System.out.print("\n Process: ");
        for(int i = 0; i < processes; ++i) {
            System.out.print("\t P" + (i + 1));
        }
        System.out.print("\n");
        System.out.print("\n Burst Time: ");
        for(int i = 0; i < processes; ++i) {
            System.out.print("\t" + burstTime[i] + "s");
        }
        System.out.println();
    }
}

public class SchedulingAlgorithms {
    public static void main(String args[]) throws IOException
    {
        FirstComeFirstServe fcfs = new FirstComeFirstServe();
        fcfs.TWTime();
    }

}
