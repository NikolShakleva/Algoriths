import java.util.*;

/**
 * Sort
 */
public class Sort {

    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        int N = sc.nextInt();
        int [] list = new int [N];
        for(int i=0; i<N; i++) {
            list[i] = sc.nextInt();
        }
        Arrays.sort(list);
        int min = list[0];
        System.out.println(min);

        sc.close();
        
    }
}