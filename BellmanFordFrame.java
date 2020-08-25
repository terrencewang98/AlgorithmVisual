import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

public class BellmanFordFrame extends GraphFrame{

	int[][] distances;
	Color[][] tableColors;

	public BellmanFordFrame(int[][] adjMatrix, GraphNode[] nodes, Color[][] edgeColors, Text[] pseudocode, int[][] distances, Color[][] tableColors){
		super(adjMatrix, nodes, edgeColors, pseudocode, new Text[1]);
		this.distances = distances;
		this.tableColors = tableColors;
	}

	public void draw(Graphics g, Canvas canvas){

		int width = canvas.getWindowWidth();
		int height = canvas.getWindowHeight();

		drawPseudocode(g);
		drawText(g);
		drawNodes(g);
		drawEdges(g);
		drawTable(g, width, height);
	}

	@Override
	public void drawEdges(Graphics g){
		g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
		g.setColor(Color.black);

		double radius = 35;

		for(int i = 0; i < adjMatrix.length; i++){

			int x1 = nodes[i].getX();
			int y1 = nodes[i].getY();

			for(int j = 0; j < adjMatrix[i].length; j++){
				if(i != j && adjMatrix[i][j] != 0){
					int x2 = nodes[j].getX();
					int y2 = nodes[j].getY();
					
					double slope1 = Math.atan(((double)y2 - (double)y1) / ((double)x2 - (double)x1));
					double slope2 = Math.atan(((double)x1 - (double)x2) / ((double)y1 - (double)y2));
					
					slope1 += Math.toRadians(10);
					slope2 += Math.toRadians(10);

					int x1Offset = (int)(Math.abs(Math.cos(slope1) * radius));
					int y1Offset = (int)(Math.abs(Math.sin(slope1) * radius));
					int x2Offset = (int)(Math.abs(Math.sin(slope2) * radius));
					int y2Offset = (int)(Math.abs(Math.cos(slope2) * radius));

					if(x1 == x2){
						if(y1 < y2){ 
							x1Offset = -5;
							x2Offset = -5;
						}
						else{
							x1Offset = 5;
							x2Offset = 5;
						}
					}

					if(y1 == y2){
						if(x1 < x2){
							y1Offset = 5;
							y2Offset = -5;
						}
						else{
							y1Offset = -5;
							y2Offset = 5;
						}
					}

					int startX = x1 <= x2 ? x1 + x1Offset : x1 - x1Offset;
					int startY = y2 >= y1 ? y1 + y1Offset : y1 - y1Offset;
					int endX = x1 < x2 ? x2 - x2Offset : x2 + x2Offset;
					int endY = y2 >= y1 ? y2 - y2Offset : y2 + y2Offset;

					Color edgeColor = edgeColors[i][j] == null ? Color.black : edgeColors[i][j];
					drawArrowLine(g, startX, startY, endX, endY, 10, 5, edgeColor);					

					int midX = (startX + endX) / 2;
					int midY = (startY + endY) / 2;
					
					if(x1 == x2){
						if(y1 < y2){
							midX -= 15;
						}
						else{
							midX += 15;
						}
					}
					else if(y1 < y2){
						if(x1 < x2){
							midY += 15;
						}
						else{
							midY -= 15;
						}
					}
					else
					{
						if(x1 < x2){
							midY += 20;
						}
						else{
							midY -= 10;
						}						
					}

					g.drawString(Integer.toString(adjMatrix[i][j]), midX, midY);	
				}
			}
		}
	}

	public void drawTable(Graphics g, int width, int height){
		FontMetrics metrics = g.getFontMetrics();
		g.setFont(new Font("TimesRoman", Font.PLAIN, 24)); 
		g.setColor(Color.black);

		int tableX = width / 2;
		int tableY = height / 2 - 70;
		int tableWidth = 450;
		int tableHeight = 450;
		int cellWidth = tableWidth / (distances.length + 1);
		int cellHeight = tableHeight / nodes.length;

		g.drawRect(tableX, tableY, tableWidth, tableHeight);

		for(int i = 1; i < distances.length + 1; i++){
			g.drawLine(tableX + cellWidth * i, tableY, tableX + cellWidth * i, tableY + tableHeight);
		}
		for(int i = 1; i < nodes.length; i++){
			g.drawLine(tableX, tableY + cellHeight * i, tableX + tableWidth, tableY + cellHeight * i);
		}

		int labelX = tableX + cellWidth + cellWidth / 2 - 7;
		int labelY = tableY - 5;
		for(int i = 0; i < distances.length; i ++){	
			g.drawString(Integer.toString(i), labelX, labelY);
			labelX += cellWidth;
		}
		
		//vertex column
		int colorX = tableX + 1;
		int colorY = tableY + 1;
		int cellX = tableX + cellWidth / 2 - 5;
		int cellY = tableY + cellHeight / 2 + 5;

		for(int i = 0; i < nodes.length; i++){
			g.setColor(nodes[i].getColor());
			g.fillRect(colorX, colorY, cellWidth - 2, cellHeight - 2);
			g.setColor(Color.black);
			g.drawString(nodes[i].getLabel(), cellX, cellY);
			colorY += cellHeight;
			cellY += cellHeight;
		}

		//distances
		for(int i = 0 ; i < tableColors.length; i++){
			colorX += cellWidth;
			colorY = tableY + 1;
			cellX += cellWidth;
			cellY = tableY + cellHeight / 2 + 5;

			for(int j = 0; j < tableColors[i].length; j++){
				if(tableColors[i][j] != null){
					g.setColor(tableColors[i][j]);
					if(i == tableColors.length - 1){
						g.fillRect(colorX, colorY, tableX + tableWidth - colorX, cellHeight - 2);
					}
					else{
						g.fillRect(colorX, colorY, cellWidth - 2, cellHeight - 2);
					}
					
				}
				String toWrite;
				int offset = 0;

				if(distances[i][j] == Integer.MIN_VALUE){
					toWrite = "";
				}
				else if(distances[i][j] == Integer.MAX_VALUE){
					toWrite = "\u221e";
					offset = 5;
				}
				else{
					toWrite = Integer.toString(distances[i][j]);
					if(distances[i][j] > 9){
						offset = 5;
					}
				}

				g.setColor(Color.black);
				g.drawString(toWrite, cellX - offset, cellY);
				colorY += cellHeight;
				cellY += cellHeight;
			}
		}
	}

	//from https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
	public void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h, Color color) {
	    int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g.setColor(color);
	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}
}