package figure;

import javax.swing.*;

/** 이 클래스는 모델러와 정제기가 사용하는 프레임의 공통 인터페이스를 제공하는 클래스이다.
 */
public class CommonFrame extends JFrame {
	/** 화면 콤포넌트 객체 레퍼런스
	 */ 
	protected GraphicController _controller = null;
	/** 이 프레임의 패키지 이름
	 */
	public String packageName;
	/** 데이타 멤버 packageName에 대한 읽기 access 함수이다.
	 */
	public String getPackageName() {
		return new String(packageName);
	}
	/** 데이타 멤버 _controller에 대한 읽기 access 함수이다.
	 */
	public GraphicController controller() {
		return _controller;
	}

	/** 생성자이다.
	 */	
	public CommonFrame(String name) {
		super(name);
	}
	
	/** 모델 저자의 이름을 돌려주는 함수이다.
	 */
	public String getAuthorName() {
		return new String("");
	}
	/** 모델 저자의 이름을 설정하는 함수이다.
	 */
	public void setAuthorName(String author) {
	}
}