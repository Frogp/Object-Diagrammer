
package figure;

import modeler.*;

import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Point;

/** 이 클래스는 UML 표기법의 aggregation을 표현하기 위한 것이다.
 * 이 클래스에서 specialized된 기능은 aggregation표기법을 위한 다이아몬드 표기법을
 * 지원하는 것이다.
 */
public final class AggTion extends AnyTion 
{
	Graphics<Integer> sss;
	ArrayList<KMethod> methods;
	int a[];
	int[] x;
	int y[][];
	int [][]z;
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -1098620041014430181L;
	/** aggregation표기법을 위한 다이아몬드 표기법이다.
	 */
	private Diamond _symbol;
	/** aggregation인가 composition인가를 나타내는 flag이다.
	 * _strongflag == true : composition
	 * _strongflag == false : aggregation
	 */
	private boolean _strongflag;
	/** 다아아몬드의 길이
	 */
	private static int DIAMONDLENGTH = 24;
	/** 다이아몬드의 폭
	 */
	private static int DIAMONDOFFSET = DIAMONDLENGTH/3;
	/** 클래스의 이동에 따라 현재 aggregation 객체가 움직일 때, 다이아몬드도
	 * 따라서 움직이게 할 것인가를 나타내는 flag이다.
	 */
	private transient boolean _symbolMoveMark;
	/** 클래스의 이동 전에 현 aggregation 객체의 다이아몬드 표기법을 지우는
	 * 함수이다.
	 */
	public void redrawForArrow() {
		if (_symbol != null) _controller.clear(_symbol,false);
		Graphics g = _controller.getgraphics();
		Graphics eg = _controller.getgraphics();
		eg.setColor(_controller.getBackground());
		super.draw(g,Const.DRAWING,eg);
		eg.dispose();
		g.dispose();
		return;
	}
	/** aggregation을 composition으로 혹은 그 반대로 상태 변화를 하는 함수이다.
	 */
	public void changeAggregationStrength() {
		_strongflag = !_strongflag;
		_controller.draw(this);
	}
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		if (_symbol != null) _symbol.setController(ptr);
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.aggtionPopup());
	}
	/** 다중성을 표시를 위한 좌표를 다시 계산하는 함수이다. 그런데 OMT에서와 달리
	 * UML 표기법에서는 의미가 없다.
	 */
	private void adjustMultiplicity(LineNode node) {
		AnyTionInfoTuple tuple = null;
		Line aline = node.line();
		tuple = getCurrentTuple(aline,0,0);	
		if (tuple == null) {
			return;
		}
		Figure mul =  tuple.multiplicity;
		if (mul == null) return;
		if (mul.whoAreYou().isEqual(Const.IAMPOINT) == false) return;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		aline.coords(p1,p2);
		Point cp = new Point(-1,-1);
		findCenterPositionForAPoint(tuple,p1,p2,cp);
		if (cp.x == -1 || cp.y == -1) return;
		mul.setXY1(cp.x,cp.y);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		if (_symbol != null) {
			_symbol.delete(); _symbol = null;
		}
		super.delete();
	}
	/** 생성자이다.
	 */
	public AggTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
		_symbol = null;
		_symbolMoveMark = false;
		_strongflag = false;
	}
	/** 현재 마우스가 눌러졌을 때 라인 전체를 움직일 것인가 혹은  이름 부분이나, 다중성 부분을
	 * 움직일 것인가를 결정하는 함수이다.
	 */
	public boolean wantMoveFocus(CPoint point) {
		if (super.wantMoveFocus(point) == true) return true;
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (_focus == roleName) {
				return true;
			}
			Figure mul = tuple.multiplicity;
			if (_focus == mul) {
				return true;
			}
			tuple = _infoTuples.getNext();
		}
		return false;
	}
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		super.drawDots(g);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.drawDots(g);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.drawDots(g);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMAGGTION;
	}
	/** aggregation을 위한 다이아몬드 심볼을 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbol(ClassLike classPtr) {
		if (_classes.nOfList() == 1) {
			Line firstLine = _lines.front().line();
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			firstLine.coords(p1,p2);
			ClassLike firstClass = _classes.front();
			AnyTionInfoTuple tuple = new AnyTionInfoTuple(firstClass,p1.x,p1.y);
			_infoTuples.insert(tuple,0);
		}
		Line lastLine = _lines.rear().line();
		Point p = lastLine.last();
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classPtr,p.x,p.y);
		_infoTuples.insert(tuple,0);
		if (_controller.forkFlag) return false;
		Line line = _lines.front().line();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		line.coords(p1,p2);
		ClassLike clss = _classes.front();
		Point startP = new Point(0,0);
		Point endP = new Point(0,0);
		if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
			endP.x = p2.x;
			endP.y = p2.y;	
		} else /* if  (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y))*/ {
			endP.x = p1.x;
			endP.y = p1.y;	
		}	
//		if (line.length() < DIAMONDLENGTH) {
//			return true;
//		}
		line.findDiamondEndCorners(endP,startP,DIAMONDLENGTH,DIAMONDOFFSET);
		_symbol = new Diamond(_controller,startP.x,startP.y,endP.x,endP.y,null);
		_controller.draw(_symbol);
		return false;
	}
	/** aggregation을 위한 다이아몬드 심볼을 일괄적으로 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbolAll() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.beforeList().empty()) {
				adjustMultiplicity(tmp);
			}
			if (tmp.afterList().empty()) {
				adjustMultiplicity(tmp);
			} 
			tmp = _lines.getNext();
		}
		ClassLike clss = _classes.front();
		tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.afterList().empty() || tmp.beforeList().empty()) {
				tmp.line().coords(p1,p2);
				Point startP = new Point(0,0);
				Point endP = new Point(0,0);
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
					endP.x = p1.x;
					endP.y = p1.y;
				} else if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
					endP.x = p2.x;
					endP.y = p2.y;
				} else {
					tmp = _lines.getNext();
					continue;
				}
//				if (tmp.line().length() < DIAMONDLENGTH) {
//					return true;
//				}
				tmp.line().findDiamondEndCorners(endP,startP,DIAMONDLENGTH,DIAMONDOFFSET);
				if (_symbol == null) {
					_symbol = new Diamond(_controller,startP.x,startP.y,endP.x,endP.y,null);		
				} else {
					_symbol.setXY1(startP.x,startP.y);
					_symbol.setXY2(endP.x,endP.y);
				}
				return false;
			}
			tmp = _lines.getNext();
		}
		return true;
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		super.draw(g,style,specialdcp);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.draw(g,style,specialdcp);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.draw(g,style,specialdcp);
			}
			tuple = _infoTuples.getNext();
		}
		if (style == Const.RUBBERBANDING && _symbolMoveMark == false) return;
		if (_symbolMoveMark) {
			if (_symbol != null) _symbol.draw(g,style,specialdcp);
		} else {
			if (_symbol != null) {
				if (_strongflag) _symbol.drawEmpty(g,style,g);
				else _symbol.drawEmpty(g,style,specialdcp);
			}
		}
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		super.move(g,dx,dy);
		if (_moveAllFlag == false) return;
		if (_symbol != null) _symbol.moveCoord(dx,dy);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			tuple.moveCoord(dx,dy);
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.move(g,dx,dy);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		if (_symbol != null) _symbol.clear(g,expose);
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		super.makeRegion();
		if (_symbol != null) {
			_symbol.makeRegion();
			CRgn.myUnionRgn(_region,_symbol.region(),_region);
			CRgn.myUnionRgn(_maxRegion,_symbol.maxRegion(),_maxRegion);
		}
	}
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
	 */
	public boolean checkInRegion(CRgn someregion) {
		if (super.checkInRegion(someregion)) return true;
		if (_symbol != null && _symbol.checkInRegion(someregion)) return true;
		return false;
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		if (_symbol != null) _symbol.setInCanvas(flag);
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		AggTion copied;
		if (ptr == null) {
			copied = new AggTion(_controller,null,null);
		} else {
			copied = (AggTion)ptr;
		}
		if (_symbol != null) {
			copied._symbol = (Diamond)_symbol.born(null);
		}
		return(super.born((Figure)copied));
	}
	/** 현재 마우스의 위치에 따른 해당 선분의 꼭지점 정보를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int x,int y) {
		Line focusLine;
		if (aline == null) focusLine = (Line)_focus;
		else focusLine = aline;
		if (focusLine == null) {
			_controller.beep("AggTion.getCurrentTuple");
			return null;
		}
		if (_lines.nOfList() == 1) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			focusLine.coords(p1,p2);
			AnyTionInfoTuple tuple = _infoTuples.findTupleFor(p1.x,p1.y);
			if (tuple.classPtr != _classes.front()) return tuple;
			return _infoTuples.findTupleFor(p2.x,p2.y);
		}
		return _infoTuples.getTupleForLine(focusLine);
	}
	/** OMT의 다중성 표현 시에 조그만 원의 가운데 좌표를 찾아내는 함수이다. UML에서는 필요없지만
	 * 만약을 위해 reserve한다.
	 */
	public void findCenterPositionForAPoint(AnyTionInfoTuple tuple,Point p1,Point p2,Point cp) {
		cp.x = -1; cp.y = -1;
		int x1 = p1.x; int y1 = p1.y;
		int x2 = p2.x; int y2 = p2.y;
		if (tuple.meet(x1,y1)) {
			if (x1 == x2) {
				// vertical line
				cp.x = x1;
				if (y1 > y2) {
					cp.y = y1 - Const.POINTRADIUS;	
				} else {
					cp.y = y1 + Const.POINTRADIUS;	
				}
			} else {
				// horizontal line
				cp.y = y1;
				if (x1 > x2) {
					cp.x = x1 - Const.POINTRADIUS;
				} else {
					cp.x = x1 + Const.POINTRADIUS;
				}
			}
		} else if (tuple.meet(x2,y2)) {
			if (x1 == x2) {
				// vertical line
				cp.x = x1;
				if (y1 > y2) {
					cp.y = y2 + Const.POINTRADIUS;
				} else {
					cp.y = y2 - Const.POINTRADIUS;
				}
			} else {
				// horizontal line
				cp.y = y1;
				if (x1 > x2) {
					cp.x = x2 + Const.POINTRADIUS;
				} else {
					cp.x = x2 - Const.POINTRADIUS;
				}
			}
		}
		return;
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.aggtionPopup();
	}
	/** 이 객체를 위한 팝업 메뉴를 display하는 함수이다.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		if (_focus.whoAreYou().isEqual(Const.IAMSINGLELINETEXT)) {
			_focus.popup(event);
			return;
		}
		if (_strongflag) {
			//		((AggTionPopup)_popup).strengthB.setLabel("Weak");
		} else {
			//		((AggTionPopup)_popup).strengthB.setLabel("Strong");
		}
		boolean isFocusBoundaryLine = isBoundary(_focusNode);
		if (isFocusBoundaryLine || _lines.nOfList() == 1) {
			((AggTionPopup)_popup).roleB.setEnabled(true);
			((AggTionPopup)_popup).cardinalB.setEnabled(true);
		} else {
			((AggTionPopup)_popup).roleB.setEnabled(false);
			((AggTionPopup)_popup).cardinalB.setEnabled(false);
		}
		_popup.popup(event);
	}
	/** aggregation의 다이아몬드 표기법 부분을 지우는 함수이다.
	 */
	public void clearModelSpecificSymbol() {
		_controller.clear(_symbol,true);
		return;
	}
	/** parameter로 넘겨진 텍스트 객체가 현재 aggregation에서 어떠한 목적으로 사용되는
	 * 텍스트 객체인가를 판별하는 함수이다.
	 */
	public int whatIsMyRole(SingleLineText text) {
		if (_infoTuples.inlistForRole(text)) {
			return Const.ROLENAME;
		} else if (_infoTuples.inlistForMultiplicity((Figure)text)) {
			return Const.MULTIPLICITYNAME;
		}
		return super.whatIsMyRole(text);
	}
	/** 이 관계 객체가 클래스의 이동을 따라갈 필요가 있을때 이동의 시작 시점에서
	 * 따라갈 준비를 하는 함수이다. 주로 클래스를 따라가야 될 선분들을 결정하고
	 * 그 선분들의 상태 값을 초기화 한다.
	 */
	public void followClassProlog(ClassLike aclass) {
		super.followClassProlog(aclass);
		if (aclass == _classes.front()) {
			_symbolMoveMark = true;
		} else {
			_symbolMoveMark = false;
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			tuple.mark = false;
			if (tuple.classPtr == aclass) {
				int x = tuple.x();
				int y = tuple.y();
				LineNode tmp = findNodeFor(x,y);
				if (tmp != null) {
					tuple.mark = true;
					tmp.mark = true;
					tmp.makeCascadeFollow(_controller,_lines,_type);
					if (tmp.beforeList().empty()) {
						Point p1 = new Point(0,0);
						Point p2 = new Point(0,0);
						tmp.line().coords(p1,p2);
						Line newline = new Line(_controller,p1.x,p1.y,null,Const.STRAIGHT);
						newline.setOrient(Line.invertOrient(tmp.line().orient()));
						LineNode newnode = new LineNode(newline,null,null);
						newnode.cascade = true;
						newnode.afterList().insert(tmp,0);
						tmp.beforeList().insert(newnode,0);
						_lines.insert(newnode,0);
					}
				}
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 관계와 연결된 클래스가 움직일 때 그 클래스에 따라서 이동하는 함수이다.
	 */
	public void followClass(Graphics g,int dx,int dy) {
		super.followClass(g,dx,dy);
		if (_symbolMoveMark) {
			_symbol.move(g,dx,dy);
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
				if (tuple.role != null) 
					tuple.role.move(g,dx,dy);
				if (tuple.multiplicity != null) 
					tuple.multiplicity.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 관계 객체의 이동이 모두 끝난 후, 관련되는 flag 값을 리셋하는 함수이다.
	 */
	public void resetMarkForMove() {
		super.resetMarkForMove();
		_symbolMoveMark = false;
	}
}