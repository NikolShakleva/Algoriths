    

        // import java.io.File;
        // import java.io.FileWriter;
        // import java.text.SimpleDateFormat;
        // import java.util.Date;
        
        
        // public class Benchmark {
        
        //     private static final String [] mode = new String [] { "half-distinct", "same", "random"};
        //     // private static final String [] N =  new String [] {"50_000", "1_000_000", "5_000_000", "10_000_000"};
        //     private static final String [] N =  new String [] { "1_000", "400"};
        //     private static final String [] m = new String [] {"64", "128", "516"};
        //     private static final int seed = 1234;
        
        
        //     public static void main(String args []) {
        //         // WARMIN UP BEFORE THE EXPERIMENT
        //         System.out.println("Running the warm-up");
        //             for(int j = 0; j< mode.length; j++ ) {
        //                 // only want to test for 2 diffrerent inout lenghts 
        //                 for(int k = 0; k < 2; k++) {
        //                     // hard-coding the buckets number for the warm-up
        //                     String input = Producer.generate(new String [] {mode[j], N[k], "128", seed+""});  
        //                     // hard-coding the iterations and the n for the bechcmark
        //                     Benchmark.warmUp(input, 100_000);
        //                     }
        //             System.out.println("Warm-up done for HyperLogLog");
        //         }
        
        //         // RUNNING THE EXPERIMENT 
        //         String s =  new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "-" + 
        //                     new SimpleDateFormat("HH.mm.ss").format(new Date());;  
        //         File dir = new File(s);    
        //         dir.mkdirs();
        
        //         System.out.println("Running the experiment");
        //             try { 
        //                 File file = new File(s + "/" + "random" + "_" + "HyperLogLog" + ".table");
        
        //                 FileWriter writer = new FileWriter(file);
        
        //                 System.out.println("Running for " + "HyperLogLog" + "--" + "random");
        //                 String [] multipleSeed = Seed.createSeed(seed);
        //                 for(int l = 0; l < multipleSeed.length; l++ ){
        //                     for(int p=0; p< m.length; p++) {
        //                         for (int j = 0; j<N.length; j++) {
        //                                 String  [] array = new String [] {"random", N[j], m[p], multipleSeed[l] };
        //                                 String input = (Producer.generate(array));
        //                                 // hard-coding the iterations and the n for the bechcmark 
        //                                 Benchmark.run(input, 10_000, 10, Integer.parseInt(m[p]), Integer.parseInt(N[j]));
        //                                 String line = N[j] + " " + Benchmark.getMean() + " " + Benchmark.getSdev() + "\n";
        //                                 writer.append(line, 0, line.length()) ;
        //                         }
        //                     }
        //                  }
        //                 writer.flush();
        //                 writer.close();
        //             } catch (Exception e ) {}
        
                
        //     }
        // }


