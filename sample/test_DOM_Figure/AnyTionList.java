package figure;


import java.io.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� ���� ��ü����
 * �����ϴ� Ŭ�����̴�.
 */
public final 
	class AnyTionList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -4118332197889509309L;
	/** �������̴�.
	 */
	public AnyTionList() {
		super();
	}
	/** ���� ������ push ��� �Լ��̴�.
	 */
	public void push(AnyTion ptr) {
		super._push((Object)ptr);
	}
	/** ���� ������ pop ��� �Լ��̴�.
	 */
	public AnyTion pop() {
		return (AnyTion)super._pop();
	}
	/** ���� ������ top ��� �Լ��̴�.
	 */
	public AnyTion top() {
		return (AnyTion)super._top();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(AnyTion ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� ó�� �ι�°�� ���ο� ��ü�� append �ϴ� �Լ��̴�.
	 */
	public void append(AnyTion ptr) {
		super._append((Object)ptr);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(AnyTion ptr) {
		return super._inList((Object)ptr);
	}
	/** �� ����Ʈ�� ���ϴ� ���۷��� �߿��� ���� ���� ���ο� ���۷��� ������ �����ϴ� �Լ��̴�.
	 */
	public boolean replacePtr(AnyTion from,AnyTion to) {
		return super._replacePtr((Object)from,(Object)to);
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(AnyTion ptr) {
		super._remove((Object)ptr,0);
	}
	/** �� ����Ʈ�� ���� �Լ��̴�.
	 */
	public void clear() {
		super._clear();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� �� ù��° ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public AnyTion getFirst() {
		return (AnyTion)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public AnyTion getNext() {
		return (AnyTion)super._getNext();
	}
}
