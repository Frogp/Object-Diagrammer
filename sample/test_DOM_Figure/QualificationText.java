package figure;

import java.awt.*;
import java.awt.Point;
import java.io.*;
import java.awt.event.*;
/** 이 클래스는 UML 표기법의 qualification 심볼을 처리하기 위한 것이다.
 */
public final 
	class QualificationText extends Text {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 7807224294718618899L;
	/** 이 qualification이 소속된 결합 관계에 대한 레퍼런스 
	 */
	private AssTion _asstionPtr;
	/** 이 qualification이 클래스의 어느 쪽에 붙어있는 가를 나타내는 변수 
	 */
	private int _orient;
	/** qualification 심볼의 중앙 부분에 위치한 선분
	 */
	private Line _dynamicLine;
	/** qualification 텍스트의 시작 위치 x
	 */
	private int _startX;
	/** qualification 텍스트의 시작 위치 y
	 */
	private int _startY;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 x 변위
	 */
	private static int _GapX = 1;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 y 변위
	 */
	private static int _GapY = 5;
	/** qualification 텍스트를 둘러싸는 사각형 객체
	 */
	private Box _boundary;
	/** 이 객체가 편집 후에 파괴되어야 함을 나타내는 flag
	 */
	private boolean _shouldBeDeleted;
	/** qualification 텍스트의 최대 크기
	 */
	private int _maxWH;
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simplePopup();
	}  
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_boundary.setController(ptr);
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simplePopup());
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
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
	/** qualification이 그려질 수 있는 최소 공간이 확보되어있는가를
	 * 검사하는 함수이다.
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
	/** 데이타 멤버 _dynamicLine의 값을 세트하는 함수
	 */
	public void setDynamicLine(Line dynamicLine) {
		_dynamicLine = dynamicLine;
		if (_dynamicLine != null) return;
		_dynamicLine = _asstionPtr.getLineFor(_startX,_startY);
	}
	/** 데이타 멤버 _asstionPtr 대한 쓰기 access 함수
	 */
	public void setAsstionPtr(AssTion ptr) {
		_asstionPtr = ptr;
	}
	/** 데이타 멤버 _orient에 대한 읽기 access 함수
	 */
	public int orient() {
		return _orient;
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		_asstionPtr = null;
		_boundary.delete(); _boundary = null;
	}
	/** 생성자이다.
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
	/** qualification 의 좌표 값을 출발점으로 부터 다시 계산하는 함수이다.
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
	/** 텍스트 객체에 대한 편집이 끝날 때 빈 라인들을 제거하는 함수이다.
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
	/** qualification 텍스트의 편집 시에 폭의 변화에 따라 x 좌표 값을 조정해주는 함수이다.
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
	/** qualification 텍스트의 편집 시에 길이의 변화에 따라 y 좌표 값을 조정해주는 함수이다.
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
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMQUALIFICATIONTEXT;
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_boundary.setInCanvas(flag);
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
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
	/** 객체의 파괴 이전에 일관성 문제가 없음을 확인하는 함수이다.
	 */
	public void ensureConsistencyBeforeDelete() {
		_asstionPtr.resetQualificationFor(this);
		setDynamicLine(null);
		_dynamicLine = null;
	}
	/** 데이타 멤버 _shouldBeDeleted에 대한 읽기 access 함수
	 */
	public boolean shouldBeDeleted() {
		return _shouldBeDeleted;
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
	/** 키보드로 부터 [Enter] 키가 입력되었을 때 줄 바꿈을 처리하는 함수이다.
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
	/** 텍스트의 한 줄을 지우는 함수이다. 텍스트 한 줄을 없애기 위한 키보드 입력은 [Ctrl]+[BackSpace] 이다.
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
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		_startX = _startX+dx;
		_startY = _startY+dy;
		moveCoord(dx,dy);
		_boundary.move(g,dx,dy);	
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		_boundary.clear(g,expose);
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		_boundary.makeRegion();
		super.makeRegion();
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
	 */
	public boolean onEnter(int x,int y) {
		return _boundary.onEnter(x,y);
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 이 함수를 통하여 GraphicController 객체의
	 * 최대 최소 좌표값에 반영된다.
	 */
	public void minMaxXY() {
		_boundary.minMaxXY(); 
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 인자들 값에 반영되어 돌려진다.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		_boundary.getMinMaxXY(minP,maxP);
	}
	/** 현재 텍스트의 편집 작업이 마무리되었을 때 정리 작업을 하는 함수이다.
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
	/** 텍스트의 편집 종료 시에 마무리 작업을 하는 함수이다.
	 */
	public void bye() {
		super.bye();
		delEmptyLines();
	}
	/** 이 객체를 포함하는 객체를 찾는 함수이다.
	 */
	public Figure container() {
		return _asstionPtr;
	}
	/** 이 객체의 좌표값을 알아오는 함수이다.
	 */
	public void coords(Point p1,Point p2) {
		_boundary.coords(p1,p2);
	}
	/** 이 qualification 객체의 좌표를 적절히 조정하는 함수이다.
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
	/** 이 객체의 폭과 높이를 다시 계산하는 함수이다.
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
