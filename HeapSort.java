import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.Point;

import java.util.ArrayList;

public class HeapSort extends SortingAlgorithm{

	private Color[] nodeColors;
	private int heapSize;

	public HeapSort(Canvas canvas){
		this.width = canvas.getWindowWidth();
		this.windowHeight = canvas.getWindowHeight();
		this.arrayHeight = canvas.getArrayHeight(); 
		this.topMargin = canvas.getTopMargin();
		init();
	}

	public void init(){
		arrColors = new Color[10];
		shapes = new Shape[9]; //0: i pointer 1: j pointer 2: maxPos pointer 3: swap arrow 4: swap arrow 5-7: heap divider lines 8: divider line 
		shapes[5] = createDividerLine(1);
		shapes[6] = createDividerLine(3);
		shapes[7] = createDividerLine(7);
		shapeColors = new Color[]{LIGHT_BLUE, LIGHT_BLUE, LIGHT_GREEN, LIGHT_RED, LIGHT_RED, Color.blue, Color.blue, Color.blue, Color.red};
		frames = new ArrayList<SortFrame>();
		currentFrame = 0;
		pseudocode = new Text[16];
		labels = new Text[3]; //0: i pointer 1: j pointer 2: maxPos pointer
		nodeColors = new Color[10];
		heapSize = 10;
		initPseudocode();
		shuffle();
		sort();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[16];
		pseudoToWrite[0] = "HeapSort(A): ";
		pseudoToWrite[1] = "   A.maxHeapify()";
		pseudoToWrite[2] = "   for i = 0 to A.length - 1";
		pseudoToWrite[3] = "      swap(A, 0, A.length - 1 - i)";
		pseudoToWrite[4] = "      remove(A.length - 1 - i)";
		pseudoToWrite[5] = "      siftDown(0)";
		pseudoToWrite[6] = "";
		pseudoToWrite[7] = "maxHeapify(): ";
		pseudoToWrite[8] = "   for j = A.length / 2 - 1 to 0";
		pseudoToWrite[9] = "      siftDown(j)";
		pseudoToWrite[10] = "";
		pseudoToWrite[11] = "siftDown(root): ";
		pseudoToWrite[12] = "   maxPos = max(root, left, right)";
		pseudoToWrite[13] = "   if(maxPos != root)";
		pseudoToWrite[14] = "      swap(maxPos, root)";
		pseudoToWrite[15] = "      siftDown(maxPos)";

		int textX = 20;
		int textY = windowHeight / 2 - 60;
		for(int i = 0; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void sort(){
		addCurrentFrame();

		pseudocode[1].setColor(LIGHT_BLUE);
		addCurrentFrame();

		for(int j = arr.length / 2 - 1; j >= 0; j--){

			arrColors[j] = LIGHT_BLUE;
			nodeColors[j] = LIGHT_BLUE;
			shapes[1] = createIndexArrow(j);
			labels[1] = createIndexLabel("j", j);
			pseudocode[8].setColor(LIGHT_BLUE);
			addCurrentFrame();

			arrColors[j] = null;
			shapes[1] = null;
			labels[1] = null;
			pseudocode[8].setColor(null);
			
			arrColors[j] = LIGHT_GREEN;
			nodeColors[j] = LIGHT_GREEN;
			pseudocode[9].setColor(LIGHT_GREEN);
			addCurrentFrame();

			siftDown(j, arr.length);	
			pseudocode[9].setColor(null);
		}
		
		shapes[1] = null;
		labels[1] = null;
		pseudocode[1].setColor(null);
	
		for(int i = 0; i < arr.length - 1; i++){

			if(arr.length - 1 - i < 3)
				shapes[6] = null;
			if(arr.length - 1 - i < 7)
				shapes[7] = null;

			arrColors[arr.length - 1 - i] = LIGHT_BLUE;
			shapes[0] = createIndexArrow(arr.length - 1 - i);
			labels[0] = createIndexLabel("A.length - 1 - i", arr.length - 1 - i);
			pseudocode[2].setColor(LIGHT_BLUE);
			addCurrentFrame();

			pseudocode[2].setColor(null);	

			arrColors[0] = LIGHT_RED;
			arrColors[arr.length - 1 - i] = LIGHT_RED;
			nodeColors[0] = LIGHT_RED;
			nodeColors[arr.length - 1 - i] = LIGHT_RED;
			shapes[3] = createIndexArrow(0);
			shapes[4] = createIndexArrow(arr.length - 1 - i);
			pseudocode[3].setColor(LIGHT_RED);
			addCurrentFrame();

			swap(0, arr.length - 1 - i);
			addCurrentFrame();

			arrColors[0] = null;
			nodeColors[0] = null;
			nodeColors[arr.length - 1 - i] = null;
			shapes[0] = null;
			shapes[3] = null;
			shapes[4] = null;
			labels[0] = null;
			pseudocode[3].setColor(null);

			arrColors[arr.length - 1 - i] = LIGHT_RED;
			shapes[8] = createDividerLine(arr.length - 1 - i);
			pseudocode[4].setColor(LIGHT_BLUE);
			heapSize--;
			addCurrentFrame();			

			pseudocode[4].setColor(null);

			arrColors[0] = LIGHT_GREEN;
			nodeColors[0] = LIGHT_GREEN;

			pseudocode[5].setColor(LIGHT_GREEN);
			addCurrentFrame();

			siftDown(0, arr.length - 1 - i);

			pseudocode[5].setColor(null);
		}

		heapSize--;
		arrColors[0] = LIGHT_RED;
		shapes[2] = null;
		shapes[5] = null;
		shapes[8] = createDividerLine(0);
		labels[2] = null;
		pseudocode[5].setColor(null);
		pseudocode[12].setColor(null);
		addCurrentFrame();
	}

	private void siftDown(int root, int heapSize){
		int max = root;
		int left = root * 2 + 1;
		int right = root * 2 + 2;

		if(left < heapSize && arr[left] > arr[max]){
			max = left;
		}

		if(right < heapSize && arr[right] > arr[max]){
			max = right;
		}

		if(max != root){

			arrColors[max] = LIGHT_GREEN;
			nodeColors[max] = LIGHT_GREEN;
			shapes[2] = createIndexArrow(max);
			labels[2] = createIndexLabel("maxPos", max);
			pseudocode[12].setColor(LIGHT_GREEN);
			addCurrentFrame();

			pseudocode[12].setColor(null);
			pseudocode[13].setColor(LIGHT_GREEN);
			addCurrentFrame();

			pseudocode[13].setColor(null);

			arrColors[max] = LIGHT_RED;
			arrColors[root] = LIGHT_RED;
			nodeColors[max] = LIGHT_RED;
			nodeColors[root] = LIGHT_RED;
			shapes[3] = createIndexArrow(max);
			shapes[4] = createIndexArrow(root);
			pseudocode[14].setColor(LIGHT_RED);
			addCurrentFrame();

			swap(max, root);
			addCurrentFrame();

			arrColors[max] = null;
			arrColors[root] = null;
			nodeColors[max] = null;
			nodeColors[root] = null;
			shapes[3] = null;
			shapes[4] = null;
			pseudocode[14].setColor(null);

			arrColors[max] = LIGHT_GREEN;
			nodeColors[max] = LIGHT_GREEN;
			shapes[2] = createIndexArrow(max);
			pseudocode[15].setColor(LIGHT_GREEN);
			addCurrentFrame();

			shapes[2] = null;
			labels[2] = null;
			pseudocode[15].setColor(null);

			pseudocode[11].setColor(LIGHT_GREEN);
			addCurrentFrame();

			pseudocode[11].setColor(null);
			siftDown(max, heapSize);
		}

		else{			
			shapes[2] = createIndexArrow(max);
			labels[2] = createIndexLabel("maxPos", max);
			pseudocode[12].setColor(LIGHT_GREEN);
			addCurrentFrame();

			arrColors[root] = null;
			nodeColors[root] = null;
			shapes[2] = null;
			labels[2] = null;
			pseudocode[12].setColor(null);
		}
	}

	@Override
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

		HeapSortFrame currentFrame = new HeapSortFrame(arr.clone(), arrColors.clone(), shapes.clone(), 
			shapeColors.clone(), pseudocodeClone, labelsClone, nodeColors.clone(), heapSize);
		frames.add(currentFrame);
	}
}