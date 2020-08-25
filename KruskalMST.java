import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class KruskalMST extends GraphAlgorithm{

	protected int exampleNum;
	
	public KruskalMST(Canvas canvas, int exampleNum){
		this.width = canvas.getWindowWidth();
		this.windowHeight = canvas.getWindowHeight();
		this.arrayHeight = canvas.getArrayHeight(); 
		this.topMargin = canvas.getTopMargin();
		this.exampleNum = exampleNum + 1;
		init();
	}

	public void init(){
		if(exampleNum == 1){
			nodes = new GraphNode[5];
			nodes[0] = new GraphNode("a", 100, 150);
			nodes[1] = new GraphNode("b", 400, 50);
			nodes[2] = new GraphNode("c", 400, 250);
			nodes[3] = new GraphNode("d", 800, 50);
			nodes[4] = new GraphNode("e", 800, 250);

			adjMatrix = new int[nodes.length][nodes.length];
			for(int i = 0; i < adjMatrix.length; i++){
				for(int j = 0; j < adjMatrix[i].length; j++){
					adjMatrix[i][j] = -1;
				}
			}

			addUndirectedEdge(0, 1, 3);
			addUndirectedEdge(0, 2, 3);
			addUndirectedEdge(1, 2, 6);
			addUndirectedEdge(1, 3, 10);
			addUndirectedEdge(1, 4, 2);
			addUndirectedEdge(2, 4, 1);
			addUndirectedEdge(3, 4, 4);
		}
		else if(exampleNum == 2){
			nodes = new GraphNode[7];
			nodes[0] = new GraphNode("a", 100, 150);
			nodes[1] = new GraphNode("b", 300, 50);
			nodes[2] = new GraphNode("c", 300, 250);
			nodes[3] = new GraphNode("d", 500, 150);
			nodes[4] = new GraphNode("e", 700, 50);
			nodes[5] = new GraphNode("f", 700, 250);
			nodes[6] = new GraphNode("g", 900, 150);

			adjMatrix = new int[nodes.length][nodes.length];
			for(int i = 0; i < adjMatrix.length; i++){
				for(int j = 0; j < adjMatrix[i].length; j++){
					adjMatrix[i][j] = -1;
				}
			}

			addUndirectedEdge(0, 1, 6);
			addUndirectedEdge(0, 2, 8);
			addUndirectedEdge(1, 2, 3);
			addUndirectedEdge(1, 3, 1);
			addUndirectedEdge(2, 3, 4);
			addUndirectedEdge(2, 5, 10);
			addUndirectedEdge(3, 4, 9);
			addUndirectedEdge(3, 5, 11);
			addUndirectedEdge(4, 5, 2);
			addUndirectedEdge(4, 6, 7);
			addUndirectedEdge(5, 6, 5);		
		}
		else{
			nodes = new GraphNode[10];
			nodes[0] = new GraphNode("s", 50, 150);
			nodes[1] = new GraphNode("a", 200, 50);
			nodes[2] = new GraphNode("b", 200, 250);
			nodes[3] = new GraphNode("c", 350, 150);
			nodes[4] = new GraphNode("d", 500, 50);
			nodes[5] = new GraphNode("e", 500, 250);
			nodes[6] = new GraphNode("f", 650, 150);
			nodes[7] = new GraphNode("g", 800, 50);
			nodes[8] = new GraphNode("h", 800, 250);
			nodes[9] = new GraphNode("i", 950, 150);

			adjMatrix = new int[nodes.length][nodes.length];
			for(int i = 0; i < adjMatrix.length; i++){
				for(int j = 0; j < adjMatrix[i].length; j++){
					adjMatrix[i][j] = -1;
				}
			}

			addUndirectedEdge(0, 1, 4);
			addUndirectedEdge(0, 2, 8);
			addUndirectedEdge(1, 2, 11);
			addUndirectedEdge(1, 4, 8);
			addUndirectedEdge(2, 3, 7);
			addUndirectedEdge(2, 5, 1);
			addUndirectedEdge(3, 4, 2);
			addUndirectedEdge(3, 5, 6);
			addUndirectedEdge(4, 5, 3);
			addUndirectedEdge(4, 6, 4);
			addUndirectedEdge(4, 7, 3);
			addUndirectedEdge(5, 8, 2);
			addUndirectedEdge(6, 7, 4);
			addUndirectedEdge(6, 8, 5);
			addUndirectedEdge(7, 8, 14);
			addUndirectedEdge(7, 9, 9);
			addUndirectedEdge(8, 9, 10);
		}

		pseudocode = new Text[6];
		text = new Text[1];
		frames = new ArrayList<GraphFrame>();
		currentFrame = 0;
		currentLine = 0;

		edgeColors = new Color[nodes.length][nodes.length];

		initPseudocode();
		kruskalMST();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[6];
		pseudoToWrite[0] = "KruskalMST(G)";
		pseudoToWrite[1] = "   T = {}";
		pseudoToWrite[2] = "   E: edges sorted in nondecreasing order of cost";
		pseudoToWrite[3] = "   for edge(u, v) in E";
		pseudoToWrite[4] = "      if adding (u, v) to T doesn't create a cycle";
		pseudoToWrite[5] = "          add (u, v) to T";

		int textX = 20;
		int textY = windowHeight / 2 - 60;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void kruskalMST(){
		addCurrentFrame();

		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int i = 0; i < adjMatrix.length; i++){
			for(int j = 0; j < adjMatrix[i].length; j++){
				if(adjMatrix[i][j] > 0){
					Edge toAdd = new Edge(i, j, adjMatrix[i][j]);

					int toAddPos = edges.size();
					for(int k = 0; k < edges.size(); k++){
						if(edges.get(k).equals(toAdd)){
							toAddPos = -1;
							break;
						}
						else if(edges.get(k).weight > toAdd.weight){
							toAddPos = k;
							break;
						}
					}

					if(toAddPos != -1){
						edges.add(toAddPos, toAdd);
					}	
				}
			}
		}

		int[] parents = new int[adjMatrix.length];	
		for(int i = 0; i < parents.length; i++){
			parents[i] = -1; 
		}

		for(Edge e : edges){

			nodes[e.src].setColor(LIGHT_BLUE);
			nodes[e.dest].setColor(LIGHT_BLUE);
			edgeColors[e.src][e.dest] = LIGHT_BLUE;
			edgeColors[e.dest][e.src] = LIGHT_BLUE;
			setNextLine(3, LIGHT_BLUE);
			addCurrentFrame();

			if(find(parents, e.src) != find(parents, e.dest)){
				setNextLine(4, LIGHT_BLUE);
				addCurrentFrame();

				union(parents, e.src, e.dest);

				nodes[e.src].setColor(LIGHT_RED);
				nodes[e.dest].setColor(LIGHT_RED);
				edgeColors[e.src][e.dest] = LIGHT_RED;
				edgeColors[e.dest][e.src] = LIGHT_RED;
				setNextLine(5, LIGHT_RED);
				addCurrentFrame();
			}
			else{
				nodes[e.src].setColor(LIGHT_RED);
				nodes[e.dest].setColor(LIGHT_RED);
				edgeColors[e.src][e.dest] = null;
				edgeColors[e.dest][e.src] = null;
			}
		}

		setNextLine(0, null);
		addCurrentFrame();
	}

	private int find(int[] parents, int n){
		if(parents[n] == -1){
			return n;
		}
		else{
			return find(parents, parents[n]);
		}
	}

	private void union(int[] parents, int n1, int n2){
		int head1 = find(parents, n1);
		int head2 = find(parents, n2);
		parents[head1] = head2;
	}
}	

class Edge{
	int src;
	int dest;
	int weight;

	public Edge(int n1, int n2, int weight){
		src = Math.min(n1, n2);
		dest = Math.max(n1, n2);
		this.weight = weight;
	}

	public boolean equals(Edge e){
		return src == e.src && dest == e.dest;
	}

}
