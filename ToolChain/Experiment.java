import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Experiment {

    private static final String [] mode = new String [] {"sorted", "backwards", "same", "random", "semi-sorted"};
    // private static final String [] N = new String [] {"10","50","100","200","500","1000"};
    private static final String [] N =  new String [] {"5", "10", "50", "100"};
    private static final String seed = "1234";
    private static String [] algorithms = new String [] {"Sort", "Priority"};


    public static void main(String args []) {
        for (int i = 0; i < mode.length; i++) {
            for (int k = 0; k < algorithms.length; k++) {
                try {
                //Path path = Paths.get("Algorithms/ToolChain/" + LocalDateTime.now()); 
                System.out.println(LocalDateTime.now());

                //String s = LocalDateTime.now() + "";     
                //var dir = Files.createDirectories(path);
            
                // File dir = new File(path); 
                // dir.mkdirs();  
                File file = new File(mode[i] + "_" + algorithms[k] + ".table");
                file.mkdir();
                FileWriter writer = new FileWriter(file);

                System.out.println(mode[i]);
                for (int j = 0; j<N.length; j++) {
                        String  [] array = new String [] {mode[i], N[j], seed};
                        String input = (Producer.generate(array));
                        // hard-coding the iterations and the n for the warm-up 
                        // args[0] is the algorithm name which we specify in the terminal
                        Benchmark.warmUp(input, 100_000, algorithms[k]);
                        // hard-coding the iterations and the n for the bechcmark 
                        Benchmark.run(input, 10_000, 10, algorithms[k]);
                        // double sdev = Benchmark.getSdev();
                        // double mean = Benchmark.getMean();
                        String line = N[j] + " " + Benchmark.getMean() + " " + Benchmark.getSdev() + "\n";
                        writer.append(line, 0, line.length()) ;

                }
                writer.flush();
                writer.close();
            } catch (Exception e ) {}
            }

            }
    }
}


