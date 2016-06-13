package figure;

import java.lang.*;
import java.io.*;
import java.util.Vector;
import java.util.ListIterator;
/** �� Ŭ������ �����̳� ������ �ϴ� ���� ����Ʈ Ŭ�����̴�.
 */
public 
	class List extends Vector {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 8780852480737875046L;
	/** ����Ʈ�� traverse �ϱ� ���� ���Ǵ� �ݺ���
	 */
	private transient ListIterator iterator = null;
	/** �������̴�.
	 */
	protected List() {
		super(10,10);
	}
	/** ���� ������ push ��� �Լ��̴�.
	 */
	protected void _push(Object ptr) {
		super.add(0,ptr);
	}
	/** ���� ������ pop ��� �Լ��̴�.
	 */
	protected Object _pop() {
		if (empty()) return null;
		Object element = super.elementAt(0);
		super.removeElementAt(0);
		return element;
	}
	/** ���� ������ top ��� �Լ��̴�.
	 */
	protected Object _top() {
		if (empty()) return null;
		else return super.elementAt(0);
	}
	/** ť ������ enter ��� �Լ��̴�.
	 */
	protected void _enter(Object ptr) {
		_insert(ptr,0); 
	}
	/** ť ������ leave ��� �Լ��̴�.
	 */
	protected Object _leave() {
		return _pop(); 
	}
	/** ť ������ front ��� �Լ��̴�.
	 */
	protected Object _front() {
		return _top(); 
	}
	/** ť ������ rear ��� �Լ��̴�.
	 */
	protected Object _rear() {
		if (empty()) return null;
		else return super.lastElement();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	protected void _insert(Object ptr,int tag) {
		if (tag != Const.ABSOLUTELY && _inList(ptr)) return;
		super.add(ptr);
	}
	/** ���ڿ� ��õ� ��ġ�� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	protected void _insertAt(Object ptr,int pos) {
		super.insertElementAt(ptr,pos);
	}
	/** ���ڿ� ��õ� ��ġ���� ��ü�� ���� �ϴ� �Լ��̴�.
	 */
	protected Object _removeAt(int pos) {
		if (pos < 0) return null;
		if (pos >= elementCount) return null;
		Object element = super.elementAt(pos);
		super.removeElementAt(pos);
		return element;
	}
	/** �� ����Ʈ�� ó�� �ι�°�� ���ο� ��ü�� append �ϴ� �Լ��̴�.
	 */
	protected void _append(Object ptr) {
		if (_inList(ptr)) return;
		super.add(ptr);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	protected boolean _inList(Object ptr) {
		return super.contains(ptr);
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	protected void _remove(Object ptr,int tag) {
		if (tag == 0) super.removeElement(ptr);
		else {
			while (super.contains(ptr)) super.removeElement(ptr);
		}
	}
	/** �� ����Ʈ�� ���� �Լ��̴�.
	 */
	protected void _clear() {
		super.clear();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� �� ù��° ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	protected Object _getFirst() {
		iterator = null;
		if (elementCount == 0) return null;
		iterator = super.listIterator();
		if (iterator.hasNext()) return iterator.next();
		else {
			iterator = null;
			return null;
		}
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	protected Object _getLast() {
		iterator = null;
		if (elementCount == 0) return null;
		iterator = super.listIterator(elementCount);
		if (iterator.hasPrevious()) return iterator.previous();
		else {
			iterator = null;
			return null;
		}
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	protected Object _getNext() {
		if (iterator == null) return null;
		if (iterator.hasNext()) return iterator.next();
		else {
			iterator = null;
			return null;
		}
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	protected Object _getPrevious() {
		if (iterator == null) return null;
		if (iterator.hasPrevious()) return iterator.previous();
		else {
			iterator = null;
			return null;
		}
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		_clear();
	}
	/** ����Ʈ�� ���Ե� ��ü���� ���� ���ϴ� �Լ��̴�.
	 */
	public int nOfList() {
		return elementCount; 
	}
	/** ����Ʈ�� ����°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean empty() {
		return isEmpty();
	}
	/** �� ����Ʈ�� ���ϴ� ���۷��� �߿��� ���� ���� ���ο� ���۷��� ������ �����ϴ� �Լ��̴�.
	 */
	protected boolean _replacePtr(Object from,Object to) {
		if (from == null || to == null) {
			return false;
		}
		boolean replaced = false;
		while (super.contains(from)) {
			replaced = true;
			int index = super.indexOf(from);
			super.setElementAt(to,index);
		}
		return replaced;
	}
	/** ���ڿ� ��õ� ����Ʈ�� ������ �� ����Ʈ�� �����ϴ� �Լ��̴�.
	 */
	public void copy(List from) {
		_clear();
		ListIterator i = from.listIterator();
		while (i.hasNext()) {
			Object o = i.next();
			super.add(o);
		}
	}
}
