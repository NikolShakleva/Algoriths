import java.util.*;
/**
 * Producer
 */
public class Producer {

    public static int mynextInt(Random R) {
		return R.nextInt();
    }
    

        public static String generate (String mode1, int N1, int buckets1) {
        final String mode = mode1;
        final int N = N1;
        final Random R = new Random();
        final int buckets = buckets1;
        // R.setSeed(seed1+ N);

        
		final List<Integer> vals = new ArrayList<>();

		switch (mode) {
            case "same":
            vals.add(mynextInt(R));
            for(int i = 1; i<N; i++) {
                vals.add(vals.get(0));
         }
                break;
            case "random": 
                for(int i = 0; i<N; i++) {
                    vals.add(mynextInt(R));
         }
                break;
            case "half-distinct":
            // for every random number we add 2 numbers similar to the random number
                for(int i = 0; i + 3 <N; i+=2) {
                    vals.add(mynextInt(R));
                    vals.add(vals.get(0));
                    
         }
        //  if the size of the array list is smaller then N we need to fill the arrays list 
            if(vals.size()!=N) {
                for(int i = vals.size()-1; i<N; i++) {
                    vals.add(mynextInt(R));
         }
        }
				break;
			default:
				System.err.println("Unknown mode: " + mode);
		}

        StringBuilder sb = new StringBuilder();
        sb.append(buckets + "\n");
        for (int i = 0; i<N; ++i) sb.append(vals.get(i) + " ");
        
        return sb.toString();
        
    }
    }
    
    