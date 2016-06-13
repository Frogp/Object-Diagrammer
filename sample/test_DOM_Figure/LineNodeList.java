package figure;

import java.lang.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� ���� ��� ��ü����
 * �����ϴ� Ŭ�����̴�.
 */
public final
	class LineNodeList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -4980665917223104262L;
	/** �������̴�.
	 */
	public LineNodeList() {
		super();
	}
	/** ���� ������ push ��� �Լ��̴�.
	 */
	public void push(LineNode ptr) {
		super._push((Object)ptr);
	}
	/** ���� ������ pop ��� �Լ��̴�.
	 */
	public LineNode pop() {
		return (LineNode)super._pop();
	}
	/** ���� ������ top ��� �Լ��̴�.
	 */
	public LineNode top() {
		return (LineNode)super._top();
	}
	/** ť ������ enter ��� �Լ��̴�.
	 */
	public void enter(LineNode ptr) {
		super._enter((Object)ptr);
	}
	/** ť ������ leave ��� �Լ��̴�.
	 */
	public LineNode leave() {
		return (LineNode)super._leave();
	}
	/** ť ������ front ��� �Լ��̴�.
	 */
	public LineNode front() {
		return (LineNode)super._front();
	}
	/** ť ������ rear ��� �Լ��̴�.
	 */
	public LineNode rear() {
		return (LineNode)super._rear();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(LineNode ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� ó�� �ι�°�� ���ο� ��ü�� append �ϴ� �Լ��̴�.
	 */
	public void append(LineNode ptr) {
		super._append((Object)ptr);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(LineNode ptr) {
		return super._inList((Object)ptr);
	}
	/** �� ����Ʈ�� ���ϴ� ���۷��� �߿��� ���� ���� ���ο� ���۷��� ������ �����ϴ� �Լ��̴�.
	 */
	public boolean replacePtr(LineNode from,LineNode to) {
		return super._replacePtr((Object)from,(Object)to);
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(LineNode ptr,boolean vanish) {
		if (vanish == false) {
			super._remove((Object)ptr,0);
			return;
		}
		LineNode tmp = getFirst();
		while(tmp != null) {
			if (tmp != ptr) {
				tmp.removeAfter(ptr);
				tmp.removeBefore(ptr);
			}
			tmp = getNext();
		}
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
	public LineNode getFirst() {
		return (LineNode)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public LineNode getLast() {
		return (LineNode)super._getLast();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public LineNode getNext() {
		return (LineNode)super._getNext();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	public LineNode getPrevious() {
		return (LineNode)super._getPrevious();
	}
}
