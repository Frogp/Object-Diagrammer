package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** �� �˾� Ŭ���� ��ü�� <����>�� <����> ����� �����Ѵ�.
 */
public final
	class SimpleDCPopup extends figure.Popup implements ActionListener {
	/** �������̴�.
	 */
	public SimpleDCPopup(GraphicController controller) {
		super("Line Popup",controller);

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
	}
}