import java.util.Scanner;

public class HyperLogLog {
    private static final int[] A = { 0x21ae4036, 0x32435171, 0xac3338cf, 0xea97b40c, 0x0e504b22, 0x9ff9a4ef, 0x111d014d,
            0x934f3787, 0x6cd079bf, 0x69db5c31, 0xdf3c28ed, 0x40daf2ad, 0x82a5891c, 0x4659c7b0, 0x73dc0ca8, 0xdad3aca2,
            0x00c74c7e, 0x9a2521e2, 0xf38eb6aa, 0x64711ab6, 0x5823150a, 0xd13a3a9a, 0x30a5aa04, 0x0fb9a1da, 0xef785119,
            0xc9f0b067, 0x1e7dde42, 0xdda4a7b2, 0x1a1c2640, 0x297c0633, 0x744edb48, 0x19adce93 };

    public static int findPositionOfFirstOne(int x) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            x = x | (x >> i);
        }
        count = Integer.bitCount(x);
        count = 32 - count + 1; 
        return count;
    }

    public static int hash(int x) {
        int vector = 0;
        for (int i = 31; i >= 0; i--) {
            int bits = A[31 - i] & x;
            bits = Integer.bitCount(bits) & 1;
            // bits = Integer.bitCount(bits) % 2;
            vector = vector | bits << i;
        }
        return vector;
    }

    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        var N = sc.nextInt();
        int m = 1024;
        int[] M = new int[m];

        while(sc.hasNextInt()){
            int i = sc.nextInt();
            int f = ((i * 0xbc164501) & 0x7fffffff) >> 21;
            // M[j] := max(M[j],rho(h(y[i])))
             M[f] = Math.max(M[f],findPositionOfFirstOne(hash(i)));
        }

        // 1/(2^(-M[0])+...+2^(-M[m-1]))
        double tempZ = 0;
        for (int k = 0 ; k < m ; k++){
            double temp = Math.pow(2,-M[k]);
            tempZ += temp;
        }
        double mm = m;

        double Z = 1.0 / tempZ;
        // |{i | M[i]=0}|
        double V = 0.0;
        for (int v = 0; v < m ; v++){
            if (M[v] == 0){
                V += 1.0;
            }
        }

        //m*m*Z*0.7213/(1 + 1.079/m)
        double E = (mm * mm * Z * 0.7213)/(1 + 1.079 / mm);

        if (E < 2.5 * mm && V > 0.0) {
            E = mm * Math.log(mm / V); 
        } 
        if ( E < N ) System.out.println("below");
        else System.out.println("above"); 
    }
}
