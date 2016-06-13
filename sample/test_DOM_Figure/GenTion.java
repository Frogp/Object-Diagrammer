package figure;

import java.awt.*;
import java.awt.Point;
import java.io.*;
import java.awt.event.*;
/** 이 클래스는 UML 표기법의 generalization을 표현하기 위한 것이다.
 * 이 클래스에서 specialized된 기능은 generalization 표기법을 위한 삼각형 표기법을
 * 지원하는 것이다.
 */
public final 
	class GenTion extends AnyTion {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -6210313719958032761L;
	/** 삼각형의 길이
	 */
	private static int TRIANGLELENGTH = 20;
	/** 일반화 관계를 위한 삼각형 표기법이다.
	 */
	private Triangle _symbol;
	/** 선분의 이동시에 심볼도 이동되는 가를 나타내는 flag
	 */
	private transient boolean _symbolMoveMark;
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
		setPopupPtr(controller.gentionPopup());
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
	public GenTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
		_symbol = null;
		_symbolMoveMark = false;
	}
	/** 삼각형 길이 읽기 access 함수
	 */
	public int symbolLength() {
		return TRIANGLELENGTH;
	}  
	/** 클래스의 이동 전에 현 generalization 객체의 삼각형 표기법을 지우는
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
	/** 삼각형 심볼을 인자에의해 주어진 위치에 설정하는 함수이다.
	 */
	public void setSymbol(int sx,int sy,int ex,int ey) {
		_symbol = new Triangle(_controller,sx,sy,ex,ey,null);
	}
	/** 인자 값을 반영하는 AnyTionInfoTuple 을 구성하는 함수이다.
	 */
	public void makeATuple(int x,int y,ClassLike classptr) {
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classptr,x,y);
		_infoTuples.insert(tuple,0);
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMGENTION;
	}
	/** generalization을 위한 삼각형 심볼을 재 설정하는 함수이다.
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
		int endX = 0;
		int endY = 0;
		if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
			endX = p1.x;
			endY = p1.y;
		} else if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
			endX = p2.x;
			endY = p2.y;
		} else {
			return true;
		}
		line.findTriangleStartPoints(endX,endY,startP,TRIANGLELENGTH);
		_symbol = new Triangle(_controller,startP.x,startP.y,endX,endY,null);
		return false;
	}
	/** generalization을 위한 삼각형 심볼을 일괄적으로 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbolAll() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		ClassLike clss = _classes.front();
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.afterList().empty() || tmp.beforeList().empty()) {
				tmp.line().coords(p1,p2);
				Point startP = new Point(0,0);
				int endX,endY;
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
					endX = p1.x;
					endY = p1.y;
				} else if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
					endX = p2.x;
					endY = p2.y;
				} else {
					tmp = _lines.getNext();
					continue;
				}
				tmp.line().findTriangleStartPoints(endX,endY,startP,TRIANGLELENGTH);
				if (_symbol == null) {
					_symbol = new Triangle(_controller,startP.x,startP.y,endX,endY,null);		
				} else {
					_symbol.setXY1(startP.x,startP.y);
					_symbol.setXY2(endX,endY);
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
		if (style == Const.RUBBERBANDING && _symbolMoveMark == false) return;
		if (_symbolMoveMark) {
			if (_symbol != null) _symbol.draw(g,style,specialdcp);
		} else {
			if (_symbol != null) _symbol.drawEmpty(g,style,specialdcp);
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
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		if (_symbol != null) _symbol.clear(g,true);
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
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		GenTion copied;
		if (ptr == null) {
			copied = new GenTion(_controller,null,null);
		} else {
			copied = (GenTion)ptr;
		}
		if (_symbol != null) {
			copied._symbol = (Triangle)_symbol.born(null);
		}
		return(super.born((Figure)copied));
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
	/** 화면에 그려진 관계의 심볼이 이상하지 않은가를 확인하는 함수이다.
	 */
	public boolean validForModelSpecific(ClassLike clss) {
		/*
		if (_classes.front().hasAncestor(clss)) return false;
		if (_classes.front().hasChildAlready(clss)) return false;
		*/
		return true;
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.gentionPopup();
	}  
	/** 이 객체를 위한 팝업 메뉴를 display하는 함수이다.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		if (_focus == _name) {
			_name.popup(event);
			return;
		}
		_popup.popup(event);
	}
	/** generalization의 삼각형 표기법 부분을 지우는 함수이다.
	 */
	public void clearModelSpecificSymbol() {
		_controller.clear(_symbol,true);
		return;
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
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 관계와 연결된 클래스가 움직일 때 그 클래스에 따라서 이동하는 함수이다.
	 */
	public void followClass(int dx,int dy) {
		super.followClass(dx,dy);
		if (_symbolMoveMark) {
			_symbol.moveCoord(dx,dy);
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
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
