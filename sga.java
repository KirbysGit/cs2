// Colin Kirby
// 1/11/2024
// sga.java
// Java Code that calculates the number of possible President/Vice-President pairs
// who have a potential to win the SGA election through the usage of multiple HashMaps
// and its according API.

// Import Scanner and HashMap Classes
import java.util.Scanner;
import java.util.HashMap;

// DEclare Public Class
public class sga {
    public static void main (String [] array) {

        // Declaring two HashMaps to track counts of first letters and counts of individual names
        HashMap<Character, Integer> sameFirstLetterCount = new HashMap<>();
        HashMap<String, Integer> sameNameCount = new HashMap<>();

        // Holds final value of pairs, using long because int value can be exceeded
        long finalPairs = 0;

        // Taking input of number of students
        Scanner myScanner = new Scanner(System.in);
        int ucfStudents = myScanner.nextInt();

        // Takes in newLine char to not disrupt further string input
        myScanner.nextLine(); 

        for (int i = 0; i < ucfStudents; i++) {
            // Reads student names
            String studentName = myScanner.nextLine();

            // Takes first letter of student name
            char letterOne = studentName.charAt(0);

            // Increments count of letter from first letter of student name
            if (sameFirstLetterCount.containsKey(letterOne)) {
                // If COunter for First Letter Exists then Increment that Counter 
                sameFirstLetterCount.put(letterOne, sameFirstLetterCount.get(letterOne) + 1);
            } else {
                // If Counter for First Letter DNE then set Value to 1
                sameFirstLetterCount.put(letterOne, 1);
            }

            // Increments count of student name
            if (sameNameCount.containsKey(studentName)) {
                // If Counter for the Student Name Exists then Increment that Counter
                sameNameCount.put(studentName, sameNameCount.get(studentName) + 1);
            } else {
                // If Counter for Student Name DNE then Set Counter to 1
                sameNameCount.put(studentName, 1);
            }
        }

        // Calculates number of possible pairs by Multiplying Count by Itself minus 1 to Obtain the possible variations per First Letter
        for (Character key : sameFirstLetterCount.keySet()) {
            int count = sameFirstLetterCount.get(key);
            finalPairs += (long) count * (count - 1);
        }

        // Considers the possibility of multiple names being the same and decrements from the total amount
        for (String key : sameNameCount.keySet()) {
            int count = sameNameCount.get(key);
            if (count > 1) {
                finalPairs -= (long) count * (count - 1);
            }
        }

        // Outputs final value
        System.out.println(finalPairs);
    }
}
