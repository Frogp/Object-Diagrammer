package figure;

import modeler.*;

import java.awt.*;
import java.awt.Point;
import java.io.*;
import java.awt.event.*;
/** 이 클래스는 UML 표기법의 dependency를 표현하기 위한 것이다.
 * 이 클래스에서 specialized된 기능은 dependency을 위해 라인의 끝에
 * 화살표가 사용되는 것이다.
 */
public final 
	class ColTion extends AnyTion {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -2105700459025229873L;
	/** 생성자이다.
	 */
	public ColTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		super.delete();
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
			tuple = _infoTuples.getNext();
		}
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMCOLTION;
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.coltionPopup());
	}
	/** 의존 관계을 위한 심볼을 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbol(ClassLike classPtr) {
		if (_classes.nOfList() == 1) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			Line firstLine = _lines.front().line();
			firstLine.coords(p1,p2);
			ClassLike firstClass = _classes.front();
			AnyTionInfoTuple tuple = new AnyTionInfoTuple(firstClass,p1.x,p1.y);
			_infoTuples.insert(tuple,0);
		}
		Line lastLine = _lines.rear().line();
//		if (lastLine.length() < _controller.arrowLength()) return true;
		lastLine.setDir(Const.NORMALDIR,Const.HEADARROW1);
		Point p = lastLine.last();
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classPtr,p.x,p.y);
		_infoTuples.insert(tuple,0);
		return false;
	}
	/** 관계를 위한 심볼을 일괄적으로 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbolAll() {
		int arrowlength = _controller.arrowLength();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		ClassLike clss = _classes.front();
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.beforeList().empty()) {
				tmp.line().coords(p1,p2);
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y) == false) {
//					if (tmp.line().length() < arrowlength) {
//						return true;
//					}
					tmp.line().setDir(Const.INVERTDIR,Const.HEADARROW1);
				}
			}
			if (tmp.afterList().empty()) {
				tmp.line().coords(p1,p2);
				if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y) == false) {
//					if (tmp.line().length() < arrowlength) {
//						return true;
//					}
					tmp.line().setDir(Const.NORMALDIR,Const.HEADARROW1);
				}
			}
			tmp = _lines.getNext();
		}
		return false;
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		super.drawDashed(g,style,specialdcp);
		if (_name != null) _name.draw(g,style,specialdcp);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.draw(g,style,specialdcp);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		super.move(g,dx,dy);
		if (_moveAllFlag == false) return;
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			tuple.moveCoord(dx,dy);
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		ColTion copied;
		if (ptr == null) {
			copied = new ColTion(null,null,null);
		} else {
			copied = (ColTion)ptr;
		}
		return(super.born((Figure)copied));
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
	 */
	public boolean onEnter(int x,int y) {
		_focus = null;
		return super.onEnter(x,y);
	}
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
	 */
	public boolean checkInRegion(CRgn someregion) {
		if (super.checkInRegion(someregion)) return true;
		return false;
	}
	/** 현재 마우스의 위치에 따른 해당 선분의 꼭지점 정보를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int x,int y) {
		Line focusLine;
		if (aline == null) focusLine = (Line)_focus;
		else focusLine = aline;
		if (focusLine == null) {
			_controller.beep("ColTion.AnyTionInfoTuple");
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
	/** AITList의 resetRoleNameFor() 함수 참조
	 */
	public void resetRoleNameFor(SingleLineText text) {
		_infoTuples.resetRoleNameFor(text);
		return;
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.coltionPopup();
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
		boolean isFocusBoundaryLine = isBoundary(_focusNode);
		if (isFocusBoundaryLine || _lines.nOfList() == 1) {
			((ColTionPopup)_popup).roleB.setEnabled(true);
		} else {
			((ColTionPopup)_popup).roleB.setEnabled(false);
		}
		_popup.popup(event);
	}
	/** parameter로 넘겨진 텍스트 객체가 현재 aggregation에서 어떠한 목적으로 사용되는
	 * 텍스트 객체인가를 판별하는 함수이다.
	 */
	public int whatIsMyRole(SingleLineText text) {
		if (_infoTuples.inlistForRole(text)) {
			return Const.ROLENAME;
		}
		return super.whatIsMyRole(text);
	}
	/** 이 관계 객체가 클래스의 이동을 따라갈 필요가 있을때 이동의 시작 시점에서
	 * 따라갈 준비를 하는 함수이다. 주로 클래스를 따라가야 될 선분들을 결정하고
	 * 그 선분들의 상태 값을 초기화 한다.
	 */
	public void followClassProlog(ClassLike aclass) {
		super.followClassProlog(aclass);
		resetHeadType();
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
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
				if (tuple.role != null) {
					tuple.role.move(g,dx,dy);
				}
			}
			tuple = _infoTuples.getNext();
		}
	}
}