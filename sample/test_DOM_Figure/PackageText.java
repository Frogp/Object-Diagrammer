package figure;

import java.io.*;
import java.awt.*;
/** 이 클래스는 텍스트 클래스가 특화된 경우로서 패키지의 부속품인 패키지 텍스트를 표현하기 위한 것이다.
 */
public 
	class PackageText extends Text {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 5010535304552359L;
	/** 이 객체를 포함하고 있는 패키지에 대한 레퍼런스 
	 */
	private PackageTemplate _myPackage;
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
		_myPackage.modifyHeight(-delCount);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		super.delete();
		_myPackage = null;
	}
	/** 생성자이다.
	 */
	public PackageText(GraphicController controller,PackageTemplate myPackage,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		if (controller == null) return;
		_myPackage = myPackage;
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
		int pos = _controller.activeTextField.getCaretPosition();
		if (pos == 0) return;
		super.gonextline();
		_myPackage.modifyHeight(1);
	}
	/** 텍스트의 한 줄을 지우는 함수이다. 텍스트 한 줄을 없애기 위한 키보드 입력은 [Ctrl]+[BackSpace] 이다.
	 */
	public boolean delLine() {
		String currentLine = _controller.activeTextField.getText();
		String actualLine = currentLine.trim();
		if (height() == 1 && actualLine.length() == 0) return true;
		if (super.delLine()) return true;
		_myPackage.modifyHeight(-1);
		return false;
	}
	/** 현재 텍스트의 편집 작업이 마무리되었을 때 정리 작업을 하는 함수이다.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		_myPackage.farewell();
	}
	/** 텍스트의 편집 종료 시에 마무리 작업을 하는 함수이다.
	 */
	public void bye() {
		super.bye();
		_content.doParse(ClassText.NAMEFIELD);
		delEmptyLines();
	}
	/** 이 패키지 텍스트가 패키지 이름 부분인 경우 텍스트의 내용이 비워 있을 때
	 *  미리 주어진 이름 내용으로 채우는 함수이다.
	 */
	public void setDummyName() {
		TextLine firstLine = _content.lineAt(0);
		setX2();
		firstLine.setString("Unknown");
		_content.doParse(ClassText.NAMEFIELD);
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMPACKAGETEXT; 
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		PackageText copied;
		if (ptr == null) {
			copied = new PackageText(_controller,null,0,0,null);
		} else {
			copied = (PackageText)ptr;
		}
		copied._myPackage = _myPackage;
		return (super.born((Figure)copied));
	}
	/** 데이타 멤버 _myPackage에 대한 쓰기 access 함수이다.
	 */
	public void setPackagePtr(PackageTemplate pk) {
		_myPackage = pk;
	}
	/** 이 패키지 텍스트가 패키지 이름 부분인 경우 이름을 지정하는 함수이다.
	*/	
	public void setName(String newName)
	{
		TextLine firstLine = _content.lineAt(0);
		int oldLength = firstLine.getLength();
		firstLine.clear();
		firstLine.setString(newName);
		setX2();
		_content.doParse(ClassText.NAMEFIELD);	
	}
}