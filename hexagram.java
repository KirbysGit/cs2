// Colin Kirby
// 2/14/2024
// hexagram.java


import java.util.*;
import java.io.*;
// Import util & io

public class hexagram {

    // Private Static Global Variables Declarations
    private static int[] puzzle = new int[12]; // Input Value
    private static boolean[] used = new boolean[12]; // Used Value
    private static int[] cur = new int[12]; // Current Perm
    private static int answer; // Solution
    private static int magic; // Line Sum

    public static void main(String[] args) {
        // Declares Scanner for Input
        Scanner stdin = new Scanner(System.in);

        // Takes First 12 Int's of Input
        for (int i = 0; i < 12; i++) puzzle[i] = stdin.nextInt();

        // Declares answer Val to 0
        answer = 0;


        // While Input is NOT 12 Zeros
        while(!zero()) {
            //  Our Magic Formula 
            magic = magicSum();

            // Declares All Falses in used Boolean Arr to be False
            for (int i = 0; i < 12; i++) {
                used[i] = false;
            }

            // Sets First Index to True To Solve For Perms from that Value On
            used[0] = true;

            // Sets All Values in Cur to 0
            for (int i = 0; i < 12; i++) {
                cur[i] = 0;
            }

            // Sets First Index of Cur to first Input Value
            cur[0] = puzzle[0];

            // Calls solveHex func with Index initialized at 0
            solveHex(0);

            // Displays Answer
            System.out.println(answer);

            // Resets Answer to 0
            answer = 0;

            // Takes next 12 Int's of Input
            for(int i = 0; i < 12; i++) puzzle[i] = stdin.nextInt();
        }
    }
    
    // Returns False if All of Input Arr is 0's
    private static boolean zero() {
        for (int num : puzzle) {
            if (num != 0) {
                return false;
            }
        }
        return true;
    }

    // Returns Magic Formula for Hexagram Line Sum Values : (Sum of Val's) / 3
    private static int magicSum() {
        int sum = 0;
        for (int num : puzzle) {
            sum += num;
        }
        sum = sum / 3;
        return sum;
    }

    // Returns Nothing, Increments Answer when Index has finally Incremeneted All the Way Up to 12
    private static void solveHex(int index) {
        int expected = -1; // flag Value

    
        /*int[][] lines = {
            {0, 1, 2, 3}, {0, 4, 5, 6}, {3, 6, 7, 8}, {1, 4, 9, 10}, { 5, 7, 9, 11}, {2, 8, 10, 11}
        };*/

        // If Statements to Only Prune when at Specific Indexes (6, 8, 10, 11)
        // Then Solves for the expected Val, and saves that value to the Var, if expected is < 0 we then Reset to 0
        if (index == 6 || index == 8 || index == 10 || index == 11) {
            if (index == 6) 
            { 
                expected = magic - cur[0] - cur[4] - cur[5];
            } else if (index == 8) 
            { 
                expected = magic - cur[3] - cur[6] - cur[7];
            } else if (index == 10) 
            { 
                expected = magic - cur[9] - cur[4] - cur[1];
            } else 
            { 
                expected = magic - cur[9] - cur[5] - cur[7];
                if (expected >= 0 && expected != magic - cur[10] - cur[2] - cur[8]) expected = 0;
            }
            if (expected < 0) expected = 0; // Resetter
        } else if (index == 12) {
            // If Index Val Incremented to 12 with No Pruning Prior then we must have a Solution!!!
            // But I call checkAnswer to handle any extraneous cases I didn't consider, then returns to break current call
            if (checkAnswer()) answer++;
            return;
        }

        // If Index is still Less Than 12 and Value at Index equals 0 (Hasnt been changed yet)
        if (index < 12) {
            if (cur[index] == 0) {
                // Iterates through all 12 values in array that
                for (int i = 0; i < 12; i++) {
                    // If Not Used And Expected Value is Less Than Zero Or Expected is the Value in Puzzle
                    if (!used[i] && (expected < 0 || puzzle[i] == expected)) {
                        // Set used to True to prevent BackTracking over it again
                        used[i] = true;
                        // Set Cur Index to Value of Input at index i
                        cur[index] = puzzle[i];

                        // Recursive Call
                        solveHex(index + 1);

                        // Reset Used for Next Posistion or Perm
                        used[i] = false;
                    }
                }
                // Reset Index of Current Permutation to 0 to consider this Index for Diff Perm
                cur[index] = 0;
            } else {
                // If Not Equal to 0 Increment Index Up and Permutate Again!!!
                solveHex(index + 1);
            }
        }
    }

    // Final Checker for My Code considering I missed some Extra Possible Cases and the Index still makes it to 12
    private static boolean checkAnswer() {
        // Declares the Lines of Hexagram in Terms of Indexes of Vertics
        int[][] lines = {
            {0, 1, 2, 3}, {0, 4, 5, 6}, {6, 7, 8, 3}, {9, 4, 1, 10}, {9, 5, 7, 11}, {10, 2, 8, 11}
        };

        // Iterates through each Int Array in Lines
        for (int[] line : lines) {
            // Resets sum each Iteration
            int sum = 0;
            // Iterates through Each Index in Line
            for (int index : line) {
                // Adds current Index Value to Sum
                sum += cur[index];
            }
            // Checks if Sum is Magic Number
            if (sum != magic) {
                // If Not, Return False
                return false;
            }
        }
        // Else, Return True
        return true;
    }

    
}
