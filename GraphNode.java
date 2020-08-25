import java.awt.Color;

public class GraphNode{
	
	private String label;
	private int x; 
	private int y;
	private Color color;
	private boolean visited;
	private GraphNode parent;

	public GraphNode(String label, int x, int y){
		this.label = label;
		this.x = x;
		this.y = y;
		color = new Color(238, 238, 238);
		visited = false;
		parent = null;
	}

	public String getLabel(){
		return label;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public Color getColor(){
		return color;
	}

	public void resetColor(){
		color = new Color(238, 238, 238);
	}

	public void setColor(Color color){
		this.color = color;
	}

	public void setVisited(boolean b){
		visited = b;
	}

	public void setParent(GraphNode node){
		parent = node;
	}

	public GraphNode getParent(){
		return parent;
	}

	@Override
	public GraphNode clone(){
		GraphNode clone = new GraphNode(label, x, y);
		clone.setColor(color);
		clone.setVisited(visited);
		clone.setParent(parent);
		return clone;
	}
}