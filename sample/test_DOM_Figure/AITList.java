package figure;

/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� AnyTionInfoTuple ��ü����
 * �����ϴ� Ŭ�����̴�.
 */
public final 
	class AITList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 8537814107151771612L;
	/** �������̴�.
	 */
	public AITList() {
		super();
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		AnyTionInfoTuple tmp = getFirst();
		while(tmp != null) {
			tmp.delete();
			tmp = getNext();
		}
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(AnyTionInfoTuple ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� ó�� �ι�°�� ���ο� ��ü�� append �ϴ� �Լ��̴�.
	 */
	public void append(AnyTionInfoTuple ptr) {
		super._append((Object)ptr);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(AnyTionInfoTuple ptr) {
		return super._inList((Object)ptr);
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(AnyTionInfoTuple ptr) {
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
	public AnyTionInfoTuple getFirst() {
		return (AnyTionInfoTuple)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public AnyTionInfoTuple getNext() {
		return (AnyTionInfoTuple)super._getNext();
	}
	/** �� ����Ʈ���� Ŭ���� ��ü ���� ������ �ִ� Ʃ���� ���۷����� ã�Ƴ��� �Լ��̴�.
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
	/** �� ����Ʈ���� ��ġ�Ǵ� x, y ��ǥ ���� ������ �ִ� Ʃ���� ���۷����� ã�Ƴ��� �Լ��̴�.
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
	/** �� ����Ʈ���� ���� ��ü ���� ������ �ִ� Ʃ���� ���۷����� ã�Ƴ��� �Լ��̴�.
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
	/** �� ����Ʈ���� qualification ��ü ���� ������ �ִ� Ʃ���� ���۷����� ã�Ƴ��� �Լ��̴�.
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� �ش�Ǵ� qualification ���� ������ Ʃ���� �ִ����� Ȯ���ϴ� �Լ��̴�.
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� �ش�Ǵ� role name ���� ������ Ʃ���� �ִ����� Ȯ���ϴ� �Լ��̴�.
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� �ش�Ǵ� multiplicity ���� ������ Ʃ���� �ִ����� Ȯ���ϴ� �Լ��̴�.
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� �ش�Ǵ� qulification ���� ������ Ʃ���� qualification �ʵ尪�� �����ϴ� �Լ��̴�..
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� �ش�Ǵ� role name ���� ������ Ʃ���� role name �ʵ尪�� �����ϴ� �Լ��̴�..
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� �ش�Ǵ� multiplicity ���� ������ Ʃ���� multiplicity �ʵ尪�� �����ϴ� �Լ��̴�..
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
	/** �� ����Ʈ�� ���ϴ� Ʃ�� �߿��� Ŭ���� �ʵ� ���� ���ο� �ʵ� ������ �����ϴ� �Լ��̴�.
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
	/** �� ����Ʈ�� ���ϴ� Ʃ���� controller �ʵ� ���� ���ο� controller ������ �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		AnyTionInfoTuple tmp = getFirst();
		while (tmp != null) {
			tmp.setController(ptr);
			tmp = getNext();
		}
	}
}