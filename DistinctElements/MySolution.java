public class MySolution {

        public static int findLeadingZeros(int x) {
            int count = 0;
            for(int i  = 0; i< 32; i++) {
                // shifts the position of all the 1s in x one at a time for each iratation with the number of i(x>>1, x>>2, x>>3...x>>32)
                // or the x with the shifted x  
                 // we do that so we fill all the 0s after the fist 1 (right to left) with 1s  
                x = x | (x >> i);
            }
            count = Integer.bitCount(x);
           return 32 - count;
        }
    
        // find the leading 0s in 43
        // x = 43 =       0000 0000 0000 0000 0000 0000 0010 1011
        // 43 <<  1 =     0000 0000 0000 0000 0000 0000 0001 0101
        // 43 | 43 << 1 = 0000 0000 0000 0000 0000 0000 0011 1111
        // 32-6 = 26

        public static int findPositionOfFirstOne(int x) {
            int count = 0;
            for(int i  = 0; i< 32; i++) {
                // shifts the position of all the 1s in x one at a time for each iratation with the number of i(x>>1, x>>2, x>>3...x>>32)
                // or the x with the shifted x  
                 // we do that so we fill all the 0s after the fist 1 (right to left) with 1s  
                x = x | (x >> i);
            }
            count = Integer.bitCount(x);
            //  count if we start from index 1
        //    return count;
        //  count -1 if we start from index 0
        //   return count-1;
        }
    
    
        
    public static void main(String[] args) {
    
        int a = 12;
        System.out.println(a<<3);
        int b = 128;
        System.out.println(b>>>5);
        System.out.println(findLeadingZeros(43));
        System.out.println(findPositionOfFirstOne(43));

    
    }
        
    
}
