package figure;

import java.io.*;
/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 관계 스트링들을
 * 관리하는 클래스이다.
 */
public final
	class StrRelation extends List {
	/** 생성자이다.
	 */
	public StrRelation() {
		super();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 * 리스트에 이미 객체가 있으면 insert 하지 않는다.
	 */
	public void insert(StrRelItem ptr) {
		if (inList(ptr)) return;
		_insert((Object)ptr,0);
	}
	/** 이 리스트에 스트링 관계 객체가 존재하는지 확인하는 함수이다.
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
	/** 이 리스트에 스트링 관계 객체의 위치를 찾는 함수이다.
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
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	public void remove(StrRelItem ptr) {
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
	public StrRelItem getFirst() {
		return (StrRelItem)super._getFirst();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public StrRelItem getLast() {
		return (StrRelItem)super._getLast();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public StrRelItem getNext() {
		return (StrRelItem)super._getNext();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	public StrRelItem getPrevious() {
		return (StrRelItem)super._getPrevious();
	}
}
