import java.util.Arrays;
import java.util.Scanner;

/***********************************************************
 * TABULATION
 * 
 * @author Emelie Skörvald  emsk@itu.dk
 * @author Nikol Shakleva   nikv@itu.dk
 * @author Szilvia Gaspar   szga@itu.dk
 ***********************************************************/

public class Tabulation implements Search {

    int k = 10;
    int [] A;
    int[][] table;
    int size;
    int buckets;
    StringBuilder sb = new StringBuilder();


    public Tabulation(String input, int K){
        k = K;
        buckets = (int) Math.pow(2, k);
        makeTabulation(input);
        createTable();
    }

    /**
     * 
     * @param input
     */
    public void makeTabulation(String input){

        var sc = new Scanner(input);
        size = sc.nextInt();
        A = new int[size];

        for (int i = 0; i< size; i++){
            A[i] = sc.nextInt();
        }
        Arrays.sort(A);
        sc.close();
    }

	/**
     * Creates a Look-up table containing 2^k indexes each
     * containing entries for the smalles and biggest 
     * index for numbers in the specific range
     */
    public void createTable(){

        table = new int[buckets][2];
        for (int j = 0; j < buckets; j++){
            // we fill all the positions in the array with minus infinity (instead of 0)
            table[j][0] = Integer.MAX_VALUE;
            table[j][1] = Integer.MIN_VALUE;
        }
        
        for(int i = 0; i < size ; i++) {
            int current = A[i];
            int index = kthMostInteger(current);
            if(table[index][0] > i) table[index][0] = i;
            if(table[index][1] < i) table[index][1] = i;
        }
    }

    /**
     * 
     * @param x integer that should be added to the lookup table
     * @return an index in the lookup table
     */
    public int kthMostInteger(int x){
        int shift = 32 - k;
        int res = x >> shift;
        if (res >= buckets/2) res = res - (buckets/2);
        else                  res = res + (buckets/2);
        
        return  res;
    }

    /**
     * 
     * @param input the integer that should be found
     * @return returns the integer y <= x else None
     */
    public String pred(String input){
        var sc = new Scanner(input);

        while (sc.hasNextInt()){
            int x = sc.nextInt();

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
        sc.close();
        return sb.toString();   
    }

}