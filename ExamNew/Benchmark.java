
/***********************************************************
 * BENCHMARK
 * 
 * @author  Emelie Skörvald emsk@itu.dk
 * @author  Nikol Shakleva  nikv@itu.dk
 * @author  Szilvia Gaspar  szga@itu.dk
 * 
 ***********************************************************/
public class Benchmark {

    private static double[] sdev;
    private static double[] mean;
    private static Timer[] t;
    
    /**
     * Warmup
     * 
     * Warming up the algorithms that will be tested before executing the real test
     * During the test there will also be a correctness test to make sure that they
     * are correct.
     * 
     * @param inputArray    String with input data
     * @param inputPred     String with prediction data
     * @param n             integer of how many times the test will be ran
     * @param algo          a String array with all the algorithms that will be tested
     * @return              a string Failure or Success
     */
    public static String warmUp (String inputArray, String inputPred, int n, String[] algo) {
        int algorithms = algo.length;
        String dummy[] = new String[algorithms];
        
        for(int i = 0; i < n; i++ ) {
            for(int j = 0; j < algorithms ; j++ ) {
                Search search = searchObject(algo[j], inputArray, 10);  

                dummy[j] = search.pred(inputPred);
            }

            if(!correctnessTest(algorithms, dummy)) return "Failure";
        }
        return "Success";
    } 

    /**
     * @param inputArray    String with input data
     * @param inputPred     String with prediction data
     * @param n             integer of how many times the test will be ran
     * @param algo          a String array with all the algorithms that will be tested    
     * @param K             integer of how many buckets the tabular algorithm will store
     * @return              a string Failure or Success  
     */
    public static String run(String inputArray, String inputPred, int iterations, int n, String[] algo, int K) {

        int algorithms = algo.length;
        double[] st    = new double[algorithms];
        double[] sst   = new double[algorithms];
        String[] dummy = new String[algorithms];

        sdev           = new double[algorithms];
        mean           = new double[algorithms];
        t              = new Timer[algorithms];
        

        for(int i=0; i< n; i++) {
            for(int j = 0; j < algorithms ; j++) t[j] = new Timer();
            for(int k = 0; k < iterations ; k++) {
                for(int j = 0; j < algorithms ; j++ ) {

                    Search search = searchObject(algo[j], inputArray, K);  

                    t[j].play();
                    dummy[j] = search.pred(inputPred);
                    t[j].pause();
                }
                if(!correctnessTest(algorithms, dummy)) return "Failure";
            }
            addTime(algorithms, iterations, st, sst);
        }
        calculateResult(algo, n, st, sst);

        return "Success";
    }


    /**
     * 
     * @param algo
     * @param input
     * @param K
     * @return
     */
    public static Search searchObject(String algo, String input, int K){
        Search search;
        if   (algo.equals("BinarySearch")) search = new BinarySearch(input);
        else                               search = new Tabulation(input, K); 
        return search;
    }

    /**
     * 
     * @param a
     * @param dummy
     * @return
     */
    public static Boolean correctnessTest(int a, String[] dummy){
        for(int l = 0 ; l < a-1 ; l++) {
            if(!dummy[l].equals(dummy[l+1])) {
                System.out.println("Failed, Algorithms are not correct");
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param a
     * @param i
     * @param st
     * @param sst
     */
    public static void addTime(int a, int i, double[] st, double[] sst) {
        for(int m = 0; m < a ; m++){    
            double time = t[m].check() / i;
            st[m]  += time;
            sst[m] += time * time;
        }
    }

    /**
     * 
     * @param a
     * @param n
     * @param st
     * @param sst
     */
    public static void calculateResult(String[] a, int n, double[] st, double[] sst){
        for(int i = 0; i < a.length ; i++) {
            mean[i] = (st[i]/n)/ 1000;
            sdev[i] = (Math.sqrt((sst[i] - mean[i] * mean[i] * n)/(n-1)))/1000;

            System.out.printf( "%12s  %6.1f mu  +/-  %6.3f %n", a[i], mean[i], sdev[i]);
        }
    }

    /**
     * 
     * @return
     */
    public static double[] getMean(){
        return mean;
    }

    /**
     * 
     * @return
     */
    public static double[] getSdev(){
        return sdev;
    }


}




