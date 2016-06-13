package figure;

import java.io.*;
import java.awt.*;
/** 이 클래스는 텍스트 클래스가 특화된 경우로서 노트의 부속품인 노트 텍스트를 표현하기 위한 것이다.
 */
public 
	class NoteText extends Text {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 6305605703789502426L;
	/** 이 객체를 포함하고 있는 노트에 대한 레퍼런스 
	 */
	private Note _myNote;
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
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		super.delete();
		_myNote = null;
	}
	/** 생성자이다.
	 */
	public NoteText(GraphicController controller,Note myNote,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		if (controller == null) return;
		_myNote = myNote;
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		moveCoord(dx,dy);
	}
	/** 키보드로 부터 [Enter] 키가 입력되었을 때 줄 바꿈을 처리하는 함수이다.
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
	/** 텍스트의 한 줄을 지우는 함수이다. 텍스트 한 줄을 없애기 위한 키보드 입력은 [Ctrl]+[BackSpace] 이다.
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
	/** 현재 텍스트의 편집 작업이 마무리되었을 때 정리 작업을 하는 함수이다.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
	}
	/** 텍스트의 편집 종료 시에 마무리 작업을 하는 함수이다.
	 */
	public void bye() {
		super.bye();
		delEmptyLines();
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMNOTETEXT; 
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
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
	/** 데이타 멤버 _myNote에 대한 쓰기 access 함수이다.
	 */
	public void setNotePtr(Note pk) {
		_myNote = pk;
	}
}
