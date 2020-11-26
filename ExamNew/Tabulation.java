import java.util.Arrays;
import java.util.Scanner;

/**
 * TABULATION
 * 
 * @author Emelie Skörvald  emsk@itu.dk
 * @author Nikol Shakleva   
 * @author Szilvia Gaspar   szga@itu.dk
 */

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

    /**
     * Creates a Look-up table containing 2^k indexes each
     * containing entries for the smalles and biggest 
     * index for numbers in the specific range
     */
    public void createTable(){
        
        for(int i = 0; i < size ; i++) {
            int current = A[i];
            int index = kthMostInteger(current);
            if(table[index][0] > i) table[index][0] = i;
            if(table[index][1] < i) table[index][1] = i;
            counter[index] = 1;
        }
    }

    /**
     * 
     * @param x integer that should be added to the lookup table
     * @return an index in the lookup table
     */
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

    /**
     * 
     * @param input the integer that should be found
     * @return returns the integer y <= x else None
     */
    public String pred(String input){
        sc1 = new Scanner(input);

        while (sc1.hasNextInt()){
            int x = sc1.nextInt();

            int index = kthMostInteger(x);
            if (table[index][0] == Integer.MAX_VALUE){
                while(table[index][0] == Integer.MAX_VALUE && index > 0) {
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

}