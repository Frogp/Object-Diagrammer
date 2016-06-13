package figure;

import java.awt.*;
import java.io.*;
import java.awt.Point;
/** 이 클래스는 두개의 좌표 값을 갖는 그림 객체의 공통 인터페이스를 제공하는 
 * 클래스이다.
 */
public abstract 
	class TwoPointFigure extends Figure {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 7743269709786343370L;
	/** x1 좌표값
	 */
	public int _x1;
	/** y1 좌표값
	 */
	public int _x2;
	/** x2 좌표값
	 */
	public int _y1;
	/** y2 좌표값
	 */
	public int _y2;
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simpleDCPopup());
	}
	/** 생성자이다.
	 */
	protected TwoPointFigure(GraphicController controller,int x,int y) {
		super(controller);
		_points = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			_points[i] = new CPoint();
		}
		_dots = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			_dots[i] = new CPoint();
		}
		if (controller == null) return;
		_x1 = x;
		_y1 = y;
		_x2 = x;
		_y2 = y;
	}
	/** 생성자이다.
	 */
	protected TwoPointFigure(GraphicController controller,int x1,int y1,int x2,int y2) {
		super(controller);
		_points = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			_points[i] = new CPoint();
		}
		_dots = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			_dots[i] = new CPoint();
		}
		if (x1>x2) {
			int tmpx = x1; x1 = x2; x2 = tmpx;
		}
		if (y1>y2) {
			int tmpy = y1; y1 = y2; y2 = tmpy;
		}
		if (controller == null) return;
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
	}
	/** 두 좌표값을 서로 바꾸는 함수이다.
	 */
	protected void swapPoints() {
		int tx = _x1;
		int ty = _y1;
		_x1 = _x2;
		_y1 = _y2;
		_x2 = tx;
		_y2 = ty;
	}
	/** 이 객체의 중앙에 대한 좌표값을 구하는 함수이다.
	 */
	public Point center() {
		Point p = new Point((_x1 + _x2) / 2,(_y1 + _y2) / 2);
		return p;
	}
	/** 이 객체의 마지막 모서리에 대한 좌표값을 구하는 함수이다.
	 */
	public Point last() {
		Point p = new Point(_x2,_y2);
		return p;
	}
	/** 그림 객체의 마지막 좌표(x2,y2) 값이 인자에 명시된 좌표 값과 가까워지도록 설정하는 함수이다.
	 */
	public boolean checkNear(int x,int y) {
		int l1 = MySys.distance(x,y,_x1,_y1);
		int l2 = MySys.distance(x,y,_x2,_y2);
		if (l1 < l2) {
			swapPoints();
			return true;
		} else {
			return false;
		}	
	}
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
	 */
	public boolean checkInRegion(CRgn someregion) {
		return CRgn.checkInRegion(someregion,_x1,_y1,_x2,_y2);
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 이 함수를 통하여 GraphicController 객체의
	 * 최대 최소 좌표값에 반영된다.
	 */
	public void minMaxXY() {
		_controller.minmaxXY(_x1,_y1,_x2,_y2);
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 인자들 값에 반영되어 돌려진다.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		if (minP.x > _x1) minP.x = _x1;
		if (maxP.x < _x1) maxP.x = _x1;
		if (minP.y > _y1) minP.y = _y1;
		if (maxP.y < _y1) maxP.y = _y1;
		if (minP.x > _x2) minP.x = _x2;
		if (maxP.x < _x2) maxP.x = _x2;
		if (minP.y > _y2) minP.y = _y2;
		if (maxP.y < _y2) maxP.y = _y2;
	}
	/** 좌표만 이동 시키는 함수이다.
	 */
	public void moveCoord(int dx,int dy) {
		_x1 = _x1 + dx; _y1 = _y1 + dy;
		_x2 = _x2 + dx; _y2 = _y2 + dy;
	}
	/** 이 객체의 모서리 좌표, x1,y1,x2,y2의 값을 알아내는 함수이다.
	 */
	public void coords(Point p1,Point p2) {
		p1.x = _x1;
		p1.y = _y1;
		p2.x = _x2;
		p2.y = _y2;
	}
	/** 두 좌표 값을 설정하는 함수이다.
	 */
	public void setCoords(int x1,int y1,int x2,int y2) {
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		TwoPointFigure copied = (TwoPointFigure)ptr;
		copied._x1 = _x1;
		copied._y1 = _y1;
		copied._x2 = _x2;
		copied._y2 = _y2;
		return (super.born((Figure)copied));
	}
	/** 이 그림 객체의 최소 폭과 높이를 만족하는가 확인하는 함수이다.
	 */
	public boolean checkMinWH() {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		if (Math.abs(w) >= Const.FIGUREMINW && Math.abs(h) >= Const.FIGUREMINH) return(false);
		_controller.beep("TwoPointFigure.checkMinWH");
		return(true);
	}
	/** _x1, _y1 좌표값을 설정하는 함수이다.
	 */
	public void setXY1(int x,int y) {
		_x1 = x; _y1 = y;
	}
	/** _x2, _y2 좌표값을 설정하는 함수이다.
	 */
	public void setXY2(int x,int y) {
		_x2 = x; _y2 = y;
	}
	/** 그림 크기를 조절하기 위해 _x2, _y2 좌표값을 설정하는 함수이다.
	 */
	public void setXY2ForResize(int x,int y) {
		_x2 = x; _y2 = y;
	}
	/** 좌표 값을 새로 설정하는 함수이다.
	 */
	public void adjustXyValue(int oldx,int oldy,int newx,int newy) {
		if (_x1 == oldx && _y1 == oldy) {
			_x1 = newx; _y1 = newy;
		} else if (_x2 == oldx && _y2 == oldy) {
			_x2 = newx; _y2 = newy;
		}
	}
	/** 좌표의 위치를 순서대로 정령해주는 함수이다.
	 */
	public void checkPoints()
	{
		if (_x1>_x2) {
			int tmpx = _x1; _x1 = _x2; _x2 = tmpx;
		}
		if (_y1>_y2) {
			int tmpy = _y1; _y1 = _y2; _y2 = tmpy;
		}
	}

   	/** 두지점의 폭에 대한 값을 돌려준다. 항상 _x2가 _x1보다 크다는 것이
      * 보장되어 있어야 한다.
      * @return  폭의 정수값
  	  */
     public int getWidth()
     {
          return _x2 - _x1;
     }
	/** 두지점의 높이에 대한 값을 돌려준다. 항상 _y2가 _y1보다 크다는 것이
       * 보장되어 있어야 한다.
       * @return  높이의 정수값
  	  */
     public int getHeight()
     {
          return _y2 - _y1;
     }
}
