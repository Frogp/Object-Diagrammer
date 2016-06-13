package figure;

import modeler.*;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
/** 이 클래스는 UML 표기법 중에서 패키지 객체를 표현하기 위한 것이다.
 */
public final class PackageTemplate extends Box 
								implements ClassLike 
{
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 1604569989804008476L;
	/** 이 객체가 부속 객체 모델을 갖고있는가를 나타내는 flag
	 */
	public boolean havePackageContent;
	/** 이 패키지 이름이 변경되는 경우의 이전 이름
	 */
	private String _oldName;
	/** 한 문자의 높이
	 */
	private int _deltaV;
	/** 패키지 표기법 위에 붙는 작은 사각형의 폭
	 */
	private int _smallboxW = 30;
	/** 패키지 표기법 위에 붙는 작은 사각형의 높이
	 */
	private int _smallboxH = 15;
	/** 작은 사각형 객체 레퍼런스
	 */
	private Box _smallbox;
	/** 패키지 이름 부분을 나타내는 텍스트 객체
	 */
	private PackageText _packagename;
	/** 이 패키지와 연결되어있는 관계들을 저장하는 리스트 객체
	 */
	private AnyTionList _anytionlist;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 x 변위
	 */
	private static int _GapX = 10;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 y 변위
	 */
	private static int _GapY = 20;
	/** 이 패키지에서 모델링된 객체들을 저장하는 리스트
	 */
	public FigureList _figures;
	/** 이 패키지에서 활성화 상태인 텍스트 객체에 대한 레퍼런스
	 */
	private transient Figure _focus;
	/** 이 패키지의 이동과 함께 관계들도 이동되어야 할 때 지정되는 상태 값
	 */
	private transient boolean _moveComplexFlag;
	/** 패키지의 이름이 정해지지 않는 경우 기본 이름을 부여한다.
	 */
	/** 한 줄의 최소 길이 (문자 단위)
	 */
	private static int _initWidth = 80;

	public void setDummyName() {
		_packagename.setDummyName();
	}
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_smallbox.setController(ptr);
		_packagename.setController(ptr);
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.packagePopup());
	}
	/** 인자에서 주어진 크기 만큼 패키지의 높이를 조정한다. 인자의 단위는 문자단위이다.
	 */
	public void modifyHeight(int dy) {
		_controller.clear(this,false);
		int deltay;
		deltay = dy * _deltaV;
		if ( dy > 0 ) {
			_y2 = _y2 + deltay;
			_controller.draw(this);
		} else {
			_y2 = _y2 + deltay;
			_controller.draw(this);
		}
		makeRegion();
		if (dy > 0) {
			AnyTion tmp = _anytionlist.getFirst();
			while (tmp != null) {
				tmp.tailorEndLinesForEditing(this,0);
				tmp = _anytionlist.getNext();
			}
		} else {
			AnyTion tmp = _anytionlist.getFirst();
			while (tmp != null) {
				tmp.extendToConFigure(this);
				tmp = _anytionlist.getNext();
			}
		}
	}
	/** 백업으로 만들어놨던 패키지 객체를 없애는 함수이다.
	 */
	private void destroyBackup() {
		PackageTemplate backup = (PackageTemplate)BackUp;
		AnyTion tmp = backup._anytionlist.getFirst();
		while(tmp != null) {
			tmp.classes().clear();
			tmp.delete();
			tmp = backup._anytionlist.getNext();
		}
		backup._anytionlist.clear();
		BackUp.delete();
		BackUp = null;
	}
	/** 그림의 이동이 실패했을 때 원래 그림으로  원상 복구한다.
	 */
	private void undoComplexMove() {
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			_controller.clear(tmp,true);
			tmp = _anytionlist.getNext();
		}
		_controller.clear(this,true);
		tmp = _anytionlist.getFirst();
		while(tmp != null) {
			ClassLike pack = tmp.classes().getFirst();
			while (pack != null) {
				if (pack != this) {
					pack.replaceAnytionPtr(tmp,(AnyTion)tmp.borned());
				}
				pack = tmp.classes().getNext();
			}
			tmp.classes().clear();
			tmp.delete();
			tmp = _anytionlist.getNext();
		}
		_anytionlist.clear();
		PackageTemplate backup = (PackageTemplate)BackUp;
		tmp = backup._anytionlist.getFirst();
		while(tmp != null) {
			tmp.resetMarkForMove();
			_controller.insert(tmp);
			_controller.lowlight(tmp);
			tmp = backup._anytionlist.getNext();
		}
		_controller.setCurrentFocus(BackUp);
		BackUp = this;
		_controller.beep("PackageTemplate.undoComplexMove");
		return;
	}
	/** 메뉴 선택에 의해 새로운 패키지를 만드는 골백 함수이다.
	 */
	public static void makeNewPackage(GraphicController controller,int popupX,int popupY,int fontSizeV,Popup popupPtr) 
	{
		PackageTemplate apack = 
					   new PackageTemplate(controller,popupX,popupY,fontSizeV,popupPtr);
		controller.startEditableObject(apack,apack._packagename);
	}
	/** 이 패키지에 속하는 모델에서 패키지 이름들을 모으는 함수이다.
	 */
	public void collectPackageNames(StringList packages) {
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			if (ptr.whoAreYou().equals(Const.IAMPACKAGE)) {
				String pName = ((PackageTemplate)ptr).getName();
				if (packages.inList(pName) == false) {
					packages.insert(pName);
					((PackageTemplate)ptr).collectPackageNames(packages);
				}
			}
			ptr = _figures.getNext();
		}
	}
	/** 이 패키지를 위한 서브 패키지 화면 오픈시에 이 패키지의 내용을 화면에 로드하는 함수이다. 
	 */
	public void loadContent(GraphicController controller) {
		controller.figures().copy(_figures);
		Figure aFig = _figures.getFirst();
		while (aFig != null) {
			aFig.setController(controller);
			aFig.replacePopup(controller);
			aFig = _figures.getNext();
		}
		controller.localMakeClear(false);
		_figures.clear();
	}
	/** 화면에 있는 서브 패키지 내용을 이 패키지로 저장하는 함수이다. 
	 */
	public void saveFromController(GraphicController packController) {
		havePackageContent = true;
		_figures.clear();
		_figures.copy(packController.figures());
	}
	/** 일반적으로 사용되는 생성자이다.
	 */
	public PackageTemplate(GraphicController controller,int x1,int y1,int deltaV,Popup popup) 
	{
		super(controller,x1,y1,
			  x1+_initWidth+2*_GapX,
			  y1+deltaV+2*_GapY,popup);
			  
		_oldName = new String("");
		havePackageContent = false;
		_deltaV = deltaV;
		_stackingFlag = true;
		_moveComplexFlag = false;
		_figures = new FigureList();

		int width = _initWidth + 2*_GapX;
		int height = _deltaV + 2*_GapY;
		_packagename = new PackageText(controller,this,
									   x1+_GapX,y1+_GapY,null);
		_smallbox = new Box(controller,_x1,_y1-_smallboxH,
							_x1+_smallboxW,_y1,null);

		_anytionlist = new AnyTionList();

		_focus = (Figure)this;
	}
	/** 객체 복사 시에 주로 사용되는 생성자이다.
	 */
	PackageTemplate(GraphicController controller,int x1,int y1,int x2,int y2,int deltaV,Popup popup) {
		super(controller,x1,y1,x2,y2,popup);
		_oldName = new String("");
		havePackageContent = false;
		_anytionlist = new AnyTionList();

		_stackingFlag = true;
		_moveComplexFlag = false;
		_focus = (Figure)this;
		_deltaV = deltaV;
		_figures = new FigureList();
	}
	
	/** 관계 객체를 가리키는 포인터를 새로운 값으로 변경한다.
	 */
	public boolean replaceAnytionPtr(AnyTion from,AnyTion to) {
		boolean	replaced = _anytionlist.replacePtr(from,to);
		return replaced;
	}
	/** 인자에 명시된 영역에 이 객체가 포함되는가를 확인하는 함수이다.
	 */
	public boolean checkEntries(CRgn reg) {
		if (visited) return true;
		CRgn tmp = CRgn.myCreateRgn();
		CRgn.myUnionRgn(reg,_region,tmp);
		if (!CRgn.myEqualRgn(reg,tmp)) {
			CRgn.myDestroyRgn(tmp);
			return false;
		}
		CRgn.myDestroyRgn(tmp);
		tmp = CRgn.myCreateRgn();
		AnyTion ptr = _anytionlist.getFirst();
		while (ptr != null) {
			CRgn.myUnionRgn(tmp,ptr.region(),tmp);
			ptr = _anytionlist.getNext();
		}
		CRgn.myUnionRgn(reg,tmp,tmp);
		if (!CRgn.myEqualRgn(reg,tmp)) {
			CRgn.myDestroyRgn(tmp);
			return false;
		}
		CRgn.myDestroyRgn(tmp);
		visited = true;

		boolean flag;
		ptr = _anytionlist.getFirst();
		while (ptr != null) {
			flag = ptr.checkEntries(reg);
			if (flag == false) {
				visited = false;
				return false;
			}
			ptr = _anytionlist.getNext();
		}
		return true;
	}
	/** _anytionlist 리스트의 내용을 비우는 함수이다.
	 */
	public void clearLists() {
		_anytionlist.clear();
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		_smallbox.delete(); _smallbox = null;
		_packagename.delete(); _packagename = null;
		if (_moveComplexFlag) {
			_anytionlist.delete(); _anytionlist = null;
			return;
		}
		AnyTion ptr = _anytionlist.getFirst();
		while (ptr != null) {
			ptr.removeFromClasses(this);
			_anytionlist.getNext();
		}
		_anytionlist.delete(); _anytionlist = null;
		_focus = null;
	}
	/** 이 패키지에 연결된 관계가 있는가를 확인하는 함수이다.
	 */	
	public boolean doYouHaveAnyTion() {
		if (_anytionlist.empty() == false) return true;
		return false;
	}
	/** 이 패키지가 이름을 가지고 있는가를 확인하는 함수이다.
	 */
	public boolean hasname() {
		return _packagename.hasContent();
	}
	/** 이 패키지의 이름을 찾아오는 함수이다.
	 */
	public String getName() {
		return _packagename.content().getName();
	}
	/** 인자의 패키지가 이 객체의 선조에 포함되는가를 확인하는 함수이다.
	 */
	public boolean hasAncestor(ClassLike tocheck) {
		if (this == tocheck) return true;
		String thisname = this.getName();
		String tocheckname = tocheck.getName();
		return false;
	}
	/** 이 패키지의 전체 이름을 찾아오는 함수이다.
	 */
	public String getFullName() {
		return getName();
	}
	/** 데이타 멤버 _focus에 새로운 값을 set하는 access 함수이다.
	 */
	public void setThisFocus(Figure focus) {
		_focus = focus;
	}
	/** 데이타 멤버 _focus에 null 값을 set하는 access 함수이다.
	 */
	public void resetFocus() {
		_focus = (Figure)this;
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMPACKAGE;
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		_smallbox.clear(g,expose);
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_packagename.setInCanvas(flag);
		_smallbox.setInCanvas(flag);
	}
	/** 좌표만 이동 시키는 함수이다.
	 */
	public void moveCoord(int dx,int dy) {
		super.moveCoord(dx,dy);
		_smallbox.moveCoord(dx,dy);
		_packagename.moveCoord(dx,dy);
		if (_moveComplexFlag == false) return;
		Graphics rg = _controller.getgraphics();
		rg.setXORMode(_controller.getBackground());
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.followClass(rg,dx,dy);
			tmp = _anytionlist.getNext();
		}
		rg.dispose();
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.packagePopup();
	}  
	/** 이 객체를 위한 팝업 메뉴를 display하는 함수이다.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		_popup.popup(event);
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		super.move(g,dx,dy);
		_smallbox.move(g,dx,dy);
		_packagename.move(g,dx,dy);
		if (_moveComplexFlag == false) return;
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.followClass(g,dx,dy);
			tmp = _anytionlist.getNext();
		}
	}
	public void setX2() {
		int oldX2 = _x2;
		int realWidth = _packagename.getRealWidth();
		if (realWidth < _initWidth) {
			realWidth = _initWidth;
		}
		_x2 = _x1 + realWidth + 2*_GapX;
		makeRegion();
		int dx = _x2 - oldX2;
		if (dx > 0) {
			AnyTion tmp = _anytionlist.getFirst();
			while (tmp != null) {
				tmp.tailorEndLinesForEditing(this,0);
				tmp = _anytionlist.getNext();
			}
		} else {
			AnyTion tmp = _anytionlist.getFirst();
			while (tmp != null) {
				tmp.extendToConFigure(this);
				tmp = _anytionlist.getNext();
			}
		}
	}
	/** 패키지 편집 작업이 끝날 때 패키지 상태 값에 대한 일관성 검증을 하는 함수이다.
	 */
	public void farewell() {
		setX2();
		makeRegion();
		AnyTion tmp = _anytionlist.getFirst();
		while (tmp != null) {
			tmp.checkConsistency();
			_controller.lowlight(tmp);
			tmp = _anytionlist.getNext();
		}
	}
	/** 이 객체에 대해 텍스트 편집 작업을 시작도록하는 함수이다.
	 */
	public void localStartEdit(int popupX,int popupY) {
		_controller.clear(this,true);
		_controller.draw(this);
		PackageText field = _packagename;
		_controller._editingTag = true;
		_controller.setCurrentFocus(this);
		field.startEdit();
		_focus = (Figure)field;
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int style,Graphics specialgc) {
		if (!_inCanvas) return;
		super.draw(g,style,specialgc);
		_smallbox.draw(g,style,specialgc);
		if (style == Const.DRAWING) {
			_packagename.draw(g,style,specialgc);
		}
		if (_moveComplexFlag == false || style != Const.RUBBERBANDING) return;
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.draw(g,style,specialgc);
			tmp = _anytionlist.getNext();
		}
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		PackageTemplate copied;
		if (ptr == null) {
			copied = new PackageTemplate(_controller,0,0,0,0,0,null);
		} else {
			copied = (PackageTemplate)ptr;
		}
		copied._popup = _popup;
		copied._controller = _controller;
		copied._stackingFlag = _stackingFlag;
		copied._focus = copied;
		copied._deltaV = _deltaV;
		copied._packagename = (PackageText)_packagename.born(null);
		copied._packagename.setPackagePtr(copied);
		copied._smallbox = (Box)_smallbox.born(null);
		return(super.born((Figure)copied));
	}
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantResize(CPoint point) {
		return false;
	}
	/** 데이타 멤버 _focus에 대한 읽기 access 함수
	 */
	public Figure focus() {
		return _focus;
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
	 */
	public boolean onEnter(int x,int y) {
		if (super.onEnter(x,y)) {
			_focus = (Figure)this;
			return true;
		} else if (_smallbox.onEnter(x,y)) {
			_focus = (Figure)this;
			return true;
		} else {
			_focus = (Figure)this;
			return false;
		}
	}
	/** 이 패키지를 이동하기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
	 */
	public boolean moveProlog(int oneorsome) {
		_moveComplexFlag = false;
		if (doYouHaveAnyTion() == false) {
			return true;
		}
		_moveComplexFlag = true;
		if (BackUp != null) {
			BackUp.delete();
		}
		BackUp = born(null);
		BackUp.makeRegion();
		PackageTemplate backup = (PackageTemplate)BackUp;
		AnyTion copied = null;
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			copied = (AnyTion)tmp.born(null);
			copied.makeRegion();
			copied.replaceClassPtr(this,backup);
			backup._anytionlist.insert(copied,0);
			_controller.remove(tmp);
			_controller.checkInlist(tmp);
			tmp = _anytionlist.getNext();
		}
		tmp = _anytionlist.getFirst();
		while(tmp != null) {
			_controller.clear(tmp,true);
			tmp = _anytionlist.getNext();
		}
		tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.followClassProlog(this);
			tmp = _anytionlist.getNext();
		}
		return true;
	}
	/** 인자에 명시된 관계 객체 레퍼런스를 관계 리스트에 추가하는 함수이다.
	 */
	public void attachToAnytionList(AnyTion anytion) {
		_anytionlist.insert(anytion,0);
	}
	/** 인자에 명시된 관계 객체 레퍼런스를 관계 리스트에서 제거하는 함수이다.
	 */
	public void removeFromAnytionList(AnyTion anytion) {
		_anytionlist.remove(anytion);
	}
	/** 패키지 객체의 이동이나 편집이 끝난 이후에 호출되는 함수로서 일관성 검사,
	 * 수치 보정들을 수행한다.
	 */
	public boolean epilog(boolean flag) {
		if (_moveComplexFlag == false) return false;
		_moveComplexFlag = false;
		makeRegion();
		boolean ok = false;
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.makeRegion();
			ok = tmp.checkConsistency();
			if (ok == false) {
				undoComplexMove();
				return false;
			}
			tmp = _anytionlist.getNext();
		}
		tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.resetMarkForMove();
			_controller.insert(tmp);
			_controller.lowlight(tmp);
			tmp = _anytionlist.getNext();
		}
		destroyBackup();
		return false;
	}
	/** 이 패키지와 연결되어 있는 관계들을 제거하는 함수이다.
	 */
	public void deleteNeighbors() {
		AnyTionList anytionlist = new AnyTionList();
		anytionlist.copy(_anytionlist);

		AnyTion anytion = anytionlist.getFirst();
		while(anytion != null) {
			_controller.setCurrentFocus(anytion);
			if (anytion.whoAreYou().isEqual(Const.IAMASSTION)) {
				_controller.deleteCurrentFigure();
			} else {
				if (anytion.isparentclass(this))
					_controller.deleteCurrentFigure();
				else {
					boolean deleteAll = anytion.detachFrom(this);
					if (deleteAll) {
						anytion.delete();
						_controller.resetCurrentFocus();
					}
				}
			}
			anytion = anytionlist.getNext();
		}
		anytionlist.delete(); anytionlist = null;
		_anytionlist.clear();
		_controller.setCurrentFocus(this);
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		super.makeRegion();
		_smallbox.makeRegion();
	}
	/** 화일을 로드할 때 클래스 내용을 ShadowClasses 리스트에 등록하는 함수이다.
	 */
	public void registerClassContentWhenLoading(String packName) {
		String thisName = this.getName();
		if (thisName == null) return;
		if (thisName.length() == 0) return;
		if (_figures.nOfList() == 0) return;
		Figure ptr = _figures.getFirst();
		while (ptr != null) {
			ptr.registerClassContentWhenLoading(thisName);
			ptr = _figures.getNext();
		}
	}
	/** 패키지 이름이 바뀐 경우에 브라우저의 패키지 이름을 바꾸는 함수이다.
	 */
	public void replacePackageNameIfNeed() {
		if ((_controller.frame() instanceof Modeler) == false) return;
		String newName = getName();
		if (newName.equals(_oldName)) {
			return;
		} else if (_oldName.length() == 0 && Modeler.BrowserDlg != null) {
			Modeler.BrowserDlg.addNewPackage(newName);
		} else if (Modeler.BrowserDlg != null) {
			Modeler.BrowserDlg.replacePackageName(_oldName,newName);
/*			GraphicController.ShadowClasses.replacePackageName(_oldName,newName);
*/
			_controller.getShadowClasses().replacePackageName(_oldName,newName);
		}
		_oldName = getName();
	}
	
	/** 이 패키지의 이름 텍스트 내용을 새로 지정하는 함수이다.
	 */
	public void setNameText(TextContent aContent) {
		_packagename.replaceTextContent(aContent);
	}
	/** 데이타 멤버 _packagename에 대한 읽기 access 함수이다. 
	 */
	public PackageText packagename() {
		return _packagename;
	}
	
	/** 이 패키지의 이름 부분을 다시 구성하는 함수이다.
	 */
	public void constructName() {
		TextLine firstLine = _packagename.content().lineAt(0);
		firstLine.setString("package");
		_packagename.seeYouLater(true);		
	}	
	
	/** 이 객체의 시작점을 새로운 위치로 바꾸는 함수
	 */
	public void changeOriginPoint(int newX,int newY) {
		int oldX = _x1;
		int oldY = _y1;
		int deltaX = newX - oldX;
		int deltaY = newY - oldY;
		super.moveCoord(deltaX,deltaY);
		_smallbox.moveCoord(deltaX,deltaY);
		_packagename.moveCoord(deltaX,deltaY);
	}

	/** 데이타 멤버 _anytionlist 에 대한 읽기 access 함수이다.
	 */
	public AnyTionList getAnyTionList() {
		return _anytionlist;
	}
}