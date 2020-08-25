import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class BellmanFord extends GraphAlgorithm{

	protected int[][] distances;
	protected Color[][] tableColors;
	protected int exampleNum;

	public BellmanFord(Canvas canvas, int exampleNum){
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

			addDirectedEdge(0, 1, -1);
			addDirectedEdge(0, 3, 4);
			addDirectedEdge(1, 3, 3);
			addDirectedEdge(2, 4, -3);
			addDirectedEdge(3, 2, 3);
			addDirectedEdge(3, 4, 1);
			addDirectedEdge(4, 1, 1);
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

			addDirectedEdge(0, 1, 7);
			addDirectedEdge(0, 2, 14);
			addDirectedEdge(0, 3, 9);
			addDirectedEdge(1, 3, 10);
			addDirectedEdge(1, 4, 15);
			addDirectedEdge(2, 5, -9);
			addDirectedEdge(3, 2, 2);
			addDirectedEdge(3, 4, -11);
			addDirectedEdge(3, 5, 16);
			addDirectedEdge(4, 5, -4);
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

			addDirectedEdge(0, 1, 10);
			addDirectedEdge(0, 2, 14);
			addDirectedEdge(0, 3, -5);
			addDirectedEdge(0, 4, 15);
			addDirectedEdge(0, 5, 16);
			addDirectedEdge(1, 4, 2);
			addDirectedEdge(2, 5, 6);
			addDirectedEdge(3, 2, 7);
			addDirectedEdge(3, 5, 12);
			addDirectedEdge(3, 7, 14);
			addDirectedEdge(4, 5, -6);
			addDirectedEdge(4, 7, 7);
			addDirectedEdge(4, 9, 20);
			addDirectedEdge(5, 6, -2);	
			addDirectedEdge(5, 8, 5);
			addDirectedEdge(6, 8, 9);
			addDirectedEdge(6, 9, 1);
			addDirectedEdge(7, 9, 7);
			addDirectedEdge(8, 9, -11);
		}
		
		pseudocode = new Text[11];
		frames = new ArrayList<GraphFrame>();
		currentFrame = 0;
		currentLine = 0;

		distances = new int[nodes.length][nodes.length];
		for(int i = 0; i < distances.length; i++){
			for(int j = 0; j < distances[i].length; j++){
				distances[i][j] = Integer.MIN_VALUE;
			}
		}
		edgeColors = new Color[nodes.length][nodes.length];
		tableColors = new Color[nodes.length][nodes.length]; //0: vertex 1: distance 2: parent		

		initPseudocode();
		bellmanFord();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[11];
		pseudoToWrite[0] = "BellmanFord (G = (V, E), s \u2208 V)";
		pseudoToWrite[1] = "   W[n][n] = new array";
		pseudoToWrite[2] = "   set W[0][s] = 0, w[0][j] = \u221e for j \u2260 s";
		pseudoToWrite[3] = "";
		pseudoToWrite[4] = "   for i = 1 to n:";
		pseudoToWrite[5] = "      for each j \u2208 v";
		pseudoToWrite[6] = "         set W[i][j] = W[i - 1][j]"; 
		pseudoToWrite[7] = "         for each edge (u, j) \u2208 E";
		pseudoToWrite[8] = "            if c(u, j) + W[i - 1][u] < W[i][j]:";
		pseudoToWrite[9] = "               set W[i][j] = c(u, j) + W[i - 1][u]";         
		pseudoToWrite[10] = "   return W[n - 1]";  

		int textX = 20;
		int textY = windowHeight / 2 - 60;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void bellmanFord(){
		addCurrentFrame();

		setNextLine(1, LIGHT_BLUE);
		addCurrentFrame();

		for(int i = 0; i < nodes.length; i++){
			distances[0][i] = Integer.MAX_VALUE;
		}
		distances[0][0] = 0;
		setNextLine(2, LIGHT_BLUE);
		addCurrentFrame();

		tableColors[0][0] = null;
		

		for(int i = 1; i < distances.length; i++){

			setNextLine(4, LIGHT_BLUE);
			addCurrentFrame();

			for(int j = 0; j < adjMatrix.length; j++){

				setNextLine(5, LIGHT_BLUE);
				nodes[j].setColor(LIGHT_BLUE);
				addCurrentFrame();

				distances[i][j] = distances[i - 1][j];
				tableColors[i][j] = LIGHT_BLUE;
				setNextLine(6, LIGHT_BLUE);
				addCurrentFrame();

				tableColors[i][j] = null;

				for(int u = 0; u < adjMatrix[j].length; u++){

					Color uColor = nodes[u].getColor();

					if(adjMatrix[u][j] != 0){

						nodes[u].setColor(LIGHT_BLUE);
						edgeColors[u][j] = LIGHT_BLUE;
						setNextLine(7, LIGHT_BLUE);
						addCurrentFrame();

						edgeColors[u][j] = LIGHT_GREEN;
						tableColors[i - 1][u] = LIGHT_GREEN;
						tableColors[i][j] = LIGHT_GREEN;
						setNextLine(8, LIGHT_GREEN);
						addCurrentFrame();

						if(distances[i - 1][u] != Integer.MAX_VALUE && adjMatrix[u][j] + distances[i - 1][u] < distances[i][j]){

							distances[i][j] = adjMatrix[u][j] + distances[i - 1][u];
							nodes[j].setParent(nodes[u]);

							tableColors[i - 1][u] = null;
							setNextLine(9, LIGHT_GREEN);
							addCurrentFrame();
						}
						else{
							tableColors[i - 1][u] = null;
						}

						nodes[u].setColor(uColor);
						edgeColors[u][j] = null;
						tableColors[i][j] = null;
					}
				}

				nodes[j].resetColor();

				if(i == distances.length - 1){
					nodes[j].setColor(LIGHT_RED);
					tableColors[i][j] = LIGHT_RED;

					if(nodes[j].getParent() != null){
						int parentIndex = -1;
						for(int k = 0; k < nodes.length; k++){
							if(nodes[j].getParent().equals(nodes[k])){
								parentIndex = k;
								break;
							}
						}
						edgeColors[parentIndex][j] = LIGHT_RED;
					}
				}
			}
		}

		setNextLine(10, LIGHT_RED);
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

		Color[][] edgeColorsClone = new Color[edgeColors.length][edgeColors[0].length];
		for(int i = 0; i < edgeColorsClone.length; i++){
			edgeColorsClone[i] = edgeColors[i].clone();
		}

		Text[] pseudocodeClone = new Text[pseudocode.length];
		for(int i = 0; i < pseudocode.length; i++){
			if(pseudocode[i] != null){
				pseudocodeClone[i] = pseudocode[i].clone();
			}		
		}

		int[][] distancesClone = new int[distances.length][distances[0].length];
		for(int i = 0; i < distances.length; i++){
			distancesClone[i] = distances[i].clone();
		}

		Color[][] tableColorsClone = new Color[tableColors.length][tableColors[0].length];
		for(int i = 0; i < tableColors.length; i++){
			tableColorsClone[i] = tableColors[i].clone();
		}

		BellmanFordFrame currentFrame = new BellmanFordFrame(adjMatrix, nodesClone, edgeColorsClone, pseudocodeClone, 
			distancesClone, tableColorsClone);
		frames.add(currentFrame);
	}
}	
