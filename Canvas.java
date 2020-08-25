import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Canvas extends JPanel{
	
	private final int WINDOW_WIDTH = 1000;
	private final int WINDOW_HEIGHT = 800;
	private final int ARRAY_HEIGHT = 200;
	private final int TOP_MARGIN = 30;

	private Frame frame;
	private IAlgorithm algo;

	public Canvas(){
		frame = null;
	}	

	public int getWindowWidth(){
		return WINDOW_WIDTH;
	}

	public int getWindowHeight(){
		return WINDOW_HEIGHT;
	}

	public int getArrayHeight(){
		return ARRAY_HEIGHT;
	}

	public int getTopMargin(){
		return TOP_MARGIN;
	}

	public Frame getFrame(){
		return frame;
	}

	public void updateFrame(Frame newFrame){
		frame = newFrame;
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
        return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(frame != null){
        	frame.draw(g, this);
        }
        
    }
}