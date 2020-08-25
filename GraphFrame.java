import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class GraphFrame implements Frame{

	protected int[][] adjMatrix;
	protected GraphNode[] nodes;
	protected Color[][] edgeColors;
	protected Text[] pseudocode;
	protected Text[] text;

	public GraphFrame(int[][] adjMatrix, GraphNode[] nodes, Color[][] edgeColors, Text[] pseudocode, Text[] text){
		this.adjMatrix = adjMatrix;
		this.nodes = nodes;
		this.edgeColors = edgeColors;
		this.pseudocode = pseudocode;
		this.text = text;
	}

	public void draw(Graphics g, Canvas canvas){
		drawEdges(g);
		drawNodes(g);
		drawPseudocode(g);
		drawText(g);
	}

	public void drawEdges(Graphics g){
		g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		g.setColor(Color.black);

		for(int i = 0; i < adjMatrix.length; i++){

			int x1 = nodes[i].getX();
			int y1 = nodes[i].getY();

			for(int j = 0; j < adjMatrix[i].length; j++){

				if(i != j && adjMatrix[i][j] > 0){

					if(edgeColors[i][j] != null){
						g.setColor(edgeColors[i][j]);
					}
					else{
						g.setColor(Color.black);
					}
					
					int x2 = nodes[j].getX();
					int y2 = nodes[j].getY();
					g.drawLine(x1, y1, x2, y2);

					int midX = (x1 + x2) / 2;
					int midY = (y1 + y2) / 2;
					
					if(x1 == x2){
						midX -= 20;
					}
					else{
						midY -= 10;
					}						

					g.drawString(Integer.toString(adjMatrix[i][j]), midX, midY);	
				}
			}
		}
	}

	public void drawNodes(Graphics g){
		int radius = 35;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 24)); 

		for(GraphNode node : nodes){
			int ovalX = node.getX() - radius;
			int ovalY = node.getY() - radius;

			g.setColor(node.getColor());
			g.fillOval(ovalX, ovalY, radius * 2, radius * 2);
			g.setColor(Color.black);
			g.drawOval(ovalX, ovalY, radius * 2, radius * 2);
		
			int numX = node.getX() - 5;
			int numY = node.getY() + 5;
			g.drawString(node.getLabel(), numX, numY);
		}
	}	

	public void drawPseudocode(Graphics g){
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 24));

    	for(int i = 0; i < pseudocode.length; i++){
       		Text t = pseudocode[i];
       		String toWrite = t.getText();

       		FontMetrics metrics = g.getFontMetrics();
       		int stringWidth = metrics.stringWidth(toWrite);

      		if(t.getColor() != null){
       			g.setColor(t.getColor());
       			g.fillRect(t.getX(), t.getY() - 20, stringWidth, 30);
    		}		

       		g.setColor(Color.black);
    		g.drawString(toWrite, t.getX(), t.getY());       		
    	}
    }

    public void drawText(Graphics g){
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
    	for(Text t : text){
    		if(t != null){
    			g.setColor(t.getColor());
    			g.drawString(t.getText(), t.getX(), t.getY());
    		}
    	}
    }
   
}
    