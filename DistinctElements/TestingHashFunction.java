
import java.io.IOException;
import java.util.Random;


public class TestingHashFunction {

    public static int numberOfLeadingZeros(int i) {

        if (i == 0)
            return 32;
        int n = 1;
        if (i >>> 16 == 0) {
            n += 16;
            i <<= 16;
        }
        if (i >>> 24 == 0) {
            n += 8;
            i <<= 8;
        }
        if (i >>> 28 == 0) {
            n += 4;
            i <<= 4;
        }
        if (i >>> 30 == 0) {
            n += 2;
            i <<= 2;
        }
        n -= i >>> 31;
        return n + 1;
    }

    public static void main(String[] args) {
        int[] A = { 0x21ae4036,
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
            
        int N = 1_000_000;
        var r = new Random();
        int[] FirstNumber = new int[33];
        for(int l = 0; l < N ; l++) {
            var sb = new StringBuilder();
            int[] B = new int[32];
            int[] C = new int[32];
            
            int x = r.nextInt();
            for(int i = 0; i < 32 ; i++){
                B[i] = A[i] & x;                    // Adding 2 ingeters represented as bits together
                int temp = Integer.bitCount(B[i]);  // Storing amount of set bits in the binary representation
                C[i] = temp % 2;                    // Counting set bits % 2 to be sure that we only have 1 and 0
            }

            for(int j = 0; j < 32 ; j++){
                sb.append(C[j]);
            }
            Long bin64 = Long.parseLong(sb.toString(), 2);
            int result = bin64.intValue();
            int positionFirstOne = numberOfLeadingZeros(result);  

            FirstNumber[positionFirstOne] += 1;


        }  
        for (int i = 0 ; i< FirstNumber.length;i++) System.out.println(FirstNumber[i]);

    }
        
    
    
}
