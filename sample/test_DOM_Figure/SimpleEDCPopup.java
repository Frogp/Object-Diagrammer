package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** �� �˾� Ŭ���� ��ü�� <����>, <����>�� <����> ����� �����Ѵ�.
 */
public final
	class SimpleEDCPopup extends figure.Popup implements ActionListener {
	/** �������̴�.
	 */
	public SimpleEDCPopup(GraphicController controller) {
		super("Simple Popup",controller);

		JMenuItem editB = new JMenuItem("Edit");
		editB.addActionListener(this);
		_popupPtr.add(editB);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(controller);
		_popupPtr.add(deleteB);

		JMenuItem copyB = new JMenuItem("Copy");
		copyB.addActionListener(controller);
		_popupPtr.add(copyB);
	}
	/** ��ư ���� �̺�Ʈ�� ó���ϴ� �ݹ� �Լ��̴�.
	 */
	public void actionPerformed(ActionEvent event) {
		_controller._popupflag = false;
		_controller.setDirtyFlag();
		String command = event.getActionCommand();
		if (command.equals("Edit")) {
			CPoint point = new CPoint();
			point.x = _controller.popupX();
			point.y = _controller.popupY();
			_controller.startEditor(point);
		}
	}
}