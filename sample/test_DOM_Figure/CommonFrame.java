package figure;

import javax.swing.*;

/** �� Ŭ������ �𵨷��� �����Ⱑ ����ϴ� �������� ���� �������̽��� �����ϴ� Ŭ�����̴�.
 */
public class CommonFrame extends JFrame {
	/** ȭ�� ������Ʈ ��ü ���۷���
	 */ 
	protected GraphicController _controller = null;
	/** �� �������� ��Ű�� �̸�
	 */
	public String packageName;
	/** ����Ÿ ��� packageName�� ���� �б� access �Լ��̴�.
	 */
	public String getPackageName() {
		return new String(packageName);
	}
	/** ����Ÿ ��� _controller�� ���� �б� access �Լ��̴�.
	 */
	public GraphicController controller() {
		return _controller;
	}

	/** �������̴�.
	 */	
	public CommonFrame(String name) {
		super(name);
	}
	
	/** �� ������ �̸��� �����ִ� �Լ��̴�.
	 */
	public String getAuthorName() {
		return new String("");
	}
	/** �� ������ �̸��� �����ϴ� �Լ��̴�.
	 */
	public void setAuthorName(String author) {
	}
}