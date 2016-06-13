package figure;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
/** �� Ŭ������ UML ǥ��� �߿��� �ּ����� ���Ǵ� ��Ʈ ǥ��� ��ü��
 * �׷��ֱ� ���� ���̴�.
 */
public final 
	class Note extends Box {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 5738864656242680262L;
	/** �� ������ ��
	 */
	private int _deltaH;
	/** �� ������ ����
	 */
	private int _deltaV;
	/** ��Ʈ�� ������ �����ϴ� �ؽ�Ʈ
	 */
	private NoteText _noteContent;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� x ����
	 */
	private static int _GapX = 8;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� y ����
	 */
	private static int _GapY = 10;
	/** ��Ʈ ǥ����� ������ ���� �ִ� �ﰢ���� ��
	 */
	private static int _vertexGap = 9;
	/** �� ���� �ּ� ���� (���� ����)
	 */
	private static int _NofCharsinLine = 10;
	/** �ؽ�Ʈ ���� �� ���� �� ���� ����
	 */
	public int _maxchars;
	/** �� ��ü���� Ȱ��ȭ ������ �ؽ�Ʈ ��ü�� ���� ���۷���
	 */
	private transient Figure _focus;
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_noteContent.setController(ptr);
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simpleEDCPopup());
	}
	/** ���� �� ���ڿ��� ���̸� ���ϴ� �Լ��̴�.
	 */
	public int maxChars() {
		_maxchars = _noteContent.width();
		if (_NofCharsinLine-1 > _maxchars) _maxchars = _NofCharsinLine-1;
		return _maxchars;
	}
	/** ���ڿ��� �־��� ũ�� ��ŭ Ŭ������ ���� �����Ѵ�. ������ ������ ���ڴ����̴�.
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
	/** ���ڿ��� �־��� ũ�� ��ŭ Ŭ������ ���̸� �����Ѵ�. ������ ������ ���ڴ����̴�.
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
	/** ���� �� ���ڿ��� ���̸� ���ϴ� �Լ��̴�.
	 */
	private int maxchars() {
		int maxchars = _NofCharsinLine;
		if ( maxchars < _noteContent.width() ) {
			maxchars = _noteContent.width();
		}
		return maxchars;
	}
	/** �޴� ���ÿ� ���� ���ο� ��Ʈ�� ����� �Լ��̴�.
	 */
	public static void makeNewNote(GraphicController controller,int popupX,int popupY,int fontSizeH,int fontSizeV,Popup popupPtr) {
		Note aNote =
					new Note(controller,popupX,popupY,
							 fontSizeH,fontSizeV,popupPtr);
		// normal case follows
		controller.startEditableObject(aNote,aNote._noteContent);
	}
	/** �������̴�.
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
	/** �������̴�. �� �����ڴ� ��ü ����ÿ� �ַ� ���ȴ�.
	 */
	Note(GraphicController controller,int x1,int y1,int x2,int y2,int deltaH,int deltaV,Popup popup) {
		super(controller,x1,y1,x2,y2,popup);

		_stackingFlag = true;
		_focus = (Figure)this;
		_deltaH = deltaH;
		_deltaV = deltaV;
		_maxchars = _NofCharsinLine;
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		_noteContent.delete(); _noteContent = null;
		_focus = null;
	}
	/** ����Ÿ ��� _focus�� ���ο� ���� set�ϴ� access �Լ��̴�.
	 */
	public void setThisFocus(Figure focus) {
		_focus = focus;
	}
	/** ����Ÿ ��� _focus�� null ���� set�ϴ� access �Լ��̴�.
	 */
	public void resetFocus() {
		_focus = (Figure)this;
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMNOTE;
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_noteContent.setInCanvas(flag);
	}
	/** ��ǥ�� �̵� ��Ű�� �Լ��̴�.
	 */
	public void moveCoord(int dx,int dy) {
		super.moveCoord(dx,dy);
		_noteContent.moveCoord(dx,dy);
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simpleEDCPopup();
	}  
	/** �� ��ü�� ���� �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		_popup.popup(event);
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
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
	/** �� ǥ����� ������ �� �ﰢ�� �κ��� ����� �Լ��̴�.
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
	/** �� ǥ����� �ﰢ�� �κ��� �׸��� �Լ��̴�.
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
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
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
	/** �� ��ü�� ���� �ؽ�Ʈ ���� �۾��� ���۵����ϴ� �Լ��̴�.
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
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		return false;
	}
	/** ����Ÿ ��� _focus�� ���� �б� access �Լ�
	 */
	public Figure focus() {
		return _focus;
	}
	/** ����Ÿ ��� _NofCharsinLine �� ���� ��� �Լ��̴�.
	 */
	public static int NofCharsinLine() {
		return _NofCharsinLine;
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
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