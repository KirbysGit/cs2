// Colin Kirby
// 2/12/2024
// dobra.java
// Java Code that calculates the number of possible pleasant words with "pleasant"
// being defined by 3 constraints, that there is no 3 consecutive vowels and/or
// consonant pairs, and the string must contain the letter 'L'. The program uses
// a backtracking method to approach this problem and pruning based on provided
// constraints to prevent the 3^10 possiblities

import java.util.Scanner;


// TA PSUEDO CODE :
//
// 1. Permutation
//      pos in word is _, then try to place either vowel or cons
//      3 consecutive vowels or const and there has to at least one L
// if you reach end, return hasL, then 1 else 0
// check if possible
//      pos, loop, bounds, left and right
//          - pos >= 2 [pos - 1] [pos - 2]
//          - pos >= 1 && pos < n - 1 [pos - 1] [pos + 1]
//          - pos < n - 2 [pos + 1] [pos + 2]
// 'A'
// += 5 * res
// 'B'
// += 20 * res
// 'L'
// += res

public class dobra {

    static long ans = 0; // Global Var for Ans Bc Int too Small

    public static void main(String[] args) {
        Scanner user = new Scanner(System.in); 
        String input = user.nextLine(); // Input
        char[] myLetters = input.toCharArray(); // Conversion of Inp Str to Char Arr


        pleasantCheck(myLetters, 0, 1); // Method Call with cur Multiplicative set to 1 (comboWays)
        System.out.print(ans); // Display Final
    }

    // Determines if the provided char Arr meets the constraints provided in the problem.
    //      - Can NOT have 3 Consecutive Consonants and/or Vowels
    //      - Must contain the char 'L'
    private static boolean validStr(char[] arr) {
        boolean LFlag = false;
        int consCons = 0; // Increment Trackers
        int consVowel = 0;

        // Iterates through all chars in Arr checking : 
        for (char cur : arr) {
            if (cur == 'L') LFlag = true; // If char is a Vowel

            if (vowelCheck(cur)) { // If char is a Vowel
                consVowel = consVowel + 1; 
                consCons = 0; // reset
            } else if (consonantCheck(cur)) { // If char is a Consonant
                consCons = consCons + 1;
                consVowel = 0; // reset
            }
              
            if (consCons >= 3 || consVowel >= 3) return false; // Consecutive Checker
        }

        return LFlag; // If meets 3 consecutive requirement, Then Check if meets LFlag requirement
    }

    // My BackTracking Method
    private static void pleasantCheck(char[] arr,  int index, long comboWays) {  
        //System.out.println(Arr);
        int len = arr.length; // Len Declarator

        // If Index = Len of Array, Check if Valid Str, If so, Increment Ans by Value in Parameters (ComboWays)
        if (index == len) { 
            if (validStr(arr)) { // If hasL
                ans += comboWays;
            }
            return;
        } else if (index < len) { // While Index < Len

            if (arr[index] != '_') {
                pleasantCheck(arr, index + 1, comboWays); // If Arr is not '_' Char, Move onto Next Index
            } else {
                char[] cloneArrL = arr.clone(); 
                cloneArrL[index] = 'L';
                pleasantCheck(cloneArrL, index + 1, comboWays * 1); // Uses comboWays * 1, as 'L' provides only 1 output

                char[] cloneArrC = arr.clone();
                cloneArrC[index] = 'B';
                pleasantCheck(cloneArrC, index + 1, comboWays * 20); // Uses comboWays * 20, to simulate 20 Consonants (Not L)

                char[] cloneArrV = arr.clone();
                cloneArrV[index] = 'A';
                pleasantCheck(cloneArrV, index + 1, comboWays * 5); // Uses comboWays * 5, to simulate 5 Vowels
            }
        }
    }
       
    // Checks if provided Char is a Vowel
    private static boolean vowelCheck (char cur) {
        String vowels = "AEIOU";
        for (int i = 0; i < vowels.length(); i++) {
            if (vowels.charAt(i) == cur) {
                return true;
            }
        }
        return false;
    }

    // Checks if provided Char is a Consonant
    private static boolean consonantCheck (char cur) {
        String consonants = "BCDFGHIJKLMNPQRSTVWXYZ";
        for (int i = 0; i < consonants.length(); i++) {
            if (consonants.charAt(i) == cur) {
                return true;
            }
        }
        return false;
    }
}

