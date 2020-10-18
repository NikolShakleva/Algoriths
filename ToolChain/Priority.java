import java.util.*;
public class Priority implements SortingAlgorithm {
    
    
    public long sortInput(String input) {
        Scanner sc =  new Scanner(input);
        int N = sc.nextInt();
        PriorityQueue<Long> pq = new PriorityQueue <> ();
        for(int i=0; i<N; i++) {
             pq.offer(sc.nextLong());
        }
        sc.close();

        return pq.remove();
    }

}

