package figure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/** 이 클래스는 모든 팝업 메뉴의 공통 사항을 처리하는 추상 클래스이다.
 */
public abstract
	class Popup extends Object {
	/** 화면 콤포넌트에 대한 레퍼런스
	 */
	protected GraphicController _controller;
	/** 메뉴의 이름을 나타내는 레이블
	 */
	protected JMenuItem _label;
	/** 팝업 메뉴의 레퍼런스
	 */
	protected JPopupMenu _popupPtr;
	/** 생성자이다.
	 */
	public Popup(String name,GraphicController controller) {
		super();
		_controller = controller;
		_popupPtr = new JPopupMenu(name);
		_controller.add(_popupPtr);
		_popupPtr.add(name);
		_popupPtr.addSeparator();
	}
	/** 팝업 메뉴를 display하는 함수이다.
	 */
	public void popup(MouseEvent event) {
		_popupPtr.show(event.getComponent(),event.getX(),event.getY());
	}
	/** 레이블 이름을 변경하는 함수이다.
	 */
	public void setLabelString(String label) {
	}
	/** 버튼의 사용 가능 여부를 설정하는 함수이다.
	 */
	public void setButtonSensitive(String name,boolean flag) {}
	/** 버튼의 이름을 변경하는 함수이다.
	 */
	public void renameButton(String from,String to) {}
}
