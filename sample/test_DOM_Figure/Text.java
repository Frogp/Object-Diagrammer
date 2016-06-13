package figure;


import java.awt.*;
import java.io.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import javax.swing.JTextField;
import java.awt.geom.Rectangle2D;
/** �� Ŭ������ �ؽ�Ʈ ��ü�� ���� �������̽��� �����ϴ� Ŭ�����̴�.
 * �ؽ�Ʈ�� ������ �� ���� ��ü�� ���� ������ ó���Ǵµ� �ؽ�Ʈ�� ������
 * TextContent ��ü�� ����ǰ� ���� ������ JTextField������ �̷������.
 */
public 
	class Text extends Box {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 234952246800358301L;
	/** �ؽ�Ʈ ������ �����ϴ� �����̳� ��ü
	 */
	protected TextContent _content;
	/** ���� �������� �ؽ�Ʈ�� Ŀ�� ��ġ x
	 */
	protected int _cursorX;
	/** ���� �������� �ؽ�Ʈ�� Ŀ�� ��ġ y
	 */
	protected int _cursorY;
	/** ������ ����
	 */
	protected int _deltaV;
	/** false�� �ش��ϴ� ���
	 */
	static int KTGNOTOK = 0;
	/** true�� �ش��ϴ� ���
	 */
	static int KTGOK = 1;
	/** �������̴�.
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
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
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
	/** ����Ÿ ��� _content �� ���� �б� access �Լ��̴�.
	 */
	public TextContent content() {
		return _content;
	}
	/** �ؽ�Ʈ�� ������ �ִ°��� �˻��ϴ� �Լ�
	 */
	public boolean hasContent() {
		TextLine aLine = _content.getFirst();
		while (aLine != null) {
			if (aLine.hasContent()) return true;
			aLine = _content.getNext();
		}
		return false;
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simpleEDCPopup();
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simpleEDCPopup());
	}
	/** �ؽ�Ʈ�� ��(���� ����)
	 */
	public int width() {
		return _content.width();
	}
	/** �ؽ�Ʈ�� ����(���� ����)
	 */
	public int height() {
		return nOfLines();
	}
	/** ����Ÿ ��� _deltaV �� ���� �б� access �Լ��̴�.
	 */
	public int deltaV()	{
		return _deltaV;
	}
	/** ����Ÿ ��� _cursorX �� ���� �б� access �Լ��̴�.
	 */
	public int cursorX() {
		return _cursorX;
	}
	/** ����Ÿ ��� _cursorY �� ���� �б� access �Լ��̴�.
	 */
	public int cursorY() {
		return _cursorY;
	}
	/** ��Ʈ�� ���̿� ���� �б� access �Լ��̴�.
	 */
	public int fontHeight()	{
		return _controller.fontSizeV();
	}
	/**
	 * �� �Լ��� �Ҹ����� ����̴�. Java���� �Ҹ��ڰ� �ʿ����� ������
	 * ������ �Ҹ����� ������μ� ����Ÿ ����� ���� reset��Ű�� ���� ������ ��찡 �ִ�.
	 */
	public void delete() {
		if (_content != null) {
			_content.delete();
			_content = null;
		}
	}
	/** �� ��ü�� ������ ��ü�ΰ� Ȯ���ϴ� �Լ��̴�. Ư�� �ؽ�Ʈ ��ü�� ��� �Էµ�
	 * ���ڿ��� ������ ������ ��ü�̴�.
	 */
	public boolean isObsolete() {
		if (height() ==	1 && width() == 1 && _content.valueAt(0,0) == '\0')	return true;
		return false;
	}
	/** �׸� ��ü�� ������ ��ǥ(x2,y2) ���� ���ڿ� ��õ� ��ǥ ���� ����������� �����ϴ� �Լ��̴�.
	 */
	public boolean checkNear(int x,int y) {
		return false;
	}
	/** �ؽ�Ʈ�� ũ�� ������ �����ϴ� �Լ��̴�.
	 */
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		return false;
	}
	/** ���ڿ��� ��õ� ���� ��ȣ���� �ؽ�Ʈ ������ �׸��� �Լ��̴�. 
	 */
	public void	drawLinesFrom(int lineNo) {
		Graphics g = _controller.getgraphics();
		g.setFont(_controller.font());
		drawLinesFrom(g,lineNo);
		g.dispose();
	}
	/** ���ڿ��� ��õ� ���� ��ȣ���� �־��� �׷��� ��ü�� �̿��Ͽ� �ؽ�Ʈ ������ �׸��� �Լ��̴�. 
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
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
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
	/** �ؽ�Ʈ�� ���� �ÿ� �ؽ�Ʈ�� ���� ���ϸ� _x2 ��ǥ ���� �����ϴ� �Լ��̴�.
	 */
	protected void setX2() {
		_x2 = _x1 + getRealWidth();
	}
	/** �ؽ�Ʈ ���ʺ��� Ŀ���� �־�� �� ��ġ�� ����ϴ� �Լ��̴�. 
	 */
	protected int getCursorPosFrom0(String line,int maxVal) {
		int cursorX = 0;
		int len = line.length();
		if (len <= 0) return 0;
		else if (len >= maxVal) return maxVal;
		else return len;
	}
	/** Ű����� ���� [Enter] Ű�� �ԷµǾ��� �� �� �ٲ��� ó���ϴ� �Լ��̴�.
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
	/** Ű����� ���� �� ���� ȭ��ǥ�� �ԷµǾ��� �� Ŀ�� �̵��� �����ϴ� �Լ��̴�.
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
	/** Ű����� ���� �Ʒ� ���� ȭ��ǥ�� �ԷµǾ��� �� Ŀ�� �̵��� �����ϴ� �Լ��̴�.
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
	/** �ؽ�Ʈ�� �� ���� ����� �Լ��̴�. �ؽ�Ʈ �� ���� ���ֱ� ���� Ű���� �Է��� [Ctrl]+[BackSpace] �̴�.
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
	/** ���� �ؽ�Ʈ�� ���� �۾��� �������Ǿ��� �� ���� �۾��� �ϴ� �Լ��̴�.
	 */
	public void seeYouLater(boolean susflag) {
		bye();
	}
	/** �� �ؽ�Ʈ�� ���� ���� �۾��� �����ϴ� �Լ��̴�.
	 */
	public void	startEdit()	{
		_controller.activateTextField(_x1,_y1+_deltaV*_cursorY,_content.lineAt(_cursorY).getString());
		_controller.activeTextField.setCaretPosition(_cursorX);
	}
	/** �ؽ�Ʈ ������ ���� ���� ���� �Լ��̴�.
	 */
	public int nOfLines() {
		return _content.nOfLines();
	}
	/** �ؽ�Ʈ�� ���� ���� �ÿ� ������ �۾��� �ϴ� �Լ��̴�.
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
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** Ŭ���� �ؽ�Ʈ�� ��쿡 ���Ǵ� ���� �Լ��̴�.
	 */
	public Figure bornForClassText(Figure ptr) {
		Text copied = (Text)ptr;
		copied._cursorX	= _cursorX;
		copied._cursorY	= _cursorY;
		copied._deltaV = _deltaV;
		copied._content	= _content;
		return (super.born((Figure)copied));
	}
	/** �� �׸� ��ü�� �ּ� ���� ���̸� �����ϴ°� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean checkMinWH() {
		return false;
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMTEXT;
	}
	/** �� ��ü�� ���� ���̸� �ٽ� ����Ѵ�. 
	 */
	public void recalcWidthHeight() {
		setX2();
		_y2 = _y1 + height()*_deltaV;
	}
	/** ����Ÿ ��� _shouldBeDeleted�� ���� �б� access �Լ��̴�.
	 */
	public boolean shouldBeDeleted() {
		return false;
	}
	/** �ؽ�Ʈ�� ���� ��ǥ�� ���� ������ ��ǥ ������ �ٽ� ����ϴ� �Լ��̴�.
	 */
	public void recalcCoordFromXy1(int x,int y) {
		_x1 = x;
		_y1 = y;
		setX2();
		_y2 = y + height()*_deltaV;
	}
	/** �ؽ�Ʈ�� ��ǥ������ �ٽ� ����ϴ� �Լ��̴�.
	 */
	public void recalcCoord(int x0,int y0) {
		_deltaV = _controller.fontSizeV();
		_x1 = x0;
		_y1 = y0;
		setX2();
		_y2 = y0 + height()*_deltaV;
		makeRegion();
	}
	/** �ؽ�Ʈ�� ũ�� ������ ������� �����ϴ� �Լ��ϴ� �Լ��̴�.
	 */
	public void normalize() {
		_deltaV = _controller.fontSizeV();
		setX2();
		_y2 = _y1 + (int)(_deltaV * height());
		makeRegion();
	}
	/** �ؽ�Ʈ�� ������ �׸��� �Լ��̴�.
	 */
	public void drawSimple() {
		this.drawLinesFrom(0);
	}
	/** ���콺�� Ŭ�� ��ġ�� ���� Ŀ���� ó�� ��ġ�� ��ǥ�� ����ϴ� �Լ��̴�.
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
	/** �ؽ�Ʈ�� ����
	 */
	protected int newwidth() {
		return width();
	}
	/** �ؽ�Ʈ�� ���� ��
	 */
	public int lines() {
		return nOfLines();
	}
	/** �ؽ�Ʈ�� �������� ���ο� ������ �߰��ϴ� �Լ��̴�.
	 */
	public void insertLineAtLast(TextLine newLine) {
		TextLine lastLine = _content.lineAt(height()-1);
		if (lastLine.hasContent()) {
			_content.insert(newLine);
		} else {
			lastLine.copy(newLine);
		}
	}
	/** �ؽ�Ʈ�� i-��° �ٿ� ���ο� ������ �߰��ϴ� �Լ��̴�.
	 */
	public void insertLineAt(int i,TextLine newLine) {
		TextLine aLine = _content.lineAt(i);
		if (aLine.hasContent()) {
			_content.insertAt(newLine,i);
		} else {
			aLine.copy(newLine);
		}
	}
	/** ���ڿ��� ��õ� ������ �����ϴ� �Լ��̴�.
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
	/** �� �ؽ�Ʈ�� ������ ���ο� �������� �ٲٴ� �Լ��̴�.
	 */
	public void replaceTextContent(TextContent newContent) {
		if (_content == newContent) return;
		_content = null;
		_content = newContent;
	}
}