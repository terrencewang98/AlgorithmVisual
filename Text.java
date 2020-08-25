import java.awt.Color;
import java.awt.Point;

public class Text{
	private String text;
	private Color color;
	private Point point;

	public Text(String text, Color color, Point point){
		this.text = text;
		this.color = color;
		this.point = point;
	}

	public String getText(){
		return text;
	}

	public Color getColor(){
		return color;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public Point getPoint(){
		return point;
	}

	public int getX(){
		return (int)point.getX();
	}

	public int getY(){
		return (int)point.getY();
	}

	@Override
	public Text clone(){
		return new Text(text, color, point);
	}
}