import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class PrimMST extends GraphAlgorithm{

	protected int exampleNum;

	public PrimMST(Canvas canvas, int exampleNum){
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
		primMST();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[6];
		pseudoToWrite[0] = "PrimMST(G)";
		pseudoToWrite[1] = "   S = {x}: choose arbitrary starting point";
		pseudoToWrite[2] = "   while S != V";
		pseudoToWrite[3] = "      (u, v): choose edge with lowest c(u, v), v \u2208 S and u \u2208 V - S";
		pseudoToWrite[4] = "      add (u, v) to T";
		pseudoToWrite[5] = "      add u to S";

		int textX = 20;
		int textY = windowHeight / 2;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void primMST(){
		addCurrentFrame();

		ArrayList<Integer> s = new ArrayList<Integer>();
		int random = (int)(Math.random() * 7);
		s.add(random);
		nodes[random].setColor(LIGHT_RED);
		setNextLine(1, LIGHT_RED);
		addCurrentFrame();

		while(s.size() < nodes.length){
			setNextLine(2, LIGHT_BLUE);
			addCurrentFrame();

			int u = -1;
			int v = -1;
			int minCost = Integer.MAX_VALUE;

			boolean dupMinCost = false;
			for(int i : s){
				for(int j = 0; j < adjMatrix[i].length; j++){
					if(!s.contains(j) && adjMatrix[i][j] > 0 && adjMatrix[i][j] <= minCost){
						dupMinCost = false;
						if(adjMatrix[i][j] == minCost){
							dupMinCost = true;
						}
						v = i;
						u = j;
						minCost = adjMatrix[i][j];
					}
				}
			}

			if(dupMinCost){
				text[0] = new Text("//more than one edge with same weight, select random edge", LIGHT_BLUE, new Point(20, 340));
			}

			edgeColors[v][u] = LIGHT_BLUE;
			edgeColors[u][v] = LIGHT_BLUE;
			setNextLine(3, LIGHT_BLUE);
			addCurrentFrame();

			text[0] = null;
			edgeColors[v][u] = LIGHT_RED;
			edgeColors[u][v] = LIGHT_RED;
			setNextLine(4, LIGHT_RED);
			addCurrentFrame();

			s.add(u);
			nodes[u].setColor(LIGHT_RED);
			setNextLine(5, LIGHT_RED);
			addCurrentFrame();
		}
	}
}	
