package figure;
/** 이 클래스는 UML 표기법에서 관계의 이름, role name, 다중성과 같이 한 줄로
 * 명시되는 텍스트를 처리하기 위한 것이다.
 */
public final 
	class SingleLineText extends Text {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -8431981187362754519L;
	/** 이 텍스트가 소속된 결합 관계에 대한 레퍼런스 
	 */
	private AnyTion _anytionPtr;
	/** 이 객체가 편집 후에 파괴되어야 함을 나타내는 flag
	 */
	private boolean _shouldBeDeleted;
	/** 생성자이다.
	 */
	public SingleLineText(GraphicController controller,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		_shouldBeDeleted = false;
		_anytionPtr = null;
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simplePopup());
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simplePopup();
	} 
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		_anytionPtr = null;
	}
	/** 데이타 멤버 _anytionPtr 대한 쓰기 access 함수
	 */
	public void setAnytionPtr(AnyTion ptr) {
		_anytionPtr = ptr;
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMSINGLELINETEXT;
	}
	/** 현재 텍스트의 편집 작업이 마무리되었을 때 정리 작업을 하는 함수이다.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		if (_content.valueAt(0,0) == '\0') {
			ensureConsistencyBeforeDelete();
			_shouldBeDeleted = true;
		}
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		SingleLineText copied;
		if (ptr == null) {
			copied = new SingleLineText(_controller,0,0,null);
		} else {
			copied = (SingleLineText)ptr;
		}
		copied._anytionPtr = _anytionPtr;
		copied._shouldBeDeleted = false;
		return(super.born((Figure)copied));	
	}
	/** 객체의 파괴 이전에 일관성 문제가 없음을 확인하는 함수이다.
	 */
	public void ensureConsistencyBeforeDelete() {
		int myRole = _anytionPtr.whatIsMyRole(this);
		if (myRole == Const.ANYTIONNAME) {
			_anytionPtr.setNameText(null);
		} else if (myRole == Const.ROLENAME) {
			_anytionPtr.resetRoleNameFor(this);
		} else if (myRole == Const.MULTIPLICITYNAME) {
			_anytionPtr.resetMultiplicityFor(this);
		}
	}
	/** 데이타 멤버 _shouldBeDeleted에 대한 읽기 access 함수
	 */
	public boolean shouldBeDeleted() {
		return _shouldBeDeleted;
	}
	/** 이 객체의 폭과 높이를 다시 계산하는 함수이다.
	 */
	public void recalcWidthHeight() {
		/*
		int myRole = _anytionPtr.whatIsMyRole(this);
		if (myRole == ROLENAME) {
		_xscale = 0.9;
		_yscale = 0.9;
		_screen.xscale() = 9.0;
		_screen.yscale() = 9.0;
		_screen.deltaH() = irint(0.9*_controller.fontSizeH());
		_screen.deltaV() = irint(0.9*_controller.fontSizeV());
		Text::recalcWidthHeight();
		return;
		} else {
		_xscale = 1.0;
		_yscale = 1.0;
		_screen.xscale() = 1.0;
		_screen.yscale() = 1.0;
		_screen.deltaH() = _controller.fontSizeH();
		_screen.deltaV() = _controller.fontSizeV();
		Text::recalcWidthHeight();
		}
		*/
	}
	/** 이 객체를 포함하는 객체를 찾는 함수이다.
	 */
	public Figure container() {
		return _anytionPtr;
	}
	/** 이 텍스트를 바이트 문자열로 구성하는 함수이다.
	 */
	public char[] getRelationStatement() {
		return null;
	}
}
