package figure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** �� Ŭ������ ��� �˾� �޴��� ���� ������ ó���ϴ� �߻� Ŭ�����̴�.
 */
public abstract
	class Popup extends Object {
	/** ȭ�� ������Ʈ�� ���� ���۷���
	 */
	protected GraphicController _controller;
	/** �޴��� �̸��� ��Ÿ���� ���̺�
	 */
	protected JMenuItem _label;
	/** �˾� �޴��� ���۷���
	 */
	protected JPopupMenu _popupPtr;
	/** �������̴�.
	 */
	public Popup(String name,GraphicController controller) {
		super();
		_controller = controller;
		_popupPtr = new JPopupMenu(name);
		_controller.add(_popupPtr);
		_popupPtr.add(name);
		_popupPtr.addSeparator();
	}
	/** �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		_popupPtr.show(event.getComponent(),event.getX(),event.getY());
	}
	/** ���̺� �̸��� �����ϴ� �Լ��̴�.
	 */
	public void setLabelString(String label) {
	}
	/** ��ư�� ��� ���� ���θ� �����ϴ� �Լ��̴�.
	 */
	public void setButtonSensitive(String name,boolean flag) {}
	/** ��ư�� �̸��� �����ϴ� �Լ��̴�.
	 */
	public void renameButton(String from,String to) {}
}
