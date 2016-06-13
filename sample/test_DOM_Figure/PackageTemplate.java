package figure;

import modeler.*;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
/** �� Ŭ������ UML ǥ��� �߿��� ��Ű�� ��ü�� ǥ���ϱ� ���� ���̴�.
 */
public final class PackageTemplate extends Box 
								implements ClassLike 
{
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 1604569989804008476L;
	/** �� ��ü�� �μ� ��ü ���� �����ִ°��� ��Ÿ���� flag
	 */
	public boolean havePackageContent;
	/** �� ��Ű�� �̸��� ����Ǵ� ����� ���� �̸�
	 */
	private String _oldName;
	/** �� ������ ����
	 */
	private int _deltaV;
	/** ��Ű�� ǥ��� ���� �ٴ� ���� �簢���� ��
	 */
	private int _smallboxW = 30;
	/** ��Ű�� ǥ��� ���� �ٴ� ���� �簢���� ����
	 */
	private int _smallboxH = 15;
	/** ���� �簢�� ��ü ���۷���
	 */
	private Box _smallbox;
	/** ��Ű�� �̸� �κ��� ��Ÿ���� �ؽ�Ʈ ��ü
	 */
	private PackageText _packagename;
	/** �� ��Ű���� ����Ǿ��ִ� ������� �����ϴ� ����Ʈ ��ü
	 */
	private AnyTionList _anytionlist;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� x ����
	 */
	private static int _GapX = 10;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� y ����
	 */
	private static int _GapY = 20;
	/** �� ��Ű������ �𵨸��� ��ü���� �����ϴ� ����Ʈ
	 */
	public FigureList _figures;
	/** �� ��Ű������ Ȱ��ȭ ������ �ؽ�Ʈ ��ü�� ���� ���۷���
	 */
	private transient Figure _focus;
	/** �� ��Ű���� �̵��� �Բ� ����鵵 �̵��Ǿ�� �� �� �����Ǵ� ���� ��
	 */
	private transient boolean _moveComplexFlag;
	/** ��Ű���� �̸��� �������� �ʴ� ��� �⺻ �̸��� �ο��Ѵ�.
	 */
	/** �� ���� �ּ� ���� (���� ����)
	 */
	private static int _initWidth = 80;

	public void setDummyName() {
		_packagename.setDummyName();
	}
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_smallbox.setController(ptr);
		_packagename.setController(ptr);
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.packagePopup());
	}
	/** ���ڿ��� �־��� ũ�� ��ŭ ��Ű���� ���̸� �����Ѵ�. ������ ������ ���ڴ����̴�.
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
	/** ������� �������� ��Ű�� ��ü�� ���ִ� �Լ��̴�.
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
	/** �׸��� �̵��� �������� �� ���� �׸�����  ���� �����Ѵ�.
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
	/** �޴� ���ÿ� ���� ���ο� ��Ű���� ����� ��� �Լ��̴�.
	 */
	public static void makeNewPackage(GraphicController controller,int popupX,int popupY,int fontSizeV,Popup popupPtr) 
	{
		PackageTemplate apack = 
					   new PackageTemplate(controller,popupX,popupY,fontSizeV,popupPtr);
		controller.startEditableObject(apack,apack._packagename);
	}
	/** �� ��Ű���� ���ϴ� �𵨿��� ��Ű�� �̸����� ������ �Լ��̴�.
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
	/** �� ��Ű���� ���� ���� ��Ű�� ȭ�� ���½ÿ� �� ��Ű���� ������ ȭ�鿡 �ε��ϴ� �Լ��̴�. 
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
	/** ȭ�鿡 �ִ� ���� ��Ű�� ������ �� ��Ű���� �����ϴ� �Լ��̴�. 
	 */
	public void saveFromController(GraphicController packController) {
		havePackageContent = true;
		_figures.clear();
		_figures.copy(packController.figures());
	}
	/** �Ϲ������� ���Ǵ� �������̴�.
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
	/** ��ü ���� �ÿ� �ַ� ���Ǵ� �������̴�.
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
	
	/** ���� ��ü�� ����Ű�� �����͸� ���ο� ������ �����Ѵ�.
	 */
	public boolean replaceAnytionPtr(AnyTion from,AnyTion to) {
		boolean	replaced = _anytionlist.replacePtr(from,to);
		return replaced;
	}
	/** ���ڿ� ��õ� ������ �� ��ü�� ���ԵǴ°��� Ȯ���ϴ� �Լ��̴�.
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
	/** _anytionlist ����Ʈ�� ������ ���� �Լ��̴�.
	 */
	public void clearLists() {
		_anytionlist.clear();
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
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
	/** �� ��Ű���� ����� ���谡 �ִ°��� Ȯ���ϴ� �Լ��̴�.
	 */	
	public boolean doYouHaveAnyTion() {
		if (_anytionlist.empty() == false) return true;
		return false;
	}
	/** �� ��Ű���� �̸��� ������ �ִ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasname() {
		return _packagename.hasContent();
	}
	/** �� ��Ű���� �̸��� ã�ƿ��� �Լ��̴�.
	 */
	public String getName() {
		return _packagename.content().getName();
	}
	/** ������ ��Ű���� �� ��ü�� ������ ���ԵǴ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasAncestor(ClassLike tocheck) {
		if (this == tocheck) return true;
		String thisname = this.getName();
		String tocheckname = tocheck.getName();
		return false;
	}
	/** �� ��Ű���� ��ü �̸��� ã�ƿ��� �Լ��̴�.
	 */
	public String getFullName() {
		return getName();
	}
	/** ����Ÿ ��� _focus�� ���ο� ���� set�ϴ� access �Լ��̴�.
	 */
	public void setThisFocus(Figure focus) {
		_focus = focus;
	}
	/** ����Ÿ ��� _focus�� null ���� set�ϴ� access �Լ��̴�.
	 */
	public void resetFocus() {
		_focus = (Figure)this;
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMPACKAGE;
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		_smallbox.clear(g,expose);
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_packagename.setInCanvas(flag);
		_smallbox.setInCanvas(flag);
	}
	/** ��ǥ�� �̵� ��Ű�� �Լ��̴�.
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
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.packagePopup();
	}  
	/** �� ��ü�� ���� �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		_popup.popup(event);
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
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
	/** ��Ű�� ���� �۾��� ���� �� ��Ű�� ���� ���� ���� �ϰ��� ������ �ϴ� �Լ��̴�.
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
	/** �� ��ü�� ���� �ؽ�Ʈ ���� �۾��� ���۵����ϴ� �Լ��̴�.
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
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
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
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantResize(CPoint point) {
		return false;
	}
	/** ����Ÿ ��� _focus�� ���� �б� access �Լ�
	 */
	public Figure focus() {
		return _focus;
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
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
	/** �� ��Ű���� �̵��ϱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
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
	/** ���ڿ� ��õ� ���� ��ü ���۷����� ���� ����Ʈ�� �߰��ϴ� �Լ��̴�.
	 */
	public void attachToAnytionList(AnyTion anytion) {
		_anytionlist.insert(anytion,0);
	}
	/** ���ڿ� ��õ� ���� ��ü ���۷����� ���� ����Ʈ���� �����ϴ� �Լ��̴�.
	 */
	public void removeFromAnytionList(AnyTion anytion) {
		_anytionlist.remove(anytion);
	}
	/** ��Ű�� ��ü�� �̵��̳� ������ ���� ���Ŀ� ȣ��Ǵ� �Լ��μ� �ϰ��� �˻�,
	 * ��ġ �������� �����Ѵ�.
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
	/** �� ��Ű���� ����Ǿ� �ִ� ������� �����ϴ� �Լ��̴�.
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
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		super.makeRegion();
		_smallbox.makeRegion();
	}
	/** ȭ���� �ε��� �� Ŭ���� ������ ShadowClasses ����Ʈ�� ����ϴ� �Լ��̴�.
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
	/** ��Ű�� �̸��� �ٲ� ��쿡 �������� ��Ű�� �̸��� �ٲٴ� �Լ��̴�.
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
	
	/** �� ��Ű���� �̸� �ؽ�Ʈ ������ ���� �����ϴ� �Լ��̴�.
	 */
	public void setNameText(TextContent aContent) {
		_packagename.replaceTextContent(aContent);
	}
	/** ����Ÿ ��� _packagename�� ���� �б� access �Լ��̴�. 
	 */
	public PackageText packagename() {
		return _packagename;
	}
	
	/** �� ��Ű���� �̸� �κ��� �ٽ� �����ϴ� �Լ��̴�.
	 */
	public void constructName() {
		TextLine firstLine = _packagename.content().lineAt(0);
		firstLine.setString("package");
		_packagename.seeYouLater(true);		
	}	
	
	/** �� ��ü�� �������� ���ο� ��ġ�� �ٲٴ� �Լ�
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

	/** ����Ÿ ��� _anytionlist �� ���� �б� access �Լ��̴�.
	 */
	public AnyTionList getAnyTionList() {
		return _anytionlist;
	}
}