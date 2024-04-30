// Colin Kirby
// 3/4/2024
// breakingbad.java
// https://open.kattis.com/problems/breakingbad

import java.io.*;
import java.util.*;
// HashMap<Integer, String> retrieve
// HashMap<String, Integer> nodeValue
//
// read data items and add to hashmap
//
// ArrayList<ArrayList<Integer>> edges and Initialize
// for (i to n) edges.add(new ArrayList(<>())
//
// read the edges and add them
// u, v
// edges.get(u).add(v) 
// edges.get(v).add(u)
//
// int [] color 1e6
//
// boolean flag
// for (0 to n)
//  if color[i] != -1 continue
//  else dfe(i, 0, color, edges)
//
// if flag is false : impossible
// else 
//  print all colors that have 1
//  all colors that have 0
//
// function boolean dfs(int pos, int color, int[] color, ar<ar<>> edges) 
//      if (color[pos] != - 1) 
//          return color[pos] == color
//  
//      color[pos] = color
//      boolean result = false
//      oppColor = (color == 0) ? 1 : 0
//      for(ar<> ed: edges.get(pos))
//         result &= dfs(pos, oppColor, color, edges)

// DFS
// node
//  visited
// for (edge : node) 
//  rec(edge)

// BFS
// queue
// while (!queue empty)
//  node = queue.pop
//  visited
//  for (edge : node)
//      if !visited
//      queue.odd(edge)


// HashMap<int, string> -> for print
// HasHMap<String, int> -> for work through

// dfs
// != -1
//  return color[i] == C
// flag

public class breakingbad { 

    // Maps Indexes to Items
    private static HashMap<Integer, String> retrieves = new HashMap<>();

    // Maps Items to Indexes
    private static HashMap<String, Integer> values = new HashMap<>();

    // ArrayList to Represent Edges of Graph
    private static ArrayList<ArrayList<Integer>> edges = new ArrayList();

    // Array to Store "colors" of Nodes, Initialized to 1000000 Based Off TA Instruction
    private static int [] color = new int [1000000];

    // DFS method based off TA and Guha's Method in Class.
    private static boolean dfs (int index, ArrayList<ArrayList<Integer>> edges, int[] color) {
        // For All Edges, Color Nodes, Check Possiblility.
        for (int nextNode : edges.get(index)) {
            // If Uncolored.
            if (color[nextNode] == -1) {

                // Color Opposite of Prev Node.
                color[nextNode] = 1 - color[index];

                // Recursively Color Nodes.
                if (!dfs(nextNode, edges, color)) {
                    return false;
                }

            } else if (color[nextNode] == color[index]) {
                // Both Cur and Next Node are Same Color so Impossible (Not Bipartite)
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        
        // Declare FastScanner Obj to Take Input.
        FastScanner inp = new FastScanner();

        // Declare Val to Hold # Items.
        int numItems = inp.nextInt();

        // Initialize ArrayList edges per Node.
        for (int i = 0; i < numItems; i++) { 
            edges.add(new ArrayList<>());
        }

        // Initialize all Nodes in Color to -1 (Uncolored).
        Arrays.fill(color, -1);

        // Accept Input to ArrayList of Items.
        for (int i = 0; i < numItems; i++) {
            // Take Next Item.
            String items = inp.next();

            // Place Item at Index i.
            retrieves.put(i, items);

            // Place Index i at "Index" of Items
            values.put(items, i);
        }

        // Declare Val to Hold # Suspicious Pairs.
        int numPairs = inp.nextInt();

        // Accept Input for Suspicious Pairs.
        for (int i = 0; i < numPairs; i++) {

            // Take Next Pair of Items.
            String suspicious1 = inp.next();
            String suspicious2 = inp.next();

            // Gets Indexes of Items Names from Map.
            int u = values.get(suspicious1);
            int v = values.get(suspicious2);

            // Create double-edged direction (or undirectional direction).
            edges.get(u).add(v);
            edges.get(v).add(u);
        }

        // Flag Val to Guarantee Correct Output.
        boolean notSuspicious = true;

        // Initialize All Arr Vals of color to -1.
        for (int i = 0; i < numItems && notSuspicious; i++) {
            if (color[i] == -1) {
                color[i] = 0;
                if (!dfs(i, edges, color)) {
                    notSuspicious = false;
                }
            }
        }

        // If at a Point in DFS, Cur & Next Node have Same Color.
        if (notSuspicious) {
            // List for Items (walter representing Red, jesse representing Blue).
            ArrayList<String> waltersList = new ArrayList<>(); 
            ArrayList<String> jessesList = new ArrayList<>();

            // Iterates Through All color Indexes, Adding Nodes colored "0" to Walter and Nodes colored "1" to Jesse
            // Based on Key (Index i)
            for (int i = 0; i < numItems; i++) {
                if (color[i] == 0) {
                    waltersList.add(retrieves.get(i));
                } else {
                    jessesList.add(retrieves.get(i));
                }
            }

            // Displays List with Spaces Between Each Item.
            System.out.println(String.join(" ", waltersList));
            System.out.println(String.join(" ", jessesList));
        } else {

            // If at NO Point a Solution Exists, Display Impossible.
            System.out.println("impossible");
        }

        /*
        System.out.println("Num Items : " + numItems);
        System.out.println("Items : " + items.toString());
        System.out.println("Num Pairs : " + numPairs);
        System.out.println("Suspicious Pairs : " + suspicious.toString());
        */
    }
}

// FastScanner Class Provided by the TA
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
