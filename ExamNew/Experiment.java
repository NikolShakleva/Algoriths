import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Experiment {

    private static String[] algorithms      = { "BinarySearch", "Tabulation" };
    private static final String[] modeArray = { "non-existent", "non-existent pred", 
                                                "random",       "random pred" };
    private static final String[] N         = { "1", "2", "10", "50", "100", "200", "500", "1000" };


    private static final int n = 2;
    private static final int seed = 1234;
    private static final int runPerSeed = 2;
    private static final int iterations = 10;
    private static int[] multipleSeed;
   

    public static void main(String args[]) {
        systemInfo();
        // WARMING UP BEFORE THE EXPERIMENT
        System.out.println("Running the warm-up...");
        String correctness = "";
        for (int i = 0; i < modeArray.length; i += 2) {
            // Running warmup for 2 first size N 
            for (int j = 0; j < 2; j++) {
                String inputArray = Producer.generate(createTestData(i,   j, seed));
                String inputPred  = Producer.generate(createTestData(i+1, j, seed));

                correctness = Benchmark.warmUp(inputArray, inputPred, (iterations * n), algorithms);
            } 
            System.out.println("Warm-up " + i+1 + "/" + modeArray.length/2 + " done! " + correctness);
        }

        String s = createDir();
        FileWriter[] file = new FileWriter[algorithms.length];

        // RUNNING THE EXPERIMENT
        // i = mode, a = algorithm, l = seed, j = N
        System.out.println();
        System.out.println("Running the experiment...");
        
        for (int i = 0; i < modeArray.length; i=i+2) {
            System.out.println();
            for (int a = 0; a < algorithms.length; a++) {
                try   {file[a] = createFile(s, a, i);} 
                catch (IOException e) { e.printStackTrace();}
            }

            System.out.println("All Algorithms with mode: " + modeArray[i]);
            System.out.println("-------------------------------------");
            multipleSeed = Seed.createSeed(seed);
            for (int l = 0; l < multipleSeed.length; l++) {
                for (int j = 0; j < N.length; j++) {
                    double[] mean = new double[algorithms.length];
                    double[] sDev = new double[algorithms.length];

                    for (int k = 0; k < runPerSeed; k++) {
                        
                        String inputArray = Producer.generate(createTestData(i,   j, multipleSeed[l]));
                        String inputPred  = Producer.generate(createTestData(i+1, j, multipleSeed[l]));
                        // hard-coding the iterations and the n for the bechcmark
                        correctness = Benchmark.run(inputArray, inputPred, iterations, n, algorithms);
                        var tempMean = Benchmark.getMean();
                        var tempSdev = Benchmark.getSdev();

                        for (int m = 0; m < algorithms.length; m++) {
                            mean[m] += tempMean[m];
                            sDev[m] += tempSdev[m];
                        }
                    
                        for(int n = 0; n < algorithms.length; n++){
                            try {
                                String line = N[j] + " " + (mean[n] / runPerSeed) + " " + (sDev[n] / runPerSeed) + "\n";
                                file[n].append(line,0,line.length());
                            } catch (IOException e) { e.printStackTrace();}
                        }
                    }
                }
            }
        }
            
        for(int j = 0; j < algorithms.length; j++) {
            try {
                file[j].flush();
                file[j].close();
            } catch (IOException e) { e.printStackTrace();}
        }

        end(s);
    }





    public static void addMeasurements(String s, FileWriter f) throws IOException {
        f.append(s, 0, s.length());
    }

    public static String createDir() {
        String s = new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-"
                + new SimpleDateFormat("HH.mm.ss").format(new Date());
        File dir = new File(s);
        dir.mkdirs();
        return s;
    }

    public static FileWriter createFile(String s, int a, int i) throws IOException {

        File file = new File(s + "/" + algorithms[a] + modeArray[i] + "_" + ".table");
        FileWriter writer = new FileWriter(file);
        writer.write("N mean sdev\n");

        return writer;
    }

    public static String[] createTestData(int j, int k, int seed){
        return new String[] {modeArray[j], N[k], seed + ""};
    }


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

      public static void end(String s){
        System.out.println("-------------------------------------");
        System.out.println("Experiment all Done!");
        System.out.println("Find the results here:");
        System.out.println(System.getProperty("user.dir") +"/"+ s);
        System.out.println();
    }
}


