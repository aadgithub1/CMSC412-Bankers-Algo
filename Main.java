import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static int nProcesses;
    static int mResourceTypes;

    //add 2D ArrList to store safe sequences
    static ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();

    public static void main(String[] args) {
        try{
            //get data from files
            File file = new File("input1.txt");
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

            //difference between claim and allocation
            int[][] need = new int[nProcesses][mResourceTypes];
            for(int i = 0; i < nProcesses; i++){
                for(int j = 0; j < mResourceTypes; j++){
                    need[i][j] = claim[i][j] - allocation[i][j];
                }
            }

            //marks finished processes based on index
            boolean finished[] = new boolean[nProcesses];

            //ArrayList instance to track safe sequences in the method
            ArrayList<Integer> seq = new ArrayList<>();

            findSafeSeq(seq, nProcesses, mResourceTypes, systemResources,
            available, finished, allocation, claim, need);

            scanner.close();

            //print both solutions based on solution size
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
        } catch(Exception e){
            e.printStackTrace();
        }
    }

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

        for(int i = 0; i < numProcesses; i++){
            if(!finished[i] && canRun(i, available, need)){

                finished[i] = true; //run the process

                for(int j = 0; j < mResourceTypes; j++){ //relinquish resources
                    available[j] += allocation[i][j];
                }

                seq.add(i); //add process to the safe seq

                if(seq.size() == numProcesses){
                    //add the solution
                    solutions.add(new ArrayList<>(seq));
                }

                if(solutions.size() == 2){ //check if we have both solutions
                    return; //if so return
                }
                
                findSafeSeq(seq, numProcesses, mResourceTypes,
                systemResources, available, finished, allocation, claim, need);
                
                seq.remove(seq.size()-1); //only reaches this after return

                for(int j = 0; j < mResourceTypes; j++){ //relinquish resources
                    available[j] -= allocation[i][j];   //after dead end
                }

                finished[i] = false; //unmark if hit dead end
            }
        }
    }
}