package figure;


import modeler.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.Point;

import javax.swing.*;
/** 이 클래스는 UML 표기법 중에서 클래스 객체를 표현하기 위한 것이다.
 * 이 객체는 기본적으로 사각형 모양을 하고있으며 부속 객체로 2개의 선분과
 * 3개의 텍스트 객체를 보유한다.
 */
public final class ClassTemplate extends Box 
										implements ClassLike 
{ 
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 1682354401448728381L;
	/** 이 클래스가 template 인가를 나타내는 flag
	 */
	private boolean _templateFlag;
	/** 이 클래스가 template인 경우 template의 변수 문자열
	 */
	private char[] _tagString;
	/** 한 문자의 높이
	 */
	private int _deltaV;
	private boolean _fixedSizeFlag;
	/** 이 클래스를 resize 시작하기 전에 _x2 값
	 */
	private int _savedx2;
	/** 이 클래스를 resize 시작하기 전에 _y2 값
	 */
	private int _savedy2;
	/** 첫번째 경계선
	 */
	private Line _separator1;
	/** 두번째 경계선
	 */
	private Line _separator2;
	/** 클래스 이름 부분을 나타내는 텍스트 객체
	 */
	private ClassText _classname;
	/** 데이타 멤버 부분을 나타내는 텍스트 객체
	 */
	private ClassText _vars;
	/** 멤버 함수 부분을 나타내는 텍스트 객체
	 */
	private ClassText _methods;
	/** 이 클래스와 연결되어있는 관계들을 저장하는 리스트 객체
	 */
	private AnyTionList _anytionlist;
	/** 이 클래스가 링크된 클래스인 경우 이 클래스가 연결된 관계에 대한 레퍼런스
	 */
	private AssTion _linkAssTion;
	/** 이 클래스에서 가장 활성화 상태인 텍스트 객체에 대한 레퍼런스
	 */
	private transient Figure _focus;
	/** 이 클래스의 이동과 함께 관계들도 이동되어야 할 때 지정되는 상태 값
	 */
	private transient boolean _moveComplexFlag;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 x 변위
	 */
	private static int _GapX = 6;
	/** 텍스트 객체가 사각형으로 부터 떨어져있는 y 변위
	 */
	private static int _GapY = 4;
	/** 한 줄의 최소 길이 (문자 단위)
	 */
	private static int _initWidth = 80;
	private static int _defaultMaxWidth = 160;


	 /** 링크 관계 설정 함수
	  */
	public void setLinkAssTion(AssTion aTion) {
		_linkAssTion = aTion;
	}
	/** 이 클래스의 내용이 비어있는가를 묻는 함수
	 */
	public boolean emptyClass() {
		if (_vars.hasContent() == true) return false;
		if (_methods.hasContent() == true) return false;
		return true;
	}
	/** 이 객체의 시작점을 새로운 위치로 바꾸는 함수
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
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		_separator1.setController(ptr);
		_separator2.setController(ptr);
		_classname.setController(ptr);
		_vars.setController(ptr);
		_methods.setController(ptr);
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) 
	{
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.classPopup());
		_classname.doSomethingAfterLoad(controller);
		_vars.doSomethingAfterLoad(controller);
		_methods.doSomethingAfterLoad(controller);		
	}
	/** 메뉴 선택에 의해 새로운 클래스를 만드는 콜백 함수이다.
	 */
	public static void makeNewClass(GraphicController controller,int popupX,int popupY,int fontSizeV,Popup popupPtr) {
		ClassTemplate aclass = 
							  new ClassTemplate(controller,popupX,popupY,fontSizeV,popupPtr);
												
		controller.startEditableObject(aclass,aclass._classname);
	}
	/** 생성자이다. 일반적으로 자주 사용된다.
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
	/** 생성자이다. 복사시에 주로 호출된다.
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
	/** 인자에서 주어진 크기 만큼 클래스의 높이를 조정한다. 인자의 단위는 문자단위이다.
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
	/** 인자의 클래스가 이 객체의 선조에 포함되는가를 확인하는 함수이다.
	 */
	public boolean hasAncestor(ClassLike tocheck) {
		if (this == tocheck) return true;
		String thisname = this.getName();
		String tocheckname = tocheck.getName();
		return false;
	}
	/** 관계 객체를 가리키는 포인터를 새로운 값으로 변경한다.
	 */
	public boolean replaceAnytionPtr(AnyTion from,AnyTion to) {
		boolean	replaced = _anytionlist.replacePtr(from,to);
		return replaced;
	}
	/** 백업으로 만들어놨던 클래스 객체를 없애는 함수이다.
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
	/** 이 클래스에 연결된 관계가 있는가를 확인하는 함수이다.
	 */	
	public boolean doYouHaveAnyTion() {
		if (_anytionlist.empty() == false) return true;
		return false;
	}
	/** 이 클래스가 이름을 가지고 있는가를 확인하는 함수이다.
	 */
	public boolean hasname() {
		return _classname.hasContent();
	}
	/** 이 클래스의 이름을 찾아오는 함수이다.
	 */
	public String getName() {
		return _classname.content().getName();
	}
	/** 이 클래스의 전체 이름을 찾아오는 함수이다.
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
		return Const.IAMCLASSTEMPLATE;
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		if (_templateFlag == true) {
			clearTemplateTag();	
		}
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		_separator1.setInCanvas(flag);
		_separator2.setInCanvas(flag);
		_classname.setInCanvas(flag);
		_vars.setInCanvas(flag);
		_methods.setInCanvas(flag);
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) 
	{
		_popup = controller.classPopup();	
	}
	/** 이 객체를 위한 팝업 메뉴를 display하는 함수이다.
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
	/** 이 클래스를 템플리트로 변환시키는 함수이다.
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
	/** 이 클래스를 최소화 혹은 최대화 시킴으로써 크기를 변화시키는 함수이다.
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
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
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
	/** 좌표만 이동 시키는 함수이다.
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
	/** 이 객체에 대해 텍스트 편집 작업을 시작도록하는 함수이다.
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
	/** 인자에 주어진 팝업 좌표 값이 클래스 내용 부분에서 어느 텍스트에 속하는가를 찾아내는 함수이다.
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
	/** 이 클래스가 템플리트 클래스인 경우 템플리트 인수 부분 그림 영역을 지우는 함수이다.
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
	/** 이 클래스가 템플리트 클래스인 경우 템플리트 인수 부분을 그리는 함수이다.
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
	/** 클래스의 좌표 값을 텍스트 내용을 이용하여 다시 계산하는 함수이다.
	 * 이 함수는 한 클래스이 내용이 바뀌었을 때 이를 공유하는 같은 이름의 다른 클래스
	 * 그림을 다시 그려주기 위해 사용된다.
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
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
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
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
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
	/** 데이타 멤버 _focus에 대한 읽기 access 함수
	 */
	public Figure focus() {
		return _focus;
	}
	/** 이 클래스의 폭과 높이를 다시 계산한다. 특히 편집 전에 최소화 되어 있던 경우에
	 * 원래 크기를 회복 시키는 함수이다.
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
	/** 데이타 멤버 _classname 에 대한 읽기 access 함수이다.
	 */
	public ClassText classname() {
		return _classname;
	}
	/** 데이타 멤버 _vars 에 대한 읽기 access 함수이다.
	 */
	public ClassText vars() {
		return _vars;
	}
	/** 데이타 멤버 _methods 에 대한 읽기 access 함수이다.
	 */
	public ClassText methods() {
		return _methods;
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
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
	/** 이 클래스에 연결된 링크 심볼을 지우는 함수이다.
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
	/** 이 클래스를 이동하기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
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
	/** 클래스 객체의 이동이나 편집이 끝난 이후에 호출되는 함수로서 일관성 검사,
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
	/** 이 클래스와 연결되어 있는 관계들을 제거하는 함수이다.
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
	/** 인자에 주어진 클래스가 이미 자식 클래스에 속해있는가를 확인하는 함수이다.
	 */
	public boolean hasChildAlready(ClassTemplate tocheck) {
		AnyTion ptr = _anytionlist.getFirst();
		while (ptr != null) {
			if (ptr.classes().inlistForName(tocheck)) return true;
			ptr = _anytionlist.getNext();
		}
		return false;
	}
	/** 이 클래스에 연결하고자하는 선분이 클래스의 어느쪽 변에 접촉되는가를 찾는 함수이다.
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
	/** 클래스 내용 중에 인자에 의해 명시된 멤버의 특정 라인을 제거하는 함수이다.
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
	/** 인자에 의해 명시된 멤버의 특정 위치(마우스의 위치)에 새로운 라인을 
	 * 추가하는 함수이다. 
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
	/** 이 클래스의 이름 내용을 새로 지정하는 함수이다.
	 */
	public void setNameString(String aString)
	{
		TextLine newLine = new TextLine();
		newLine.setString(aString);
		newLine.dataMemberFlag = false;
		_classname.insertLineAtLast(newLine);
		_classname.content().doParse(ClassText.NAMEFIELD);
	}		
	/** 새로운 데이타 멤버를 추가한다.
	 */
	public void addMemberFunctionString(String aString) {
		TextLine newLine = new TextLine();
		newLine.setString(aString);
		newLine.dataMemberFlag = false;
		_methods.insertLineAtLast(newLine);
		_methods.content().doParse(ClassText.METHODFIELD);
	}
	/** 새로운 데이타 멤버를 추가한다.
	 */
	public void addDataMemberString(String aString) {
		TextLine newLine = new TextLine();
		newLine.setString(aString);
		newLine.dataMemberFlag = true;
		_vars.insertLineAtLast(newLine);
		_vars.content().doParse(ClassText.VARFIELD);
	}
	/** 클래스의 이름이 정해지지 않는 경우 기본 이름을 부여한다.
	 */
	public void setDummyName() {
		_classname.setDummyName();
	}
	/** 화일을 로드할 때 클래스 내용을 ShadowClasses 리스트에 등록하는 함수이다.
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
	/** 클래스의 편집이 끝났을 때 클래스 내용을 ShadowClasses 리스트에 등록하는 함수이다.
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
	/** 이 클래스의 멤버 내용을 공유 클래스의 내용으로 부터 새로 설정하는 함수이다.
	 * 이 클래스와 같은 이름의 다른 클래스 내용이 변경되었을 때 이 함수의 호출이 이루어
	 * 짐으로써 일관성을 유지한다.
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
	/** 이 클래스의 이름 텍스트 내용을 새로 지정하는 함수이다.
	 */
	public void setNameText(TextContent aContent)
	{
		_classname.replaceTextContent(aContent);
	}		
	/** 데이타 멤버 _anytionlist 에 대한 읽기 access 함수이다.
	 */
	public AnyTionList getAnyTionList() {
		return _anytionlist;
	}
	/** 역공학 후에 클래스의 좌표 값을 텍스트 내용을 이용하여 다시 계산하는 함수이다.
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