package figure;

import java.io.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� �׸� ��ü����
 * �����ϴ� Ŭ�����̴�.
 */
public final
	class FigureList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -5831216093743509171L;
	/** �������̴�.
	 */
	public FigureList() {
		super();
	}
	/** ���� ������ push ��� �Լ��̴�.
	 */
	public void push(Figure ptr) {
		super._push((Object)ptr);
	}
	/** ���� ������ pop ��� �Լ��̴�.
	 */
	public Figure pop() {
		return (Figure)super._pop();
	}
	/** ���� ������ top ��� �Լ��̴�.
	 */
	public Figure top() {
		return (Figure)super._top();
	}
	/** ť ������ enter ��� �Լ��̴�.
	 */
	public void enter(Figure ptr) {
		super._enter((Object)ptr);
	}
	/** ť ������ leave ��� �Լ��̴�.
	 */
	public Figure leave() {
		return (Figure)super._leave();
	}
	/** ť ������ front ��� �Լ��̴�.
	 */
	public Figure front() {
		return (Figure)super._front();
	}
	/** ť ������ rear ��� �Լ��̴�.
	 */
	public Figure rear() {
		return (Figure)super._rear();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(Figure ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 * ����Ʈ�� �̹� ��ü�� ������ insert ���� �ʴ´�.
	 */
	public void insert(Figure ptr) {
		insert(ptr,0);
	}
	/** �� ����Ʈ�� ó�� �ι�°�� ���ο� ��ü�� append �ϴ� �Լ��̴�.
	 */
	public void append(Figure ptr) {
		super._append((Object)ptr);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(Figure ptr) {
		return super._inList((Object)ptr);
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(Figure ptr) {
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
	public Figure getFirst() {
		return (Figure)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public Figure getLast() {
		return (Figure)super._getLast();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public Figure getNext() {
		return (Figure)super._getNext();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	public Figure getPrevious() {
		return (Figure)super._getPrevious();
	}
}
