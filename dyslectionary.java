// Colin Kirby
// 1/22/2024
// dyslectionary.java
// https://open.kattis.com/problems/dyslectionary

import java.util.*;

// scanner read datad
// list
// while hasNext
//    addstring to list, until you find blank line
//    if blank line; sort list, print list, empty list
//       collections sort, comaprator function or lamba sorting
//       compare each letter from right and keep track of index (out of bounds)
//              can reverse both strings then compare both strings
//       print list: right justifed
//                find longest length
//                pad the remainign space in front for string, longest - length
//    else keep on adding words to the list
//
//  if list is not empty sort list and print list


// while loop with hasNext
public class dyslectionary {
    
    public static void main(String[] args) {
        // scanner
        Scanner myScanner = new Scanner(System.in);

        // list
        List<String> myList = new ArrayList<>();

        // while hasNext loop
        while (myScanner.hasNextLine()) {
            // take in next line
            String nextLine = myScanner.nextLine();

            // if empty then begin sort, print and empty list
            if(nextLine.isEmpty()) {
                mySort(myList);
                // prints new line after mySort is complete
                if (myScanner.hasNextLine()) {
                    System.out.println();
                }
                myList.clear();
            } else {
                // else add nextLine to list
                myList.add(nextLine);
            }
        }

        // if list is still not empty after while loop, sort and print again
        if (!myList.isEmpty()) {
            mySort(myList);
        }

    }

    private static void mySort(List<String> myList) {

        // call collections sort on two strings flipped at a time then return value compareTo
        Collections.sort(myList, (myList1, myList2) -> {
            String flipString1 = new StringBuilder(myList1).reverse().toString();
            String flipString2 = new StringBuilder(myList2).reverse().toString();
            return flipString1.compareTo(flipString2);
        });

        int len = 0;

        // solves for max length to allow for right justify 
        for (String line : myList) {
            len = Math.max(len, line.length());
        }

        // special formatting printf in java to pad the left side with proper number of spaces (instead of using for loop for spaces)
        for (String word : myList) {
            System.out.printf("%" + len + "s\n", word);
        }

    }
}
