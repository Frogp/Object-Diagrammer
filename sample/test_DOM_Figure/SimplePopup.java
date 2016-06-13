package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** 이 팝업 클래스 객체는 <삭제> 기능만을 제공한다.
 */
final
	class SimplePopup extends figure.Popup implements ActionListener {
	/** 생성자이다.
	 */
	public SimplePopup(GraphicController controller) {
		super("Simple Popup",controller);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(this);
		_popupPtr.add(deleteB);
	}
	/** 버튼 선택 이벤트를 처리하는 콜백 함수이다.
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
