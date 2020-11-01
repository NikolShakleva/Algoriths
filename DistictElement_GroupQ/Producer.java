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

        
		final List<Integer> vals = new ArrayList<>();

		switch (mode) {

            case "random": 
                for(int i = 0; i<N; i++) {
                    vals.add(mynextInt(R));
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
    
    