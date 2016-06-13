package Figure;

import java.awt.*;
import javax.swing.*;

public class Line extends Figure 
{
	private static final long serialVersionUID = -3025060104125903715L;
	public int _x1;
	public int _y1;
	public int _x2;
	public int _y2;
	public PointableBox mySideBox;
	public ClassBox classSideBox;

	public Line(JPanel view,Color color,int x1,int y1,int x2,int y2) {
		super(view, color);
		_x1 = x1; _y1 = y1;
		_x2 = x2; _y2 = y2;
	}
	public void setMySideBox(PointableBox pBox)
	{
		mySideBox = pBox;
		_x1 = mySideBox.getCenterPoint().x;
		_y1 = mySideBox.getCenterPoint().y;
	}
	public void setClassSideBox(ClassBox cBox)
	{
		classSideBox = cBox;
		//_x2 = classSideBox.getCenterPoint().x;
		//_y2 = classSideBox.getCenterPoint().y;
		_x2 = getNearPoint().x;
		_y2 = getNearPoint().y;
	}
	Point getNearPoint()
	{
		//if(classSideBox == null);
		Polygon poly = classSideBox.getRegion();
		Point mySideBoxPoint = mySideBox.getCenterPoint();
		Point result = new Point(poly.xpoints[0], poly.ypoints[0]);
		for(int i = 0; i < poly.npoints; i++){
			if( Math.abs((poly.xpoints[i] - mySideBoxPoint.x)) + Math.abs((poly.ypoints[i] - mySideBoxPoint.y)) 
				< Math.abs((result.x - mySideBoxPoint.x)) + Math.abs((result.y - mySideBoxPoint.y))){
				result.x = poly.xpoints[i];
				result.y = poly.ypoints[i];
			}
		}
		return result;
	}
	public void draw(Graphics g) {
		g.setColor(_color);
		g.drawLine(_x1,_y1,_x2,_y2);
		if(classSideBox != null){	
			drawHead(g);
		}
		//g.drawRect(getNearPoint().x, getNearPoint().y, 10,10);
		makeRegion();
	}

	public void drawDots(Graphics g) {
		_dotedFlag = true;
		g.setColor(Color.black);
		g.fillRect(_x1-DOTSIZE/2,_y1-DOTSIZE/2,DOTSIZE,DOTSIZE);
		g.fillRect(_x2-DOTSIZE/2,_y2-DOTSIZE/2,DOTSIZE,DOTSIZE);
	}
	public void drawing(Graphics g,int newX,int newY) {
		draw(g);
		_x2 = newX;
		_y2 = newY;
		draw(g);
	}
	public void mySideBoxMove(Graphics g,int dx,int dy)
	{
		draw(g);
		mySideBoxMove(dx, dy);
		draw(g);
	}
	public void classSideBoxMove(Graphics g,int dx,int dy)
	{
		draw(g);
		classSideBoxMove(dx, dy);
		draw(g);
	}
	public void mySideBoxMove(int dx, int dy)
	{
		_x1 = _x1 + dx; _y1 = _y1 + dy;
		_x2 = getNearPoint().x;
		_y2 = getNearPoint().y;

	}
	public void classSideBoxMove(int dx, int dy)
	{
		_x2 = getNearPoint().x;
		_y2 = getNearPoint().y;
		//_x2 = _x2 + dx; _y2 = _y2 + dy;
	}
	public void move(int dx,int dy){
	//	_x1 = _x1 + dx; _y1 = _y1 + dy;
	//	_x2 = _x2 + dx; _y2 = _y2 + dy;
	}
	public void remove()
	{
		if(	mySideBox != null)
			mySideBox.removeLine();
		mySideBox = null;
		if(classSideBox != null)
			classSideBox.removeLine(this);
		classSideBox = null;
	}

	private void fillArrow(Graphics g,int x, int y,int dx1,int dy1,int dx2,int dy2)
	{
		Point data[] = new Point[3];
		for(int i = 0; i < 3; i++) {
			data[i] = new Point();
		}
		data[0].x = x;
		data[0].y = y;
		data[1].x = x + dx1;
		data[1].y = y + dy1;
		data[2].x = x + dx2;
		data[2].y = y + dy2;
		g.drawLine(data[0].x,data[0].y,data[1].x,data[1].y);
		g.drawLine(data[0].x,data[0].y,data[2].x,data[2].y);

		//MySys.myFillPolygon(g,data,3);
	}
	public double sign(double x) {
		if(x<0) return (-1);
		else return(1);
	}
	private void drawHead(Graphics g) {
		int arrowlength = 20;
		double deltax = (double)(_x2 - _x1);
		double deltay = (double)(_y2 - _y1);
		double theta;
		if (deltax == 0) {
			theta = sign(deltay)*Math.PI/2.;
		} else {
			theta = Math.atan(deltay/deltax);
		}
		double angle1 = theta - (Math.PI * 3. / 4. + 0.5);
		double angle2 = theta + (Math.PI * 3. / 4. + 0.5);
		int dx1 = (int)(arrowlength * Math.cos(angle1));
		int dy1 = (int)(arrowlength * Math.sin(angle1));
		int dx2 = (int)(arrowlength * Math.cos(angle2));
		int dy2 = (int)(arrowlength * Math.sin(angle2));
		if ( deltax >= 0 ) {
			fillArrow(g,_x2,_y2,dx1,dy1,dx2,dy2);
		} else {
			fillArrow(g,_x2,_y2,-dx1,-dy1,-dx2,-dy2);
		}		
	}

	public void makeRegion() {
		int regionWidth = 6;
		int x = _x1;
		int y = _y1;
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		int sign_h = 1;
		if (h < 0) sign_h = -1;
		double angle;
		double theta = (w!=0) ? Math.atan((double)(h)/(double)(w)) : sign_h*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(regionWidth * Math.cos(angle));
		int dy = (int)(regionWidth * Math.sin(angle));
		int xpoints[] = new int[4];
		int ypoints[] = new int[4];
		xpoints[0] = x + dx;     ypoints[0] = y + dy;
		xpoints[1] = x - dx;     ypoints[1] = y - dy;
		xpoints[2] = x + w - dx; ypoints[2] = y + h - dy;
		xpoints[3] = x + w + dx; ypoints[3] = y + h + dy;
		_region = new Polygon(xpoints,ypoints,4);
	}
}
