import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static int nProcesses;
    static int mResourceTypes;
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
            //haves: systemResources[], available[], allocation[][],
            //claim[][], need[][]
            boolean[] finished = new boolean[nProcesses];

            findSafeSeq(nProcesses, mResourceTypes, systemResources,
            available, finished, allocation, need, claim);
            
            scanner.close();
            // System.out.println("the sequence is: ");
            // for(int i = 0; i < seq.size(); i++){
            //     System.out.println((seq.get(i)+1) + ", ");
            // }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean canRun(
        int[][] need, int[] available, int processIndex){
            boolean canRunFlag = false;

            for(int j = 0; j < available.length; j++){
                if(need[processIndex][j] <= available[j]){
                    if(j == available.length - 1){
                        canRunFlag = true;
                    }
                } else{
                    return false;
                }
            }
            return canRunFlag;
        }

    public static void findSafeSeq(
        int numProcesses, int numResourceTypes,
        int[] system, int[] available, boolean[] finished,
        int[][] allocation, int[][] need, int[][] claim){

            ArrayList<Integer> safe = new ArrayList<>();

            for(int i = 0; i < numProcesses; i++){
                if(!finished[i] && canRun(need, available, i)){
                    finished[i] = true;

                }
            }
        }
}