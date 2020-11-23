    


    import java.util.*;
/**
 * Producer
 */
public class Producer {

    public static long mynextLong(Random R) {
		return R.nextLong() / 4;
    }
    

        public static String generate (String[] args) {
        final String mode = args[0];
        // final String mode = "random";
        // final int N = 15;
        final int N = Integer.parseInt(args[1]);
        final Random R = new Random();
        // R.setSeed(Long.parseLong("1234") + N);
        R.setSeed(Long.parseLong(args[2] + N));


        if( N <= 1) {
			System.err.println("The list is alredy sorted "+N);
			System.exit(1);
		}

		final List<Long> vals = new ArrayList<>();

		vals.add(mynextLong(R));

		switch (mode) {
            case "sorted": 
                for(int i =1; i<N; i++) {
                   vals.add(vals.get(i-1) + 1);
                }
				break;
            case "backwards":
                for(int i =1; i<N; i++) {
                    vals.add(vals.get(i-1) - 1);
             }
				break;
            case "same":
            for(int i =1; i<N; i++) {
                vals.add(vals.get(0));
         }
                break;
            case "random": 
                for(int i =1; i<N; i++) {
                    vals.add(mynextLong(R));
         }
                break;
            case "semi-sorted":
            // for every random number we add 2 numbers similar to the random number
                for(int i =1; i + 3 <N; i+=3) {
                    vals.add(mynextLong(R));
                    vals.add(vals.get(i)+1);
                    vals.add(vals.get(i)+3);
         }
        //  if the size of the array list is smaller then N we need to fill the arrays list 
            if(vals.size()!=N) {
                for(int i = vals.size()-1; i<N; i++) {
                    vals.add(mynextLong(R));
         }
        }
				break;
			default:
				System.err.println("Unknown mode: " + mode);
		}

        StringBuilder sb = new StringBuilder();
        sb.append(N + " ");
        for (int i = 0; i<N; ++i) sb.append(vals.get(i) + " ");
        
        return sb.toString();
        
    }
    }
    
    
    