import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Font;
import java.awt.Point;
import java.awt.FontMetrics;

public class SortFrame implements Frame{

	protected int[] arr;
	protected Color[] arrColors;
	protected Shape[] shapes;
	protected Color[] shapeColors;
	protected Text[] text;
	protected Text[] labels;

	public SortFrame(int[] arr, Color[] arrColors, Shape[] shapes, Color[] shapeColors, Text[] text, Text[] labels){
		this.arr = arr;
		this.arrColors = arrColors;
		this.shapes = shapes;
		this.shapeColors = shapeColors;
		this.text = text;
		this.labels = labels;
	}

	public Color[] getColors(){
		return arrColors;
	}

	public int[] getData(){
		return arr;
	}

	public Shape[] getShapes(){
		return shapes;
	}

	public void draw(Graphics g, Canvas canvas){
		int width = canvas.getWindowWidth();
		int height = canvas.getWindowHeight();
		int arrayHeight = canvas.getArrayHeight();
		int topMargin = canvas.getTopMargin();

		drawNumbers(g, width, arrayHeight, topMargin);
		drawArray(g, width, height, arrayHeight, topMargin);
		drawIndexes(g, width, topMargin);
		drawShapes(g);
		drawPseudocode(g);
		drawLabels(g);
	}

	public void drawArray(Graphics g, int width, int height, int arrayHeight, int topMargin){
    	g.drawRect(0, topMargin, width, arrayHeight);

        int lineX = width / arr.length;

        while(lineX < width){
        	g.drawLine(lineX, topMargin, lineX, arrayHeight + topMargin);
        	lineX += width / arr.length;
        }
    }

    public void drawNumbers(Graphics g, int width, int arrayHeight, int topMargin){
    	int numX = (width / arr.length) / 2 - 5;
        int numY = (topMargin + (arrayHeight + topMargin)) / 2;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24)); 

        for(int i = 0; i < arr.length; i++){
        	int rectX = (width / arr.length) * i;
        	int rectY = topMargin;

        	if(arrColors[i] != null){
        		g.setColor(arrColors[i]);
	        	g.fillRect(rectX, rectY, width / arr.length, arrayHeight);
        	}

        	if(arr[i] >= 0){
        		g.setColor(Color.black);               	
        		g.drawString(Integer.toString(arr[i]), numX, numY);  
        	}    	
        	numX += width / arr.length;  
        }
    }
    	
    public void drawIndexes(Graphics g, int width, int topMargin){
    	int labelX = (width / arr.length) / 2 -5;
        int labelY = topMargin - 10;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g.setColor(Color.black);

        for(int i = 0; i < arr.length; i++){
        	g.drawString(Integer.toString(i), labelX, labelY);
        	labelX += width / arr.length;
        }
    }

    public void drawShapes(Graphics g){
    	Graphics2D g2d = (Graphics2D)g;

        for(int i = 0; i < shapes.length; i++) {
        	if(shapes[i] != null){
        		g2d.setColor(shapeColors[i]);
        		g2d.fill(shapes[i]);
        	} 
		}
    }

    public void drawPseudocode(Graphics g){
    	g.setFont(new Font("TimesRoman", Font.PLAIN, 24));

    	for(int i = 0; i < text.length; i++){
       		Text t = text[i];
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

    public void drawLabels(Graphics g){
    	for(int i = 0; i < labels.length; i++){
    		if(labels[i] != null){
    			Text t = labels[i];
	    		g.setColor(t.getColor());
	    		g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
	    		g.drawString(t.getText(), t.getX(), t.getY());
    		}   		
    	}
    }
}