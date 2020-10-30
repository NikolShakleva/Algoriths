import java.util.*;

/**
 * Sort
 */
public class Sort implements SortingAlgorithm{

    public long sortInput(String input) {
        Scanner sc =  new Scanner(input);
        int N = sc.nextInt();
        long [] list = new long [N];
        for(int i=0; i<N; i++) {
            list[i] = sc.nextLong();
        }
        sc.close();
        
        Arrays.sort(list);
        return list[0];
        
    }
}