package figure;
import java.awt.*;
import java.io.*;
/** 이 클래스는 텍스트 클래스가 특화된 경우로서 클래스의 부속품인 클래스 텍스트를 표현하기 위한 것이다.
 */
public final 
	class ClassText extends Text {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 1794797505500759336L;
	/** 현재 텍스트 객체가 무슨 목적으로 사용되는 가를 나타내는 상수 :클래스의 이름 부분
	 */
	public static int NAMEFIELD = 1;
	/** 현재 텍스트 객체가 무슨 목적으로 사용되는 가를 나타내는 상수 :클래스의 데이타 멤버 부분
	 */
	public static int VARFIELD = 2;
	/** 현재 텍스트 객체가 무슨 목적으로 사용되는 가를 나타내는 상수 :클래스의 멤버 함수 부분
	 */
	public static int METHODFIELD = 3;
	/** 현재 텍스트 객체가 클래스에서 무슨 목적으로 사용되는 가를 나타내는 데이타 멤버
	 */
	private int _whoami;
	/** 이 객체를 포함하고 있는 클래스에 대한 레퍼런스 
	 */
	private ClassTemplate _myClass;
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
			if (aLine.theLine.length() == 0) {
				delLines[delCount] = index;
				delCount++;
			}
			index++;
			aLine = _content.getNext();
		}
		if (delCount == nLines) {
			delCount = delCount - 1;
		}
		for(int i = delCount-1; i >= 0; i--) {
			_content.removeLineAt(delLines[i]);
		}
		if (delCount == 0) {
			return;
		}
		_y2 = _y1 + _deltaV * height();
		_myClass.modifyHeight(_whoami,-delCount);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		_content = null;
		super.delete();
		_myClass = null;
	}
	/** 생성자이다.
	 */
	public ClassText(GraphicController controller,ClassTemplate myClass,int whoami,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		if (controller == null) return;
		_whoami = whoami;
		_myClass = myClass;
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		moveCoord(dx,dy);
	}
	/** 키보드로 부터 [Enter] 키가 입력되었을 때 줄 바꿈을 처리하는 함수이다.
	 */
	public void gonextline() {
		String currentLine = _controller.activeTextField.getText();
		String actualLine = currentLine.trim();
		if (actualLine.length() == 0) return;
		if (_whoami == NAMEFIELD) {
			activateNextText(_myClass);
			return;
		}
		super.gonextline();
		_myClass.modifyHeight(_whoami,1);
	}
	/** 키보드로 부터 윗 방향 화살표가 입력되었을 때 커서 이동을 수행하는 함수이다.
	 */
	public void goup() {
		if (_cursorY == 0) {
			activatePrevText(_myClass);
			return;
		}
		super.goup();
	}
	/** 키보드로 부터 아래 방향 화살표가 입력되었을 때 커서 이동을 수행하는 함수이다.
	 */
	public void godown() {
		if (_cursorY >= height()-1) {
			activateNextText(_myClass);
			return;
		}
		super.godown();
	}
	/** 텍스트의 한 줄을 지우는 함수이다. 텍스트 한 줄을 없애기 위한 키보드 입력은 [Ctrl]+[BackSpace] 이다.
	 */
	public boolean delLine() {
		String currentLine = _controller.activeTextField.getText();
		String actualLine = currentLine.trim();
		if (height() == 1 && actualLine.length() == 0) return true;
		if (super.delLine()) return true;
		_myClass.modifyHeight(_whoami,-1);
		return false;
	}
	/** 현재 텍스트의 편집 작업이 마무리되었을 때 정리 작업을 하는 함수이다.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		_myClass.setX2AndCentering();
	}
	/** 텍스트의 편집 종료 시에 마무리 작업을 하는 함수이다.
	 */
	public void bye() {
		super.bye();
		_content.doParse(_whoami);
		delEmptyLines();
	}
	/** 이 클래스 텍스트가 클래스 이름 부분인 경우 텍스트의 내용이 비워 있을 때
	 *  미리 주어진 이름 내용으로 채우는 함수이다.
	 */
	public void setDummyName() {
		TextLine firstLine = _content.lineAt(0);
		firstLine.setString("Undefined");
		setX2();
		_myClass.setX2AndCentering();
		_content.doParse(_whoami);
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMCLASSTEXT; 
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		ClassText copied;
		int x = _x1; int y = _y1;
		if (ptr == null) {
			copied = new ClassText(_controller,null,0,0,0,null);
		} else {
			copied = (ClassText)ptr;
		}
		copied._whoami = _whoami;
		copied._myClass = _myClass;
		return (super.bornForClassText((Figure)copied));
	}
	/** 현재 클래스 텍스트의 편집을 마치고 다음 클래스 텍스트를 활성화 시키는 함수이다.
	 * 클래스 텍스트의 편집 순서는 클래스 이름 -> 데이타 멤버 -> 멤버 함수 순이다.
	 */
	public void activateNextText(ClassTemplate currentclass) {
		bye();
		int x,y;
		ClassText getNext = null;
		if (_whoami == NAMEFIELD) {
			_myClass.setClassContentFromShadow();
//			_controller.clear(_myClass,true);
//			_controller.draw(_myClass);
			getNext = currentclass.vars();
		} else if (_whoami == VARFIELD) {
			getNext = currentclass.methods();
		} else if (_whoami == METHODFIELD) {
			getNext = currentclass.classname();
		}
		_myClass.setThisFocus((Figure)getNext);
		getNext.startEdit();
	}
	/** 현재 클래스 텍스트의 편집을 마치고 이전 클래스 텍스트를 활성화 시키는 함수이다.
	 * 클래스 텍스트의 편집 순서는 클래스 이름 <- 데이타 멤버 <- 멤버 함수와 같이 역순이다.
	 */
	public void activatePrevText(ClassTemplate currentclass) {
		bye();
		int x,y;
		ClassText getNext = null;
		if (_whoami == NAMEFIELD) {
			_myClass.setClassContentFromShadow();
//			_controller.clear(_myClass,true);
//			_controller.draw(_myClass);
			getNext = currentclass.methods();
		} else if (_whoami == VARFIELD) {
			getNext = currentclass.classname();
		} else if (_whoami == METHODFIELD) {
			getNext = currentclass.vars();
		}
		_myClass.setThisFocus((Figure)getNext);
		getNext.startEdit();
	}
	/** 데이타 멤버 _myClass에 대한 쓰기 access 함수이다.
	 */
	public void setClassPtr(ClassTemplate clss) {
		_myClass = clss;
	}
	/** 이 클래스 텍스트가 클래스 이름 부분인 경우 이름을 지정하는 함수이다.
	*/	
	public void setName(String newName)
	{
		TextLine firstLine = _content.lineAt(0);
		firstLine.clear();
		firstLine.setString(newName);
		setX2();
		_myClass.setX2AndCentering();
		_content.doParse(_whoami);
	}	
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.memberPopup());
	}
}