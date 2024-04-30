// Colin Kirby
// 2/27/2024
// classrooms.java
// Java Code that displays the maximum number of activities that can be scheduled based on
// an n amount of pairs representing start and end time of those activities, and using a 
// greedy algorithm (sorting by end times then placing those end times most efficiently
// based on next lowest time) by way of a TreeMap to produce the correct answer.

// * Didn't understand how to implement a TreeSet with this code's purpose, also
// didn't understand the hint provided in the Directions file, so I used a TreeMap
// instead, hopefully thats alright, apologies if not*


import java.util.*;

class Event implements Comparable<Event> { // New Object to Help Sort Time Pairs
    int startTime; 
    int endTime;

    Event(int startTime, int endTime) { // Initialize Event with Start & End Time
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int compareTo(Event other) { // CompareTo method to first compare endTime then startTime
        if (this.endTime == other.endTime) { // Compare End
            return this.startTime - other.startTime; // Compare Start, If End are Same
        }
        return this.endTime - other.endTime;
    }
}



public class classrooms {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in); // Scanner Declarator

        int propActs = inp.nextInt(); // Take In # of Proposed Activities
        int classRooms = inp.nextInt(); // Take In # of Classrooms

        // TreeMap: Key = Time, Val = # of Rooms Available
        TreeMap<Integer, Integer> isAvailable = new TreeMap<>(); // Declare TreeMap to Hold If Rooms Are Available
        List<Event> myEvents = new ArrayList<>(); // Events List to Allow for Efficient Insertion after Sort into TreeMap

        for (int i = 0; i < propActs; i++) { // Takes in Start and End Times then Inserts Into Object List
            int startTime = inp.nextInt(); // start
            int endTime = inp.nextInt(); // end
            myEvents.add(new Event(startTime, endTime)); // insertion
        }

        Collections.sort(myEvents); // Sorts based off CompareTo method, Sorting by End Times then Start Times accordingly

        for (int i = 0; i < classRooms; i++) { // Initializes Map
            isAvailable.put(0, ifExists(isAvailable, 0) + 1); // Sets Availibilty to 0 for All Classrooms
        }

        int ans = 0; // Initializes Output to 0

        for (Event party : myEvents) { // For Event in Event List
            // Takes the Available Room based off the Start Time per Entry
            Map.Entry<Integer, Integer> firstIndex = isAvailable.floorEntry(party.startTime); 

            if (firstIndex != null) { // Assuming Entry is Not NULL!
                // System.out.println("INSIDE ENTRY!!");
                int openTime = firstIndex.getKey(); // Takes Time when Room is Available
                int openRoom = firstIndex.getValue() - 1; // Gets rid of 1 Available Room

                if (openRoom == 0) { // If There Are No More Open Rooms
                    isAvailable.remove(openTime); // Remove that time b/c nothings available at that time now
                } else { // There Are Open Rooms
                    isAvailable.put(openTime, openRoom); // Put that time slot in that open room
                }

                // Serves as Reset Line
                // Increments End Time by 1 to allow check of next available slot right after (cannot be 4 5 & 5 6)
                // Increments # of Rooms as well in 2nd parameter after checking cur # of rooms with isExists
                isAvailable.put(party.endTime + 1, ifExists(isAvailable, party.endTime + 1) + 1);
                
                ans++; // Increment Answer as new Max Vals are found
            }
        }

        System.out.println(ans); // Print answer
    }

    // Method to prevent issues with NULL values, if map has key return it, else return 0
    private static int ifExists(TreeMap<Integer, Integer> myMap, int cur) {
        if (myMap.containsKey(cur)) {
            return myMap.get(cur);
        }
        return 0;
    }
}
