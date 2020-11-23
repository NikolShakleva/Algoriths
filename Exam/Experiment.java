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
    private static final int runPerSeed = 2;
    private static final int iterations = 10;
    private static final int n = 2;
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
                    correctness = Benchmark.warmUp(inputArray, inputPred, (iterations * n));
                }

                System.out.println("Warm-up done with " + correctness);
        }
        // RUNNING THE EXPERIMENT 
        String s =  new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-" + 
                    new SimpleDateFormat("HH.mm.ss").format(new Date());;  
        File dir = new File(s);    
        dir.mkdirs();

        System.out.println("Running the experiment");
        for (int i = 0; i < modeArray.length; i++) {
                try { 
                File fileBin = new File(s + "/" + "Binary Search" + modeArray[i] + "_" + ".table");
                File fileTab = new File(s + "/" + "Tabulation" + modeArray[i] + "_" + ".table");

                FileWriter writerBin = new FileWriter(fileBin);
                FileWriter writerTab = new FileWriter(fileTab);
                writerBin.write("N mean sdev\n");
                writerTab.write("N mean sdev\n");

                System.out.println("Running for Algorithms with" + "--" + modeArray[i]);
                String [] multipleSeed = Seed.createSeed(seed);
                for(int l = 0; l < multipleSeed.length; l++ ){
                for (int j = 0; j < N.length; j++) {
                    double meanBin = 0.0;
                    double sDevBin = 0.0;
                    double meanTab = 0.0;
                    double sDevTab = 0.0;

                    for (int k = 0 ; k < runPerSeed ; k++ ){
                        String  [] array = new String [] {modeArray[i], N[j], multipleSeed[l] };
                        String  [] pred = new String [] {modePred[i], N[j], multipleSeed[l] };
                        String inputArray = (Producer.generate(array));
                        String inputPred = (Producer.generate(pred));
                        // hard-coding the iterations and the n for the bechcmark 
                        correctness = Benchmark.run(inputArray, inputPred, iterations, n);
                        meanBin += Benchmark.getMeanBin();
                        sDevBin += Benchmark.getSdevBin();
                        meanTab += Benchmark.getMeanTab();
                        sDevTab += Benchmark.getSdevTab();
                    }
                        String lineBin = N[j] + " " + (meanBin / runPerSeed) + " " + (sDevBin/runPerSeed) + "\n";
                        String lineTab = N[j] + " " + (meanTab/runPerSeed) + " " + (sDevTab/runPerSeed) + "\n";
                        writerBin.append(lineBin, 0, lineBin.length()) ;
                        writerTab.append(lineTab, 0, lineTab.length()) ;

                }
                }
                writerBin.flush();
                writerBin.close();
                writerTab.flush();
                writerTab.close();
            } catch (Exception e ) {}
            

            }
    }
}


