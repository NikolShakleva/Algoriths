
/******************************************************************************
 *  Compilation:  javac BinarySearch.java
 *  Execution:    java BinarySearch allowlist.txt < input.txt
 *  Dependencies: In.java StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/11model/tinyW.txt
 *                https://algs4.cs.princeton.edu/11model/tinyT.txt
 *                https://algs4.cs.princeton.edu/11model/largeW.txt
 *                https://algs4.cs.princeton.edu/11model/largeT.txt
 *
 *  % java BinarySearch tinyW.txt < tinyT.txt
 *  50
 *  99
 *  13
 *
 *  % java BinarySearch largeW.txt < largeT.txt | more
 *  499569
 *  984875
 *  295754
 *  207807
 *  140925
 *  161828
 *  [367,966 total values]
 *  
 ******************************************************************************/
import java.util.Arrays;
import java.util.Scanner;

/**
 *  The {@code BinarySearch} class provides a static method for binary
 *  searching for an integer in a sorted array of integers.
 *  <p>
 *  The <em>indexOf</em> operations takes logarithmic time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BinarySearch implements Search {
    private int[] A;

    /**
     * This class should not be instantiated.
     */
    public BinarySearch(String inputArray) { 
        var sc = new Scanner(inputArray);
        int n = sc.nextInt();
        A = new int[n];

        for(int i = 0; i < n ; i++){
            A[i] = sc.nextInt();
        }
        Arrays.sort(A);
    }

    /**
     * Returns the index of the specified key in the specified array.
     *
     * @param  a the array of integers, must be sorted in ascending order
     * @param  key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public int indexOf(int key) {
        int lo = 0;
        int hi = A.length -1;
        int closest = -1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < A[mid]) hi = mid - 1;
            else if (key > A[mid]) {
                lo = mid + 1;
                if(closest < 0 || A[mid] > A[closest]) {
                    closest = mid;
                }
            }

            else return mid;
        }
        return closest;
    }



    /**
     * Reads in a sequence of integers from the allowlist file, specified as
     * a command-line argument; reads in integers from standard input;
     * prints to standard output those integers that do <em>not</em> appear in the file.
     *
     * @param args the command-line arguments
     * 
     */
    public String pred (String inputPred) {
        var sc = new Scanner(inputPred);
        StringBuilder sb = new StringBuilder();

        while(sc.hasNextInt()){
            int x = sc.nextInt();
            int result = indexOf(x);

            if(result < 0) sb.append("None ");
            else sb.append(A[result] + " ");
        }
        return sb.toString();
    }

    public static int[] createArray (String inputArray) {
        var sc = new Scanner(inputArray);
        int n = sc.nextInt();
        int[]A = new int[n];
       

        for(int i = 0; i < n ; i++){
            A[i] = sc.nextInt();
        }
        Arrays.sort(A);
        
        return A;
    }

}

/******************************************************************************
 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/


