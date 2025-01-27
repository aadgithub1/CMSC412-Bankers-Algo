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

            boolean[] finished = new boolean[nProcesses];
            ArrayList<Integer> safeSeq = new ArrayList<>();

            findSafeSeq(nProcesses, mResourceTypes, systemResources,
            available, finished, safeSeq, allocation, need, claim);
            
            scanner.close();
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
        int[] system, int[] available, boolean[] finished, ArrayList<Integer> safe,
        int[][] allocation, int[][] need, int[][] claim){

        for(int i = 0; i < numProcesses; i++){
            if(!finished[i] && canRun(need, available, i)){
                finished[i] = true;

                for(int j = 0; j < numResourceTypes; j++){
                    available[j] += allocation[i][j];
                }

                safe.add(i);
                findSafeSeq(numProcesses, numResourceTypes, system, available,
                finished, safe, allocation, need, claim);
                safe.remove(safe.size()-1);

                for(int j = 0; j < numResourceTypes; j++){
                    available[j] -= allocation[i][j];
                }

                finished[i] = false;
            }

            if(safe.size() == numProcesses){
                System.out.println("the sequence is:");
                for(Integer item : safe){
                    System.out.println(item+1);
                }
                return;
            }
        }
    }
}