package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** 이 클래스는 Dependency를 위한 popup 메뉴이다.
 */
public final
	class ColTionPopup extends figure.Popup implements ActionListener {
	/** role name을 입력하기 위한 버튼이다.
	 */
	public JMenuItem roleB;
	/** 생성자이다.
	 */
	public ColTionPopup(GraphicController controller) {
		super("ColTion Popup",controller);

		JMenuItem nameB = new JMenuItem("Name");
		nameB.addActionListener(this);
		_popupPtr.add(nameB);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(controller);
		_popupPtr.add(deleteB);

//		JMenuItem forkB = new JMenuItem("Fork");
//		forkB.addActionListener(this);
//		_popupPtr.add(forkB);

		roleB = new JMenuItem("Role Name");
		roleB.setActionCommand("role");
		roleB.addActionListener(this);
		_popupPtr.add(roleB);
	}
	/** 버튼 선택 이벤트를 처리하는 콜백 함수이다.
	 */
	public void actionPerformed(ActionEvent event) {
		_controller._popupflag = false;
		_controller.setDirtyFlag();
		String command = event.getActionCommand();
		if (command.equals("Name")) {
			ColTion coltion = (ColTion)_controller.currentFocus();
			coltion.localMakeName();
		} else if (command.equals("Fork")) {
			AnyTion.doForkCallback(_controller,Const.COLTIONPTR);
		} else if (command.equals("role")) {
			ColTion coltion = (ColTion)_controller.currentFocus();
			coltion.localMakeRoleName();
		}
	}
}