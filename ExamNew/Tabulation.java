import java.util.Arrays;
import java.util.Scanner;

public class Tabulation implements Search {

    int k = 10;
    int [] A;
    int[][] table;
    Scanner sc;
    Scanner sc1;
    int size;
    int buckets = (int) Math.pow(2, k);
    int[] counter = new int[buckets]; 
    StringBuilder sb = new StringBuilder();


    public Tabulation(String input){

        table = new int[buckets][2];
    

        for (int j = 0; j < buckets; j++){
            
                // we fill all the positions in the array with minus infinity (instead of 0)
                table[j][0] = Integer.MAX_VALUE;
                table[j][1] = Integer.MIN_VALUE;
        }

        sc = new Scanner(input);
        size = sc.nextInt();

        A = new int[size];

        for (int i = 0; i< size; i++){
            A[i] = sc.nextInt();
        }

        Arrays.sort(A);
        
        createTable();

    }
    

    public void createTable(){
        
        for(int i = 0; i < size ; i++) {
            int current = A[i];
            int index = kthMostInteger(current);
            if(table[index][0] > current) table[index][0] = i;
            if(table[index][1] < current) table[index][1] = i;
            counter[index] = 1;
        }
    }

    // public int[] resize(int[] in){
    //     int oldSize = in.length;
    //     int newSize = oldSize * 2;
    //     int[] temp = new int[newSize];
    //     for (int i = 0; i < oldSize;i++){
    //         temp[i] = in[i];
    //     }
    //     for(int k = oldSize ; k < newSize ; k++){
    //         temp[k] = Integer.MIN_VALUE;
    //     }
    //     return temp;
    // }

    // public int minBit(){
    //     int temp = 0;
    //     int shift = 32-k;
    //     for (int i = shift; i<32; i++ ){
    //         temp = 1 << i | temp;
    //     }
    //     return temp;
    // }

    // public int maxBit(){
    //     int temp = 0;
    //     int shift = 32-k;
    //     temp = (1 << shift)-1;
    //     return temp;
    // }

    public int kthMostInteger(int x){
        int shift = 32- k;
        int res = x >> shift;
        if (res >= buckets/2){
            res = res - buckets/2;
        }
        else {
            res = res + buckets/2;
        }
        return  res;
    }

    public String pred(String input){
        sc1 = new Scanner(input);

        while (sc1.hasNextInt()){
            int x = sc1.nextInt();

            int index = kthMostInteger(x);
            if (table[index][0] == Integer.MAX_VALUE){
                while(table[index][0] == Integer.MAX_VALUE && index > 0){
                    index = index - 1;
                }
            }
            if(table[index][0] != Integer.MAX_VALUE) {
                int left  = table[index][0];
                int right = table[index][1] + 1;

                int result = BinarySearch.indexOf(A, x, left, right);

                if (result == Integer.MIN_VALUE && left == 0)   sb.append("None ");
                else if (result != Integer.MIN_VALUE)           sb.append(A[result] + " ");
                else                                            sb.append(A[left-1] + " ");
            }
            else sb.append("None ");
            
        }
        return sb.toString();   
    }

    // public static void main(String[] args) {

    //     Tabulation st = new Tabulation();

    // }
//}



}