package figure;

import java.awt.*;
import java.io.*;
import java.awt.Point;
/** 이 클래스는 여러개의 선분으로 이루어지는 선 객체를 나타내기 위한 클래스이다.
 */
public 
	class Lines extends Figure {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 6812527262275365375L;
	/** ORDINARY or STRAIGHT */
	protected int _type;
	/** SOLID or DASHED */
	protected int _style;
	/** 선분 노드들을 저장하는 리스트
	 */
	protected LineNodeList _lines;
	/** 선 그리기 중의 마지막 x 좌표
	 */
	protected int _lastx;
	/** 선 그리기 중의 마지막 y 좌표
	 */
	protected int _lasty;
	/** 선 객체의 이동 시 모든 선분이 이동되어야 함을 나타내는 flag
	 */
	protected boolean _moveAllFlag;
	/** 초점이 되는 선분 노드
	 */
	protected transient LineNode _focusNode;
	/** 초점이 되는 선분
	 */
	protected transient Figure _focus;
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.line().setController(ptr);
			node = _lines.getNext();
		}
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.line().setInCanvas(flag);
			node = _lines.getNext();
		}
	}
	/** 선분 노드들에 있는 mark flag 값을 초기화하는 함수이다.
	 */
	protected void resetMark() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.mark = false;
			node = _lines.getNext();
		}
	}
	/** 선분 노드들에 있는 move flag 값을 초기화하는 함수이다.
	 */
	protected void resetMove() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.mark = false;
			node = _lines.getNext();
		}
	}
	/** 선분 노드들에 있는 모든 flag 값들을 초기화하는 함수이다.
	 */
	protected void resetFlags() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.resetFlags();
			node = _lines.getNext();
		}
	}
	/** 선분 노드들에 있는 followed flag 값을 초기화하는 함수이다.
	 */
	protected void resetFollowed() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.followed = false;
			node = _lines.getNext();
		}
	}
	/** 선분들 중에서 인자로 명시된 좌표를 포함하는 선분 노드를 찾는 함수이다.
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
	/** 초점으로 선택된 선분을 지우는 함수이다.
	 */
	protected void removeFocusLine() {
		if (_controller != null) _controller.clear(_focus,true);
		_lines.remove(_focusNode,true);
		_focusNode.delete();
		_focus.delete();
		_focusNode = null;
		_focus = null;
	}
	/** 선분들 중에서 겹치는 것들을 제거하는 함수이다.
	 */
	protected void checkZammedLines() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			if (node.move == false)
				node.checkIfZammed();
			node = _lines.getNext();
		}
	}
	/** 이동중인 인접 선분에 따라갈 수 있도록 조치하는 함수이다.
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
	/** 선분들로 하여금 주어진 변위 만큼 좌표 값을 이동하게하는 함수이다.
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
	/** 선분들로 하여금 주어진 변위 만큼 좌표 값을 이동하게하는 함수이다.
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
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
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
	/** 생성자이다.
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
	/** 데이타 멤버 _type에 대한 access 함수
	 */
	public int drawingType() {
		if (_type == Const.STRAIGHT) {
			return Const.SLINESPTR;
		} else {
			return Const.LINESPTR;
		}
	}
	/** 인자로 주어진 선분에 해당하는 선분 노드를 찾는 함수이다.
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
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMLINES;
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
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
	/** 그림 객체의 초점 부분 객체를 복사하는 함수이다.
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
	/** 이 객체의 중앙에 대한 좌표값을 구하는 함수이다.
	 */
	public Point center() {
		if (_focusNode != null) {
			return _focusNode.line().center();
		} else {
			return _lines.top().line().center();
		}
	}
	/** 이 객체의 마지막 좌표값을 구하는 함수이다.
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
	/** 이 객체의 선분 리스트에 새로운 선분을 삽입하는 함수이다.
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
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.clear(g,expose);
			tmp = _lines.getNext();
		}
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 이 함수를 통하여 GraphicController 객체의
	 * 최대 최소 좌표값에 반영된다.
	 */
	public void minMaxXY() {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.minMaxXY();
			tmp = _lines.getNext();
		}
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 인자들 값에 반영되어 돌려진다.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.getMinMaxXY(minP,maxP);
			tmp = _lines.getNext();
		}
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
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
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
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
	/** 현재 마우스가 눌러졌을 때 선분을 움직일 것인가를 결정하는 함수이다.
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
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
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
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			Line line = tmp.line();
			line.drawDots(g);
			tmp = _lines.getNext();
		}
	}
	/** 좌표만 이동 시키는 함수이다.
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
	/** 이 선을 점선으로 그려주는 함수이다.
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
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
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
	/** 초점 선분의 _x2, _y2 좌표값을 설정하는 함수이다.
	 */
	public void setXY2(int x,int y) {
		if (_focusNode == null) return;
		_focusNode.line().setXY2(x,y);
	}
	/** 그림 크기를 조절하기 위해 초점 선분의 _x2, _y2 좌표값을 설정하는 함수이다.
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
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 * 현재 마우스의 새로운 위치 값을 이용하여 rubberbanding 한다.
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
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
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
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
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
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
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
	/** 데이타 멤버 _focus에 대한 읽기 access 함수.
	 */
	public Figure focus() {
		return (Figure)_focus;
	}
	/** 마우스의 위치를 이용하여 초섬 선분을 찾는 함수이다.
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
	/** 촛점 선분을 리셋하는 함수이다.
	 */
	public void resetFocus() {
		_focus = null;
		_focusNode = null;
	}
	/** 이 선에 포함된 선분의 갯수를 돌려주능 함수이다.
	 */
	public int nOfFigures() {
		return _lines.nOfList();
	}
	/** 그림 객체의 마지막 좌표(x2,y2) 값이 인자에 명시된 좌표 값과 가까워지도록 설정하는 함수이다.
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
	/** 표기법 객체의 편집이 끝난 이후에 호출되는 함수로서 일관성 검사,
	 * 수치 보정들을 수행한다.
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
	/** 선 객체에서 fork가 이루어질 때 그 위치를 계산하는 함수이다.
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
	/** 이 객체의 모양을 바꾸기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
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
	/** 초점 선분의 설정이 실패한 경우 초점 설정을 다시 시도하는 함수이다.
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
	/** 선분 노드의 내용을 새로 만드는 함수이다.
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
	/** 화살표 모양을 없애는 함수이다.
	 */
	public void resetHeadType() {
		LineNode node = _lines.getFirst();
		while(node != null) {
			node.line().head = Const.HEADNONE;
			node = _lines.getNext();
		}
	}
	/** 이 선 객체에 새로운 선분을 추가하는 함수이다.
	 */
	public void insertALine(Line aline) {
		LineNode newnode = new LineNode(aline,null,null);
		_lines.insert(newnode,0);
	}
	/** 클래스의 이동 전에 현 관계 객체의 심볼이나 화살표를 지우는
	 * 함수이다.
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
	/** 데이타 멤버 _style 에 대한 읽기 access 함수이다.
	 */
	public int style() {
		return _style;
	}
	/** 서로 다른 선 객체를 하나로 연결하는 함수이다.
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
	/** 이 객체를 이동하기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
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
