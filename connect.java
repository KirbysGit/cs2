// Colin Kirby
// 2/15/2024
// connect.java
// Java Code that displays a fraction reperesenting average connectivity of a disjoint set when queried
// after a user connects certian elements to each other through specific commands.

// sample input #1 : 

// start        1 2 3 4 5 6 7

// query -> return 7 / 7 (unchanged) -> 1 / 1

// connect #1   1 1 3 4 5 6 7
// connect #2   1 1 1 4 5 6 7

// query -> ( 3^2 + 1^2 + 1^2 + 1^2 + 1^2 ) / 5 -> 13 / 5

// connect #3   1 1 1 1 5 6 7
// connect #4   1 1 1 1 5 6 7 (no change)

// query -> ( 4^2 + 1^2 + 1^2 + 1^2 ) / 4 -> 19 / 4

// connect #5   1 1 1 1 5 6 6

// query -> ( 4^2 + 1^2 + 2^2 ) / 3 -> 7 / 1



import java.util.Scanner;
import java.util.Arrays;

public class connect {


    static class djset { 
        private int[] par; // arr to hold parent of each index
        private int[] child; // arr to hold # of children for each index
        private long nodeSq = 0; // sum holding (sizes of each element)^2 
        private long nodes = 0; // holds number of separete groups
    
    
        // Constructor: n separate trees, initialize all parents to selves
        public djset(int size) {

            nodes = size; // set equal to size at beg.
            nodeSq = size; // set equal to size at beg.
            par = new int[size]; // initializes size of par arr
            child = new int[size]; // initializes size of child arr

            // sets all values in par arr to index and all values in child arr to 1
            for (int i = 0; i < size; i++) {
                par[i] = i;
                child[i] = 1;
            }
        }
    
        // find the root of tree storing u. if node's parent equals self then its root return.
        private int find(int u) {
    
            // at top.
            if (par[u] == u) return u;
    
            // this does path compression.
            return par[u] = find(par[u]);
        }
    
        public boolean union (int u, int v) {
            
            // get roots
            u = find(u);
            v = find(v);
    
            // same tree
            if (u == v) return false;
    
            // attach v to u
            par[v] = u;
    
            // adjusts proper nodeSq value after successful connection
            adjustSize(u, v);

            // decrements node if successful connection assuming par isn't same
            nodes--;
    
            return true;
        }
    
        // func to properly change size based on the new unionizing of two vals
        public void adjustSize(int firstIndex, int secondIndex) {
            nodeSq = nodeSq - ((child[firstIndex] * child[firstIndex]) + (child[secondIndex] * child[secondIndex])); // subtracts the prior amount of squares from total
    
            child[firstIndex] = child[firstIndex] + child[secondIndex]; // makes size u, size of u plus size of v
    
            nodeSq = nodeSq + (child[firstIndex] * child[firstIndex]); // now adds the new size of u squared to total
        }
    
        // func to print out properly simplified fraction
        public void calcDJ() { 
            long gcd = gcd(nodeSq, nodes); // returns gcd of 2 values to simplify fraction
            long tmpSq = nodeSq / gcd; // holds val of nodeSq / gcd to NOT adjust overall nodeSq val
            long tmpNode = nodes / gcd; // holds val of nodes / gcd to NOT adjust overall nodes val

            System.out.println(tmpSq + "/" + tmpNode); // display resulting simplfied fraction (if needed to simplify)
        }

        public long gcd(long a, long b) { 
            if (b == 0) {  // if b is 0 then
                return a; // return smallest val that can be divided
            }
            return gcd(b, a % b); // else, recurse with val modulo a
        }
        
    }

    
    public static void main (String[] args) {
        // Input of N and M
        // -> N = Number of Nodes -> elements
        // -> M = Number of Lines to Take Input -> actions

        // Scanner Dec
        Scanner take = new Scanner(System.in);

        // elements (n) = # of elements at start of code
        int elements = take.nextInt();

        // actions (m) = # of lines to take input
        int actions = take.nextInt();

        // declare new djset with size of nodes
        djset myComputer = new djset(elements);

        // first Num = 1 or 2 inp
        // firIndex, secIndex = elements to connect
        int firstNum = 0; 
        int firIndex, secIndex = 0;

        // take input for i < actions
        for (int i = 0; i < actions; i++) {

            // take first num int
            firstNum = take.nextInt();

            // if val equals 1, take u & v
            if (firstNum == 1) {
                firIndex = take.nextInt() - 1; // u
                secIndex = take.nextInt() - 1; // v
                myComputer.union(firIndex, secIndex); // add v to u tree
            } else { 
                myComputer.calcDJ(); // solve for vals, and print fraction
            }
        }


    }
}
