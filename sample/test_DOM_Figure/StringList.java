package figure;

import java.io.*;
/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 스트링들을
 * 관리하는 클래스이다.
 */
public final
	class StringList extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 7152811463032768254L;
	/** 생성자이다.
	 */
	public StringList() {
		super();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insert(String ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 * 리스트에 이미 객체가 있으면 insert 하지 않는다.
	 */
	public void insert(String ptr) {
		if (inList(ptr)) return;
		insert(ptr,0);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	public boolean inList(Object ptr) {
		return super._inList(ptr);
	}
	/** 이 리스트에 스트링 객체가 존재하는지 확인하는 함수이다.
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
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	public void remove(String ptr) {
		// 내용이 같은 경우 뺀다
		String aName = getFirst();
		while (aName != null) {
			if (aName.compareTo(ptr) == 0) {
				super._remove((Object)aName,0);
				return;
			}
			aName = getNext();
		}
	}
	/** 이 리스트를 비우는 함수이다.
	 */
	public void clear() {
		super._clear();
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public String getFirst() {
		return (String)super._getFirst();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public String getLast() {
		return (String)super._getLast();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public String getNext() {
		return (String)super._getNext();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	public String getPrevious() {
		return (String)super._getPrevious();
	}
}
