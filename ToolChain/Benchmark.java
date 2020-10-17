public class Benchmark {

    private static double sdev;
    private static double mean;
    
    public static long warmUp (String input, int n, String algorithm) {
        long dummy = 0;
        for(int i = 0; i< n; i++ ) {
            if(algorithm.equals("Sort"))
             {
                var sort = new Sort();  
                dummy = sort.sortInput(input);
             } else if (algorithm.equals("Priority")) {
                var priority = new Priority();
                dummy = priority.sortInput(input);
             }
        }
        return dummy;
    } 

    public static long run(String input, int count, int n, String algorithm) {

            double st = 0.0;
            double sst  = 0.0;
            long dummy = 0;
            for(int i=0; i< n; i++) {
                Timer t = new Timer();
                for(int j=0; j< count; j++) {
                    if(algorithm.equals("Sort"))
                    {
                        var sort = new Sort();
                        dummy = sort.sortInput(input);
                    } else if (algorithm.equals("Priority")) {
                        var priority = new Priority();
                        dummy = priority.sortInput(input);
                    }
                }
                double time = t.check() * 1e9 / count;
                st += time;
                sst += time * time;
        }
        mean = st/n;
        sdev = Math.sqrt((sst - mean*mean*n)/(n-1));
        System.out.printf("%6.1f ns +/- %6.3f %n", mean, sdev);
        return dummy;
        }

    public static double getSdev() {
        return sdev;
    }

    public static double getMean() {
        return mean;
    }
    }


