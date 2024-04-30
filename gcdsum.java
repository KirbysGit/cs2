// Colin Kirby
// 4/16/2024
// SOLVED BOTH TASKS
// gcdsum.java
// This code is used to obtain the highest GCD sum available for a list of 
// n integers split into k segments (must be in series) through use of dynamic programming
// to track the max summed GCD's through iterating through the possible segments and
// maintaing the values that have been used to compare and maintain the highest possible
// GCD sum from the k segments and then display that total sum as well as the starting
// indices of those said max GCD segments by tracking the indices during the dynamic
// programming iteration process.


import java.util.*; // Imports.
import java.io.*;

public class gcdsum {
    public static void main (String [] args) {

        Scanner inp = new Scanner(System.in); // Inp Declarator.

        int numInts = inp.nextInt(); // Take # Ints.

        int numSegments = inp.nextInt(); // Take # Segments.

        int [] inpList = new int [numInts + 1]; // Dec Inp List (1-based Idx).

        inpList[0] = 0; // Just in case, Zeroed.

        for (int i = 1; i <= numInts; i++) { // Take Ints.

            inpList[i] = inp.nextInt(); // Insert into List.

        }

        dp(numInts, numSegments, inpList); // Call dp func.

    }

    // Recursive GCD func.
    public static int gcd (int factorA, int factorB) { 

        if (factorB == 0) return factorA;

        else return gcd (factorB, factorA % factorB);

    }
    
    public static void dp (int numInts, int numSegments, int [] inpList) {

        long [][] table = new long [numInts + 1][numSegments + 1]; // Declare dp table.

        long [][] tracker = new long [numInts + 1][numSegments + 1]; // Table to track starting idx's.

        // Initialize Table.
        for (int i = 0; i <= numInts; i++) { 
            for (int j = 0; j <= numSegments; j++) {
                table[i][j] = -1;
                tracker[i][j] = -1;
            }
        }

        // Set First Idx to 0.
        table[0][0] = 0;

        for (int segment = 1; segment <= numSegments; segment++) { // For each segment.

            for (int idx = 1; idx <= numInts; idx++) { // Per Idx.

                int curBest = 0; // Set Cur Best Value for Idx to 0.

                for (int start = idx; start > 0; start--) { // Start at Idx & Move Backwards. (Reverse Loop)

                    curBest = gcd(curBest, inpList[start]); // Take GCD of Cur Idx in List & Cur Best Value for Idx.

                    if (start > 1) { // If Start Not On First Idx, Check If Cur Segment has Larger Result.

                        if (table[start - 1][segment - 1] != -1) {// If Previous Segment is Valid.
                            
                            if (table[idx][segment] < table[start - 1][segment - 1] + curBest) {
                                
                                table[idx][segment] = Math.max(table[idx][segment], table[start - 1][segment - 1] + curBest); // Update Cur Val of Cur Idx's to New Max.
                                tracker[idx][segment] = start; // Add Start Idx to Tracker Table.

                            }
                        }

                    } else {

                        if (table[idx][segment] < curBest) {

                            table[idx][segment] = Math.max(table[idx][segment], curBest); // If No Idx Before, Compare with CurBest Directly.  
                            tracker[idx][segment] = start; // Add Start Idx to Tracker Table.
                        }

                    }
                }
            }
        }

        System.out.println(table[numInts][numSegments]); // Display Final DP table value, dp[i][j].
        
        ArrayList<Integer> startIdxs = new ArrayList<>(); // Create List to Hold Start Idxs.

        // Working backwards.
        int cur = numInts; // Start from Last Idx.
        int back = numSegments; // Start from Last Segment.

        while (back > 0 && cur > 0) { // While Segments != 0.

            int start = (int)tracker[cur][back]; // Set Start Idx's to Tmp Val.

            startIdxs.add(start); // Add Tmp Val to List.

            cur = start - 1; // Decrement Idx #.

            back--; // Decrement Segment #.

        }

        Collections.reverse(startIdxs); // Reverse List (b/c we're "backtracking" idx's are backwards).

        
        // Print out Idx's with proper format.
        for (int idx : startIdxs) { 
            System.out.print(idx + " ");
        }
    
    }
}
