// Colin Kirby
// 3/28/2024
// codes.java
// This code utilizes the Network Algorithm, Dinics, to simulate a network of Drugs to Abbreviations (Codes), and find if
// based on the Drugs & Codes provided in the Inp, if it is possible to match each code to a corresponding drug. Then display
// these codes so that each Drug only has one corresponding Code, furthermore, the codes should be in the first lexicographicl
// order so that each prior requirement is satisfied.

// ALL TASK (#1, #2, #3) COMPLETED !!!

// Requirements :

// a) determine if its possible to make assignment.

// b) if its possible, determine valid matching.

// c) if possible, find first lexicographical mapping that is valid.


// Psuedo : 

// a ) Take in Inp.

// b ) From these Drugs & Codes, create Edges between if Code if Substring of Drug.

// c ) After assembling network, run "flow()" on Dinics Obj to obtain Max Flow from Network. (Part #1)

// d ) If "no", then nothing.

// e ) If "yes", then iterate and remove all edges from drugs.

// f ) ( Prior to creating network done in Part b, sort the drugs lexicographically as to satisfy Part 3) (Part #3)

// g ) Re-add edges (Set capacity of edge to 1) if cur code is substring of cur drug.

// h ) Run "flow()" on cur Dinic Obj. If flow is equal to # drugs. Move onto next drug.

// i ) Else continue to next code until one works.

// j ) Iterate to next drug and repeat process. (Part #2)


import java.util.*;
import java.io.*;


public class codes {
    
    public static void main (String [] args) { 

        FastScanner inp = new FastScanner(); // Declare Scanner.

        int numDrugs = inp.nextInt(); // Take in # of drugs. (n)

        int numCodes = inp.nextInt(); // Take in # of potential codes. (m)

        String [] myDrugs = new String [numDrugs]; // Declare Arr to store drugs.

        String [] myCodes = new String [numCodes]; // Declare Arr to store codes.

        for (int i = 0; i < numDrugs; i++) {

            myDrugs[i] = inp.next(); // Take in inp for myDrugs.
            
        }

        for (int i = 0; i < numCodes; i++) {

            myCodes[i] = inp.next(); // Take in inp for myCodes.
        }

        Arrays.sort(myCodes); // Sort Codes lexicographically.

        Dinic organizeDrugs = new Dinic(numDrugs + numCodes); // Declare Dinic Obj of Size # of Drugs + # of Codes.

        for (int drug = 0; drug < numDrugs; drug++) { // For Drug.

            organizeDrugs.add(numDrugs + numCodes, drug, 1, 0); // Add edge from Source to Drug.

            for (int code = 0; code < numCodes; code++) { // For Code.

                if (myDrugs[drug].indexOf(myCodes[code]) == -1) { // If Code is NOT Substring of Drug.

                    continue;
                
                }

                organizeDrugs.add(drug, numDrugs + code, 1, 0);// Add edge from Drug to Substring.

            }
        }

        for (int i = 0; i < numCodes; i++) { // For Code.

            organizeDrugs.add(numDrugs + i, numDrugs + numCodes + 1, 1, 0); // Add edge from Code to Sink.

        }

        int res = organizeDrugs.flow(); // Obtain Max Flow of Network.

        if (res == numDrugs) { // If Max Flow == # of Drugs.
            System.out.println("yes");

            for (int drug = 0; drug < numDrugs; drug++) { // For Drug.

                for (Edge outgoing : organizeDrugs.adj[drug]) { // For Edge leaving Drug. 

                    outgoing.cap = 0; // Cap = 0;
                    //System.out.println("Drug: " + myDrugs[drug] + ", V1 : " + outgoing.v1 + ", V2 : " + outgoing.v2 + ", Cap : " + outgoing.cap + ", Flow : " + outgoing.flow);
                }
    
                for (int code = 0; code < numCodes; code++) { // For Code.
    
                    if(myDrugs[drug].indexOf(myCodes[code]) == -1) continue; // If Code is NOT substring of cur Drug, continue.
    
                    organizeDrugs.add(drug, numDrugs + code, 1, 0); // Add Edge from cur Drug to cur Code.

                    //organizeDrugs.adj[drug].get(code).cap = 1; // Effectively "add" Edge back to network.
    
                    if (organizeDrugs.flow() == numDrugs) { // If Max flow is still equal to # Drugs
    
                        System.out.println(myCodes[code]); // Display cur Code for cur Drug.

                        break; // Continue to Next Drug, Only 1 Edge per Drug.
    
                    } else {
    
                        organizeDrugs.adj[drug].get(code).cap = 0; // Keep Capacity At Zero, Effectively Removing Edge.
    
                    }
                }
            }
    
        } else {
            System.out.println("no");
        }

    
    }
}


// FastScanner Class Provided by Guha.

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

// An edge connects v1 to v2 with a capacity of cap, flow of flow.
class Edge {
	int v1, v2, cap, flow;
	Edge rev;
	Edge(int V1, int V2, int Cap, int Flow) {
		v1 = V1;
		v2 = V2;
		cap = Cap;
		flow = Flow;
	}
}

// Dinic Class Provided by Guha.

class Dinic {

	// Queue for the top level BFS.
	public ArrayDeque<Integer> q;

	// Stores the graph.
	public ArrayList<Edge>[] adj;
	public int n;

	// s = source, t = sink
	public int s;
	public int t;


	// For BFS.
	public boolean[] blocked;
	public int[] dist;

	final public static int oo = (int)1E9;

	// Constructor.
	public Dinic (int N) {

		// s is the source, t is the sink, add these as last two nodes.
		n = N; s = n++; t = n++;

		// Everything else is empty.
		blocked = new boolean[n];
		dist = new int[n];
		q = new ArrayDeque<Integer>();
		adj = new ArrayList[n];
		for(int i = 0; i < n; ++i)
			adj[i] = new ArrayList<Edge>();
	}

	// Just adds an edge and ALSO adds it going backwards.
	public void add(int v1, int v2, int cap, int flow) {
		Edge e = new Edge(v1, v2, cap, flow);
		Edge rev = new Edge(v2, v1, 0, 0);
		adj[v1].add(rev.rev = e);
		adj[v2].add(e.rev = rev);
	}

	// Runs other level BFS.
	public boolean bfs() {

		// Set up BFS
		q.clear();
		Arrays.fill(dist, -1);
		dist[t] = 0;
		q.add(t);

		// Go backwards from sink looking for source.
		// We just care to mark distances left to the sink.
		while(!q.isEmpty()) {
			int node = q.poll();
			if(node == s)
				return true;
			for(Edge e : adj[node]) {
				if(e.rev.cap > e.rev.flow && dist[e.v2] == -1) {
					dist[e.v2] = dist[node] + 1;
					q.add(e.v2);
				}
			}
		}

		// Augmenting paths exist iff we made it back to the source.
		return dist[s] != -1;
	}

	// Runs inner DFS in Dinic's, from node pos with a flow of min.
	public int dfs(int pos, int min) {

		// Made it to the sink, we're good, return this as our max flow for the augmenting path.
		if(pos == t)
			return min;
		int flow = 0;

		// Try each edge from here.
		for(Edge e : adj[pos]) {
			int cur = 0;

			// If our destination isn't blocked and it's 1 closer to the sink and there's flow, we
			// can go this way.
			if(!blocked[e.v2] && dist[e.v2] == dist[pos]-1 && e.cap - e.flow > 0) {

				// Recursively run dfs from here - limiting flow based on current and what's left on this edge.
				cur = dfs(e.v2, Math.min(min-flow, e.cap - e.flow));

				// Add the flow through this edge and subtract it from the reverse flow.
				e.flow += cur;
				e.rev.flow = -e.flow;

				// Add to the total flow.
				flow += cur;
			}

			// No more can go through, we're good.
			if(flow == min)
				return flow;
		}

		// mark if this node is now blocked.
		blocked[pos] = flow != min;

		// This is the flow
		return flow;
	}

	public int flow() {
		clear();
		int ret = 0;

		// Run a top level BFS.
		while(bfs()) {

			// Reset this.
			Arrays.fill(blocked, false);

			// Run multiple DFS's until there is no flow left to push through.
			ret += dfs(s, oo);
		}
		return ret;
	}

	// Just resets flow through all edges to be 0.
	public void clear() {
		for(ArrayList<Edge> edges : adj)
			for(Edge e : edges)
				e.flow = 0;
	}
}