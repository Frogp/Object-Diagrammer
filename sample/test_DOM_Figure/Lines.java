package figure;

import java.awt.*;
import java.io.*;
import java.awt.Point;
/** �� Ŭ������ �������� �������� �̷������ �� ��ü�� ��Ÿ���� ���� Ŭ�����̴�.
 */
public 
	class Lines extends Figure {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 6812527262275365375L;
	/** ORDINARY or STRAIGHT */
	protected int _type;
	/** SOLID or DASHED */
	protected int _style;
	/** ���� ������ �����ϴ� ����Ʈ
	 */
	protected LineNodeList _lines;
	/** �� �׸��� ���� ������ x ��ǥ
	 */
	protected int _lastx;
	/** �� �׸��� ���� ������ y ��ǥ
	 */
	protected int _lasty;
	/** �� ��ü�� �̵� �� ��� ������ �̵��Ǿ�� ���� ��Ÿ���� flag
	 */
	protected boolean _moveAllFlag;
	/** ������ �Ǵ� ���� ���
	 */
	protected transient LineNode _focusNode;
	/** ������ �Ǵ� ����
	 */
	protected transient Figure _focus;
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.line().setController(ptr);
			node = _lines.getNext();
		}
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.line().setInCanvas(flag);
			node = _lines.getNext();
		}
	}
	/** ���� ���鿡 �ִ� mark flag ���� �ʱ�ȭ�ϴ� �Լ��̴�.
	 */
	protected void resetMark() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.mark = false;
			node = _lines.getNext();
		}
	}
	/** ���� ���鿡 �ִ� move flag ���� �ʱ�ȭ�ϴ� �Լ��̴�.
	 */
	protected void resetMove() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.mark = false;
			node = _lines.getNext();
		}
	}
	/** ���� ���鿡 �ִ� ��� flag ������ �ʱ�ȭ�ϴ� �Լ��̴�.
	 */
	protected void resetFlags() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.resetFlags();
			node = _lines.getNext();
		}
	}
	/** ���� ���鿡 �ִ� followed flag ���� �ʱ�ȭ�ϴ� �Լ��̴�.
	 */
	protected void resetFollowed() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.followed = false;
			node = _lines.getNext();
		}
	}
	/** ���е� �߿��� ���ڷ� ��õ� ��ǥ�� �����ϴ� ���� ��带 ã�� �Լ��̴�.
	 */
	protected LineNode findNodeFor(int x,int y) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			line.coords(p1,p2);
			if (p1.x == x && p1.y == y) {
				line.swapPoints();
				tmp.swapBeforeAfter();
				return tmp;
			}
			if (p2.x == x && p2.y == y) {
				return tmp;
			}
			tmp = _lines.getNext();
		}
		MySys.myprinterr("Error : unexpected return value in Lines::findNodeFor().");
		return null;
	}
	/** �������� ���õ� ������ ����� �Լ��̴�.
	 */
	protected void removeFocusLine() {
		if (_controller != null) _controller.clear(_focus,true);
		_lines.remove(_focusNode,true);
		_focusNode.delete();
		_focus.delete();
		_focusNode = null;
		_focus = null;
	}
	/** ���е� �߿��� ��ġ�� �͵��� �����ϴ� �Լ��̴�.
	 */
	protected void checkZammedLines() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			if (node.move == false)
				node.checkIfZammed();
			node = _lines.getNext();
		}
	}
	/** �̵����� ���� ���п� ���� �� �ֵ��� ��ġ�ϴ� �Լ��̴�.
	 */
	protected void checkNearForFollow() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		int level = 1; // level should be 2 for straight line
		LineNode node = _lines.getFirst();
		while(node != null) {
			if (node.move) {
				node.line().coords(p1,p2);
				node.checkNearForFollow(_controller,_lines,_type,p1.x,p1.y,Const.ATBEFORE);
				node.checkNearForFollow(_controller,_lines,_type,p2.x,p2.y,Const.ATAFTER);
			}
			node = _lines.getNext();
		}
	}
	/** ���е�� �Ͽ��� �־��� ���� ��ŭ ��ǥ ���� �̵��ϰ��ϴ� �Լ��̴�.
	 */
	protected void followSomeObject(int dx,int dy) {
		resetFollowed();
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			if (tmp.mark) {
				Point p = tmp.line().last();
				int newx = p.x + dx;
				int newy = p.y + dy;
				tmp.followMe(newx,newy);
			}
			tmp = _lines.getNext();
		}
	}
	/** ���е�� �Ͽ��� �־��� ���� ��ŭ ��ǥ ���� �̵��ϰ��ϴ� �Լ��̴�.
	 */
	protected void followSomeObject(Graphics g,int dx,int dy) {
		resetFollowed();
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			if (tmp.mark) {
				Point p = tmp.line().last();
				int newx = p.x + dx;
				int newy = p.y + dy;
				tmp.followMe(g,newx,newy);
			}
			tmp = _lines.getNext();
		}
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.delete();
			tmp.delete();
			tmp = _lines.getNext();
		}
		_lines.delete(); _lines = null;
		_focusNode = null;
		_focus = null;
	}
	/** �������̴�.
	 */
	public Lines(GraphicController controller,Popup popup,int type,Line line) {
		super(controller);
		_popup = popup;
		_focusNode = null;
		_focus = null;
		_type = type;
		_lines = new LineNodeList();
		if (line != null) {
			insert(line,true);
		}
		_lastx = -1;
		_lasty = -1;
		_style = Const.SOLID;
		_moveAllFlag = true;
	}
	/** ����Ÿ ��� _type�� ���� access �Լ�
	 */
	public int drawingType() {
		if (_type == Const.STRAIGHT) {
			return Const.SLINESPTR;
		} else {
			return Const.LINESPTR;
		}
	}
	/** ���ڷ� �־��� ���п� �ش��ϴ� ���� ��带 ã�� �Լ��̴�.
	 */
	public LineNode node(Line line) {
		if (line == _focus) return _focusNode;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			if (line == tmp.line())
				return tmp;
			tmp = _lines.getNext();
		}
		return null;
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMLINES;
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		Lines copied;
		if (ptr == null) {
			copied = new Lines(_controller,null,0,null);
		} else {
			copied = (Lines)ptr;
		}
		copied._type = _type;
		LineNode node = _lines.getFirst();
		while(node != null) {
			Line copiedLine = (Line)node.line().born(null);
			LineNode copiedNode = new LineNode(copiedLine,null,null);	
			copied._lines.insert(copiedNode,0);
			if (_focusNode == node) {
				copied._focusNode = copiedNode;
				copied._focus = copiedLine;
			}
			node = _lines.getNext();		
		}
		copied.remake();
		return (super.born((Figure)copied));
	}
	/** �׸� ��ü�� ���� �κ� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void bornFocus() {
		resetFlags();
		Line newline = (Line)((Line)_focus).born(null);
		LineNode newnode = new LineNode(newline,null,null);
		_lines.insert(newnode,0);
		_focus = newline;
		_focusNode = newnode;
		_moveAllFlag = false;
		_focusNode.move = true;
		Point p = ((Line)_focus).center();
		Line savedfocus = (Line)_focus;
		LineNode savednode = _focusNode;
		_controller.checkToGo(p.x,p.y,_controller.popupX(),_controller.popupY());
		_controller.warpPointer(p.x,p.y);
		_focus = savedfocus;
		_focusNode = savednode;
	}
	/** �� ��ü�� �߾ӿ� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public Point center() {
		if (_focusNode != null) {
			return _focusNode.line().center();
		} else {
			return _lines.top().line().center();
		}
	}
	/** �� ��ü�� ������ ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public Point last() {
		if (_lines.nOfList() == 0) {
			return new Point(_lastx,_lasty);
		}
		if (_focusNode != null) {
			return _focusNode.line().last();
		} else {
			return _lines.rear().line().last();
		}
	}
	/** �� ��ü�� ���� ����Ʈ�� ���ο� ������ �����ϴ� �Լ��̴�.
	 */
	public LineNode insert(Line newline,boolean focusflag) {
		LineNode newnode = new LineNode(newline,_focusNode,null);
		_lines.insert(newnode,0);
		if (focusflag) {
			_focus = newline;
			_focusNode = newnode;
		}
		return newnode;
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.clear(g,expose);
			tmp = _lines.getNext();
		}
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� �� �Լ��� ���Ͽ� GraphicController ��ü��
	 * �ִ� �ּ� ��ǥ���� �ݿ��ȴ�.
	 */
	public void minMaxXY() {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.minMaxXY();
			tmp = _lines.getNext();
		}
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� ���ڵ� ���� �ݿ��Ǿ� ��������.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.getMinMaxXY(minP,maxP);
			tmp = _lines.getNext();
		}
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		if (_region != null) CRgn.myDestroyRgn(_region);
		_region = CRgn.myCreateRgn();
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
		_maxRegion = CRgn.myCreateRgn();
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.makeRegion();
			CRgn.myUnionRgn(_region,line.region(),_region);
			CRgn.myUnionRgn(_maxRegion,line.maxRegion(),_maxRegion);
			tmp = _lines.getNext();
		}
	}
	/** �� ��ü�� �־��� �����ȿ� ���ԵǴ°��� ��Ÿ���� �Լ��̴�.
	 */
	public boolean checkInRegion(CRgn someregion) {
		boolean flag = false;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			flag = line.checkInRegion(someregion);
			if (flag) break;
			tmp = _lines.getNext();
		}
		return flag;
	}
	/** ���� ���콺�� �������� �� ������ ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantMove(CPoint point) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			if (line.wantMove(point) == true) {
				_focus = line;
				_focusNode = tmp;
				return true;
			}
			tmp = _lines.getNext();
		}
		return false;
	}
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			if (line.wantResize(point) == true) {
				_focus = line;
				_focusNode = tmp;
				return true;
			}
			tmp = _lines.getNext();
		}
		return false;
	}
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public void drawDots(Graphics g) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.drawDots(g);
			tmp = _lines.getNext();
		}
	}
	/** ��ǥ�� �̵� ��Ű�� �Լ��̴�.
	 */
	public void moveCoord(int dx,int dy) {
		if (_moveAllFlag == true) {
			LineNode tmp = _lines.getFirst();
			while (tmp != null) {
				Line line = tmp.line();
				line.moveCoord(dx,dy);
				tmp = _lines.getNext();
			}
		} else {
			resetFollowed();
			LineNode tmp = _lines.getFirst();
			while (tmp != null) {
				if (tmp.move) {
					Line line = tmp.line();
					line.moveCoord(dx,dy);
					tmp.followResizing();
				}
				tmp = _lines.getNext();
			}
		}
	}
	/** �� ���� �������� �׷��ִ� �Լ��̴�.
	 */
	public void drawDashed(Graphics g,int style,Graphics specialg) {
		if (!_inCanvas) return;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.drawDashed(g,style,specialg);
			tmp = _lines.getNext();
		}
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int style,Graphics specialg) {
		if (!_inCanvas) return;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.draw(g,style,specialg);
			tmp = _lines.getNext();
		}
	}
	/** ���� ������ _x2, _y2 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY2(int x,int y) {
		if (_focusNode == null) return;
		_focusNode.line().setXY2(x,y);
	}
	/** �׸� ũ�⸦ �����ϱ� ���� ���� ������ _x2, _y2 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY2ForResize(int newx,int newy) {
		if (_focusNode == null) return;
		resetFollowed();
		_focusNode.followMe(newx,newy);
		LineNodeList afters = _focusNode.afterList();
		LineNode ptr = afters.getFirst();
		while (ptr != null) {
			ptr.followMe(newx,newy);
			ptr = afters.getNext();
		}
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 * ���� ���콺�� ���ο� ��ġ ���� �̿��Ͽ� rubberbanding �Ѵ�.
	 */
	public void drawing(Graphics g,int newx,int newy,boolean flag) {
		if (_controller.currentLines() == null) {
			// resizing
			resetFollowed();
			_focusNode.followMe(g,newx,newy);
			LineNodeList afters = _focusNode.afterList();
			LineNode ptr = afters.getFirst();
			while (ptr != null) {
				ptr.followMe(g,newx,newy);
				ptr = afters.getNext();
			}
		} else {
			// drawing
			((Line)_focus).drawing(g,newx,newy,false);
			LineNodeList afters = _focusNode.afterList();
			LineNode ptr = afters.getFirst();
			while (ptr != null) {
				ptr.line().drawing(g,newx,newy,false);
				ptr = afters.getNext();
			}
		}
	}
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 */
	public void drawing(Graphics g,boolean flag) {
		boolean nocheck;
		if (_controller.currentLines() == null) {
			// resizing
			nocheck = true;
		} else {
			// drawing
			nocheck = false;
		}
		((Line)_focus).drawing(g,nocheck);
		LineNodeList afters = _focusNode.afterList();
		LineNode ptr = afters.getFirst();
		while (ptr != null) {
			ptr.line().drawing(g,nocheck);
			ptr = afters.getNext();
		}
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		if (_moveAllFlag) {
			LineNode tmp = _lines.getFirst();
			while (tmp != null) {
				Line line = tmp.line();
				line.move(g,dx,dy);
				tmp = _lines.getNext();
			}
		} else {
			resetFollowed();
			LineNode tmp = _lines.getFirst();
			while (tmp != null) {
				if (tmp.move) {
					Line line = tmp.line();
					line.move(g,dx,dy);
					tmp.followResizing(g);
				}
				tmp = _lines.getNext();
			}
		}
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
	 */
	public boolean onEnter(int x,int y) {
		boolean value = false;
		_focus = null;
		_focusNode = null;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			if (line.onEnter(x,y)) {
				value = true;
				_focus = line;
				_focusNode = tmp;
				break;
			}
			tmp = _lines.getNext();
		}
		return value;
	}
	/** ����Ÿ ��� _focus�� ���� �б� access �Լ�.
	 */
	public Figure focus() {
		return (Figure)_focus;
	}
	/** ���콺�� ��ġ�� �̿��Ͽ� �ʼ� ������ ã�� �Լ��̴�.
	 */
	public void setFocus(int x,int y) {
		_focus = null;
		_focusNode = null;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			if (line.doesMeet(x,y)) {
				_focus = line;
				_focusNode = tmp;
				return;
			}
			tmp = _lines.getNext();
		}
	}
	/** ���� ������ �����ϴ� �Լ��̴�.
	 */
	public void resetFocus() {
		_focus = null;
		_focusNode = null;
	}
	/** �� ���� ���Ե� ������ ������ �����ִ� �Լ��̴�.
	 */
	public int nOfFigures() {
		return _lines.nOfList();
	}
	/** �׸� ��ü�� ������ ��ǥ(x2,y2) ���� ���ڿ� ��õ� ��ǥ ���� ����������� �����ϴ� �Լ��̴�.
	 */
	public boolean checkNear(int x,int y) {
		if (_focus == null || _focusNode == null) {
			MySys.myprinterr("Warning : No focus in Lines::checkNear().");
			return false;
		}
		boolean swaped = ((Line)_focus).checkNear(x,y);
		if (swaped) {
			_focusNode.swapBeforeAfter();
		}
		Point endP = ((Line)_focus).last();
		LineNodeList afters = _focusNode.afterList();
		LineNode ptr = afters.getFirst();
		while (ptr != null) {
			boolean localSwaped = ptr.line().checkNear(endP.x,endP.y);
			if (localSwaped) {
				ptr.swapBeforeAfter();
			}
			ptr = afters.getNext();
		}
		return swaped;
	}
	/** ǥ��� ��ü�� ������ ���� ���Ŀ� ȣ��Ǵ� �Լ��μ� �ϰ��� �˻�,
	 * ��ġ �������� �����Ѵ�.
	 */
	public boolean epilog(boolean flag) {
		_moveAllFlag = true;
		if (_focus == null || _focusNode == null) {
			int loopcount = 0;
			boolean satisfied = false;
			while(!satisfied) {
				if (loopcount > 10) {
					remake();
					return false;
				}
				loopcount++;
				satisfied = true;
				resetMark();
				LineNode node = _lines.getFirst();
				while(node != null) {
					boolean changed;
					if (node.cascade) {
						changed = node.checkToMerge(_controller,_lines,false);
						if (changed) {
							node.checkToMerge(_controller,_lines,false);
							satisfied = false;
							node = _lines.getFirst();
						} else {
							node = _lines.getNext();
						}
					} else {	
						node = _lines.getNext();
					}
				}
				resetFlags(); // this should be reset after using cascade flag
				_controller.clear(this,false);
				node = _lines.getFirst();
				while(node != null) {
					boolean obsolete;
					obsolete = node.checkIfObsolete();
					if (obsolete) {
						satisfied = false;
						node.delete();
						_lines.remove(node,true);
						node = _lines.getFirst();
					} else {
						node = _lines.getNext();
					}
				}
				// first merge before attach
				resetMark();
				node = _lines.getFirst();
				while(node != null) {
					boolean changed;
					changed = node.checkToMerge(_controller,_lines,false);
					if (changed) {
						node.checkToMerge(_controller,_lines,false);
						satisfied = false;
						node = _lines.getFirst();
					} else {
						node = _lines.getNext();
					}
				}
				resetMark();
				node = _lines.getFirst();
				while(node != null) {
					boolean obsolete;
					obsolete = node.checkIfObsolete();
					if (obsolete) {
						satisfied = false;
						node.delete();
						_lines.remove(node,true);
						node = _lines.getFirst();
					} else {
						node = _lines.getNext();
					}
				}
				node = _lines.getFirst();
				while(node != null) {
					node.line().slope();
					node = _lines.getNext();
				}
				resetMark();
				node = _lines.getFirst();
				while(node != null) {
					boolean changed;
					Line line = node.line();
					Point p1 = new Point(0,0);
					Point p2 = new Point(0,0);
					line.coords(p1,p2);
					changed = localDoAttach(p1.x,p1.y,line,node,false);
					if (changed) {
						satisfied = false;
						node = _lines.getFirst();
					} else {
						changed = localDoAttach(p2.x,p2.y,line,node,false);
						if (changed) {
							satisfied = false;
							node = _lines.getFirst();
						} else {
							node.mark = true;
							node = _lines.getNext();
						}
					}
				}
				LineNodeList copied = new LineNodeList();
				copied.copy(_lines);
				node = _lines.getFirst();
				while(node != null) {
					LineNode tmp = copied.getFirst();
					boolean same = false;
					while(tmp != null) {
						if ((node != tmp) && 
							(node.line().checkIfSame(tmp.line()))) {
							Line tmpline = tmp.line();
							tmpline.delete();
							tmp.delete();
							_lines.remove(tmp,true);
							same = true;
							break;
						}
						tmp = copied.getNext();
					}
					if (same) {
						satisfied = false;
						copied.clear();
						copied.copy(_lines);
						node = _lines.getFirst();
					} else {
						node = _lines.getNext();
					}
				}
				copied.delete();
				// second merge after attach
				resetMark();
				node = _lines.getFirst();
				while(node != null) {
					boolean changed;
					changed = node.checkToMerge(_controller,_lines,false);
					if (changed) {
						node.checkToMerge(_controller,_lines,false);
						satisfied = false;
						node = _lines.getFirst();
					} else {
						node = _lines.getNext();
					}
				}
			}
			remake();
			return false;
		}
		int orient = ((Line)_focus).setOrient(Const.RESETORIENT);
		_controller.currentOrient = orient;
		Point lastP = ((Line)_focus).last();
		// protecting focus change
		Line savedfocus = (Line)_focus;
		LineNode savednode = _focusNode;
		_controller.warpPointer(lastP.x,lastP.y);
		_focus = savedfocus;
		_focusNode = savednode;
		if (((Line)_focus).isObsolete()) {
			Point tmpP = ((Line)_focus).last();
			_lastx = tmpP.x; _lasty = tmpP.y;
			LineNode nextFocusNode;
			if (_lines.nOfList() > 1) {
				nextFocusNode = _focusNode.beforeList().getFirst();
				if (nextFocusNode == null) {
					nextFocusNode = _focusNode.afterList().getFirst();
					if (nextFocusNode == null) {
						return false;
					}
				}	
				boolean swaped = nextFocusNode.line().checkNear(_lastx,_lasty);
				if (swaped) {
					nextFocusNode.swapBeforeAfter();
				}
				_focusNode.mark = false;
				_focusNode.checkIfObsolete();
				_focusNode.delete();
				_lines.remove(_focusNode,true);
				_focusNode = nextFocusNode;
				_focus = nextFocusNode.line();
			} else {
				if (_controller.currentLines() != null) {
					removeFocusLine();
					_focusNode = null;
					_focus = null;
					return false;
				} else {
					return true;
				}
			}
		} else {
			_focusNode.mark = false;
			boolean obsolete = _focusNode.checkIfObsolete();
			if (obsolete) {
				_focusNode.delete();
				_lines.remove(_focusNode,true);
			} else {
				_focusNode.checkToMerge(_controller,_lines,true);
			}
		}
		return false;
	}
	/** �� ��ü���� fork�� �̷���� �� �� ��ġ�� ����ϴ� �Լ��̴�.
	 */
	public void calcForkPoint(int popupX,int popupY,Point newPoint,boolean originalfork) {
		if (_focus == null || _focusNode == null) {
			return;
		}
		BoolVar swaped = new BoolVar(false);
		_controller.clear(_focus,false);
		Line newline = ((Line)_focus).calcForkPoint(popupX,popupY,newPoint,swaped,originalfork);
		_controller.lowlight(_focus);
		if (newline != null) {
			_controller.currentOrient = newline.orient();
			_controller.lowlight(newline);
			LineNode newnode = new LineNode(newline,null,null);
			newnode.afterList().copy(_focusNode.afterList());
			newnode.beforeList().insert(_focusNode,0);
			LineNodeList alist = _focusNode.afterList();
			LineNode node = alist.getFirst();
			while(node != null) {
				node.afterList().replacePtr(_focusNode,newnode);
				node.beforeList().replacePtr(_focusNode,newnode);
				node = alist.getNext();
			}
			alist.clear();
			alist.insert(newnode,0);
			_lines.insert(newnode,0);
		} else {
			_controller.currentOrient = Const.UNDEFINED;
			if (swaped.v)
				_focusNode.swapBeforeAfter();
		}
	}
	/** �� ��ü�� ����� �ٲٱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
	 */
	public void resizeProlog(boolean flag) {
		resetFlags();
		if (_type == Const.ORDINARY) return;
		_focusNode.makeCascadeFollow(_controller,_lines,_type);
		LineNodeList afters = _focusNode.afterList();
		LineNode ptr = afters.getFirst();
		while (ptr != null) {
			ptr.makeCascadeFollow(_controller,_lines,_type);
			ptr = afters.getNext();
		}
	}
	/** ���� ������ ������ ������ ��� ���� ������ �ٽ� �õ��ϴ� �Լ��̴�.
	 */
	public void retrySetFocus(int x,int y) {
		if (_focus != null) return;
		int minDist = 10000;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_focus = null;
		_focusNode = null;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.coords(p1,p2);
			int d1 = MySys.distance(x,y,p1.x,p1.y);
			int d2 = MySys.distance(x,y,p2.x,p2.y);
			if (d1 < minDist) {
				_focus = line;
				_focusNode = tmp;
				minDist = d1;
			}
			if (d2 < minDist) {
				_focus = line;
				_focusNode = tmp;
				minDist = d2;
			}
			tmp = _lines.getNext();
		}
		if (_focus == null) return;
		((Line)_focus).coords(p1,p2);
		int d1 = MySys.distance(x,y,p1.x,p1.y);
		int d2 = MySys.distance(x,y,p2.x,p2.y);
		if (d1 < d2) {
			x = p1.x; y = p1.y;
		} else {
			x = p2.x; y = p2.y;
		}
	}
	/** ���� ����� ������ ���� ����� �Լ��̴�.
	 */
	public void remake() {
		LineNodeList copied = new LineNodeList();
		copied.copy(_lines);
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			tmp.line().coords(p1,p2);
			if (p1.x == p2.x && p1.y == p2.y) {
				tmp = _lines.getNext();
				continue;
			}
			tmp.afterList().clear();
			tmp.beforeList().clear();
			LineNode ptr1 = copied.getFirst();
			while(ptr1 != null) {
				if (ptr1 != tmp) {
					if (ptr1.line().doesMeet(p1.x,p1.y) && 
						ptr1.line().isObsolete() == false) {
						tmp.insertBefore(ptr1);
					} else if (ptr1.line().doesMeet(p2.x,p2.y) && 
							   ptr1.line().isObsolete() == false) {
						tmp.insertAfter(ptr1);
					}
				}
				ptr1 = copied.getNext();
			}
			tmp = _lines.getNext();
		}
		copied.delete();
	}
	/** ȭ��ǥ ����� ���ִ� �Լ��̴�.
	 */
	public void resetHeadType() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.line().head = Const.HEADNONE;
			node = _lines.getNext();
		}
	}
	/** �� �� ��ü�� ���ο� ������ �߰��ϴ� �Լ��̴�.
	 */
	public void insertALine(Line aline) {
		LineNode newnode = new LineNode(aline,null,null);
		_lines.insert(newnode,0);
	}
	/** Ŭ������ �̵� ���� �� ���� ��ü�� �ɺ��̳� ȭ��ǥ�� �����
	 * �Լ��̴�.
	 */
	public void redrawForArrow() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			if (node.line().head != Const.HEADNONE) {
				_controller.clear(node.line(),false);
				node.line().setDir(Const.NODIR,Const.HEADNONE);
				_controller.lowlight(node.line());
			}
			node = _lines.getNext();
		}
	}
	/** ����Ÿ ��� _style �� ���� �б� access �Լ��̴�.
	 */
	public int style() {
		return _style;
	}
	/** ���� �ٸ� �� ��ü�� �ϳ��� �����ϴ� �Լ��̴�.
	 */
	public boolean localDoAttach(int popupX,int popupY,Line focus,LineNode focusNode,boolean originalattach)
	{
		if (focusNode.mark) return false;
		if (focus.isObsolete()) {
			focusNode.mark = true;
			return false;
		}
		BoolVar swaped = new BoolVar(false);
		swaped.v = focus.checkNear(popupX,popupY);
		if (swaped.v) {
			focusNode.swapBeforeAfter();
		}
		Point newP = focus.last();
		LineNode foundNode = null;
		if (originalattach) {
			LineNode node = _lines.getFirst();
			while (node != null) {
				if (_type == Const.ORDINARY) {
					if (node != focusNode &&
						focusNode.afterList().inList(node) == false &&
						node.line().onEnter(newP.x,newP.y)) {
						foundNode = node;
						break;
					}
				} else /* STRAIGHT */ {
					if (node != focusNode &&
						focusNode.afterList().inList(node) == false &&
						node.line().incl(newP.x,newP.y)) {
						foundNode = node;
						break;
					}
				}
				node = _lines.getNext();
			}
		} else {
			int oldx = newP.x; int oldy = newP.y;
			LineNode node = _lines.getFirst();
			while (node != null) {
				if (node != focusNode &&
					focusNode.afterList().inList(node) == false &&
					node.line().incl(focus,newP)) {
					foundNode = node;
					break;
				}
				node = _lines.getNext();
			}
			if (oldx != newP.x || oldy != newP.y) {
				LineNode tmpNode = focusNode.afterList().getFirst();
				while(tmpNode != null) {
					tmpNode.line().adjustXyValue(oldx,oldy,newP.x,newP.y);
					tmpNode = focusNode.afterList().getNext();
				}
			}
		}
		if (foundNode == null) {
			if (originalattach) _controller.beep("Lines.localDoAttach");
			return false;
		}
		swaped.v = false;
		int x = newP.x;
		int y = newP.y;
		Line newline = foundNode.line().calcForkPoint(x,y,newP,swaped,false);
		if (newline != null) {
			LineNode newnode = new LineNode(newline,null,null);
			newnode.afterList().copy(foundNode.afterList());
			newnode.beforeList().insert(foundNode,0);
			LineNodeList alist = foundNode.afterList();
			LineNode node = alist.getFirst();
			while(node != null) {
				node.afterList().replacePtr(foundNode,newnode);
				node.beforeList().replacePtr(foundNode,newnode);
				node = alist.getNext();
			}
			alist.clear();
			alist.insert(newnode,0);
			_lines.insert(newnode,0);
			LineNode ptr = focusNode.afterList().getFirst();
			while (ptr != null) {
				if (ptr.beforeList().inList(focusNode)) {
					ptr.beforeList().insert(foundNode,0);
					ptr.beforeList().insert(newnode,0);
					if (originalattach) {
						ptr.line().setXY1(newP.x,newP.y);
					}
				} else if (ptr.afterList().inList(focusNode)) {
					ptr.afterList().insert(foundNode,0);
					ptr.afterList().insert(newnode,0);
					if (originalattach) {
						ptr.line().setXY2(newP.x,newP.y);
					}
				}
				foundNode.afterList().insert(ptr,0);
				newnode.beforeList().insert(ptr,0);
				ptr = focusNode.afterList().getNext();
			}
			foundNode.afterList().insert(focusNode,0);
			newnode.beforeList().insert(focusNode,0);
			focusNode.afterList().insert(foundNode,0);
			focusNode.afterList().insert(newnode,0);
			if (originalattach) {
				focus.setXY2(newP.x,newP.y);
			}
		} else {
			if (swaped.v) {
				foundNode.swapBeforeAfter();
			}
			LineNode ptr = foundNode.afterList().getFirst();
			while (ptr != null) {
				if (ptr.beforeList().inList(foundNode)) {
					ptr.beforeList().insert(focusNode,0);
					LineNode tmp = focusNode.afterList().getFirst();
					while(tmp != null) {
						ptr.beforeList().insert(tmp,0);
						tmp = focusNode.afterList().getNext();
					}
				} else if (ptr.afterList().inList(foundNode)) {
					ptr.afterList().insert(focusNode,0);
					LineNode tmp = focusNode.afterList().getFirst();
					while(tmp != null) {
						ptr.afterList().insert(tmp,0);
						tmp = focusNode.afterList().getNext();
					}
				}
				focusNode.afterList().insert(ptr,0);
				ptr = foundNode.afterList().getNext();
			}
			ptr = focusNode.afterList().getFirst();
			while (ptr != null) {
				if (ptr.beforeList().inList(focusNode)) {
					ptr.beforeList().insert(foundNode,0);
					if (originalattach) {
						ptr.line().setXY1(newP.x,newP.y);
					}
					LineNode tmp = foundNode.afterList().getFirst();
					while(tmp != null) {
						ptr.beforeList().insert(tmp,0);
						tmp = foundNode.afterList().getNext();
					}
				} else if (ptr.afterList().inList(focusNode)) {
					ptr.afterList().insert(foundNode,0);
					if (originalattach) {
						ptr.line().setXY2(newP.x,newP.y);
					}
					LineNode tmp = foundNode.afterList().getFirst();
					while(tmp != null) {
						ptr.afterList().insert(tmp,0);
						tmp = foundNode.afterList().getNext();
					}
				}
				foundNode.afterList().insert(ptr,0);
				ptr = focusNode.afterList().getNext();
			}
			focusNode.afterList().insert(foundNode,0);
			foundNode.afterList().insert(focusNode,0);
			if (originalattach) {
				focus.setXY2(newP.x,newP.y);
			}
		}
		if (newline != null) {
			return true;
		} else {
			return false;
		}
	}
	/** �� ��ü�� �̵��ϱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
	 */
	public boolean moveProlog(int oneorsomeptr) {
		if (oneorsomeptr == 0) { // moving all 
			_moveAllFlag = true;
			return true;
		}
		_moveAllFlag = false;
		if (oneorsomeptr == Const.ONEPTR) {
			resetFlags();
			_focusNode.move = true;
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			((Line)_focus).coords(p1,p2);
			_focusNode.checkNearForFollow(_controller,_lines,_type,p1.x,p1.y,Const.ATBEFORE);
			_focusNode.checkNearForFollow(_controller,_lines,_type,p2.x,p2.y,Const.ATAFTER);
			return true;
		} else { // SOMEPTR 
			MySys.myprinterr("Not impelemented yet.Lines::moveProlog()");
			return false;
		}
	}
}
