import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static int nProcesses;
    static int mResourceTypes;
    static ArrayList<Integer> safe;
    static ArrayList<Integer> safe2;
    public static void main(String[] args) {
        try{
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);
            nProcesses = scanner.nextInt();
            mResourceTypes = scanner.nextInt();
            
            int[] systemResources = new int[mResourceTypes];
            for(int i = 0; i < mResourceTypes; i++){
                systemResources[i] = scanner.nextInt();
            }

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

            int[][] claim = new int[nProcesses][mResourceTypes];
            for(int i = 0; i < nProcesses; i++){
                for(int j = 0; j < mResourceTypes; j++){
                    claim[i][j] = scanner.nextInt();
                }
            }

            int[][] need = new int[nProcesses][mResourceTypes];
            for(int i = 0; i < nProcesses; i++){
                for(int j = 0; j < mResourceTypes; j++){
                    need[i][j] = claim[i][j] - allocation[i][j];
                }
            }

            //int nProcesses, mResourceTypes
            //int[] systemResources, int[] available,
            //int[][] allocation, int[][]claim, int[][] need
            int[] availableClone = available.clone();
            ArrayList<Integer> tempSeq = new ArrayList<>();
            boolean finished[] = new boolean[nProcesses];

            ArrayList<Integer> seq = new ArrayList<>();
            findSafeSeq(seq, nProcesses, mResourceTypes, systemResources,
            availableClone, finished, allocation, claim, need);
            tempSeq.clear();

            // ArrayList<Integer> seq2 = findSafeSeq(tempSeq, nProcesses, mResourceTypes, systemResources,
            // available, finished, allocation, claim, need);


            scanner.close();
            System.out.println("the sequence is: ");
            for(int i = 0; i < safe.size(); i++){
                System.out.println((safe.get(i)+1) + ", ");
            }

            System.out.println("the second sequence is: ");
            for(int i = 0; i < safe2.size(); i++){
                System.out.println((safe2.get(i)+1) + ", ");
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

                findSafeSeq(seq, numProcesses, mResourceTypes,
                systemResources, available, finished, allocation, claim, need);
                if(seq.size() == numProcesses) {
                    if(safe == null){
                        safe = new ArrayList<>(seq);
                    } else if(safe2 == null){
                        safe2 = new ArrayList<>(seq);
                    }
                }
                seq.remove(seq.size()-1); //only reaches this after return

                for(int j = 0; j < mResourceTypes; j++){ //relinquish resources
                    available[j] -= allocation[i][j];   //after dead end
                }

                finished[i] = false; //unmark if hit dead end
            }
        }
    }
}