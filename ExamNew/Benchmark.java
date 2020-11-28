
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
     * run
     * 
     * Running the benchmark for the algorithms, all algorithms run after eachother and
     * the output is sent to a correctness test to make sure that they are correct.
     * 
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
     * searchObject
     * 
     * Instanciates an object of the algorithm that should be tested and stores them in a
     * Search array. 
     * To add another algorithm to the test write another if else statement below and to the
     * algorithms array in the Experiment class
     * 
     * @param algo      String of the algorithms that should be tested
     * @param input     a String with values to use for the array A in the search object
     * @param K         int of how many buckets Tabulation will use
     * @return          the searchObject
     */
    public static Search searchObject(String algo, String input, int K){
        Search search;
        if   (algo.equals("BinarySearch")) search = new BinarySearch(input);
        else                               search = new Tabulation(input, K); 
        return search;
    }

    /**
     * correctnessTest
     * 
     * Tests that all the output of the algorithms are the same. If they are not
     * that means that one or more algorithms are incorrect.
     * 
     * @param a         int of how many algorithms are in the test
     * @param dummy     an array with the output from the algortihms
     * @return          a boolean if the output are the same
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
     * addTime
     * 
     * adds the running time for each iteration together for the individual
     * algorithms.
     * 
     * @param a     int of how many algorithms are in the test
     * @param i     int of iterations
     * @param st    a double array with st for each algorithm
     * @param sst   a double array with sst for each algorithm
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
     * @param a     int of how many algorithms are in the test
     * @param n     int of iterations
     * @param st    a double array with st for each algorithm
     * @param sst   a double array with sst for each algorithm
     */
    public static void calculateResult(String[] a, int n, double[] st, double[] sst){
        for(int i = 0; i < a.length ; i++) {
            mean[i] = (st[i]/n)/ 1000;
            sdev[i] = (Math.sqrt((sst[i] - mean[i] * mean[i] * n)/(n-1)))/1000;

            System.out.printf( "%12s  %6.1f mu  +/-  %6.3f %n", a[i], mean[i], sdev[i]);
        }
    }

    /**
     * getMean
     * 
     * @return  an array with the means of the test for each algorithm
     */
    public static double[] getMean(){
        return mean;
    }

    /**
     * getSdev
     * 
     * @return  an array with the standard diviation of the test for each algorithm
     */
    public static double[] getSdev(){
        return sdev;
    }


}




