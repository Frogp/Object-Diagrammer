package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
/** �� Ŭ������ Association�� ���� popup �޴��̴�.
 */
public final
	class AssTionPopup extends figure.Popup implements ActionListener {
	/** role name�� �Է��ϱ� ���� ��ư�̴�.
	 */
	public JMenuItem roleB;
	/** ���߼��� �Է��ϱ� ���� ��ư�̴�. 
	 */
	public JMenuItem cardinalB;
	/** qualification�� �Է��ϱ� ���� ��ư�̴�. 
	 */
	public JMenuItem qualificationB;
	/** �������̴�.
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
	/** ��ư ���� �̺�Ʈ�� ó���ϴ� �ݹ� �Լ��̴�.
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