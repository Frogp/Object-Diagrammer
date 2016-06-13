package figure;

import java.io.*;
import java.awt.*;
/** �� Ŭ������ �ؽ�Ʈ Ŭ������ Ưȭ�� ���μ� ��Ʈ�� �μ�ǰ�� ��Ʈ �ؽ�Ʈ�� ǥ���ϱ� ���� ���̴�.
 */
public 
	class NoteText extends Text {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 6305605703789502426L;
	/** �� ��ü�� �����ϰ� �ִ� ��Ʈ�� ���� ���۷��� 
	 */
	private Note _myNote;
	/** �ؽ�Ʈ ��ü�� ���� ������ ���� �� �� ���ε��� �����ϴ� �Լ��̴�.
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
		}
		if (delCount == 0) return;
		int sx1 = _x1; int sy1 = _y1;
		int sx2 = _x2; int sy2 = _y2;
		_y2 = _y1 + _deltaV * height();
		Box tmp = new Box(_controller,sx1,sy1,sx2,sy2,null);
		_controller.clear(tmp,false);
		tmp.delete();
		drawSimple();
		_myNote.modifyHeight(-delCount);
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		super.delete();
		_myNote = null;
	}
	/** �������̴�.
	 */
	public NoteText(GraphicController controller,Note myNote,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		if (controller == null) return;
		_myNote = myNote;
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		moveCoord(dx,dy);
	}
	/** Ű����� ���� [Enter] Ű�� �ԷµǾ��� �� �� �ٲ��� ó���ϴ� �Լ��̴�.
	 */
	/*
	public void gonextline() {
		if (_cursorX == 0) return;
		int maxCharsOld = _myNote.maxChars();
		super.gonextline();
		_screen.vanish();
		_myNote.modifyHeight(1);
		_screen.activate();
		int maxCharsNew = _myNote.maxChars();
		if (maxCharsOld == maxCharsNew) return;
		_screen.vanish();
		_myNote.modifyWidth(maxCharsNew-maxCharsOld);
		_screen.activate();
	}
	*/
	/** �ؽ�Ʈ�� �� ���� ����� �Լ��̴�. �ؽ�Ʈ �� ���� ���ֱ� ���� Ű���� �Է��� [Ctrl]+[BackSpace] �̴�.
	 */
	/*
	public boolean delLine() {
		if (height() == 1 && _content.valueAt(0,0) == '\0') {
			return true;
		}
		int maxCharsOld = _myNote.maxChars();
		if (super.delLine()) {
			int maxCharsNew = _myNote.maxChars();
			if (maxCharsOld == maxCharsNew) return true;
			_screen.vanish();
			_myNote.modifyWidth(maxCharsNew-maxCharsOld);
			_screen.activate();
			return true;
		}
		_screen.vanish();
		_myNote.modifyHeight(-1);
		_screen.activate();
		int maxCharsNew = _myNote.maxChars();
		if (maxCharsOld == maxCharsNew) return false;
		_screen.vanish();
		_myNote.modifyWidth(maxCharsNew-maxCharsOld);
		_screen.activate();
		return false;
	}
	*/
	/** ���� �ؽ�Ʈ�� ���� �۾��� �������Ǿ��� �� ���� �۾��� �ϴ� �Լ��̴�.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
	}
	/** �ؽ�Ʈ�� ���� ���� �ÿ� ������ �۾��� �ϴ� �Լ��̴�.
	 */
	public void bye() {
		super.bye();
		delEmptyLines();
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMNOTETEXT; 
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		NoteText copied;
		int x = _x1; int y = _y1;
		if (ptr == null) {
			copied = new NoteText(_controller,null,0,0,null);
		} else {
			copied = (NoteText)ptr;
		}
		copied._myNote = _myNote;
		return (super.born((Figure)copied));
	}
	/** ����Ÿ ��� _myNote�� ���� ���� access �Լ��̴�.
	 */
	public void setNotePtr(Note pk) {
		_myNote = pk;
	}
}
