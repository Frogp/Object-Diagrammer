package figure;

import java.awt.*;
import java.lang.*;
/** 이 클래스는 기본 표기법 중에서 마름모꼴을 표현하기 위한 것이다..
 */
public final 
	class Diamond extends TwoPointFigure {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 4513274562410642167L;
	/** 이 마름모꼴 객체의 영역을 만드는 함수이다.
	 */
	protected void makeRegion(int x,int y,int w,int h) {
		_points[0].x = x - 1;
		_points[0].y = y - 1;
		_points[1].x = x + w + 1;
		_points[1].y = y - 1;
		_points[2].x = x + w + 1;
		_points[2].y = y + h + 1;
		_points[3].x = x - 1;
		_points[3].y = y + h + 1;
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
		_maxRegion = CRgn.myPolygonRgn(_points,4);
		_points[0].x = x;
		_points[0].y = y;
		_points[1].x = x + w;
		_points[1].y = y;
		_points[2].x = x + w;
		_points[2].y = y + h;
		_points[3].x = x;
		_points[3].y = y + h;
		if (_region != null) CRgn.myDestroyRgn(_region);
		_region = CRgn.myPolygonRgn(_points,4);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		super.delete();
	}
	/** 생성자이다.
	 */
	public Diamond(GraphicController controller,int x,int y,Popup popup) {
		super(controller,x,y);
		_popup = popup;
	}
	/** 생성자이다.
	 */
	public Diamond(GraphicController controller,int x1,int y1,int x2,int y2,Popup popup){
		super(controller,x1,y1,x2,y2);
		_popup = popup;
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		draw(g,Const.DRAWING,g);
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		if (w == 0 || h == 0) return;
		g.fillRect(_x1+1,_y1+1,w-2,h-2);
		if (expose == true) {
			_controller.setRepaint();
		}
	}
	/** 이 마름모꼴의 내부가 비워지도록 그림을 그리는 함수이다.
	 */
	public void drawEmpty(Graphics g,int style,Graphics fillg) {
		fill(fillg);
		draw(g,style,fillg);
	}
	/** 이 마름모꼴의 내부가 채워지도록 그림을 그리는 함수이다.
	 */
	public void fill(Graphics g) {
		if (!_inCanvas) return;
		int width = _x2 - _x1;
		int height = _y2 - _y1;
		int dx = width/2;
		int dy = height/2;
		int x = _x1;
		int y = _y1;
		CPoint data[] = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			data[i] = new CPoint();
		}
		data[0].x = _x1 + dx;
		data[0].y = _y1;
		data[1].x = _x2;
		data[1].y = _y1 + dy;
		data[2].x = _x1 + dx;
		data[2].y = _y2;
		data[3].x = _x1;
		data[3].y = _y1 + dy;
		MySys.myFillPolygon(g,data,4);
	}
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		_doted = true;
		_dots[0].x = _x1;
		_dots[0].y = _y1;
		_dots[1].x = _x2;
		_dots[1].y = _y1;
		_dots[2].x = _x2;
		_dots[2].y = _y2;
		_dots[3].x = _x1;
		_dots[3].y = _y2;
		MySys.myDrawDots(g,_dots,4);
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
		int width = _x2 - _x1;
		int height = _y2 - _y1;
		int dx = width/2;
		int dy = height/2;
		int x = _x1;
		int y = _y1;
		if (mode == Const.LOWLIGHTING && _doted == true) {
			_doted = false;
			_dots[0].x = _x1;
			_dots[0].y = _y1;
			_dots[1].x = _x2;
			_dots[1].y = _y1;
			_dots[2].x = _x2;
			_dots[2].y = _y2;
			_dots[3].x = _x1;
			_dots[3].y = _y2;
			MySys.myDrawDots(specialgc,_dots,4);
		}
		MySys.myDrawLine(g,_x1+dx,_y1,
						 _x1,_y1+dy);
		MySys.myDrawLine(g,_x1,_y1+dy,
						 _x1+dx,_y2);
		MySys.myDrawLine(g,_x1+dx,_y2,
						 _x2,_y1+dy);
		MySys.myDrawLine(g,_x2,_y1+dy,
						 _x1+dx,_y1);
	}
	/** 마름모꼴의 내부에 십자가 선을 지우도록 그리는 함수이다.
	 */
	public void drawEmptyCross(Graphics g,int mode,Graphics erasegc) {
		if (!_inCanvas) return;
		int width = _x2 - _x1;
		int height = _y2 - _y1;
		int dx = width/2;
		int dy = height/2;
		MySys.myDrawLine(erasegc,
						 _x1+dx-1,_y1,_x1+dx-1,_y2);
		MySys.myDrawLine(erasegc,
						 _x1+dx,_y1,_x1+dx,_y2);
		MySys.myDrawLine(erasegc,
						 _x1+dx+1,_y1,_x1+dx+1,_y2);
		MySys.myDrawLine(erasegc,
						 _x1,_y1+dy-1,_x2,_y1+dy-1);	
		MySys.myDrawLine(erasegc,
						 _x1,_y1+dy,_x2,_y1+dy);	
		MySys.myDrawLine(erasegc,
						 _x1,_y1+dy+1,_x2,_y1+dy+1);	
		draw(g,mode,erasegc);
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
		int width = _x2 - _x1;
		int height = _y2 - _y1;
		int dx = width/2;
		int dy = height/2;
		MySys.myDrawLine(g,_x1+dx,_y1,
						 _x1,_y1+dy);
		MySys.myDrawLine(g,_x1,_y1+dy,
						 _x1+dx,_y2);
		MySys.myDrawLine(g,_x1+dx,_y2,
						 _x2,_y1+dy);
		MySys.myDrawLine(g,_x2,_y1+dy,
						 _x1+dx,_y1);
		_x2 = newx;
		_y2 = newy;
		width = _x2 - _x1;
		height = _y2 - _y1;
		dx = width/2;
		dy = height/2;
		MySys.myDrawLine(g,_x1+dx,_y1,
						 _x1,_y1+dy);
		MySys.myDrawLine(g,_x1,_y1+dy,
						 _x1+dx,_y2);
		MySys.myDrawLine(g,_x1+dx,_y2,
						 _x2,_y1+dy);
		MySys.myDrawLine(g,_x2,_y1+dy,
						 _x1+dx,_y1);
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		int newx = _x2 + dx;
		int newy = _y2 + dy;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		int deltax = width/2;
		int deltay = height/2;
		MySys.myDrawLine(g,_x1+deltax,_y1,
						 _x1,_y1+deltay);
		MySys.myDrawLine(g,_x1,_y1+deltay,
						 _x1+deltax,_y2);
		MySys.myDrawLine(g,_x1+deltax,_y2,
						 _x2,_y1+deltay);
		MySys.myDrawLine(g,_x2,_y1+deltay,
						 _x1+deltax,_y1);
		_x1 = _x1+dx;
		_y1 = _y1+dy;
		_x2 = newx;
		_y2 = newy;
		width = Math.abs(_x2 - _x1);
		height = Math.abs(_y2 - _y1);
		deltax = width/2;
		deltay = height/2;
		MySys.myDrawLine(g,_x1+deltax,_y1,
						 _x1,_y1+deltay);
		MySys.myDrawLine(g,_x1,_y1+deltay,
						 _x1+deltax,_y2);
		MySys.myDrawLine(g,_x1+deltax,_y2,
						 _x2,_y1+deltay);
		MySys.myDrawLine(g,_x2,_y1+deltay,
						 _x1+deltax,_y1);
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMDIAMOND;
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		Diamond copied;
		if (ptr == null) {
			copied = new Diamond(_controller,0,0,null);
		} else {
			copied = (Diamond)ptr;
		}
		return (super.born((Figure)copied));
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		super.checkPoints();
		makeRegion(_x1,_y1,_x2-_x1,
					_y2-_y1);
	}
}