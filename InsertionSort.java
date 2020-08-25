import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.Point;

import java.util.ArrayList;

public class InsertionSort extends SortingAlgorithm{

	public InsertionSort(Canvas canvas){
		this.width = canvas.getWindowWidth();
		this.windowHeight = canvas.getWindowHeight();
		this.arrayHeight = canvas.getArrayHeight(); 
		this.topMargin = canvas.getTopMargin();
		init();
	}

	public void init(){
		arrColors = new Color[10];
		shapes = new Shape[4]; //0: current index box (green); 1: search arrow (blue); 2: found arrow (green); 3: divider line (red)
		shapeColors = new Color[]{LIGHT_GREEN, LIGHT_BLUE, LIGHT_GREEN, Color.red};
		pseudocode = new Text[8];
		labels = new Text[3]; //0: temp 1: temp value 2: j
		frames = new ArrayList<SortFrame>();
		currentFrame = 0;

		initPseudocode();
		shuffle();
		sort();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[8];
		pseudoToWrite[0] = ("InsertionSort(A): ");
		pseudoToWrite[1] = ("   for i = 1 to A.length - 1");
		pseudoToWrite[2] = ("      temp = arr[i]");
		pseudoToWrite[3] = ("      j = i - 1");
		pseudoToWrite[4] = ("      while j >= 0 and A[j] > temp");
		pseudoToWrite[5] = ("         A[j + 1] = A[j]");
		pseudoToWrite[6] = ("         j = j - 1");
		pseudoToWrite[7] = ("      A[j + 1] = temp");


		int textX = 20;
		int textY = windowHeight / 2 + 50;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void sort(){

		arrColors[0] = LIGHT_RED;
		shapes[3] = createDividerLine(1);
		addCurrentFrame();

		for(int i = 1; i < arr.length; i++){

			pseudocode[1].setColor(LIGHT_BLUE);

			addCurrentFrame();
			pseudocode[1].setColor(null);

			int current = arr[i];
			removeIndexToBox(i);
			labels[0] = createIndexLabel("temp", i);
			pseudocode[2].setColor(LIGHT_GREEN);
			addCurrentFrame();		

			int j = i - 1;
			pseudocode[2].setColor(null);
			pseudocode[3].setColor(LIGHT_BLUE);
			shapes[1] = createIndexArrow(j);
			labels[2] = createIndexLabel("j", j);
			addCurrentFrame();
			pseudocode[3].setColor(null);

			while(j >= 0){

				arrColors[j] = LIGHT_BLUE;
				pseudocode[4].setColor(LIGHT_BLUE);
				addCurrentFrame();
				pseudocode[4].setColor(null);

				arrColors[j] = LIGHT_RED;

				if(current > arr[j]){
					break;
				}
				
				swap(j, j + 1);
				arrColors[j + 1] = LIGHT_RED;
				arrColors[j] = null;
				pseudocode[5].setColor(LIGHT_RED);
				addCurrentFrame();

				j--;
				pseudocode[5].setColor(null);
				pseudocode[6].setColor(LIGHT_BLUE);
				shapes[1] = createIndexArrow(j);
				labels[2] = createIndexLabel("j", j);
				if(j < 0){
					labels[2] = new Text("//j = -1", LIGHT_BLUE, new Point(150, 630));
				}
				addCurrentFrame();	
				pseudocode[6].setColor(null);			
			}

			shapes[1] = null;
			labels[2] = null;
			shapes[2] = createIndexArrow(j + 1);
			pseudocode[4].setColor(null);
			pseudocode[7].setColor(LIGHT_GREEN);
			addCurrentFrame();

			arr[j + 1] = current;
			arrColors[j + 1] = LIGHT_RED;
			shapes[0] = null;
			shapes[2] = null;
			shapes[3] = createDividerLine(i + 1);

			labels[0] = null;
			labels[1] = null;
			pseudocode[7].setColor(null);
			addCurrentFrame();
		}
	}

	public void removeIndexToBox(int index){

		int numX = (width / arr.length) * index + 45;
		int numY = arrayHeight + topMargin + 115;
		String num = Integer.toString(arr[index]);
		arr[index] = -1;
		Text boxText = new Text(num, Color.black, new Point(numX, numY));
		labels[1] = boxText;
		
    	int boxX = index * (width / arr.length);
    	int boxY = arrayHeight + topMargin + 55;
    	int boxWidth = width / arr.length;
    	shapes[0] = new Rectangle(boxX, boxY, boxWidth, boxWidth);
    }
}
