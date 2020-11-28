import java.util.Arrays;
import java.util.Scanner;

/**
 * SortedArrayWithTabulation
 */
public class SortedArrayWithTabulation {

    int k = 10;
    int [][] A;
    Scanner sc;
    Scanner sc1;
    int size;
    int buckets = (int) Math.pow(2, k);
    int[] counter = new int[buckets]; 
    StringBuilder sb = new StringBuilder();


    public SortedArrayWithTabulation(String input){

        A = new int[buckets][2];

        for (int j = 0; j < buckets; j++){
            for (int l = 0; l < 2; l++){
                // we fill all the positions in the array with minus infinity (instead of 0)
                A[j][l] = Integer.MIN_VALUE;
            }
        }

        sc = new Scanner(input);
        size = sc.nextInt();

        for (int i = 0; i< size; i++){
            int x = sc.nextInt();
            createTable(x);
        }

        for (int i = 0 ; i < buckets ; i++){
            Arrays.sort(A[i]);
        }

    }

    public void createTable(int x){
        // int min = minBit();
        // int max = maxBit();
        // int resultMin = min & x;
        // int resultMax = max | x;
        
        int index = kthMostInteger(x);
        // for (int i = 0; i< k; i++){
        //     if(A[index][i]== Integer.MIN_VALUE){
        //         A[index][i] = x;
        //         Arrays.sort(A[index]);
        //         break;
        //     }
        // }
        int ind = counter[index];
        if(A[index].length == ind) A[index] = resize (A[index]);
        

        A[index][ind] = x;
        counter[index] = ind + 1;

        // System.out.println("resultMin is: "+ resultMin);
        // System.out.println("resultMax is: " +resultMax);
    }

    public int[] resize(int[] in){
        int oldSize = in.length;
        int newSize = oldSize * 2;
        int[] temp = new int[newSize];
        for (int i = 0; i < oldSize;i++){
            temp[i] = in[i];
        }
        for(int k = oldSize ; k < newSize ; k++){
            temp[k] = Integer.MIN_VALUE;
        }
        return temp;
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

    public String pred(String input){
        sc1 = new Scanner(input);

        while (sc1.hasNextInt()){
            int x = sc1.nextInt();

            int index = kthMostInteger(x);
            int result = indexOf(A[index], x, 0, A[index].length -1);
            while (result == Integer.MIN_VALUE || A[index][result] == Integer.MIN_VALUE &&  index != 0){
                    index = index- 1;
                    result = indexOf(A[index], x, 0, A[index].length -1);
            }
            if (result == Integer.MIN_VALUE || A[index][result] == Integer.MIN_VALUE){
                sb.append("None ");
            }

            else{
            sb.append(A[index][result] + " ");
            }
        }
        return sb.toString();   
    }

            /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public int indexOf(int[] a, int key, int left, int right) {
        int lo = left;
        int hi = right;
        int closest = Integer.MIN_VALUE;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) {
                lo = mid + 1;
                if(closest == Integer.MIN_VALUE || a[mid] > a[closest]) {
                    closest = mid;
                }
            }

            else return mid;
        }
        return closest;
    }
    // public static void main(String[] args) {

    //     SortedArrayWithTabulation st = new SortedArrayWithTabulation(args[0]);

    // }
}