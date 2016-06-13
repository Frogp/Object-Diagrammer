package figure;

import java.io.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� ClassLike ��ü����
 * �����ϴ� Ŭ�����̴�.
 */
public final
	class ClassLikeList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -1591854927167425184L;
	/** �������̴�.
	 */
	public ClassLikeList() {
		super();
	}
	/** ���� ������ push ��� �Լ��̴�.
	 */
	public void push(ClassLike ptr) {
		super._push((Object)ptr);
	}
	/** ���� ������ pop ��� �Լ��̴�.
	 */
	public ClassLike pop() {
		return (ClassLike)super._pop();
	}
	/** ���� ������ top ��� �Լ��̴�.
	 */
	public ClassLike top() {
		return (ClassLike)super._top();
	}
	/** ť ������ front ��� �Լ��̴�.
	 */
	public ClassLike front() {
		return (ClassLike)super._front();
	}
	/** ť ������ rear ��� �Լ��̴�.
	 */
	public ClassLike rear() {
		return (ClassLike)super._rear();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(ClassLike ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� ó�� �ι�°�� ���ο� ��ü�� append �ϴ� �Լ��̴�.
	 */
	public void append(ClassLike ptr) {
		super._append((Object)ptr);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(ClassLike ptr) {
		return super._inList((Object)ptr);
	}
	/** �� ����Ʈ�� ���ڿ� �־��� Ŭ������ �̸��� ���� Ŭ������
	 * �ִ� ���� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inlistForName(ClassLike ptr) {
		/*
		char *className = ptr.name();
		ClassLike * clss = getFirst();
		while (clss != null) {
		char *tmpName = clss.name();
		if (strcmp(className,tmpName) == 0) {
		class.delete()_name;
		tmp.delete()_name;
		return true;
		}
		tmp.delete()_name;
		clss = getNext();
		}
		class.delete()_name;
		*/
		return false;
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(ClassLike ptr,int tag) {
		super._remove((Object)ptr,tag);
	}
	/** �� ����Ʈ�� ���ϴ� ���۷��� �߿��� ���� ���� ���ο� ���۷��� ������ �����ϴ� �Լ��̴�.
	 */
	public void replacePtr(ClassLike from,ClassLike to) {
		super._replacePtr((Object)from,(Object)to);
	}
	/** �� ����Ʈ�� ���� �Լ��̴�.
	 */
	public void clear() {
		super._clear();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� �� ù��° ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public ClassLike getFirst() {
		return (ClassLike)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public ClassLike getNext() {
		return (ClassLike)super._getNext();
	}
}
