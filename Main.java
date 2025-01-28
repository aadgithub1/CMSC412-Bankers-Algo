import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main{
    static int nProcesses;
    static int mResourceTypes;

    //add 2D ArrayList to store safe sequences
    static ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();

    public static void main(String[] args) {
        try{
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Input the file path: ");

            //get data from files
            File file = new File(inputScanner.nextLine());
            inputScanner.close();
            

            Scanner scanner = new Scanner(file);

            //first two ints tell num processes and num resource types
            nProcesses = scanner.nextInt();
            mResourceTypes = scanner.nextInt();
            
            //total available resources
            int[] systemResources = new int[mResourceTypes];
            for(int i = 0; i < mResourceTypes; i++){
                systemResources[i] = scanner.nextInt();
            }

            //initial resources available & current allocations
            int[] available = new int[mResourceTypes];
            int[][] allocation = new int[nProcesses][mResourceTypes];
            for(int i = 0; i < nProcesses; i++){
                for(int j = 0; j < mResourceTypes; j++){
                    allocation[i][j] = scanner.nextInt();
                    if(i == 0){
                        available[j] = systemResources[j] - allocation[i][j];
                    } else{
                        available[j] -= allocation[i][j];
                    }
                }
            }

            //the max number of each resource each process needs to complete
            int[][] claim = new int[nProcesses][mResourceTypes];
            for(int i = 0; i < nProcesses; i++){
                for(int j = 0; j < mResourceTypes; j++){
                    claim[i][j] = scanner.nextInt();
                }
            }
            scanner.close();

            //difference between claim and allocation
            int[][] need = new int[nProcesses][mResourceTypes];
            for(int i = 0; i < nProcesses; i++){
                for(int j = 0; j < mResourceTypes; j++){
                    need[i][j] = claim[i][j] - allocation[i][j];
                }
            }
            display1D(systemResources, "system resources");
            display2D(allocation, "allocation");
            display1D(available,"available");
            display2D(claim, "claim");
            display2D(need, "need");

            //marks finished processes based on index
            boolean finished[] = new boolean[nProcesses];

            //ArrayList instance to track safe sequences in the below method
            ArrayList<Integer> seq = new ArrayList<>();

            findSafeSeq(seq, nProcesses, mResourceTypes, systemResources,
            available, finished, allocation, claim, need);

            //print both solutions based on solution ArrayList size
            if(solutions.size() == 0){
                System.out.println("There are no safe sequences.");
            }else if(solutions.size() == 1){
                System.out.println("There is one safe sequence, it is:");
                for(int j = 0; j < solutions.get(0).size(); j++){
                    System.out.print(solutions.get(0).get(j) + 1);
                }
            } else{
                System.out.println("There are two or more safe sequences."
                + " Here are two of them:");
                for(int i = 0; i < solutions.size(); i++){
                    for(int j = 0; j < solutions.get(0).size(); j++){
                        System.out.print(solutions.get(i).get(j) + 1);
                    }
                    System.out.println();
                }
            }
        } catch(FileNotFoundException fe){
            System.out.println("The file path you entered could not "
                + "be found, please restart and enter a valid path.");
        } catch(NoSuchElementException nse){ //covers mismatch as well
            System.out.println("The file format seems to be incorrect. Please "
            + "ensure the data is input properly and restart. "
            + "\nProper format is all numbers - number of processes, "
            + "number of resource types, then each start on their own lines: "
            + "The capacity of each resource and then each matrix.");
        }
    }

    public static void display1D(int[] array, String nameOfArr){
        System.out.println("The " + nameOfArr + " array:");
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println("\n");
    }

    public static void display2D(int[][] array, String nameOfArr){
        System.out.println("The " + nameOfArr + " matrix:");
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //returns true if enough resources are available for process to run
    public static boolean canRun(int processIndex, int[] available,
    int[][] need){
        for(int j = 0; j < available.length; j++){
            if(need[processIndex][j] <= available[j]){
                if(j == available.length - 1){
                    return true;
                }
            } else{
                return false;
            }
        }
        return false;
    }

    public static void findSafeSeq(
        ArrayList<Integer> seq,
        int numProcesses, int mResourceTypes,
        int[] systemResources, int[] available, boolean[] finished,
        int[][] allocation, int[][]claim, int[][] need
    ){

        for(int i = 0; i < numProcesses; i++){ //iterate over processes
            if(!finished[i] && canRun(i, available, need)){

                finished[i] = true; //run the process

                for(int j = 0; j < mResourceTypes; j++){ //relinquish resources
                    available[j] += allocation[i][j];
                }

                seq.add(i); //add process to the safe seq

                if(seq.size() == numProcesses){
                    //if we have all processes, add the solution
                    solutions.add(new ArrayList<>(seq));
                }

                if(solutions.size() == 2){ //check if we have both solutions
                    return; //if so return (do not look for any more)
                }
                
                //iterate and make it to the next process
                findSafeSeq(seq, numProcesses, mResourceTypes,
                systemResources, available, finished, allocation, claim, need);
                
                seq.remove(seq.size()-1); //only reaches this after return

                for(int j = 0; j < mResourceTypes; j++){ //take back resources
                    available[j] -= allocation[i][j];   //after dead end
                }

                finished[i] = false; //unmark if hit dead end
            }
        }
    }
}