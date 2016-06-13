package figure;

import modeler.*;

import java.awt.*;
import java.awt.Point;
import java.io.*;
import java.awt.event.*;
/** �� Ŭ������ UML ǥ����� dependency�� ǥ���ϱ� ���� ���̴�.
 * �� Ŭ�������� specialized�� ����� dependency�� ���� ������ ����
 * ȭ��ǥ�� ���Ǵ� ���̴�.
 */
public final 
	class ColTion extends AnyTion {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -2105700459025229873L;
	/** �������̴�.
	 */
	public ColTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		super.delete();
	}
	/** ���� ���콺�� �������� �� ���� ��ü�� ������ ���ΰ� Ȥ��  �̸� �κ��̳�, ���߼� �κ���
	 * ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantMoveFocus(CPoint point) {
		if (super.wantMoveFocus(point) == true) return true;
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (_focus == roleName) {
				return true;
			}
			tuple = _infoTuples.getNext();
		}
		return false;
	}
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public void drawDots(Graphics g) {
		super.drawDots(g);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.drawDots(g);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMCOLTION;
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.coltionPopup());
	}
	/** ���� ������ ���� �ɺ��� �� �����ϴ� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbol(ClassLike classPtr) {
		if (_classes.nOfList() == 1) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			Line firstLine = _lines.front().line();
			firstLine.coords(p1,p2);
			ClassLike firstClass = _classes.front();
			AnyTionInfoTuple tuple = new AnyTionInfoTuple(firstClass,p1.x,p1.y);
			_infoTuples.insert(tuple,0);
		}
		Line lastLine = _lines.rear().line();
//		if (lastLine.length() < _controller.arrowLength()) return true;
		lastLine.setDir(Const.NORMALDIR,Const.HEADARROW1);
		Point p = lastLine.last();
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classPtr,p.x,p.y);
		_infoTuples.insert(tuple,0);
		return false;
	}
	/** ���踦 ���� �ɺ��� �ϰ������� �� �����ϴ� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbolAll() {
		int arrowlength = _controller.arrowLength();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		ClassLike clss = _classes.front();
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.beforeList().empty()) {
				tmp.line().coords(p1,p2);
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y) == false) {
//					if (tmp.line().length() < arrowlength) {
//						return true;
//					}
					tmp.line().setDir(Const.INVERTDIR,Const.HEADARROW1);
				}
			}
			if (tmp.afterList().empty()) {
				tmp.line().coords(p1,p2);
				if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y) == false) {
//					if (tmp.line().length() < arrowlength) {
//						return true;
//					}
					tmp.line().setDir(Const.NORMALDIR,Const.HEADARROW1);
				}
			}
			tmp = _lines.getNext();
		}
		return false;
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		super.drawDashed(g,style,specialdcp);
		if (_name != null) _name.draw(g,style,specialdcp);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.draw(g,style,specialdcp);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		super.move(g,dx,dy);
		if (_moveAllFlag == false) return;
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			tuple.moveCoord(dx,dy);
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		ColTion copied;
		if (ptr == null) {
			copied = new ColTion(null,null,null);
		} else {
			copied = (ColTion)ptr;
		}
		return(super.born((Figure)copied));
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
	 */
	public boolean onEnter(int x,int y) {
		_focus = null;
		return super.onEnter(x,y);
	}
	/** �� ��ü�� �־��� �����ȿ� ���ԵǴ°��� ��Ÿ���� �Լ��̴�.
	 */
	public boolean checkInRegion(CRgn someregion) {
		if (super.checkInRegion(someregion)) return true;
		return false;
	}
	/** ���� ���콺�� ��ġ�� ���� �ش� ������ ������ ������ ã�Ƴ��� �Լ��̴�.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int x,int y) {
		Line focusLine;
		if (aline == null) focusLine = (Line)_focus;
		else focusLine = aline;
		if (focusLine == null) {
			_controller.beep("ColTion.AnyTionInfoTuple");
			return null;
		}
		if (_lines.nOfList() == 1) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			focusLine.coords(p1,p2);
			AnyTionInfoTuple tuple = _infoTuples.findTupleFor(p1.x,p1.y);
			if (tuple.classPtr != _classes.front()) return tuple;
			return _infoTuples.findTupleFor(p2.x,p2.y);
		}
		return _infoTuples.getTupleForLine(focusLine);
	}
	/** AITList�� resetRoleNameFor() �Լ� ����
	 */
	public void resetRoleNameFor(SingleLineText text) {
		_infoTuples.resetRoleNameFor(text);
		return;
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.coltionPopup();
	}  
	/** �� ��ü�� ���� �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		if (_focus.whoAreYou().isEqual(Const.IAMSINGLELINETEXT)) {
			_focus.popup(event);
			return;
		}
		boolean isFocusBoundaryLine = isBoundary(_focusNode);
		if (isFocusBoundaryLine || _lines.nOfList() == 1) {
			((ColTionPopup)_popup).roleB.setEnabled(true);
		} else {
			((ColTionPopup)_popup).roleB.setEnabled(false);
		}
		_popup.popup(event);
	}
	/** parameter�� �Ѱ��� �ؽ�Ʈ ��ü�� ���� aggregation���� ��� �������� ���Ǵ�
	 * �ؽ�Ʈ ��ü�ΰ��� �Ǻ��ϴ� �Լ��̴�.
	 */
	public int whatIsMyRole(SingleLineText text) {
		if (_infoTuples.inlistForRole(text)) {
			return Const.ROLENAME;
		}
		return super.whatIsMyRole(text);
	}
	/** �� ���� ��ü�� Ŭ������ �̵��� ���� �ʿ䰡 ������ �̵��� ���� ��������
	 * ���� �غ� �ϴ� �Լ��̴�. �ַ� Ŭ������ ���󰡾� �� ���е��� �����ϰ�
	 * �� ���е��� ���� ���� �ʱ�ȭ �Ѵ�.
	 */
	public void followClassProlog(ClassLike aclass) {
		super.followClassProlog(aclass);
		resetHeadType();
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			tuple.mark = false;
			if (tuple.classPtr == aclass) {
				int x = tuple.x();
				int y = tuple.y();
				LineNode tmp = findNodeFor(x,y);
				if (tmp != null) {
					tuple.mark = true;
					tmp.mark = true;
					tmp.makeCascadeFollow(_controller,_lines,_type);
					if (tmp.beforeList().empty()) {
						Point p1 = new Point(0,0);
						Point p2 = new Point(0,0);
						tmp.line().coords(p1,p2);
						Line newline = new Line(_controller,p1.x,p1.y,null,Const.STRAIGHT);
						newline.setOrient(Line.invertOrient(tmp.line().orient()));
						LineNode newnode = new LineNode(newline,null,null);
						newnode.cascade = true;
						newnode.afterList().insert(tmp,0);
						tmp.beforeList().insert(newnode,0);
						_lines.insert(newnode,0);
					}
				}
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ����� ����� Ŭ������ ������ �� �� Ŭ������ ���� �̵��ϴ� �Լ��̴�.
	 */
	public void followClass(Graphics g,int dx,int dy) {
		super.followClass(g,dx,dy);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
				if (tuple.role != null) {
					tuple.role.move(g,dx,dy);
				}
			}
			tuple = _infoTuples.getNext();
		}
	}
}