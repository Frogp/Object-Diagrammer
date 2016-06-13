package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** 이 팝업 클래스 객체는 <편집>, <삭제>와 <복사> 기능을 제공한다.
 */
public final
	class SimpleEDCPopup extends figure.Popup implements ActionListener {
	/** 생성자이다.
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
	/** 버튼 선택 이벤트를 처리하는 콜백 함수이다.
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