package figure;
import java.awt.*;
import java.io.*;
/** �� Ŭ������ �ؽ�Ʈ Ŭ������ Ưȭ�� ���μ� Ŭ������ �μ�ǰ�� Ŭ���� �ؽ�Ʈ�� ǥ���ϱ� ���� ���̴�.
 */
public final 
	class ClassText extends Text {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 1794797505500759336L;
	/** ���� �ؽ�Ʈ ��ü�� ���� �������� ���Ǵ� ���� ��Ÿ���� ��� :Ŭ������ �̸� �κ�
	 */
	public static int NAMEFIELD = 1;
	/** ���� �ؽ�Ʈ ��ü�� ���� �������� ���Ǵ� ���� ��Ÿ���� ��� :Ŭ������ ����Ÿ ��� �κ�
	 */
	public static int VARFIELD = 2;
	/** ���� �ؽ�Ʈ ��ü�� ���� �������� ���Ǵ� ���� ��Ÿ���� ��� :Ŭ������ ��� �Լ� �κ�
	 */
	public static int METHODFIELD = 3;
	/** ���� �ؽ�Ʈ ��ü�� Ŭ�������� ���� �������� ���Ǵ� ���� ��Ÿ���� ����Ÿ ���
	 */
	private int _whoami;
	/** �� ��ü�� �����ϰ� �ִ� Ŭ������ ���� ���۷��� 
	 */
	private ClassTemplate _myClass;
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
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		_content = null;
		super.delete();
		_myClass = null;
	}
	/** �������̴�.
	 */
	public ClassText(GraphicController controller,ClassTemplate myClass,int whoami,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		if (controller == null) return;
		_whoami = whoami;
		_myClass = myClass;
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		moveCoord(dx,dy);
	}
	/** Ű����� ���� [Enter] Ű�� �ԷµǾ��� �� �� �ٲ��� ó���ϴ� �Լ��̴�.
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
	/** Ű����� ���� �� ���� ȭ��ǥ�� �ԷµǾ��� �� Ŀ�� �̵��� �����ϴ� �Լ��̴�.
	 */
	public void goup() {
		if (_cursorY == 0) {
			activatePrevText(_myClass);
			return;
		}
		super.goup();
	}
	/** Ű����� ���� �Ʒ� ���� ȭ��ǥ�� �ԷµǾ��� �� Ŀ�� �̵��� �����ϴ� �Լ��̴�.
	 */
	public void godown() {
		if (_cursorY >= height()-1) {
			activateNextText(_myClass);
			return;
		}
		super.godown();
	}
	/** �ؽ�Ʈ�� �� ���� ����� �Լ��̴�. �ؽ�Ʈ �� ���� ���ֱ� ���� Ű���� �Է��� [Ctrl]+[BackSpace] �̴�.
	 */
	public boolean delLine() {
		String currentLine = _controller.activeTextField.getText();
		String actualLine = currentLine.trim();
		if (height() == 1 && actualLine.length() == 0) return true;
		if (super.delLine()) return true;
		_myClass.modifyHeight(_whoami,-1);
		return false;
	}
	/** ���� �ؽ�Ʈ�� ���� �۾��� �������Ǿ��� �� ���� �۾��� �ϴ� �Լ��̴�.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		_myClass.setX2AndCentering();
	}
	/** �ؽ�Ʈ�� ���� ���� �ÿ� ������ �۾��� �ϴ� �Լ��̴�.
	 */
	public void bye() {
		super.bye();
		_content.doParse(_whoami);
		delEmptyLines();
	}
	/** �� Ŭ���� �ؽ�Ʈ�� Ŭ���� �̸� �κ��� ��� �ؽ�Ʈ�� ������ ��� ���� ��
	 *  �̸� �־��� �̸� �������� ä��� �Լ��̴�.
	 */
	public void setDummyName() {
		TextLine firstLine = _content.lineAt(0);
		firstLine.setString("Undefined");
		setX2();
		_myClass.setX2AndCentering();
		_content.doParse(_whoami);
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMCLASSTEXT; 
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** ���� Ŭ���� �ؽ�Ʈ�� ������ ��ġ�� ���� Ŭ���� �ؽ�Ʈ�� Ȱ��ȭ ��Ű�� �Լ��̴�.
	 * Ŭ���� �ؽ�Ʈ�� ���� ������ Ŭ���� �̸� -> ����Ÿ ��� -> ��� �Լ� ���̴�.
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
	/** ���� Ŭ���� �ؽ�Ʈ�� ������ ��ġ�� ���� Ŭ���� �ؽ�Ʈ�� Ȱ��ȭ ��Ű�� �Լ��̴�.
	 * Ŭ���� �ؽ�Ʈ�� ���� ������ Ŭ���� �̸� <- ����Ÿ ��� <- ��� �Լ��� ���� �����̴�.
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
	/** ����Ÿ ��� _myClass�� ���� ���� access �Լ��̴�.
	 */
	public void setClassPtr(ClassTemplate clss) {
		_myClass = clss;
	}
	/** �� Ŭ���� �ؽ�Ʈ�� Ŭ���� �̸� �κ��� ��� �̸��� �����ϴ� �Լ��̴�.
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
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.memberPopup());
	}
}