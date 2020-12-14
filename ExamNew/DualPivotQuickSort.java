import java.util.Arrays;

public class DualPivotQuickSort {
    public static void dualSort(int[] A, int left, int right){
        
        //assume a sentinel A[0] = minus infinity
        if (right - left >= 1){
            int p = A [left];
            int q = A [right];
            
            if (p > q){             // TODO: 
                int temp = p;
                p = q;
                q = temp;
                A[left] = p;
                A[right] = q;
            }
            int l = left + 1;
            int g = right -1;
            int k = l;
            
            
            while (k <= g){
                //SWAP
                if (A[k] < p) {         //TODO : maybe has to change A[k]
                    int temp = A[k];
                    A[k] = A[l];
                    A[l] = temp;
                    l = l +1;
                }
                else {
                    if (A[k] > q){
                        while ((A[g] > q) &&  (k <g)){
                            g = g-1;
                        }
                        // SWAP A[k] and A[g] and decrement g
                        int temp = A[k];
                        A[k] = A[g];
                        A[g] = temp;
                        g = g-1;
                        if (A[k] < p ){
                            //SWAP A[k] and A[l] and increment l
                            int dummy = A[k];
                            A[k] = A[l];
                            A[l] = dummy;
                            l = l+1;
                        }
                    }
                }
                k = k +1;
            }
            l = l-1;
            g = g+1;
            
            //2 Swaps
            int temp = A[left];
            A[left] = A[l];
            A[l] = temp;

            int dummy = A[right];
            A[right] = A[g];
            A[g] = dummy;
            
            dualSort(A, left, l-1);
            dualSort(A, l+1, g-1);
            dualSort(A, g+1, right);

        }
    }


    public static void main(String[] args) {
        int[] A = {130, 15, 13, 110, 12, -125, 126, 14};

        dualSort(A, 0, A.length-1);
        System.out.println(Arrays.toString(A));
    }
    
}
