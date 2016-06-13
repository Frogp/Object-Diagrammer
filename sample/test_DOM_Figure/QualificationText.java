package figure;

import java.awt.*;
import java.awt.Point;
import java.io.*;
import java.awt.event.*;
/** �� Ŭ������ UML ǥ����� qualification �ɺ��� ó���ϱ� ���� ���̴�.
 */
public final 
	class QualificationText extends Text {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 7807224294718618899L;
	/** �� qualification�� �Ҽӵ� ���� ���迡 ���� ���۷��� 
	 */
	private AssTion _asstionPtr;
	/** �� qualification�� Ŭ������ ��� �ʿ� �پ��ִ� ���� ��Ÿ���� ���� 
	 */
	private int _orient;
	/** qualification �ɺ��� �߾� �κп� ��ġ�� ����
	 */
	private Line _dynamicLine;
	/** qualification �ؽ�Ʈ�� ���� ��ġ x
	 */
	private int _startX;
	/** qualification �ؽ�Ʈ�� ���� ��ġ y
	 */
	private int _startY;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� x ����
	 */
	private static int _GapX = 1;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� y ����
	 */
	private static int _GapY = 5;
	/** qualification �ؽ�Ʈ�� �ѷ��δ� �簢�� ��ü
	 */
	private Box _boundary;
	/** �� ��ü�� ���� �Ŀ� �ı��Ǿ�� ���� ��Ÿ���� flag
	 */
	private boolean _shouldBeDeleted;
	/** qualification �ؽ�Ʈ�� �ִ� ũ��
	 */
	private int _maxWH;
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simplePopup();
	}  
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_boundary.setController(ptr);
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simplePopup());
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		if (!_inCanvas) return;
		recalcCoordFromStartXY();
		if (style == Const.LOWLIGHTING) {
			_boundary.clear(specialdcp,false);
			this.drawLinesFrom(0);
			_boundary.draw(g,style,specialdcp);
			return;
		} else if (style == Const.HIGHLIGHTING) {
			_boundary.clear(specialdcp,false);
			this.drawLinesFrom(0);
			_boundary.draw(g,style,specialdcp);
			return;
		}
		_boundary.draw(g,style,specialdcp);
		if (style == Const.RUBBERBANDING) return;
		_boundary.clear(specialdcp,false);
		_boundary.draw(g,style,specialdcp);
		super.draw(g,style,specialdcp);
	}
	/** qualification�� �׷��� �� �ִ� �ּ� ������ Ȯ���Ǿ��ִ°���
	 * �˻��ϴ� �Լ��̴�.
	 */
	public static int checkMinLen(Line aline,GraphicController controller,QualificationText opposite) {
		int sizeH = controller.fontSizeH();
		int sizeV = controller.fontSizeV();
		int len = aline.length();
		if (opposite != null) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			opposite._boundary.coords(p1,p2);
			int x1 = p1.x; int y1 = p1.y;
			int x2 = p2.x; int y2 = p2.y;
			if (aline.orient() == Const.NORTH) {
				len = len - (y2-y1);
			} else if (aline.orient() == Const.EAST) {
				len = len - (x2-x1);
			}
		}
		if (aline.orient() == Const.NORTH && len <= sizeV+2*_GapY) {
			len = 0;
		} else if (aline.orient() == Const.EAST && len <= 2*sizeH+2*_GapX) {
			len = 0;
		} else {
			len--;
		}
		return len;
	}
	/** ����Ÿ ��� _dynamicLine�� ���� ��Ʈ�ϴ� �Լ�
	 */
	public void setDynamicLine(Line dynamicLine) {
		_dynamicLine = dynamicLine;
		if (_dynamicLine != null) return;
		_dynamicLine = _asstionPtr.getLineFor(_startX,_startY);
	}
	/** ����Ÿ ��� _asstionPtr ���� ���� access �Լ�
	 */
	public void setAsstionPtr(AssTion ptr) {
		_asstionPtr = ptr;
	}
	/** ����Ÿ ��� _orient�� ���� �б� access �Լ�
	 */
	public int orient() {
		return _orient;
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		_asstionPtr = null;
		_boundary.delete(); _boundary = null;
	}
	/** �������̴�.
	 */
	public QualificationText(GraphicController controller,int ox,int oy,int orient,int maxLen,Popup popup) {
		super(controller,ox,oy,popup);
		_asstionPtr = null;
		_shouldBeDeleted = false;
		_dynamicLine = null;
		if (controller == null) return;
		_orient = orient;
		_startX = ox;
		_startY = oy;
		int sizeH = controller.fontSizeH();
		int sizeV = controller.fontSizeV();
		int odd = 0;
		if (sizeV/2*2 == sizeV) {
			odd = 1;
		}
		_maxWH = maxLen;
		if (_orient == Const.NORTH) {
			_x1 = ox;
			_y1 = oy - sizeV - _GapY;
			_x2 = ox + sizeH;
			_y2 = oy - _GapY;
			_boundary = new Box(controller,
								ox-sizeH-_GapX,oy-sizeV-2*_GapY,
								ox+sizeH+_GapX,oy,null);
		} else if (_orient == Const.EAST) {
			_x1 = ox + sizeH + _GapX;
			_y1 = oy - sizeV/2;
			_x2 = ox + 2*sizeH + _GapX;
			_y2 = oy + sizeV/2 + odd;
			_boundary = new Box(controller,
								ox,oy-sizeV/2-_GapY,
								ox+2*sizeH+2*_GapX,oy+sizeV/2+_GapY+odd,null);
		} else if (_orient == Const.SOUTH) {
			_x1 = ox;
			_y1 = oy + _GapY;
			_x2 = ox + sizeH;
			_y2 = oy + sizeV + _GapY;
			_boundary = new Box(controller,
								ox-sizeH-_GapX,oy,
								ox+sizeH+_GapX,oy+sizeV+2*_GapY,null);
		} else { // WEST
			_x1 = ox - sizeH - _GapX;
			_y1 = oy - sizeV/2;
			_x2 = ox - _GapX;
			_y2 = oy + sizeV/2 + odd;
			_boundary = new Box(controller,
								ox-2*sizeH-2*_GapX,oy-sizeV/2-_GapY,
								ox,oy+sizeV/2+_GapY+odd,null);	
		}
	}
	/** qualification �� ��ǥ ���� ��������� ���� �ٽ� ����ϴ� �Լ��̴�.
	 */
	private void recalcCoordFromStartXY() {
		int w = this.width();
		int h = this.height();
		int sizeH = _controller.fontSizeH();
		int sizeV = _controller.fontSizeV();
		int odd = 0;
		if (sizeV/2*2 == sizeV) {
			odd = 1;
		}
		int ox = _startX;
		int oy = _startY;
		if (_orient == Const.NORTH) {
			_x1 = ox - sizeH*w/2;
			_y1 = oy - (sizeV*h+_GapY);
			_x2 = ox + sizeH*(w+2)/2;
			_y2 = oy - _GapY;
			_boundary = new Box(_controller,
								ox-(sizeH*(w+2)/2+_GapX),oy-(sizeV*h+2*_GapY),
								ox+sizeH*(w+2)/2+_GapX,oy,null);
		} else if (_orient == Const.EAST) {
			_x1 = ox + sizeH + _GapX;
			_y1 = oy - sizeV*h/2;
			_x2 = ox + sizeH*(w+2) + _GapX;
			_y2 = oy + sizeV*h/2 + odd;
			_boundary = new Box(_controller,
								ox,oy-(sizeV*h/2+_GapY),
								ox+sizeH*(w+2)+2*_GapX,oy+sizeV*h/2+_GapY+odd,null);
		} else if (_orient == Const.SOUTH) {
			_x1 = ox - sizeH*w/2;
			_y1 = oy + _GapY;
			_x2 = ox + sizeH*(w+2)/2;
			_y2 = oy + sizeV*h+_GapY;
			_boundary = new Box(_controller,
								ox-(sizeH*(w+2)/2+_GapX),oy,
								ox+sizeH*(w+2)/2+_GapX,oy+sizeV*h+2*_GapY,null);
		} else { // WEST
			_x1 = ox - (sizeH*(w+1) + _GapX);
			_y1 = oy - sizeV*h/2;
			_x2 = ox - (sizeH + _GapX);
			_y2 = oy + sizeV*h/2 + odd;
			_boundary = new Box(_controller,
								ox-(sizeH*(w+2)+2*_GapX),oy-(sizeV*h/2+_GapY),
								ox,oy+sizeV*h/2+_GapY+odd,null);	
		}
	}
	/** �ؽ�Ʈ ��ü�� ���� ������ ���� �� �� ���ε��� �����ϴ� �Լ��̴�.
	 */
	private void delEmptyLines() {
		int nLines = nOfLines();
		if (nLines <= 1) return;
		int delLines[] = new int[nLines];
		int delCount = 0;
		int index = 0;
		TextLine aLine = _content.getFirst();
		while(aLine != null) {
			if (aLine.valueAt(0) == '\0') {
				delLines[delCount] = index;
				delCount++;
			}
			index++;
			aLine = _content.getNext();
		}
		for(int i = delCount-1; i >= 0; i--) {
			_content.removeLineAt(delLines[i]);
			if (_orient == Const.NORTH) {
				_y1 = _y1 + _deltaV;
			} else if (_orient == Const.EAST) {
				adjustY(-_deltaV);
			} else if (_orient == Const.SOUTH) {
				_y2 = _y2 - _deltaV;
			} else { // WEST
				adjustY(-_deltaV);
			}
		}
		if (delCount == 0) {
			return;
		}
		_controller.clear(_boundary,true);
//		_boundary.setXY1(_x1-_deltaH-_GapX,_y1-_GapY);
		_boundary.setXY2(_x2+_GapX,_y2+_GapY);
		drawSimple();
		_controller.draw(_boundary);
	}
	/** qualification �ؽ�Ʈ�� ���� �ÿ� ���� ��ȭ�� ���� x ��ǥ ���� �������ִ� �Լ��̴�.
	 */
	private void adjustX(int delta) {
		if (delta/2*2 != delta) {
			if (width()/2*2 != width()) {
				if (delta > 0) {
					_x1 = _x1 - delta/2;
					_x2 = _x2 + delta/2 + 1;
				} else {
					_x1 = _x1 - delta/2 + 1;
					_x2 = _x2 + delta/2;
				}
			} else {
				if (delta > 0) {
					_x1 = _x1 - delta/2 - 1;
					_x2 = _x2 + delta/2;
				} else {
					_x1 = _x1 - delta/2;
					_x2 = _x2 + delta/2 - 1;
				}
			}
		} else {
			_x1 = _x1 - delta/2;
			_x2 = _x2 + delta/2;
		}
	}
	/** qualification �ؽ�Ʈ�� ���� �ÿ� ������ ��ȭ�� ���� y ��ǥ ���� �������ִ� �Լ��̴�.
	 */
	private void adjustY(int delta) {
		if (delta/2*2 != delta) {
			if (height()/2*2 != height()) {
				if (delta > 0) {
					_y1 = _y1 - delta/2;
					_y2 = _y2 + delta/2 + 1;
				} else {
					_y1 = _y1 - delta/2 + 1;
					_y2 = _y2 + delta/2;
				}
			} else {
				if (delta > 0) {
					_y1 = _y1 - delta/2 - 1;
					_y2 = _y2 + delta/2;
				} else {
					_y1 = _y1 - delta/2;
					_y2 = _y2 + delta/2 - 1;
				}
			}
		} else {
			_y1 = _y1 - delta/2;
			_y2 = _y2 + delta/2;
		}
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMQUALIFICATIONTEXT;
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_boundary.setInCanvas(flag);
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		QualificationText copied;
		if (ptr == null) {
			copied = new QualificationText(_controller,0,0,0,0,null);
		} else {
			copied = (QualificationText)ptr;
		}
		copied._startX = _startX;
		copied._startY = _startY;
		copied._orient = _orient;
		copied._maxWH = _maxWH;
		copied._asstionPtr = _asstionPtr;
		copied._boundary = (Box)_boundary.born(null);
		return(super.born((Figure)copied));	
	}
	/** ��ü�� �ı� ������ �ϰ��� ������ ������ Ȯ���ϴ� �Լ��̴�.
	 */
	public void ensureConsistencyBeforeDelete() {
		_asstionPtr.resetQualificationFor(this);
		setDynamicLine(null);
		_dynamicLine = null;
	}
	/** ����Ÿ ��� _shouldBeDeleted�� ���� �б� access �Լ�
	 */
	public boolean shouldBeDeleted() {
		return _shouldBeDeleted;
	}
	/** �� ��ü�� ���� �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		_popup.popup(event);
	}
	/** Ű����� ���� [Enter] Ű�� �ԷµǾ��� �� �� �ٲ��� ó���ϴ� �Լ��̴�.
	 */
	/*
	public void gonextline() {
		int maxCharsOld = width();
		TextLine nextLine = _content.splitLineAt(_cursorX,_cursorY);
		currentLine.copy(nextLine);
		_cursorY++;
		_cursorX = 0;
		setX2AsOtherLinesMaxWidth();
		_screen.vanish();
		int deltaV = _deltaV;
		if (_orient == Const.NORTH) {
			_boundary.expandVertical(Const.NORTH,0,deltaV,height(),_dynamicLine);
			_y1 = _y1 - deltaV;
		} else if (_orient == Const.EAST) {
			_boundary.expandVertical(Const.NORTH,Const.SOUTH,deltaV,height(),_dynamicLine);
			adjustY(deltaV);
		} else if (_orient == Const.SOUTH) {	
			_boundary.expandVertical(0,Const.SOUTH,deltaV,height(),_dynamicLine);
		} else { // WEST
			_boundary.expandVertical(Const.NORTH,Const.SOUTH,deltaV,height(),_dynamicLine);
			adjustY(deltaV);
		}
		int maxCharsNew = width();
		int diff = maxCharsNew - maxCharsOld;
		int delta = _deltaH * diff;
		if (diff < 0) {
			if (_orient == Const.NORTH) {
				_boundary.expandHorizontal(Const.WEST,Const.EAST,delta,width(),_dynamicLine);
				adjustX(delta);
			} else if (_orient == Const.EAST) {
				_boundary.expandHorizontal(0,Const.EAST,delta,width(),_dynamicLine);
			} else if (_orient == Const.SOUTH) {
				_boundary.expandHorizontal(Const.WEST,Const.EAST,delta,width(),_dynamicLine);
				adjustX(delta);
			} else { // WEST
				_boundary.expandHorizontal(Const.WEST,0,delta,width(),_dynamicLine);
				_x1 = _x1 - delta;
			}
		}
		_screen.activate();
		this.drawLinesFrom(0);
		_screen.goNextLine(_deltaV*_cursorY);
	}
	*/
	/** �ؽ�Ʈ�� �� ���� ����� �Լ��̴�. �ؽ�Ʈ �� ���� ���ֱ� ���� Ű���� �Է��� [Ctrl]+[BackSpace] �̴�.
	 */
	/*
	public boolean delLine() {
		int maxCharsOld = width();
		if (height() ==	1) {
			_content.lineAt(0).clear();
			currentLine.clear();
			_cursorX = 0; _cursorY = 0;
			int maxCharsNew = width();
			int diff = maxCharsNew - maxCharsOld;
			int delta = _deltaH * diff;
			_screen.vanish();
			if (diff < 0) {
				if (_orient == Const.NORTH) {
					_boundary.expandHorizontal(Const.WEST,Const.EAST,delta,width(),_dynamicLine);
					adjustX(delta);
				} else if (_orient == Const.EAST) {
					_boundary.expandHorizontal(0,Const.EAST,delta,width(),_dynamicLine);
				} else if (_orient == Const.SOUTH) {
					_boundary.expandHorizontal(Const.WEST,Const.EAST,delta,width(),_dynamicLine);
					adjustX(delta);
				} else { // WEST
					_boundary.expandHorizontal(Const.WEST,0,delta,width(),_dynamicLine);
					_x1 = _x1 - delta;
				}
			}
			_screen.activate();
			_screen.delFirstLine();
			_controller.draw(_boundary);
			return true;
		}
		if (_cursorY ==	height()-1)	{
			_content.removeLineAt(_cursorY);
			_cursorY--;
		} else {
			_content.removeLineAt(_cursorY);
		}
		currentLine.copy(_content.lineAt(_cursorY));
		_cursorX = 0;
		int deltaV = _deltaV;
		_screen.vanish();
		if (_orient == Const.NORTH) {
			_boundary.expandVertical(Const.NORTH,0,-deltaV,height(),_dynamicLine);
			_y1 = _y1 + deltaV;
		} else if (_orient == Const.EAST) {
			_boundary.expandVertical(Const.NORTH,Const.SOUTH,-deltaV,height(),_dynamicLine);
			adjustY(-deltaV);
		} else if (_orient == Const.SOUTH) {
			_boundary.expandVertical(0,Const.SOUTH,-deltaV,height(),_dynamicLine);
			_y2 = _y2 - deltaV;
		} else { // WEST
			_boundary.expandVertical(Const.NORTH,Const.SOUTH,-deltaV,height(),_dynamicLine);
			adjustY(-deltaV);
		}
		_screen.activate();

		int maxCharsNew = width();
		int diff = maxCharsNew - maxCharsOld;
		int delta = _deltaH * diff;
		_screen.vanish();
		if (diff < 0) {
			if (_orient == Const.NORTH) {
				_boundary.expandHorizontal(Const.WEST,Const.EAST,delta,width(),_dynamicLine);
				adjustX(delta);
			} else if (_orient == Const.EAST) {
				_boundary.expandHorizontal(0,Const.EAST,delta,width(),_dynamicLine);
			} else if (_orient == Const.SOUTH) {
				_boundary.expandHorizontal(Const.WEST,Const.EAST,delta,width(),_dynamicLine);
				adjustX(delta);
			} else { // WEST
				_boundary.expandHorizontal(Const.WEST,0,delta,width(),_dynamicLine);
				_x1 = _x1 - delta;
			}
		}
		this.drawLinesFrom(0);
		_screen.activate();
		_screen.delLine(_cursorY*deltaV(),true);
		setX2AsOtherLinesMaxWidth();
		_controller.draw(_boundary);
		return false;
	}
	*/
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		_startX = _startX+dx;
		_startY = _startY+dy;
		moveCoord(dx,dy);
		_boundary.move(g,dx,dy);	
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		_boundary.clear(g,expose);
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		_boundary.makeRegion();
		super.makeRegion();
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
	 */
	public boolean onEnter(int x,int y) {
		return _boundary.onEnter(x,y);
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� �� �Լ��� ���Ͽ� GraphicController ��ü��
	 * �ִ� �ּ� ��ǥ���� �ݿ��ȴ�.
	 */
	public void minMaxXY() {
		_boundary.minMaxXY(); 
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� ���ڵ� ���� �ݿ��Ǿ� ��������.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		_boundary.getMinMaxXY(minP,maxP);
	}
	/** ���� �ؽ�Ʈ�� ���� �۾��� �������Ǿ��� �� ���� �۾��� �ϴ� �Լ��̴�.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		AnyTionInfoTuple tuple = _asstionPtr.findTupleForQual(this);
		if (_content.valueAt(0,0) == '\0') {
			_controller.clear(this,true);
			ensureConsistencyBeforeDelete();
			_shouldBeDeleted = true;
		}
		_dynamicLine = null;
	}
	/** �ؽ�Ʈ�� ���� ���� �ÿ� ������ �۾��� �ϴ� �Լ��̴�.
	 */
	public void bye() {
		super.bye();
		delEmptyLines();
	}
	/** �� ��ü�� �����ϴ� ��ü�� ã�� �Լ��̴�.
	 */
	public Figure container() {
		return _asstionPtr;
	}
	/** �� ��ü�� ��ǥ���� �˾ƿ��� �Լ��̴�.
	 */
	public void coords(Point p1,Point p2) {
		_boundary.coords(p1,p2);
	}
	/** �� qualification ��ü�� ��ǥ�� ������ �����ϴ� �Լ��̴�.
	 */
	public void adjustCoordsFor(int sx,int sy,int orient,Line aline,QualificationText opposite) {
		/*
		_orient = orient;
		_startX = sx;
		_startY = sy;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_boundary.coords(p1,p2);
		int x1 = p1.x; int y1 = p1.y;
		int x2 = p2.x; int y2 = p2.y;
		int w = x2 - x1;
		int h = y2 - y1;
		if (_orient == Const.NORTH) {
			_boundary.setXY1(sx-w/2,sy-h);
			_boundary.setXY2(sx+w-w/2,sy);
		} else if (_orient == Const.EAST) {
			_boundary.setXY1(sx,sy-h/2);
			_boundary.setXY2(sx+w,sy+h-h/2);
		} else if (_orient == Const.SOUTH) {
			_boundary.setXY1(sx-w/2,sy);
			_boundary.setXY2(sx+w-w/2,sy+h);
		} else { // WEST
			_boundary.setXY1(sx-w,sy-h/2);
			_boundary.setXY2(sx,sy+h-h/2);
		}		
		super.coords(p1,p2);
		x1 = p1.x; y1 = p1.y;
		x2 = p2.x; y2 = p2.y;
		w = x2 - x1;
		h = y2 - y1;
		int sizeH = _deltaH;
		int sizeV = _deltaV;
		if (_orient == Const.NORTH) {	
			setXY1(sx-w/2+sizeH/2,sy-h-_GapY);
		} else if (_orient == Const.EAST) {	   
			setXY1(sx+_GapX+sizeH,sy-h/2);
		} else if (_orient == Const.SOUTH) {	  
			setXY1(sx-w/2+sizeH/2,sy+_GapY);
		} else { // WEST    
			setXY1(sx-w-_GapX,sy-h/2);
		}
		_x2 = _x1 + width() * sizeH;
		_y2 = _y1 + height() * sizeV;
		int len = aline.length();
		if (opposite != null) {
			opposite._boundary.coords(p1,p2);
			x1 = p1.x; y1 = p1.y;
			x2 = p2.x; y2 = p2.y;
			if (aline.orient() == Const.NORTH) {
				len = len - (y2-y1);
			} else if (aline.orient() == Const.EAST) {
				len = len - (x2-x1);
			}
		}
		if (aline.orient() == Const.NORTH && len <= sizeV+2*_GapY) {
			len = 0;
		} else if (aline.orient() == Const.EAST && len <= 2*sizeH+2*_GapX) {
			len = 0;
		} else {
			len--;
		}
		_maxWH = len;
		*/
	}
	/** �� ��ü�� ���� ���̸� �ٽ� ����ϴ� �Լ��̴�.
	 */
	public void recalcWidthHeight() {
		/*
		_xscale = 1.0;
		_yscale = 1.0;
		_screen.xscale = 1.0;
		_screen.yscale = 1.0;
		_screen.deltaH = _controller.fontSizeH();
		_screen.deltaV = _controller.fontSizeV();
		super.recalcWidthHeight();
		int x1 = _x1;
		int y1 = _y1;
		int x2 = _x2;
		int y2 = _y2;
		int sizeH = _controller.fontSizeH();
		_boundary.setXY1(x1-sizeH-_GapX,y1-_GapY);
		_boundary.setXY2(x2+_GapX,y2+_GapY);
		*/
	}
}
