import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/****************************************************************************************
 * EXPERIMENT
 * 
 * Compilation:     javac Experiment.java
 * Execution:       java Experiment
 * Dependencies:    Seed.java,   Producer.java,   Benchmark.java,  Timer.java,
 *                  Search.java  Tabulation.java, BinarySearch.java
 *                  
 * 
 * This Experiment Class tests the runningtime for Binary Search.
 * A new direcotry will be made and filled with algorithm * mode files with the test data
 * 
 * To add a new algorithm to the test, make sure that it implements the Search interface,
 * add it as a string to the algorithms String array in this class
 * and add an if statement to the searchObject method in the Benchmarking Class.
 * 
 * @author  Emelie Skörvald emsk@itu.dk
 * @author  Nikol Shakleva  nikv@itu.dk
 * @author  Szilvia Gaspar  szga@itu.dk
 * 
 ***************************************************************************************/

public class Experiment {

    private static String[] algorithms      = { "Tabulation", "BinarySearch"};
    private static final String[] modeArray = { "big pred, small ints",     "big pred, small ints pred",
                                                "non-existent", "non-existent pred",
                                                "random",       "random pred", "same bucket", "same bucket pred" };
    //  private static final int[] N            = { 100, 20, 500, 1000};
    //  private static final int[] K            = { 4, 8, 10, 12 };
     private static final int[] N            = { 10_000};
      private static final int[] K            = { 10 };





    private static final int n = 2;
    private static final int seed = 1234;
    private static final int runPerSeed = 2;
    private static final int iterations = 10;
    private static String dir;
   

    public static void main(String args[]) {
        systemInfo();                                                       // Prints info about the machine doing the experiment
        warmUp();                                                           // Warmup for the Benchmark                                                              
        createDir();                                                        // Creates a directory string for test data

        // RUNNING THE EXPERIMENT
        System.out.println();
        System.out.println("Running the experiment...");
        
        for (int i = 0; i < modeArray.length; i=i+2) {                      // For each Mode
            FileWriter[] file = new FileWriter[algorithms.length];          
            StringBuilder[] sb = new StringBuilder[algorithms.length];      

            try   {                                                         // Try/catch for FileWriter
                System.out.println();
                for (int a = 0; a < algorithms.length; a++) {               // FOR EACH ALGORITHM ////////////
                    file[a] = createFile(a, i);                             // FileWriter for each algorithm
                    sb[a] = new StringBuilder();                            // Stringbuilder for each algorithm
                }

                System.out.println("All Algorithms with mode: " + modeArray[i]);
                
                int[] seedArray = Seed.createSeed(seed);                                    
                for (int l = 0; l < seedArray.length; l++) {                 // FOR EACH SEED ///////////////
                    for (int j = 0; j < N.length; j++) {                     // FOR EACH N     /////////////
                        for (int k = 0; k < K.length;k++ ){
                            System.out.println("-------------------------------------");
                            System.out.println("N: " + N[j] +", K: " + K[k] + " and Seed: " + seedArray[l]);
                            System.out.println("-------------------------------------");

                            double[] mean = new double[algorithms.length];
                            double[] sDev = new double[algorithms.length];

                            for (int r = 0; r < runPerSeed; r++) {              // We run each SEED and N several times
                                String inputArray = Producer.generate(createTestData(i,   j, seedArray[l]));
                                String inputPred  = Producer.generate(createTestData(i+1, j, seedArray[l]));

                                Benchmark.run(inputArray, inputPred, iterations, n, algorithms, K[k]);

                                double[] tempMean = Benchmark.getMean();
                                double[] tempSdev = Benchmark.getSdev();

                                for (int a = 0; a < algorithms.length; a++) {   // Adding up all the mean 
                                    mean[a] += tempMean[a];
                                    sDev[a] += tempSdev[a];
                                }
                            }
                            for(int a = 0; a < algorithms.length; a++){
                                double totalMean = mean[a] / runPerSeed;        // Dividing mean with RunPerSeed
                                double totalSdev = sDev[a] / runPerSeed;        // Dividing sDev with RunPerSeed
                                sb[a].append(N[j] + " " + totalMean + " " +     // Adding test data with N, mean, sDev
                                                        totalSdev + "\n");
                            }
                        }
                    }
                }
                addMeasurements(sb, file);                                  // Adding all the measurements to the data files
            } catch (IOException e) { e.printStackTrace();}                 // catch for the FileWriter
        }
        end();                                                              // Prints end-statment for the experiment
    }

    /**
     * Warms up the Benchmarking iterations * n times
     */
    public static void warmUp() {

        System.out.println("Running the warm-up...");
        String correctness = "";
        for (int i = 0; i < modeArray.length; i += 2) {
            
            for (int j = 0; j < 1; j++) {                                          
                String inputArray = Producer.generate(createTestData(i,   j, seed));
                String inputPred  = Producer.generate(createTestData(i+1, j, seed));

                correctness = Benchmark.warmUp(inputArray, inputPred, 
                                                (iterations * n), algorithms);
            } 
            System.out.println("Warm-up " + i+1 + "/" + modeArray.length/2 + " done! " + correctness);
        }
    }

// HELPER FUNCTIONS ///////////////////////////////////////////////////////////////////////////////////

    /** Adds all the test data to the the file and closes the FileWriter
     * 
     * @param sb a StringBuilder array
     * @param fw a FileWriter array
     * @throws IOException
     */
    public static void addMeasurements(StringBuilder[] sb, FileWriter[] fw) throws IOException {
        for(int j = 0; j < algorithms.length; j++) {
                fw[j].append(sb[j], 0, sb[j].length());
                fw[j].flush();
                fw[j].close();
        }
    }

    /** Creates a directory that will contain all the test data files
     * 
     * @return a string for the newly created directory
     */
    public static String createDir() {
        dir = new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-"
                + new SimpleDateFormat("HH.mm.ss").format(new Date());
        File s = new File(dir);
        s.mkdirs();
        return dir;
    }

    /** Creates Files for each algorithm and mode
     * 
     * @param s String of the directory
     * @param a index of the current algorithm
     * @param i index of the current mode
     * @return returns the writer
     * @throws IOException
     */
    public static FileWriter createFile(int a, int i) throws IOException {

        File file = new File(dir + "/" + algorithms[a] + modeArray[i] + "_" + ".table");
        FileWriter writer = new FileWriter(file);
        writer.write("N mean sdev\n");

        return writer;
    }

    /** Creates the String[] that will be passed to the producer
     * 
     * @param j     index of the current mode
     * @param k     index of the current N
     * @param seed  the current seed
     * @return      returns a String[] for the producer
     */
    public static String[] createTestData(int i, int j, int seed){
        return new String[] {modeArray[i], Integer.toString(N[j]), seed + ""};
    }

    /**
     * Prints information of the system creating the test
     */
    public static void systemInfo() {
        System.out.println();
        System.out.printf("# OS:   %s; %s; %s%n", 
                          System.getProperty("os.name"), 
                          System.getProperty("os.version"), 
                          System.getProperty("os.arch"));
        System.out.printf("# JVM:  %s; %s%n", 
                          System.getProperty("java.vendor"), 
                          System.getProperty("java.version"));
        // The processor identifier works only on MS Windows:
        System.out.printf("# CPU:  %s; %d \"cores\"%n", 
                          System.getenv("PROCESSOR_IDENTIFIER"),
                          Runtime.getRuntime().availableProcessors());
        java.util.Date now = new java.util.Date();
        System.out.printf("# Date: %s%n", 
          new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(now));
          System.out.println();
          System.out.println("BENCHMARKING Binary Search");
          System.out.println("-------------------------------------");
          System.out.println();
      }
    /**
     * Prints a statement that the test has finished and where to find
     * the test data
     */
    public static void end(){
        System.out.println("-------------------------------------");
        System.out.println("Experiment all Done!");
        System.out.println("Find the results here:");
        System.out.println(System.getProperty("user.dir") +"/"+ dir);
        System.out.println();
    }
}


