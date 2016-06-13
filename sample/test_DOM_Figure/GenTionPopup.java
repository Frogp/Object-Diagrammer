package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** 이 클래스는 Generalization을 위한 popup 메뉴이다.
 */
public final class GenTionPopup extends figure.Popup implements ActionListener {
	/** 생성자이다.
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
	/** 버튼 선택 이벤트를 처리하는 콜백 함수이다.
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