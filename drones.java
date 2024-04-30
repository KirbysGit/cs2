// Colin Kirby
// 3/17/2024
// drones.java
// Java Code that takes in an 8 x 8 Grid of Drones, Group, NFZ's, and Empty Spaces, that by using BFS
// we must find the smallest amount of coordinated movements for the drones for them each to reach
// their respective group. This assignment was done using BitMasking to store the States of the Grid,
// in contradiction to storing the whole grid.

import java.io.*; // Import Statements.
import java.util.*;

public class drones { 

    private static int[][] moves = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}}; // W E N S

    private static HashSet<Integer> noFlyZones = new HashSet<>(); // Declare HashSet to Hold N.F.Z's on Idx basis.

    public static void main(String[] args) {

       FastScanner inp = new FastScanner(); // Declare Scanner.

       int numDrones = inp.nextInt(); // Take in # of Drones.

       int droneIdx = 0, groupIdx = 0; // Declare droneIdx & groupIdx to 0.

       long startPt = 0, finalPt = 0; // Declare All States to 0.

       boolean [] atGroup = new boolean[numDrones]; // Declare Bool Arr to hold if at Group.

       Arrays.fill(atGroup, false); // Initialize atGroup Arr.

       for (int i = 0; i < 8; i++) { // For X in Row
        
            for (int j = 0; j < 8; j++) { // For Y in Column
            
                String cur = inp.next(); // Take Next Inp
                
                if (cur.startsWith("D")) { // If Dx (x = 1, 2, 3, 4).

                    droneIdx = Integer.parseInt(cur.substring(1)) - 1; // Obtains the x from the Dx in Inp.
                    startPt |= ((long)(i * 8 + j) << (6 * droneIdx)); // Takes Dx Cur Pos & Shifts Into Correct Placement.

                } else if (cur.startsWith("G")) { // If Gx (x = 1, 2, 3, 4).

                    groupIdx = Integer.parseInt(cur.substring(1)) - 1; // Obtains the x from the Gx in Inp.
                    finalPt |= ((long)(i * 8 + j) << (6 * groupIdx)); // Takes Gx Cur Pos & Shifts Into Correct Placement.

                } else if (cur.startsWith("X")) { // If Idx = XX (N.F.Z)

                    noFlyZones.add(i * 8 + j); // Set NoFlyZone for Idx.
                }
            }
       }

       // Debugging...
       // System.out.println("Start Pt: " + Long.toBinaryString(startPt));
       // System.out.println("Final Pt: " + Long.toBinaryString(finalPt));

       int[] distances = new int[1 << (6 * numDrones)]; // Create very large Arr based on # of Drones in Problem

       Arrays.fill(distances, -1); // Initialize all values of distances Arr to -1.
    
       int finalVal = bfs(startPt, finalPt, distances, numDrones, atGroup); // Call bfs on start & final Pt.
       
       System.out.println(finalVal); // Display Val to User.
       


    }

    private static int bfs(long startPt, long finalPt, int[] distances, int numDrones, boolean [] atGroup) {
        
        LinkedList<Long> ptQueue = new LinkedList<>(); // Declares Queue of Possible Idx's in BFS.

        ptQueue.add(startPt); // Add CurPt to Queue.

        distances[(int) startPt] = 0; // Declare Distance of CurPt to 0.

        while (!ptQueue.isEmpty()) { // While Queue is NOT Empty.

            long curPt = ptQueue.poll(); // Take Idx at Front of Queue.

            int curDist = distances[(int) curPt]; // Take Dist from Idx at Front of Queue.

            Arrays.fill(atGroup, false); // Resets atGroup to all False.

            oneAtGroup(curPt, finalPt, atGroup, numDrones); // Updates atGroup's Booleans.

            if (allAtGroup(atGroup, curPt, finalPt)) return curDist; // If All at Cor. Group, return Distance.

            for (int[] move : moves) { // For Possible Move, N, S, E, W.

                long nextPos = curPt; // Initialize nextPos to curPos.

                for (int i = 0; i < numDrones; i++) { // For Drone.

                    int dronePt = (int) (curPt >> (6 * i) & 63); // Take Idx of Cur Drone.

                    int row = dronePt / 8; // X-coord of Cur Drone Idx.
                    int col = dronePt % 8; // Y-coord of Cur Drone Idx.

                    int curRow = row + move[0]; // W or E or NO_MOVE.
                    int curCol = col + move[1]; // N or S or NO_MOVE.

                    if (curRow >= 0 && curRow < 8 && curCol >= 0 && curCol < 8) { // Immediate InBounds Check.

                        if (validMove(i, curRow, curCol, finalPt, atGroup)) { // If Valid Move.

                            nextPos &= ~(63L << (6 * i)); // Clear Cur Drone Idx.
                            nextPos |= ((long) (curRow * 8 + curCol) & 63) << (6 * i); // Set Cur Drone Idx w/ BitMask.

                        }
                    }
                }

                if (distances[(int) nextPos] == -1) { // If nextPos NOT Visited.

                    distances[(int) nextPos] = curDist + 1; // Increment Distance.

                    ptQueue.add(nextPos); // Add nextPos to Queue.

                }
            }
        }
        return -1; // If the goal is not reachable.
    }

    private static void oneAtGroup(long curPt, long finalPt, boolean [] atGroup, int numDrones) {
        

        for (int i = 0; i < numDrones; i++) { // For Drone.
            
            int dronePos = (int) (curPt >> (6 * i) & 63); // Take cur Idx of Drone "i".
            int groupPos = (int) (finalPt >> (6 * i) & 63); // Take cur Idx of Group "i";

            if (dronePos == groupPos) { // If Idx's are =.

                atGroup[i] = true; // Drone "i" is at desired Group "i";
                
            }
        }
    }

    private static boolean allAtGroup(boolean [] droneReached, long curPt, long finalPt) {
        
        for (boolean home : droneReached) { // For All Drones.

            if (!home) return false; // If Not at desired Group, False.

        }

        // Debugging...
        // System.out.println("CurPt : " + Long.toBinaryString(curPt));
        // System.out.println("FinalPt : " + Long.toBinaryString(finalPt));

        return true; // All at desired Group, True.
    }

    private static boolean validMove(int droneIdx, int row, int col, long finalPt, boolean [] atGroup) { // Checks Next Move If ...
        
        int targetPos = (int) (finalPt >> (6 * droneIdx) & 63); // Obtain Idx Val of Group "i" in Grid.

        if ((row * 8 + col) == targetPos) return true; // If Next Move is to Group "i" for Drone "i", true.

        if (atGroup[droneIdx]) return false;;  // Already at Group.

        if (row < 0 || row >= 8 || col < 0 || col >= 8) return false; // Out of Bounds.

        if (noFlyZones.contains(row * 8 + col)) return false; // No Fly Zone.

        for (int i = 0; i < atGroup.length; i++) { // For Drone.

            if (i != droneIdx) { // If Not Drone "i".

                int groupPos = (int) (finalPt >> (6 * i) & 63); // Obtain Idx Val of Group "i".

                if ((row * 8 + col == groupPos)) return false; // If Next Move is to Group "i" for Drone "j", false.

            }
        }

        return true; // All Criteria Met for Valid Move.
    }
}

// FastScanner Class Provided by the TA (I dont think this really affects this program, but I included it just in case.)
class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    FastScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
        update();
    }

    private void update() {
        try { 
            st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String next() {
        if (st.hasMoreTokens())
            return st.nextToken();
        update();
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

}