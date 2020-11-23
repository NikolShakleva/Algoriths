import java.util.*;
import java.util.stream.IntStream;
/**
 * Producer
 */
public class Producer {
    

        public static String generate (String[] args) {

        final String mode = args[0];
        final int N = Integer.parseInt(args[1]);
        final Random R = new Random();
        R.setSeed(Integer.parseInt(args[2] + N));
        StringBuilder sbvals = new StringBuilder();
      


        final List<Integer> vals = new ArrayList<>();

		vals.add(R.nextInt());

		switch (mode) {
            // Make sure that the pred does not exist in the vals and the algorithm will go through empty buckets before returning None
            case "non-existent": 
                sbvals.append(N + " ");
                // Making N random large numbers in range from 47_483_647 to 2_147_483_647
                IntStream largeNumbers = R.ints(N, 47_483_647, 2_147_483_647);
                largeNumbers.forEach(e -> vals.add(e));
                break;

            case "non-existent pred": 
                // Making N random large numbers in range from 47_483_647 to 2_147_483_647
                // Making N random smaller numbers than in vals in range from 0 to 47_483_647
                IntStream smallNumbers = R.ints(N, 0, 47_483_646);
                smallNumbers.forEach(e -> vals.add(e));
            break;
                
            // case "All in One":
            //     for(int i =1; i < N; i++) {
            //         vals.add(vals.get(i-1) - 1);
            //  }
            //     break;

            case "random": 
                sbvals.append(N + " ");
                for(int i =1; i<N; i++) {
                    vals.add(R.nextInt());
            }
                break;

            
                case "random pred": 
                for(int i = 1; i < N; i++) {
                    vals.add(R.nextInt());
                }
                break;

			default:
				System.err.println("Unknown mode: " + mode);
		}

        for (int i = 0; i < N; ++i) sbvals.append(vals.get(i) + " ");
 
        
        return sbvals.toString();
        
        }
    }