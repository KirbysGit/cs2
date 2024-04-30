// Colin Kirby
// 4/17/2024
// SOLVED BOTH TASK
// subseqsum.java
// This code utilizes dynammic programming to obtain the highest possible sum of "smooth"
// subsequence values, with smooth referring to the adjacent values being within k of each
// other. This is done by incrementing through the possible sub sequences in a more efficient
// manner and saving the max values within the dp table (mySubSeq). After filling out table
// finding the max value, and thus the idx of that value and reversing back through utilizing
// the tracker arr that holds the prev idx of the cur idx holding the max value. Then properly
// adjusting the idx's to correct order and 1-based idx'ing and then display these values to
// the user.

import java.util.*; // Imports.
import java.io.*;


public class subseqsum {
    
    public static void main (String [] args) {

        Scanner inp = new Scanner(System.in); // Inp Declarator.

        int numInts = inp.nextInt(); // Take # Ints.

        long smooth = inp.nextLong(); // Take # Segments.

        int [] inpList = new int [numInts]; // Declare Inp List.

        for (int i = 0; i < numInts; i++) { // Take Ints.

            inpList[i] = inp.nextInt(); // Insert into List.

        }

        long [] mySubSeq = new long [numInts]; // Declare dp table.

        long [] tracker = new long [numInts]; // Declare tracker array.

        for (int i = 0; i < numInts; i++) {

            mySubSeq[i] = inpList[i]; // Initialize dp table to Inp List.

            tracker[i] = -1; // Initialize tracker arr to empty values (-1).

        }

        dp(numInts, smooth, inpList, mySubSeq, tracker); // Call dp func.

    }

    public static void dp (int numInts, long smooth, int [] inpList, long [] mySubSeq, long [] tracker) {

        // Process of Filling DP Table with Max Possible Values at that Idx.

        for (int endPt = 0; endPt < numInts; endPt++) { // For Possible EndPt in List.

            for (int idx = 0; idx < endPt; idx++) { // For Possible Idx Before EndPt.

                // If "Smooth" (Difference <= k) & New Sum of Cur SubSeq > Cur Val in Dp Table.
                if (Math.abs(inpList[endPt] - inpList[idx]) <= smooth && mySubSeq[idx] + inpList[endPt] > mySubSeq[endPt]) {

                    mySubSeq[endPt] = mySubSeq[idx] + inpList[endPt]; // Set Cur Val of Dp Table of EndPt to New Sum.

                    tracker[endPt] = idx; // Save Start Idx of SubSeq.

                }
            }
        }

        long actualMax = 0; // Declare Var to Hold Max Value as dp[finalVal] != Max Val (in some cases).

        long lastIdx = 0; // Declare Var to Hold Last Idx for Tracker.

        /* 
        System.out.print("dpTable: ");
        for (int i = 0; i < numInts; i++) {
            System.out.print(mySubSeq[i] + " ");
        }
        System.out.print("\n");
        System.out.print("Tracker: ");
        for (int i = 0; i < numInts; i++) {
            System.out.print(tracker[i] + " ");
        } */

        //System.out.println("\n");

        for (int i = 0; i < numInts; i++) { // Iterate through dp table.

            if (mySubSeq[i] > actualMax) { // If cur dp Val is greater than highest dpVal.
                
                actualMax = mySubSeq[i]; // Set new max Val to cur dp Table Val.

                lastIdx = i; // Save Idx of max Val.

            }
        }

        //System.out.println("LAST IDX: " + lastIdx);
        
        ArrayList<Long> seqIdxs = new ArrayList<>();

        while (lastIdx != -1) { // While Last Idx Not Empty Val.

            seqIdxs.add(lastIdx + 1); // Add Idx to List (Convert back to 1-based for Display).

            lastIdx = tracker[(int)lastIdx]; // Go to Prev Idx of Cur SubSeq.

            //System.out.println("Prev Idx: " + lastIdx);

        }

        Collections.reverse(seqIdxs); // Flip order to reverse backtracking process to obtain Idxs.

        System.out.println(actualMax); // Print out max val.

        for (long idx : seqIdxs) { // Print out Idx's with proper format.

            System.out.print(idx + " ");

        }
    }
}
