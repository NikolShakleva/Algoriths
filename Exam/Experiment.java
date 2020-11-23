import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Experiment {

    private static final String [] modeArray = new String [] {"non-existent","random"};
    private static final String [] modePred = new String [] {"non-existent pred","random pred"};

    // private static final String [] N = new String [] {"10","50","100","200","500","1000"};
    private static final String [] N =  new String [] {"1", "2"}; //"50", "100"};
    private static final int seed = 1234;
    //private static String [] algorithms = new String [] {"BinarySearch", "SortedArrayWithTabulation"};


    public static void main(String args []) {
        // WARMIN UP BEFORE THE EXPERIMENT
        System.out.println("Running the warm-up");
        String correctness = "";
            for(int j = 0; j < modeArray.length; j++ ) {
                // only want to test for 2 diffrerent input lenghts 
                for(int k = 0; k < 2; k++) {
                    String inputArray = Producer.generate(new String [] {modeArray[j], N[k], seed + ""});
                    String inputPred = Producer.generate(new String [] {modePred[j], N[k], seed + ""});
                    // hard-coding the iterations and the n for the bechcmark
                    correctness = Benchmark.warmUp(inputArray, inputPred, 100_000);
                }

                System.out.println("Warm-up done with correctness " + correctness);
        }
        // RUNNING THE EXPERIMENT 
        String s =  new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-" + 
                    new SimpleDateFormat("HH.mm.ss").format(new Date());;  
        File dir = new File(s);    
        dir.mkdirs();

        System.out.println("Running the experiment");
        for (int i = 0; i < modeArray.length; i++) {
                try { 
                File file = new File(s + "/" + modeArray[i] + "_" + ".table");

                FileWriter writer = new FileWriter(file);

                System.out.println("Running for Algorithms with" + "--" + modeArray[i]);
                String [] multipleSeed = Seed.createSeed(seed);
                for(int l = 0; l < multipleSeed.length; l++ ){
                for (int j = 0; j < N.length; j++) {
                        String  [] array = new String [] {modeArray[i], N[j], multipleSeed[l] };
                        String  [] pred = new String [] {modePred[i], N[j], multipleSeed[l] };
                        String inputArray = (Producer.generate(array));
                        String inputPred = (Producer.generate(pred));
                        // hard-coding the iterations and the n for the bechcmark 
                        correctness = Benchmark.run(inputArray, inputPred, 10_000, 10);
                        String lineBin = "BinarySearch " + N[j] + " " + Benchmark.getMeanBin() + " " + Benchmark.getSdevBin() + "\n";
                        String lineTab = "SortedArrayWithTabulation " + N[j] + " " + Benchmark.getMeanTab() + " " + Benchmark.getSdevTab() + "\n";
                        writer.append(lineBin, 0, lineBin.length()) ;
                        writer.append(lineTab, 0, lineTab.length()) ;
                }
                }
                writer.flush();
                writer.close();
            } catch (Exception e ) {}
            

            }
    }
}


