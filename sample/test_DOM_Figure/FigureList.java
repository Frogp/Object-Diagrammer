package figure;

import java.io.*;
/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 그림 객체들을
 * 관리하는 클래스이다.
 */
public final
	class FigureList extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -5831216093743509171L;
	/** 생성자이다.
	 */
	public FigureList() {
		super();
	}
	/** 스택 연산의 push 기능 함수이다.
	 */
	public void push(Figure ptr) {
		super._push((Object)ptr);
	}
	/** 스택 연산의 pop 기능 함수이다.
	 */
	public Figure pop() {
		return (Figure)super._pop();
	}
	/** 스택 연산의 top 기능 함수이다.
	 */
	public Figure top() {
		return (Figure)super._top();
	}
	/** 큐 연산의 enter 기능 함수이다.
	 */
	public void enter(Figure ptr) {
		super._enter((Object)ptr);
	}
	/** 큐 연산의 leave 기능 함수이다.
	 */
	public Figure leave() {
		return (Figure)super._leave();
	}
	/** 큐 연산의 front 기능 함수이다.
	 */
	public Figure front() {
		return (Figure)super._front();
	}
	/** 큐 연산의 rear 기능 함수이다.
	 */
	public Figure rear() {
		return (Figure)super._rear();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insert(Figure ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 * 리스트에 이미 객체가 있으면 insert 하지 않는다.
	 */
	public void insert(Figure ptr) {
		insert(ptr,0);
	}
	/** 이 리스트의 처음 두번째에 새로운 객체를 append 하는 함수이다.
	 */
	public void append(Figure ptr) {
		super._append((Object)ptr);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	public boolean inList(Figure ptr) {
		return super._inList((Object)ptr);
	}
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	public void remove(Figure ptr) {
		super._remove((Object)ptr,0);
	}
	/** 이 리스트를 비우는 함수이다.
	 */
	public void clear() {
		super._clear();
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public Figure getFirst() {
		return (Figure)super._getFirst();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public Figure getLast() {
		return (Figure)super._getLast();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public Figure getNext() {
		return (Figure)super._getNext();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	public Figure getPrevious() {
		return (Figure)super._getPrevious();
	}
}
