package figure;

/** 이 클래스는 상속 트리를 구성할 때 사용된다.
 * 즉 상속 트리의 한 노드에 대한 정보를 표현하기 위해 사용한다.
 */
public final 
	class GentionTreeNodeValue extends Object {
	/** 폭에 대한 그리드 인덱스
	 */
	public int i;
	/** 깊이에 대한 그리드 인덱스
	 */
	public int j;
	/** 클래스 이름
	 */
	public String className;
	/** class에 대한 포인터
	 */
	public ClassTemplate classPtr;
	/** 생성자이다.
	 */
	public GentionTreeNodeValue(String name) {
		super();
		className = name;
		classPtr = null;
		i = 0; j = 0;
	}
	/** 스트링으로 변환해주는 함수이다.
	 */
	public String toString() {
		return className;
	}
}