package figure;

import java.io.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� ��Ʈ������
 * �����ϴ� Ŭ�����̴�.
 */
public final
	class StringList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 7152811463032768254L;
	/** �������̴�.
	 */
	public StringList() {
		super();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(String ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 * ����Ʈ�� �̹� ��ü�� ������ insert ���� �ʴ´�.
	 */
	public void insert(String ptr) {
		if (inList(ptr)) return;
		insert(ptr,0);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(Object ptr) {
		return super._inList(ptr);
	}
	/** �� ����Ʈ�� ��Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(String name) {
		String aName = getFirst();
		while (aName != null) {
			if (aName.compareTo(name) == 0) {
				return true;
			}
			aName = getNext();
		}
		return false;
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(String ptr) {
		// ������ ���� ��� ����
		String aName = getFirst();
		while (aName != null) {
			if (aName.compareTo(ptr) == 0) {
				super._remove((Object)aName,0);
				return;
			}
			aName = getNext();
		}
	}
	/** �� ����Ʈ�� ���� �Լ��̴�.
	 */
	public void clear() {
		super._clear();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� �� ù��° ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public String getFirst() {
		return (String)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public String getLast() {
		return (String)super._getLast();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public String getNext() {
		return (String)super._getNext();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	public String getPrevious() {
		return (String)super._getPrevious();
	}
}
