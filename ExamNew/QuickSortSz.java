import java.util.Arrays;

public class QuickSortSz {

    // assume a sentinel A[0]= minus infinity
    public static void sort(int[] A, int left, int right) {

        if (right - left >= 1){
            int p = A[right];
            int i = left- 1;        
            int j = right;

            //do
            while (j > i){
                while (A[i] < p){
                    i = i +1;
                }
                while (A[j] > p){
                    j = j -1;
                }
                if (j > i){
                    int temp = A[i];
                    A[i] = A[j];
                    A[j] = temp;  
                }
            }

            while (j > i){
                int temp = A[i];
                A[i] = A[right];
                A[right] = temp;
            }   
            sort(A, left, i-1);
            sort(A, i +1, right); 
        }
    }


    public static void main(String[] args) {
        int[] A = {30, 5, 3, 10, 2, -25, 26, 4};

        sort(A, 1, A.length-1);
        System.out.println(Arrays.toString(A));
    }
    
}
