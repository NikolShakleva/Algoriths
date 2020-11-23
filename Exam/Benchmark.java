public class Benchmark {

    private static double sdevBin;
    private static double meanBin;
    private static double sdevTab;
    private static double meanTab;
    
    public static String warmUp (String inputArray, String inputPred, int n) {
        String dummyBin = "";
        String dummyTab = "";

        for(int i = 0; i< n; i++ ) {
                 
                int[] A = BinarySearch.createArray(inputArray);
                dummyBin = BinarySearch.search(A, inputPred);
                var tab = new SortedArrayWithTabulation(inputArray);
                dummyTab = tab.pred(inputPred);
                if(!dummyBin.equals(dummyTab)) {
                    System.out.println(dummyBin);
                    System.out.println(dummyTab);
                    return "Failure";
                }
        }
        return "Success";
    } 

    public static String run(String inputArray, String inputPred, int count, int n) {

            double stBin = 0.0;
            double stTab = 0.0;
            double sstBin = 0.0;
            double sstTab = 0.0;
            String dummyBin = "";
            String dummyTab = "";
            for(int i=0; i< n; i++) {
                Timer tBin = new Timer();
                Timer tTab = new Timer();
                for(int j=0; j< count; j++) {
         
                        int[] A = BinarySearch.createArray(inputArray);
                        tBin.play();
                        dummyBin = BinarySearch.search(A, inputPred);
                        tBin.pause();
                 
                        var tab = new SortedArrayWithTabulation(inputArray);
                        tTab.play();
                        dummyTab = tab.pred(inputPred);
                        tTab.pause();

                        if(!dummyBin.equals(dummyTab)) {
                            System.out.println(dummyBin);
                            System.out.println(dummyTab);
                            return "Failure";
                        }
                    }
                
                double timeBin = tBin.check() * 1e9 / count;
                double timeTab = tTab.check() * 1e9 / count;
                stBin += timeBin;
                stTab += timeTab;
                sstBin += timeBin * timeBin;
                sstTab += timeTab * timeTab;
        }
        meanBin = (stBin/n)/ 1000;
        sdevBin = (Math.sqrt((sstBin - meanBin * meanBin * n)/(n-1)))/1000;
        meanTab = (stTab/n)/ 1000;
        sdevTab = (Math.sqrt((sstTab - meanTab * meanTab * n)/(n-1)))/1000;



        System.out.printf("BinarySearch %6.1f ns +/- %6.3f %n", meanBin, sdevBin);
        System.out.printf("SortedArrayWithTabulation %6.1f ns +/- %6.3f %n", meanTab, sdevTab);
        return "Success";
        }

    public static double getSdevBin() {
        return sdevBin;
    }

    public static double getMeanBin() {
        return meanBin;
    }
    public static double getSdevTab() {
        return sdevTab;
    }

    public static double getMeanTab() {
        return meanTab;
    }
    }


