package figure;

import java.awt.*;
import java.lang.*;
import java.io.*;
/** 이 클래스는 기본 표기법 중에서 삼각형을 표현하기 위한 것이다..
 */
public 
	class Triangle extends TwoPointFigure {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -6090829103669198754L;
	/** 이 삼각형 객체의 영역을 만드는 함수이다.
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
	/** 이 삼각형 영역 보다 한 픽셀 면적이 넓은 영역을 만드는 함수이다. 
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
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		super.delete();
	}
	/** 삼각형을 그릴 수 있는 최소한의 폭과 높이를 갖는가를 확인하는 함수이다.
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
	/** 생성자이다.
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
	/** 생성자이다.
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
	/** 이 삼각형의 _x2, _y2 꼭지점이 x,y 좌표와 가깝도록 설정하는 함수이다.
	 * 도형을 redraw할 때 사용된다.
	 */
	public boolean checkNear(int x,int y) {
		return false;
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
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
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		_doted = true;
		_dots[0].x = _x1;
		_dots[0].y = _y1;
		_dots[1].x = _x2;
		_dots[1].y = _y2;
		MySys.myDrawDots(g,_dots,2);
	}
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantResize(CPoint point) {
		Rectangle tocheck = new Rectangle(point.x-4,point.y-4,8,8);
		return tocheck.contains(_x2,_y2);
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
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
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 */
	public void drawing(Graphics g,boolean flag) {
		drawing(g,_x2,_y2,flag);
	}
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 * 현재 마우스의 새로운 위치 값을 이용하여 rubberbanding 한다.
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
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
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
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMTRIANGLE; 
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
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
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		makeRegion(_x1,_y1,_x2-_x1,_y2-_y1);
	}
	/** 이 객체의 중앙에 대한 좌표값을 구하는 함수이다.
	 */
	public Point center() {
		int cx = ((_x2) - (_x1)) / 3 + (_x1);
		int cy = ((_y2) - (_y1)) / 3 + (_y1); 
		return new Point(cx,cy);
	}
	/** 삼각형의 내부가 비어있도록 삼각형을 그리는 함수이다.
	 */
	public void drawEmpty(Graphics g,int style,Graphics fillg) {
		fill(fillg);
		draw(g,style,fillg);
	}
	/** 삼각형의 내부가 채워 지도록 삼각형을 그리는 함수이다.
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
