package figure;

/** �� Ŭ������ parameter passing �ÿ� call by reference ����� �̿��ϱ� ����
 * ���̴�. Ư�� �Ҹ� ���� ���� �˾ƿ��� ���� actual parameter�� ���ȴ�.
 */
public final 
	class BoolVar extends Object {
	/** side effect�� �̿��Ͽ� �˾ƿ����� �ϴ� �Ҹ� ��
	 */
	public boolean v;
	/** �������̴�.
	 */
	public BoolVar(boolean flag) {
		super();
		v = flag;
	}
}