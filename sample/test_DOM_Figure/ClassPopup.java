package figure;


import javax.swing.*;
/** �� Ŭ������ Ŭ���� ��ü�� ���� popup �޴��̴�. �� Ŭ������ �𵨷��� �����⿡��
 * ���Ǵ� Ŭ���� �˾��� ���� ����Ÿ ����� �����Ѵ�.
 */
public class ClassPopup extends figure.Popup {
	/** Ŭ������ ũ�⸦ ������ �� ���Ǵ� ��ư�̴�. �� ��ư�� ���̺���
	 * Ŭ���� ��ü�� ���¿� ���� "minimize"�� "maximize" ���� ���´�.
	 */
//	public JMenuItem sizeB;
	/** �� Ŭ���� ��ü�� template class�� ��ȯ ��ų ��, Ȥ�� �� �ݴ� ��������
	 * ��ȯ ��ų �� ���Ǵ� ��ư�̴�.
	 */
	public JMenuItem templateB;
	/** �������̴�.
	 */
	public ClassPopup(GraphicController controller) {
		super("Class Popup",controller);
	}
}