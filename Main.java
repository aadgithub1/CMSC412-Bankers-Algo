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

            
            ArrayList<Integer> validStartPoints = new ArrayList<>();
            //create a list of potential safe starting points
                //if need for this process and this resource < available + allocation
                    //add to list

                //for each item in the list
                    //print item i is a valid starting point


            for(int k = 0; k < nProcesses; k++){
                for(int l = 0; l < mResourceTypes; l++){
                    if(need[k][l] <= allocation[k][l]){
                        if(l == mResourceTypes - 1 && !validStartPoints.contains(k)){
                            validStartPoints.add(k);
                            
                        }
                    }
                }
            }

            ArrayList<Integer> seq = new ArrayList<>();
            int zeroCounter = 0;
            while(seq.size() < nProcesses){
                for(int i = 0; i < nProcesses; i++){
                    for(int j = 0; j < mResourceTypes; j++){
                        if(i == 0){
                            zeroCounter++;
                        }
                        if(need[i][j] <= available[j]){
                            if(j == mResourceTypes - 1 && !seq.contains(i)){
                                seq.add(i);
                                for(int q = 0; q < mResourceTypes; q++){
                                    available[q] += allocation[i][q];
                                    // System.out.print(available[q]);
                                }
                            }
                        } else{
                            break;
                        }
                    }
                }
                if(zeroCounter > 10){
                    System.out.println("infinite");
                    break;
                }
            }
            scanner.close();
            System.out.println("the sequence is: ");
            for(int i = 0; i < seq.size(); i++){
                System.out.println((seq.get(i)+1) + ", ");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}