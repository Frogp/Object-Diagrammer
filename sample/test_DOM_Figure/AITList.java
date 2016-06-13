package figure;

/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 AnyTionInfoTuple 객체들을
 * 관리하는 클래스이다.
 */
public final 
	class AITList extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 8537814107151771612L;
	/** 생성자이다.
	 */
	public AITList() {
		super();
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		AnyTionInfoTuple tmp = getFirst();
		while(tmp != null) {
			tmp.delete();
			tmp = getNext();
		}
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insert(AnyTionInfoTuple ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** 이 리스트의 처음 두번째에 새로운 객체를 append 하는 함수이다.
	 */
	public void append(AnyTionInfoTuple ptr) {
		super._append((Object)ptr);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	public boolean inList(AnyTionInfoTuple ptr) {
		return super._inList((Object)ptr);
	}
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	public void remove(AnyTionInfoTuple ptr) {
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
	public AnyTionInfoTuple getFirst() {
		return (AnyTionInfoTuple)super._getFirst();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public AnyTionInfoTuple getNext() {
		return (AnyTionInfoTuple)super._getNext();
	}
	/** 이 리스트에서 클래스 객체 값을 가지고 있는 튜블의 레퍼런스를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple findTupleFor(ClassLike clss) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.classPtr == clss) {
				return tmp;
			}
			tmp = getNext();
		}
		return null;
	}
	/** 이 리스트에서 매치되는 x, y 좌표 값을 가지고 있는 튜블의 레퍼런스를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple findTupleFor(int x,int y) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.x() == x && tmp.y() == y) {
				return tmp;
			}
			tmp = getNext();
		}
		return null;
	}
	/** 이 리스트에서 라인 객체 값을 가지고 있는 튜블의 레퍼런스를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple getTupleForLine(Line aline) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.meet(aline)) {
				return tmp;
			}
			tmp = getNext();
		}
		return null;
	}
	/** 이 리스트에서 qualification 객체 값을 가지고 있는 튜블의 레퍼런스를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple findTupleFor(QualificationText qual) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.qualification == qual) {
				return tmp;
			}
			tmp = getNext();
		}
		return null;
	}
	/** 이 리스트에 속하는 튜플 중에서 해당되는 qualification 값을 가지는 튜플이 있는지를 확인하는 함수이다.
	 */
	public boolean inlistForQualification(QualificationText aqualification) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.qualification == aqualification) {
				return true;
			}
			tmp = getNext();
		}
		return false;
	}
	/** 이 리스트에 속하는 튜플 중에서 해당되는 role name 값을 가지는 튜플이 있는지를 확인하는 함수이다.
	 */
	public boolean inlistForRole(SingleLineText arole) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.role == arole) {
				return true;
			}
			tmp = getNext();
		}
		return false;
	}
	/** 이 리스트에 속하는 튜플 중에서 해당되는 multiplicity 값을 가지는 튜플이 있는지를 확인하는 함수이다.
	 */
	public boolean inlistForMultiplicity(Figure text) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.multiplicity == text) {
				return true;
			}
			tmp = getNext();
		}
		return false;
	}
	/** 이 리스트에 속하는 튜플 중에서 해당되는 qulification 값을 가지는 튜플의 qualification 필드값을 리셋하는 함수이다..
	 */
	public void resetQualificationFor(QualificationText text) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.qualification == text) {
				tmp.qualification = null;
			}
			tmp = getNext();
		}
	}
	/** 이 리스트에 속하는 튜플 중에서 해당되는 role name 값을 가지는 튜플의 role name 필드값을 리셋하는 함수이다..
	 */
	public void resetRoleNameFor(SingleLineText text) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.role == text) {
				tmp.role = null;
			}
			tmp = getNext();
		}
	}
	/** 이 리스트에 속하는 튜플 중에서 해당되는 multiplicity 값을 가지는 튜플의 multiplicity 필드값을 리셋하는 함수이다..
	 */
	public void resetMultiplicityFor(SingleLineText text) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.multiplicity == (Figure)text) {
				tmp.multiplicity = null;
			}
			tmp = getNext();
		}
	}
	/** 이 리스트에 속하는 튜플 중에서 클래스 필드 값을 새로운 필드 값으로 변경하는 함수이다.
	 */
	public void replaceClassPtr(ClassLike from,ClassLike to) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			if (tmp.classPtr == from) {
				tmp.classPtr = to;
			}
			tmp = getNext();
		}
	}
	/** 이 리스트에 속하는 튜플의 controller 필드 값을 새로운 controller 값으로 변경하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			tmp.setController(ptr);
			tmp = getNext();
		}
	}
}