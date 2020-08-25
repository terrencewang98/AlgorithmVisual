import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Polygon;
import java.awt.Point;

import java.util.ArrayList;

public class QuickSort extends SortingAlgorithm{

	public QuickSort(Canvas canvas){
		this.width = canvas.getWindowWidth();
		this.windowHeight = canvas.getWindowHeight();
		this.arrayHeight = canvas.getArrayHeight(); 
		this.topMargin = canvas.getTopMargin();
		init();
	}

	public void init(){
		arrColors = new Color[10];
		shapes = new Shape[10]; //0: lo pointer 1: hi pointer 2: r pointer 3: i pointer 4: j pointer 5: p pointer 6/7: swap arrow 8: left divider 9: right divider
		shapeColors = new Color[]{LIGHT_BLUE, LIGHT_BLUE, LIGHT_BLUE, LIGHT_GREEN, LIGHT_GREEN, LIGHT_RED, LIGHT_RED, LIGHT_RED, Color.red, Color.red};
		pseudocode = new Text[19];
		labels = new Text[8]; //0: lo pointer 1: hi pointer 2: r pointer 3: i pointer 4: j pointer 5: p pointer 6: i out of bounds comment 7: lo/hi out of bounds comment
		frames = new ArrayList<SortFrame>();
		currentFrame = 0;
	
		initPseudocode();
		shuffle();
		sort();
	}

	public void initPseudocode(){
		String[] pseudoToWrite = new String[19];
		pseudoToWrite[0] = "QuickSort(A, lo, hi): ";
		pseudoToWrite[1] = "   if(hi <= lo) return";
		pseudoToWrite[2] = "   r = hi";
		pseudoToWrite[3] = "   p = partition(A, lo, hi, r)";
		pseudoToWrite[4] = "   QuickSort(A, lo, p - 1)";
		pseudoToWrite[5] = "   QuickSort(A, p + 1, hi)";
		pseudoToWrite[6] = "partition(A, lo, hi, r):";
		pseudoToWrite[7] = "   swap(A, r, lo)";
		pseudoToWrite[8] = "   i = lo + 1, j = hi";
		pseudoToWrite[9] = "   while i <= j";
		pseudoToWrite[10] = "      if(A[i] < A[lo])";
		pseudoToWrite[11] = "         i++";
		pseudoToWrite[12] = "      else if(A[j] >= A[lo])";
		pseudoToWrite[13] = "         j--";
		pseudoToWrite[14] = "      else //A[i] >= A[lo] and A[j] < A[lo]";
		pseudoToWrite[15] = "         swap(A, i, j)";
		pseudoToWrite[16] = "         i++, j--";
		pseudoToWrite[17] = "   swap(A, lo, j)";
		pseudoToWrite[18] = "   return j";

		int textX = 20;
		int textY = windowHeight / 2 - 50;
		for(int i = 0; i < 6; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}

		textX = width / 2 - 150	;
		textY = windowHeight / 2 - 50;
		for(int i = 6; i < pseudoToWrite.length; i++){
			Text toAdd = new Text(pseudoToWrite[i], null, new Point(textX, textY));
			pseudocode[i] = toAdd;
			textY += 30;
		}
	}

	public void sort(){
		addCurrentFrame();
		quickSort(0, arr.length - 1);
		addCurrentFrame();
	}

	public void quickSort(int lo, int hi){

		if(hi > lo){
			for(int i = lo; i <= hi; i++){
				arrColors[i] = LIGHT_BLUE;
			}
			shapes[0] = createIndexArrow(lo);
			labels[0] = createIndexLabel("lo", lo);
			shapes[1] = createIndexArrow(hi);
			labels[1] = createIndexLabel("hi", hi);

			pseudocode[0].setColor(LIGHT_BLUE);
			addCurrentFrame();
			pseudocode[0].setColor(null);

			int r = hi;
			labels[1] = createIndexLabel("hi,r", r);
			pseudocode[2].setColor(LIGHT_BLUE);
			addCurrentFrame();

			pseudocode[2].setColor(null);
			pseudocode[3].setColor(LIGHT_GREEN);

			int p = partition(lo, hi, r);

			arrColors[p] = LIGHT_RED;
			if(p == lo){
				shapes[0] = null;
				labels[0] = null;
				shapes[1] = createIndexArrow(hi);
				labels[1] = createIndexLabel("hi", hi);
				shapes[5] = createIndexArrow(p);
				labels[5] = createIndexLabel("lo, p", p);
			}
			else if(p == hi){
				shapes[0] = createIndexArrow(lo);
				labels[0] = createIndexLabel("lo", lo);
				shapes[1] = null;
				labels[1] = null;
				shapes[5] = createIndexArrow(p);
				labels[5] = createIndexLabel("p, hi", p);
			}
			else{
				shapes[0] = createIndexArrow(lo);
				labels[0] = createIndexLabel("lo", lo);
				shapes[1] = createIndexArrow(hi);
				labels[1] = createIndexLabel("hi", hi);
				shapes[5] = createIndexArrow(p);
				labels[5] = createIndexLabel("p", p);
			}
			pseudocode[3].setColor(LIGHT_RED);
			addCurrentFrame();

			pseudocode[3].setColor(null);
			pseudocode[4].setColor(LIGHT_BLUE);
			addCurrentFrame();
			pseudocode[4].setColor(null);

			shapes[5] = null;
			labels[5] = null;

			quickSort(lo, p - 1);
		
			if(p == lo){
				shapes[0] = null;
				labels[0] = null;
				shapes[1] = createIndexArrow(hi);
				labels[1] = createIndexLabel("hi", hi);
				shapes[5] = createIndexArrow(p);
				labels[5] = createIndexLabel("lo, p", p);
			}
			else if(p == hi){
				shapes[0] = createIndexArrow(lo);
				labels[0] = createIndexLabel("lo", lo);
				shapes[1] = null;
				labels[1] = null;
				shapes[5] = createIndexArrow(p);
				labels[5] = createIndexLabel("p, hi", p);
			}
			else{
				shapes[0] = createIndexArrow(lo);
				labels[0] = createIndexLabel("lo", lo);
				shapes[1] = createIndexArrow(hi);
				labels[1] = createIndexLabel("hi", hi);
				shapes[5] = createIndexArrow(p);
				labels[5] = createIndexLabel("p", p);
			}
			pseudocode[5].setColor(LIGHT_BLUE);
			addCurrentFrame();
			pseudocode[5].setColor(null);

			shapes[5] = null;
			labels[5] = null;

			quickSort(p + 1, hi);
		}
		else{

			shapes[0] = null;
			labels[0] = null;
			shapes[1] = null;
			labels[1] = null;

			if(lo == hi){
				shapes[0] = createIndexArrow(lo);
				labels[0] = createIndexLabel("lo, hi", lo);
			}
			else{
				if(lo < arr.length){
					shapes[0] = createIndexArrow(lo);
					labels[0] = createIndexLabel("lo", lo);
				}
				else{
					labels[7] = new Text("//lo = 10", LIGHT_BLUE, new Point(225, 350)); 
				}

				if(hi > -1){
					shapes[1] = createIndexArrow(hi);
					labels[1] = createIndexLabel("hi", hi);	
				}
				else{
					labels[7] = new Text("//hi = -1", LIGHT_BLUE, new Point(225, 350)); 
				}
			}
			
			pseudocode[0].setColor(LIGHT_BLUE);
			addCurrentFrame();
			pseudocode[0].setColor(null); 

			if(lo == hi){
				arrColors[lo] = LIGHT_RED;
			}
			pseudocode[1].setColor(LIGHT_RED);
			addCurrentFrame();
			pseudocode[1].setColor(null);

			shapes[0] = null;
			labels[0] = null;
			shapes[1] = null;
			labels[1] = null;
			labels[7] = null;
		}
	}

	public int partition(int lo, int hi, int r){
		for(int i = lo; i <= hi; i++){
			arrColors[i] = LIGHT_GREEN;
		}
		shapes[8] = createDividerLine(lo);
		shapes[9] = createDividerLine(hi + 1);
		pseudocode[6].setColor(LIGHT_GREEN);
		addCurrentFrame();

		pseudocode[6].setColor(null);

		arrColors[r] = LIGHT_RED;
		arrColors[lo] = LIGHT_RED;
		shapes[6] = createIndexArrow(r);
		shapes[7] = createIndexArrow(lo);
		pseudocode[7].setColor(LIGHT_RED);
		addCurrentFrame();

		swap(r, lo);
		addCurrentFrame();

		arrColors[r] = LIGHT_GREEN;
		arrColors[lo] = LIGHT_GREEN;
		shapes[1] = null;
		labels[1] = null;
		shapes[6] = null;
		shapes[7] = null;
		pseudocode[7].setColor(null);

		int i = lo + 1;
		int j = hi;

		if(i == j){
			shapes[3] = null;
			labels[3] = null;
			shapes[4] = createIndexArrow(j);
			labels[4] = createIndexLabel("i, j", j);
		}
		else{
			shapes[3] = createIndexArrow(i);
			labels[3] = createIndexLabel("i", i);
			shapes[4] = createIndexArrow(j);
			labels[4] = createIndexLabel("j", j);
		}

		pseudocode[8].setColor(LIGHT_GREEN);
		addCurrentFrame();
		pseudocode[8].setColor(null);

		while(i <= j){
			pseudocode[9].setColor(LIGHT_GREEN);
			addCurrentFrame();
			pseudocode[9].setColor(null);

			if(arr[i] < arr[lo]){
				pseudocode[10].setColor(LIGHT_GREEN);
				addCurrentFrame();
				pseudocode[10].setColor(null);

				i++;
				if(i == j){
					shapes[3] = null;
					labels[3] = null;
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("i, j", j);
				}
				else if(i > 10){
					labels[4] = createIndexLabel("j", j);
					labels[6] = new Text("//i = 10", Color.green, new Point(450, 500));
				}
				else{
					shapes[3] = createIndexArrow(i);
					labels[3] = createIndexLabel("i", i);
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("j", j);
				}
			
				pseudocode[11].setColor(LIGHT_GREEN);		
				addCurrentFrame();
				pseudocode[11].setColor(null);

				labels[6] = null;
			}
			else if(arr[j] >= arr[lo]){
				pseudocode[12].setColor(LIGHT_GREEN);
				addCurrentFrame();
				pseudocode[12].setColor(null);

				j--;
				if(i == j){
					shapes[3] = null;
					labels[3] = null;
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("i, j", j);
				}
				else if(j == lo){
					shapes[0] = null;
					labels[0] = null;
					shapes[3] = createIndexArrow(i);
					labels[3] = createIndexLabel("i", i);
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("lo, j", j);
				}
				else{
					shapes[3] = createIndexArrow(i);
					labels[3] = createIndexLabel("i", i);
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("j", j);
				}

				pseudocode[13].setColor(LIGHT_GREEN);		
				addCurrentFrame();		
				pseudocode[13].setColor(null);
			}
			else{
				pseudocode[14].setColor(LIGHT_GREEN);
				addCurrentFrame();
				pseudocode[14].setColor(null);

				arrColors[i] = LIGHT_RED;
				arrColors[j] = LIGHT_RED;
				shapes[6] = createIndexArrow(i);
				shapes[7] = createIndexArrow(j);
				pseudocode[15].setColor(LIGHT_RED);
				addCurrentFrame();

				swap(i, j);
				addCurrentFrame();

				arrColors[i] = LIGHT_GREEN;
				arrColors[j] = LIGHT_GREEN;
				shapes[6] = null;
				shapes[7] = null;
				pseudocode[15].setColor(null);

				i++;
				j--;

				if(i == j){
					shapes[3] = null;
					labels[3] = null;
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("i, j", j);
				}
				else{
					shapes[3] = createIndexArrow(i);
					labels[3] = createIndexLabel("i", i);
					shapes[4] = createIndexArrow(j);
					labels[4] = createIndexLabel("j", j);
				}
			
				pseudocode[16].setColor(LIGHT_GREEN);
				addCurrentFrame();
				pseudocode[16].setColor(null);
			}
		}
		arrColors[lo] = LIGHT_RED;
		arrColors[j] = LIGHT_RED;
		shapes[6] = createIndexArrow(lo);
		shapes[7] = createIndexArrow(j);
		pseudocode[17].setColor(LIGHT_RED);
		addCurrentFrame();

		if(lo != j){
			swap(lo, j);
			addCurrentFrame();
		}
		
		shapes[0] = null;
		labels[0] = null;
		shapes[3] = null;
		labels[3] = null;
		shapes[6] = null;
		shapes[7] = null;

		arrColors[lo] = LIGHT_GREEN;
		arrColors[j] = LIGHT_GREEN;
		shapes[4] = createIndexArrow(j);
		labels[4] = createIndexLabel("j", j);
		pseudocode[17].setColor(null);

		pseudocode[18].setColor(LIGHT_GREEN);
		addCurrentFrame();

		for(int index = 0; index < arr.length; index++){
			if(arrColors[index] != null && arrColors[index].equals(LIGHT_GREEN)){
				arrColors[index] = null;
			}
		}

		shapes[4] = null;
		labels[4] = null;
		shapes[8] = null;
		shapes[9] = null;
		pseudocode[18].setColor(null);

		return j;
	}
}