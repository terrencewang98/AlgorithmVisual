import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class GraphAlgorithm implements IAlgorithm{

	protected final Color LIGHT_RED = new Color(255, 102, 102);
	protected final Color LIGHT_GREEN = new Color(102, 255, 102);
	protected final Color LIGHT_BLUE = new Color(51, 204, 255);

	protected int width;
	protected int windowHeight; 
	protected int arrayHeight; 
	protected int topMargin;

	protected int[][] adjMatrix;
	protected GraphNode[] nodes;
	protected Color[][] edgeColors;
	protected Text[] pseudocode;
	protected Text[] text;
	protected ArrayList<GraphFrame> frames;
	protected int currentLine;
	protected int currentFrame;
	
	public abstract void init();
	public abstract void initPseudocode();

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

		Text[] textClone = new Text[text.length];
		for(int i = 0; i < text.length; i++){
			if(text[i] != null){
				textClone[i] = text[i].clone();
			}		
		}

		Color[][] edgeColorsClone = new Color[edgeColors.length][edgeColors.length];
		for(int i = 0; i < edgeColorsClone.length; i++){
			edgeColorsClone[i] = edgeColors[i].clone();
		}

		GraphFrame currentFrame = new GraphFrame(adjMatrix.clone(), nodesClone, edgeColorsClone, pseudocodeClone, textClone);
		frames.add(currentFrame);
	}

	public GraphFrame getCurrentFrame(){
		return frames.get(currentFrame);
	}

	public GraphFrame next(){
		if(currentFrame < frames.size() - 1){
    		currentFrame++;
    	}
    	return frames.get(currentFrame);
	}

	public GraphFrame prev(){
		if(currentFrame > 0){
			currentFrame--;
		}
		return frames.get(currentFrame);
	}

	public void addDirectedEdge(int n1, int n2, int cost){
		adjMatrix[n1][n2] = cost;
	}

	public void addUndirectedEdge(int n1, int n2, int cost){
		adjMatrix[n1][n2] = cost;
		adjMatrix[n2][n1] = cost;
	}

	public void setNextLine(int nextLine, Color color){
		pseudocode[currentLine].setColor(null);
		currentLine = nextLine;
		pseudocode[currentLine].setColor(color);
	}
		

}