package figure;

import java.lang.*;
/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 선분 노드 객체들을
 * 관리하는 클래스이다.
 */
public final
	class LineNodeList extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -4980665917223104262L;
	/** 생성자이다.
	 */
	public LineNodeList() {
		super();
	}
	/** 스택 연산의 push 기능 함수이다.
	 */
	public void push(LineNode ptr) {
		super._push((Object)ptr);
	}
	/** 스택 연산의 pop 기능 함수이다.
	 */
	public LineNode pop() {
		return (LineNode)super._pop();
	}
	/** 스택 연산의 top 기능 함수이다.
	 */
	public LineNode top() {
		return (LineNode)super._top();
	}
	/** 큐 연산의 enter 기능 함수이다.
	 */
	public void enter(LineNode ptr) {
		super._enter((Object)ptr);
	}
	/** 큐 연산의 leave 기능 함수이다.
	 */
	public LineNode leave() {
		return (LineNode)super._leave();
	}
	/** 큐 연산의 front 기능 함수이다.
	 */
	public LineNode front() {
		return (LineNode)super._front();
	}
	/** 큐 연산의 rear 기능 함수이다.
	 */
	public LineNode rear() {
		return (LineNode)super._rear();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insert(LineNode ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** 이 리스트의 처음 두번째에 새로운 객체를 append 하는 함수이다.
	 */
	public void append(LineNode ptr) {
		super._append((Object)ptr);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	public boolean inList(LineNode ptr) {
		return super._inList((Object)ptr);
	}
	/** 이 리스트에 속하는 레퍼런스 중에서 이전 값을 새로운 레퍼런스 값으로 변경하는 함수이다.
	 */
	public boolean replacePtr(LineNode from,LineNode to) {
		return super._replacePtr((Object)from,(Object)to);
	}
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
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
	/** 이 리스트를 비우는 함수이다.
	 */
	public void clear() {
		super._clear();
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public LineNode getFirst() {
		return (LineNode)super._getFirst();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public LineNode getLast() {
		return (LineNode)super._getLast();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public LineNode getNext() {
		return (LineNode)super._getNext();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	public LineNode getPrevious() {
		return (LineNode)super._getPrevious();
	}
}
