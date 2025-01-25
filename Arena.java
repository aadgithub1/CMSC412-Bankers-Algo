import java.util.ArrayList;

public class Arena {
    public static void main(String[] args) {
        // ArrayList<Integer> list2 = new ArrayList<>();
        // while(true){    
        //     ArrayList<Integer> list = new ArrayList<>();
        //     list.add(2);
        //     list.add(3);
        //     list.add(4);
        //     list.add(5);

        //     if(list2.isEmpty()){
        //         list2 = new ArrayList<>(list);
        //         list2.set(2, 12);
        //         list2.set(3, 13);

        //     }
        //     list.clear();
        //     System.out.println(list.isEmpty());
        //     for(Integer i : list){
        //         System.out.println(i);
        //     }

        //     for(Integer i : list2){
        //         System.out.println(i);
        //     }
        //     break;
        // }

        int[] nums = new int[5];
        for(int i = 0; i < nums.length; i++){
            nums[i] = i;
        }

        for(int i = 0; i < nums.length; i++){
            System.out.println(nums[i]);
        }

        int[] num2 = nums;
        num2[2] = 18;
        num2[4] = 24;
        for(int i = 0; i < num2.length; i++){
            System.out.println(num2[i]);
        }

    }
    
}
