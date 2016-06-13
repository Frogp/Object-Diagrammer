package Figure;

import Parse.*;
import javax.swing.*;
import java.awt.*;

public class PointableBox extends SlaveBox
{
	private static final long serialVersionUID = 2994551803170633706L;
	Line line;
	public PointableBox(JPanel view, KField field, int x1, int y1, int x2, int y2) {
		super(view, field,Color.black,x1,y1,x2,y2);
		line = null;
	}
	public void draw(Graphics g) 
	{
		super.draw(g);
		g.drawRect(centerPoint.x - 5, centerPoint.y - 5, 10, 10);
	}
	public boolean hasLine()
	{
		if(line == null)
			return false;
		else 
			return true;
	}
	public Line getLine()
	{
		return line;
	}
	public void removeLine()
	{
		line = null;
	}
	public void setLine(Line line)
	{
		this.line = line;
	}
}
