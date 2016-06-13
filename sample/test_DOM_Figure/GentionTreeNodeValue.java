package figure;

/** �� Ŭ������ ��� Ʈ���� ������ �� ���ȴ�.
 * �� ��� Ʈ���� �� ��忡 ���� ������ ǥ���ϱ� ���� ����Ѵ�.
 */
public final 
	class GentionTreeNodeValue extends Object {
	/** ���� ���� �׸��� �ε���
	 */
	public int i;
	/** ���̿� ���� �׸��� �ε���
	 */
	public int j;
	/** Ŭ���� �̸�
	 */
	public String className;
	/** class�� ���� ������
	 */
	public ClassTemplate classPtr;
	/** �������̴�.
	 */
	public GentionTreeNodeValue(String name) {
		super();
		className = name;
		classPtr = null;
		i = 0; j = 0;
	}
	/** ��Ʈ������ ��ȯ���ִ� �Լ��̴�.
	 */
	public String toString() {
		return className;
	}
}