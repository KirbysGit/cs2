// Colin Kirby
// 3/28/2024
// codes.java
// This code utilizes the Dynamic Programming to calculate the percentage of availabe tight words
// based on vals k and n, where k represents the final value of a list of possible values, and n
// represents the length of said "word" of which each index must not be less than 1 value of each
// other on both sides. We can do this using DP to create more efficient approach to prevent 
// references to states that dont provide any use to the calculation of the final value thus 
// saving run time.

import java.util.Scanner; // Import Util.

import java.util.Arrays;

public class tight {

    private static int posVals, wordLen; // Global Declarations.

    private static double[][] myMemo;
    
    private static double dp(int remainingVals, int cur) { // Dp Func.

        if (cur < 0 || cur > posVals) return 0; // If current digit is out of bounds, return 0.

        if (remainingVals == 1) return 1; // If one character left, that's one tight word.

        if (myMemo[cur][remainingVals] != -1) return myMemo[cur][remainingVals]; // Return result already stored if it exists.
        
        double sol = 0; // Declare Val for # Sols for Cur State.

        for (int i = cur - 1; i <= cur + 1; i++) { // Check Prev, Cur, and Next Digits.

            sol += dp(remainingVals - 1, i); // Call Dp per Digit.

        }

        myMemo[cur][remainingVals] = sol; // Set Ans of Cur State to Val of Memo.

        return sol; // Return Ans of Cur State.
    }

    public static void main(String[] args) {

        Scanner inp = new Scanner(System.in); // Declare Scanner.

        while (inp.hasNextInt()) { // While Active Inp.

            posVals = inp.nextInt(); // Max Value so we know Possible Values (0 to k).

            wordLen = inp.nextInt(); // Length of Word.

            myMemo = new double[posVals + 1][wordLen + 1]; // Initialize Arr to Hold Vals for States.

            for (double[] arr : myMemo) { // For Arr in 2D Arr.

                Arrays.fill(arr, -1); // Fill with Empty Values (-1).

            }

            double totalPossibilities = Math.pow(posVals + 1, wordLen); // Calculate total possibilities of words.

            double tightWordsCount = 0; // Val to hold final answer per digit iteration.

            for (int i = 0; i <= posVals; i++) { // Iterate over all possible starting digits. (0 - k)

                tightWordsCount += dp(wordLen, i); // Add all tight words per i value.
            }

            System.out.printf("%.10f\n", (tightWordsCount * 100) / totalPossibilities); // Print the percentage of tight words.
        }
    }
}
