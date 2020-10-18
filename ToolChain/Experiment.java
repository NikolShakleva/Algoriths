import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Experiment {

    private static final String [] mode = new String [] {"sorted", "backwards", "same", "random", "semi-sorted"};
    // private static final String [] N = new String [] {"10","50","100","200","500","1000"};
    private static final String [] N =  new String [] {"5", "10", "50"};
    private static final String seed = "1234";
    private static String [] algorithms = new String [] {"Sort", "Priority"};


    public static void main(String args []) {
        for (int i = 0; i < mode.length; i++) {
            // try {
            // CSVWriter writer = new CSVWriter(new FileWriter("C:/Users/Admin/Desktop/ToolChain/" + mode[i] +".csv"));
            for (int k = 0; k < algorithms.length; k++) {
                System.out.println(mode[i]);
                for (int j = 0; j<N.length; j++) {
                        String  [] array = new String [] {mode[i], N[j], seed};
                        String input = (Producer.generate(array));
                        // hard-coding the iterations and the n for the warm-up 
                        // args[0] is the algorithm name which we specify in the terminal
                        Benchmark.warmUp(input, 100_000, algorithms[k]);
                        // hard-coding the iterations and the n for the bechcmark 
                        Benchmark.run(input, 10_000, 10, algorithms[k]);
                        double sdev = Benchmark.getSdev();
                        double mean = Benchmark.getMean();
                }
            }
        //  } catch (Exception e ) {}
    }
}

}
