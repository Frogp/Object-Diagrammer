package figure;

import java.io.*;
import java.awt.Point;
/** 이 클래스는 관계를 나타내는 객체들에 속하는 경계선들 중에서 클래스와
 * 접하는 꼭지점에 관한 정보를 저장하는 클래스이다. 이들 객체는 AITList 컨테이너
 * 객체에 의해 주로 관리된다.
 */
public final 
	class AnyTionInfoTuple extends Object implements Serializable {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 6320028524375679645L;
	/** 경계선 선분의 꼭지점 x 좌표
	 */
	protected int _x;
	/** 경계선 선분의 꼭지점 y 좌표
	 */
	protected int _y;
	/** 경계선과 접하는 클래스 객체에 대한 레퍼런스
	 */
	public ClassLike classPtr;
	/** 경계선에 존재하는 role name 텍스트의 레퍼런스
	 */
	public SingleLineText role;
	/** 경계선에 존재하는 qualification 텍스트의 레퍼런스
	 */
	public QualificationText qualification;
	/** 경계선에 존재하는 multiplicity 텍스트의 레퍼런스
	 */
	public SingleLineText multiplicity;
	/** 경계선의 이동시에 사용되는 flag
	 */
	public transient boolean mark;
	/** 각 부속 텍스트 객체의 controller 값을 새로 지정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		if (role != null) role.setController(ptr);
		if (qualification != null) qualification.setController(ptr);
		if (multiplicity != null) multiplicity.setController(ptr);
	}
	/** 생성자이다.
	 */
	public AnyTionInfoTuple(ClassLike clss,int x,int y) {
		super();
		classPtr = clss;
		role = null;
		qualification = null;
		multiplicity = null;
		mark = false;
		if (clss == null) return;
		GraphicController controller = clss.controller();
		if (controller == null) return;
		_x = x;
		_y = y;
	}
	/** 생성자이다.
	 */
	public AnyTionInfoTuple(AnyTionInfoTuple from) {
		classPtr = from.classPtr;
		_x = from._x;
		_y = from._y;
		role = from.role;
		qualification = from.qualification;
		multiplicity = from.multiplicity;
		mark = false;
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		classPtr = null;
		if (role != null) {
			role.delete(); role = null;
		}
		if (qualification != null) {
			qualification.delete(); qualification = null;
		}
		if (multiplicity != null) {
			multiplicity.delete(); multiplicity = null;
		}
	}
	/** 데이타 멤버 _x 에 대한 access 함수
	 */
	public int x() {
		return _x;
	}
	/** 데이타 멤버 _y 에 대한 access 함수
	 */
	public int y() {
		return _y;
	}
	/** 데이타 멤버 _x,_y 에 대한 access 함수
	 */
	public void setxy(int x,int y) {
		_x = x; _y = y;
	}
	/** 좌표 이동 함수
	 */
	public void moveCoord(int dx,int dy) {
		_x = _x+ dx; _y = _y + dy;
	}
	/** 이 꼭지점이 인자에 명시된 라인과 만나는가를 검사하는 함수이다.
	 */
	public boolean meet(Line aline) {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		aline.coords(p1,p2);
		if (p1.x == _x && p1.y == _y) return true;
		if (p2.x == _x && p2.y == _y) return true;
		return false;
	}
	/** 이 꼭지점이 인자에 명시된 좌표와 만나는가를 검사하는 함수이다.
	 */
	public boolean meet(int x,int y) {
		if (x == _x && y == _y) return true;
		return false;
	}
}
