
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HeapSortFrame extends SortFrame{	

	protected Color[] nodeColors;
	protected int heapSize;

	public HeapSortFrame(int[] arr, Color[] arrColors, Shape[] shapes, Color[] shapeColors, 
		Text[] text, Text[] labels, Color[] nodeColors, int heapSize){
		super(arr, arrColors, shapes, shapeColors, text, labels);
		this.nodeColors = nodeColors;
		this.heapSize = heapSize;
	}

	@Override
	public void draw(Graphics g, Canvas canvas){
		int width = canvas.getWindowWidth();
		int height = canvas.getWindowHeight();

		super.draw(g, canvas);
		drawHeap(g, width, height);
	}

	public void drawHeap(Graphics g, int width, int height){
		int[] numX = new int[]{692, 515, 860, 433, 602, 780, 950, 385, 479, 567};
		int[] numY = new int[]{453, 530, 530, 620, 620, 620, 620, 730, 730, 730};
		
		g.drawLine(numX[0], numY[0], numX[1], numY[1]);
		g.drawLine(numX[0], numY[0], numX[2], numY[2]);
		g.drawLine(numX[1], numY[1], numX[3], numY[3]);
		g.drawLine(numX[1], numY[1], numX[4], numY[4]);
		g.drawLine(numX[2], numY[2], numX[5], numY[5]);
		g.drawLine(numX[2], numY[2], numX[6], numY[6]);
		g.drawLine(numX[3], numY[3], numX[7], numY[7]);
		g.drawLine(numX[3], numY[3], numX[8], numY[8]);
		g.drawLine(numX[4], numY[4], numX[9], numY[9]);
		
		int radius = 39;
		int offsetX = 6;
		int offsetY = -8;
		for(int i = 0; i < nodeColors.length; i++){
			if(nodeColors[i] != null){
				g.setColor(nodeColors[i]);
			}
			else {
				g.setColor(new Color(238, 238, 238));
			}
			g.fillOval(numX[i] - radius + offsetX , numY[i] - radius + offsetY, radius * 2, radius * 2);
			g.setColor(Color.black);
			g.drawOval(numX[i] - radius + offsetX , numY[i] - radius + offsetY, radius * 2, radius * 2);
		}
		
		for(int i = 0; i < heapSize; i++){
			g.drawString(Integer.toString(arr[i]), numX[i], numY[i]);
		}	
	}
}