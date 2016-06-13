package figure;

import java.io.*;
import java.awt.*;
/** �� Ŭ������ �ؽ�Ʈ Ŭ������ Ưȭ�� ���μ� ��Ű���� �μ�ǰ�� ��Ű�� �ؽ�Ʈ�� ǥ���ϱ� ���� ���̴�.
 */
public 
	class PackageText extends Text {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 5010535304552359L;
	/** �� ��ü�� �����ϰ� �ִ� ��Ű���� ���� ���۷��� 
	 */
	private PackageTemplate _myPackage;
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
		_myPackage.modifyHeight(-delCount);
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		super.delete();
		_myPackage = null;
	}
	/** �������̴�.
	 */
	public PackageText(GraphicController controller,PackageTemplate myPackage,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		if (controller == null) return;
		_myPackage = myPackage;
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
		int pos = _controller.activeTextField.getCaretPosition();
		if (pos == 0) return;
		super.gonextline();
		_myPackage.modifyHeight(1);
	}
	/** �ؽ�Ʈ�� �� ���� ����� �Լ��̴�. �ؽ�Ʈ �� ���� ���ֱ� ���� Ű���� �Է��� [Ctrl]+[BackSpace] �̴�.
	 */
	public boolean delLine() {
		String currentLine = _controller.activeTextField.getText();
		String actualLine = currentLine.trim();
		if (height() == 1 && actualLine.length() == 0) return true;
		if (super.delLine()) return true;
		_myPackage.modifyHeight(-1);
		return false;
	}
	/** ���� �ؽ�Ʈ�� ���� �۾��� �������Ǿ��� �� ���� �۾��� �ϴ� �Լ��̴�.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		_myPackage.farewell();
	}
	/** �ؽ�Ʈ�� ���� ���� �ÿ� ������ �۾��� �ϴ� �Լ��̴�.
	 */
	public void bye() {
		super.bye();
		_content.doParse(ClassText.NAMEFIELD);
		delEmptyLines();
	}
	/** �� ��Ű�� �ؽ�Ʈ�� ��Ű�� �̸� �κ��� ��� �ؽ�Ʈ�� ������ ��� ���� ��
	 *  �̸� �־��� �̸� �������� ä��� �Լ��̴�.
	 */
	public void setDummyName() {
		TextLine firstLine = _content.lineAt(0);
		setX2();
		firstLine.setString("Unknown");
		_content.doParse(ClassText.NAMEFIELD);
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMPACKAGETEXT; 
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** ����Ÿ ��� _myPackage�� ���� ���� access �Լ��̴�.
	 */
	public void setPackagePtr(PackageTemplate pk) {
		_myPackage = pk;
	}
	/** �� ��Ű�� �ؽ�Ʈ�� ��Ű�� �̸� �κ��� ��� �̸��� �����ϴ� �Լ��̴�.
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