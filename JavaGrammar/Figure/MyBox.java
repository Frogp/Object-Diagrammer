package Figure;

import java.awt.*;
import javax.swing.*;

public class MyBox extends TwoPointFigure 
{
// Attributes
	//private String className;
	protected Point centerPoint = new Point();
	private static final long serialVersionUID = 5338192847883583687L;
// Operations
	public MyBox(JPanel view, Color color,int x1,int y1,int x2,int y2) {
		super(view, color,x1,y1,x2,y2);
	}
	public void draw(Graphics g) {
		g.setColor(_color);
		g.drawRect(_x1,_y1,_x2-_x1,_y2-_y1);
	}
	Figure copy() {
		MyBox newBox = new MyBox(_view,_color,_x1,_y1,_x2,_y2);
		newBox.move(20,10);
		return newBox;
    }
	void move(int dx,int dy) {
		_x1 = _x1 + dx; _y1 = _y1 + dy;
		_x2 = _x2 + dx; _y2 = _y2 + dy;
	}
	public Point getCenterPoint()
	{
		return centerPoint;
	}
	public void makeCenterPoint()
	{
		centerPoint.x = (_x1+_x2)/2;
		centerPoint.y = (_y1+_y2)/2;
	}

}
