import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{
    static int nProcesses;
    static int mResourceTypes;
    public static void main(String[] args) {
        try{
            for(int m = 0; m < 2; m++){
                File file = new File("input.txt");
                Scanner scanner = new Scanner(file);
                nProcesses = scanner.nextInt();
                mResourceTypes = scanner.nextInt();
                Random random = new Random();
                int randint = random.nextInt(nProcesses);
                
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

                // for(int item : available){
                //     System.out.print(item);
                // }
                // System.out.println();


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

                ArrayList<Integer> seq = new ArrayList<>();
                while(seq.size() < nProcesses){
                    for(int i = randint; i < nProcesses; i++){
                        for(int j = 0; j < mResourceTypes; j++){
                            if(claim[i][j] <= available[j] + allocation[i][j]){
                                if(j == mResourceTypes - 1 && !seq.contains(i)){
                                    seq.add(i);
                                    for(int q = 0; q < mResourceTypes; q++){
                                        available[q] += allocation[i][q];
                                        // System.out.print(available[q]);
                                    }
                                    // System.out.println();
                                }
                                continue;
                            } else{
                                break;
                            }
                        }
                    }
                }
                scanner.close();
                for(Integer process : seq){
                    System.err.println("process " + (process+1));
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}