package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** �� Ŭ������ Generalization�� ���� popup �޴��̴�.
 */
public final class GenTionPopup extends figure.Popup implements ActionListener {
	/** �������̴�.
	 */
	public GenTionPopup(GraphicController controller) {
		super("GenTion Popup",controller);

		JMenuItem nameB = new JMenuItem("Name");
		nameB.addActionListener(this);
		_popupPtr.add(nameB);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(controller);
		_popupPtr.add(deleteB);

//		JMenuItem forkB = new JMenuItem("Fork");
//		forkB.addActionListener(this);
//		_popupPtr.add(forkB);
	}
	/** ��ư ���� �̺�Ʈ�� ó���ϴ� �ݹ� �Լ��̴�.
	 */
	public void actionPerformed(ActionEvent event) {
		_controller._popupflag = false;
		_controller.setDirtyFlag();
		String command = event.getActionCommand();
		if (command.equals("Name")) {
			GenTion gention = (GenTion)_controller.currentFocus();
			gention.localMakeName();
		} else if (command.equals("Fork")) {
			AnyTion.doForkCallback(_controller,Const.GENTIONPTR);
		}
	}
}