package figure;

import java.awt.*;
import java.lang.*;
import java.io.*;
/** �� Ŭ������ �⺻ ǥ��� �߿��� ���� ǥ���ϱ� ���� ���̴�.
 */
public 
	class Circle extends TwoPointFigure {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 5405103859417477916L;
	/** �� �� ��ü�� ������ ����� �Լ��̴�.
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
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		super.delete();
	}
	/** �������̴�.
	 */
	public Circle(GraphicController controller,int x,int y,Popup popup) {
		super(controller,x,y);
		filled = false;
		_popup = popup;
	}
	/** �������̴�.
	 */
	public Circle(GraphicController controller,int x1,int y1,int x2,int y2,Popup popup) {
		super(controller,x1,y1,x2,y2);
		filled = false;
		_popup = popup;
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		draw(g,Const.DRAWING,g);
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		if (w == 0 || h == 0) return;
		g.fillRect(_x1,_y1,w,h);
		if (expose == true) {
			_controller.dispatchPaintEvent(_x1,_y1,w,h);
		}
	}
	
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
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

	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		Rectangle tocheck = new Rectangle(point.x-4,point.y-4,8,8);
		return tocheck.contains(_x2,_y2);
	}

	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int mode,Graphics specialgc){
		if (!_inCanvas) return;
		int x,y;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		if (_x1 > _x2 ) x = _x2;
		else x = _x1;
		if (_y1 > _y2 ) y = _y2;
		else y = _y1;
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
		MySys.myDrawOval(g,x,y,width,height);
		if (filled) {
			MySys.myFillOval(g,x,y,width,height);
		}
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
		int x,y;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		if (_x1 > _x2 ) x = _x2;
		else x = _x1;
		if (_y1 > _y2 ) y = _y2;
		else y = _y1;
		MySys.myDrawOval(g,x,y,width,height);
		if (filled) {
			MySys.myFillOval(g,x,y,width,height);
		}
		if (_x1 > newx ) x = newx;
		else x = _x1;
		if (_y1 > newy ) y = newy;
		else y = _y1;
		width = Math.abs(newx - _x1);
		height =  Math.abs(newy - _y1);
		MySys.myDrawOval(g,x,y,width,height);
		if (filled) {
			MySys.myFillOval(g,x,y,width,height);
		}
		_x2 = newx;
		_y2 = newy;
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		int newx = _x2 + dx;
		int newy = _y2 + dy;
		int x,y;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		if (_x1 > _x2 ) x = _x2;
		else x = _x1;
		if (_y1 > _y2 ) y = _y2;
		else y = _y1;
		MySys.myDrawOval(g,x,y,width,height);
		if (filled) {
			MySys.myFillOval(g,x,y,width,height);
		}
		_x1 = _x1 + dx;
		_y1 = _y1 + dy;
		if (_x1 > newx ) x = newx;
		else x = _x1;
		if (_y1 > newy ) y = newy;
		else y = _y1;
		width = Math.abs(newx - _x1);
		height = Math.abs(newy - _y1);
		MySys.myDrawOval(g,x,y,width,height);
		if (filled) {
			MySys.myFillOval(g,x,y,width,height);
		}
		_x2 = newx;
		_y2 = newy;
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMCIRCLE; 
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		Circle copied;
		if (ptr == null) {
			copied = new Circle(null,0,0,null);
		} else {
			copied = (Circle)ptr;
		}
		copied.filled = filled;
		return (super.born((Figure)copied));
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		super.checkPoints();
		makeRegion(_x1,_y1,_x2-_x1,
					_y2-_y1);
	}
}