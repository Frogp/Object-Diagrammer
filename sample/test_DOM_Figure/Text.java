package figure;


import java.awt.*;
import java.io.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import javax.swing.JTextField;
import java.awt.geom.Rectangle2D;
/** 이 클래스는 텍스트 객체의 공통 인터페이스를 제공하는 클래스이다.
 * 텍스트의 관리는 두 가지 객체에 의해 나뉘어 처리되는데 텍스트의 내용은
 * TextContent 객체에 저장되고 실제 편집은 JTextField에의해 이루어진다.
 */
public 
	class Text extends Box {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 234952246800358301L;
	/** 텍스트 내용을 저장하는 컨테이너 객체
	 */
	protected TextContent _content;
	/** 현재 편집중인 텍스트의 커서 위치 x
	 */
	protected int _cursorX;
	/** 현재 편집중인 텍스트의 커서 위치 y
	 */
	protected int _cursorY;
	/** 문자의 높이
	 */
	protected int _deltaV;
	/** false에 해당하는 상수
	 */
	static int KTGNOTOK = 0;
	/** true에 해당하는 상수
	 */
	static int KTGOK = 1;
	/** 생성자이다.
	 */
	Text(GraphicController controller,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		_cursorX = 0;
		_cursorY = 0;
		if (controller == null) return;
		_deltaV = controller.fontSizeV();
		_content = new TextContent();
		_content.newLineAt(0);
		_x2 = _x1 + 1;
		_y2 = _y1 + _deltaV;
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		if (w == 0 || h == 0) return;
		g.fillRect(_x1,_y1,w+1,h+1);
		if (expose == true) {
			_controller.setRepaint();
		}
	}
	/** 데이타 멤버 _content 에 대한 읽기 access 함수이다.
	 */
	public TextContent content() {
		return _content;
	}
	/** 텍스트의 내용이 있는가를 검사하는 함수
	 */
	public boolean hasContent() {
		TextLine aLine = _content.getFirst();
		while (aLine != null) {
			if (aLine.hasContent()) return true;
			aLine = _content.getNext();
		}
		return false;
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simpleEDCPopup();
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simpleEDCPopup());
	}
	/** 텍스트의 폭(문자 단위)
	 */
	public int width() {
		return _content.width();
	}
	/** 텍스트의 길이(문자 단위)
	 */
	public int height() {
		return nOfLines();
	}
	/** 데이타 멤버 _deltaV 에 대한 읽기 access 함수이다.
	 */
	public int deltaV()	{
		return _deltaV;
	}
	/** 데이타 멤버 _cursorX 에 대한 읽기 access 함수이다.
	 */
	public int cursorX() {
		return _cursorX;
	}
	/** 데이타 멤버 _cursorY 에 대한 읽기 access 함수이다.
	 */
	public int cursorY() {
		return _cursorY;
	}
	/** 폰트의 높이에 대한 읽기 access 함수이다.
	 */
	public int fontHeight()	{
		return _controller.fontSizeV();
	}
	/**
	 * 이 함수는 소멸자의 대용이다. Java에서 소멸자가 필요없기는 하지만
	 * 때때로 소멸자의 대용으로서 데이타 멤버의 값을 reset시키는 것이 안전한 경우가 있다.
	 */
	public void delete() {
		if (_content != null) {
			_content.delete();
			_content = null;
		}
	}
	/** 이 객체가 무용한 객체인가 확인하는 함수이다. 특히 텍스트 객체의 경우 입력된
	 * 문자열이 없으면 무용한 객체이다.
	 */
	public boolean isObsolete() {
		if (height() ==	1 && width() == 1 && _content.valueAt(0,0) == '\0')	return true;
		return false;
	}
	/** 그림 객체의 마지막 좌표(x2,y2) 값이 인자에 명시된 좌표 값과 가까워지도록 설정하는 함수이다.
	 */
	public boolean checkNear(int x,int y) {
		return false;
	}
	/** 텍스트의 크기 비율을 조정하는 함수이다.
	 */
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantResize(CPoint point) {
		return false;
	}
	/** 인자에서 명시된 라인 번호부터 텍스트 내용을 그리는 함수이다. 
	 */
	public void	drawLinesFrom(int lineNo) {
		Graphics g = _controller.getgraphics();
		g.setFont(_controller.font());
		drawLinesFrom(g,lineNo);
		g.dispose();
	}
	/** 인자에서 명시된 라인 번호부터 주어진 그래픽 객체를 이용하여 텍스트 내용을 그리는 함수이다. 
	 */
	public void	drawLinesFrom(Graphics g,int lineNo) {
		int	x =	_x1;
		int	y =	_y1;
		TextLine aLine	= _content.getFirst();
		for	(int i = 0;	i <	lineNo;	i++) {
			y = y + _deltaV;
			aLine =	_content.getNext();
		}
		while(aLine	!= null) {
			g.drawString(aLine.theLine,x,y+_controller.fontAscent());
//			_controller.drawString(g,x,y+_controller.fontAscent(),aLine.line,0,MySys.strlen(aLine.line,0));
			y =	y +	_deltaV;
			aLine =	_content.getNext();
		}
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int mode,Graphics specialgc) {
		if (!_inCanvas) return;
		if (mode == Const.LOWLIGHTING) {
			super.draw(specialgc,mode,specialgc);
		} else if (mode == Const.HIGHLIGHTING) {
			super.draw(g,mode,specialgc);
			return;
		} else if (mode == Const.RUBBERBANDING) {
			super.draw(g,mode,specialgc);
			return;
		}	
		drawLinesFrom(g,0);
	}
	protected int getRealWidth() {
		Graphics g = _controller.getgraphics();
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext frc = g2.getFontRenderContext();
		return _content.width(_controller.font(),frc);
	}
	static public int getStringWidth(Graphics g,Font f,String s) {
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D r = f.getStringBounds(s,frc);
		return (int)r.getWidth();
	}
	/** 텍스트의 편집 시에 텍스트의 폭이 변하면 _x2 좌표 값을 수정하는 함수이다.
	 */
	protected void setX2() {
		_x2 = _x1 + getRealWidth();
	}
	/** 텍스트 왼쪽부터 커서가 있어야 할 위치를 계산하는 함수이다. 
	 */
	protected int getCursorPosFrom0(String line,int maxVal) {
		int cursorX = 0;
		int len = line.length();
		if (len <= 0) return 0;
		else if (len >= maxVal) return maxVal;
		else return len;
	}
	/** 키보드로 부터 [Enter] 키가 입력되었을 때 줄 바꿈을 처리하는 함수이다.
	 */
	public void gonextline() {
		String text = _controller.activeTextField.getText();
		if (text == null) {
			_cursorX = 0;
			_content.lineAt(_cursorY).setString(new String(""));
		} else {
			_content.lineAt(_cursorY).setString(new String(text));
			_cursorX = _controller.activeTextField.getCaretPosition();
		}
		TextLine nextLine = _content.splitLineAt(_cursorX,_cursorY);
		setX2(); 
		_controller.clearArea(_x1,_y1+_deltaV*_cursorY,_x2-_x1,_y2-_y1-_deltaV*_cursorY,false);
		_y2 = _y2 + _deltaV;
		_cursorY++;
		drawLinesFrom(_cursorY);
		_controller.activateTextField(_x1,_y1+_deltaV*_cursorY,_content.lineAt(_cursorY).getString());
	}
	/** 키보드로 부터 윗 방향 화살표가 입력되었을 때 커서 이동을 수행하는 함수이다.
	 */
	public void goup() {
		if (_cursorY ==	0) return;
		String text = _controller.activeTextField.getText();
		if (text == null) {
			_content.lineAt(_cursorY).setString(new String(""));
			_cursorX = 0;
		} else {
			_content.lineAt(_cursorY).setString(new String(text));
			_cursorX = _controller.activeTextField.getCaretPosition();
		}
		setX2();
		_cursorY--;
		TextLine newLine =	_content.lineAt(_cursorY);
		_controller.activateTextField(_x1,_y1+_deltaV*_cursorY,_content.lineAt(_cursorY).getString());
		_cursorX = getCursorPosFrom0(newLine.theLine,_cursorX);
		_controller.activeTextField.setCaretPosition(_cursorX);
	}
	/** 키보드로 부터 아래 방향 화살표가 입력되었을 때 커서 이동을 수행하는 함수이다.
	 */
	public void godown() {
		if (_cursorY >=	height()-1)	return;
		String text = _controller.activeTextField.getText();
		if (text == null) {
			_content.lineAt(_cursorY).setString(new String(""));
			_cursorX = 0;
		} else {
			_content.lineAt(_cursorY).setString(new String(text));
			_cursorX = _controller.activeTextField.getCaretPosition();
		}
		setX2();
		_cursorY++;
		TextLine newLine =	_content.lineAt(_cursorY);
		_controller.activateTextField(_x1,_y1+_deltaV*_cursorY,_content.lineAt(_cursorY).getString());
		_cursorX = getCursorPosFrom0(newLine.theLine,_cursorX);
		_controller.activeTextField.setCaretPosition(_cursorX);
	}
	/** 텍스트의 한 줄을 지우는 함수이다. 텍스트 한 줄을 없애기 위한 키보드 입력은 [Ctrl]+[BackSpace] 이다.
	 */
	public boolean delLine() {
		if (height() ==	1) {
			_content.lineAt(0).clear();
			_cursorX = 0; _cursorY = 0;
			_controller.activeTextField.setText("");
			_x2 = _x1 + 1;
			return true;
		}
		if (_cursorY ==	height()-1)	{
			_content.removeLineAt(_cursorY);
			_cursorY--;
		} else {
			_content.removeLineAt(_cursorY);
		}
		_cursorX = 0;
		_controller.clearArea(_x1,_y1+_deltaV*_cursorY,_x2-_x1,_y2-_y1-_deltaV*_cursorY,false);
		drawLinesFrom(_cursorY);
		setX2();
		_y2 = _y2 - _deltaV;
		TextLine newLine =	_content.lineAt(_cursorY);
		_controller.activateTextField(_x1,_y1+_deltaV*_cursorY,_content.lineAt(_cursorY).getString());
		return false;
	}
	/** 현재 텍스트의 편집 작업이 마무리되었을 때 정리 작업을 하는 함수이다.
	 */
	public void seeYouLater(boolean susflag) {
		bye();
	}
	/** 이 텍스트에 대한 편집 작업을 시작하는 함수이다.
	 */
	public void	startEdit()	{
		_controller.activateTextField(_x1,_y1+_deltaV*_cursorY,_content.lineAt(_cursorY).getString());
		_controller.activeTextField.setCaretPosition(_cursorX);
	}
	/** 텍스트 내용의 라인 수를 세는 함수이다.
	 */
	public int nOfLines() {
		return _content.nOfLines();
	}
	/** 텍스트의 편집 종료 시에 마무리 작업을 하는 함수이다.
	 */
	public void bye() {
		String text = _controller.activeTextField.getText();
		if (text == null) {
			_content.lineAt(_cursorY).setString(new String(""));
		} else {
			_content.lineAt(_cursorY).setString(new String(text));
		}
		setX2();
		_controller.deactivateTextField();
		_cursorX = 0;
		_cursorY = 0;
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		Text copied;
		if (ptr == null) {
			copied = new Text(_controller,0,0,null);
		} else {
			copied = (Text)ptr;
		}
		copied._cursorX	= _cursorX;
		copied._cursorY	= _cursorY;
		copied._deltaV = _deltaV;
		copied._content	= _content.born();
		return (super.born((Figure)copied));
	}
	/** 클래스 텍스트의 경우에 사용되는 복사 함수이다.
	 */
	public Figure bornForClassText(Figure ptr) {
		Text copied = (Text)ptr;
		copied._cursorX	= _cursorX;
		copied._cursorY	= _cursorY;
		copied._deltaV = _deltaV;
		copied._content	= _content;
		return (super.born((Figure)copied));
	}
	/** 이 그림 객체의 최소 폭과 높이를 만족하는가 확인하는 함수이다.
	 */
	public boolean checkMinWH() {
		return false;
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMTEXT;
	}
	/** 이 객체의 폭과 높이를 다시 계산한다. 
	 */
	public void recalcWidthHeight() {
		setX2();
		_y2 = _y1 + height()*_deltaV;
	}
	/** 데이타 멤버 _shouldBeDeleted에 대한 읽기 access 함수이다.
	 */
	public boolean shouldBeDeleted() {
		return false;
	}
	/** 텍스트의 원점 좌표로 부터 나머지 좌표 값들을 다시 계산하는 함수이다.
	 */
	public void recalcCoordFromXy1(int x,int y) {
		_x1 = x;
		_y1 = y;
		setX2();
		_y2 = y + height()*_deltaV;
	}
	/** 텍스트의 좌표값들을 다시 계산하는 함수이다.
	 */
	public void recalcCoord(int x0,int y0) {
		_deltaV = _controller.fontSizeV();
		_x1 = x0;
		_y1 = y0;
		setX2();
		_y2 = y0 + height()*_deltaV;
		makeRegion();
	}
	/** 텍스트의 크기 비율을 원래대로 조장하는 함수하는 함수이다.
	 */
	public void normalize() {
		_deltaV = _controller.fontSizeV();
		setX2();
		_y2 = _y1 + (int)(_deltaV * height());
		makeRegion();
	}
	/** 텍스트의 내용을 그리는 함수이다.
	 */
	public void drawSimple() {
		this.drawLinesFrom(0);
	}
	/** 마우스의 클릭 위치로 부터 커서가 처음 위치할 좌표를 계산하는 함수이다.
	 */
	public void setXY(int x,int y)
	{
//		int diffX = x - _x1;
		int diffY = y - _y1;
//		int offsetX = diffX / _deltaH;
		int offsetY = diffY / _deltaV;
		_cursorX = 0;
		_cursorY = offsetY;
		int lineLength = _content.lineAt(_cursorY).getLength();
		if (_cursorX > lineLength) {
			_cursorX = lineLength;
		}
	}
	/** 텍스트의 길이
	 */
	protected int newwidth() {
		return width();
	}
	/** 텍스트의 라인 수
	 */
	public int lines() {
		return nOfLines();
	}
	/** 텍스트의 마지막에 새로운 라인을 추가하는 함수이다.
	 */
	public void insertLineAtLast(TextLine newLine) {
		TextLine lastLine = _content.lineAt(height()-1);
		if (lastLine.hasContent()) {
			_content.insert(newLine);
		} else {
			lastLine.copy(newLine);
		}
	}
	/** 텍스트의 i-번째 줄에 새로운 라인을 추가하는 함수이다.
	 */
	public void insertLineAt(int i,TextLine newLine) {
		TextLine aLine = _content.lineAt(i);
		if (aLine.hasContent()) {
			_content.insertAt(newLine,i);
		} else {
			aLine.copy(newLine);
		}
	}
	/** 인자에서 명시된 라인을 삭제하는 함수이다.
	 */
	public TextLine removeLineAt(int pos) {
		if (height() ==	1) {
			TextLine copied = new TextLine();
			copied.copy(_content.lineAt(0));
			_content.lineAt(0).clear();
			return copied;
		}
		TextLine copied = new TextLine();
		copied.copy(_content.lineAt(pos));
		_content.removeLineAt(pos);
		return copied;
	}
	/** 이 텍스트의 내용을 새로운 내용으로 바꾸는 함수이다.
	 */
	public void replaceTextContent(TextContent newContent) {
		if (_content == newContent) return;
		_content = null;
		_content = newContent;
	}
}