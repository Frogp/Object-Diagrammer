package figure;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
/** 이 클래스는 UML 표기법 중에서 주석으로 사용되는 노트 표기법 객체를
 * 그려주기 위한 것이다.
 */
public final 
	class Note extends Box {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 5738864656242680262L;
	/** 한 문자의 폭
	 */
	private int _deltaH;
	/** 한 문자의 높이
	 */
	private int _deltaV;
	/** 노트의 내용을 저장하는 텍스트
	 */
	private NoteText _noteContent;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 x 변위
	 */
	private static int _GapX = 8;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 y 변위
	 */
	private static int _GapY = 10;
	/** 노트 표기법의 오른쪽 위에 있는 삼각형의 폭
	 */
	private static int _vertexGap = 9;
	/** 한 줄의 최소 길이 (문자 단위)
	 */
	private static int _NofCharsinLine = 10;
	/** 텍스트 내용 중 가장 긴 줄의 길이
	 */
	public int _maxchars;
	/** 이 객체에서 활성화 상태인 텍스트 객체에 대한 레퍼런스
	 */
	private transient Figure _focus;
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_noteContent.setController(ptr);
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simpleEDCPopup());
	}
	/** 가장 긴 문자열의 길이를 구하는 함수이다.
	 */
	public int maxChars() {
		_maxchars = _noteContent.width();
		if (_NofCharsinLine-1 > _maxchars) _maxchars = _NofCharsinLine-1;
		return _maxchars;
	}
	/** 인자에서 주어진 크기 만큼 클래스의 폭을 조정한다. 인자의 단위는 문자단위이다.
	 */
	public void modifyWidth(int dx) {
		int deltax;
		deltax = dx * _deltaH;
		if (dx > 0) {
			clearUpperLeftArea(0);
			Box tmp = new Box(_controller,_x2,_y1,
							  _x2+deltax,_y2,null);
			tmp.makeRegion();
			_controller.clear(tmp,false);
			if (tmp != null) tmp.delete();
			_x2 = _x2 + deltax;
			_controller.lowlight((Figure)this);
		} else {
			clearUpperLeftArea(-deltax+_vertexGap);
			Box tmp = new Box(_controller,_x2+deltax,_y1,
							  _x2,_y2,null);
			tmp.makeRegion();
			_controller.clear(tmp,false);
			if (tmp != null) tmp.delete();
			_x2 = _x2 + deltax;
			_controller.lowlight((Figure)this);
		}
		makeRegion();
	}
	/** 인자에서 주어진 크기 만큼 클래스의 높이를 조정한다. 인자의 단위는 문자단위이다.
	 */
	public void modifyHeight(int dy) {
		int deltay;
		deltay = dy * _deltaV;
		if ( dy > 0 ) {
			Box tmp = new Box(_controller,_x1,_y2,
							  _x2,_y2+deltay,null);
			tmp.makeRegion();
			_controller.clear(tmp,false);
			tmp.delete();
			_y2 = _y2 + deltay;
			_controller.lowlight((Figure)this);
		} else {
			Box tmp = new Box(_controller,_x1,_y2+deltay,
							  _x2,_y2,null);
			tmp.makeRegion();
			_controller.clear(tmp,true);
			tmp.delete();
			_y2 = _y2 + deltay;
			_controller.lowlight((Figure)this);
		}
		makeRegion();
	}
	/** 가장 긴 문자열의 길이를 구하는 함수이다.
	 */
	private int maxchars() {
		int maxchars = _NofCharsinLine;
		if ( maxchars < _noteContent.width() ) {
			maxchars = _noteContent.width();
		}
		return maxchars;
	}
	/** 메뉴 선택에 의해 새로운 노트를 만드는 함수이다.
	 */
	public static void makeNewNote(GraphicController controller,int popupX,int popupY,int fontSizeH,int fontSizeV,Popup popupPtr) {
		Note aNote =
					new Note(controller,popupX,popupY,
							 fontSizeH,fontSizeV,popupPtr);
		// normal case follows
		controller.startEditableObject(aNote,aNote._noteContent);
	}
	/** 생성자이다.
	 */
	Note(GraphicController controller,int x1,int y1,int deltaH,int deltaV,Popup popup) {
		super(controller,x1,y1,
			  x1+deltaH*_NofCharsinLine+2*_GapX+deltaH,
			  y1+deltaV+2*_GapY,popup);
		_deltaH = deltaH;
		_deltaV = deltaV;
		_stackingFlag = true;

		int width = _deltaH*_NofCharsinLine + 2*_GapX + _deltaH;
		int height = _deltaV + 2*_GapY;
		_maxchars = _NofCharsinLine;
		_noteContent = new NoteText(controller,this,
									   x1+_GapX+_deltaH,
									   y1+_GapY,null);
		_focus = (Figure)this;
	}
	/** 생성자이다. 이 생성자는 객체 복사시에 주로 사용된다.
	 */
	Note(GraphicController controller,int x1,int y1,int x2,int y2,int deltaH,int deltaV,Popup popup) {
		super(controller,x1,y1,x2,y2,popup);

		_stackingFlag = true;
		_focus = (Figure)this;
		_deltaH = deltaH;
		_deltaV = deltaV;
		_maxchars = _NofCharsinLine;
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		_noteContent.delete(); _noteContent = null;
		_focus = null;
	}
	/** 데이타 멤버 _focus에 새로운 값을 set하는 access 함수이다.
	 */
	public void setThisFocus(Figure focus) {
		_focus = focus;
	}
	/** 데이타 멤버 _focus에 null 값을 set하는 access 함수이다.
	 */
	public void resetFocus() {
		_focus = (Figure)this;
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMNOTE;
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_noteContent.setInCanvas(flag);
	}
	/** 좌표만 이동 시키는 함수이다.
	 */
	public void moveCoord(int dx,int dy) {
		super.moveCoord(dx,dy);
		_noteContent.moveCoord(dx,dy);
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simpleEDCPopup();
	}  
	/** 이 객체를 위한 팝업 메뉴를 display하는 함수이다.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		_popup.popup(event);
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
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

		myOwnDraw(g);

		_x1 = _x1 + dx;
		_y1 = _y1 + dy;
		if (_x1 > newx ) x = newx;
		else x = _x1;
		if (_y1 > newy ) y = newy;
		else y = _y1;
		width = Math.abs(newx - _x1);
		height = Math.abs(newy - _y1);

		_x2 = newx;
		_y2 = newy;

		myOwnDraw(g);

		_noteContent.move(g,dx,dy);
	}
	/** 이 표기법의 오른쪽 위 삼각형 부분을 지우는 함수이다.
	 */
	private void clearUpperLeftArea(int w) {
		Box tmp;
		if (w == 0) {
			tmp = new Box(_controller,_x2-_vertexGap,_y1,
						  _x2,_y1+_vertexGap,null);
		} else {
			tmp = new Box(_controller,_x2-w,_y1,
						  _x2,_y1+_vertexGap,null);
		}
		_controller.clear(tmp,false);
		tmp.delete();	
	}
	/** 이 표기법의 삼각형 부분을 그리는 함수이다.
	 */
	private void myOwnDraw(Graphics g) {
		int vertexGap = _vertexGap;
		g.drawLine(_x1,_y1,_x1,_y2);
		g.drawLine(_x1,_y1,_x2-vertexGap,_y1);
		g.drawLine(_x2,_y1+vertexGap,_x2,_y2);
		g.drawLine(_x1,_y2,_x2,_y2);
		g.drawLine(_x2-vertexGap,_y1,_x2,_y1+vertexGap);
		g.drawLine(_x2-vertexGap,_y1,_x2-vertexGap,_y1+vertexGap);
		g.drawLine(_x2-vertexGap,_y1+vertexGap,_x2,_y1+vertexGap);
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int style,Graphics specialgc) {
		if (!_inCanvas) return;

		if (style == Const.LOWLIGHTING && _doted == true) {
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

		myOwnDraw(g);

		if (style == Const.DRAWING) {
			_noteContent.draw(g,style,specialgc);
		}
	}
	/** 이 객체에 대해 텍스트 편집 작업을 시작도록하는 함수이다.
	 */
	public void localStartEdit(int popupX,int popupY) {
		_controller.clear(this,true);
		_controller.draw(this);
		NoteText field = _noteContent;
//		field.screen().activate();
		_controller._editingTag = true;
		_controller.setCurrentFocus(this);
		_focus = (Figure)field;
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		Note copied;
		if (ptr == null) {
			copied = new Note(_controller,0,0,0,0,0,0,null);
		} else {
			copied = (Note)ptr;
		}
		copied._popup = _popup;
		copied._controller = _controller;
		copied._stackingFlag = _stackingFlag;
		copied._focus = copied;
		copied._deltaH = _deltaH;
		copied._deltaV = _deltaV;
		copied._maxchars = _maxchars;
		copied._noteContent = (NoteText)_noteContent.born(null);
		copied._noteContent.setNotePtr(copied);
		return(super.born((Figure)copied));
	}
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantResize(CPoint point) {
		return false;
	}
	/** 데이타 멤버 _focus에 대한 읽기 access 함수
	 */
	public Figure focus() {
		return _focus;
	}
	/** 데이타 상수 _NofCharsinLine 에 대한 멤버 함수이다.
	 */
	public static int NofCharsinLine() {
		return _NofCharsinLine;
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
	 */
	public boolean onEnter(int x,int y) {
		if (super.onEnter(x,y)) {
			_focus = (Figure)this;
			return true;
		} else {
			_focus = (Figure)this;
			return false;
		}
	}
}