package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** �� �˾� Ŭ���� ��ü�� <����> ��ɸ��� �����Ѵ�.
 */
final
	class SimplePopup extends figure.Popup implements ActionListener {
	/** �������̴�.
	 */
	public SimplePopup(GraphicController controller) {
		super("Simple Popup",controller);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(this);
		_popupPtr.add(deleteB);
	}
	/** ��ư ���� �̺�Ʈ�� ó���ϴ� �ݹ� �Լ��̴�.
	 */
	public void actionPerformed(ActionEvent event) {
		_controller._popupflag = false;
		_controller.setDirtyFlag();
		String command = event.getActionCommand();
		if (command.equals("Delete")) {
			_controller.deleteCurrentFocus();
		}
	}
}
