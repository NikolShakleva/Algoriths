import java.util.Arrays;

/**
 * ThreePivotQuickSort
 */
public class ThreePivotQuickSort {

    public static void threeSort(int[] A, int left, int right){

    if (right - left >= 1){

        int a = left + 2;
        int b = left + 2;

        int c = right -1;
        int d = right -1;

        int p = A[left];
        int q = A[left +1];
        int r = A[right];

            //EXTRA///////////////////////
        // while (!(p < q )&& (q< r)){
            if (p > r){
            int temp = p;
            p = r;
            r = temp;
            A[left] = p;
            A [right] = r;
            }

            if (p > q){             // TODO: 
                int temp = p;
                p = q;
                q = temp;
                A[left] = p;
                A[left + 1] = q;
            }

            if (q > r){
                int temp = q; 
                q = r;
                r = temp;
                A[left + 1] = q;
                A[right] = r;
            }
        // }
        //////////////////////////////////////////////
        while (b <= c){
            while (A[b] < q && b<c){
                if(A[b]< p){
                    //SWAP (A[a], A[b])
                    int temp = A[a];
                    A[a] = A[b];
                    A[b] = temp;
                    a = a + 1;
                }
                b = b + 1;
            }

            while (A[c] > q && b<= c){
                if (A[c] > r){
                    //SWAP (A[c], A[d])
                    int temp = A[c];
                    A[c] = A[d];
                    A[d] = temp;
                    d = d - 1;
                }
                c = c - 1;
            }
            if (b <= c){
                if (A[b] > r){
                    if (A[c] < p){
                        // swap (A[b],A[a]), swap (A[a],A[c])
                        int temp = A[b];
                        A[b] = A[a];
                        A[a] = temp;

                        int dummy = A[a];
                        A[a] = A[c];
                        A[c] = dummy;
                        //increment a
                        a = a +1;
                    }
                    else {
                        //swap(A[b],A[c])
                        int temp = A[b];
                        A[b] = A[c];
                        A[c] = temp;
                    }
                    //swap(A[c],A[d])
                    int temp = A[c];
                    A[c] = A[d];
                    A[d] = temp;
                    b = b +1;
                    c = c-1;
                    d = d-1;
                }
                else {
                    if (A[c] < p){
                        //swap(A[b],A[a]), swap(A[a],A[c])
                        int temp = A[b];
                        A[b] = A[a];
                        A[a] = temp;

                        int dummy = A[a];
                        A[a] = A[c];
                        A[c] = dummy;
                        
                        a = a +1;
                    }
                    else {
                        //swap(A[b],A[c])
                        int temp = A[b];
                        A[b] = A[c];
                        A[c] = temp;
                    }
                    b = b + 1;
                    c = c - 1;
                }
            }
        }
        a = a - 1;
        b = b - 1;
        c = c + 1; 
        d = d + 1;
        //swap(A[left + 1],A[a]), swap(A[a],A[b])
        int temp = A [left + 1];
        A [left +1] = A[a];
        A[a] = temp;

        int dummy = A[a];
        A[a] = A[b];
        A[b] = dummy;
        a = a - 1;
        
        //swap(A[left],A[a]), swap(A[right],A[d])
        int holder = A[left];
        A[left] = A[a];
        A[a] = holder;

        int keeper = A[right];
        A[right] = A[d];
        A[d] = keeper;

        // threeSort(A, left, a-2);
        // threeSort(A, a+2, b-2);
        // threeSort(A, b+2, d-1);
        // threeSort(A, d+1, right);

        threeSort(A, left, a-1);
        threeSort(A, a+1, b-1);
        threeSort(A, b+1, d-1);
        threeSort(A, d+1, right);


    }
    }

    public static void main(String[] args) {
        // int[] A = {130, 15, 13, 110, 12, -125, 126, 14};

        // int[] A = {-130, -15, 13, 110, 12, -125, 126, 14, 345, -23, 5, 7, 10, 43};

        int[] A = {-130, -15, 13, 110, 12, -125, 126, 14, 345, -23, 5, 7, 10, 43, 345, -14535, 45,234, 8674,1,-5};



        threeSort(A, 0, A.length-1);
        System.out.println(Arrays.toString(A));
    }
}