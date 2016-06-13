package figure;
import java.awt.Point;
/** 이 인터페이스는 클래스 객체와 패키지 객체를 동일한 부류의 객체처럼
 * 이용하기 위해 사용되는 인터페이스이다. ClassTemplate 클래스와 PackageTemplate
 * 클래스는 이 인터페이스를 구현한다.
 */
public interface ClassLike {
	/** 관계 객체를 가리키는 포인터를 새로운 값으로 변경한다.
	 */
	public boolean replaceAnytionPtr(AnyTion a,AnyTion b);
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion();
	/** 데이타 멤버 _region의 access 함수이다.
	 */
	public CRgn region();
	/** 데이타 멤버 _maxRegion의 access 함수이다.
	 */
	public CRgn maxRegion();
	/** 입력 인자인 라인을 사각형의 변경까지 위치하도록 길이를 조절하는 함수이다.
	 */
	public boolean adjustLine(Line a,boolean b);
	/** 입력 인자인 라인의 모서리를 확장시켜서 이 사각형 객체의 중앙까지 길어지게하는
	 * 함수이다.
	 */
	public void expandLineToCenter(Line a,boolean b);
	/** 인자에 명시된 관계 객체 레퍼런스를 관계 리스트에서 제거하는 함수이다.
	 */
	public void removeFromAnytionList(AnyTion a);
	/** 인자에 명시된 관계 객체 레퍼런스를 관계 리스트에 추가하는 함수이다.
	 */
	public void attachToAnytionList(AnyTion a);
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou();
	/** 데이타 멤버 controller의 access 함수이다.
	 */
	public GraphicController controller();
	/** 이 객체의 모서리 좌표, x1,y1,x2,y2의 값을 알아내는 함수이다.
	 */
	public void coords(Point p1,Point p2);
	/** 이 객체의 중앙 좌표값을 알아내는 함수이다.
	 */
	public Point center();
	/** 좌표 x,y 가 이 객체 내부에 포함되는가를 결정하는 함수이다.
	 */
	public boolean contains(int a, int b);
	/** 이 클래스나 패키지 객체의 이름을 알아오는 함수이다.
	 */
	public String getName();
	/** 인자의 클래스가 이 객체의 선조에 포함되는가를 확인하는 함수이다.
	 */
	public boolean hasAncestor(ClassLike tocheck);
	/** 이 객체에 대해 텍스트 편집 작업을 시작도록하는 함수이다.
	 */
	public void localStartEdit(int a, int b);
}