package figure;

import java.lang.*;
import java.io.*;
import java.util.Vector;
import java.util.ListIterator;
/** 이 클래스는 컨테이너 역할을 하는 범용 리스트 클래스이다.
 */
public 
	class List extends Vector {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 8780852480737875046L;
	/** 리스트를 traverse 하기 우해 사용되는 반복자
	 */
	private transient ListIterator iterator = null;
	/** 생성자이다.
	 */
	protected List() {
		super(10,10);
	}
	/** 스택 연산의 push 기능 함수이다.
	 */
	protected void _push(Object ptr) {
		super.add(0,ptr);
	}
	/** 스택 연산의 pop 기능 함수이다.
	 */
	protected Object _pop() {
		if (empty()) return null;
		Object element = super.elementAt(0);
		super.removeElementAt(0);
		return element;
	}
	/** 스택 연산의 top 기능 함수이다.
	 */
	protected Object _top() {
		if (empty()) return null;
		else return super.elementAt(0);
	}
	/** 큐 연산의 enter 기능 함수이다.
	 */
	protected void _enter(Object ptr) {
		_insert(ptr,0); 
	}
	/** 큐 연산의 leave 기능 함수이다.
	 */
	protected Object _leave() {
		return _pop(); 
	}
	/** 큐 연산의 front 기능 함수이다.
	 */
	protected Object _front() {
		return _top(); 
	}
	/** 큐 연산의 rear 기능 함수이다.
	 */
	protected Object _rear() {
		if (empty()) return null;
		else return super.lastElement();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	protected void _insert(Object ptr,int tag) {
		if (tag != Const.ABSOLUTELY && _inList(ptr)) return;
		super.add(ptr);
	}
	/** 인자에 명시된 위치에 새로운 객체를 insert 하는 함수이다.
	 */
	protected void _insertAt(Object ptr,int pos) {
		super.insertElementAt(ptr,pos);
	}
	/** 인자에 명시된 위치에서 객체를 제거 하는 함수이다.
	 */
	protected Object _removeAt(int pos) {
		if (pos < 0) return null;
		if (pos >= elementCount) return null;
		Object element = super.elementAt(pos);
		super.removeElementAt(pos);
		return element;
	}
	/** 이 리스트의 처음 두번째에 새로운 객체를 append 하는 함수이다.
	 */
	protected void _append(Object ptr) {
		if (_inList(ptr)) return;
		super.add(ptr);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	protected boolean _inList(Object ptr) {
		return super.contains(ptr);
	}
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	protected void _remove(Object ptr,int tag) {
		if (tag == 0) super.removeElement(ptr);
		else {
			while (super.contains(ptr)) super.removeElement(ptr);
		}
	}
	/** 이 리스트를 비우는 함수이다.
	 */
	protected void _clear() {
		super.clear();
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
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
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
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
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	protected Object _getNext() {
		if (iterator == null) return null;
		if (iterator.hasNext()) return iterator.next();
		else {
			iterator = null;
			return null;
		}
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	protected Object _getPrevious() {
		if (iterator == null) return null;
		if (iterator.hasPrevious()) return iterator.previous();
		else {
			iterator = null;
			return null;
		}
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		_clear();
	}
	/** 리스트에 포함된 객체들의 수를 구하는 함수이다.
	 */
	public int nOfList() {
		return elementCount; 
	}
	/** 리스트가 비었는가를 확인하는 함수이다.
	 */
	public boolean empty() {
		return isEmpty();
	}
	/** 이 리스트에 속하는 레퍼런스 중에서 이전 값을 새로운 레퍼런스 값으로 변경하는 함수이다.
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
	/** 인자에 명시된 리스트의 내용을 이 리스트에 복사하는 함수이다.
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
