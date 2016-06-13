package figure;
/** �� Ŭ������ UML ǥ������� ������ �̸�, role name, ���߼��� ���� �� �ٷ�
 * ��õǴ� �ؽ�Ʈ�� ó���ϱ� ���� ���̴�.
 */
public final 
	class SingleLineText extends Text {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -8431981187362754519L;
	/** �� �ؽ�Ʈ�� �Ҽӵ� ���� ���迡 ���� ���۷��� 
	 */
	private AnyTion _anytionPtr;
	/** �� ��ü�� ���� �Ŀ� �ı��Ǿ�� ���� ��Ÿ���� flag
	 */
	private boolean _shouldBeDeleted;
	/** �������̴�.
	 */
	public SingleLineText(GraphicController controller,int ox,int oy,Popup popup) {
		super(controller,ox,oy,popup);
		_shouldBeDeleted = false;
		_anytionPtr = null;
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.simplePopup());
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simplePopup();
	} 
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		_anytionPtr = null;
	}
	/** ����Ÿ ��� _anytionPtr ���� ���� access �Լ�
	 */
	public void setAnytionPtr(AnyTion ptr) {
		_anytionPtr = ptr;
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMSINGLELINETEXT;
	}
	/** ���� �ؽ�Ʈ�� ���� �۾��� �������Ǿ��� �� ���� �۾��� �ϴ� �Լ��̴�.
	 */
	public void seeYouLater(boolean suspendflag) {
		bye();
		if (_content.valueAt(0,0) == '\0') {
			ensureConsistencyBeforeDelete();
			_shouldBeDeleted = true;
		}
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** ��ü�� �ı� ������ �ϰ��� ������ ������ Ȯ���ϴ� �Լ��̴�.
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
	/** ����Ÿ ��� _shouldBeDeleted�� ���� �б� access �Լ�
	 */
	public boolean shouldBeDeleted() {
		return _shouldBeDeleted;
	}
	/** �� ��ü�� ���� ���̸� �ٽ� ����ϴ� �Լ��̴�.
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
	/** �� ��ü�� �����ϴ� ��ü�� ã�� �Լ��̴�.
	 */
	public Figure container() {
		return _anytionPtr;
	}
	/** �� �ؽ�Ʈ�� ����Ʈ ���ڿ��� �����ϴ� �Լ��̴�.
	 */
	public char[] getRelationStatement() {
		return null;
	}
}
