import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Experiment {

    private static String[] algorithms      = { "Tabulation", "BinarySearch" };
    private static final String[] modeArray = { "non-existent", "non-existent pred",
                                                "random",       "random pred" };
    private static final String[] N         = { "200", "500", "1000" };


    private static final int n = 2;
    private static final int seed = 1234;
    private static final int runPerSeed = 2;
    private static final int iterations = 10;
    private static String dir;
   

    public static void main(String args[]) {
        systemInfo();
        warmUp();
        createDir();

        // RUNNING THE EXPERIMENT
        System.out.println();
        System.out.println("Running the experiment...");
        
        for (int i = 0; i < modeArray.length; i=i+2) {
            FileWriter[] file = new FileWriter[algorithms.length];
            StringBuilder[] sb = new StringBuilder[algorithms.length];
            try   {
                System.out.println();
                for (int a = 0; a < algorithms.length; a++) {
                    file[a] = createFile(a, i);
                    sb[a] = new StringBuilder();
                }

                System.out.println("All Algorithms with mode: " + modeArray[i]);
                
                int[] seedArray = Seed.createSeed(seed);
                for (int l = 0; l < seedArray.length; l++) {
                    for (int j = 0; j < N.length; j++) {
                        System.out.println("-------------------------------------");
                        System.out.println("N: " + N[j] + " and Seed: " + seedArray[l]);
                        System.out.println("-------------------------------------");
                        double[] mean = new double[algorithms.length];
                        double[] sDev = new double[algorithms.length];

                        for (int k = 0; k < runPerSeed; k++) {
                            
                            String inputArray = Producer.generate(createTestData(i,   j, seedArray[l]));
                            String inputPred  = Producer.generate(createTestData(i+1, j, seedArray[l]));

                            Benchmark.run(inputArray, inputPred, iterations, n, algorithms);

                            double[] tempMean = Benchmark.getMean();
                            double[] tempSdev = Benchmark.getSdev();

                            for (int m = 0; m < algorithms.length; m++) {
                                mean[m] += tempMean[m];
                                sDev[m] += tempSdev[m];
                            }
                        }
                        for(int n = 0; n < algorithms.length; n++){
                            sb[n].append(N[j] + " " + (mean[n] / runPerSeed) + " " + (sDev[n] / runPerSeed) + "\n");
                        }
                    }
                }
                addMeasurements(sb, file);
            } catch (IOException e) { e.printStackTrace();}
        }
        end();
    }


// HELPER FUNCTIONS ///////////////////////////////////////////////////////////////////////////////////

public static void warmUp() {

    System.out.println("Running the warm-up...");
    String correctness = "";
    for (int i = 0; i < modeArray.length; i += 2) {
        
        for (int j = 0; j < 2; j++) {                                           // Running warmup for 2 first size N 
            String inputArray = Producer.generate(createTestData(i,   j, seed));
            String inputPred  = Producer.generate(createTestData(i+1, j, seed));

            correctness = Benchmark.warmUp(inputArray, inputPred, 
                                            (iterations * n), algorithms);
        } 
        System.out.println("Warm-up " + i+1 + "/" + modeArray.length/2 + " done! " + correctness);
    }
}

    /**
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

    /**
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

    /**
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

    /**
     * 
     * @param j     index of the current mode
     * @param k     index of the current N
     * @param seed  the current seed
     * @return      returns a String[] for the producer
     */
    public static String[] createTestData(int j, int k, int seed){
        return new String[] {modeArray[j], N[k], seed + ""};
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


