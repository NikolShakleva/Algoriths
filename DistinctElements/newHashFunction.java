import java.util.Scanner;

public class newHashFunction {
    private static final int[] A = { 0x21ae4036,
                0x32435171,
                0xac3338cf,
                0xea97b40c,
                0x0e504b22,
                0x9ff9a4ef,
                0x111d014d,
                0x934f3787,
                0x6cd079bf,
                0x69db5c31,
                0xdf3c28ed,
                0x40daf2ad,
                0x82a5891c,
                0x4659c7b0,
                0x73dc0ca8,
                0xdad3aca2,
                0x00c74c7e,
                0x9a2521e2,
                0xf38eb6aa,
                0x64711ab6,
                0x5823150a,
                0xd13a3a9a,
                0x30a5aa04,
                0x0fb9a1da,
                0xef785119,
                0xc9f0b067,
                0x1e7dde42,
                0xdda4a7b2,
                0x1a1c2640,
                0x297c0633,
                0x744edb48,
                0x19adce93};

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
                   return count;
                }

    public static int hash(int x){
        int vector = 0;
        for(int i = 31; i>=0; i--) {
            int bits = A[31-i] & x;
            bits = Integer.bitCount(bits) & 1;
            // bits = Integer.bitCount(bits) % 2;
            vector = vector | bits << i;
        }
        return vector;
    }


    public static void main(String[] args) {


    //  var sc = new Scanner("-2147483648\n-2147483648");
    var sc = new Scanner(System.in);
    while(sc.hasNextInt()) {
        int vector = 0;
        int x = sc.nextInt(); 
        for(int i = 31; i>=0; i--) {
            int bits = A[31-i] & x;
            bits = Integer.bitCount(bits) & 1;
            // bits = Integer.bitCount(bits) % 2;
            vector = vector | bits << i;
        }
        System.out.println(vector);
    }
  } 
}
