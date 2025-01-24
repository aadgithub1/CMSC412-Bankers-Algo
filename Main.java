import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        try{
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);
            int[] items = new int[2];
            for(int i = 0; i < 2; i++){
                items[i] = scanner.nextInt();
            }
            for(int item : items){
                System.out.println(item);
            }
            scanner.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}