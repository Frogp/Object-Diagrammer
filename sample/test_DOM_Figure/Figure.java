package figure;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.Point;
/**
 * 이 클래스는 모든 표기법 객체의 공통 인터페이스를 제공하는 추상클래스이다.
 */
public abstract
	class Figure extends Object implements Serializable {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -8019524329147825158L;
	/**
	 * 이 포인트 배열은 그림 영역을 설정하기 위해 꼭지점의 좌표값들을 저장한다.
	 * 매번 영역을 재 설정할 때마다 꼭지점의 좌표값이 다시 계산된다.
	 * 이 데이타는 주로 makeRegion() 함수에서 사용된다.
	 */
	protected CPoint _points[];
	/** 이 포인트 배열은 현재 그림이 초점으로 선택되었을 때 모서리에 그려야되는 사각점
	 * 좌표들을 저장하기 위한 것이다.
	 */
	protected CPoint _dots[];
	/** 그림의 영역
	 */
	protected CRgn _region;
	/** 그림의 영역 보다 한 픽셀 넓이가 큰 영역
	 */
	protected CRgn _maxRegion;
	/** 이 그림이 그려져야 하는 Component 객체에 대한 레퍼런스 
	 */
	protected transient GraphicController _controller;
	/** 현재 그림이 화면 상의 초점인가를 나타내는 flag
	 */
	protected transient boolean _doted;
	/** 이 그림 객체가 화면 안에 있는 가를 나타내는 flag
	 */
	protected transient boolean _inCanvas;
	/** 이 그림 객체의 작성 이후에 stacking을 할 것인가를 나타내는 flag
	 */
	protected transient boolean _stackingFlag;
	/** 이 그림 객체가 다른 객체로 부터 복사된 경우 원본 객체를 가리키는 레퍼런스
	 */
	protected transient Figure _borned;
	/** 이 객체를 위한 팝업 메뉴 레퍼런스
	 */
	protected transient Popup _popup;
	/** 그림 객체들을 모두 traverse 할 때 사용되는 flag
	 */
	public transient boolean visited;
	/** 그림 객체의 내부가 색으로 채워지는가를 나타내는 flag
	 */
	public transient boolean filled;
	/** 그림 객체의 이동 시에 원상 복구를 하기 위한 원본 그림의 백업 객체
	 */
	public static Figure BackUp;
	
	/**
	 * Figure 클래스의 생성자로서 자료 구조를 초기화 한다.
	 */
	protected Figure(GraphicController controller) {
		super();
		_controller = controller;
		_doted = false;
		_points = null;
		_region = CRgn.myCreateRgn();
		_maxRegion = CRgn.myCreateRgn();
		visited = false;
		_inCanvas = true;
		_stackingFlag = true;
		_borned = null;
		_popup = null;
	}
	/**
	 * 이 함수는 소멸자의 대용이다. Java에서 소멸자가 필요없기는 하지만
	 * 때때로 소멸자의 대용으로서 데이타 멤버의 값을 reset시키는 것이 안전한 경우가 있다.
	 */
	public void delete() {
		if (_region != null) {
			CRgn.myDestroyRgn(_region); 
			_region = null;
		}
		if (_maxRegion != null) {
			CRgn.myDestroyRgn(_maxRegion); 
			_maxRegion = null;
		}
		if (_points != null) {
			_points = null;
		}
		_popup = null;
		_borned = null;
	}
	/** 데이타 멤버 _borned 에 대한 읽기 access 함수이다.
	 */
	public Figure borned() {
		return _borned; 
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		setController(controller);
		setPopupPtr(controller.simplePopup());
		makeRegion();
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수이다.
	 */
	public void setInCanvas(boolean flag) {
		_inCanvas = flag;
	}
	/** 데이타 멤버 _inCanvas 에 대한 읽기 access 함수이다.
	 */
	public boolean inCanvas() {
		return _inCanvas;
	}
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
	 */
	public boolean checkInRegion(CRgn rgn) {
		return true; 
	}
	/** 그림 객체의 마지막 좌표(x2,y2) 값이 인자에 명시된 좌표 값과 가까워지도록 설정하는 함수이다.
	 */
	public boolean checkNear(int nx,int ny) {
		return false;
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
	 */
	public boolean onEnter(int x,int y) {
		if (!CRgn.myEmptyRgn(_region)) {
			return CRgn.myPtInRgn(_region,x,y);
		} else {
			return false;
		}
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simpleDCPopup();
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
	/** 데이타 멤버 _popup 에 대한 읽기 access 함수이다.
	 */
	public figure.Popup popupPtr() {
		return _popup;
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		Figure copied = ptr;
		if (ptr == null) {
			MySys.myprinterr("Error in Figure::born()");
			return this;
		} else {
			copied = ptr;
		}
		_borned = copied;
		copied._controller = _controller;
		copied._popup = _popup;
		copied._stackingFlag = _stackingFlag;
		// need nothing for _region, _maxRegion
		return(copied);
	}
	/** 인자에 명시된 영역에 이 객체가 포함되는가를 확인하는 함수이다.
	 */
	public boolean checkEntries(CRgn rgn) {
		return true; 
	}
	/** 데이타 멤버 _region 에 대한 읽기 access 함수이다.
	 */
	public CRgn region() {
		return _region; 
	}
	/** 데이타 멤버 _maxRegion 에 대한 읽기 access 함수이다.
	 */
	public CRgn maxRegion() {
		return _maxRegion; 
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMFIGURE; 
	}
	/** 이 객체를 포함하는 객체를 찾는 함수이다.
	 */
	public Figure container() {
		return this;
	}
	/** 이 객체를 포함하는 객체에 대한 영역을 설정하는 함수이다.
	 */
	public void remakeRegionForContainer() {
		makeRegion();
	}
	/** 이 객체의 레퍼런스를 인자에 명시된 리스트에서 제거하는 함수이다.
	 */
	public void checkInlist(FigureList alist) {
		if (alist.inList(this)) alist.remove(this);
	}
	/** 이 그림 객체의 최소 폭과 높이를 만족하는가 확인하는 함수이다.
	 */
	public boolean checkMinWH() {
		return(false);
	}
	/** 이 객체가 복합 객체인 경우 부속 객체의 갯수를 구하는 함수이다.
	 */
	public int nOfFigures() {
		return 1;
	}
	/** 이 객체가 복합 객체인 경우 부속 객체의 첫번째 객체를 돌려주는 함수이다.
	 */
	public Figure pop() {
		MySys.myprinterr("Error : This must not be called. Figure.pop()\n");
		return null;
	}
	/** 표기법 객체의 편집이 끝난 이후에 호출되는 함수로서 일관성 검사,
	 * 수치 보정들을 수행한다.
	 */
	public boolean epilog(boolean skipflag) {
		return false;
	}
	public boolean stopDraw() {
		return epilog(false);
	}
	/** 이 객체가 무용한 객체인가 확인하는 함수이다. 특히 텍스트 객체의 경우 입력된
	 * 문자열이 없으면 무용한 객체이다.
	 */
	public boolean isObsolete() {
		return false;
	}
	/** 데이타 멤버 _focus에 대한 읽기 access 함수. Figure 클래스 경우에는 가상함수이다.
	 */
	public Figure focus() {
		return this;
	}
	/** 이 객체를 이동하기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
	 */
	public boolean moveProlog(int oneorsome) {
		return true;
	}
	/** 관계를 위한 필요한 심볼을 일괄적으로 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbolAll() {
		return true;
	}
	/** 그림 객체와 관련되는 영역들을 재설정하는 함수이다.
	 */
	public void resetRegion() {
		if (_region != null) CRgn.myDestroyRgn(_region);
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion); 
		_region = CRgn.myCreateRgn();
		_maxRegion = CRgn.myCreateRgn();
	}
	/** 데이타 멤버 _controller 에 대한 읽기 access 함수이다.
	 */
	public GraphicController controller() {
		return _controller;
	}
	/** 데이타 멤버 _controller 에 대한 쓰기 access 함수이다.
	 */
	public void setController(GraphicController ptr) {
		_controller = ptr;
	}
	/** 데이타 멤버 _popup 에 대한 쓰기 access 함수이다.
	 */
	public void setPopupPtr(Popup ptr) {
		_popup = ptr;
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public abstract void makeRegion();
	/** 이 객체의 중앙에 대한 좌표값을 구하는 함수이다.
	 */
	public abstract Point center();
	/** 이 객체의 마지막 모서리에 대한 좌표값을 구하는 함수이다.
	 */
	public abstract Point last();
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 이 함수를 통하여 GraphicController 객체의
	 * 최대 최소 좌표값에 반영된다.
	 */
	public abstract void minMaxXY();
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 인자들 값에 반영되어 돌려진다.
	 */
	public abstract void getMinMaxXY(Point minP,Point maxP);
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public abstract void draw(Graphics g,int mode,Graphics specialgc);
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 */
	public abstract void drawing(Graphics g,boolean flag);
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 * 현재 마우스의 새로운 위치 값을 이용하여 rubberbanding 한다.
	 */
	public abstract void drawing(Graphics g,int nx,int ny,boolean flag);
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public abstract void move(Graphics g,int dx,int dy);
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public abstract void clear(Graphics g,boolean expose);
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public abstract void drawDots(Graphics g);
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
	 */
	public abstract boolean wantResize(CPoint point);
	/** 좌표만 이동 시키는 함수이다.
	 */
	public abstract void moveCoord(int dx,int dy);
	/** 이 객체의 시작점을 새로운 위치로 바꾸는 함수이다.
	 */
	public void changeOriginPoint(int x,int y) {}
	/** 입력 인자인 라인을 사각형의 변경까지 위치하도록 길이를 조절하는 함수이다.
	 */
	public boolean adjustLine(Line line,boolean startpoint) { return true; }
	/** 현재 마우스가 눌러졌을 때 이 객체를 움직이기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantMove(CPoint point) { return false; }
	/** 현재 마우스가 눌러졌을 때 이 객체의 초점을 움직이기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantMoveFocus(CPoint point) { return false; }
	/** _x1, _y1 좌표값을 설정하는 함수이다.
	 */
	public void setXY1(int x1,int y1) {}
	/** _x2, _y2 좌표값을 설정하는 함수이다.
	 */
	public void setXY2(int x2,int y2) {}
	/** 그림 크기를 조절하기 위해 _x2, _y2 좌표값을 설정하는 함수이다.
	 */
	public void setXY2ForResize(int x2,int y2) {}
	/** 선분의 끝 부분을 확장시켜서 연관되는 클래스 내부에 선분이 포함되도록하는 함수이다.
	 * 이 함수는 그림 축소와 확대 시에 이상한 그림이 만들어지지 않도록하는 역할을 한다.
	 */
	public void expandEndLineALittle() {}
	/** 그림 객체의 초점 부분 객체를 복사하는 함수이다.
	 */
	public void bornFocus() {}
	/** 이 객체의 폭과 높이를 다시 계산한다. 
	 */
	public void recalcWidthHeight() {}
	/** 인자로 주어진 위치값을 이용해 초점 객체를 결정하는 함수이다. 
	 */
	public void setFocus(int x,int y) {}
	/** 인자로 주어진 객체값을 초점 객체로 설정하는 함수이다. 
	 */
	public void setThisFocus(Figure focus) {}
	/** 이 객체의 모양을 바꾸기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
	 */
	public void resizeProlog(boolean makeBackUp) {}
	/** 화일의 로드 이후에 관련 객체의 레퍼런스를 일관성있게 조정하는 함수이다.
	 */
	public void makeConsistent(Figure oldfig) {}
	/** 객체의 파괴 이전에 일관성 문제가 없음을 확인하는 함수이다.
	 */
	public void ensureConsistencyBeforeDelete() {}
	/** 객체에 속하는 리스트의 내용을 비우는 함수이다.
	 */
	public void clearLists() {}
	/** 이 객체와 연결되어 있는 관계들을 제거하는 함수이다.
	 */
	public void deleteNeighbors() {}
	/** 이 객체가 관계인 경우 관계에서 선택된 일부분의 선분을 삭제하는 함수이다.
	 */
	public void deleteSegmentFocus() {}
	/** 데이타 멤버 _focus에 null 값을 set하는 access 함수이다.
	 */
	public void resetFocus() {}
	/** 화일을 로드할 때 클래스 내용을 ShadowClasses 리스트에 등록하는 함수이다.
	 */
	public void registerClassContentWhenLoading(String packName) {}
}
