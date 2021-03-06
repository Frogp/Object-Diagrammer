package Figure;

import java.awt.*;
import javax.swing.*;


abstract class TwoPointFigure extends Figure
{
    private static final long serialVersionUID = 7956325732725509831L;
	protected int _x1;
	protected int _y1;
	protected int _x2;
	protected int _y2;
	TwoPointFigure(JPanel view, Color color,int x1,int y1,int x2,int y2) {
		super(view, color);
		_x1 = x1; _y1 = y1;
		_x2 = x2; _y2 = y2;
	}
	public void drawDots(Graphics g) {
		_dotedFlag = true;
		if (_x1 > _x2) {
			int tmp = _x1;
			_x1 = _x2;
			_x2 = tmp;
		}
		if (_y1 > _y2) {
			int tmp = _y1;
			_y1 = _y2;
			_y2 = tmp;
		}
		int x1 = _x1;
		int y1 = _y1;
		int x2 = _x2;
		int y2 = _y2;
		g.setColor(Color.black);
		g.fillRect(x1-DOTSIZE,y1-DOTSIZE,DOTSIZE,DOTSIZE);
		g.fillRect(x2,y1-DOTSIZE,DOTSIZE,DOTSIZE);
		g.fillRect(x2,y2,DOTSIZE,DOTSIZE);
		g.fillRect(x1-DOTSIZE,y2,DOTSIZE,DOTSIZE);
    }
	public void eraseDots(Graphics g)
	{
		drawDots(g);
		/*
		if(_dotedFlag != true)
			return;
		_dotedFlag = false;
		if (_x1 > _x2) {
			int tmp = _x1;
			_x1 = _x2;
			_x2 = tmp;
		}
		if (_y1 > _y2) {
			int tmp = _y1;
			_y1 = _y2;
			_y2 = tmp;
		}
		int x1 = _x1;
		int y1 = _y1;
		int x2 = _x2;
		int y2 = _y2;
		g.setColor(Color.black);
		g.fillRect(x1-DOTSIZE - 1,y1-DOTSIZE-1,DOTSIZE+1,DOTSIZE+1);
		g.fillRect(x2-1,y1-DOTSIZE-1,DOTSIZE+1,DOTSIZE+1);
		g.fillRect(x2-1,y2-1,DOTSIZE+1,DOTSIZE+1);
		g.fillRect(x1-DOTSIZE-1,y2-1,DOTSIZE+1,DOTSIZE+1);
		*/
	}
	void move(int dx,int dy) {
		_x1 = _x1 + dx; _y1 = _y1 + dy;
		_x2 = _x2 + dx; _y2 = _y2 + dy;
	}
	public void drawing(Graphics g,int newX,int newY) {
		draw(g);
		_x2 = newX;
		_y2 = newY;
		draw(g);
	}
	public void makeRegion() {
		int xpoints[] = new int[4];
		int ypoints[] = new int[4];
		xpoints[0] = _x1; ypoints[0] = _y1;
		xpoints[1] = _x2; ypoints[1] = _y1;
		xpoints[2] = _x2; ypoints[2] = _y2;
		xpoints[3] = _x1; ypoints[3] = _y2;
		_region = new Polygon(xpoints,ypoints,4);
	}
}
