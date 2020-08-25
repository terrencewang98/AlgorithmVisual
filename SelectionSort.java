import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.Point;

import java.util.ArrayList;

public class SelectionSort extends SortingAlgorithm{

	public SelectionSort(Canvas canvas){
		this.width = canvas.getWindowWidth();
		this.windowHeight = canvas.getWindowHeight();
		this.arrayHeight = canvas.getArrayHeight(); 
		this.topMargin = canvas.getTopMargin();
		init();
	}

	public void init(){
		arrColors = new Color[10];
		shapes = new Shape[5]; //0: minIndex arrow (green); 1: search arrow (blue); 2: swap arrow (red); 3: swap arrow (red); 4: divider line (red)
		shapeColors = new Color[]{LIGHT_GREEN, LIGHT_BLUE, LIGHT_RED, LIGHT_RED, Color.red};
		pseudocode = new Text[7];
		labels = new Text[2]; // 0: min_pos 1: j
		frames = new ArrayList<SortFrame>();
		currentFrame = 0;

		initPseudocode();
		shuffle();
		sort();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[7];
		pseudoToWrite[0] = ("SelectionSort(A): ");
		pseudoToWrite[1] = ("   for i = 0 to A.length - 1");
		pseudoToWrite[2] = ("      min_pos = i");
		pseudoToWrite[3] = ("      for j = i + 1 to A.length - 1");
		pseudoToWrite[4] = ("         if A[j] < A[min_pos]");
		pseudoToWrite[5] = ("            min_pos = j");
		pseudoToWrite[6] = ("      swap(A, i, min_pos)");

		int textX = 20;
		int textY = windowHeight / 2 + 50;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void sort(){
 		addCurrentFrame();

		for(int i = 0; i < arr.length; i++){

			int minIndex = i;
			arrColors[minIndex] = LIGHT_GREEN;
			shapes[0] = createIndexArrow(minIndex);
			labels[0] = createIndexLabel("min_pos", minIndex);
			pseudocode[2].setColor(LIGHT_GREEN);
			addCurrentFrame();
			
			for(int j = i + 1; j < arr.length; j++){

				arrColors[j] = LIGHT_BLUE;
				shapes[1] = createIndexArrow(j);
				labels[1] = createIndexLabel("j", j);
				pseudocode[2].setColor(null);
				pseudocode[3].setColor(LIGHT_BLUE);
				addCurrentFrame();

				pseudocode[3].setColor(null);
				pseudocode[4].setColor(LIGHT_BLUE);
				addCurrentFrame();

				pseudocode[4].setColor(null);

				if(arr[j] < arr[minIndex]){
					arrColors[minIndex] = null;
					minIndex = j;
					arrColors[minIndex] = LIGHT_GREEN;
					shapes[0] = createIndexArrow(minIndex);
					labels[0] = createIndexLabel("min_pos", minIndex);
					labels[1] = null;
					shapes[1] = null;
					pseudocode[5].setColor(Color.green);
					addCurrentFrame();

					pseudocode[4].setColor(null);
					pseudocode[5].setColor(null);
				}

				else{
					arrColors[j] = null;
				}		
			}

			shapes[0] = null;
			labels[0] = null;
			shapes[1] = null;
			labels[1] = null;
			shapes[2] = createIndexArrow(i);
			shapes[3] = createIndexArrow(minIndex);
			pseudocode[3].setColor(null);
			pseudocode[6].setColor(LIGHT_RED);
			addCurrentFrame();

			if(minIndex != i){
				swap(i, minIndex);
				arrColors[minIndex] = null;	
			}

			arrColors[i] = LIGHT_RED;
			shapes[2] = null;
			shapes[3] = null;
			shapes[4] = createDividerLine(i + 1);	
			pseudocode[6].setColor(null);		
			addCurrentFrame();
		}
	}

}