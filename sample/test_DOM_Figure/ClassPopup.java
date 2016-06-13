package figure;


import javax.swing.*;
/** 이 클래스는 클래스 객체를 위한 popup 메뉴이다. 이 클래스는 모델러와 정제기에서
 * 사용되는 클래스 팝업의 공통 데이타 멤버를 유지한다.
 */
public class ClassPopup extends figure.Popup {
	/** 클래스의 크기를 변경할 때 사용되는 버튼이다. 이 버튼의 레이블은
	 * 클래스 객체의 상태에 따라 "minimize"와 "maximize" 값을 갖는다.
	 */
//	public JMenuItem sizeB;
	/** 이 클래스 객체를 template class로 변환 시킬 때, 혹은 그 반대 방향으로
	 * 변환 시킬 때 사용되는 버튼이다.
	 */
	public JMenuItem templateB;
	/** 생성자이다.
	 */
	public ClassPopup(GraphicController controller) {
		super("Class Popup",controller);
	}
}