package figure;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;

/** �� Ŭ������ Aggregation�� ���� popup �޴��̴�.
 */
public final
	class AggTionPopup extends figure.Popup implements ActionListener {
	/** composition �ΰ� aggregation �ΰ��� �����ϴ� ��ư�̴�.
	*/
	public JMenuItem strengthB;
	/** role name�� �Է��ϱ� ���� ��ư�̴�.
	 */
	public JMenuItem roleB;
	/** ���߼��� �Է��ϱ� ���� ��ư�̴�. 
	 */
	public JMenuItem cardinalB;
	/** �������̴�.
	 */
	public AggTionPopup(GraphicController controller) {
		super("AggTion Popup",controller);

		JMenuItem nameB = new JMenuItem("Name");
		nameB.addActionListener(this);
		_popupPtr.add(nameB);

		JMenuItem deleteB = new JMenuItem("Delete");
		deleteB.addActionListener(controller);
		_popupPtr.add(deleteB);

//		JMenuItem forkB = new JMenuItem("Fork");
//		forkB.addActionListener(this);
//		_popupPtr.add(forkB);

		strengthB = new JMenuItem("Strong");
		strengthB.setActionCommand("strength");
		strengthB.addActionListener(this);
		_popupPtr.add(strengthB);

		roleB = new JMenuItem("Role Name");
		roleB.setActionCommand("role");
		roleB.addActionListener(this);
		_popupPtr.add(roleB);

		cardinalB = new JMenuItem("Cardinality");
		cardinalB.addActionListener(this);
		_popupPtr.add(cardinalB);
	}
	/** ��ư ���� �̺�Ʈ�� ó���ϴ� �ݹ� �Լ��̴�.
	 */
	public void actionPerformed(ActionEvent event) {
		_controller._popupflag = false;
		_controller.setDirtyFlag();
		String command = event.getActionCommand();
		if (command.equals("Name")) {
			AggTion aggtion = (AggTion)_controller.currentFocus();
			aggtion.localMakeName();
		} else if (command.equals("Fork")) {
			AnyTion.doForkCallback(_controller,Const.AGGTIONPTR);
		} else if (command.equals("strength")) {
			AggTion aggtion = (AggTion)_controller.currentFocus();
			aggtion.changeAggregationStrength();
			_controller.resetCurrentFocus();
		} else if (command.equals("role")) {
			AggTion aggtion = (AggTion)_controller.currentFocus();
			aggtion.localMakeRoleName();
		} else if (command.equals("Cardinality")) {
			AggTion aggtion = (AggTion)_controller.currentFocus();
			aggtion.localMakeCardinalText();
		}
	}
}