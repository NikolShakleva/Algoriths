public class Seed {
    
    public static String [] createSeed (int seed) {
      var  seedArray = new String [4];
        for (int i =0; i< seedArray.length; i++ ) {
            seedArray[i] = seed + 7896*i+ "";
        }
        return seedArray;
    }
}
