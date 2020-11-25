public class Benchmark {

    private static double[] sdev;
    private static double[] mean;
    
    public static String warmUp (String inputArray, String inputPred, int n, String[] algo) {
        int algorithms = algo.length;
        String dummy[] = new String[algorithms];
        
        for(int i = 0; i < n; i++ ) {
            for(int j = 0; j < algorithms ; j++ ) {
                Search search;

                if   (algo[j].equals("BinarySearch")) search = new BinarySearch(inputArray);
                else                                  search = new Tabulation(inputArray);  

                dummy[j] = search.pred(inputPred);
            }

            for(int l = 0 ; l < algorithms - 1 ; l++) {
                if(!dummy[l].equals(dummy[l+1])) return "Failure";
            }
        }
        return "Success";
    } 

    public static String run(String inputArray, String inputPred, int iterations, int n, String[] algo) {

        int algorithms = algo.length;
        sdev           = new double[algorithms];
        mean           = new double[algorithms];
        double[] st    = new double[algorithms];
        double[] sst   = new double[algorithms];
        //double stBin, stTab, sstBin, sstTab  = 0.0;
        String[] dummy = new String[algorithms];

        for(int i=0; i< n; i++) {
            Timer[] t = new Timer[algorithms];
            for(int j = 0; j < algorithms ; j++) t[j] = new Timer();
            for(int k = 0; k < iterations ; k++) {
                for(int j = 0; j < algorithms ; j++ ) {
                    Search search;

                    if   (algo[j].equals("BinarySearch")) search = new BinarySearch(inputArray);
                    else                                  search = new Tabulation(inputArray);  

                    t[j].play();
                    dummy[j] = search.pred(inputPred);
                    t[j].pause();
                }
                for(int l = 0 ; l < algorithms - 1 ; l++) {
                    if(!dummy[l].equals(dummy[l+1])) {
                        System.out.println("Failed, Algorithms are not correct");
                        return "Failure";
                    }
                }
            }
            for(int m = 0; m < algorithms ; m++){    
                double time = t[m].check() / iterations;
                st[m]  += time;
                sst[m] += time * time;
            }
        }
        for(int i = 0; i < algorithms ; i++) {
            mean[i] = (st[i]/n)/ 1000;
            sdev[i] = (Math.sqrt((sst[i] - mean[i] * mean[i] * n)/(n-1)))/1000;

            System.out.printf( "%12s  %6.1f mu  +/-  %6.3f %n", algo[i], mean[i], sdev[i]);
        }
        return "Success";
    }

    public static double[] getMean(){
        return mean;
    }
    public static double[] getSdev(){
        return sdev;
    }
    }


