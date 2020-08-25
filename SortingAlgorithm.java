import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import java.util.Random;
import java.util.ArrayList;

public abstract class SortingAlgorithm implements IAlgorithm{

	protected final Color LIGHT_RED = new Color(255, 102, 102);
	protected final Color LIGHT_GREEN = new Color(102, 255, 102);
	protected final Color LIGHT_BLUE = new Color(51, 204, 255);

	protected int width;
	protected int windowHeight; 
	protected int arrayHeight; 
	protected int topMargin;

	protected int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	protected Color[] arrColors;
	protected Shape[] shapes;
	protected Color[] shapeColors;
	protected Text[] pseudocode;
	protected Text[] labels;
	protected ArrayList<SortFrame> frames;
	protected int currentFrame;
	
	public abstract void init();
	public abstract void initPseudocode();
	public abstract void sort();

	public void shuffle(){
		int index;
		Random rng = new Random();

		for(int i = arr.length - 1; i > 0; i--){
			index = rng.nextInt(i + 1);
			if(index != i){
				swap(i, index);
			}
		}
	}

	public void swap(int index1, int index2){
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	public void addCurrentFrame(){
		Text[] pseudocodeClone = new Text[pseudocode.length];
		for(int i = 0; i < pseudocode.length; i++){
			if(pseudocode[i] != null){
				pseudocodeClone[i] = pseudocode[i].clone();
			}		
		}

		Text[] labelsClone = new Text[labels.length];
		for(int i = 0; i < labels.length; i++){
			if(labels[i] != null){
				labelsClone[i] = labels[i].clone();
			}	
		}

		SortFrame currentFrame = new SortFrame(arr.clone(), arrColors.clone(), 
			shapes.clone(), shapeColors.clone(), pseudocodeClone, labelsClone);
		frames.add(currentFrame);
	}

	public SortFrame getCurrentFrame(){
		return frames.get(currentFrame);
	}

	public SortFrame next(){
		if(currentFrame < frames.size() - 1){
    		currentFrame++;
    	}
    	return frames.get(currentFrame);
	}

	public SortFrame prev(){
		if(currentFrame > 0){
			currentFrame--;
		}
		return frames.get(currentFrame);
	}
		
	public Shape createDividerLine(int index){
    	int x = index * (width / arr.length) - 10;
    	int y = topMargin;
    	int lineWidth = 20;
    	int lineHeight = arrayHeight;

    	return new Rectangle(x, y, lineWidth, lineHeight);
    }

    public Shape createIndexArrow(int index){
    	int x1 = index * (width / arr.length) + 50;
    	int y1 = arrayHeight + topMargin + 50;
    	int x2 = index * (width / arr.length) + 50;
    	int y2 = arrayHeight + topMargin + 10;

    	return createArrow(x1, y1, x2, y2);
    }

   	//from https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
    public Shape createArrow(int x1, int y1, int x2, int y2){
    	Polygon arrow = new Polygon();
    	arrow.addPoint(-6,1);
	    arrow.addPoint(3,1);
	    arrow.addPoint(3,3);
	    arrow.addPoint(6,0);
	    arrow.addPoint(3,-3);
	    arrow.addPoint(3,-1);
	    arrow.addPoint(-6,-1);

	    double rotate = Math.atan2(y2 - y1, x2 - x1);

	    double midpointX = (x1 + x2) / 2.0;
	    double midpointY = (y1 + y2) / 2.0;

	    AffineTransform transform = new AffineTransform();
	    transform.translate(midpointX, midpointY);	

	    double ptDistance = Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) );
	    double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.

	    transform.scale(scale, scale);
	    transform.rotate(rotate);

    	return transform.createTransformedShape(arrow);
    }

    public Text createIndexLabel(String label, int index){
    	AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		Font font = new Font("TimesRoman", Font.PLAIN, 24);
		int stringWidth = (int)(font.getStringBounds(label, frc).getWidth());
		
		/*double offset = label.length() * 12.5 / 2;	
		int labelX = index * (width / arr.length) + 50 - (int)offset;*/
		int labelX = index * (width / arr.length) + 50 - stringWidth / 2;
		int labelY = arrayHeight + topMargin + 80;

		return new Text(label, Color.black, new Point(labelX, labelY));
	}
}