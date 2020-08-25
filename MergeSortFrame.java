import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Font;

public class MergeSortFrame extends SortFrame{

	int[] tempArray;
	Color[] tempColors;
	
	public MergeSortFrame(int[] arr, Color[] arrColors, Shape[] shapes, Color[] shapeColors, Text[] text, Text[] labels, int[] tempArray, Color[] tempColors){
		super(arr, arrColors, shapes, shapeColors, text, labels);
		this.tempArray = tempArray;
		this.tempColors = tempColors;
	}

	@Override
	public void draw(Graphics g, Canvas canvas){
		int width = canvas.getWindowWidth();
		int arrayHeight = canvas.getArrayHeight();
		int topMargin = canvas.getTopMargin();

		super.draw(g, canvas);
		drawTempArray(g, width, arrayHeight, topMargin);
	}

	public void drawTempArray(Graphics g, int width, int arrayHeight, int topMargin){
		int lo = 0;
		while(lo < tempArray.length && tempArray[lo] == -2){
			lo++;
		}

		if(lo >= tempArray.length){
			return;
		}

		int hi = lo;
		while(hi + 1 < tempArray.length && tempArray[hi + 1] != -2){
			hi++;
		}

		int rectX = lo * (width / arr.length);
		int rectY = topMargin + arrayHeight + 100;
		int rectWidth = (hi - lo + 1) * (width / arr.length);
		int rectHeight = 100;
		g.drawRect(rectX, rectY, rectWidth, rectHeight);

        int lineX = rectX + (width / arr.length);

        while(lineX < rectX + rectWidth){
        	g.drawLine(lineX, rectY, lineX, rectY + rectHeight);
        	lineX += width / arr.length;
        }

        int numX = rectX + (width / arr.length) / 2 - 5;
        int numY = (rectY + (rectY + rectHeight)) / 2;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24)); 

        for(int i = lo; i <= hi; i++){
        	int cellX = (width / arr.length) * i;

        	if(tempColors[i] != null){
        		g.setColor(tempColors[i]);
	        	g.fillRect(cellX, rectY, width / arr.length, rectHeight);
        	}

        	if(tempArray[i] >= 0){
        		g.setColor(Color.black);               	
        		g.drawString(Integer.toString(tempArray[i]), numX, numY);  
        	}    	
        	numX += width / arr.length;  
        }
	}
}