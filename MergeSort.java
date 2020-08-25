import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.Point;

import java.util.ArrayList;

public class MergeSort extends SortingAlgorithm{

	int[] tempArray;
	Color[] tempColors;

	public MergeSort(Canvas canvas){
		this.width = canvas.getWindowWidth();
		this.windowHeight = canvas.getWindowHeight();
		this.arrayHeight = canvas.getArrayHeight(); 
		this.topMargin = canvas.getTopMargin();
		init();
	}

	public void init(){
		arrColors = new Color[10];
		shapes = new Shape[9]; //0: lo pointer 1: hi pointer 2: mid pointer 3: tempIndex pointer 4: left pointer 5: right pointer 6: left divider 7: right divider 8: mid divider
		labels = new Text[6]; //0: lo pointer 1: hi pointer 2: mid pointer 3: tempIndex pointer 4: left pointer 5: right pointer
		shapeColors = new Color[]{LIGHT_BLUE, LIGHT_BLUE, LIGHT_BLUE, LIGHT_RED, LIGHT_RED, LIGHT_RED, Color.red, Color.red, Color.red};
		pseudocode = new Text[15];
		frames = new ArrayList<SortFrame>();
		currentFrame = 0;
		tempArray = new int[10];
		for(int i = 0; i < tempArray.length; i++){
			tempArray[i] = -2;
		}
		tempColors = new Color[10];

		initPseudocode();
		shuffle();
		sort();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[15];
		pseudoToWrite[0] = "MergeSort(A, lo, hi): ";
		pseudoToWrite[1] = "   if(hi <= lo) return";
		pseudoToWrite[2] = "   mid = (lo + hi) / 2 ";
		pseudoToWrite[3] = "   mergeSort(A, lo, mid)";
		pseudoToWrite[4] = "   mergeSort(A, mid + 1, hi)";
		pseudoToWrite[5] = "   merge(A, lo, mid, hi)";
		pseudoToWrite[6] = "merge(A, lo, hi, mid):";
		pseudoToWrite[7] = "   tempArr = new int[hi - lo + 1], i = 0, left = lo, right = mid + 1";
		pseudoToWrite[8] = "   while(left <= mid and right <= hi)";
		pseudoToWrite[9] = "      if(arr[left] < arr[right])";
		pseudoToWrite[10] = "         tempArr[i] = arr[left], left++, i++";
		pseudoToWrite[11] = "      else";
		pseudoToWrite[12] = "         tempArr[i] = arr[right], right++, i++";
		pseudoToWrite[13] = "   copy remaining elements";
		pseudoToWrite[14] = "   copy temp to array";

		int textX = 20;
		int textY = windowHeight / 2 + 150;
		for(int i = 0; i < 6; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}

		textX = width / 2 - 150	;
		textY = windowHeight / 2 + 150;
		for(int i = 6; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void sort(){
		addCurrentFrame();
		mergeSort(0, arr.length - 1);
		addCurrentFrame();
	}

	public void mergeSort(int lo, int hi){

		for(int i = 0; i < arr.length; i++){
			if(i >= lo && i <= hi){
				arrColors[i] = LIGHT_BLUE;
			}
			else{
				arrColors[i] = null;
			}
		}
		shapes[6] = createDividerLine(lo);
		shapes[7] = createDividerLine(hi + 1);
		pseudocode[0].setColor(LIGHT_BLUE);

		if(lo < hi){

			shapes[0] = createIndexArrow(lo);
			shapes[1] = createIndexArrow(hi);
			labels[0] = createIndexLabel("lo", lo);
			labels[1] = createIndexLabel("hi", hi);
			addCurrentFrame();

			pseudocode[0].setColor(null);

			int mid = (lo + hi) / 2;
			shapes[2] = createIndexArrow(mid);
			if(mid == lo){
				labels[0] = null;
				labels[2] = createIndexLabel("lo, mid", mid);
			}
			else{
				labels[2] = createIndexLabel("mid", mid);
			}

			pseudocode[2].setColor(LIGHT_BLUE);
			addCurrentFrame();
			pseudocode[2].setColor(null);

			for(int i = lo; i <=  mid; i++){
				arrColors[i] = LIGHT_GREEN;
			}
			pseudocode[3].setColor(LIGHT_GREEN);
			addCurrentFrame();

			shapes[2] = null;
			labels[2] = null;
			pseudocode[3].setColor(null);

			mergeSort(lo, mid);

			for(int i = mid + 1; i <= hi; i++){
				arrColors[i] = LIGHT_GREEN;
			}
			shapes[1] = createIndexArrow(hi);
			labels[1] = createIndexLabel("hi", hi);
			shapes[0] = createIndexArrow(lo);
			if(mid == lo){
				labels[0] = createIndexLabel("lo, mid", lo);
			}
			else{
				labels[0] = createIndexLabel("lo", lo);
				shapes[2] = createIndexArrow(mid);
				labels[2] = createIndexLabel("mid", mid);
			}
			shapes[6] = createDividerLine(lo);
			shapes[7] = createDividerLine(hi + 1);
			pseudocode[4].setColor(LIGHT_GREEN);
			addCurrentFrame();

			shapes[2] = null;
			labels[2] = null;
			pseudocode[4].setColor(null);

			mergeSort(mid + 1, hi);

			for(int i = lo; i <= hi; i++){
				arrColors[i] = LIGHT_RED;
			}
			shapes[1] = createIndexArrow(hi);
			labels[1] = createIndexLabel("hi", hi);
			shapes[0] = createIndexArrow(lo);
			if(mid == lo){
				labels[0] = createIndexLabel("lo, mid", lo);
			}
			else{
				labels[0] = createIndexLabel("lo", lo);
				shapes[2] = createIndexArrow(mid);
				labels[2] = createIndexLabel("mid", mid);
			}
			shapes[6] = createDividerLine(lo);
			shapes[7] = createDividerLine(hi + 1);
			pseudocode[5].setColor(LIGHT_RED);
			pseudocode[6].setColor(LIGHT_RED);
			addCurrentFrame();

			pseudocode[6].setColor(null);

			merge(lo, mid, hi);
			pseudocode[5].setColor(null);	
		}
		else{
			shapes[0] = createIndexArrow(lo);
			labels[0] = createIndexLabel(" lo, hi", lo);
			shapes[1] = null;
			labels[1] = null;
			addCurrentFrame();

			pseudocode[0].setColor(null);

			arrColors[lo] = LIGHT_RED;
			pseudocode[1].setColor(LIGHT_RED);
			addCurrentFrame();

			shapes[0] = null;
			labels[0] = null;
			pseudocode[1].setColor(null);
		}
	}

	public void merge(int lo, int mid, int hi){	
		
		for(int i = 0; i < tempArray.length; i++){
			if(i >= lo && i <= hi){
				tempArray[i] = -1; 
			}
			else{
				tempArray[i] = -2;
			}
		}

		int tempIndex = lo;
		int left = lo; 
		int right = mid + 1;

		shapes[0] = null;
		labels[0] = null;
		shapes[1] = null;
		labels[1] = null;
		shapes[2] = null;
		labels[2] = null;
			
		shapes[3] = createTempIndexArrow(tempIndex);		
		labels[3] = createTempIndexLabel("i", tempIndex);
		shapes[4] = createIndexArrow(left);
		labels[4] = createIndexLabel("left", left);
		shapes[5] = createIndexArrow(right);
		labels[5] = createIndexLabel("right", right);
		shapes[8] = createDividerLine(mid + 1);

		pseudocode[7].setColor(LIGHT_RED);
		addCurrentFrame();
		pseudocode[7].setColor(null);

		while(left <= mid && right <= hi){

			pseudocode[8].setColor(LIGHT_RED);
			addCurrentFrame();
			pseudocode[8].setColor(null);

			if(arr[left] < arr[right]){

				pseudocode[9].setColor(LIGHT_RED);
				addCurrentFrame();
				pseudocode[9].setColor(null);
				pseudocode[10].setColor(LIGHT_RED);

				tempArray[tempIndex] = arr[left];
				left++;

				if(left == right){
					shapes[4] = null;
					labels[4] = null;
					labels[5] = createIndexLabel("left, right", right);
				}
				else{
					shapes[4] = createIndexArrow(left);
					labels[4] = createIndexLabel("left", left);
				}
			}

			else{

				pseudocode[11].setColor(LIGHT_RED);
				addCurrentFrame();
				pseudocode[11].setColor(null);
				pseudocode[12].setColor(LIGHT_RED);

				tempArray[tempIndex] = arr[right];
				right++;

				shapes[5] = createIndexArrow(right);
				labels[5] = createIndexLabel("right", right);
			}

			tempIndex++;
			shapes[3] = createTempIndexArrow(tempIndex);
			labels[3] = createTempIndexLabel("i", tempIndex);
			addCurrentFrame();

			pseudocode[10].setColor(null);
			pseudocode[12].setColor(null);
		}

		pseudocode[13].setColor(LIGHT_RED);

		while(left <= mid){
			tempArray[tempIndex] = arr[left];
			tempIndex++;
			left++;

			if(left == right){
				shapes[4] = null;
				labels[4] = null;
				labels[5] = createIndexLabel("left, right", right);
			}
			else{
				shapes[4] = createIndexArrow(left);
				labels[4] = createIndexLabel("left", left);
			}
			addCurrentFrame();
		}

		while(right <= hi){
			tempArray[tempIndex] = arr[right];
			tempIndex++;
			right++;
			
			shapes[5] = createIndexArrow(right);
			labels[5] = createIndexLabel("right", right);
	
			addCurrentFrame();
		}

		shapes[3] = null;
		labels[3] = null;
		shapes[4] = null;
		labels[4] = null;
		shapes[5] = null;
		labels[5] = null;	
		shapes[8] = null;

		pseudocode[13].setColor(null);
		pseudocode[14].setColor(LIGHT_RED);
		addCurrentFrame();
		pseudocode[14].setColor(null);

		for(int i = lo; i <= hi; i++){
			arr[i] = tempArray[i];
			tempArray[i] = -2;
		}

		addCurrentFrame();		
	}

	 public Shape createTempIndexArrow(int index){
    	int x1 = index * (width / arr.length) + 50;
    	int y1 = arrayHeight + topMargin + 250;
    	int x2 = index * (width / arr.length) + 50;
    	int y2 = arrayHeight + topMargin + 210;

    	return createArrow(x1, y1, x2, y2);
    }

     public Text createTempIndexLabel(String label, int index){
		double offset = label.length() * 12.5 / 2;
		int labelX = index * (width / arr.length) + 50 - (int)offset;
		int labelY = arrayHeight + topMargin + 280;

		return new Text(label, Color.black, new Point(labelX, labelY));
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

		MergeSortFrame currentFrame = new MergeSortFrame(arr.clone(), arrColors.clone(), shapes.clone(), 
			shapeColors.clone(), pseudocodeClone, labelsClone, tempArray.clone(), tempColors.clone());
		frames.add(currentFrame);
	}


}