package figure;

import java.awt.*;
import java.io.*;
import java.awt.Point;
/** �� Ŭ������ �ΰ��� ��ǥ ���� ���� �׸� ��ü�� ���� �������̽��� �����ϴ� 
 * Ŭ�����̴�.
 */
public abstract 
	class TwoPointFigure extends Figure {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 7743269709786343370L;
	/** x1 ��ǥ��
	 */
	public int _x1;
	/** y1 ��ǥ��
	 */
	public int _x2;
	/** x2 ��ǥ��
	 */
	public int _y1;
	/** y2 ��ǥ��
	 */
	public int _y2;
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simpleDCPopup());
	}
	/** �������̴�.
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
	/** �������̴�.
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
	/** �� ��ǥ���� ���� �ٲٴ� �Լ��̴�.
	 */
	protected void swapPoints() {
		int tx = _x1;
		int ty = _y1;
		_x1 = _x2;
		_y1 = _y2;
		_x2 = tx;
		_y2 = ty;
	}
	/** �� ��ü�� �߾ӿ� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public Point center() {
		Point p = new Point((_x1 + _x2) / 2,(_y1 + _y2) / 2);
		return p;
	}
	/** �� ��ü�� ������ �𼭸��� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public Point last() {
		Point p = new Point(_x2,_y2);
		return p;
	}
	/** �׸� ��ü�� ������ ��ǥ(x2,y2) ���� ���ڿ� ��õ� ��ǥ ���� ����������� �����ϴ� �Լ��̴�.
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
	/** �� ��ü�� �־��� �����ȿ� ���ԵǴ°��� ��Ÿ���� �Լ��̴�.
	 */
	public boolean checkInRegion(CRgn someregion) {
		return CRgn.checkInRegion(someregion,_x1,_y1,_x2,_y2);
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� �� �Լ��� ���Ͽ� GraphicController ��ü��
	 * �ִ� �ּ� ��ǥ���� �ݿ��ȴ�.
	 */
	public void minMaxXY() {
		_controller.minmaxXY(_x1,_y1,_x2,_y2);
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� ���ڵ� ���� �ݿ��Ǿ� ��������.
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
	/** ��ǥ�� �̵� ��Ű�� �Լ��̴�.
	 */
	public void moveCoord(int dx,int dy) {
		_x1 = _x1 + dx; _y1 = _y1 + dy;
		_x2 = _x2 + dx; _y2 = _y2 + dy;
	}
	/** �� ��ü�� �𼭸� ��ǥ, x1,y1,x2,y2�� ���� �˾Ƴ��� �Լ��̴�.
	 */
	public void coords(Point p1,Point p2) {
		p1.x = _x1;
		p1.y = _y1;
		p2.x = _x2;
		p2.y = _y2;
	}
	/** �� ��ǥ ���� �����ϴ� �Լ��̴�.
	 */
	public void setCoords(int x1,int y1,int x2,int y2) {
		_x1 = x1;
		_y1 = y1;
		_x2 = x2;
		_y2 = y2;
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		TwoPointFigure copied = (TwoPointFigure)ptr;
		copied._x1 = _x1;
		copied._y1 = _y1;
		copied._x2 = _x2;
		copied._y2 = _y2;
		return (super.born((Figure)copied));
	}
	/** �� �׸� ��ü�� �ּ� ���� ���̸� �����ϴ°� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean checkMinWH() {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		if (Math.abs(w) >= Const.FIGUREMINW && Math.abs(h) >= Const.FIGUREMINH) return(false);
		_controller.beep("TwoPointFigure.checkMinWH");
		return(true);
	}
	/** _x1, _y1 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY1(int x,int y) {
		_x1 = x; _y1 = y;
	}
	/** _x2, _y2 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY2(int x,int y) {
		_x2 = x; _y2 = y;
	}
	/** �׸� ũ�⸦ �����ϱ� ���� _x2, _y2 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY2ForResize(int x,int y) {
		_x2 = x; _y2 = y;
	}
	/** ��ǥ ���� ���� �����ϴ� �Լ��̴�.
	 */
	public void adjustXyValue(int oldx,int oldy,int newx,int newy) {
		if (_x1 == oldx && _y1 == oldy) {
			_x1 = newx; _y1 = newy;
		} else if (_x2 == oldx && _y2 == oldy) {
			_x2 = newx; _y2 = newy;
		}
	}
	/** ��ǥ�� ��ġ�� ������� �������ִ� �Լ��̴�.
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

   	/** �������� ���� ���� ���� �����ش�. �׻� _x2�� _x1���� ũ�ٴ� ����
      * ����Ǿ� �־�� �Ѵ�.
      * @return  ���� ������
  	  */
     public int getWidth()
     {
          return _x2 - _x1;
     }
	/** �������� ���̿� ���� ���� �����ش�. �׻� _y2�� _y1���� ũ�ٴ� ����
       * ����Ǿ� �־�� �Ѵ�.
       * @return  ������ ������
  	  */
     public int getHeight()
     {
          return _y2 - _y1;
     }
}
