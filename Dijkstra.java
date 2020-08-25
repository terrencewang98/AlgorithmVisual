import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Dijkstra extends GraphAlgorithm{

	protected int[] distances;
	protected GraphNode[] parentsFinal;
	protected ArrayList<Integer> unvisited;
	protected Color[][] tableColors;
	protected int exampleNum;

	public Dijkstra(Canvas canvas, int exampleNum){
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
			nodes[0] = new GraphNode("s", 100, 150);
			nodes[1] = new GraphNode("a", 300, 50);
			nodes[2] = new GraphNode("b", 800, 50);
			nodes[3] = new GraphNode("c", 300, 250);
			nodes[4] = new GraphNode("d", 600, 250);

			adjMatrix = new int[nodes.length][nodes.length];
			for(int i = 0; i < adjMatrix.length; i++){
				for(int j = 0; j < adjMatrix[i].length; j++){
					adjMatrix[i][j] = -1;
				}
			}

			addDirectedEdge(0, 1, 6);
			addDirectedEdge(0, 3, 2);
			addDirectedEdge(1, 2, 2);
			addDirectedEdge(1, 3, 3);
			addDirectedEdge(1, 4, 1);
			addDirectedEdge(2, 3, 4);
			addDirectedEdge(3, 0, 1);
			addDirectedEdge(3, 1, 2);
			addDirectedEdge(3, 4, 4);
			addDirectedEdge(4, 1, 1);
			addDirectedEdge(4, 2, 2);
			addDirectedEdge(4, 3, 2);
		}
		else if(exampleNum == 2){
			nodes = new GraphNode[7];
			nodes[0] = new GraphNode("s", 50, 150);
			nodes[1] = new GraphNode("a", 275, 50);
			nodes[2] = new GraphNode("b", 275, 250);
			nodes[3] = new GraphNode("c", 500, 150);
			nodes[4] = new GraphNode("d", 775, 50);
			nodes[5] = new GraphNode("e", 775, 250);
			nodes[6] = new GraphNode("f", 950, 150);

			adjMatrix = new int[nodes.length][nodes.length];
			for(int i = 0; i < adjMatrix.length; i++){
				for(int j = 0; j < adjMatrix[i].length; j++){
					adjMatrix[i][j] = -1;
				}
			}

			addDirectedEdge(0, 1, 7);
			addDirectedEdge(0, 2, 14);
			addDirectedEdge(0, 3, 9);
			addDirectedEdge(1, 3, 10);
			addDirectedEdge(1, 4, 15);
			addDirectedEdge(2, 5, 9);
			addDirectedEdge(3, 2, 2);
			addDirectedEdge(3, 4, 11);
			addDirectedEdge(3, 5, 16);
			addDirectedEdge(4, 5, 4);
			addDirectedEdge(4, 6, 5);
			addDirectedEdge(5, 6, 2);
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

			addDirectedEdge(0, 1, 10);
			addDirectedEdge(0, 2, 14);
			addDirectedEdge(0, 3, 5);
			addDirectedEdge(0, 4, 15);
			addDirectedEdge(0, 5, 16);
			addDirectedEdge(1, 4, 2);
			addDirectedEdge(2, 5, 6);
			addDirectedEdge(3, 2, 7);
			addDirectedEdge(3, 5, 12);
			addDirectedEdge(3, 7, 14);
			addDirectedEdge(4, 5, 3);
			addDirectedEdge(4, 7, 7);
			addDirectedEdge(4, 9, 20);
			addDirectedEdge(5, 6, 2);	
			addDirectedEdge(5, 8, 5);
			addDirectedEdge(6, 8, 9);
			addDirectedEdge(6, 9, 1);
			addDirectedEdge(7, 9, 7);
			addDirectedEdge(8, 9, 11);
		}
		
		pseudocode = new Text[16];
		frames = new ArrayList<GraphFrame>();
		currentFrame = 0;
		currentLine = 0;

		distances = new int[nodes.length];
		for(int i = 0; i < distances.length; i++){
			distances[i] = -1;
		}
		parentsFinal = new GraphNode[nodes.length];
		tableColors = new Color[4][nodes.length]; //0: vertex 1: visited 2: distance 3: parent		

		initPseudocode();
		dijkstra();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[16];
		pseudoToWrite[0] = "Dijkstra (G = (V, E), s \u2208 V)";
		pseudoToWrite[1] = "   T \u2190 (\u2205, \u2205)";
		pseudoToWrite[2] = "   for each vertex v \u2208 V";
		pseudoToWrite[3] = "      v.visited = false, v.dist = \u221e, v.parent = \u2205, ";
		pseudoToWrite[4] = "   s.dist = 0";
		pseudoToWrite[5] = "";
		pseudoToWrite[6] = "   while(not done)";
		pseudoToWrite[7] = "      v \u2190 unvisited node with smallest dist";
		pseudoToWrite[8] = "      for each unvisited neighbor u of v";
		pseudoToWrite[9] = "         if v.dist + d(v, u) < u.dist";
		pseudoToWrite[10] = "            u.dist = v.dist + d(v, u)";
		pseudoToWrite[11] = "            u.parent = v";
		pseudoToWrite[12] = "      v.visited = true";
		pseudoToWrite[13] = "      add v to T";
		pseudoToWrite[14] = "      add (v, v.parent) to T";
		pseudoToWrite[15] = "   return T";

		int textX = 20;
		int textY = windowHeight / 2 - 60;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void dijkstra(){
		addCurrentFrame();

		setNextLine(2, LIGHT_BLUE);
		addCurrentFrame();

		unvisited = new ArrayList<Integer>();
		for(int i = 0; i < nodes.length; i++){
			distances[i] = Integer.MAX_VALUE;
			unvisited.add(i);
		}

		setNextLine(3, LIGHT_BLUE);
		addCurrentFrame();

		distances[0] = 0;

		tableColors[2][0] = LIGHT_RED;
		setNextLine(4, LIGHT_RED);
		addCurrentFrame();

		tableColors[2][0] = null;

		while(!unvisited.isEmpty()){

			setNextLine(6, LIGHT_BLUE);
			addCurrentFrame();

			int v = unvisited.get(0);
			for(int i = 1; i < unvisited.size(); i++){
				if(distances[unvisited.get(i)] < distances[v]){
					v = unvisited.get(i);
				}
			}

			tableColors[0][v] = LIGHT_BLUE;
			nodes[v].setColor(LIGHT_BLUE);
			setNextLine(7, LIGHT_BLUE);
			addCurrentFrame();

			for(int w = 0; w < adjMatrix[v].length; w++){

				if(adjMatrix[v][w] != -1 && unvisited.contains(w)){

					tableColors[0][w] = LIGHT_BLUE;
					nodes[w].setColor(LIGHT_BLUE);
					setNextLine(8, LIGHT_BLUE);
					addCurrentFrame();

					if(distances[v] + adjMatrix[v][w] < distances[w]){

						tableColors[0][w] = LIGHT_GREEN;
						nodes[w].setColor(LIGHT_GREEN);
						setNextLine(9, LIGHT_GREEN);
						addCurrentFrame();

						distances[w] = distances[v] + adjMatrix[v][w];

						tableColors[2][w] = LIGHT_GREEN;
						setNextLine(10, LIGHT_GREEN);
						addCurrentFrame();

						tableColors[2][w] = null;

						nodes[w].setParent(nodes[v]);

						tableColors[3][w] = LIGHT_GREEN;
						setNextLine(11, LIGHT_GREEN);
						addCurrentFrame();

						tableColors[3][w] = null;
					}

					tableColors[0][w] = null;
					nodes[w].resetColor();
				}
			}

			int removeIndex = unvisited.indexOf(v);
			unvisited.remove(removeIndex);

			tableColors[1][v] = LIGHT_RED;
			setNextLine(12, LIGHT_RED);
			addCurrentFrame();

			tableColors[0][v] = LIGHT_RED;
			tableColors[2][v] = LIGHT_RED;
			nodes[v].setColor(LIGHT_RED);
			setNextLine(13, LIGHT_RED);
			addCurrentFrame();

			if(nodes[v].getParent() != null){
				parentsFinal[v] = nodes[v].getParent();
			}
			tableColors[3][v] = LIGHT_RED;
			setNextLine(14, LIGHT_RED);
			addCurrentFrame();
		}
		setNextLine(15, LIGHT_RED);
		addCurrentFrame();
	}

	@Override
	public void addCurrentFrame(){
		GraphNode[] nodesClone = new GraphNode[nodes.length];
		for(int i = 0; i < nodes.length; i++){
			if(nodes[i] != null){
				nodesClone[i] = nodes[i].clone();
			}		
		}

		Text[] pseudocodeClone = new Text[pseudocode.length];
		for(int i = 0; i < pseudocode.length; i++){
			if(pseudocode[i] != null){
				pseudocodeClone[i] = pseudocode[i].clone();
			}		
		}

		Color[][] tableColorsClone = new Color[4][5];
		for(int i = 0; i < tableColors.length; i++){
			tableColorsClone[i] = tableColors[i].clone();
		}

		ArrayList<Integer> unvisitedClone = unvisited == null ? null : new ArrayList<Integer>(unvisited);

		DijkstraFrame currentFrame = new DijkstraFrame(adjMatrix.clone(), nodesClone, pseudocodeClone, 
			unvisitedClone, distances.clone(), parentsFinal.clone(), tableColorsClone);
		frames.add(currentFrame);
	}
}	
