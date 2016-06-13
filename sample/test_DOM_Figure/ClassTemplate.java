package figure;


import modeler.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.Point;

import javax.swing.*;
/** �� Ŭ������ UML ǥ��� �߿��� Ŭ���� ��ü�� ǥ���ϱ� ���� ���̴�.
 * �� ��ü�� �⺻������ �簢�� ����� �ϰ������� �μ� ��ü�� 2���� ���а�
 * 3���� �ؽ�Ʈ ��ü�� �����Ѵ�.
 */
public final class ClassTemplate extends Box 
										implements ClassLike 
{ 
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 1682354401448728381L;
	/** �� Ŭ������ template �ΰ��� ��Ÿ���� flag
	 */
	private boolean _templateFlag;
	/** �� Ŭ������ template�� ��� template�� ���� ���ڿ�
	 */
	private char[] _tagString;
	/** �� ������ ����
	 */
	private int _deltaV;
	private boolean _fixedSizeFlag;
	/** �� Ŭ������ resize �����ϱ� ���� _x2 ��
	 */
	private int _savedx2;
	/** �� Ŭ������ resize �����ϱ� ���� _y2 ��
	 */
	private int _savedy2;
	/** ù��° ��輱
	 */
	private Line _separator1;
	/** �ι�° ��輱
	 */
	private Line _separator2;
	/** Ŭ���� �̸� �κ��� ��Ÿ���� �ؽ�Ʈ ��ü
	 */
	private ClassText _classname;
	/** ����Ÿ ��� �κ��� ��Ÿ���� �ؽ�Ʈ ��ü
	 */
	private ClassText _vars;
	/** ��� �Լ� �κ��� ��Ÿ���� �ؽ�Ʈ ��ü
	 */
	private ClassText _methods;
	/** �� Ŭ������ ����Ǿ��ִ� ������� �����ϴ� ����Ʈ ��ü
	 */
	private AnyTionList _anytionlist;
	/** �� Ŭ������ ��ũ�� Ŭ������ ��� �� Ŭ������ ����� ���迡 ���� ���۷���
	 */
	private AssTion _linkAssTion;
	/** �� Ŭ�������� ���� Ȱ��ȭ ������ �ؽ�Ʈ ��ü�� ���� ���۷���
	 */
	private transient Figure _focus;
	/** �� Ŭ������ �̵��� �Բ� ����鵵 �̵��Ǿ�� �� �� �����Ǵ� ���� ��
	 */
	private transient boolean _moveComplexFlag;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� x ����
	 */
	private static int _GapX = 6;
	/** �ؽ�Ʈ ��ü�� �簢������ ���� �������ִ� y ����
	 */
	private static int _GapY = 4;
	/** �� ���� �ּ� ���� (���� ����)
	 */
	private static int _initWidth = 80;
	private static int _defaultMaxWidth = 160;


	 /** ��ũ ���� ���� �Լ�
	  */
	public void setLinkAssTion(AssTion aTion) {
		_linkAssTion = aTion;
	}
	/** �� Ŭ������ ������ ����ִ°��� ���� �Լ�
	 */
	public boolean emptyClass() {
		if (_vars.hasContent() == true) return false;
		if (_methods.hasContent() == true) return false;
		return true;
	}
	/** �� ��ü�� �������� ���ο� ��ġ�� �ٲٴ� �Լ�
	 */
	public void changeOriginPoint(int newX,int newY) {
		int oldX = _x1;
		int oldY = _y1;
		int deltaX = newX - oldX;
		int deltaY = newY - oldY;
		super.moveCoord(deltaX,deltaY);
		_separator1.moveCoord(deltaX,deltaY);
		_separator2.moveCoord(deltaX,deltaY);
		_classname.moveCoord(deltaX,deltaY);
		_vars.moveCoord(deltaX,deltaY);
		_methods.moveCoord(deltaX,deltaY);
	}
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_separator1.setController(ptr);
		_separator2.setController(ptr);
		_classname.setController(ptr);
		_vars.setController(ptr);
		_methods.setController(ptr);
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) 
	{
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.classPopup());
		_classname.doSomethingAfterLoad(controller);
		_vars.doSomethingAfterLoad(controller);
		_methods.doSomethingAfterLoad(controller);		
	}
	/** �޴� ���ÿ� ���� ���ο� Ŭ������ ����� �ݹ� �Լ��̴�.
	 */
	public static void makeNewClass(GraphicController controller,int popupX,int popupY,int fontSizeV,Popup popupPtr) {
		ClassTemplate aclass = 
							  new ClassTemplate(controller,popupX,popupY,fontSizeV,popupPtr);
												
		controller.startEditableObject(aclass,aclass._classname);
	}
	/** �������̴�. �Ϲ������� ���� ���ȴ�.
	 */
	public ClassTemplate(GraphicController controller,int x1,int y1,int deltaV,Popup popup) 
	{
		super(controller,x1,y1,
			  x1+_initWidth+2*_GapX,
			  y1+3*(deltaV+2*_GapY),popup);

		_fixedSizeFlag = false;
		_templateFlag = false;
		_tagString = null;
		_savedx2 = 0;
		_savedy2 = 0;
		_deltaV = deltaV;
		_stackingFlag = true;
		_moveComplexFlag = false;

		// open part
		int width = _initWidth + 2*_GapX;
		int height = _deltaV + 2*_GapY;
		_separator1 = new Line(controller,x1,y1+height,x1+width,y1+height,null,Const.STRAIGHT);
		_separator2 = new Line(controller,x1,y1+2*height,x1+width,y1+2*height,null,Const.STRAIGHT);
		_classname = new ClassText(controller,this,ClassText.NAMEFIELD,
								   x1+_GapX,y1+_GapY,null);
								   
		_vars = new ClassText(controller,this,ClassText.VARFIELD,
							  x1+_GapX,y1+3*_GapY+_deltaV,controller.memberPopup());
		_methods = new ClassText(controller,this,ClassText.METHODFIELD,
							 x1+_GapX,y1+5*_GapY+2*_deltaV,controller.memberPopup());
								   
		_anytionlist = new AnyTionList();

		_focus = (Figure)this;
	}
	/** �������̴�. ����ÿ� �ַ� ȣ��ȴ�.
	 */
	ClassTemplate(GraphicController controller,int x1,int y1,int x2,int y2,int deltaV,Popup popup) {
		super(controller,x1,y1,x2,y2,popup);

		_anytionlist = new AnyTionList();

		_fixedSizeFlag = false;
		_templateFlag = false;
		_tagString = null;
		_stackingFlag = true;
		_moveComplexFlag = false;
		_focus = (Figure)this;
		_savedx2 = 0;
		_savedy2 = 0;
		_deltaV = deltaV;
	}
	/** ���ڿ��� �־��� ũ�� ��ŭ Ŭ������ ���̸� �����Ѵ�. ������ ������ ���ڴ����̴�.
	 */
	public void modifyHeight(int field,int dy) {
		_controller.clear(this,false);
		int deltay;
		deltay = dy * _deltaV;
		if (doYouHaveAnyTion() == false && _fixedSizeFlag == false) {
			_y2 = _y2 + deltay;
		}
		if ( dy > 0 ) {
			if ( field == ClassText.VARFIELD) {
				_separator2._y1 = _separator2._y1 + deltay;
				_separator2._y2 = _separator2._y2 + deltay;

				_methods._y1 = _methods._y1 + deltay;
				_methods._y2 = _methods._y2 + deltay;
			}
			_controller.draw(this);
		} else {
			if (field == ClassText.VARFIELD) {
				_separator2._y1 = _separator2._y1 + deltay;
				_separator2._y2 = _separator2._y2 + deltay;

				_methods._y1 = _methods._y1 + deltay;
				_methods._y2 = _methods._y2 + deltay;
			}
			_controller.draw(this);
		}
	}
	/** ������ Ŭ������ �� ��ü�� ������ ���ԵǴ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasAncestor(ClassLike tocheck) {
		if (this == tocheck) return true;
		String thisname = this.getName();
		String tocheckname = tocheck.getName();
		return false;
	}
	/** ���� ��ü�� ����Ű�� �����͸� ���ο� ������ �����Ѵ�.
	 */
	public boolean replaceAnytionPtr(AnyTion from,AnyTion to) {
		boolean	replaced = _anytionlist.replacePtr(from,to);
		return replaced;
	}
	/** ������� �������� Ŭ���� ��ü�� ���ִ� �Լ��̴�.
	 */
	private void destroyBackup() {
		ClassTemplate backup = (ClassTemplate)BackUp;
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
			ClassLike clss = tmp.classes().getFirst();
			while (clss != null) {
				if (clss != this) {
					clss.replaceAnytionPtr(tmp,(AnyTion)tmp.borned());
				}
				clss = tmp.classes().getNext();
			}
			tmp.classes().clear();
			tmp.delete();
			tmp = _anytionlist.getNext();
		}
		_anytionlist.clear();
		ClassTemplate backup = (ClassTemplate)BackUp;
		tmp = backup._anytionlist.getFirst();
		while(tmp != null) {
			tmp.resetMarkForMove();
			_controller.insert(tmp);
			_controller.lowlight(tmp);
			tmp = backup._anytionlist.getNext();
		}
		_controller.setCurrentFocus(BackUp);
		BackUp = this;
		_controller.beep("ClassTemplate.undoComplexMove");
		return;
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
	public void delete() 
	{
		_separator1.delete(); _separator1 = null;
		_separator2.delete(); _separator2 = null;
		_classname.delete(); _classname = null;
		_vars.delete(); _vars = null;
		_methods.delete(); _methods = null;
		if (_moveComplexFlag) 
		{
			_anytionlist.delete(); _anytionlist = null;
			return;
		}
		
		AnyTion ptr = _anytionlist.getFirst();
		while (ptr != null) 
		{
			ptr.removeFromClasses(this);
			_anytionlist.getNext();
		}
		_anytionlist.delete(); _anytionlist = null;
	    if (_linkAssTion != null) _linkAssTion.resetLinkSymbol();
		_linkAssTion = null;
		_focus = null;
	}
	/** �� Ŭ������ ����� ���谡 �ִ°��� Ȯ���ϴ� �Լ��̴�.
	 */	
	public boolean doYouHaveAnyTion() {
		if (_anytionlist.empty() == false) return true;
		return false;
	}
	/** �� Ŭ������ �̸��� ������ �ִ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasname() {
		return _classname.hasContent();
	}
	/** �� Ŭ������ �̸��� ã�ƿ��� �Լ��̴�.
	 */
	public String getName() {
		return _classname.content().getName();
	}
	/** �� Ŭ������ ��ü �̸��� ã�ƿ��� �Լ��̴�.
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
		return Const.IAMCLASSTEMPLATE;
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		if (_templateFlag == true) {
			clearTemplateTag();	
		}
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_separator1.setInCanvas(flag);
		_separator2.setInCanvas(flag);
		_classname.setInCanvas(flag);
		_vars.setInCanvas(flag);
		_methods.setInCanvas(flag);
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) 
	{
		_popup = controller.classPopup();	
	}
	/** �� ��ü�� ���� �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		/*
		if (_minimized) {
			((ClassPopup)_popup).sizeB.setText("Maximize");
		} else {
			((ClassPopup)_popup).sizeB.setText("Minimize");
		}
		*/
		if (_templateFlag) {
			((ClassPopup)_popup).templateB.setText("As Class");
		} else {
			((ClassPopup)_popup).templateB.setText("As Template");
		}
		_popup.popup(event);
	}
	/** �� Ŭ������ ���ø�Ʈ�� ��ȯ��Ű�� �Լ��̴�.
	 */
	public void asTemplate() {
		_controller.lowlight((Figure)this);
		if (_templateFlag == true) {
			_controller.clear(this,true);
			_templateFlag = false;
			_tagString = null;
			_controller.draw(this);
			return;
		}
		String ans = JOptionPane.showInputDialog(_controller.frame(),"Type template tag:","Question",JOptionPane.QUESTION_MESSAGE);
		if (ans == null) return;
		if (ans.length() == 0) return;
		_tagString = MySys.toCharArray(ans);
		if (MySys.emptyString(_tagString) == true) return;
		_templateFlag = true;
		_controller.draw(this);
	}
	/** �� Ŭ������ �ּ�ȭ Ȥ�� �ִ�ȭ ��Ŵ���ν� ũ�⸦ ��ȭ��Ű�� �Լ��̴�.
	 */
	public void localDoSize(boolean insertflag) {
		/*
		if (insertflag) {
			// testing anytions if they can be correctly redrawed
			if (_minimized) {
				int dx = _savedx2 - _x2;
				int dy = _savedy2 - _y2;
				if (modifyCheckXy(dx,dy) == false) {
					AnyTion anytion = _anytionlist.getFirst();
					while(anytion != null) {
						_controller.lowlight(anytion);
						anytion = _anytionlist.getNext();
					}
					_controller.lowlight((Figure)this);
					_controller.beep("ClassTemplate.localDoSize");
					return;
				}
			}
		}
		if (_minimized) {
			_minimized = false;
			_x2 = _savedx2;
			_y2 = _savedy2;
			makeRegion();
			_controller.clear(this,false);
			_controller.draw(this);
			_classname.centering(_x1,_x2,true);
			if (insertflag) {
				AnyTion tmp = _anytionlist.getFirst();
				while (tmp != null) {
					tmp.tailorEndLinesForEditing(this,0);
					tmp = _anytionlist.getNext();
				}
				farewell();
			}
			return;
		}
		_controller.lowlight((Figure)this);
		_controller.checkInlist(this);
		_controller.clear(this,true);

		makeRegion();
		_savedx2 = _x2;
		_savedy2 = _y2;
		_minimized = true;
		int newwidth = minimizedWidth();
		_x2 = _x1 + newwidth;
		_y2 = _separator1._y2;
		makeRegion();
		_controller.clear(this,true);
		_controller.draw(this);
		_classname.centering(_x1,_x2,true);
		if (insertflag) {
			AnyTion tmp = _anytionlist.getFirst();
			while (tmp != null) {
				tmp.extendToConFigure(this);
				tmp = _anytionlist.getNext();
			}
			farewell();
		}
		*/
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		Rectangle clipR = g.getClipBounds();
		g.setClip(_x1,_y1,_x2-_x1,_y2-_y1);
		_separator1.draw(g,Const.RUBBERBANDING,null);
		_separator2.draw(g,Const.RUBBERBANDING,null);
		g.setClip(clipR);
		super.move(g,dx,dy);
		_separator1.moveCoord(dx,dy);
		_separator2.moveCoord(dx,dy);
		g.setClip(_x1,_y1,_x2-_x1,_y2-_y1);
		_separator1.draw(g,Const.RUBBERBANDING,null);
		_separator2.draw(g,Const.RUBBERBANDING,null);
		g.setClip(clipR);
		_classname.move(g,dx,dy);
		_vars.move(g,dx,dy);
		_methods.move(g,dx,dy);
		if (_moveComplexFlag == false) return;
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.followClass(g,dx,dy);
			tmp = _anytionlist.getNext();
		}
	}
	/** ��ǥ�� �̵� ��Ű�� �Լ��̴�.
	 */
	public void moveCoord(int dx,int dy) {
		super.moveCoord(dx,dy);
		_separator1.moveCoord(dx,dy);
		_separator2.moveCoord(dx,dy);
		_classname.moveCoord(dx,dy);
		_vars.moveCoord(dx,dy);
		_methods.moveCoord(dx,dy);
		if (_moveComplexFlag == false) return;
		AnyTion tmp = _anytionlist.getFirst();
		while(tmp != null) {
			tmp.followClass(dx,dy);
			tmp = _anytionlist.getNext();
		}
	}
	public void setX2AndCentering() {
		if (_fixedSizeFlag == true) {
			int realWidth = _classname.getRealWidth();
			int w = _x2 - _x1;
			if (realWidth < w) {
				int dx = (w - realWidth)/2;
				_classname.moveCoord(dx,0);
			}
			return;
		}
		int realWidth = _classname.getRealWidth();
		if (realWidth > _defaultMaxWidth) {
			realWidth = _defaultMaxWidth;
		}
		if (realWidth < _initWidth) {
			int dx = (_initWidth - realWidth)/2;
			_classname.moveCoord(dx,0);
			realWidth = _initWidth;
		}
		_x2 = _x1 + realWidth + 2*_GapX;

		_separator1._x2 = _x2;
		_separator2._x2 = _x2;
	}
	/** �� ��ü�� ���� �ؽ�Ʈ ���� �۾��� ���۵����ϴ� �Լ��̴�.
	 */
	public void localStartEdit(int popupX,int popupY) {
		_controller.clear(this,false);
		_controller._editingTag = true;
		_controller.setCurrentFocus(this);
		ClassText field;
		field = _classname;
		field = whichfield(popupX,popupY);
		
		// uncentering
		int dx = _classname._x1 - _x1 - _GapX;
		_classname.moveCoord(-dx,0);
		_controller.draw(this);

		field.startEdit();
		_focus = (Figure)field;
	}
	/** ���ڿ� �־��� �˾� ��ǥ ���� Ŭ���� ���� �κп��� ��� �ؽ�Ʈ�� ���ϴ°��� ã�Ƴ��� �Լ��̴�.
	 */
	private ClassText whichfield(int popx,int popy) {
		int x,y,w,h;
		x = _x1;
		y = _y1;
		w = _x2 - _x1;
		h = _separator1._y1 - _y1;
		CRgn temp = CRgn.makeRectRegion(x,y,w,h);
		if (CRgn.myPtInRgn(temp,popx,popy)) {
			CRgn.myDestroyRgn(temp);
			return _classname;
		}
		CRgn.myDestroyRgn(temp);
		y = y + h;
		h = _separator2._y1 - _separator1._y1;
		temp = CRgn.makeRectRegion(x,y,w,h);
		if (CRgn.myPtInRgn(temp,popx,popy)) {
			CRgn.myDestroyRgn(temp);
			return _vars;
		}
		CRgn.myDestroyRgn(temp);
		return _methods;
	}
	/** �� Ŭ������ ���ø�Ʈ Ŭ������ ��� ���ø�Ʈ �μ� �κ� �׸� ������ ����� �Լ��̴�.
	 */
	private void clearTemplateTag() {
		/*
		int x1 = _x1 + (_x2-_x1)*2/3;
		int y1 = _y1 - _deltaV/2 - 4;
		Graphics eg = _controller.getgraphics();
		eg.setColor(_controller.getBackground());
		int len = MySys.strlen(_tagString,0);
		int w = len*_deltaH + 4;
		int h = _deltaV + 2;
		eg.fillRect(x1,y1,w+1,h+1);
		eg.dispose();
		*/
	}
	/** �� Ŭ������ ���ø�Ʈ Ŭ������ ��� ���ø�Ʈ �μ� �κ��� �׸��� �Լ��̴�.
	 */
	private void drawTemplateTag(Graphics g) {
		/*
		int x1 = _x1 + (_x2-_x1)*2/3;
		int fontH = _deltaV;
		int y1 = _y1 - fontH/2 - 4;
		Graphics eg = g.create();
		Rectangle clipR = g.getClipBounds();
		Rectangle newClipR = new Rectangle(clipR);
		eg.setClip(newClipR);
		eg.setColor(_controller.getBackground());
		eg.setFont(_controller.font());
		int len = MySys.strlen(_tagString,0);
		int w = len*_deltaH + 4;
		int h = fontH + 4;
		eg.fillRect(x1,y1,w,h);
		eg.dispose();
		int x2 = x1 + w;
		int y2 = y1 + h;
			
		_controller.drawString(g,x1+2,y1+_deltaV-2,_tagString,0,len);
		
		MySys.myDrawDashedLine(g,x1,y1,x2,y1);
		MySys.myDrawDashedLine(g,x2,y1,x2,y2);
		MySys.myDrawDashedLine(g,x1,y1,x1,y2);
		MySys.myDrawDashedLine(g,x1,y2,x2,y2);
		*/
	}
	/** Ŭ������ ��ǥ ���� �ؽ�Ʈ ������ �̿��Ͽ� �ٽ� ����ϴ� �Լ��̴�.
	 * �� �Լ��� �� Ŭ������ ������ �ٲ���� �� �̸� �����ϴ� ���� �̸��� �ٸ� Ŭ����
	 * �׸��� �ٽ� �׷��ֱ� ���� ���ȴ�.
	 */
	public void recalcCoordUsingContent(Graphics g,Graphics specialgc) {
		/*
		if (_controller._editingTag == true && _controller.currentFocus() != this) return;
		int sx2 = _x2; int sy2 = _y2;
		if (_minimized) {
			int nameWidth = _classname.width();
			int nameHeight = _classname.height();
			int maxWidth = nameWidth;
			if (maxWidth < _NofCharsinLine-1) maxWidth = _NofCharsinLine-1;
			int x = _x1 + (int)(_controller.xFactor()*(_GapX+_deltaH));
			int y = _y1 + (int)(_controller.yFactor()*_GapY);
			_classname.recalcCoordFromXy1(x,y);
			_x2 = _x1 + (int)(_controller.xFactor()*(_deltaH*maxWidth + 2*_GapX + 2*_deltaH));
			_y2 = _y1 + (int)(_controller.yFactor()*(_deltaV*(nameHeight) + 2*_GapY));
			if (_controller._editingTag == false) _classname.centering(g,specialgc,_x1,_x2,true);
		} else {
			int nameWidth = _classname.width();
			int nameHeight = _classname.height();
			int varsWidth = _vars.width();
			int varsHeight = _vars.height();
			int methodWidth = _methods.width();
			int methodHeight = _methods.height();
			int maxWidth = MySys.max(nameWidth,varsWidth,methodWidth); 
			if (maxWidth < _NofCharsinLine-1) maxWidth = _NofCharsinLine-1;
			int x = _x1 + (int)(_controller.xFactor()*(_GapX+_deltaH));
			int y = _y1 + (int)(_controller.yFactor()*_GapY);
			_classname.recalcCoordFromXy1(x,y);
			_x2 = _x1 + (int)(_controller.xFactor()*(_deltaH*maxWidth + 2*_GapX + 2*_deltaH));
			_y2 = _y1 + (int)(_controller.yFactor()*(_deltaV*(nameHeight+varsHeight+methodHeight) + 6*_GapY));
			if (_controller._editingTag == false) _classname.centering(g,specialgc,_x1,_x2,true);
			_separator1._x1 = _x1;
			_separator1._x2 = _x2;
			_separator1._y1 = _y1 + (int)(_controller.yFactor()*(_deltaV*(nameHeight) + 2*_GapY));
			_separator1._y2 = _separator1._y1;
			y = _y1 + (int)(_controller.yFactor()*(_deltaV*(nameHeight) + 3*_GapY));
			_vars.recalcCoordFromXy1(x,y);
			_separator2._x1 = _x1;
			_separator2._x2 = _x2;
			_separator2._y1 = _y1 + (int)(_controller.yFactor()*(_deltaV*(nameHeight+varsHeight) + 4*_GapY));
			_separator2._y2 = _separator2._y1;
			y = _y1 + (int)(_controller.yFactor()*(_deltaV*(nameHeight+varsHeight) + 5*_GapY));
			_methods.recalcCoordFromXy1(x,y);
		}
		makeRegion();
		if (doYouHaveAnyTion() == false) return;
		if (sx2 == _x2 && sy2 == _y2) return;
		FigureList figs = _controller.figures();
		AnyTion tmp = _anytionlist.getFirst();
		while (tmp != null) {
			tmp.extendToConFigure(this);
			tmp = _anytionlist.getNext();
		}
		makeRegion();
		tmp = _anytionlist.getFirst();
		while (tmp != null) {
			boolean strange = tmp.tailorEndLinesAll();
			if (strange == true) {
				_anytionlist.remove(tmp);
				figs.remove(tmp);
				tmp.delete();
				tmp = _anytionlist.getFirst();
			} else {
				tmp = _anytionlist.getNext();
			}
		}
		tmp = _anytionlist.getFirst();
		while (tmp != null) {
			boolean strange = tmp.setModelSpecificSymbolAll();
			if (strange == true) {
				_anytionlist.remove(tmp);
				figs.remove(tmp);
				tmp.delete();
				tmp = _anytionlist.getFirst();
			} else {
				tmp = _anytionlist.getNext();
			}
		}
		tmp = _anytionlist.getFirst();
		while (tmp != null) {
			tmp.remake();
			_controller.clear(tmp,false);
			_controller.draw(tmp);
			tmp = _anytionlist.getNext();
		}
		*/
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int style,Graphics specialgc) {
		if (!_inCanvas) {
			return;
		}
		if (style == Const.DRAWING && _controller.printFlag == false) {
			recalcCoordUsingContent(g,specialgc);
		}
		super.draw(g,style,specialgc);
		if (style != Const.RUBBERBANDING && _templateFlag == true) {
			drawTemplateTag(g);
		}

		if (_controller._editingTag == true && 
			_controller.currentFocus() == this) {
			_separator1.draw(g,style,specialgc);
			_separator2.draw(g,style,specialgc);
			_classname.draw(g,style,specialgc);
			_vars.draw(g,style,specialgc);
			_methods.draw(g,style,specialgc);
		} else if (style == Const.DRAWING) {
			Rectangle clipR = g.getClipBounds();
			Rectangle newClipR = clipR.intersection(new Rectangle(_x1,_y1,_x2-_x1,_y2-_y1));
			g.setClip(newClipR);
			_separator1.draw(g,style,specialgc);
			_separator2.draw(g,style,specialgc);
			_classname.draw(g,style,specialgc);
			_vars.draw(g,style,specialgc);
			_methods.draw(g,style,specialgc);
			g.setClip(clipR);
		} else if (style == Const.RUBBERBANDING) {
			Rectangle clipR = g.getClipBounds();
			Rectangle newClipR = clipR.intersection(new Rectangle(_x1,_y1,_x2-_x1,_y2-_y1));
			g.setClip(newClipR);
			_separator1.draw(g,style,specialgc);
			_separator2.draw(g,style,specialgc);
			g.setClip(clipR);
		}
		if (_moveComplexFlag == false || style != Const.RUBBERBANDING) return;
		if (_controller._editingTag == true /*&& _minimizedOrg == true*/) return;
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
		ClassTemplate copied;
		if (ptr == null) {
			copied = new ClassTemplate(_controller,0,0,0,0,0,null);
		} else {
			copied = (ClassTemplate)ptr;
		}
		copied._popup = _popup;
		copied._controller = _controller;
		copied._stackingFlag = _stackingFlag;
		copied._focus = copied;
		copied._deltaV = _deltaV;
		copied._fixedSizeFlag = _fixedSizeFlag;
		copied._classname = (ClassText)_classname.born(null);
		copied._vars = (ClassText)_vars.born(null);
		copied._methods = (ClassText)_methods.born(null);
		copied._classname.setClassPtr(copied);
		copied._vars.setClassPtr(copied);
		copied._methods.setClassPtr(copied);
		copied._separator1 = (Line)_separator1.born(null);
		copied._separator2 = (Line)_separator2.born(null);
		return(super.born((Figure)copied));
	}
	/** ����Ÿ ��� _focus�� ���� �б� access �Լ�
	 */
	public Figure focus() {
		return _focus;
	}
	/** �� Ŭ������ ���� ���̸� �ٽ� ����Ѵ�. Ư�� ���� ���� �ּ�ȭ �Ǿ� �ִ� ��쿡
	 * ���� ũ�⸦ ȸ�� ��Ű�� �Լ��̴�.
	 */
	public void recalcWidthHeight() {
		/*
		if (_minimizedOrg) {
			_minimizedOrg = false;
			_controller.lowlight((Figure)this);
			_controller.checkInlist(this);
			_controller.clear(this,true);
			AnyTion anytion = _anytionlist.getFirst();
			while(anytion != null) {
				anytion = _anytionlist.getNext();
			}
			makeRegion();
			_savedx2 = _x2;
			_savedy2 = _y2;
			_minimized = true;
			int newwidth = minimizedWidth();
			_x2 = _x1 + newwidth;
			_y2 = _separator1._y2;
			makeRegion();
			_controller.clear(this,true);
			_controller.draw(this);
			_classname.centering(_x1,_x2,true);
			anytion = _anytionlist.getFirst();
			while(anytion != null) {
				anytion.extendToClass(this);
				anytion = _anytionlist.getNext();
			}
			boolean okflag = true;
			boolean success;
			anytion = _anytionlist.getFirst();
			while(anytion != null) {
				success = anytion.checkConsistency();
				if (success == false) okflag = false;
				_controller.draw(anytion);
				anytion = _anytionlist.getNext();
			}
			if (okflag == false) {
				_controller.beep("ClassTemplate.recalcWidthHeight");
			}
		}
		*/
	}
	/** ����Ÿ ��� _classname �� ���� �б� access �Լ��̴�.
	 */
	public ClassText classname() {
		return _classname;
	}
	/** ����Ÿ ��� _vars �� ���� �б� access �Լ��̴�.
	 */
	public ClassText vars() {
		return _vars;
	}
	/** ����Ÿ ��� _methods �� ���� �б� access �Լ��̴�.
	 */
	public ClassText methods() {
		return _methods;
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
	 */
	public boolean onEnter(int x,int y) {
		if (super.onEnter(x,y)) {
			_focus = (Figure)this;
			return true;
		} else {
			_focus = (Figure)this;
			return false;
		}
	}
	/** �� Ŭ������ ����� ��ũ �ɺ��� ����� �Լ��̴�.
	 */
	private void eraseLinkSymbol() {
		Figure moreTemp = _controller.figures().getFirst();
		while (moreTemp != null) {
			if (moreTemp.whoAreYou().isEqual(Const.IAMASSTION) == true) {
				AssTion aTion = ((AssTion)moreTemp);
				if (aTion.linkedClass() == this) {
					Graphics g = _controller.getgraphics();
					aTion.drawLinkSymbol(g,false);
					g.dispose();
				}
			}
			moreTemp = _controller.figures().getNext();
		}
	}
	/** �� Ŭ������ �̵��ϱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
	 */
	public boolean moveProlog(int oneorsome) {
		eraseLinkSymbol();
		/*
		Figure moreTemp = _controller.figures().getFirst();
		while (moreTemp != null) {
			if (moreTemp.whoAreYou().isEqual(Const.IAMASSTION) == true) {
				Graphics g = _controller.getgraphics();
				((AssTion)moreTemp).drawLinkSymbol(g,false);
				g.dispose();
			}
			moreTemp = _controller.figures().getNext();
		}
		*/
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
		ClassTemplate backup = (ClassTemplate)BackUp;
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
	public void resizeProlog(boolean makeBackUp) {
		_savedx2 = _x2;
		_savedy2 = _y2;
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
	/** Ŭ���� ��ü�� �̵��̳� ������ ���� ���Ŀ� ȣ��Ǵ� �Լ��μ� �ϰ��� �˻�,
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
	public boolean stopDraw() {
		if (_x1 > _x2 - 40 || _y1 > _y2 - _deltaV) {
			_x2 = _savedx2;
			_y2 = _savedy2;	
			_controller.beep();
			return false;
		}	
		int realWidth = _classname.getRealWidth();
		int w = _x2 - _x1;
		if (realWidth < w) {
			int dx = (w - realWidth)/2;
			_classname._x1 = _x1 + dx;
			_classname.setX2();
		}
		_fixedSizeFlag = true;
		_separator1._x2 = _x2;
		_separator2._x2 = _x2;
		makeRegion();
		AnyTion tmp = _anytionlist.getFirst();
		while (tmp != null) {
			tmp.extendToConFigure(this);
			tmp = _anytionlist.getNext();
		}
		tmp = _anytionlist.getFirst();
		while (tmp != null) {
			tmp.checkConsistency();
			tmp = _anytionlist.getNext();
		}
		tmp = _anytionlist.getFirst();
		while (tmp != null) {
			tmp.remake();
			_controller.clear(tmp,false);
			_controller.draw(tmp);
			tmp = _anytionlist.getNext();
		}
		return false;
	}
	/** �� Ŭ������ ����Ǿ� �ִ� ������� �����ϴ� �Լ��̴�.
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
	/** ���ڿ� �־��� Ŭ������ �̹� �ڽ� Ŭ������ �����ִ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasChildAlready(ClassTemplate tocheck) {
		AnyTion ptr = _anytionlist.getFirst();
		while (ptr != null) {
			if (ptr.classes().inlistForName(tocheck)) return true;
			ptr = _anytionlist.getNext();
		}
		return false;
	}
	/** �� Ŭ������ �����ϰ����ϴ� ������ Ŭ������ ����� ���� ���˵Ǵ°��� ã�� �Լ��̴�.
	 */
	public int whereami(int x,int y,Line aline) {
		int x1 = _x1;
		int y1 = _y1;
		int x2 = _x2;
		int y2 = _y2;
		if (x == x1 && y == y1) {
			if (aline.orient() == Const.NORTH) {
				return Const.NORTH;
			} else if (aline.orient() == Const.EAST) {
				return Const.WEST;
			} else {	
				return Const.UNDEFINED;
			}
		} else if (x == x2 && y == y1) {
			if (aline.orient() == Const.NORTH) {
				return Const.NORTH;
			} else if (aline.orient() == Const.EAST) {
				return Const.EAST;
			} else {	
				return Const.UNDEFINED;
			}
		} else if (x == x2 && y == y2) {
			if (aline.orient() == Const.NORTH) {
				return Const.SOUTH;
			} else if (aline.orient() == Const.EAST) {
				return Const.EAST;
			} else {	
				return Const.UNDEFINED;
			}
		} else if (x == x1 && y == y2) {
			if (aline.orient() == Const.NORTH) {
				return Const.SOUTH;
			} else if (aline.orient() == Const.EAST) {
				return Const.WEST;
			} else {	
				return Const.UNDEFINED;
			}
		}
		if (x == x1) {
			return Const.WEST;
		} else if (x == x2) {
			return Const.EAST;
		} else if (y == y1) {
			return Const.NORTH;
		} else if (y == y2) {
			return Const.SOUTH;
		} 
		if (x >= x1 && x <= x2) {
			if (y <= y1) return Const.NORTH;
			if (y >= y2) return Const.SOUTH;
		}
		if (y >= y1 && y <= y2) {
			if (x <= x1) return Const.WEST;
			if (x >= x2) return Const.EAST;
		}
		return Const.UNDEFINED;
	}
	/** Ŭ���� ���� �߿� ���ڿ� ���� ��õ� ����� Ư�� ������ �����ϴ� �Լ��̴�.
	 */
	public TextLine removeLineAt(int whichMember,int pos) 
	{
		if (whichMember == ClassText.VARFIELD) {
			return _vars.removeLineAt(pos);
		} else if (whichMember == ClassText.METHODFIELD) {
			return _methods.removeLineAt(pos);
		} else { 
			return null;
		}
	}
	/** ���ڿ� ���� ��õ� ����� Ư�� ��ġ(���콺�� ��ġ)�� ���ο� ������ 
	 * �߰��ϴ� �Լ��̴�. 
	 */
	public void insertLineAt(TextLine newLine,int whichMember,CPoint point) {
		int x = _separator1._x1;
		int y = _separator1._y1;
		int w = _separator1._x2 - _separator1._x1;
		int h = _separator2._y1 - _separator1._y1;
		Rectangle varBox = new Rectangle(x,y,w,h);
		if (varBox.contains(point.x,point.y)) {
			if (whichMember == ClassText.VARFIELD) {
				int yPoint = y + _deltaV + _GapY;
				int i = 0;
				while (yPoint < y+h) {
					if (point.y < yPoint) {
						break;
					}
					yPoint = yPoint + _deltaV;
					i++;
				}
				if (i > _vars.height()-1) {
					_vars.insertLineAtLast(newLine);
				} else {
					_vars.insertLineAt(i,newLine);
				}
			} else {
				_methods.insertLineAtLast(newLine);
			}
			return;
		}
		x = _separator2._x1;
		y = _separator2._y1;
		w = _separator2._x2 - _separator2._x1;
		h = _y2 - _separator2._y1;
		Rectangle methodBox = new Rectangle(x,y,w,h);
		if (methodBox.contains(point.x,point.y)) {
			if (whichMember == ClassText.METHODFIELD) {
				int yPoint = y + _deltaV + _GapY;
				int i = 0;
				while (yPoint < y+h) {
					if (point.y < yPoint) {
						break;
					}
					yPoint = yPoint + _deltaV;
					i++;
				}
				if (i > _methods.height()-1) {
					_methods.insertLineAtLast(newLine);
				} else {
					_methods.insertLineAt(i,newLine);
				}
			} else {
				_vars.insertLineAtLast(newLine);
			}
			return;
		}
		if (whichMember == ClassText.VARFIELD) {
			_vars.insertLineAtLast(newLine);
		} else {
			_methods.insertLineAtLast(newLine);
		}
	}
	/** �� Ŭ������ �̸� ������ ���� �����ϴ� �Լ��̴�.
	 */
	public void setNameString(String aString)
	{
		TextLine newLine = new TextLine();
		newLine.setString(aString);
		newLine.dataMemberFlag = false;
		_classname.insertLineAtLast(newLine);
		_classname.content().doParse(ClassText.NAMEFIELD);
	}		
	/** ���ο� ����Ÿ ����� �߰��Ѵ�.
	 */
	public void addMemberFunctionString(String aString) {
		TextLine newLine = new TextLine();
		newLine.setString(aString);
		newLine.dataMemberFlag = false;
		_methods.insertLineAtLast(newLine);
		_methods.content().doParse(ClassText.METHODFIELD);
	}
	/** ���ο� ����Ÿ ����� �߰��Ѵ�.
	 */
	public void addDataMemberString(String aString) {
		TextLine newLine = new TextLine();
		newLine.setString(aString);
		newLine.dataMemberFlag = true;
		_vars.insertLineAtLast(newLine);
		_vars.content().doParse(ClassText.VARFIELD);
	}
	/** Ŭ������ �̸��� �������� �ʴ� ��� �⺻ �̸��� �ο��Ѵ�.
	 */
	public void setDummyName() {
		_classname.setDummyName();
	}
	/** ȭ���� �ε��� �� Ŭ���� ������ ShadowClasses ����Ʈ�� ����ϴ� �Լ��̴�.
	 */
	public void registerClassContentWhenLoading(String packName) {
		String className = this.getName();
/*		ClassContent aClassContent = GraphicController.ShadowClasses.classContentFor(className);
*/
		ClassContent aClassContent = _controller.getShadowClasses().classContentFor(className);
		if (aClassContent != null) return;
		TextContent nameContent = _classname.content();
		TextContent varContent = _vars.content();
		TextContent methodContent = _methods.content();
		ClassContent newClassContent = new ClassContent(nameContent,varContent,methodContent);
		newClassContent.setPackageName(packName);
/*		GraphicController.ShadowClasses.insert(newClassContent);
*/
		_controller.getShadowClasses().insert(newClassContent);
	}
	/** Ŭ������ ������ ������ �� Ŭ���� ������ ShadowClasses ����Ʈ�� ����ϴ� �Լ��̴�.
	 */
	public void registerClassContent() {
		String className = this.getName();
		ClassContent aClassContent = _controller.classContentFor(className);
		if (aClassContent != null) return;
		
		String packName = new String("");
		if(_controller.frame() instanceof Modeler) {
			Modeler mFrame = (Modeler)_controller.frame();
			packName = mFrame.packageName;
		} else {
			return;
		}
			
		TextContent nameContent = _classname.content();
		TextContent varContent = _vars.content();
		TextContent methodContent = _methods.content();
		ClassContent newClassContent = new ClassContent(nameContent,varContent,methodContent);
		newClassContent.setPackageName(packName);
		_controller.insertClassContent(newClassContent);
	}
	/** �� Ŭ������ ��� ������ ���� Ŭ������ �������� ���� ���� �����ϴ� �Լ��̴�.
	 * �� Ŭ������ ���� �̸��� �ٸ� Ŭ���� ������ ����Ǿ��� �� �� �Լ��� ȣ���� �̷��
	 * �����ν� �ϰ����� �����Ѵ�.
	 */
	public boolean setClassContentFromShadow() {
		String className = this.getName();
		ClassContent aClassContent = _controller.classContentFor(className);
		if (aClassContent == null) return false;
		TextContent nameContent = aClassContent.nameContent;
		TextContent varContent = aClassContent.varContent;
		TextContent methodContent = aClassContent.methodContent;
		_classname.replaceTextContent(nameContent);
		_vars.replaceTextContent(varContent);
		_methods.replaceTextContent(methodContent);
		return true;
	}
	/** �� Ŭ������ �̸� �ؽ�Ʈ ������ ���� �����ϴ� �Լ��̴�.
	 */
	public void setNameText(TextContent aContent)
	{
		_classname.replaceTextContent(aContent);
	}		
	/** ����Ÿ ��� _anytionlist �� ���� �б� access �Լ��̴�.
	 */
	public AnyTionList getAnyTionList() {
		return _anytionlist;
	}
	/** ������ �Ŀ� Ŭ������ ��ǥ ���� �ؽ�Ʈ ������ �̿��Ͽ� �ٽ� ����ϴ� �Լ��̴�.
	 */
	public void adjustClassSize() {
		int sx2 = _x2; int sy2 = _y2;
		int nameWidth = _classname.width();
		int nameHeight = _classname.height();
		int varsWidth = _vars.width();
		int varsHeight = _vars.height();
		int methodWidth = _methods.width();
		int methodHeight = _methods.height();
		int maxWidth = _initWidth + 2*_GapX; 
		int x = _x1 + _GapX;
		int y = _y1 + _GapY;
		_classname.recalcCoordFromXy1(x,y);
		_x2 = _x1 + maxWidth;
		_y2 = _y1 + _deltaV*(nameHeight+varsHeight+methodHeight) + 6*_GapY;
		if (_y2 > _y1 + 150) {
			_y2 = _y1 + 150;
		}
		setX2AndCentering();
		_separator1._x1 = _x1;
		_separator1._x2 = _x2;
		_separator1._y1 = _y1 + _deltaV*(nameHeight) + 2*_GapY;
		_separator1._y2 = _separator1._y1;
		y = _y1 + _deltaV*(nameHeight) + 3*_GapY;
		_vars.recalcCoordFromXy1(x,y);
		_separator2._x1 = _x1;
		_separator2._x2 = _x2;
		_separator2._y1 = _y1 + _deltaV*(nameHeight+varsHeight) + 4*_GapY;
		_separator2._y2 = _separator2._y1;
		y = _y1 + _deltaV*(nameHeight+varsHeight) + 5*_GapY;
		_methods.recalcCoordFromXy1(x,y);
		makeRegion();
	}
	int determinePosition(ClassTemplate toClass) {
		int fx1 = _x1;
		int fy1 = _y1;
		int tx1 = toClass._x1;
		int ty1 = toClass._y1;
		
		if (fx1 == tx1 && fy1 > ty1) return 1;
		if (fx1 < tx1 && fy1 > ty1) return 2;
		if (fx1 < tx1 && fy1 == ty1) return 3;
		if (fx1 < tx1 && fy1 < ty1) return 4;
		if (fx1 == tx1 && fy1 < ty1) return 5;
		if (fx1 > tx1 && fy1 < ty1) return 6;
		if (fx1 > tx1 && fy1 == ty1) return 7;
		if (fx1 > tx1 && fy1 > ty1) return 8;
		return 0;
	}
	static public int random(int lower,int upper) {
		double s = Math.random();
		int diff = upper - lower;
		s = s * (double)diff;
		return lower + (int)s;
	}
	private AnyTion drawTionTo1(int id,GraphicController controller,ClassTemplate toClass) {
		int fromX = _x2;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y2-1;
		
		int point1X = fromX+random(5,50);
		int point1Y = fromY;
		
		int point2X = point1X;
		int point2Y = random(toClass._y2+10,toClass._y2+100);
		
		int point3X = toX;
		int point3Y = point2Y;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,point2X,point2Y,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
		
		Line line3 = new Line(controller,point2X,point2Y,point3X,point3Y,null,Const.STRAIGHT);
		line3.setOrient(Const.EAST);
		newTion.insert(line3,true);

		Line line4 = new Line(controller,point3X,point3Y,toX,toY,null,Const.STRAIGHT);
		line4.setOrient(Const.NORTH);
		newTion.insert(line4,true);
		
		return newTion;
	}
	private AnyTion drawTionTo2(int id,GraphicController controller,ClassTemplate toClass) {
		return drawTionTo1(id,controller,toClass);
		/*
		int fromX = _x2;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y2-1;
		
		int point1X = toX;
		int point1Y = fromY;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,toX,toY,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
				
		return newTion;
		*/
	}
	private AnyTion drawTionTo3(int id,GraphicController controller,ClassTemplate toClass) {
		return drawTionTo1(id,controller,toClass);
	}
	private AnyTion drawTionTo4(int id,GraphicController controller,ClassTemplate toClass) {
		int fromX = _x2;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y1;
		
		int point1X = fromX+random(5,50);
		int point1Y = fromY;
		
		int point2X = point1X;
		int point2Y = random(toClass._y1-100,toClass._y1+10);
		
		int point3X = toX;
		int point3Y = point2Y;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,point2X,point2Y,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
		
		Line line3 = new Line(controller,point2X,point2Y,point3X,point3Y,null,Const.STRAIGHT);
		line3.setOrient(Const.EAST);
		newTion.insert(line3,true);

		Line line4 = new Line(controller,point3X,point3Y,toX,toY,null,Const.STRAIGHT);
		line4.setOrient(Const.NORTH);
		newTion.insert(line4,true);
		
		return newTion;
		/*
		int fromX = _x2;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y1;
		
		int point1X = toX;
		int point1Y = fromY;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,toX,toY,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
				
		return newTion;
		*/
	}
	private AnyTion drawTionTo5(int id,GraphicController controller,ClassTemplate toClass) {
		return drawTionTo4(id,controller,toClass);
	}
	private AnyTion drawTionTo6(int id,GraphicController controller,ClassTemplate toClass) {
		int fromX = _x1;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y1;
		
		int point1X = fromX-random(5,50);
		int point1Y = fromY;
		
		int point2X = point1X;
		int point2Y = random(toClass._y1-100,toClass._y1+10);
		
		int point3X = toX;
		int point3Y = point2Y;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,point2X,point2Y,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
		
		Line line3 = new Line(controller,point2X,point2Y,point3X,point3Y,null,Const.STRAIGHT);
		line3.setOrient(Const.EAST);
		newTion.insert(line3,true);

		Line line4 = new Line(controller,point3X,point3Y,toX,toY,null,Const.STRAIGHT);
		line4.setOrient(Const.NORTH);
		newTion.insert(line4,true);
		
		return newTion;
		/*
		int fromX = _x1;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y1;
		
		int point1X = toX;
		int point1Y = fromY;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,toX,toY,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
				
		return newTion;
		*/
	}
	private AnyTion drawTionTo7(int id,GraphicController controller,ClassTemplate toClass) {
		int fromX = _x1;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y2-1;
		
		int point1X = fromX-random(5,50);
		int point1Y = fromY;
		
		int point2X = point1X;
		int point2Y = random(toClass._y2+10,toClass._y2+100);
		
		int point3X = toX;
		int point3Y = point2Y;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,point2X,point2Y,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
		
		Line line3 = new Line(controller,point2X,point2Y,point3X,point3Y,null,Const.STRAIGHT);
		line3.setOrient(Const.EAST);
		newTion.insert(line3,true);

		Line line4 = new Line(controller,point3X,point3Y,toX,toY,null,Const.STRAIGHT);
		line4.setOrient(Const.NORTH);
		newTion.insert(line4,true);
		
		return newTion;
	}
	private AnyTion drawTionTo8(int id,GraphicController controller,ClassTemplate toClass) {
		int fromX = _x1;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y2-1;
		
		int point1X = fromX-random(5,50);
		int point1Y = fromY;
		
		int point2X = point1X;
		int point2Y = random(toClass._y2+10,toClass._y2+100);
		
		int point3X = toX;
		int point3Y = point2Y;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,point2X,point2Y,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
		
		Line line3 = new Line(controller,point2X,point2Y,point3X,point3Y,null,Const.STRAIGHT);
		line3.setOrient(Const.EAST);
		newTion.insert(line3,true);

		Line line4 = new Line(controller,point3X,point3Y,toX,toY,null,Const.STRAIGHT);
		line4.setOrient(Const.NORTH);
		newTion.insert(line4,true);
		
		return newTion;
		/*
		int fromX = _x1;
//		int fromY = _y1+10;
		int fromY = random(_y1+1,_y2-1);
		
		int toX = random(toClass._x1+1,toClass._x2-1);
		int toY = toClass._y2-1;
		
		int point1X = toX;
		int point1Y = fromY;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.EAST);
		
		AnyTion newTion;
		if (id == 0) {
			newTion = new ColTion(controller,controller.coltionPopup(),line1);
		} else {
			newTion = new AggTion(controller,controller.aggtionPopup(),line1);
		}
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,toX,toY,null,Const.STRAIGHT);
		line2.setOrient(Const.NORTH);
		newTion.insert(line2,true);
				
		return newTion;
		*/
	}
	void drawDependTo(GraphicController controller,ClassTemplate toClass) {
		int where = determinePosition(toClass);
		if (where == 0) return;
		AnyTion anyTion = null;
		switch (where) {
		case 1:
			anyTion = drawTionTo1(0,controller,toClass);
			break;
		case 2:
			anyTion = drawTionTo2(0,controller,toClass);
			break;
		case 3:
			anyTion = drawTionTo3(0,controller,toClass);
			break;
		case 4:
			anyTion = drawTionTo4(0,controller,toClass);
			break;
		case 5:
			anyTion = drawTionTo5(0,controller,toClass);
			break;
		case 6:
			anyTion = drawTionTo6(0,controller,toClass);
			break;
		case 7:
			anyTion = drawTionTo7(0,controller,toClass);
			break;
		case 8:
			anyTion = drawTionTo8(0,controller,toClass);
			break;
		}
		ColTion newTion = (ColTion)anyTion;
		if (newTion == null) return;

		newTion.setModelSpecificSymbol(toClass);
		newTion.epilog(true);
		toClass.attachToAnytionList(newTion);
		newTion.classes().insert(toClass,Const.ABSOLUTELY);
		newTion.makeRegion();
		controller.insert(newTion);
		controller.draw(newTion);
	}
	void drawAggTionTo(GraphicController controller,ClassTemplate toClass) {
		int where = determinePosition(toClass);
		if (where == 0) return;
		AnyTion anyTion = null;
		switch (where) {
		case 1:
			anyTion = drawTionTo1(1,controller,toClass);
			break;
		case 2:
			anyTion = drawTionTo2(1,controller,toClass);
			break;
		case 3:
			anyTion = drawTionTo3(1,controller,toClass);
			break;
		case 4:
			anyTion = drawTionTo4(1,controller,toClass);
			break;
		case 5:
			anyTion = drawTionTo5(1,controller,toClass);
			break;
		case 6:
			anyTion = drawTionTo6(1,controller,toClass);
			break;
		case 7:
			anyTion = drawTionTo7(1,controller,toClass);
			break;
		case 8:
			anyTion = drawTionTo8(1,controller,toClass);
			break;
		}
		AggTion newTion = (AggTion)anyTion;
		if (newTion == null) return;
		
		newTion.setModelSpecificSymbol(toClass);
		newTion.epilog(true);
		toClass.attachToAnytionList(newTion);
		newTion.classes().insert(toClass,Const.ABSOLUTELY);
		newTion.makeRegion();
		controller.insert(newTion);
		controller.draw(newTion);
	}
	void drawGentionTo(GraphicController controller,ClassTemplate toClass) {
		int fromX = (_x1 + _x2) / 2;
		int fromY = _y2;
		
		int toX = (toClass._x1 + toClass._x2) / 2;
		int toY = toClass._y1;
		
		int point1X = fromX;
		int point1Y = (fromY + toY) / 2;
		
		int point2X = toX;
		int point2Y = point1Y;
		
		Line line1 = new Line(controller,fromX,fromY,point1X,point1Y,null,Const.STRAIGHT);
		line1.setOrient(Const.NORTH);
		
		GenTion newTion = new GenTion(controller,controller.gentionPopup(),line1);
		attachToAnytionList(newTion);
		newTion.classes().insert(this,Const.ABSOLUTELY);

		Line line2 = new Line(controller,point1X,point1Y,point2X,point2Y,null,Const.STRAIGHT);
		line2.setOrient(Const.EAST);
		newTion.insert(line2,true);
		
		Line line3 = new Line(controller,point2X,point2Y,toX,toY,null,Const.STRAIGHT);
		line3.setOrient(Const.NORTH);
		newTion.insert(line3,true);
		
		newTion.setModelSpecificSymbol(toClass);
		newTion.epilog(true);
		toClass.attachToAnytionList(newTion);
		newTion.classes().insert(toClass,Const.ABSOLUTELY);
		newTion.makeRegion();
		controller.insert(newTion);
		controller.draw(newTion);
	}
}