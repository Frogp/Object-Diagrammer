package figure;

/** 이 클래스는 parameter passing 시에 call by reference 방식을 이용하기 위한
 * 것이다. 특히 불린 변수 값을 알아오기 위해 actual parameter로 사용된다.
 */
public final 
	class BoolVar extends Object {
	/** side effect를 이용하여 알아오고자 하는 불린 값
	 */
	public boolean v;
	/** 생성자이다.
	 */
	public BoolVar(boolean flag) {
		super();
		v = flag;
	}
}