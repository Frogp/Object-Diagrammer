package figure;

import java.io.*;
import java.awt.Point;
/** �� Ŭ������ ���踦 ��Ÿ���� ��ü�鿡 ���ϴ� ��輱�� �߿��� Ŭ������
 * ���ϴ� �������� ���� ������ �����ϴ� Ŭ�����̴�. �̵� ��ü�� AITList �����̳�
 * ��ü�� ���� �ַ� �����ȴ�.
 */
public final 
	class AnyTionInfoTuple extends Object implements Serializable {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 6320028524375679645L;
	/** ��輱 ������ ������ x ��ǥ
	 */
	protected int _x;
	/** ��輱 ������ ������ y ��ǥ
	 */
	protected int _y;
	/** ��輱�� ���ϴ� Ŭ���� ��ü�� ���� ���۷���
	 */
	public ClassLike classPtr;
	/** ��輱�� �����ϴ� role name �ؽ�Ʈ�� ���۷���
	 */
	public SingleLineText role;
	/** ��輱�� �����ϴ� qualification �ؽ�Ʈ�� ���۷���
	 */
	public QualificationText qualification;
	/** ��輱�� �����ϴ� multiplicity �ؽ�Ʈ�� ���۷���
	 */
	public SingleLineText multiplicity;
	/** ��輱�� �̵��ÿ� ���Ǵ� flag
	 */
	public transient boolean mark;
	/** �� �μ� �ؽ�Ʈ ��ü�� controller ���� ���� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		if (role != null) role.setController(ptr);
		if (qualification != null) qualification.setController(ptr);
		if (multiplicity != null) multiplicity.setController(ptr);
	}
	/** �������̴�.
	 */
	public AnyTionInfoTuple(ClassLike clss,int x,int y) {
		super();
		classPtr = clss;
		role = null;
		qualification = null;
		multiplicity = null;
		mark = false;
		if (clss == null) return;
		GraphicController controller = clss.controller();
		if (controller == null) return;
		_x = x;
		_y = y;
	}
	/** �������̴�.
	 */
	public AnyTionInfoTuple(AnyTionInfoTuple from) {
		classPtr = from.classPtr;
		_x = from._x;
		_y = from._y;
		role = from.role;
		qualification = from.qualification;
		multiplicity = from.multiplicity;
		mark = false;
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		classPtr = null;
		if (role != null) {
			role.delete(); role = null;
		}
		if (qualification != null) {
			qualification.delete(); qualification = null;
		}
		if (multiplicity != null) {
			multiplicity.delete(); multiplicity = null;
		}
	}
	/** ����Ÿ ��� _x �� ���� access �Լ�
	 */
	public int x() {
		return _x;
	}
	/** ����Ÿ ��� _y �� ���� access �Լ�
	 */
	public int y() {
		return _y;
	}
	/** ����Ÿ ��� _x,_y �� ���� access �Լ�
	 */
	public void setxy(int x,int y) {
		_x = x; _y = y;
	}
	/** ��ǥ �̵� �Լ�
	 */
	public void moveCoord(int dx,int dy) {
		_x = _x+ dx; _y = _y + dy;
	}
	/** �� �������� ���ڿ� ��õ� ���ΰ� �����°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean meet(Line aline) {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		aline.coords(p1,p2);
		if (p1.x == _x && p1.y == _y) return true;
		if (p2.x == _x && p2.y == _y) return true;
		return false;
	}
	/** �� �������� ���ڿ� ��õ� ��ǥ�� �����°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean meet(int x,int y) {
		if (x == _x && y == _y) return true;
		return false;
	}
}
