package figure;


import modeler.*;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.print.*;
import java.awt.geom.*;
import javaparse.ParseTreeNode;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;

/**  이 클래스는 이 시스템에서 가장 핵심적인 클래스로서 표기법이 그려지는
 * 화면을 제어하고 사용자에 의해 발생하는 대부분의 이벤트를 처리한다.
 */
public abstract class GraphicController extends JComponent 
													implements ActionListener,
																	MouseMotionListener,
																	AdjustmentListener, 
																	KeyListener,
																	DocumentListener,
																	Printable
																	 
{
	/** 이 데이타 멤버는 클래스의 내용을 공유하기 위해 사용되는 클래스 내용의 리스트이다.
	 * 디버깅 때문에 정제기에서는 이 객체를 그냥 사용하고 모델러에서는 똑같은 이름의 객체를
	 * 만들어서 사용한다. 이 객체에 대한 access 함수는 getShadowClasses() 이다.
	 */
	public static ClassContentList ShadowClasses = new ClassContentList();
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : fixPointerAbsolute() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FFixPointerAbsolute = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : traceEnterForList() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FTraceEnterForList = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionEditNameText() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionEditNameText = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionEditCardinalText() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionEditCardinalText = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionEditRoleNameText() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionEditRoleNameText = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionContinueDraw() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionContinueDraw = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionStopDraw() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionStopDraw = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionStartDraw() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionStartDraw = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionDrawingHandler() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionDrawingHandler = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionWalkCorridor() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionWalkCorridor = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : anyTionStartForkForTernary() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAnyTionStartForkForTernary = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : assTionSetLinkeSymbol() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	public static boolean FAssTionSetLinkSymbol = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : drawingHandler() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	protected static boolean FDrawingHandler = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : stopDraw() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	protected static boolean FStopDraw = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : startDraw() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	protected static boolean FStartDraw = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : movingHandler() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	protected static boolean FMovingHandler = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : stopSimpleMove() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	protected static boolean FStopSimpleMove = false;
	/** 이벤트 핸들러의 등록을 관리하기 위한 flag : linesStopper() 함수의 호출을 enable 시키거나 disable 시킴
	 */
	protected static boolean FLinesStopper = false;
	/** 화면의 초기 높이
	 */
	protected static int VERTICALINCREMENT = 550;
	/** 화면의 초기 폭
	 */
    protected static int HORIZONTALINCREMENT = 800;
	/** [Ctrl] 키가 눌러진 상태인가를 나타내는 flag
	 */
	protected static boolean _ctrlKeyPressed = false;
	/** 그림 객체가 실제로 움직였는가를 나타내는 flag
	 */
	protected boolean _reallyMovedFlag;
	/** 복사된 그림의 레퍼런스
	 */
	public static Figure CopiedFigure = null;
	/** 화면축소비율
	 */
	protected double _shrinkRatio;
	/** 폰트의 크기
	 */
	public static int MyFontSize = 12;
	/** 화면 축소시에 가장자리 여분
	 */
	protected int _shrinkMargin;
	/** 관계와 같은 선을 작성 중일때 그 선에 대한 레퍼런스
	 */
	protected Lines _currentLines;
	/** 새로운 선분을 그리는가 혹은 기존의 선에 새 선분을 추가 하는 중인가를
	 * 나타내는 flag
	 */
	protected boolean _isNewLines;
	/** 마우스가 grab되었는가를 나타내는 flag
	 */
	protected boolean _isGrabbed;
	/** 팝업 버튼이 눌러졌는가를 나타내는 flag
	 */
	public boolean _popupflag;
	/** 현재 작성 중인 그림의 종류
	 */
	protected int _currentDrawingType;
	/** 현재 텍스트 편집 작업 중인가를 나타내는 flag
	 */
	public boolean _editingTag;
	/** 현재 화면인 사용 가능한가를 나타내는 flag
	 */
	protected boolean _enable;
	/** paint 이벤트를 발생시킬 것인가를 나타내는 flag
	 */
	private boolean _paintFlag;
	/** 화살표의 길이
	 */
	private int _arrowLength;
	/** 선분 영역의 폭
	 */
	private int _regionLength;
	/** 점의 반지름
	 */
	private int _pointRadius;
	/** 둥근 사각형의 모서리 여분
	 */
	private int _roundBoxGap;
	/** 화면 상에 있는 그림들 중에서 마우스가 선택한 그림들의 리스트
	 */
	protected FigureList _focusList;
	/** 단순한 팝업의 레퍼런스
	 */
	protected Popup _simplePopup;
	/** 삭제와 복사 버튼이 있는 단순한 팝업의 레퍼런스
	 */
	protected Popup _simpleDCPopup;
	/** 편집, 삭제, 복사 버튼이 있는 단순한 팝업의 레퍼런스
	 */
	protected Popup _simpleEDCPopup;
	/** 그림의 이동 변위 x
	 */
	protected int _movedX;
	/** 그림의 이동 변위 y
	 */
	protected int _movedY;
	/** 그림 위치의 최소값 x
	 */
	protected int _minX;
	/** 그림 위치의 최소값 y
	 */
	protected int _minY;
	/** 그림 위치의 최대값 x
	 */
	protected int _maxX;
	/** 그림 위치의 최대값 y
	 */
	protected int _maxY;
	/** 화면의 폭
	 */
	private int _width;
	/** 화면의 높이
	 */
	private int _height;
	/** 화면의 최대 폭
	 */
	protected int _maxWidth;
	/** 화면의 최대 높이
	 */
	protected int _maxHeight;
	/** 그룹의 크기를 재조정할 때 마우스이 이동 변위 x
	 */
	private int _dxForGroupResize;
	/** 그룹의 크기를 재조정할 때 마우스이 이동 변위 y
	 */
	private int _dyForGroupResize;
	/** 글자 편집을 위한 텍스트 필드
	 */
	public JTextField activeTextField;
	/** 화면의 내용이 변경되었음을 나타내는 flag
	 */
	public boolean _dirtyFlag;
	/** 마우스의 현재 위치 x
	 */
	protected int _currentX;
	/** 마우스의 현재 위치 y
	 */
	protected int _currentY;
	/** 팝업 위치 x
	 */
	protected int _popupX;
	/** 팝업 위치 y
	 */
	protected int _popupY;
	/** highlight 해야하는 클래스
	 */
	ClassTemplate _focusClass;
	/** 화면에 포함된 그림들의 리스트
	 */
	public FigureList _figures;
	/** 관계의 작성시에 의미있는 클래스와 패키지 객체 만을 모아논 리스트
	 */
	private FigureList _activeFigures;
	/** 화면에서 현재 초점이 되는 그림의 레퍼런스
	 */
	public Figure _currentFocus;
	/** 현재 초점을 저장해놓은 값
	 */
	protected Figure _savedFocus;
	/** 화면의 영역
	 */
	protected CRgn _canvasRgn;
	/** 이 화면을 포함하는 프레임 객체
	 */
	protected CommonFrame _frame;
	/** 스크롤제어기
	 */
	protected JScrollPane scroller;
	/** 수직 스크롤바
	 */
	protected JScrollBar hScrollBar;
	/** 수평 스크롤바
	 */
	protected JScrollBar vScrollBar;
	/** 문자 폰트
	 */
	protected Font _font;
	/** 폰트 ascent
	 */
	protected int _fontAscent;
	/** 폰트 descent
	 */
	protected int _fontDescent;
	/** 폰트 폭
	 */
	protected int _fontSizeH;
	/** 폰트 높이
	 */
	protected int _fontSizeV;
	/** 마우스가 고정되는 가를 나타내는 flag
	 */
	public boolean isFixed;
	/** 화면의 시작 좌표 x
	 */
	public int originX;
	/** 화면의 시작 좌표 y
	 */
	public int originY;
	/** 선분에서의 fork 작업 중인가를 나타내는 flag
	 */
	public boolean forkFlag = false;
	/** 현재의 선분 방향성
	 */
	public int currentOrient;
	/** 그래픽 객체의 클립 영역
	 */
	public Rectangle paintClipBound = null;
	
	/** 현재 프린트 중인가를 나타내는 flag 
	 */
	public boolean printFlag = false;
	/** 물리적인 프린트 용지의 폭
	 */
	private int physicalPaperWidth;
	/** 물리적인 프린트 용지의 높이
	 */
	private int physicalPaperHeight;
	/** 종이 폭
	 */
	private int paperWidth;
	/** 종이 높이
	 */
	private int paperHeight;
	/** 전체 페이지의 행수
	 */
	private int nOfRowOfPages;
	/** 전체 페이지의 열수
	 */
	private	int nOfColumnOfPages;
	/** 프린트할 전체 페이지 수
	 */
	private int totalPages;
	/** 각 프린트 페이지의 시작 위치 x
	 */
	private int paperOX;
	/** 각 프린트 페이지의 시작 위치 y
	 */
	private int paperOY;
	
	
	/** 데이타 멤버 _dirtyFlag 대한 읽기 access 함수
	 */
	public boolean dirtyFlag() {
		return _dirtyFlag;
	}
	/** 데이타 멤버 _width 대한 읽기 access 함수
	 */
	public int width() { 
		return _width; 
	}
	/** 데이타 멤버 _height 대한 읽기 access 함수
	 */
	public int height() { 
		return _height; 
	}
	/** ShadowClasses 에 대한 access 함수
	*/
	public ClassContentList getShadowClasses() {
		return ShadowClasses;
	}
	/** 인자에 주어진 클래스 이름의 클래스 내용을 찾는 함수이다.
	 */
	public ClassContent classContentFor(String className) {
		return getShadowClasses().classContentFor(className);
	}
	/** 쉐도우 클래스에 새 클래스 내용을 등록하는 함수이다.
	 */
	public void insertClassContent(ClassContent newContent) {
		getShadowClasses().insert(newContent);
	}
	/** 데이타 멤버 _frame 대한 읽기 access 함수
	 */
	public CommonFrame frame() 
	{
		return _frame;
	}
	/** 메인 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup mainPopup() {
		return null;
	}
	/** 클래스 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup classPopup() {
		return null;
	}
	/** 패키지 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup packagePopup() {
		return null;
	}
	/** 일반화 관계 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup gentionPopup() {
		return null;
	}
	/** 집합화 관계 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup aggtionPopup() {
		return null;
	}
	/** 결합 관계 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup asstionPopup() {
		return null;
	}
	/** 의존 관계 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup coltionPopup() {
		return null;
	}
	/** 데이타 멤버 _simplePopup 대한 읽기 access 함수
	 */
	public figure.Popup simplePopup() {
		return _simplePopup;
	}
	/** 데이타 멤버 _simpleDCPopup 대한 읽기 access 함수
	 */
	public figure.Popup simpleDCPopup() {
		return _simpleDCPopup;
	}
	/** 데이타 멤버 _simpleEDCPopup 대한 읽기 access 함수
	 */
	public figure.Popup simpleEDCPopup() {
		return _simpleEDCPopup;
	}
	/** 멤버 팝업 레퍼런스에 대한 가상함수
	 */
	public figure.Popup memberPopup() {
		return null;
	}
	/** 데이타 멤버 _font 대한 읽기 access 함수
	 */
	public Font font() {
		return _font;
	}
	/** 데이타 멤버 _fontSizeH 대한 읽기 access 함수
	 */
	public int fontSizeH() { 
		return _fontSizeH;
	}
	/** 데이타 멤버 _fontSizeV 대한 읽기 access 함수
	 */
	public int fontSizeV() {
		return _fontSizeV;
	}
	/** 데이타 멤버 _fontAscent 대한 읽기 access 함수
	 */
	public int fontAscent() {
		return _fontAscent;
	}
	/** 데이타 멤버 _activeFigures 대한 쓰기 access 함수
	 */
	public void setActiveFigures(FigureList figs) {
		_activeFigures = figs;
	}
	/** _activeFigures 값을 리셋시키는 함수이다.
	 */
	public void resetActiveFigures() {
		_activeFigures.delete();
		_activeFigures = null;
	}
	/** 데이타 멤버 _activeFigures 대한 읽기 access 함수
	 */
	public FigureList activeFigures() {
		return _activeFigures;
	}
	/** 마우스의 위치가 움직이지 않도록 하는 함수이다. 실제 마우스는 움직이지만 좌표값은 변경되지 않는다.
	 */
	public void fixPointerAbsolute(CPoint event) {
		isFixed = true;
		warpPointer(_currentX,_currentY);
	}
	/** 선분을 이어서 선을 그릴 때 더이상 선 긋기가 수행되지 않도록 하는 함수이다.
	 */
	protected void linesStopper(CPoint event,boolean stop) {
		int x = event.x;
		int y = event.y;
		int deltax = Math.abs(x - _currentX);
		int deltay = Math.abs(y - _currentY);
		if ((deltax < Const.FIXTOLERANCE) &&
			(deltay < Const.FIXTOLERANCE) &&
			stop == false) {
			return;
		}
		currentOrient = Const.UNDEFINED;
		FLinesStopper = false;
		_isNewLines = true;
		if (_currentLines != null) {
			if (_currentLines.nOfFigures() == 0) {
				_currentLines.delete();
			} else {
				if (_currentDrawingType != 0) {
					Line focus = (Line)_currentLines.focus();
					if (focus != null) {
						Point lp = focus.last();
						focus.toggleHead(true,lp.x,lp.y);
					}
				}

				_currentLines.resetFocus();
				_currentLines.epilog(false);
				_currentLines.makeRegion();
				clear(_currentLines,true);
				draw(_currentLines);
				_figures.insert(_currentLines,0);
			}
			_currentLines = null;
		}
		_currentDrawingType = 0;
	}
	/** 메뉴에 의해서 선택된 모양의 그림을 그리기 위해 초기화하는 함수이다. 
	 */
	public void readyDraw(int what) {
		_isNewLines = true;
		_currentDrawingType = what;
	}
	/** 데이타 멤버 _enable 대한 쓰기 access 함수
	 */
	public void setEnable(boolean flag) {
		_enable = flag;
	}
	/** 패키지의 내용을 save하는 함수이다.
	 */
	public void savePackageContent(String packageName,GraphicController packController) 
	{
		Figure aFig = _figures.getFirst();
		while (aFig != null) 
		{
			if (aFig.whoAreYou().isEqual(Const.IAMPACKAGE)) 
			{
				PackageTemplate aPack = (PackageTemplate) aFig;
				String packName = aPack.getName();
				if (packageName.equals(packName))
				{
					aPack.saveFromController(packController);
					return;
				}
			}
			aFig = _figures.getNext();
		}
	}
	/** 패키지의 내용을 이 화면으로 로드하는 함수이다.
	 */
	public void loadFromPackage(GraphicController oldController,PackageTemplate pack) 
	{
		String packageName = pack.getName();
		Figure aFig = oldController._figures.getFirst();
		while (aFig != null) 
		{
			if (aFig.whoAreYou().isEqual(Const.IAMPACKAGE)) 
			{
				PackageTemplate aPack = (PackageTemplate) aFig;
				String packName = aPack.getName();
				if (packageName.equals(packName) && aPack.havePackageContent == true) 
				{
					aPack.loadContent(this);
					return;
				}
			}
			aFig = oldController._figures.getNext();
		}
	}
	/** 인자에 의해 명시된 그림 종류만을 모아 리스트로 만드는 함수이다.
	 */
	public FigureList makeListOf(FigureID forwhom) {
		FigureList list = new FigureList();
		Figure figptr = _figures.getFirst();
		while (figptr != null) {
			if (figptr.whoAreYou().isIn(forwhom)) {
				list.insert(figptr,0);
			}
			figptr = _figures.getNext();
		}
		return list;
	}
	/** 마우스 위치 값을 옮기는 함수이다.
	 */
	public void warpPointer(int x,int y) {
		_currentX = x;
		_currentY = y;
	}
	/** 에러 메시지와 함께 소리를 발생시키는 함수이다.
	 */
	public void beep(String m) {
		System.out.println(m);
		getToolkit().beep();
	}
	/** 소리를 발생시키는 함수이다.
	 */
	public void beep() {
		getToolkit().beep();
	}
	/** 화면 영역을 만드는 함수이다.
	 */
	public CRgn canvasRgn() {
		makeCanvasRegion();
		return _canvasRgn;
	}
	/** 데이타 멤버 _figures 대한 읽기 access 함수
	 */
	public FigureList figures() {
		return _figures;
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 */
	public void minmaxXY(int x1,int y1) {
		if (_minX > x1) _minX = x1;
		if (_maxX < x1) _maxX = x1;
		if (_minY > y1) _minY = y1;
		if (_maxY < y1) _maxY = y1;
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 */
	public void minmaxXY(int x1,int y1,int x2,int y2) {
		if (_minX > x1) _minX = x1;
		if (_maxX < x1) _maxX = x1;
		if (_minY > y1) _minY = y1;
		if (_maxY < y1) _maxY = y1;
		if (_minX > x2) _minX = x2;
		if (_maxX < x2) _maxX = x2;
		if (_minY > y2) _minY = y2;
		if (_maxY < y2) _maxY = y2;
	}
	/** 데이타 멤버 _arrowLength 대한 읽기 access 함수
	 */
	public int arrowLength() {
		return _arrowLength;
	}
	/** 데이타 멤버 _regionLength 대한 읽기 access 함수
	 */
	public int regionLength() {
		return _regionLength;
	}
	/** 데이타 멤버 _pointRadius 대한 읽기 access 함수
	 */
	public int pointRadius() {
		return _pointRadius;
	}
	/** 데이타 멤버 _roundBoxGap 대한 읽기 access 함수
	 */
	public int roundBoxGap() {
		return _roundBoxGap;
	}
	/** 데이타 멤버 _currentX 대한 읽기 access 함수
	 */
	public int currentX() {
		return _currentX;
	}
	/** 데이타 멤버 _currentY 대한 읽기 access 함수
	 */
	public int currentY() {
		return _currentY;
	}
	/** 데이타 멤버 _popupX 대한 읽기 access 함수
	 */
	public int popupX() {
		return _popupX;
	}
	/** 데이타 멤버 _popupY 대한 읽기 access 함수
	 */
	public int popupY() {
		return _popupY;
	}
	/** 데이타 멤버 _currentFocus 대한 읽기 access 함수
	 */
	public Figure currentFocus() {
		return _currentFocus;
	}
	/** 이 화면에 포함된 모든 그림의 영역을 구하는 함수이다.
	 */
	public void makeRegion() {
		Figure ptr = _figures.getFirst();
		while (ptr != null) {
			ptr.makeRegion();
			ptr = _figures.getNext();
		}
	}
	/** 인자에 명시된 그림의 영역을 구하는 함수이다.
	 */
	public void makeRegion(Figure figure) {
		figure.makeRegion();
	}
	/** 마우스의 현재 좌표 값을 기록하는 함수이다.
	 */
	public void setCurrentXY(int x,int y) {
		_currentX = x;
		_currentY = y;
	}
	/** 데이타 멤버 _currentLines 대한 쓰기 access 함수
	 */
	public void setCurrentLines(Lines aLines) {
		_currentLines = aLines;
	}
	/** 데이타 멤버 _currentLines 대한 읽기 access 함수
	 */
	public Lines currentLines() {
		return _currentLines;
	}
	/** _currentFocus 값을 리셋시키는 함수이다.
	 */
	public void resetCurrentFocus() {
		_currentFocus = null;
	}
	/** 데이타 멤버 _currentFocus 대한 쓰기 access 함수
	 */
	public void setCurrentFocus(Figure newfocus) {
		_currentFocus = newfocus;
	}
	/** 현재 마우스 좌표가 화면의 경계선을 넘는가를 검사하는 함수이다.
	 */
	public boolean checkLimitForFigure(int newx,int newy,CPoint deltaxy) {
		int gap = 10;
		int oX = (int)((double)originX/_shrinkRatio);
		int oY = (int)((double)originY/_shrinkRatio);
		int width = (int)((double)_width/_shrinkRatio);
		int height = (int)((double)_height/_shrinkRatio);
		newx = newx - oX;
		newy = newy - oY;
		if ((oX + newx <= 0) ||
			(oY + newy <= 0)) {
			beep();
			return false;
		}
		
		int onewx = oX + newx + gap;
		int onewy = oY + newy + gap;
		maxBoundaryCheck(onewx,onewy);
		
		int dx = 0;
		int dy = 0;
		int diff = 0;
		if (newx < gap) {
			diff = gap - newx;
			if (diff > Const.SCROLLBARDELTA) {
				dx = -(diff + Const.SCROLLBARDELTA);
			} else {
				dx = -Const.SCROLLBARDELTA;
			}
		}
		if (newy < gap) {
			diff = gap - newy;
			if (diff > Const.SCROLLBARDELTA) {
				dy = -(diff + Const.SCROLLBARDELTA);
			} else {
				dy = -Const.SCROLLBARDELTA;
			}
		}
		if (newx > width - gap) {
			if (dx == 0) {
				diff = newx - width;
				if (diff > Const.SCROLLBARDELTA) {
					dx = (diff + Const.SCROLLBARDELTA);
				} else {
					dx = Const.SCROLLBARDELTA;
				}
			}
		}
		if (newy > height - gap) {
			if (dy == 0) {
				diff = newy - height;
				if (diff > Const.SCROLLBARDELTA) {
					dy = (diff + Const.SCROLLBARDELTA);
				} else {
					dy = Const.SCROLLBARDELTA;
				}
			}
		}
		if (dx != 0 || dy != 0) {
			if (_editingTag) {
//				VTerm scr = ((Text)(_currentFocus.focus())).screen();
//				scr.vanish();
			} else if (_currentFocus != null) {
				rubberbanding(_currentFocus);
			}
		}
		moveScrollB(dx,dy);
		if (dx != 0 || dy != 0) {
			if (_editingTag) {
//				VTerm scr = ((Text)(_currentFocus.focus())).screen();
				draw(_currentFocus);
//				scr.activate();
			}
			// these values are used to reset the position of popup
			if (deltaxy != null) {
				deltaxy.x = dx;
				deltaxy.y = dy;
			}
			return true;
		}
		return false;
	}
	/** 현재 마우스 좌표가 화면의 최대 한계를 벗어나는가 확인하는 함수이다.
	 */
	public void maxBoundaryCheck(int newx,int newy) {
		if (newx >= Integer.MAX_VALUE || newy >= Integer.MAX_VALUE) {
			beep();
			return;
		}
		boolean flag = false;
		if (newx >= _maxWidth) {
			flag = true;
			_maxWidth = _maxWidth + HORIZONTALINCREMENT;
		}
		if (newy >= _maxHeight) {
			flag = true;
			_maxHeight = _maxHeight + VERTICALINCREMENT;
		}
		if (flag == true) {
			setSize(new Dimension(_maxWidth,_maxHeight));
			setPreferredSize(new Dimension (_maxWidth,_maxHeight));
		}
	}
	/** 마우스가 화면의 경계선을 넘으면 자동으로 스크롤하는 함수이다.
	 */
	public void checkToGo(int newx,int newy,int oldx,int oldy) {
		if (CRgn.myPtInRgn(_canvasRgn,newx,newy)) return;
		int dx = newx - oldx;
		int dy = newy - oldy;
		moveScrollB(dx,dy);
	}
	/** 스크롤 바를 이동시키는 함수이다.
	 */
	private void moveScrollB(int deltax,int deltay) {
		if (deltax == 0 && deltay == 0) {
			return;
		} else if (deltax != 0 && deltay == 0) {
			originX = originX + deltax;
			hScrollBar.setValue(originX);
		} else if (deltax == 0 && deltay != 0) {
			originY = originY + deltay;
			vScrollBar.setValue(originY);
		} else {
			originX = originX + deltax;
			originY = originY + deltay;
			hScrollBar.setValue(originX);
			vScrollBar.setValue(originY);
		}
	}
	/** 그림의 이동을 처리하는 핸들러 함수이다.
	 */
	private void localMoving(CPoint event) {
		int x = event.x;
		int y = event.y;
		if (_currentX == x && _currentY == y) return;
		int dx = x - _currentX;
		int dy = y - _currentY;
		_currentX = x;
		_currentY = y;
		CPoint sdxy = new CPoint();
		boolean moved = checkLimitForFigure(x,y,sdxy);
		if (moved) {
			_movedX = sdxy.x;
			_movedY = sdxy.y;
			_currentFocus.moveCoord(dx,dy);
			return;
		}
		Graphics rg = getgraphics();
		rg.setXORMode(getBackground());
		_currentFocus.move(rg,dx,dy);
		rg.dispose();
	}
	/** 그림 그리기를 처리하는 핸들러 함수이다.
	 */
	private void localDrawing(CPoint event) {
		int x = event.x;
		int y = event.y;
		if (_currentX == x && _currentY == y) return;
		_currentX = x;
		_currentY = y;
		boolean moved = checkLimitForFigure(x,y,null);
		if (moved) {
			_currentFocus.setXY2ForResize(x,y);
			rubberbanding(_currentFocus);
			return;
		}
		Graphics rg = getgraphics();
		rg.setXORMode(getBackground());
		_currentFocus.drawing(rg,x,y,false);
		rg.dispose();
	}
	/** 화일 저장을 위한 가상 함수이다.
	 */
	abstract public void doSave(String filename);
	/** 화일 로드를 위한 가상 함수이다.
	 */
	abstract public void doLoad(String filename);
	/* 화일을 import하기위한 가상 함수이다. */
	public void importFrom(String filename) {
	}
	/** 화면을 clear 하고 그림들을 다시 그려주는 함수이다.
	 */
	public void localMakeClear(boolean clearAreaFlag) {
		if (clearAreaFlag == true) clearArea(originX,originY,_width,_height,false);
		FigureList copiedList = new FigureList();
		copiedList.copy(_figures);
		Figure all = copiedList.getFirst();
		while (all != null) {
			all.setInCanvas(all.checkInRegion(_canvasRgn));
			if (all.inCanvas()) {
				all.makeRegion();
			} else {
				all.resetRegion();
			}
			all = copiedList.getNext();
		}

		_currentFocus = null;
		if (clearAreaFlag == false) return;
		FigureList copiedListMore = new FigureList();
		copiedListMore.copy(_figures);
		Figure ptr = copiedListMore.getFirst();
		while(ptr != null) {
			this.draw(ptr);
			ptr = copiedListMore.getNext();
		}
	}
	/** 화면을 clear 하고 모든 그림 객체를 없애는 함수이다.
	 * 화면을 초기화 할 때 호출된다.
	 */
	public void clearAll(boolean clearScreen) {
		_currentFocus = null;
		_savedFocus = null;
		_focusList.clear();
		_dirtyFlag = false;
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.clearLists();
			ptr.delete();
			ptr = _figures.getNext();
		}
		_figures.clear();	
		if(clearScreen) {
//			repaint(originX,originY,_width,_height);
			super.paintImmediately(originX,originY,_width,_height);
		}
	}
	/** 화면 객체가 생성되고 난 이후에 초기화해야 할 작업을 수행하는 함수이다.
	 */
	public void initAfterRealize() {
		FontMetrics fm;
		fm = super.getGraphics().getFontMetrics(_font);
		_fontAscent = fm.getAscent();
		_fontDescent = fm.getDescent();
		_fontSizeH = fm.charWidth('A');
		_fontSizeV = _fontAscent + _fontDescent;
		
		makeCanvasRegion();
		
		hScrollBar.setEnabled(true);
		vScrollBar.setEnabled(true);
		setBackground(Color.white);
	}
	/** 생성자이다.
	 */
	public GraphicController(CommonFrame frame) {
		super();
		_frame = frame;
		
		_width = 0;
		_height = 0;
		_maxWidth = HORIZONTALINCREMENT * 4;
		_maxHeight = VERTICALINCREMENT * 4;

		_font = new Font("Dialog",Font.PLAIN,MyFontSize);
		/* initialize data members */
		_focusClass = null;
		_shrinkRatio = 1.0;
		_currentLines = null;
		_paintFlag = false;
		_isNewLines = true;
		_isGrabbed = false;
		_popupflag = false;
		_currentDrawingType = 0;
		_canvasRgn = null;
		//        _savedDrawingType = 0;
		activeTextField = null;
		_editingTag = false;
		_enable = true;
		_figures = new FigureList();
		_focusList = new FigureList();
		originX = 0;
		originY = 0;
		_movedX = 0;
		_movedY = 0;
		_minX = _maxWidth;
		_minY = _maxHeight;
		_maxX = 0; _maxY = 0;
		_dxForGroupResize = 0;
		_dyForGroupResize = 0;
		_dirtyFlag = false;
		_arrowLength = Const.ARROWLENGTH;
		_regionLength = Const.REGIONLENGTH;
		_pointRadius = Const.POINTRADIUS;
		_roundBoxGap = Const.ROUNDBOXBGAB;
		_popupX = 0;
		_popupY = 0;
		_currentX = 0;
		_currentY = 0;
		isFixed = false;
		_currentFocus = null;
		_savedFocus = null;
		currentOrient = Const.UNDEFINED;

		enableEvents(AWTEvent.MOUSE_EVENT_MASK |
					 AWTEvent.COMPONENT_EVENT_MASK |
					 AWTEvent.KEY_EVENT_MASK |
					 AWTEvent.WINDOW_EVENT_MASK);
		_simplePopup = new SimplePopup(this);
		_simpleDCPopup = new SimpleDCPopup(this);
		_simpleEDCPopup = new SimpleEDCPopup(this);

		setSize(new Dimension(_maxWidth,_maxHeight));
		setPreferredSize(new Dimension (_maxWidth,_maxHeight));
		_frame.getContentPane().setLayout(new BorderLayout());
		scroller = new JScrollPane();
		scroller.setViewportView(this);
		_frame.getContentPane().add("Center",scroller);
		
		hScrollBar = scroller.getHorizontalScrollBar();
		hScrollBar.setUnitIncrement(HORIZONTALINCREMENT/4);
		hScrollBar.setBlockIncrement(HORIZONTALINCREMENT/2);
		hScrollBar.setEnabled(false);
		
  	 	vScrollBar = scroller.getVerticalScrollBar();
		vScrollBar.setUnitIncrement(VERTICALINCREMENT/4);
		vScrollBar.setBlockIncrement(VERTICALINCREMENT/2);
		vScrollBar.setEnabled(false);

		hScrollBar.addAdjustmentListener(this);
		vScrollBar.addAdjustmentListener(this);
		
		addKeyListener(this);
		addMouseMotionListener(this);
		this.setLayout(null);
	}
	/** 화면의 foreground 색깔을 설정하는 함수이다.
	 */
	public void setForeground(Color c) {
		super.setForeground(c);
	}
	/** 화면의 background 색깔을 설정하는 함수이다.
	 */
	public void setBackground(Color c) {
		super.setBackground(c);
		scroller.getViewport().setBackground(c); 
//		_frame.getContentPane().setBackground(c);
	}
	/** 화면을 다시그리도록 하는 함수이다.
	 */
	public void flushPaintEvent()
	{
		if (_paintFlag == true) {
			repaint();
		}
		_paintFlag = false;
	}
	/** 화면을 다시 그릴 필요가 있음을 나타내도록 _paintFlag를 세트하는 함수이다.
	 */
	public void setRepaint() {
		_paintFlag = true;
	}
	/** _paintFlag를 리셋하는 함수이다.
	 */
	public void resetRepaint() {
		_paintFlag = false;
	}
	/** 화면에 그림을 그리기 위한 그래픽 객체를 할당하는 함수이다.
	 */
	public Graphics getgraphics() {
		Graphics og = super.getGraphics();
		((Graphics2D)og).scale(_shrinkRatio,_shrinkRatio);
		Graphics g = og.create();
	
		if (paintClipBound == null) {
			g.setClip((int)(((double)originX)/_shrinkRatio),
					  (int)(((double)originY)/_shrinkRatio),
					  (int)(((double)_width)/_shrinkRatio),
					  (int)(((double)_height)/_shrinkRatio));
		} else {
			g.setClip(new Rectangle(paintClipBound));
		}
		g.setPaintMode();
		g.setFont(_font);
		g.setColor(getForeground());
		return g;
	}
	/** paint 이벤트가 발생했을 때 화면을 다시 그리는 함수이다.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).scale(_shrinkRatio,_shrinkRatio);
		paintClipBound = new Rectangle(g.getClipBounds());
		g.setColor(getForeground());
		Graphics eg = g.create();
		Rectangle clipR = g.getClipBounds();
		Rectangle newClipR = new Rectangle(clipR);
		eg.setClip(newClipR);
		eg.setColor(getBackground());

		CPoint pt[] = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			pt[i] = new CPoint();
		}
		pt[0].x = clipR.x;
		pt[0].y = clipR.y;
		pt[1].x = clipR.x+clipR.width;
		pt[1].y = clipR.y;
		pt[2].x = clipR.x+clipR.width;
		pt[2].y = clipR.y+clipR.height;
		pt[3].x = clipR.x;
		pt[3].y = clipR.y+clipR.height;
		CRgn clipRgn = CRgn.myPolygonRgn(pt,4);

		FigureList copiedList = new FigureList();
		copiedList.copy(_figures);
		Figure ptr = copiedList.getFirst();
		while(ptr != null) {
			if (ptr.checkInRegion(clipRgn))
				ptr.draw(g,Const.DRAWING,eg);
			ptr = copiedList.getNext();
		}
		if (_currentFocus != null) {
			if (_editingTag) {
				_currentFocus.draw(g,Const.DRAWING,eg);
			} else if (_popupflag == true) {
				_popupflag = false;
			} else if (_isGrabbed == true) {
				_currentFocus.moveCoord(_movedX,_movedY);
				_currentX = _currentX + _movedX;
				_currentY = _currentY + _movedY;
				g.setXORMode(getBackground());
				_currentFocus.draw(g,Const.RUBBERBANDING,eg);
				g.setPaintMode();
			} else {
				_currentFocus.drawDots(g);
			}
		}
		if (_focusClass != null) {
			g.setColor(Color.red);
			_focusClass.draw(g,Const.HIGHLIGHTING,eg);
		}
		eg.dispose();
		_paintFlag = false;
		_movedX = 0;
		_movedY = 0;
		paintClipBound = null;
	}
	/** 인자로 주어진 그림 객체를 highlight 하는 함수이다.
	 */
	public void highlight(Figure figure) {
		_figures.remove(figure);
		_figures.insert(figure,0);
		Graphics eg = this.getGraphics();
		Graphics hg = this.getGraphics();
		eg.setColor(getBackground());
		hg.setColor(Color.red);
		figure.draw(hg,Const.HIGHLIGHTING,eg);
		hg.dispose();
		eg.dispose();
	}
	/** 인자로 주어진 그림 객체를 lowlight 하는 함수이다.
	 */
	public void lowlight(ClassLike figure) {
		lowlight((Figure)figure);
	}
	/** 인자로 주어진 그림 객체를 lowlight 하는 함수이다.
	 */
	public void lowlight(Figure figure) {
		Graphics g = this.getgraphics();
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.draw(g,Const.LOWLIGHTING,eg);
		eg.dispose();
		g.dispose();
	}
	/** 인자로 주어진 그림 객체를 그려주는 함수이다.
	 */
	public void draw(Figure figure) {
		figure.makeRegion();
		Graphics g = this.getgraphics();
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.draw(g,Const.DRAWING,eg);
		eg.dispose();
		g.dispose();
	}
	/** 인자로 주어진 그림 객체를 clear 하는 함수이다.
	 */
	public void clear(Figure figure,boolean expose) {
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.clear(eg,expose);
		eg.dispose();
	}
	/** 인자로 주어진 그림 객체를 rubberbanding 하는 함수이다.
	 */
	public void rubberbanding(Figure figure) {
		Graphics eg = this.getgraphics();
		Graphics rg = this.getgraphics();
		eg.setColor(getBackground());
		rg.setXORMode(getBackground());
		figure.draw(rg,Const.RUBBERBANDING,eg);
		rg.dispose();
		eg.dispose();
	}
	/** 인자로 주어진 그림 객체를 지우는 함수이다.
	 */
	public void erase(Figure figure) {
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.draw(eg,Const.DRAWING,eg);
		eg.dispose();
	}
	/** 그림 리스트에 인자로 주어진 객체를 삽입한다.
	 */
	public void insert(Figure figure) {
		_figures.insert(figure,0);
	}
	/** 그림 리스트에서 인자로 주어진 객체를 제거한다.
	 */
	public void remove(Figure figure) {
		_figures.remove(figure);
	}
	/** 인자로 주어진 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Figure figure) {
		_figures.remove(figure);
		_figures.insert(figure,0);
		lowlight(figure);
		Graphics g = getgraphics();
		figure.drawDots(g);
		g.dispose();
	}
	/** 현재 초점이 되는 그림 객체를 제거하는 함수이다. 이 함수는 객체 전체를 파괴하지 않고
	 * 현 객체의 초점 객체만 파괴한다.
	 */
	public void deleteCurrentFocus() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		Figure focusFig = _currentFocus.focus();
		if (focusFig == null) {
			beep();
			return;
		}
		if (!(focusFig.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT))) {
			beep();
			return;
		}
		clear(focusFig,true);
		focusFig.ensureConsistencyBeforeDelete();
		focusFig.delete();
		_currentFocus = null;
		flushPaintEvent();
	}
	/** 현재 마우스에 의해 지정된 객체를 화면에서 제거한다.
	 */
	public void deleteCurrentFigure() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		Figure testFocus = _currentFocus;
		// deleteSegmentFocus() resets _currentFocus after delete
		testFocus.deleteSegmentFocus(); 
		if (_currentFocus == null) return;
		_currentFocus.deleteNeighbors();
		lowlight(_currentFocus);
		_figures.remove(_currentFocus);
		clear(_currentFocus,true);
		checkInlist(_currentFocus);
		_currentFocus.delete();
		_currentFocus = null;
		flushPaintEvent();
	}
	/** 그림 객체의 이동이 끝났을 때 작업을 수행하는 함수이다.
	 */
	protected void localStopMove() {
		_currentFocus.resetFocus();
		_currentFocus.epilog(false);
		if (_currentFocus.whoAreYou().isIn(Const.WEARECLASSTEMPLATE) &&
			Figure.BackUp != null) {
			Figure.BackUp.delete();
			Figure.BackUp = null;
		}
		if (Figure.BackUp != null) {
			Figure obsoleteFigure = _currentFocus;
			_currentFocus = Figure.BackUp;
			Figure.BackUp = null;
			_currentFocus.makeConsistent(obsoleteFigure);
			obsoleteFigure.delete();
			_currentFocus.setModelSpecificSymbolAll();
			beep("GraphicController.localStopMove");
		} else {
			_currentFocus = _currentFocus.container();
		}
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		draw(_currentFocus);
		_currentFocus = null;
	}
	/** 그림 객체의 작성이 끝났을 때 작업을 수행하는 함수이다.
	 */
	protected void localStopDraw() {
		/*
		boolean ck = _currentFocus.checkMinWH();
		if (ck) {
			_focusList.clear();
			erase(_currentFocus);
			_currentFocus.delete();
			_currentFocus = null;
			return;
		} else {
			boolean destroyed = _currentFocus.epilog(false);
			if (destroyed) {
				deleteCurrentFigure();
				return;
			}
			if (_currentLines == null) {
				_currentFocus.resetFocus();
				_currentFocus.epilog(false); // epilog for resizing
				if (Figure.BackUp != null) {
					Figure obsoleteFigure = _currentFocus;
					_currentFocus = Figure.BackUp;
					Figure.BackUp = null;
					_currentFocus.makeConsistent(obsoleteFigure);
					obsoleteFigure.delete();
					_currentFocus.setModelSpecificSymbolAll();
					_currentFocus.makeRegion();
					clear(_currentFocus,true);
					beep("GraphicController.localStopDraw");
				} else {
					_currentFocus.makeRegion();
					clear(_currentFocus,true);
				}
				if (_currentFocus.whoAreYou() == Const.IAMTEXT) {
					lowlight(_currentFocus);
					((Text)_currentFocus).adjustScale();
				}
				_figures.insert(_currentFocus,0);
			} else if (!(_currentFocus.whoAreYou().isIn(Const.WEARETION))) {
				FLinesStopper = true;
			}
			_focusList.clear();
			_currentFocus = null;
			return;
		}
		*/
		boolean destroyed = _currentFocus.stopDraw();
		if (destroyed) {
			deleteCurrentFigure();
			return;
		}
		if (_currentLines == null) {
			_currentFocus.resetFocus();
			_currentFocus.epilog(false); // epilog for resizing
			if (Figure.BackUp != null) {
				Figure obsoleteFigure = _currentFocus;
				_currentFocus = Figure.BackUp;
				Figure.BackUp = null;
				_currentFocus.makeConsistent(obsoleteFigure);
				obsoleteFigure.delete();
				_currentFocus.setModelSpecificSymbolAll();
				_currentFocus.makeRegion();
				clear(_currentFocus,true);
				beep("GraphicController.localStopDraw");
			} else {
				_currentFocus.makeRegion();
				clear(_currentFocus,true);
			}
			_figures.insert(_currentFocus,0);
		} else if (!(_currentFocus.whoAreYou().isIn(Const.WEARETION))) {
			FLinesStopper = true;
		}
		_focusList.clear();
		_currentFocus = null;
		return;
	}
	/** 인자에 명시된 객체를 리스트들에서 삭제하는 함수이다.
	 */
	public void checkInlist(Figure ptr) {
		_focusList.clear();
	}
	/** 현재 마우스가 특정 그림 객체를 가리키는 가를 검사하는 함수이다.
	 */
	protected void localTraceEnter(CPoint event,FigureList list,boolean dohighlight) {
		boolean forFigures;
		FigureList figureList = list;
		if (list == null) {
			forFigures = true;
			figureList = _figures;
		} else {
			forFigures = false;
		}
		if (forFigures && _enable == false) return;
		int x = event.x;
		int y = event.y;
//		int x = (int)(((double)tmpX)/_shrinkRatio);
//		int y = (int)(((double)tmpY)/_shrinkRatio);
		if (CRgn.myPtInRgn(_canvasRgn,x,y) == false) {
			_focusList.clear();
			_currentFocus = null;
			return;
		}
		_focusList.clear();
		if (forFigures && isFixed) {
			_currentFocus = null;
			return;
		}
		if (_currentFocus != null) {
			lowlight(_currentFocus);
		}
		boolean check = false;
		boolean found = false;
		boolean newfound = false;
		Figure newFocus = null;
		Figure figureptr = figureList.getLast();
		while (figureptr != null) {
			check = figureptr.onEnter(x,y);
			found = found || check;
			if ( (check) &&
				 !(_focusList.inList(figureptr)) ) {
				_focusList.push(figureptr);
				newfound = true;
				newFocus = figureptr;
				break;
			}
			figureptr = figureList.getPrevious();
		}
		figureptr = _focusList.getFirst();
		while (figureptr != null) {
			check = figureptr.onEnter(x,y);
			if (check == false) {
				_focusList.remove(figureptr);
				figureptr = _focusList.getFirst();
			} else {
				figureptr = _focusList.getNext();
			}
		}
		if (newfound) {
			if (_currentFocus != null) lowlight(_currentFocus);
			if (dohighlight == true) highlight(newFocus);
			_currentFocus = newFocus;
		} else if (found == false) {
			if (_currentFocus != null) lowlight(_currentFocus);
			_currentFocus = null;
			_focusList.clear();
		}
	}
	/** 현재 그림의 크기를 조절하기 시작하는 함수이다.
	 */
	public void resizeCurrentFigure(boolean doCheckNear) {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		_figures.remove(_currentFocus);
		checkInlist(_currentFocus);
		clear(_currentFocus,true);
		if (doCheckNear) {
			_currentFocus.checkNear(_currentX,_currentY);
			_currentFocus.resizeProlog(true);
		}
		rubberbanding(_currentFocus);
		_currentDrawingType = 0;
		setCrossHairCursor();
		_isGrabbed = true;
		FDrawingHandler = true;
		FStopDraw = true;
	}
	/** 마우스 커서 모양을 십자가 형태로 바꾸는 함수이다.
	 */
	public void setCrossHairCursor() {
		_isGrabbed = true;
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	/** grab된 마우스를 해제하는 함수이다.
	 */
	public void xUngrabPointer() {
		_isGrabbed = false;
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	/** 현재 그림을 기억장소에 복사하는 함수이다.
	 */
	public void copyCurrentFigureInMemory() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		CopiedFigure = _currentFocus.born(null);
	}
	/** 기억장소에 복사되어있는 그림을 화면의 무작위적인 위치에 paste 하는 함수이다.
	 */
	public void pasteFigureAtRandomPosFromMemory() {
		if (CopiedFigure == null) {
			beep();
			return;
		}
		double x = Math.random();
		double y = Math.random();
		int x0 = _width / 5;
		int y0 = _height / 5;
		int w = _width*2/5;
		int h = _height*2/5;
		_popupX = originX + x0 + (int)(x*w);
		_popupY = originY + y0 + (int)(y*h);
		pasteFigureFromMemory();
	}
	/** 기억장소에 복사되어있는 그림을 화면에 paste 하는 함수이다.
	 */
	public void pasteFigureFromMemory() {
		if (CopiedFigure == null) {
			beep();
			return;
		}
		int pX = _popupX;
		int pY = _popupY;
		_currentFocus = CopiedFigure.born(null);
		_currentFocus.setController(this);
		_currentFocus.replacePopup(this);
		_currentFocus.changeOriginPoint(_popupX,_popupY);
		_currentFocus.resetFocus();
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		_focusList.clear();
		draw(_currentFocus);
		drawDots(_currentFocus);
	}
	/** 그림 객체를 화면에 복사하는 함수이다.
	 */
	public void copyCurrentFigure() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		if (_currentFocus.whoAreYou().isIn(Const.WEARETION)) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		Figure origin = _currentFocus;
		_currentFocus = _currentFocus.born(null);
		_currentFocus.moveCoord(Const.COPYDELTA,Const.COPYDELTA);
		_currentFocus.resetFocus();
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		_focusList.clear();
		draw(_currentFocus);
		drawDots(_currentFocus);
		flushPaintEvent();
	}
	/** 현재 그림의 초점 객체를 이동하기 시작하는 함수이다.
	 */
	public void moveCurrentFocus() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		Figure focusFig = _currentFocus.focus();
		if (focusFig == null) {
			beep();
			return;
		}
		if (!(focusFig.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT))) {
			beep();
			return;
		}
		remove(_currentFocus);
		checkInlist(_currentFocus);
		_currentFocus = focusFig;
		clear(_currentFocus,true);
		rubberbanding(_currentFocus);
		setCrossHairCursor();
		_isGrabbed = true;
		FMovingHandler = true;
		FStopSimpleMove = true;
	}
	/** 현재 그림 객체를 이동하기 시작하는 함수이다.
	 */
	public void moveCurrentFigure(int oneorsomeptr,boolean skipPrologFlag) {
		if (_currentFocus == null) {
			beep();
			return;
		}
		Figure focusFig = _currentFocus.focus();
		if (focusFig != null) {
			if ((focusFig.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT))) {
				moveCurrentFocus();
				return;
			}
		}
		lowlight(_currentFocus);
		// if skip flag is true, this is second call
		if (skipPrologFlag == false) {
			// this saving blocks new drawing
			//                _savedDrawingType = _currentDrawingType;
			_currentDrawingType = 0;
			boolean continueFlag =
								   _currentFocus.moveProlog(oneorsomeptr);
			if ((_currentFocus.whoAreYou().isIn(Const.WEARETION)) &&
				(continueFlag == false)) {
				beep();
				return; // error case
			}
			if (continueFlag == false) {
				beep();
				return; // SOMEPTR case
			}
		}
		_figures.remove(_currentFocus);
		checkInlist(_currentFocus);
		clear(_currentFocus,true);
		rubberbanding(_currentFocus);
		setCrossHairCursor();
		_isGrabbed = true;
		FMovingHandler = true;
		FStopSimpleMove = true;
	}
	/** 인자에 명시된 텍스트 객체에 대한 편집 작업을 시작하도록 하는 함수이다.
	 */
	public void startEditableObject(Figure obj,Text initText) {
		_editingTag = true;
		setCurrentFocus(obj);
		obj.makeRegion();
		clear(obj,false);
		draw(obj);
		initText.startEdit();
		obj.setThisFocus(initText);
//		FProcessKeys = true;
	}
	/** 텍스트 편집기를 시작하도록 하는 함수이다.
	 */
	public void startEditor(CPoint event) {
		_editingTag = true;
		localTraceEnter(event,null,false);
		int x = event.x;
		int y = event.y;
		if (_currentFocus == null) {
			_currentFocus = (Figure) new Text(this,x,y,_simpleEDCPopup);
			((Text)_currentFocus).startEdit();
		} else if (_currentFocus.whoAreYou().isEqual(Const.IAMTEXT)) {
			lowlight(_currentFocus);
			_figures.remove(_currentFocus);
			checkInlist(_currentFocus);
			clear(_currentFocus,true);
			draw(_currentFocus);
			((Text)_currentFocus).setXY(x,y);
			((Text)_currentFocus).startEdit();
		} else if (_currentFocus.whoAreYou().isIn(Const.WEARECLASSTEMPLATE)) {
			ClassLike focusclass = (ClassLike)_currentFocus;
			_figures.remove(_currentFocus);
			focusclass.localStartEdit(x,y);
		} else if (_currentFocus.whoAreYou().isEqual(Const.IAMNOTE)) {
			Note focusNote = (Note)_currentFocus;
			_figures.remove(_currentFocus);
			focusNote.localStartEdit(x,y);
		} else if ((_currentFocus.whoAreYou().isIn(Const.WEARETION)) &&
				   (_currentFocus.focus().whoAreYou().isEqual(Const.IAMSINGLELINETEXT))) {
			AnyTion focusayntion = (AnyTion) _currentFocus;
			focusayntion.localStartEdit((Text)_currentFocus.focus());
		} else if ((_currentFocus.whoAreYou().isIn(Const.WEARETION)) &&
				   (_currentFocus.focus().whoAreYou().isEqual(Const.IAMQUALIFICATIONTEXT))) {
			AnyTion focusayntion = (AnyTion) _currentFocus;
			((QualificationText)_currentFocus.focus()).setDynamicLine(null);
			focusayntion.localStartEdit((Text)_currentFocus.focus());
		} else {
			_currentFocus = (Figure) new Text(this,x,y,_simpleEDCPopup);
			((Text)_currentFocus).startEdit();
		}
	}
	/** 텍스트에 대한 편집 작업을 종료하는 함수이다.
	 */
	public void editorFinished(boolean susflag) {
		_editingTag = false;
		Text textfocus = ((Text)_currentFocus.focus());
		textfocus.seeYouLater(susflag);
		FigureID identity = textfocus.whoAreYou();
		if (identity.isEqual(Const.IAMTEXT) && textfocus.isObsolete()) {
			clear(_currentFocus,true);
			_currentFocus = null;
			return;
		}
		_currentFocus.recalcWidthHeight();
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		if ((identity.isIn(Const.WEAREEDITABLECOMPONENT)) &&
			(textfocus.shouldBeDeleted())) {
			textfocus.delete();
		}
		if (_currentFocus.whoAreYou().isEqual(Const.IAMCLASSTEMPLATE)) {
			ClassTemplate thisClass = (ClassTemplate)_currentFocus;
			String name = thisClass.getName();
			
			if (name.length() == 0) {
				thisClass.setDummyName();
			} else if (name.equals("Undefined") == false) {
				boolean setOK = false;
				if (thisClass.emptyClass()) {
					setOK = thisClass.setClassContentFromShadow();
				}
				if (setOK == false) {
					thisClass.registerClassContent();
				}
				if (_frame instanceof Modeler && Modeler.BrowserDlg != null) {
					Modeler.BrowserDlg.replaceClassContentFor(thisClass.getName());
				}
			}
			if (_frame instanceof Modeler) {
				Modeler aModeler = Modeler.allModelers.getFirst();
				while (aModeler != null) {
					aModeler.controller().repaint();
					aModeler = Modeler.allModelers.getNext();
				}
			}
		} else if (_currentFocus.whoAreYou().isEqual(Const.IAMPACKAGE)) {
			PackageTemplate thisPackage = (PackageTemplate)_currentFocus;
			String name = thisPackage.getName();
			if (name.length() == 0) {
				thisPackage.setDummyName();
			}
			if (name.length() > 0) {
				thisPackage.replacePackageNameIfNeed();
			}
		}
		_currentFocus = null;
		flushPaintEvent();
	}   
	/** 화면을 위한 영역을 만드는 함수이다.
	 */
	private void makeCanvasRegion() {
		if (_canvasRgn != null) {
			CRgn.myDestroyRgn(_canvasRgn);
			_canvasRgn = null;
		}
		originX = hScrollBar.getValue();
		originY = vScrollBar.getValue();
		int oX = (int)(((double)originX)/_shrinkRatio);
		int oY = (int)(((double)originY)/_shrinkRatio);
		Rectangle canvasRect = getVisibleRect();
		_width = canvasRect.width;
		_height = canvasRect.height;
		int actualWidth = (int)(((double)_width)/_shrinkRatio);
		int actualHeight = (int)(((double)_height)/_shrinkRatio);
		CPoint pt[] = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			pt[i] = new CPoint();
		}
		pt[0].x = oX;
		pt[0].y = oY;
		pt[1].x = oX+actualWidth;
		pt[1].y = oY;
		pt[2].x = oX+actualWidth;
		pt[2].y = oY+actualHeight;
		pt[3].x = oX;
		pt[3].y = oY+actualHeight;
		_canvasRgn = CRgn.myPolygonRgn(pt,4);
	}
	/** 화면의 크기가 변경되었을 때 호출되는 이벤트 핸들러이다.
	 */
	private void doCanvasResize() {
		makeCanvasRegion();
		Figure all = _figures.getFirst();
		while (all != null) {
			all.setInCanvas(all.checkInRegion(_canvasRgn));
			if (all.inCanvas()) {
				all.makeRegion();
			} else {
				all.resetRegion();
			}
			all = _figures.getNext();
		}
	}
	/** 좌표의 최대 최소 값을 초기화하는 함수이다.
	 */
	public void resetMinMaxXY() {
		_maxX = -1; _maxY = -1;
		_minX = _maxWidth;
		_minY = _maxHeight;
	}
	/** 화면을 축소하는 함수이다.
	 */
	public boolean doZoom(int ratio)
	{
		switch(ratio) {
		case 100:
			_shrinkRatio = 1.0;
			break;
		case 90:
			_shrinkRatio = 0.9;
			break;
		case 80:
			_shrinkRatio = 0.8;
			break;
		case 70:
			_shrinkRatio = 0.7;
			break;
		case 60:
			_shrinkRatio = 0.6;
			break;
		case 50:
			_shrinkRatio = 0.5;
			break;
		case 40:
			_shrinkRatio = 0.4;
			break;
		case 30:
			_shrinkRatio = 0.3;
			break;
		case 20:
			_shrinkRatio = 0.2;
			break;
		case 10:
			_shrinkRatio = 0.1;
			break;
		}
		hScrollBar.setValue(0);
		vScrollBar.setValue(0);
		doCanvasResize();
		repaint();
		return true;
	}
	/** 화면의 일정 영역을 지우는 함수이다.
	 */
	public void clearArea(Graphics erasegc,int x,int y,int w,int h) {
		erasegc.fillRect(x,y,w,h);
	}
	/** 화면의 일정 영역을 지우는 함수이다. 이 함수의 경우 마지막 인자에 따라
	 * paint 이벤트를 발생시키기도 한다.
	 */
	public void clearArea(int x,int y,int w,int h,boolean expose) {
		if (expose) {
			/*
			setRepaint();
			flushPaintEvent();
			*/
			Graphics g = this.getgraphics();
			_paintFlag = false;
			g.setClip(x,y,(int)(((double)w)/_shrinkRatio),(int)(((double)h)/_shrinkRatio));
			this.update(g);
		} else {
			Graphics eg = this.getgraphics();
			eg.setColor(getBackground());
			eg.fillRect(x,y,w,h);
			eg.dispose();
		}
	}
	/** 문자열을 화면에 그려주는 함수이다. 이 함수는 이 컴포넌트의 그래픽을 이용한다.
	 */
	public void drawString(int x,int y,char[] str,int s,int count) {
		if (str[s] == '\0' || str[s] == '\n') return;
		// 이 그래픽스 객체는 상당한 문제가 있어보임
		Graphics g = this.getgraphics();
		this.drawString(g,x,y,str,s,count);
		g.dispose();
	}
	/** 문자열을 화면에 그려주는 함수이다. 이 함수는 인자로 넘겨진 그래픽을 이용한다.
	 */
	public void drawString(Graphics g,int x,int y,char[] str,int s,int count) {
		if (str[s] == '\0' || str[s] == '\n') return;
		Rectangle clipR = new Rectangle(g.getClipBounds());
		Rectangle newClipR = clipR.intersection(new Rectangle(x,y-_fontAscent,count*_fontSizeH,_fontSizeV));
		g.setClip(newClipR);
		g.setFont(_font);
		g.drawChars(str,s,count,x,y); 
		g.setClip(clipR);
	}
	/** 화면의 내용이 수정되는 경우 _dirtyFlag을 세트하는 함수이다.
	 */
	public void setDirtyFlag() {
		_dirtyFlag = true;
	}
	/** background 색을 구하는 함수이다.
	 */
	public Color getBackground() {
		if (printFlag == true) {
			return Color.white;
		} else {
			return super.getBackground();
		}
	}
	/** 화면을 hardcopy 프린트하는 함수이다.
	 */
	public void print(Graphics g) {
		Graphics eg = g.create();
		Rectangle clipR = g.getClipBounds();
		Rectangle newClipR = new Rectangle(clipR);
		eg.setClip(newClipR);
		eg.setColor(Color.white);
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.setInCanvas(true);
			ptr = _figures.getNext();
		}
		ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.draw(g,Const.DRAWING,eg);
			ptr = _figures.getNext();
		}
		ptr = _figures.getFirst();
		while (ptr != null) {
			ptr.setInCanvas(ptr.checkInRegion(_canvasRgn));
			ptr = _figures.getNext();
		}
		eg.dispose();
	}
	/** 프린트 대화 상자에 의해서 호출되는 프린트 시작 함수이다.
	 */
	public int print(Graphics g,PageFormat pageFormat,int pageIndex) throws PrinterException {
		if (pageIndex >= totalPages) {
			return NO_SUCH_PAGE;
		}
		if (totalPages == 1) {
			// 한 페이지에다가 프린트 하는 경우이기 때문에 그림을 중앙에 프린트함
			int pI = pageIndex + 1;
			int nPageRow = 1;
			int nPageColumn = 1;
			Graphics2D g2 = (Graphics2D) g;
			g2.setBackground(Color.white);
			g2.setColor(Color.lightGray);
			g2.setFont(new Font("Courier",Font.ITALIC|Font.BOLD,8));
			g2.setClip(0,0,physicalPaperWidth,physicalPaperHeight);
			g2.drawRect(paperOX,paperOY,paperWidth,paperHeight);
			g2.setColor(Color.black);
			g2.drawString("Domain Object "+getFrameName()+" (ETRI-CSTL) ["+nPageRow+","+nPageColumn+"] of ["+nOfRowOfPages+","+nOfColumnOfPages+"]",
					 paperOX+paperWidth-250,paperOY+paperHeight-2);
			g2.setClip(paperOX,paperOY,paperWidth,paperHeight);
			int figureWidth = (_maxX - _minX) * 3 / 5;
			int figureHeight = (_maxY - _minY) * 3 / 5;
			int tranX = (paperWidth - figureWidth) / 2 - _minX * 3 / 5;
			int tranY = (paperHeight - figureHeight) / 2 - _minY * 3 / 5;
			g2.translate(tranX,tranY);
			g2.scale(3./5.,3./5.);
			g2.setFont(new Font("Courier",Font.PLAIN,MyFontSize));
			printFlag = true;
			print(g2); 
			printFlag = false;
			return PAGE_EXISTS;
		}
		int pI = pageIndex + 1;
		int nPageRow;
		int nPageColumn;
		if (pI % nOfColumnOfPages == 0) {
			nPageRow = pI / nOfColumnOfPages;
			nPageColumn = nOfColumnOfPages;
		} else {
			nPageRow = pI / nOfColumnOfPages + 1;
			nPageColumn = pI % nOfColumnOfPages;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.white);
		g2.setColor(Color.lightGray);
		g2.setFont(new Font("Courier",Font.ITALIC|Font.BOLD,8));
		g2.setClip(0,0,physicalPaperWidth,physicalPaperHeight);
		g2.drawRect(paperOX,paperOY,paperWidth,paperHeight);
		g2.setColor(Color.black);
		g2.drawString("Domain Object "+getFrameName()+" (ETRI-CSTL) ["+nPageRow+","+nPageColumn+"] of ["+nOfRowOfPages+","+nOfColumnOfPages+"]",
					 paperOX+paperWidth-250,paperOY+paperHeight-2);
		g2.setClip(paperOX,paperOY,paperWidth,paperHeight);
		g2.translate(-(nPageColumn-1)*paperWidth+(paperOX+1),-(nPageRow-1)*paperHeight+(paperOY+1));
		g2.scale(3./5.,3./5.);
		g2.setFont(new Font("Courier",Font.PLAIN,MyFontSize));
		printFlag = true;
		print(g2); 
		printFlag = false;
		return PAGE_EXISTS;
	}
	/** 프린트 메뉴 선택에 의하여 프린트 대화 상자를 만들고 사용자의 입력을 받아들이는 함수이다..
	 */
	public void print() {
		if (_figures.empty() == true) {
			this.beep("There is no figure to print.");
			return;
		}

		PrinterJob job = PrinterJob.getPrinterJob();
		if (job == null) {
			System.out.println("Print job fails.");
			return;
		}
		PageFormat pageFormat = job.defaultPage();
		pageFormat.setOrientation(PageFormat.PORTRAIT);
		PageFormat newPageFormat = job.pageDialog(pageFormat);
		if (pageFormat == newPageFormat) {
			return;
		}
		paperOX = 2;
		paperOY = 2;
		if (newPageFormat.getOrientation() == PageFormat.LANDSCAPE) {
			paperOX = 3;
			paperOY = 7;
		}
		job.setPrintable(this,newPageFormat);
		job.validatePage(newPageFormat);
		physicalPaperWidth = (int)newPageFormat.getWidth();
		physicalPaperHeight = (int)newPageFormat.getHeight();
		paperWidth = physicalPaperWidth - 40;
		paperHeight = physicalPaperHeight - 40;
	
		resetMinMaxXY();
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.minMaxXY();
			ptr = _figures.getNext();
		}
		/* 5/3 은 scale과 관련이 있음, 만약 scale을 1/2로 하려면 2/1을 곱해야함 */
		nOfRowOfPages = _maxY / (5*paperHeight/3) + 1;
		nOfColumnOfPages = _maxX / (5*paperWidth/3) + 1;
		totalPages = nOfRowOfPages * nOfColumnOfPages;
		if (job.printDialog()) {
			try {
				job.print();
			} catch (Exception PrinterException) {
			}
		}
	}
	/** 화면의 그림 중에서 패키지 이름들을 모으는 함수이다.
	 */
	public void collectPackageNames(StringList packages) {
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			if (ptr.whoAreYou().equals(Const.IAMPACKAGE)) {
				String pName = ((PackageTemplate)ptr).getName();
				if (packages.inList(pName) == false) {
					packages.insert(pName);
					((PackageTemplate)ptr).collectPackageNames(packages);
				}
			}
			ptr = _figures.getNext();
		}
	}
	/** 버튼 선택 이벤트를 처리하는 콜백 함수이다.
	 */
	public void actionPerformed(ActionEvent event) {
		_popupflag = false;
		String command = event.getActionCommand();
		if (command.equals("Delete")) {
			setDirtyFlag();
			deleteCurrentFigure();
		} else if (command.equals("Copy")) {
			setDirtyFlag();
			copyCurrentFigure();
		} else if (command.equals("Paste")) {
			setDirtyFlag();
			pasteFigureFromMemory();
		} else if (command.equals("EditPaste")) {
			setDirtyFlag();
			pasteFigureAtRandomPosFromMemory();
		} else if (command.equals("CopyMemory")) {
			copyCurrentFigureInMemory();
		} else if (command.equals("Refresh")) {
			localMakeClear(true);
		} else if (command.equals("100")) {
			doZoom(100);
		} else if (command.equals("90")) {
			doZoom(90);
		} else if (command.equals("80")) {
			doZoom(80);
		} else if (command.equals("70")) {
			doZoom(70);
		} else if (command.equals("60")) {
			doZoom(60);
		} else if (command.equals("50")) {
			doZoom(50);
		} else if (command.equals("40")) {
			doZoom(40);
		} else if (command.equals("30")) {
			doZoom(30);
		} else if (command.equals("20")) {
			doZoom(20);
		} else if (command.equals("10")) {
			doZoom(10);
		} else if (command.equals("Print")) {
			print();
		}
	}
	/** 마우스 이벤트를 처리하는 이벤트 핸들러이다.
	 */	
	public void processMouseEvent(MouseEvent event)
	{
		localProcessMouseEvent(event);
	}
	/** 마우스 이벤트를 처리하는 이벤트 핸들러의 내부함수이다.
	 */	
	abstract public void localProcessMouseEvent(MouseEvent event);
	/** 상위 클래스의 마우스 이벤트 핸들러를 호출하는 함수이다. 
	 */
	public void superProcessMouseEvent(MouseEvent event)
	{
		super.processMouseEvent(event);
	}
	/** 마우스의 이동을 처리하는 이벤트 핸들러이다.
	 */
	public void mouseMoved(MouseEvent e) {
		_reallyMovedFlag = true;
		CPoint ev = new CPoint();
		ev.x = (int)((double)e.getX()/_shrinkRatio);
		ev.y = (int)((double)e.getY()/_shrinkRatio);
		if (FLinesStopper == true) {
			linesStopper(ev,false);
		} else if (FFixPointerAbsolute == true) {
			fixPointerAbsolute(ev);
		} else if (FTraceEnterForList == true) {
			localTraceEnter(ev,_activeFigures,true);
		}
		ev = null;
	}
	/** 드랙 상태로 마우스가 이동하는 것을 처리하는 이벤트 핸들러이다.
	 */
	public void mouseDragged(MouseEvent e) {
		_reallyMovedFlag = true;
		CPoint ev = new CPoint();
		ev.x = (int)((double)e.getX()/_shrinkRatio);
		ev.y = (int)((double)e.getY()/_shrinkRatio);
		if (FDrawingHandler == true) {
			localDrawing(ev);
		} else if (FMovingHandler == true) {
			localMoving(ev);
		} else if (FAnyTionDrawingHandler == true) {
			AnyTion.drawingHandler(this,ev);
			if (FTraceEnterForList == true) {
				Point pt = _currentLines.last();
				ev.x = pt.x;
				ev.y = pt.y;
				localTraceEnter(ev,_activeFigures,true);
			}
		}
		ev = null;
	}
	/** 키가 눌러졌을 때 이벤트를 처리하는 핸들러이다.
	 */
	public void keyPressed(KeyEvent e) {
		int nCode = e.getKeyCode();
		OnKeyDown(nCode);
	}
	/** 키가 눌러졌다 띄어졌을 때 이벤트를 처리하는 핸들러이다.
	 */
	public void keyReleased(KeyEvent e) {
		int nCode = e.getKeyCode();
		OnKeyUp(nCode);
	}
	/** 키보드로 부터 한 문자가 입력되었을 때 이벤트를 처리하는 핸들러이다.
	 */
	public void keyTyped(KeyEvent e) {
	}
	/** 범용 이벤트의 처리 함수이다.
	 */
	protected void processEvent(AWTEvent event) {
		super.processEvent(event);
	}
	/** 컴포넌트 이벤트의 처리 함수이다.
	 */
	protected void processComponentEvent(ComponentEvent event) {
		switch(event.getID()) {
		case ComponentEvent.COMPONENT_RESIZED:
			doCanvasResize();
			break;
		}
		super.processComponentEvent(event);
	}
	/** 스크롤 바의 이동에 대한 이벤트 핸들러이다.
	 */
	public void adjustmentValueChanged(AdjustmentEvent event) {
		int spx = hScrollBar.getValue();
		int spy = vScrollBar.getValue();
		originX = spx;
		originY = spy;
		makeCanvasRegion();
		if (_currentFocus != null) {
			_currentFocus.makeRegion();
		}
		Figure all = _figures.getFirst();
		while (all != null) {
			all.setInCanvas(all.checkInRegion(_canvasRgn));
			if (all.inCanvas()) {
				all.makeRegion();
			} else {
				all.resetRegion();
			}
			all = _figures.getNext();
		}
	}
	public void deactivateTextField() {
		activeTextField.getDocument().removeDocumentListener(this);
		activeTextField.removeKeyListener(this);
		addKeyListener(this);
		activeTextField.setVisible(false);
		activeTextField = null;
		this.requestFocus();
	}
	public void activateTextField(int ox,int oy,String lineText) {
		int sx = (int)((double)ox*_shrinkRatio);
		int sy = (int)((double)oy*_shrinkRatio);
		checkLimitForFigure(sx,sy,null);
		int textWidth = Text.getStringWidth(getgraphics(),_font,lineText) + 10;
		if (activeTextField == null) {
			activeTextField = new JTextField(lineText);
			activeTextField.setFont(_font);
			activeTextField.setEditable(true);
			activeTextField.setBorder(new BevelBorder(BevelBorder.LOWERED,getBackground(),getBackground()));
			activeTextField.addKeyListener(this);
			activeTextField.getDocument().addDocumentListener(this);
			removeKeyListener(this);
			add(activeTextField);
			activeTextField.setCaretPosition(0);
			activeTextField.setBounds(sx-2,sy+1,textWidth,_fontSizeV);
			activeTextField.requestFocus();
		} else {
			activeTextField.setText(lineText);
			activeTextField.setCaretPosition(0);
			activeTextField.setBounds(sx-2,sy+1,textWidth,_fontSizeV);
			activeTextField.requestFocus();
		}
	}
	private void changeActiveTextFieldSize() {
		if (activeTextField == null) return;
		int x = activeTextField.getX();
		int y = activeTextField.getY();
		int h = activeTextField.getHeight();
		int w = activeTextField.getWidth();
		if (originX+_width-2 < x+w) return;
		String s = activeTextField.getText();
		int textWidth = Text.getStringWidth(getGraphics(),_font,s) + 10;
		activeTextField.setBounds(x,y,textWidth,h);
	}
	public void insertUpdate(DocumentEvent e) {
		changeActiveTextFieldSize();
	}
	public void removeUpdate(DocumentEvent e) {
		changeActiveTextFieldSize();
	}
	public void changedUpdate(DocumentEvent e) {
		changeActiveTextFieldSize();
	}


	/** 키가 눌러졌을 때의 작업을 수행하는 함수이다.
	 */
	protected void OnKeyDown(int nCode)
	{
		if (_editingTag == true && _currentFocus != null) {
			Text currentText = (Text)_currentFocus.focus();
			switch (nCode) { 
			case KeyEvent.VK_CONTROL :
				_ctrlKeyPressed = true;
				break;
			case KeyEvent.VK_ESCAPE :
				editorFinished(true);
				break;
			case KeyEvent.VK_UP :
				currentText.goup();
				break;
			case KeyEvent.VK_DOWN :
				currentText.godown();
				break;
			case KeyEvent.VK_BACK_SPACE :
				if (_ctrlKeyPressed == true) {
					currentText.delLine();
				}
				break;
			case KeyEvent.VK_ENTER :
				if (currentText.focus().whoAreYou().isEqual(Const.IAMSINGLELINETEXT)) {
					currentText.bye();
					editorFinished(true);
				} else {
					currentText.gonextline();
				}
				break;
			}
		} else if (_currentFocus != null) {
			if (_enable == false) return;
			if (FDrawingHandler == true) return;
			if (FMovingHandler == true) return;
			switch (nCode) {
			case KeyEvent.VK_DELETE :
				deleteCurrentFigure();
				break;
			}
		}
	}
	/** 키가 눌러졌다가 띄어졌을 때의 작업을 수행하는 함수이다.
	 */
	protected void OnKeyUp(int nCode)
	{
		if (_editingTag == true) {
			if (nCode == KeyEvent.VK_CONTROL) _ctrlKeyPressed = false;
		}
	}
	/** 마우스에 의해 선택된 그림 객체를 표시하는 함수이다.
	 */
	public void setSelectFigure(Figure figure)
	{
		_figures.remove(figure);
		_figures.insert(figure,0);
		Graphics eg = this.getgraphics();
		Graphics hg = this.getgraphics();
		eg.setColor(getForeground());
		hg.setColor(Color.blue);
		figure.draw(hg,Const.DRAWING,eg);
		hg.dispose();	
		eg.dispose();
	}
	/** 이 화면이 속하는 프레임의 이름을 돌려주는 함수이다. 
	 */	
	abstract public String getFrameName();
	/** paint 이벤트를 발생시키는 함수이다.
	 */
	public void dispatchPaintEvent(int x,int y,int w,int h) 
	{
		super.paintImmediately(x,y,w,h);
		/*
		_paintEvent = null;
		Rectangle box = new Rectangle(x,y,w,h);
		_paintEvent = new PaintEvent(this,PaintEvent.UPDATE,box);
		_paintEvent.setUpdateRect(box);
		*/
	}
	/** 입력으로 주어진 이름을 갖는 클래스 포인터를 구하는 함수이다.
	 */
	public ClassTemplate getClassTemplatePtr(String name) {
		Figure ptr = _figures.getFirst();
		while (ptr != null) {
			if (ptr.whoAreYou().isEqual(Const.IAMCLASSTEMPLATE)) {
				ClassTemplate foundClass = (ClassTemplate) ptr;
				if (foundClass.getName().compareTo(name) == 0) {
					return foundClass;
				}
			}
			ptr = _figures.getNext();
		}
		return null;
	}
	/** 클래스를 highlight하는 함수
	 */
	public void highlightClass(String name) {
		_focusClass = getClassTemplatePtr(name);
		if (_focusClass == null) return;
		Point pt = _focusClass.center();
		int cx = pt.x;
		int cy = pt.y;
		int dx = cx - (originX+_width/2);
		int dy = cy - (originY+_height/2);
		if (dx != 0 || dy != 0) moveScrollB(dx,dy);
	}
	public void resetFocusClass() {
		_focusClass = null;
		repaint();
	}
	/** aggregation 라인들을 그리는 함수 이다.
	 */
	public void createAggregation(DependencyInfoList actualAggregationInfoList) {
		DependencyInfoList theList = actualAggregationInfoList;
		DependInfo info = theList.getFirst();
		while(info != null) {
			ClassInfo from = info.getFromClass();
			ClassInfo to = info.getToClass();
			ClassContent fromContent = from.getContentPtr();
			ClassContent toContent = to.getContentPtr();
			if (fromContent != null && toContent != null) {
				String fromName = fromContent.nameContent.getName();
				String toName = toContent.nameContent.getName();
				ClassTemplate fromClass = getClassTemplatePtr(fromName);
				ClassTemplate toClass = getClassTemplatePtr(toName);
				if (fromClass != null && toClass != null) 
					fromClass.drawAggTionTo(this,toClass);
			}
			info = theList.getNext();
		}
	}
	/** dependency 라인들을 그리는 함수 이다.
	 */
	public void createDependency(DependencyInfoList actualDependencyInfoList) {
		DependencyInfoList theList = actualDependencyInfoList;
		DependInfo info = theList.getFirst();
		while(info != null) {
			ClassInfo from = info.getFromClass();
			ClassInfo to = info.getToClass();
			ClassContent fromContent = from.getContentPtr();
			ClassContent toContent = to.getContentPtr();
			if (fromContent != null && toContent != null) {
				String fromName = fromContent.nameContent.getName();
				String toName = toContent.nameContent.getName();
				ClassTemplate fromClass = getClassTemplatePtr(fromName);
				ClassTemplate toClass = getClassTemplatePtr(toName);
				if (fromClass != null && toClass != null) 
					fromClass.drawDependTo(this,toClass);
			}
			info = theList.getNext();
		}
	}
	/** 역공학 결과를 그림으로 그려주는 함수이다.
	 */
	public void createReversedDiagram(ParseTreeNode root,StringList classNames,StrRelation gentions) {
		/*
		ParseTreeNode classesLevel = root;
		while (classesLevel != null) {
			System.out.println(classesLevel.value); // "class" or "interface" string
			ParseTreeNode aClassLevel = classesLevel.children;
			String classNameInfo = aClassLevel.toStringAsClassName();
			ParseTreeNode membersLevel = aClassLevel.siblings.children;
			// 아래 루프는 두번 돌면서 처음에는 데이타 멤버만 두번째는 멤버 함수만 프린트한다.
			while (membersLevel != null) {
				if (membersLevel.value.equals("Data")) {
					ParseTreeNode aDataMember = membersLevel.children;
					String data = aDataMember.toStringAsDataMember();
					System.out.println(data);
				}
				membersLevel = membersLevel.siblings;
			}
			membersLevel = aClassLevel.siblings.children;
			while (membersLevel != null) {
				if (membersLevel.value.equals("Function")) {
					ParseTreeNode aMemberFunction = membersLevel.children;
					String func = aMemberFunction.toStringAsMemberFunction();
					System.out.println(func);
				}
				membersLevel = membersLevel.siblings;
			}
			classesLevel = classesLevel.siblings;
		}
		*/
		StringList supersOnly = new StringList();
		StrRelItem item = gentions.getFirst();
		while(item != null) {
			supersOnly.insert(new String(item.first()));
			item.setMark(false);
			item = gentions.getNext();
		}
		StringList roots = new StringList();
		String aSuper = supersOnly.getFirst();
		while (aSuper != null) {
			boolean isRoot = true;
			item = gentions.getFirst();
			while(item != null) {
				if (item.second().compareTo(aSuper) == 0) {
					isRoot = false;
					break;
				}
				item = gentions.getNext();
			}
			if (isRoot == true) {
				roots.insert(new String(aSuper));
			}
			aSuper = supersOnly.getNext();
		}
		DefaultMutableTreeNode theRoot = new DefaultMutableTreeNode(new GentionTreeNodeValue("root"));
		constructInheritanceTree(theRoot,roots,gentions,theRoot);
		if (theRoot.getChildCount() > 0)
			setTreeNodePosition((DefaultMutableTreeNode)theRoot.getFirstChild(),0,0);
		Enumeration e = theRoot.breadthFirstEnumeration();
		deployClassTemplates(root,e);
		Modeler.TheProgressBar.setString("Please wait for drawing inheritance tree.");
		Modeler.TheProgressBar.setValue(root.getSiblingCount());
		int nChild = theRoot.getChildCount();
		if (nChild > 0) {
			for (int i = 0; i < nChild; i++) {
				deployGeneralizations((DefaultMutableTreeNode)theRoot.getChildAt(i));
			}
		}
	}
	private void deployGeneralizations(DefaultMutableTreeNode currentNode) {
		int nChild = currentNode.getChildCount();
		if (nChild == 0) return;
		GentionTreeNodeValue value = (GentionTreeNodeValue)currentNode.getUserObject();
		for (int i = 0; i < nChild; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)currentNode.getChildAt(i);
			GentionTreeNodeValue childValue = (GentionTreeNodeValue)aNode.getUserObject();
			value.classPtr.drawGentionTo(this,childValue.classPtr);
		}		
		for (int i = 0; i < nChild; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)currentNode.getChildAt(i);
			deployGeneralizations(aNode);
		}		
	}
	private void deployClassTemplates(ParseTreeNode root,Enumeration e) {
		int startX = 20;
		int startY = 20;
		int gridWidth = 150;
		int gridHeight = 250;
		int gridX = startX;
		int gridY = startY;
		// skip the first node
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)e.nextElement();
		while (e.hasMoreElements()) {
			treeNode = (DefaultMutableTreeNode)e.nextElement();
			GentionTreeNodeValue value = (GentionTreeNodeValue)treeNode.getUserObject();
			int newI = value.i;
			int newJ = value.j;
			gridX = startX + newI * gridWidth;
			gridY = startY + newJ * gridHeight;
			ClassTemplate newClass = 
							  new ClassTemplate(this,gridX,gridY,fontSizeV(),classPopup());
			value.classPtr = newClass;
			newClass.setNameString(value.className);
			newClass.adjustClassSize();
			newClass.makeRegion();
			newClass.registerClassContent();
			if (_frame instanceof Modeler && Modeler.BrowserDlg != null) {
				Modeler.BrowserDlg.replaceClassContentFor(newClass.getName());
			}
			if (_frame instanceof Modeler) {
				Modeler aModeler = Modeler.allModelers.getFirst();
				while (aModeler != null) {
					aModeler.controller().repaint();
					aModeler = Modeler.allModelers.getNext();
				}
			}
			_figures.insert(newClass,0);
		}
		_maxWidth = gridWidth * gridX + 100;
		_maxHeight = gridHeight * gridY + 100;
		setSize(new Dimension(_maxWidth,_maxHeight));
		setPreferredSize(new Dimension (_maxWidth,_maxHeight));
		
		Modeler.TheProgressBar.setMaximum(root.getSiblingCount());
		int count = 0;
					
		ParseTreeNode nodePtr = root;
		while (nodePtr != null) {
			ParseTreeNode nextClassNode = nodePtr.children;
			String classNameInfo = nextClassNode.toStringAsClassName();
			
			Modeler.TheProgressBar.setString(classNameInfo);
			Modeler.TheProgressBar.setValue(count++);
			
			ClassTemplate foundClass = null;
			Figure ptr = _figures.getFirst();
			while(ptr != null) {
				ClassTemplate clss = (ClassTemplate)ptr;
				if (clss.getName().compareTo(classNameInfo) == 0) {
					foundClass = clss;
					break;
				}
				ptr = _figures.getNext();
			}
			if (foundClass == null) {
				nodePtr = nodePtr.siblings;
				continue;
			}
			ParseTreeNode membersLevel = nextClassNode.siblings.children;
			// 아래 루프는 두번 돌면서 처음에는 데이타 멤버만 두번째는 멤버 함수만 프린트한다.
			while (membersLevel != null) {
				if (membersLevel.value.equals("Data")) {
					ParseTreeNode aDataMember = membersLevel.children;
					String data = aDataMember.toStringAsDataMember();
					foundClass.addDataMemberString(data);
				}
				membersLevel = membersLevel.siblings;
			}
			membersLevel = nextClassNode.siblings.children;
			while (membersLevel != null) {
				if (membersLevel.value.equals("Function")) {
					ParseTreeNode aMemberFunction = membersLevel.children;
					String func = aMemberFunction.toStringAsMemberFunction();
					foundClass.addMemberFunctionString(func);
				}
				membersLevel = membersLevel.siblings;
			}
			
			foundClass.adjustClassSize();
			foundClass.makeRegion();
		
			draw(foundClass);
			String name = foundClass.getName();
			
			foundClass.registerClassContent();
			if (_frame instanceof Modeler && Modeler.BrowserDlg != null) {
				Modeler.BrowserDlg.replaceClassContentFor(foundClass.getName());
			}
			if (_frame instanceof Modeler) {
				Modeler aModeler = Modeler.allModelers.getFirst();
				while (aModeler != null) {
					aModeler.controller().repaint();
					aModeler = Modeler.allModelers.getNext();
				}
			}
			nodePtr = nodePtr.siblings;
		}
		Figure aFig = _figures.getFirst();
		while(aFig != null) {
			draw(aFig);
			aFig = _figures.getNext();
		}
	}
	private void setTreeNodePosition(DefaultMutableTreeNode current,int startIndex,int depth)
	{
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)current.getParent();
		
		int count = parent.getChildCount();
		int totalWidth = startIndex;
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)parent.getChildAt(i);
			GentionTreeNodeValue value = (GentionTreeNodeValue)aNode.getUserObject();
			value.j = depth;
			int width = getMaxWidth(aNode);
			value.i = totalWidth + width/2; 
			totalWidth = totalWidth + width;
			if (aNode.getChildCount() > 0)
				setTreeNodePosition((DefaultMutableTreeNode)aNode.getFirstChild(),totalWidth-width,depth+1);
		}
		
	}
	private int getMaxWidth(DefaultMutableTreeNode current) {
		if (current == null) return 0;
		if (current.getChildCount() == 0) return 1;
		int count = current.getChildCount();
		int w = 0;
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)current.getChildAt(i);
			w = w + getMaxWidth(aNode);
		}		
		return w;
	}
	private void constructInheritanceTree(DefaultMutableTreeNode theRoot,
										  StringList children,
										  StrRelation gentionsOrg,
										  DefaultMutableTreeNode currentNode) {
		if (children.empty() == true) return;
		int count = children.nOfList();
		String childName = children.getFirst();
		for (int i = 0; i < count; i++) {
			currentNode.insert(new DefaultMutableTreeNode(new GentionTreeNodeValue(childName)),i);
			childName = children.getNext();
		}
		count = currentNode.getChildCount();
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)currentNode.getChildAt(i);
			StringList newChildren = new StringList();
			GentionTreeNodeValue value = (GentionTreeNodeValue)aNode.getUserObject();
			
			StrRelation gentions = new StrRelation();
			gentions.copy(gentionsOrg);
			
			StrRelItem item = gentions.getFirst();
			while(item != null) {
				if (item.mark() == false && item.first().compareTo(value.className) == 0) {
					item.setMark(true);
					newChildren.insert(new String(item.second()));
				}
				item = gentions.getNext();
			}
			constructInheritanceTree(theRoot,newChildren,gentions,aNode);
		}
	}
}