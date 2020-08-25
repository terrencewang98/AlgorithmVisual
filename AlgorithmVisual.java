import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

public class AlgorithmVisual{

	IAlgorithm algo;
	Canvas canvas;

	public AlgorithmVisual(){
		JFrame window = new JFrame("Algorithm Visual");
		algo = null;
		canvas = new Canvas();
		JPanel algoPanel = createAlgoPanel();
		JPanel buttonPanel = createButtonPanel();
		      
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(canvas, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(algoPanel, BorderLayout.EAST);
		
		window.add(mainPanel);
		window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel createAlgoPanel(){
		JPanel algoPanel = new JPanel();
		algoPanel.setLayout(new BoxLayout(algoPanel, BoxLayout.Y_AXIS));

		JButton selectionSortButton = new JButton("Selection Sort");		
		addAlgoAction(selectionSortButton, new SelectionSort(canvas));
		selectionSortButton.setMaximumSize(new Dimension(150, 30));
		selectionSortButton.setMinimumSize(new Dimension(150, 30));

		JButton insertionSortButton = new JButton("Insertion Sort");
		addAlgoAction(insertionSortButton, new InsertionSort(canvas));
		insertionSortButton.setMaximumSize(new Dimension(150, 30));
		insertionSortButton.setMinimumSize(new Dimension(150, 30));

		JButton heapSortButton = new JButton("Heap Sort");
		addAlgoAction(heapSortButton, new HeapSort(canvas));
		heapSortButton.setMaximumSize(new Dimension(150, 30));
		heapSortButton.setMinimumSize(new Dimension(150, 30));

		JButton mergeSortButton = new JButton("Merge Sort");
		addAlgoAction(mergeSortButton, new MergeSort(canvas));
		mergeSortButton.setMaximumSize(new Dimension(150, 30));
		mergeSortButton.setMinimumSize(new Dimension(150, 30));

		JButton quickSortButton = new JButton("Quick Sort");
		addAlgoAction(quickSortButton, new QuickSort(canvas));
		quickSortButton.setMaximumSize(new Dimension(150, 30));
		quickSortButton.setMinimumSize(new Dimension(150, 30));

		String[] dijkstraMenu = {"Dijkstra's (Small)", "Dijkstra's (Medium)", "Dijkstra's (Large)"};
		JComboBox dijkstraButton = new JComboBox(dijkstraMenu){
			@Override
            public Dimension getMaximumSize() {
                return new Dimension(150, 30);
            }
        };
		dijkstraButton.setSelectedIndex(0);
		dijkstraButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				int exampleNum = dijkstraButton.getSelectedIndex();
				algo = new Dijkstra(canvas, exampleNum);
				canvas.updateFrame(algo.getCurrentFrame());
			}
		});
		dijkstraButton.setAlignmentX( Component.LEFT_ALIGNMENT );

		String[] primMenu = {"Prim's (Small)", "Prim's (Medium)", "Prim's (Large)"};
		JComboBox primButton = new JComboBox(primMenu){
			@Override
            public Dimension getMaximumSize() {
                return new Dimension(150, 30);
            }
        };
		primButton.setSelectedIndex(0);
		primButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				int exampleNum = primButton.getSelectedIndex();
				algo = new PrimMST(canvas, exampleNum);
				canvas.updateFrame(algo.getCurrentFrame());
			}
		});
		primButton.setAlignmentX( Component.LEFT_ALIGNMENT );

		String[] kruskalMenu = {"Kruskal's (Small)", "Kruskal's (Medium)", "Kruskal's (Large)"};
		JComboBox kruskalButton = new JComboBox(kruskalMenu){
			@Override
            public Dimension getMaximumSize() {
                return new Dimension(150, 30);
            }
        };
		kruskalButton.setSelectedIndex(0);
		kruskalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				int exampleNum = kruskalButton.getSelectedIndex();
				algo = new KruskalMST(canvas, exampleNum);
				canvas.updateFrame(algo.getCurrentFrame());
			}
		});
		kruskalButton.setAlignmentX( Component.LEFT_ALIGNMENT );

		String[] bellmanFordMenu = {"Bellman-Ford (Small)", "Bellman-Ford (Medium)", "Bellman-Ford (Large)"};
		JComboBox bellmanFordButton = new JComboBox(bellmanFordMenu){
			@Override
            public Dimension getMaximumSize() {
                return new Dimension(150, 30);
            }
        };
		bellmanFordButton.setSelectedIndex(0);
		bellmanFordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				int exampleNum = bellmanFordButton.getSelectedIndex();
				algo = new BellmanFord(canvas, exampleNum);
				canvas.updateFrame(algo.getCurrentFrame());
			}
		});
		bellmanFordButton.setAlignmentX( Component.LEFT_ALIGNMENT );

		algoPanel.add(selectionSortButton);
		algoPanel.add(insertionSortButton);
		algoPanel.add(heapSortButton);
		algoPanel.add(mergeSortButton);
		algoPanel.add(quickSortButton);
		algoPanel.add(dijkstraButton);
		algoPanel.add(primButton);
		algoPanel.add(kruskalButton);
		algoPanel.add(bellmanFordButton);
		algoPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		return algoPanel;
	}

	public void addAlgoAction(JButton button, IAlgorithm a){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){ 
				algo = a;
				canvas.updateFrame(algo.getCurrentFrame());
			}				
		});
	}

	public JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){ 
				if(algo != null){
					canvas.updateFrame(algo.next());
				}
			}				
		});

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				if(algo != null){
					canvas.updateFrame(algo.prev());
				}
        	}  
		});

		JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){  
				if(algo != null){
					algo.init();
					canvas.updateFrame(algo.getCurrentFrame());
				}
			}
		});
		
		buttonPanel.add(prevButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(nextButton);	
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));	

		return buttonPanel;
	}

	public static void main(String[] args){
		new AlgorithmVisual();
	}

}

	