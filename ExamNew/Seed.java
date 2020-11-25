public class Seed {
    
    public static int [] createSeed (int seed) {
      var seedArray = new int [4];
        for (int i =0; i< seedArray.length; i++ ) {
            seedArray[i] = seed + 7896*i;
        }
        return seedArray;
    }
}
