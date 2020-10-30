import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Experiment {

    private static final String [] mode = new String [] {"sorted", "backwards", "same", "random", "semi-sorted"};
    // private static final String [] N = new String [] {"10","50","100","200","500","1000"};
    private static final String [] N =  new String [] {"5", "10", "50", "100"};
    private static final int seed = 1234;
    private static String [] algorithms = new String [] {"Sort", "Priority"};


    public static void main(String args []) {
        // WARMIN UP BEFORE THE EXPERIMENT
        System.out.println("Running the warm-up");
        for (int i =0; i < algorithms.length; i++) {
            for(int j = 0; j< mode.length; j++ ) {
                // only want to test for 2 diffrerent inout lenghts 
                for(int k = 0; k < 2; k++) {
                    String input = Producer.generate(new String [] {mode[j], N[k], seed+""});
                    // hard-coding the iterations and the n for the bechcmark
                    Benchmark.warmUp(input, 100_000, algorithms[i]);
                }

            }
            System.out.println("Warm-up done for " + algorithms[i] + i+1 + "/" + algorithms.length);
        }
        // RUNNING THE EXPERIMENT 
        String s =  new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-" + 
                    new SimpleDateFormat("HH.mm.ss").format(new Date());;  
        File dir = new File(s);    
        dir.mkdirs();

        System.out.println("Running the experiment");
        for (int i = 0; i < mode.length; i++) {
            for (int k = 0; k < algorithms.length; k++) {
                try { 
                File file = new File(s + "/" + mode[i] + "_" + algorithms[k] + ".table");

                FileWriter writer = new FileWriter(file);

                System.out.println("Running for " + algorithms[k] + "--" + mode[i]);
                String [] multipleSeed = Seed.createSeed(seed);
                for(int l = 0; l < multipleSeed.length; l++ ){
                for (int j = 0; j<N.length; j++) {
                        String  [] array = new String [] {mode[i], N[j], multipleSeed[l] };
                        String input = (Producer.generate(array));
                        // hard-coding the iterations and the n for the bechcmark 
                        Benchmark.run(input, 10_000, 10, algorithms[k]);
                        String line = N[j] + " " + Benchmark.getMean() + " " + Benchmark.getSdev() + "\n";
                        writer.append(line, 0, line.length()) ;
                }
                }
                writer.flush();
                writer.close();
            } catch (Exception e ) {}
            }

            }
    }
}


