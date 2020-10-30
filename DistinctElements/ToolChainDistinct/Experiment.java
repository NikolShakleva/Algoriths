import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Experiment {

    // private static final String [] mode = new String [] { "half-distinct", "same", "random"};
    // private static final String [] N =  new String [] {"50_000", "1_000_000", "5_000_000", "10_000_000"};
    private static final int N =  1_000;
    private static final int [] m = new int [] {64, 128, 516};



    public static void main(String args []) {

        // RUNNING THE EXPERIMENT 
        String s =  new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-" + 
                    new SimpleDateFormat("HH.mm.ss").format(new Date());;  
        File dir = new File(s);    
        dir.mkdirs();

        System.out.println("Running the experiment");
            try { 
                File file = new File(s + "/" + "random" + "_" + "HyperLogLog" + ".csv");

                FileWriter writer = new FileWriter(file);

                System.out.println("Running for " + "HyperLogLog" + "--" + "random");

                writer.write("N;m;output\n");
                for(int i =0; i< m.length; i++) {
                    for(int j = 0; j< 10; j++) {
                        String input = Producer.generate("random", N, m[i]);
                        int dummy = 0;
                        dummy = HyperLogLog.distinctElements(input);
                        String line  = N + ";"  + m[i] + ";" + dummy + "\n";
                        writer.append(line, 0, line.length());
                        }
                    }
                writer.flush();
                writer.close();
            } catch (Exception e ) {}

        
    }
}


