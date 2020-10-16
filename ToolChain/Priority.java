import java.util.*;
import edu.princeton.cs.algs4.*;
public class Priority {
    
    
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        int N = sc.nextInt();
        MinPQ<Integer> pq = new MinPQ<>();
        for(int i=0; i<N; i++) {
             pq.insert(sc.nextInt());
        }
        int min = pq.min();
        System.out.println(min);

        sc.close();
    }

}
