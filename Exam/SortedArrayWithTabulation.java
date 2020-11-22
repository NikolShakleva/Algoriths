import java.util.Arrays;
import java.util.Scanner;

/**
 * SortedArrayWithTabulation
 */
public class SortedArrayWithTabulation {

    int k = 10;
    int [][] A;
    Scanner sc;
    int size;
    int buckets = (int) Math.pow(2, k);


    public SortedArrayWithTabulation(){

        A = new int[buckets][k];

        for (int j = 0; j< buckets; j++){
            for (int l = 0; l<k; l++){
                // we fill all the positions in the array with minus infinity (instead of 0)
                A[j][l] = Integer.MIN_VALUE;
            }
        }

        sc = new Scanner(System.in);
        size = sc.nextInt();

        for (int i = 0; i< size; i++){
            int x = sc.nextInt();
            createTable(x);
        }

        while (sc.hasNextInt()){
            int number = sc.nextInt();
            search(number);
        }
    }

    public void createTable(int x){
        // int min = minBit();
        // int max = maxBit();
        // int resultMin = min & x;
        // int resultMax = max | x;
        int index = kthMostInteger(x);
        for (int i = 0; i< k; i++){
            if(A[index][i]== Integer.MIN_VALUE){
                A[index][i] = x;
                Arrays.sort(A[index]);
                break;
            }
        }

        // System.out.println("resultMin is: "+ resultMin);
        // System.out.println("resultMax is: " +resultMax);
    }

    public int minBit(){
        int temp = 0;
        int shift = 32-k;
        for (int i = shift; i<32; i++ ){
            temp = 1 << i | temp;
        }
        return temp;
    }

    public int maxBit(){
        int temp = 0;
        int shift = 32-k;
        temp = (1 << shift)-1;
        return temp;
    }

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

    public void search(int x){
        int index = kthMostInteger(x);
        int result = BinarySearch.indexOf(A[index], x);
        while (result == Integer.MIN_VALUE || A[index][result] == Integer.MIN_VALUE &&  index != 0){
                index = index- 1;
                result = BinarySearch.indexOf(A[index], x);
        }
        if (result == Integer.MIN_VALUE || A[index][result] == Integer.MIN_VALUE){
        System.out.print("None ");
        }

        else{
        System.out.print(A[index][result] + " ");
        }
        
    }

    public static void main(String[] args) {

        SortedArrayWithTabulation st= new SortedArrayWithTabulation();

    }
}