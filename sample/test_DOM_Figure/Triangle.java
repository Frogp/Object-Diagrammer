package figure;

import java.awt.*;
import java.lang.*;
import java.io.*;
/** �� Ŭ������ �⺻ ǥ��� �߿��� �ﰢ���� ǥ���ϱ� ���� ���̴�..
 */
public 
	class Triangle extends TwoPointFigure {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -6090829103669198754L;
	/** �� �ﰢ�� ��ü�� ������ ����� �Լ��̴�.
	 */
	protected void makeRegion(int x,int y,int w,int h) {
		int length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		double theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		double angle = (theta + Math.PI / 2.);
		int dx = (int)(length * Math.cos(angle));
		int dy = (int)(length * Math.sin(angle));
		_points[0].x = x + dx;
		_points[0].y = y + dy;
		_points[1].x = x - dx;
		_points[1].y = y - dy;
		_points[2].x = x + w;
		_points[2].y = y + h;
		if (_region != null) CRgn.myDestroyRgn(_region);
		_region = CRgn.myPolygonRgn(_points,3);
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
		_maxRegion = CRgn.myCreateRgn();
		CRgn.myUnionRgn(_maxRegion,_region,_maxRegion);
		CRgn tmpregion = makeOverRegion(x+dx,y+dy,-2*dx,-2*dy);
		CRgn.myUnionRgn(_maxRegion,tmpregion,_maxRegion);
		CRgn.myDestroyRgn(tmpregion);
		tmpregion = makeOverRegion(x-dx,y-dy,w+dx,h+dy);
		CRgn.myUnionRgn(_maxRegion,tmpregion,_maxRegion);
		CRgn.myDestroyRgn(tmpregion);
		tmpregion = makeOverRegion(x+w,y+h,dx-w,dy-h);
		CRgn.myUnionRgn(_maxRegion,tmpregion,_maxRegion);
		CRgn.myDestroyRgn(tmpregion);
	}
	/** �� �ﰢ�� ���� ���� �� �ȼ� ������ ���� ������ ����� �Լ��̴�. 
	 */
	protected CRgn makeOverRegion(int x,int y,int w,int h) {
		double angle;
		double theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(2 * Math.cos(angle));
		int dy = (int)(2 * Math.sin(angle));
		CPoint  data[] = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			data[i] = new CPoint();
		}
		data[0].x = x + dx;
		data[0].y = y + dy;
		data[1].x = x - dx;
		data[1].y = y - dy;
		data[2].x = x + w - dx;
		data[2].y = y + h - dy;
		data[3].x = x + w + dx;
		data[3].y = y + h + dy;
		return(CRgn.myPolygonRgn(data,4));
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		super.delete();
	}
	/** �ﰢ���� �׸� �� �ִ� �ּ����� ���� ���̸� ���°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean checkMinWH() {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		int length;
		if ( w == 0 && h == 0 ) length = 0;
		else if ( w == 0 ) length = Math.abs(h);
		else if ( h == 0 ) length = Math.abs(w);
		else length = (int)(Math.sqrt((double)(w*w + h*h)));
		if ( length >= Const.FIGUREMINL ) return false;
		_controller.beep("Triangle.checkMinWH");
		return true;
	}
	/** �������̴�.
	 */
	public Triangle(GraphicController controller,int x,int y,Popup popup) {
		super(controller,x,y);
		if (_points != null) _points=null;
		_points = new CPoint[3];
		for(int i = 0; i < 3; i++) {
			_points[i] = new CPoint();
		}	
		_popup = popup;
		filled = false;
	}
	/** �������̴�.
	 */
	public Triangle(GraphicController controller,int x1,int y1,int x2,int y2,Popup popup) {
		super(controller,x1,y1,x2,y2);
		if (_points != null)  _points=null;
		_points = new CPoint[3];
		for(int i = 0; i < 3; i++) {   
			_points[i] = new CPoint();
		}
		// cancel checkPoints within the constructor of TwoPointFigure
		_x1 = x1; _y1 = y1;
		_x2 = x2; _y2 = y2;
		_popup = popup;
		filled = false;
	}
	/** �� �ﰢ���� _x2, _y2 �������� x,y ��ǥ�� �������� �����ϴ� �Լ��̴�.
	 * ������ redraw�� �� ���ȴ�.
	 */
	public boolean checkNear(int x,int y) {
		return false;
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		draw(g,Const.DRAWING,g);
		int minx = _points[0].x;
		int maxx = _points[0].x;
		int x = _points[1].x;
		if (minx > x) minx = x;
		if (maxx < x) maxx = x;
		x = _points[2].x;
		if (minx > x) minx = x;
		if (maxx < x) maxx = x;
		int miny = _points[0].y;
		int maxy = _points[0].y;
		int y = _points[1].y;
		if (miny > y) miny = y;
		if (maxy < y) maxy = y;
		y = _points[2].y;
		if (miny > y) miny = y;
		if (maxy < y) maxy = y;
		MySys.myFillPolygon(g,_points,3);
		if (expose) {
			_controller.setRepaint();
		}
	}
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public void drawDots(Graphics g) {
		_doted = true;
		_dots[0].x = _x1;
		_dots[0].y = _y1;
		_dots[1].x = _x2;
		_dots[1].y = _y2;
		MySys.myDrawDots(g,_dots,2);
	}
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		Rectangle tocheck = new Rectangle(point.x-4,point.y-4,8,8);
		return tocheck.contains(_x2,_y2);
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int mode,Graphics specialgc) {
		if (!_inCanvas) return;
		int w = (_x2 - _x1);
		int h = (_y2 - _y1);
		int length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		double angle;
		double theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(length * Math.cos(angle));
		int dy = (int)(length * Math.sin(angle));
		CPoint left = new CPoint();
		CPoint right = new CPoint();
		left.x = (_x1)+dx;
		left.y = (_y1)+dy;
		right.x = (_x1)-dx;
		right.y = (_y1)-dy;
		if (mode == Const.LOWLIGHTING && _doted == true) {
			_doted = false;
			_dots[0].x = _x1;
			_dots[0].y = _y1;
			_dots[1].x = _x2;
			_dots[1].y = _y2;
			MySys.myDrawDots(specialgc,_dots,2);
		}
		MySys.myDrawLine(g,_x2,_y2,left.x,left.y);
		MySys.myDrawLine(g,left.x,left.y,right.x,right.y);
		MySys.myDrawLine(g,right.x,right.y,_x2,_y2);
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 */
	public void drawing(Graphics g,boolean flag) {
		drawing(g,_x2,_y2,flag);
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 * ���� ���콺�� ���ο� ��ġ ���� �̿��Ͽ� rubberbanding �Ѵ�.
	 */
	public void drawing(Graphics g,int newx,int newy,boolean flag) {
		int w = (_x2 - _x1);
		int h = (_y2 - _y1);
		int length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		double angle;
		double theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(length * Math.cos(angle));
		int dy = (int)(length * Math.sin(angle));
		MySys.myDrawLine(g,_x2,_y2,(_x1)+dx,(_y1)+dy);
		MySys.myDrawLine(g,(_x1)+dx,(_y1)+dy,(_x1)-dx,(_y1)-dy);
		MySys.myDrawLine(g,(_x1)-dx,(_y1)-dy,_x2,_y2);
		_x2 = newx;
		_y2 = newy;
		w = _x2- _x1;
		h = _y2 - _y1;
		length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		dx = (int)(length * Math.cos(angle));
		dy = (int)(length * Math.sin(angle));
		MySys.myDrawLine(g,_x2,_y2,(_x1)+dx,(_y1)+dy);
		MySys.myDrawLine(g,(_x1)+dx,(_y1)+dy,(_x1)-dx,(_y1)-dy);
		MySys.myDrawLine(g,(_x1)-dx,(_y1)-dy,_x2,_y2);
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		int newx = _x2 + dx;
		int newy = _y2 + dy;
		int w = (_x2 - _x1);
		int h = (_y2 - _y1);
		int length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		double angle;
		double theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int deltax = (int)(length * Math.cos(angle));
		int deltay = (int)(length * Math.sin(angle));
		MySys.myDrawLine(g,_x2,_y2,(_x1)+deltax,(_y1)+deltay);
		MySys.myDrawLine(g,(_x1)+deltax,(_y1)+deltay,(_x1)-deltax,(_y1)-deltay);
		MySys.myDrawLine(g,(_x1)-deltax,(_y1)-deltay,_x2,_y2);
		_x1 = _x1 + dx;
		_y1 = _y1 + dy;
		_x2 = newx;
		_y2 = newy;
		w = _x2 - _x1;
		h = _y2 - _y1;
		length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		deltax = (int)(length * Math.cos(angle));
		deltay = (int)(length * Math.sin(angle));
		MySys.myDrawLine(g,_x2,_y2,(_x1)+deltax,(_y1)+deltay);
		MySys.myDrawLine(g,(_x1)+deltax,(_y1)+deltay,(_x1)-deltax,(_y1)-deltay);
		MySys.myDrawLine(g,(_x1)-deltax,(_y1)-deltay,_x2,_y2);
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMTRIANGLE; 
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		Triangle copied;
		if (ptr == null) {
			copied = new Triangle(_controller,0,0,null);
		} else {
			copied = (Triangle)ptr;
		}
		copied.filled = filled;
		return (super.born((Figure)copied));
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		makeRegion(_x1,_y1,_x2-_x1,_y2-_y1);
	}
	/** �� ��ü�� �߾ӿ� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public Point center() {
		int cx = ((_x2) - (_x1)) / 3 + (_x1);
		int cy = ((_y2) - (_y1)) / 3 + (_y1); 
		return new Point(cx,cy);
	}
	/** �ﰢ���� ���ΰ� ����ֵ��� �ﰢ���� �׸��� �Լ��̴�.
	 */
	public void drawEmpty(Graphics g,int style,Graphics fillg) {
		fill(fillg);
		draw(g,style,fillg);
	}
	/** �ﰢ���� ���ΰ� ä�� ������ �ﰢ���� �׸��� �Լ��̴�.
	 */
	public void fill(Graphics g) {
		if (!_inCanvas) return;
		int w = (_x2 - _x1);
		int h = (_y2 - _y1);
		int length = (int)(Math.sqrt((double)(w*w + h*h))/2./Math.sin(Math.PI/3.));
		double angle;
		double theta = (w!=0) ? Math.atan((double)h/(double)w) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)(length * Math.cos(angle));
		int dy = (int)(length * Math.sin(angle));
		CPoint data[] = new CPoint[3];
		for(int i = 0; i < 3; i++) {
			data[i] = new CPoint();
		}
		data[0].x = (_x2);
		data[0].y = (_y2);
		data[1].x = (_x1)+dx;
		data[1].y = (_y1)+dy;
		data[2].x = (_x1)-dx;
		data[2].y = (_y1)-dy;
		MySys.myFillPolygon(g,data,3);
	}
}
