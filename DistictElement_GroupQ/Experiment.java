import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Experiment {

    private static final int N =  100_000;
    private static final int [] m = new int [] {64, 128, 256, 512, 1024};

    public static void main(String args []) {

        String s =  new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-" + 
                    new SimpleDateFormat("HH.mm.ss").format(new Date());;  
        File dir = new File(s);    
        dir.mkdirs();
        systemInfo();
        for(int i =0; i< m.length; i++) {                        
            try { 
                File file = new File(s + "/" + "M" + m[i] + "_" + "HyperLogLog" + ".csv");
                FileWriter writer = new FileWriter(file);
                System.out.println("Running for " + "HyperLogLog" + " with M: " + m[i]);
                writer.write("N;M;Output\n");

                    for(int j = 0; j< 10_000; j++) {
                        String input = Producer.generate("random", N, m[i]);
                        int dummy = 0;
                        dummy = HyperLogLog.distinctElements(input);
                        String line  = N + ";"  + m[i] + ";" + dummy + "\n";
                        writer.append(line, 0, line.length());
                    }
                writer.flush();
                writer.close();
            }
            catch (Exception e ) {}
        } 
        System.out.println("-------------------------------------");
        System.out.println("Experiment all Done!");
        System.out.println("Find the results here:");
        System.out.println(System.getProperty("user.dir") +"/"+ s);
        System.out.println();

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
      System.out.println("Running the experiment for N: " + N);
      System.out.println("-------------------------------------");
  }
}

