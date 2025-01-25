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

            for (int av : available) {
                System.out.print(av);
            }
            System.out.println();

            //TO-DO
            //Make it find exactly 2 sequences
            //Make UI
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

            // boolean[] finished = new boolean[nProcesses];
            ArrayList<Integer> tempSeq = new ArrayList<>();
            ArrayList<Integer> seq = new ArrayList<>();
            ArrayList<Integer> seq2 = new ArrayList<>();
            int[] work = available.clone();
            boolean foundTwoSeqs = false;
            while(seq.size() < nProcesses || seq2.size() < nProcesses){
                for(int i = 0; i < nProcesses; i++){
                    for(int j = 0; j < mResourceTypes; j++){
                        if(claim[i][j] <= work[j] + allocation[i][j]){
                            if(j == mResourceTypes - 1 && !tempSeq.contains(i)){
                                tempSeq.add(i);
                                // finished[i] = true;
                                for(int q = 0; q < mResourceTypes; q++){
                                    work[q] += allocation[i][q];
                                    System.out.print(work[q]);
                                    
                                }
                                System.out.println();
                            }
                        } else{
                            break;
                        }
                        System.out.println("temp " + tempSeq);
                        System.out.println("seq " + seq);
                        System.out.println("seq2 " + seq2);
                    }
                    // System.out.println("temp: " + tempSeq.size());
                }
                
                if(tempSeq.size() == nProcesses){
                    if(seq.isEmpty()){
                        seq = new ArrayList<>(tempSeq);
                        // for(int thing : seq){
                        //     System.err.println("thing: " + thing);
                        // }
                        tempSeq.clear();
                        work = available.clone();
                    } else if(!seq.isEmpty()){
                        seq2 = new ArrayList<>(tempSeq);
                        tempSeq.clear();
                        foundTwoSeqs = true;
                    }
                }
                if(foundTwoSeqs){
                    break;
                }
            }
            scanner.close();
            for(Integer process : seq){
                System.err.println("process " + (process+1));
            }
            for(Integer process : seq2){
                System.err.println("process2 " + (process+1));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}