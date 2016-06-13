package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** 이 클래스는 Association을 위한 popup 메뉴이다.
 */
public final
	class AssTionPopup extends figure.Popup implements ActionListener {
	/** role name을 입력하기 위한 버튼이다.
	 */
	public JMenuItem roleB;
	/** 다중성을 입력하기 위한 버튼이다. 
	 */
	public JMenuItem cardinalB;
	/** qualification을 입력하기 위한 버튼이다. 
	 */
	public JMenuItem qualificationB;
	/** 생성자이다.
	 */
	public AssTionPopup(GraphicController controller) {
		super("AssTion Popup",controller);

		JMenuItem nameB = new JMenuItem("Name");
		nameB.addActionListener(this);
		_popupPtr.add(nameB);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(controller);
		_popupPtr.add(deleteB);

		roleB = new JMenuItem("Role Name");
		roleB.setActionCommand("role");
		roleB.addActionListener(this);
		_popupPtr.add(roleB);

		cardinalB = new JMenuItem("Cardinality");
		cardinalB.addActionListener(this);
		_popupPtr.add(cardinalB);

		qualificationB = new JMenuItem("Qualification");
		qualificationB.addActionListener(this);
		_popupPtr.add(qualificationB);

		JMenuItem linkSymbolB = new JMenuItem("Link Symbol");
		linkSymbolB.setActionCommand("linksymbol");
		linkSymbolB.addActionListener(this);
		_popupPtr.add(linkSymbolB);
	}
	/** 버튼 선택 이벤트를 처리하는 콜백 함수이다.
	 */
	public void actionPerformed(ActionEvent event) {
		_controller._popupflag = false;
		_controller.setDirtyFlag();
		String command = event.getActionCommand();
		if (command.equals("Name")) {
			AssTion asstion = (AssTion)_controller.currentFocus();
			asstion.localMakeName();
		} else if (command.equals("role")) {
			AssTion asstion = (AssTion)_controller.currentFocus();
			asstion.localMakeRoleName();
		} else if (command.equals("Cardinality")) {
			AssTion asstion = (AssTion)_controller.currentFocus();
			asstion.localMakeCardinalText();
		} else if (command.equals("Qualification")) {
			AssTion asstion = (AssTion)_controller.currentFocus();
			asstion.localMakeQualification();
		} else if (command.equals("linksymbol")) {
			AssTion asstion = (AssTion)_controller.currentFocus();
			asstion.localMakeLinkSymbol();
		}
	}
}