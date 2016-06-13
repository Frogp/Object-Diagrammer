package figure;

import java.awt.*;
import java.lang.*;
/** �� Ŭ������ �⺻ ǥ��� �߿��� ��������� ǥ���ϱ� ���� ���̴�..
 */
public final 
	class Diamond extends TwoPointFigure {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 4513274562410642167L;
	/** �� ������� ��ü�� ������ ����� �Լ��̴�.
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
	public Diamond(GraphicController controller,int x,int y,Popup popup) {
		super(controller,x,y);
		_popup = popup;
	}
	/** �������̴�.
	 */
	public Diamond(GraphicController controller,int x1,int y1,int x2,int y2,Popup popup){
		super(controller,x1,y1,x2,y2);
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
		g.fillRect(_x1+1,_y1+1,w-2,h-2);
		if (expose == true) {
			_controller.setRepaint();
		}
	}
	/** �� ��������� ���ΰ� ��������� �׸��� �׸��� �Լ��̴�.
	 */
	public void drawEmpty(Graphics g,int style,Graphics fillg) {
		fill(fillg);
		draw(g,style,fillg);
	}
	/** �� ��������� ���ΰ� ä�������� �׸��� �׸��� �Լ��̴�.
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
	/** ��������� ���ο� ���ڰ� ���� ���쵵�� �׸��� �Լ��̴�.
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
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 */
	public void drawing(Graphics g,boolean flag) {
		drawing(g,_x2,_y2,flag);
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 * ���� ���콺�� ���ο� ��ġ ���� �̿��Ͽ� rubberbanding �Ѵ�.
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
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
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
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMDIAMOND;
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		super.checkPoints();
		makeRegion(_x1,_y1,_x2-_x1,
					_y2-_y1);
	}
}