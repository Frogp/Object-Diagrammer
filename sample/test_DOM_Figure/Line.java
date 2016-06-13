package figure;

import java.awt.*;
import java.lang.*;
import java.io.*;
/** �� Ŭ������ �ϳ��� ���п� ���� ������ �����ϰ� ���� ���񽺸� �����ϴ� Ŭ�����̴�.
 */
public final 
	class Line extends TwoPointFigure {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -3830308108013171056L;
	/** ȭ��ǥ�� ����
	 */
	protected int _direct;
	/** ORDINARY or STRAIGHT */
	protected int _type;
	/** SOLID or DASHED */
	protected int _style;
	/* UNDEFINED, NORTH, EAST, SOUTH, NORTH */
	protected int _orient;
	/** ������ ���� */
	protected double _slope;
	/* ȭ��ǥ ��� */
	public int head;
	/** ������ ������ �ڹٲٴ� �Լ��̴�.
	 */
	public static int invertOrient(int orient)
	{
		if (orient == Const.NORTH) {
			return Const.EAST;
		} else if (orient == Const.EAST) {
			return Const.NORTH;
		} else /* UNDEFINED */ {
			return Const.UNDEFINED;
		}
	}
	/** ȭ��ǥ���� ��ĥ�ϴ� �Լ��̴�.
	 */
	private void fillArrow(Graphics g,int x, int y,int dx1,int dy1,int dx2,int dy2)
	{
		CPoint data[] = new CPoint[3];
		for(int i = 0; i < 3; i++) {
			data[i] = new CPoint();
		}
		data[0].x = x;
		data[0].y = y;
		data[1].x = x + dx1;
		data[1].y = y + dy1;
		data[2].x = x + dx2;
		data[2].y = y + dy2;
		MySys.myFillPolygon(g,data,3);
	}
	/** ȭ��ǥ�� (_x2,_y2) ��ġ�� �׸��� �Լ��̴�.
	 */
	private void drawHead(Graphics g) {
		if (_direct == Const.NODIR || _direct == Const.INVERTDIR) return;
		if (head == Const.HEADNONE) return;
		int arrowlength = _controller.arrowLength();
		if (head == Const.HEADARROW1) {
			double deltax = (double)(_x2 - _x1);
			double deltay = (double)(_y2 - _y1);
			double theta;
			if (deltax == 0) {
				theta = MySys.sign(deltay)*Math.PI/2.;
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
	}
	/** ȭ��ǥ�� (_x1,_y1) ��ġ�� �׸��� �Լ��̴�.
	 */
	private void drawTail(Graphics g) {
		if (_direct == Const.NODIR || _direct == Const.NORMALDIR) return;
		if (head == Const.HEADNONE) return;
		int arrowlength = _controller.arrowLength();
		if (head == Const.HEADARROW1) {
			double deltax = (double)(_x1 - _x2);
			double deltay = (double)(_y1 - _y2);
			double theta;
			if (deltax == 0) {
				theta = MySys.sign(deltay)*Math.PI/2.;
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
				fillArrow(g,_x1,_y1,dx1,dy1,dx2,dy2);
			} else {
				fillArrow(g,_x1,_y1,-dx1,-dy1,-dx2,-dy2);
			}
		}
	}
	/** �� ������ ������ ����� �Լ��̴�.
	 */
	protected void makeRegion(int x,int y,int w,int h) {
		int regionlength = _controller.regionLength();
		x = x;
		y = y;
		double angle;
		double theta = (w!=0) ? Math.atan((double)(h)/(double)(w)) : MySys.sign(h)*Math.PI/2.;
		if (theta < 0) theta = theta + 2 * Math.PI;
		angle = (theta + Math.PI / 2.);
		int dx = (int)((regionlength+2) * Math.cos(angle));
		int dy = (int)((regionlength+2) * Math.sin(angle));
		_points[0].x = x + dx;
		_points[0].y = y + dy;
		_points[1].x = x - dx;
		_points[1].y = y - dy;
		_points[2].x = x + w - dx;
		_points[2].y = y + h - dy;
		_points[3].x = x + w + dx;
		_points[3].y = y + h + dy;
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
		_maxRegion = CRgn.myPolygonRgn(_points,4);
		dx = (int)(regionlength * Math.cos(angle));
		dy = (int)(regionlength * Math.sin(angle));
		_points[0].x = x + dx;
		_points[0].y = y + dy;
		_points[1].x = x - dx;
		_points[1].y = y - dy;
		_points[2].x = x + w - dx;
		_points[2].y = y + h - dy;
		_points[3].x = x + w + dx;
		_points[3].y = y + h + dy;
		if (_region != null) CRgn.myDestroyRgn(_region);
		_region = CRgn.myPolygonRgn(_points,4);
	}
	/** ������ �ݴ�� �ٲٴ� �Լ��̴�.
	 */
	protected void invertDir() {
		if (_direct == Const.NODIR || _direct == Const.BIDIR) return;
		if (_direct == Const.NORMALDIR)
			_direct = Const.INVERTDIR;
		else
			_direct = Const.NORMALDIR;
	}
	/** ���ڿ� ��õ� ���а� ���� ������ �պ��� �� ������ �����ϴ� �Լ��̴�.
	 */
	protected void adjustDirs(Line second) {
		int dir = _direct;
		if (dir == Const.NORMALDIR) {
			setDir(Const.NODIR,head);
			second.setDir(Const.NORMALDIR,head);
		} else if (dir == Const.BIDIR) {
			setDir(Const.INVERTDIR,head);
			second.setDir(Const.NORMALDIR,head);
		}
	}
	/**
	 * �� �Լ��� �Ҹ����� ����̴�. Java���� �Ҹ��ڰ� �ʿ����� ������
	 * ������ �Ҹ����� ������μ� ����Ÿ ����� ���� reset��Ű�� ���� ������ ��찡 �ִ�.
	 */
	public void delete() {
		super.delete();
	}
	/** ��ü ���� �ÿ� ���Ǵ� �������̴�.
	 */
	public Line(GraphicController controller,int x,int y,Popup popup,int type) {
		super(controller,x,y);
		_popup = popup;
		_direct = Const.NODIR;
		_type = type;
		_style = Const.SOLID;
		_orient = Const.UNDEFINED;
		head = Const.HEADNONE;
		_slope = 0;
	}
	/** �Ϲ������� ���Ǵ� �������̴�.
	 */
	public Line(GraphicController controller,int x1,int y1,int x2,int y2,Popup popup, int type){
		super(controller,x1,y1,x2,y2);
		_x1 = x1; _y1 = y1;
		_x2 = x2; _y2 = y2;
		_popup = popup;
		_direct = Const.NODIR;
		_type = type;
		_style = Const.SOLID;
		_orient = Const.UNDEFINED;
		head = Const.HEADNONE;
		_slope = 0;
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		draw(g,Const.DRAWING,g);
	}
	/** �� ������ ������ ��ǥ(x2,y2) ���� ���ڿ� ��õ� ��ǥ ���� ����������� �����ϴ� �Լ��̴�.
	 */
	public boolean checkNear(int x,int y) {
		if (super.checkNear(x,y)) {
			invertDir();
			return true;
		} else {
			return false;
		}
	}
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public void drawDots(Graphics g) {
		_doted = true;
		_dots[0].x = _x1;
		_dots[0].y = _y1;
		_dots[1].x = (_x1 + _x2)/ 2;
		_dots[1].y = (_y1 + _y2)/ 2;
		_dots[2].x = _x2;
		_dots[2].y = _y2;
		MySys.myDrawDots(g,_dots,3);
	}
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		Rectangle tocheck = new Rectangle(point.x-4,point.y-4,8,8);
		if (tocheck.contains(_x1,_y1) == true)
			return true;
		if (tocheck.contains(_x2,_y2) == true)
			return true;
		return false;
	}
	/** ���� ���콺�� �������� �� �� ��ü�� �����̱� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantMove(CPoint point) {
		Rectangle tocheck = new Rectangle(point.x-4,point.y-4,8,8);
		int cx = (_x1 + _x2) / 2;
		int cy = (_y1 + _y2) / 2;
		return tocheck.contains(cx,cy);
	}
	/** �� ������ �������� �׷��ִ� �Լ��̴�.
	 */
	public void drawDashed(Graphics g,int mode,Graphics specialgc) {
		if (!_inCanvas) return;

		if (mode == Const.LOWLIGHTING && _doted == true) {
			_doted = false;
			_dots[0].x = _x1;
			_dots[0].y = _y1;
			_dots[1].x = (_x1 + _x2)/ 2;
			_dots[1].y = (_y1 + _y2)/ 2;
			_dots[2].x = _x2;
			_dots[2].y = _y2;
			MySys.myDrawDots(specialgc,_dots,3);
		}

		MySys.myDrawDashedLine(g,_x1,_y1,_x2,_y2);

		drawHead(g);
		drawTail(g);
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int mode,Graphics specialgc) {
		if (!_inCanvas) return;

		if (mode == Const.LOWLIGHTING && _doted == true) {
			_doted = false;
			_dots[0].x = _x1;
			_dots[0].y = _y1;
			_dots[1].x = (_x1 + _x2)/ 2;
			_dots[1].y = (_y1 + _y2)/ 2;
			_dots[2].x = _x2;
			_dots[2].y = _y2;
			MySys.myDrawDots(specialgc,_dots,3);
		}

		MySys.myDrawLine(g,_x1,_y1,_x2,_y2);

		drawHead(g);
		drawTail(g);
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 */
	public void drawing(Graphics g,boolean flag) {
		drawing(g,_x2,_y2,flag);
	}
	/** �� ���а� �پ��ִ� �ٸ� ������ ������ �� ���� ���󰡵��� ��ǥ�� �̵���Ű�� �Լ��̴�.
	 */
	public void following(int newx,int newy) {
		if (_type == Const.ORDINARY) {
			setXY2(newx,newy);
		} else /* STRAIGHT */ {
			if (_orient == Const.NORTH) {
				_x1 = newx;
			} else if (_orient == Const.EAST) {
				_y1 = newy;
			}
			_x2 = newx;
			_y2 = newy;
		}	
	}
	/** �� ���а� �پ��ִ� �ٸ� ������ ������ �� ���� ���󰡵��� rubberbanding �ϴ� �Լ��̴�.
	 */
	public void following(Graphics g,int newx,int newy) {
		if (_type == Const.ORDINARY) {
			drawing(g,newx,newy,true);
		} else /* STRAIGHT */ {
			MySys.myDrawLine(g,_x1,_y1,_x2,_y2);
			drawHead(g);
			drawTail(g);
			if (_orient == Const.NORTH) {
				_x1 = newx;
			} else if (_orient == Const.EAST) {
				_y1 = newy;
			}
			MySys.myDrawLine(g,_x1,_y1,newx,newy);
			_x2 = newx;
			_y2 = newy;
			drawHead(g);
			drawTail(g);
		}	
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 * ���� ���콺�� ���ο� ��ġ ���� �̿��Ͽ� rubberbanding �Ѵ�.
	 */
	public void drawing(Graphics g,int newx,int newy,boolean flag) {
		double deltax = (double)Math.abs(newx - _x1);
		double deltay = (double)Math.abs(newy - _y1);
		if (_type == Const.ORDINARY) {
			if ((flag == false) && (deltax != 0) && (deltay != 0)) {
				if (deltay < 0.05*deltax) {
					newy = _y1;
				}
				if (deltax < 0.05*deltay) {
					newx = _x1;
				}
			}
		} else if (_type == Const.STRAIGHT) {
			if (_orient == Const.NORTH) {
				newx = _x1;
			} else if (_orient == Const.EAST) {
				newy = _y1;
			} else if ((deltax != 0) && (deltay != 0)) {
				if (deltax > deltay) {
					newy = _y1;
				} else {
					newx = _x1;
				}
			}
		}
		MySys.myDrawLine(g,_x1,_y1,_x2,_y2);
		drawHead(g);
		drawTail(g);
		MySys.myDrawLine(g,_x1,_y1,newx,newy);
		_x2 = newx;
		_y2 = newy;
		drawHead(g);
		drawTail(g);
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		int newx = _x2 + dx;
		int newy = _y2 + dy;
		MySys.myDrawLine(g,_x1,_y1,_x2,_y2);
		drawHead(g);
		drawTail(g);
		_x1 = _x1 + dx;
		_y1 = _y1 + dy;
		MySys.myDrawLine(g,_x1,_y1,newx,newy);
		_x2 = newx;
		_y2 = newy;
		drawHead(g);
		drawTail(g);
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMLINE; 
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		Line copied;
		if (ptr == null) {
			copied = new Line(_controller,0,0,null,_type);
		} else {
			copied = (Line)ptr;
		}
		copied._slope = _slope;
		copied._direct = _direct;
		copied._type = _type;
		copied._style = _style;
		copied._orient = _orient;
		copied.head = head;
		return (super.born((Figure)copied));
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		makeRegion(_x1,_y1,_x2-_x1,_y2-_y1);
	}
	/** �� ������ ������ ����� ���� ���� ������ ����� �Լ��̴�.
	 */
	public void makeSmallRegion() {
		int x = _x1;
		int y = _y1;
		int w = _x2-_x1;
		int h = _y2-_y1;
		x = x;
		y = y;
		if (_orient == Const.NORTH) {
			if (Math.abs(h) < 2) {
				if (_region != null) CRgn.myDestroyRgn(_region);
				_region = CRgn.myCreateRgn();
				if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
				_maxRegion = CRgn.myCreateRgn();
				return;
			}
			_points[0].x = x - 1;
			_points[0].y = y + MySys.sign(h)*1;
			_points[1].x = x + 1;
			_points[1].y = y + MySys.sign(h)*1;
			_points[2].x = x + 1;
			_points[2].y = y + h - MySys.sign(h)*1;
			_points[3].x = x - 1;
			_points[3].y = y + h - MySys.sign(h)*1;
			if (_region != null) CRgn.myDestroyRgn(_region);
			_region = CRgn.myPolygonRgn(_points,4);
			_points[0].x = x - 2;
			_points[0].y = y + MySys.sign(h)*1;
			_points[1].x = x + 2;
			_points[1].y = y + MySys.sign(h)*1;
			_points[2].x = x + 2;
			_points[2].y = y + h - MySys.sign(h)*1;
			_points[3].x = x - 2;
			_points[3].y = y + h - MySys.sign(h)*1;
			if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
			_maxRegion = CRgn.myPolygonRgn(_points,4);
		} else if (_orient == Const.EAST) {
			if (Math.abs(w) < 2) {
				if (_region != null) CRgn.myDestroyRgn(_region);
				_region = CRgn.myCreateRgn();
				if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
				_maxRegion = CRgn.myCreateRgn();
				return;
			}
			_points[0].x = x + MySys.sign(w)*1;
			_points[0].y = y - 1;
			_points[1].x = x + w - MySys.sign(w)*1;
			_points[1].y = y - 1;
			_points[2].x = x + w - MySys.sign(w)*1;
			_points[2].y = y + 1;
			_points[3].x = x + MySys.sign(w)*1;
			_points[3].y = y + 1;
			if (_region != null) CRgn.myDestroyRgn(_region);
			_region = CRgn.myPolygonRgn(_points,4);
			_points[0].x = x + MySys.sign(w)*1;
			_points[0].y = y - 2;
			_points[1].x = x + w - MySys.sign(w)*1;
			_points[1].y = y - 2;
			_points[2].x = x + w - MySys.sign(w)*1;
			_points[2].y = y + 2;
			_points[3].x = x + MySys.sign(w)*1;
			_points[3].y = y + 2;
			if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
			_maxRegion = CRgn.myPolygonRgn(_points,4);
		} else {
			if (_region != null) CRgn.myDestroyRgn(_region);
			_region = CRgn.myCreateRgn();
			if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
			_maxRegion = CRgn.myCreateRgn();
		}
	}
	/** �� �׸� ��ü�� �ּ� ���� ���̸� �����ϴ°� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean checkMinWH() {
		int len = length();
		if (len >= Const.FIGUREMINW) return(false);
		_controller.beep("Line.checkMinWH");
		return(true);
	}
	/** �� ��ü�� ������ ��ü�ΰ� Ȯ���ϴ� �Լ��̴�. 
	 */
	public boolean isObsolete() {
		if (_x1 == _x2 && _y1 == _y2) {
			return true;
		} else {
			return false;
		}
	}
	/** ������ ���� �� ��ǥ���� ���� �ٲٴ� �Լ��̴�.
	 */
	public void swapPoints() {
		super.swapPoints();
		//invertDir(); strange in java
	}
	/** ����Ÿ ��� _style�� ���� �б� access �Լ��̴�.
	 */
	public int style() {
		return _style;
	}
	/** �� ������ ���ڿ� ��õ� ���а� ��� �ʿ��� �����°��� �˾Ƴ��� �Լ��̴�.
	 */
	public int whereToMeet(Line line) {
		if (_x1 == line._x1 && _y1 == line._y1) {
			return Const.ATBEFORE;
		} else if (_x1 == line._x2 && _y1 == line._y2) {
			return Const.ATBEFORE;
		} else if (_x2 == line._x1 && _y2 == line._y1) {
			return Const.ATAFTER;
		} else if (_x2 == line._x2 && _y2 == line._y2) {
			return Const.ATAFTER;
		} else {
			return Const.ATNOWHERE;
		}
	}
	/** �� ������ ���ڿ� ��õ� ��ǥ�� �����°� �˻��ϴ� �Լ��̴�.
	 */
	public boolean doesMeet(int x,int y) {
		if ( (_x1 == x && _y1 == y) ||
			 (_x2 == x && _y2 == y) )
			return true;
		else
			return false;
	}
	/** �� ������ ���ڿ� ��õ� ���а� �������°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean doesCross(Line that) {
		Line north;
		Line east;
		if (_orient == Const.NORTH) {
			north = this;
			east = that;
		} else {
			north = that;
			east = this;
		}
		if (east._x1 < east._x2) {
			if (north._x1 < east._x1) return false;
			if (north._x1 > east._x2) return false;
		} else if (east._x1 > east._x2) {
			if (north._x1 < east._x2) return false;
			if (north._x1 > east._x1) return false;
		}
		if (north._y1 < north._y2) {
			if (east._y1 < north._y1) return false;
			if (east._y1 > north._y2) return false;
		} else if (north._y1 > north._y2) {
			if (east._y1 < north._y2) return false;
			if (east._y1 > north._y1) return false;
		}
		return true;	
	}
	/** �� ������ ���ڿ� ��õ� ���а� �պ��� �� �ִ°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean checkToMerge(Line node) {
		int head1 = head;
		int head2 = node.head;
		int orient1 = orient();
		int orient2 = node.orient();
		if (orient1 != Const.UNDEFINED && orient2 != Const.UNDEFINED) {
			if (orient1 == orient2) return true;
			else return false;
		}
		if (_x1 == _x2 && node._x1 == node._x2) return true;
		if (_type == Const.STRAIGHT) return false;
		double slope1 = slope();
		double slope2 = node.slope();
		double ratio;
		ratio = (slope1 - slope2);
		double tolerance = 0.02;
		if (ratio > -tolerance && ratio < tolerance)
			return true;
		else
			return false;
	}
	/** ������ ���̸� ���ϴ� �Լ��̴�.
	 */
	public int length() {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		int len;
		if ( w == 0 && h == 0 ) len = 0;
		else if ( w == 0 ) len = Math.abs(h);
		else if ( h == 0 ) len = Math.abs(w);
		else len = (int)(Math.sqrt((double)(w*w + h*h)));
		return len;
	}
	/** ������ ���⸦ ���ϴ� �Լ��̴�.
	 */
	public double slope() {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		if(w != 0){
			_slope = Math.atan((double)h/(double)w);
		}else {
			_slope = MySys.sign(h)*Math.PI/2.;
		}
		return _slope;
	}
	/** �� ������ ���ڿ� ��õ� ���а� �պ��ϴ� �Լ��̴�.
	 */
	public void merge(Line node) {
		if (_x2 == node._x1 && _y2 == node._y1) {
			if (_direct == Const.BIDIR || node._direct == Const.BIDIR ||
				(_direct == Const.INVERTDIR && node._direct == Const.NORMALDIR)) {
				_direct = Const.BIDIR;
			} else if ((_direct == Const.NODIR && node._direct == Const.NODIR) ||
					   (_direct == Const.NORMALDIR && node._direct == Const.INVERTDIR)) {
				_direct = Const.NODIR;
			} else if (_direct == Const.NORMALDIR || node._direct == Const.NORMALDIR) {
				_direct = Const.NORMALDIR;
			} else {
				_direct = Const.INVERTDIR;
			}
			_x2 = node._x2;
			_y2 = node._y2;
		} else if (_x2 == node._x2 && _y2 == node._y2) {
			if (_direct == Const.BIDIR || node._direct == Const.BIDIR ||
				(_direct == Const.INVERTDIR && node._direct == Const.INVERTDIR)) {
				_direct = Const.BIDIR;
			} else if ((_direct == Const.NODIR && node._direct == Const.NODIR) ||
					   (_direct == Const.NORMALDIR && node._direct == Const.NORMALDIR)) {
				_direct = Const.NODIR;
			} else if (_direct == Const.NORMALDIR || 
					   (_direct == Const.NODIR && node._direct == Const.INVERTDIR)) {
				_direct = Const.NORMALDIR;
			} else {
				_direct = Const.INVERTDIR;
			}
			_x2 = node._x1;
			_y2 = node._y1;
		} else if (_x1 == node._x1 && _y1 == node._y1) {
			if (_direct == Const.BIDIR || node._direct == Const.BIDIR ||
				(_direct == Const.NORMALDIR && node._direct == Const.NORMALDIR)) {
				_direct = Const.BIDIR;
			} else if ((_direct == Const.NODIR && node._direct == Const.NODIR) ||
					   (_direct == Const.INVERTDIR && node._direct == Const.INVERTDIR)) {
				_direct = Const.NODIR;
			} else if (_direct == Const.NORMALDIR || 
					   (_direct == Const.NODIR && node._direct == Const.INVERTDIR)) {
				_direct = Const.NORMALDIR;
			} else {
				_direct = Const.INVERTDIR;
			}
			_x1 = node._x2;
			_y1 = node._y2;
		} else if (_x1 == node._x2 && _y1 == node._y2) {
			if (_direct == Const.BIDIR || node._direct == Const.BIDIR ||
				(_direct == Const.NORMALDIR && node._direct == Const.INVERTDIR)) {
				_direct = Const.BIDIR;
			} else if ((_direct == Const.NODIR && node._direct == Const.NODIR) ||
					   (_direct == Const.INVERTDIR && node._direct == Const.NORMALDIR)) {
				_direct = Const.NODIR;
			} else if (_direct == Const.NORMALDIR || 
					   (_direct == Const.NODIR && node._direct == Const.NORMALDIR)) {
				_direct = Const.NORMALDIR;
			} else {
				_direct = Const.INVERTDIR;
			}
			_x1 = node._x1;
			_y1 = node._y1;
		}
		if (head == Const.HEADARROW1 || node.head == Const.HEADARROW1)
			head = Const.HEADARROW1;
	}
	/** ���п� ��ġ�� ȭ��ǥ ������ �ٲٴ� �Լ��̴�.
	 */
	public void toggleHead(boolean uniflag,int popupX,int popupY) {
		head = Const.HEADARROW1;
		if (uniflag) {
			int x1 = (int)(_x1);
			int y1 = (int)(_y1);
			int x2 = (int)(_x2);
			int y2 = (int)(_y2);
			int dist1 = MySys.distance(popupX,popupY,x1,y1);
			int dist2 = MySys.distance(popupX,popupY,x2,y2);
			if (dist1 > dist2) {
				_direct = _direct ^ Const.NORMALDIR;
			} else {
				_direct = _direct ^ Const.INVERTDIR;
			}
		} else {
			_direct = _direct ^ Const.BIDIR;
		}
		if (_direct == Const.NODIR) head = Const.HEADNONE;
	}
	/** ����Ÿ ��� _orient�� ���� �б� access �Լ��̴�.
	 */
	public int orient() {
		return _orient;
	}
	/** ����Ÿ ��� _direct�� ���� ���� access �Լ��̴�.
	 */
	public void setDir(int dir,int headtype) {
		_direct = dir;
		head = headtype;
	}
	/** ���� ����� fork�� �� ��� �� ������ ������ ��ġ�� ���ϴ� �Լ��̴�.
	 */
	public Line calcForkPoint(int popupX,int popupY,Point newPoint,BoolVar swaped,boolean originalfork) {
		swaped.v = false;
		int x1 = (int)(_x1);
		int y1 = (int)(_y1);
		int x2 = (int)(_x2);
		int y2 = (int)(_y2);
		if (x1 == popupX && y1 == popupY) {
			swapPoints();
			newPoint.x = popupX;
			newPoint.y = popupY;
			swaped.v = true;
			return null;
		}
		if (x2 == popupX && y2 == popupY) {
			newPoint.x = popupX;
			newPoint.y = popupY;
			return null;
		}
		int dist = MySys.distance(popupX,popupY,x1,y1);
		int forktolerance;
		if (originalfork == false) {
			forktolerance = 0;
		} else {
			forktolerance = Const.FORKTOLERANCE;
		}
		if (dist <= forktolerance) {
			swapPoints();
			newPoint.x = x1;
			newPoint.y = y1;
			swaped.v = true;
			return null;
		}
		dist = MySys.distance(popupX,popupY,x2,y2);
		if (dist <= forktolerance) {
			newPoint.x = x2;
			newPoint.y = y2;
			return null;
		}
		if (_orient == Const.NORTH) {
			newPoint.x = x1;
			newPoint.y = popupY;
		} else if (_orient == Const.EAST) {
			newPoint.x = popupX;
			newPoint.y = y2;
		} else if (x1 == x2) {
			newPoint.x = x1;
			newPoint.y = popupY;
		} else {
			double s = slope();
			double abss;
			if (s < 0) abss = s + 2 * Math.PI;
			else abss = s;
			if ((abss >= 0 && abss <= Math.PI/4.) ||
				(abss >= Math.PI*3./4. && abss <= Math.PI*5./4.) ||
				(abss >= Math.PI*7./4.)) {
				newPoint.x = popupX;
				newPoint.y = y1 + (int)((popupX-x1)*Math.tan(s));
			} else {
				newPoint.x = x1 + (int)((popupY-y1)*Math.tan(Math.PI/2.-s));
				newPoint.y = popupY;
			}
		}
		Line newline = new Line(_controller,newPoint.x,newPoint.y,x2,y2,null,Const.SOLID);
		newline._style = _style;
		newline._orient = _orient;
		_x2 = newPoint.x;
		_y2 = newPoint.y;
		adjustDirs(newline);
		return newline;
	}
	/** �� ������ ���ڿ� ��õ� ���а� �������� �˻��ϴ� �Լ��̴�.
	 */
	public boolean checkIfSame(Line line) {
		if (_x1 == line._x1 && _y1 == line._y1 && 
			_x2 == line._x2 && _y2 == line._y2) {
			return true;
		} else if (_x1 == line._x2 && _y1 == line._y2 && 
				   _x2 == line._x1 && _y2 == line._y1) {
			return true;
		} else {
			return false;
		}
	}
	/** ������ ���⼺�� ��Ʈ�ϴ� �Լ��̴�.
	 */
	public int setOrient(int orient) {
		if (_type == Const.ORDINARY) return Const.UNDEFINED;
		if (orient == Const.RESETORIENT) {
			if (isObsolete()) {
				_orient = Const.UNDEFINED;
			} else if (_x1 == _x2) {
				_orient = Const.NORTH;
			} else if (_y1 == _y2) {
				_orient = Const.EAST;
			} else {
				_orient = Const.UNDEFINED;
			}
		} else {
			_orient = orient;
		}
		return _orient;
	}
	/** �� ������ ���ڿ� ��õ� ������ �����ϴ°� �˻��ϴ� �Լ��̴�.
	 */
	public boolean incl(Line aline,Point point) {
		if (_orient != aline._orient) return false;
		int x1 = (int)(_x1);
		int y1 = (int)(_y1);
		int x2 = (int)(_x2);
		int y2 = (int)(_y2);
		if (_orient == Const.NORTH) {
			if (x1 != point.x || x2 != point.x) return false;
			if (y1 > y2) {
				if (y1 > point.y && point.y > y2) return true;
				else return false;
			} else if (y1 < y2) {
				if (y1 < point.y && point.y < y2) return true;
				else return false;
			} else {
				return false;
			}
		} else if (_orient == Const.EAST) {
			if (y1 != point.y || y2 != point.y) return false;
			if (x1 > x2) {
				if (x1 > point.x && point.x > x2) return true;
				else return false;
			} else if (x1 < x2) {
				if (x1 < point.x && point.x < x2) return true;
				else return false;
			} else {
				return false;
			}
		} else {
			if (_type == Const.STRAIGHT) return false;
			if ((x1 == x2) && (aline._x1 == aline._x2)) {
				if (x1 != point.x || x2 != point.x) return false;
				if (y1 > y2) {
					if (y1 >= point.y && point.y >= y2) return true;
					else return false;
				} else {
					if (y1 <= point.y && point.y <= y2) return true;
					else return false;
				}
			}
			if ((point.x == x1) && (point.y == y1)) return true;
			if ((point.x == x2) && (point.y == y2)) return true;
			double slope = _slope;
			double absslope;
			if (slope < 0) absslope = slope + 2 * Math.PI;
			else absslope = slope;
			if ((absslope >= 0 && absslope <= Math.PI/4.) ||
				(absslope >= Math.PI*3./4. && absslope <= Math.PI*5./4.) ||
				(absslope >= Math.PI*7./4.)) {
				int calcY = y1 + (int)((point.x-x1)*Math.tan(slope));
				if (Math.abs(calcY-point.y) <= 1) {
					if (x1 > x2) {
						if (x1 < point.x || point.x < x2) return false;
					} else {
						if (x1 > point.x || point.x > x2) return false;
					}
					if (y1 > y2) {
						if (y1 < point.y || point.y < y2) return false;
					} else {
						if (y1 > point.y || point.y > y2) return false;
					}
					point.y = calcY;
					aline._y2 = calcY;
					return true;
				}
			} else {
				int calcX = x1 + (int)((point.y-y1)*Math.tan(Math.PI/2.-slope));
				if (Math.abs(calcX-point.x) <= 1) {
					if (x1 > x2) {
						if (x1 < point.x || point.x < x2) return false;
					} else {
						if (x1 > point.x || point.x > x2) return false;
					}
					if (y1 > y2) {
						if (y1 < point.y || point.y < y2) return false;
					} else {
						if (y1 > point.y || point.y > y2) return false;
					}
					point.x = calcX;
					aline._x2 = calcX;
					return true;
				}
			}
			return false;
		}
	}
	/** �� ������ ���ڿ� ��õ� ��ǥ�� �����ϴ°� �˻��ϴ� �Լ��̴�.
	 */
	public boolean incl(int x,int y) {
		int x1 = (int)(_x1);
		int y1 = (int)(_y1);
		int x2 = (int)(_x2);
		int y2 = (int)(_y2);
		if (_orient == Const.NORTH) {
			if (x1 != x || x2 != x) return false;
			if (y1 > y2) {
				if (y1 >= y && y >= y2) return true;
				else return false;
			} else /* y1 <= y2 */ {
				if (y1 <= y && y <= y2) return true;
				else return false;
			}
		} else if (_orient == Const.EAST) {
			if (y1 != y || y2 != y) return false;
			if (x1 > x2) {
				if (x1 >= x && x >= x2) return true;
				else return false;
			} else /* x1 <= x2 */ {
				if (x1 <= x && x <= x2) return true;
				else return false;
			}
		}
		return false;
	}
	/** �ﰢ�� ��ü�� ���̿� �ش��ϴ� ������ ���� ��ġ�� ���ϴ� �Լ��̴�. 
	 */
	public void findTriangleStartPoints(int endX,int endY,Point startP,int length) {
		if (_x1 == _x2) { // if this is vertical line
			startP.x = endX;
			if (endY > (int)(_y2)) {
				startP.y = endY - length;
			} else if (endY < (int)(_y2)) {
				startP.y = endY + length;
			} else if (endY > (int)(_y1)) {
				startP.y = endY - length;
			} else if (endY < (int)(_y1)) {
				startP.y = endY + length;
			}
		} else {
			double deltax=0,deltay=0;
			double angle;
			if (endX == (int)(_x1) && endY == (int)(_y1)) {
				deltax = (double)(_x2 - _x1);
				deltay = (double)(_y2 - _y1);
			} else if (endX == (int)(_x2) && endY == (int)(_y2)) {
				deltax = (double)(_x1 - _x2);
				deltay = (double)(_y1 - _y2);
			}
			angle = Math.atan(deltay/deltax);
			startP.x = endX + (int)MySys.sign(deltax)*(int)(length * Math.cos(angle));
			startP.y = endY + (int)MySys.sign(deltax)*(int)(length * Math.sin(angle));
		}
	}
	/** ������ ��ü�� ���̿� �ش��ϴ� ������ ���� �� ��ǥ�� ���ϴ� �Լ��̴�. 
	 */
	public void findDiamondEndCorners(Point endP,Point startP,int length,int offset) {
		if (_x1 == _x2) { // if this is vertical line
			startP.x = endP.x;
			if (endP.y > (int)(_y2)) {
				startP.y = endP.y - length;
			} else if (endP.y < (int)(_y2)) {
				startP.y = endP.y + length;
			} else if (endP.y > (int)(_y1)) {
				startP.y = endP.y - length;
			} else if (endP.y < (int)(_y1)) {
				startP.y = endP.y + length;
			}
			CRgn.checkPoints(startP,endP);
			startP.x = startP.x - offset;
			endP.x = endP.x + offset;
		} else if (_y1 == _y2) { // if this is horizontal line
			startP.y = endP.y;
			if (endP.x > (int)(_x2)) {
				startP.x = endP.x - length;
			} else if (endP.x < (int)(_x2)) {
				startP.x = endP.x + length;
			} else if (endP.x > (int)(_x1)) {
				startP.x = endP.x - length;
			} else if (endP.x < (int)(_x1)) {
				startP.x = endP.x + length;
			}
			CRgn.checkPoints(startP,endP);
			startP.y = startP.y - offset;
			endP.y = endP.y + offset;
		} else {
			MySys.myprinterr("Error : unexpected function call Line::findDiamondEndCorners()");
			startP.x = endP.x;
			startP.y = endP.y;
		}
	}
	/** ����Ÿ ��� _type�� ���� �б� access �Լ��̴�.
	 */
	public int type() {
		return _type;
	}
}