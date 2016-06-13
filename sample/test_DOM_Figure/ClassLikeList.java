package figure;

import java.io.*;
/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 ClassLike 객체들을
 * 관리하는 클래스이다.
 */
public final
	class ClassLikeList extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -1591854927167425184L;
	/** 생성자이다.
	 */
	public ClassLikeList() {
		super();
	}
	/** 스택 연산의 push 기능 함수이다.
	 */
	public void push(ClassLike ptr) {
		super._push((Object)ptr);
	}
	/** 스택 연산의 pop 기능 함수이다.
	 */
	public ClassLike pop() {
		return (ClassLike)super._pop();
	}
	/** 스택 연산의 top 기능 함수이다.
	 */
	public ClassLike top() {
		return (ClassLike)super._top();
	}
	/** 큐 연산의 front 기능 함수이다.
	 */
	public ClassLike front() {
		return (ClassLike)super._front();
	}
	/** 큐 연산의 rear 기능 함수이다.
	 */
	public ClassLike rear() {
		return (ClassLike)super._rear();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insert(ClassLike ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** 이 리스트의 처음 두번째에 새로운 객체를 append 하는 함수이다.
	 */
	public void append(ClassLike ptr) {
		super._append((Object)ptr);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	public boolean inList(ClassLike ptr) {
		return super._inList((Object)ptr);
	}
	/** 이 리스트에 인자에 주어진 클래스의 이름과 같은 클래스가
	 * 있는 지를 확인하는 함수이다.
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
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	public void remove(ClassLike ptr,int tag) {
		super._remove((Object)ptr,tag);
	}
	/** 이 리스트에 속하는 레퍼런스 중에서 이전 값을 새로운 레퍼런스 값으로 변경하는 함수이다.
	 */
	public void replacePtr(ClassLike from,ClassLike to) {
		super._replacePtr((Object)from,(Object)to);
	}
	/** 이 리스트를 비우는 함수이다.
	 */
	public void clear() {
		super._clear();
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public ClassLike getFirst() {
		return (ClassLike)super._getFirst();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public ClassLike getNext() {
		return (ClassLike)super._getNext();
	}
}
