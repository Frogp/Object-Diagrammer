package figure;

import java.io.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� ���� ��Ʈ������
 * �����ϴ� Ŭ�����̴�.
 */
public final
	class StrRelation extends List {
	/** �������̴�.
	 */
	public StrRelation() {
		super();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 * ����Ʈ�� �̹� ��ü�� ������ insert ���� �ʴ´�.
	 */
	public void insert(StrRelItem ptr) {
		if (inList(ptr)) return;
		_insert((Object)ptr,0);
	}
	/** �� ����Ʈ�� ��Ʈ�� ���� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(StrRelItem item) {
		StrRelItem anItem = getFirst();
		while (anItem != null) {
			if (anItem.equals(item)) {
				return true;
			}
			anItem = getNext();
		}
		return false;
	}
	/** �� ����Ʈ�� ��Ʈ�� ���� ��ü�� ��ġ�� ã�� �Լ��̴�.
	 */
	public StrRelItem find(StrRelItem item) {
		StrRelItem anItem = getFirst();
		while (anItem != null) {
			if (anItem.equals(item)) {
				return anItem;
			}
			anItem = getNext();
		}
		return null;
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(StrRelItem ptr) {
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
	public StrRelItem getFirst() {
		return (StrRelItem)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public StrRelItem getLast() {
		return (StrRelItem)super._getLast();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public StrRelItem getNext() {
		return (StrRelItem)super._getNext();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	public StrRelItem getPrevious() {
		return (StrRelItem)super._getPrevious();
	}
}
