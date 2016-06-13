package figure;

import modeler.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.Point;
/** 이 클래스는 UML 표기법의 association을 표현하기 위한 것이다.
 * 이 클래스에서 specialized된 기능은 association표기법을 위한 다이아몬드 표기법을
 * 지원하는 것이다.
 */
public final 
	class AssTion extends AnyTion {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -8245544830885157214L;
	/** 다아아몬드의 길이
	 */
	private static int DIAMONDLENGTH = 18;
	/** 다이아몬드의 폭
	 */
	private static int DIAMONDOFFSET = DIAMONDLENGTH*2/3;
	/** 이진 결합 관계를 나타내는 상수 값
	 */
	public static int BINARY = 2;
	/** 삼진 결합 관계를 나타내는 상수 값
	 */
	public static int TERNARY = 3;
	/** 링크된 클래스가 있는가를 나타내는 flag
	 */
	private boolean _linkSymbolFlag;
	/** 링크된 클래스에 대한 레퍼런스
	 */
	private ClassTemplate _linkedClass;
	/** 이진 관계 인가 삼진 관계 인가를 나타내는 데이타 멤버
	 */
	private int _category;
	/** 삼진 관계인 경우 다이아몬드 심볼이 그려지는 좌표의 x 값
	 */
	private int _jointX;
	/** 삼진 관계인 경우 다이아몬드 심볼이 그려지는 좌표의 y 값
	 */
	private int _jointY;
	/** 삼진 결합 관계를 위한 다이아몬드 표기법이다.
	 */
	private Diamond _symbol;
	/** 클래스들을 저장하는 리스트에서 해당 클래스 객체의 레퍼런스를 제거하는 함수이다.
	 */
	public void removeFromClasses(ClassLike clss) {
		super.removeFromClasses(clss);
		resetLinkSymbol();
	}
	/** _classes 리스트의 내용을 비우는 함수이다.
	 */
	public void clearLists() {
		super.clearLists();
		if (_linkedClass != null) {
			_linkedClass.setLinkAssTion(null);
		}
		_linkedClass = null;
		_linkSymbolFlag = false;
	}
	/** 링크된 클래스를 없애는 함수이다.
	 */
	public void resetLinkSymbol() {
		if (_linkedClass == null) return;
		Point p = getLinkSymbolStartPoint();
		int x1 = p.x; int y1 = p.y;
		p = _linkedClass.center();
		int x2 = p.x; int y2 = p.y;
		Graphics eg = _controller.getgraphics();
		eg.setColor(_controller.getBackground());
		MySys.myDrawDashedLine(eg,x1,y1,x2,y2);
		eg.dispose();
		_linkedClass = null;
		_linkSymbolFlag = false;
	}
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		if (_symbol != null) _symbol.setController(ptr);
	}
	/** 화일을 로드한 뒤 일관성을 유지하는 함수이다.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.asstionPopup());
	}
	/** 데이타멤버 _linkedClass에 대한 access 함수
	 */
	public ClassTemplate linkedClass() {
		return _linkedClass;
	}
	/** qualification 텍스트를 만들기 위한 함수이다.
	 */
	public void localMakeQualification() {
		_controller.lowlight(this);
		Line focusLine = (Line)_focus;
		if (focusLine == null) {
			_controller.beep("AssTion.localMakeQualification");
			return;
		}
		if (focusLine.whoAreYou().isEqual(Const.IAMLINE) == false) {
			_controller.beep("AssTion.localMakeQualification");
			return;
		}
		int popX = _controller.popupX();
		int popY = _controller.popupY();
		AnyTionInfoTuple tuple = getCurrentTuple(focusLine,popX,popY);
		if (tuple == null) {
			_controller.beep("AssTion.localMakeRoleName");
			return;
		}
		QualificationText thisqual = tuple.qualification;
		if (thisqual != null) {
			_controller._editingTag = true;
			thisqual.setDynamicLine(focusLine);
			localStartEdit(thisqual);
			return;
		}
		ClassTemplate clss = (ClassTemplate)tuple.classPtr;
		if (clss == null) {
			_controller.beep("AssTion.localMakeQualification");
			return;
		}
		if (clss.whoAreYou().isEqual(Const.IAMCLASSTEMPLATE) == false) {
			_controller.beep("AssTion.localMakeQualification");
			return;
		}
		int originX = _controller.originX;
		int originY = _controller.originY;
		int sx = tuple.x();
		int sy = tuple.y();
		int orient = clss.whereami(sx,sy,focusLine);
		if (orient == Const.UNDEFINED) {
			_controller.beep("AssTion.localMakeQualification");
			return;
		}
		QualificationText other = null;
		if (_lines.nOfList() == 1) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			focusLine.coords(p1,p2);
			AnyTionInfoTuple tmpTuple;
			if (p1.x == sx && p1.y == sy) {
				tmpTuple = findTupleFor(p2.x,p2.y);
				other = tuple.qualification;
			} else if (p2.x == sx && p2.y == sy) {
				tmpTuple = findTupleFor(p1.x,p1.y);
				other = tuple.qualification;
			}
		}
		int maxLen = QualificationText.checkMinLen(focusLine,_controller,other);
		if (maxLen == 0) {
			_controller.beep("AssTion.localMakeQualification");
			return;
		}
		_controller._editingTag = true;
		_controller.remove(this);
		Popup popupptr = _controller.simplePopup();
		thisqual = new QualificationText(_controller,sx,sy,orient,maxLen,popupptr);	
		thisqual.setAsstionPtr(this);
		tuple.qualification = thisqual;
		thisqual.setDynamicLine(focusLine);
		_controller.lowlight(thisqual);
		_focus = thisqual;
//		thisqual.screen().activate();	
	}
	/** 결합 관계 선분의 위치 변화에 따라 qualification 텍스트의 위치를 조정하는 함수이다.
	 */
	private boolean adjustQualification(Line aline,int x,int y) {
		AnyTionInfoTuple tuple = getCurrentTuple(aline,x,y);	
		if (tuple == null) return false;
		QualificationText qual =  tuple.qualification;
		if (qual == null) return true;
		int len = aline.length();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		qual.coords(p1,p2);
		if (aline.orient() == Const.NORTH) {
			if (len < p2.y-p1.y) return false;
		} else /* EAST */ {
			if (len < p2.x-p1.x) return false;
		}
		QualificationText other = null;
		if (_lines.nOfList() == 1) {
			AnyTionInfoTuple tmp = _infoTuples.getFirst();
			if (tmp == tuple) {
				tmp = _infoTuples.getNext();
			}
			other = tmp.qualification;	
		}
		int sx = tuple.x();
		int sy = tuple.y();
		int orient = ((ClassTemplate)tuple.classPtr).whereami(sx,sy,aline);
		qual.adjustCoordsFor(sx,sy,orient,aline,other);
		return true;
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		if (_symbol != null) {
			_symbol.delete(); _symbol = null;
		}
		if (_linkedClass != null) {
			_linkedClass.setLinkAssTion(null);
		}
		_linkedClass = null;
		_linkSymbolFlag = false;
		super.delete();
	}
	/** 생성자이다.
	 */
	public AssTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
		_linkSymbolFlag = false;
		_linkedClass = null;
		_jointX = 0;
		_jointY = 0;
		_symbol = null;
		_category = BINARY;
	}
	/** 현재 마우스가 눌러졌을 때 라인 전체를 움직일 것인가 혹은  이름 부분이나, 다중성 부분을
	 * 움직일 것인가를 결정하는 함수이다.
	 */
	public boolean wantMoveFocus(CPoint point) {
		if (super.wantMoveFocus(point) == true) return true;
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (_focus == roleName) {
				return true;
			}
			Figure mul = tuple.multiplicity;
			if (_focus == mul) {
				return true;
			}
			tuple = _infoTuples.getNext();
		}
		return false;
	}
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		super.drawDots(g);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.drawDots(g);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.drawDots(g);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 데이타 멤버 _category에 대한 access 함수
	 */
	public int category() {
		return _category;
	}
	/** 데이타 멤버 _category 값을 설정하는 access 함수
	 */
	public void setCategory(int what) {
		_category = what;
	}
	/** 데이타 멤버 _jointX, _jointY 값을 설정하는 access 함수
	 */
	public void setJoint(int x,int y) {
		_jointX = x; _jointY = y;
	}
	/** 다중성을 표시를 위한 좌표를 다시 계산하는 함수이다. 그런데 OMT에서와 달리
	 * UML 표기법에서는 의미가 없다.
	 */
	public void adjustMultiplicity(Line aline,int x,int y) {
		AnyTionInfoTuple tuple = getCurrentTuple(aline,x,y);	
		if (tuple == null) return;
		Figure mul =  tuple.multiplicity;
		if (mul == null) return;
		if (mul.whoAreYou() != Const.IAMPOINT) return;
		Point cp = new Point(-1,-1);
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		aline.coords(p1,p2);
		findCenterPositionForAPoint(tuple,p1,p2,cp);
		if (cp.x == -1 || cp.y == -1) return;
		QualificationText qual = tuple.qualification;
		if (qual != null) {
			int orient = qual.orient();
			qual.coords(p1,p2);
			int w = p2.x - p1.x;
			int h = p2.y - p1.y;
			if (orient == Const.NORTH) {
				cp.y = cp.y - h;
			} else if (orient == Const.EAST) {
				cp.x = cp.x + w;
			} else if (orient == Const.SOUTH) {
				cp.y = cp.y + h;
			} else /* WEST */ {
				cp.x = cp.x - w;
			}
		}
		mul.setXY1(cp.x,cp.y);
	}
	/** 현재 마우스 좌표 값과 만나는 선분을 찾아주는 함수이다.
	 */
	public Line getLineFor(int x,int y) {
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.line().doesMeet(x,y)) return tmp.line();
			tmp = _lines.getNext();
		}
		return null;
	}
	/** AITList의 findTupleForQual() 함수 참조 
	 */
	public AnyTionInfoTuple findTupleForQual(QualificationText qual) {
		return _infoTuples.findTupleFor(qual);
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMASSTION;
	}
	/** 화면에 그려진 관계가 논리적으로 이상하지 않은가를 확인하는 함수이다.
	 */
	public boolean valid(ClassLike clss) {
		if (validForModelSpecific(clss) == false) return false;
		return true;
	}
	/** 화면에 그려진 관계의 심볼이 이상하지 않은가를 확인하는 함수이다.
	 */
	public boolean validForModelSpecific(ClassLike clss) {
		if (_category == BINARY) {
			return true;
		}
		if (_classes.nOfList() < 3) return true;
		return true;
	}
	/** 결합 관계을 위한 심볼을 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbol(ClassLike classPtr) {
		int originX = _controller.originX;
		int originY = _controller.originY;
		if (_category == BINARY || (_category == TERNARY && _classes.nOfList() == 1)) {
			Line firstLine = _lines.front().line();
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			firstLine.coords(p1,p2);
			ClassLike firstClass = _classes.front();
			AnyTionInfoTuple tuple = new AnyTionInfoTuple(firstClass,p1.x,p1.y);
			_infoTuples.insert(tuple,0);
			Line lastLine = _lines.rear().line();
			lastLine.coords(p1,p2);
			tuple = new AnyTionInfoTuple(classPtr,p2.x,p2.y);
			_infoTuples.insert(tuple,0);
			return false;
		}
		Line lastLine = _lines.rear().line();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		lastLine.coords(p1,p2);
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classPtr,p2.x,p2.y);
		_infoTuples.insert(tuple,0);
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		startX = _jointX - originX - DIAMONDLENGTH;
		startY = _jointY - originY - DIAMONDOFFSET;
		endX = _jointX - originX + DIAMONDLENGTH;
		endY = _jointY - originY + DIAMONDOFFSET;
		_symbol = new Diamond(_controller,startX,startY,endX,endY,null);
		Line aline;
		int jointLineCount = 0;
		_jointX = 0;
		_jointY = 0;
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.beforeList().nOfList() > 1) {
				jointLineCount++;
				aline = tmp.line();
				if (aline.orient() == Const.NORTH &&
					aline.length() < DIAMONDOFFSET) {
					_symbol.delete(); _symbol = null;
					return true;
				}
				if (aline.orient() == Const.EAST &&
					aline.length() < DIAMONDLENGTH) {
					_symbol.delete(); _symbol = null;
					return true;
				}
				if (_jointX == 0 && _jointY == 0) {
					aline.coords(p1,p2);
					_jointX = p1.x + originX;
					_jointY = p1.y + originY;
				}
			} else if (tmp.afterList().nOfList() > 1) {
				jointLineCount++;
				aline = tmp.line();
				if (aline.orient() == Const.NORTH &&
					aline.length() < DIAMONDOFFSET) {
					_symbol.delete(); _symbol = null;
					return true;
				}
				if (aline.orient() == Const.EAST &&
					aline.length() < DIAMONDLENGTH) {
					_symbol.delete(); _symbol = null;
					return true;
				}
				if (_jointX == 0 && _jointY == 0) {
					aline.coords(p1,p2);
					_jointX = p2.x + originX;
					_jointY = p2.y + originY;
				}
			}
			tmp = _lines.getNext();
		}
		if (jointLineCount != 3) {
			_symbol.delete(); _symbol = null;
			return true;
		}
		p1.x = _jointX - originX - DIAMONDLENGTH;
		p1.y = _jointY - originY - DIAMONDOFFSET;
		p2.x = _jointX - originX + DIAMONDLENGTH;
		p2.y = _jointY - originY + DIAMONDOFFSET;
		_symbol.setXY1(p1.x,p1.y);
		_symbol.setXY2(p2.x,p2.y);
		return false;
	}
	/** 관계를 위한 심볼을 일괄적으로 재 설정하는 함수이다.
	 */
	public boolean setModelSpecificSymbolAll() {
		int originX = _controller.originX;
		int originY = _controller.originY;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		if (_category == TERNARY) {
			Line aline;
			int jointLineCount = 0;
			_jointX = 0;
			_jointY = 0;
			LineNode tmp = _lines.getFirst();
			while(tmp != null) {
				if (tmp.beforeList().nOfList() > 1) {
					jointLineCount++;
					aline = tmp.line();
					if (aline.orient() == Const.NORTH &&
						aline.length() < DIAMONDOFFSET) {
						return true;
					}
					if (aline.orient() == Const.EAST &&
						aline.length() < DIAMONDLENGTH) {
						return true;
					}
					if (_jointX == 0 && _jointY == 0) {
						aline.coords(p1,p2);
						_jointX = p1.x + originX;
						_jointY = p1.y + originY;
					}
				} else if (tmp.afterList().nOfList() > 1) {
					jointLineCount++;
					aline = tmp.line();
					if (aline.orient() == Const.NORTH &&
						aline.length() < DIAMONDOFFSET) {
						return true;
					}
					if (aline.orient() == Const.EAST &&
						aline.length() < DIAMONDLENGTH) {
						return true;
					}
					if (_jointX == 0 && _jointY == 0) {
						aline.coords(p1,p2);
						_jointX = p2.x + originX;
						_jointY = p2.y + originY;
					}
				}
				tmp = _lines.getNext();
			}
			if (jointLineCount != 3) return true;
			p1.x = _jointX - originX - DIAMONDLENGTH;
			p1.y = _jointY - originY - DIAMONDOFFSET;
			p2.x = _jointX - originX + DIAMONDLENGTH;
			p2.y = _jointY - originY + DIAMONDOFFSET;
			if (_symbol == null) return true;
			_symbol.setXY1(p1.x,p1.y);
			_symbol.setXY2(p2.x,p2.y);
		}

		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			Line aline = tmp.line();
			boolean okflag = true;
			if (tmp.beforeList().empty()) {
				aline.coords(p1,p2);
				okflag = adjustQualification(aline,p1.x,p1.y);
				if (okflag == false) return true;
				adjustMultiplicity(aline,p1.x,p1.y);
			}
			if (tmp.afterList().empty()) {
				aline.coords(p1,p2);
				okflag = adjustQualification(aline,p2.x,p2.y);
				if (okflag == false) return true;
				adjustMultiplicity(aline,p2.x,p2.y);
			} 
			tmp = _lines.getNext();
		}
		return false;
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		super.draw(g,style,specialdcp);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.draw(g,style,specialdcp);
			}
			QualificationText qual = tuple.qualification;
			if (qual != null) {
				qual.draw(g,style,specialdcp);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.draw(g,style,specialdcp);
			}
			tuple = _infoTuples.getNext();
		}
		if (style == Const.RUBBERBANDING) return;
		if (style == Const.DRAWING) drawLinkSymbol(g,true);
		if (_symbol != null) {
			_symbol.drawEmpty(g,style,specialdcp);
		}
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		super.move(g,dx,dy);
		if (_moveAllFlag == false) return;
		if (_symbol != null) {
			_jointX = _jointX + dx;
			_jointY = _jointY + dy;
			_symbol.moveCoord(dx,dy);
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			tuple.moveCoord(dx,dy);
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.move(g,dx,dy);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.move(g,dx,dy);
			}
			QualificationText qual = tuple.qualification;
			if (qual != null) {
				qual.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 관계의 선분 모양을 이동하기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
	 */
	public boolean moveProlog(int oneorsomeptr) {
		Graphics g = _controller.getgraphics();
		drawLinkSymbol(g,false);
		g.dispose();
		return super.moveProlog(oneorsomeptr);
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		drawLinkSymbol(g,false);
		if (_symbol != null) _symbol.clear(g,expose);
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		super.makeRegion();
		if (_symbol != null) {
			_symbol.makeRegion();
			CRgn.myUnionRgn(_region,_symbol.region(),_region);
			CRgn.myUnionRgn(_maxRegion,_symbol.maxRegion(),_maxRegion);
		}
	}
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
	 */
	public boolean checkInRegion(CRgn someregion) {
		if (super.checkInRegion(someregion)) return true;
		if (_symbol != null && _symbol.checkInRegion(someregion)) return true;
		return false;
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		if (_symbol != null) _symbol.setInCanvas(flag);
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		AssTion copied;
		if (ptr == null) {
			copied = new AssTion(_controller,null,null);
		} else {
			copied = (AssTion)ptr;
		}
		copied._category = _category;
		copied._jointX = _jointX;
		copied._jointY = _jointY;
		if (_symbol != null) {
			copied._symbol = (Diamond)_symbol.born(null);
		}
		return(super.born((Figure)copied));
	}
	/** 이 결합관계에 연결된 링크된 클래스로 링크 심볼을 연결하게 하는 함수이다.
	 */
	public void localMakeLinkSymbol() {
		_controller.lowlight(this);
		if (_linkSymbolFlag == true) {
			Graphics g = _controller.getgraphics();
			drawLinkSymbol(g,false);
			g.dispose();
			_linkSymbolFlag = false;
			_linkedClass = null;	
			return;
		}
		FigureList activeClassList = _controller.makeListOf(Const.IAMCLASSTEMPLATE);
		ClassLike clss = _classes.getFirst();
		while (clss != null) {
			if (activeClassList.inList((Figure)clss) == true) {
				activeClassList.remove((Figure)clss);
			}
			clss = _classes.getNext();
		}	
		if (activeClassList.nOfList() == 0) {
			_controller.beep("AssTion.localMakeLinkSymbol");
			return;
		}	
		_controller.setCurrentLines((Lines)this);
		_controller.setEnable(false);
		GraphicController.FTraceEnterForList = true;
		_controller.setActiveFigures(activeClassList);
		GraphicController.FAssTionSetLinkSymbol = true;
	}
	/** 링크 심볼을 그리기 위한 시작점을 찾아내는 함수이다.
	 */
	private Point getLinkSymbolStartPoint() {
		int retX = 0;
		int retY = 0;
		if (_category == TERNARY) {
			return _symbol.center();
		} else if (_category == BINARY) {
			int n = _lines.nOfList();
			if (n % 2 == 0) {
				n = n / 2;
				AnyTionInfoTuple tmp = _infoTuples.getFirst();
				if (tmp == null) {
					return _lines.top().line().center();
				}
				int sx = tmp.x();
				int sy = tmp.y();
				LineNodeList copied = new LineNodeList();
				copied.copy(_lines);
				for (int i = 0; i < n; i++) {
					Point p1 = new Point(0,0);
					Point p2 = new Point(0,0);
					LineNode node = copied.getFirst();
					while (node != null) {
						node.line().coords(p1,p2);
						if (sx == p1.x && sy == p1.y) {
							sx = p2.x; sy = p2.y;
							copied.remove(node,true);
							break;
						} else if (sx == p2.x && sy == p2.y) {
							sx = p1.x; sy = p1.y;
							copied.remove(node,true);
							break;
						}
						node = copied.getNext();
					}
				}
				retX = sx; retY = sy;
			} else {
				n = n / 2;
				AnyTionInfoTuple tmp = _infoTuples.getFirst();
				if (tmp == null) {
					return _lines.top().line().center();
				}
				int sx = tmp.x();
				int sy = tmp.y();
				LineNodeList copied = new LineNodeList();
				copied.copy(_lines);
				for (int i = 0; i < n; i++) {
					Point p1 = new Point(0,0);
					Point p2 = new Point(0,0);
					LineNode node = copied.getFirst();
					while (node != null) {
						node.line().coords(p1,p2);
						if (sx == p1.x && sy == p1.y) {
							sx = p2.x; sy = p2.y;
							copied.remove(node,true);
							break;
						} else if (sx == p2.x && sy == p2.y) {
							sx = p1.x; sy = p1.y;
							copied.remove(node,true);
							break;
						}
						node = copied.getNext();
					}
				}
				LineNode node = copied.getFirst();
				while (node != null) {
					if (node.line().doesMeet(sx,sy)) {
						return node.line().center();
					}
					node = copied.getNext();
				}
				retX = sx; retY = sy;
			}
			return new Point(retX,retY);
		} else {
			return _lines.top().line().center();
		}
	}
	/** 링크 심볼을 그리는 함수이다.
	 */
	public void drawLinkSymbol(Graphics g,boolean turnOn) {
		if (_linkSymbolFlag == false) return;
		if (_linkedClass == null) return;	
		Point p = getLinkSymbolStartPoint();
		int x1 = p.x; int y1 = p.y;
		p = _linkedClass.center();
		int x2 = p.x; int y2 = p.y;
		Graphics eg = g.create();
		Rectangle clipR = g.getClipBounds();
		Rectangle newClipR = new Rectangle(clipR);
		eg.setClip(newClipR);
		eg.setColor(_controller.getBackground());
		if (turnOn) {
			MySys.myDrawDashedLine(g,x1,y1,x2,y2);
			_linkedClass.clear(eg,false);
			_linkedClass.draw(g,Const.DRAWING,eg);
		} else {
			MySys.myDrawDashedLine(eg,x1,y1,x2,y2);
		}
		eg.dispose();
	}
	/** 이 결합 관계에 링크 심볼을 설정하는 콜백 함수이다.
	 */
	public static void setLinkSymbol(GraphicController controller) {
		controller.setEnable(true);
		controller.resetActiveFigures();
		GraphicController.FAssTionSetLinkSymbol = false;
		GraphicController.FTraceEnterForList = false;
		if (controller.currentFocus() == null) {
			controller.setCurrentLines(null);
			controller.beep("AssTion.setLinkSymbol");
			return;
		}
		ClassTemplate target = (ClassTemplate) controller.currentFocus();
		if (target.whoAreYou().isEqual(Const.IAMCLASSTEMPLATE) == false) {
			controller.setCurrentLines(null);
			controller.beep("AssTion.setLinkSymbol");
			return;
		}
		AssTion thisTion = (AssTion)controller.currentLines();
		thisTion._linkSymbolFlag = true;
		thisTion._linkedClass = target;
		target.setLinkAssTion(thisTion);
		Graphics g = controller.getgraphics();
		thisTion.drawLinkSymbol(g,true);	
		g.dispose();
		controller.setCurrentLines(null);
	}
	/** 현재 마우스의 위치에 따른 해당 선분의 꼭지점 정보를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int popX,int popY) {
		Line focusLine;
		if (aline == null) focusLine = (Line)_focus;
		else focusLine = aline;
		if (focusLine == null) {
			_controller.beep("AssTion.getCurrentTuple");
			return null;
		}
		if (_lines.nOfList() == 1) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			int x,y;
			focusLine.coords(p1,p2);
			if (MySys.distance(popX,popY,p1.x,p1.y) < MySys.distance(popX,popY,p2.x,p2.y)) {
				x = p1.x; y = p1.y;
			} else {
				x = p2.x; y = p2.y;
			}
			return _infoTuples.findTupleFor(x,y);
		}
		return _infoTuples.getTupleForLine(focusLine);
	}
	/** AITList의 resetQualificationFor() 함수 참조 
	 */
	public void resetQualificationFor(QualificationText text) {
		_infoTuples.resetQualificationFor(text);
	}
	/** OMT의 다중성 표현 시에 조그만 원의 가운데 좌표를 찾아내는 함수이다. UML에서는 필요없지만
	 * 만약을 위해 reserve한다.
	 */
	public void findCenterPositionForAPoint(AnyTionInfoTuple tuple,Point p1,Point p2,Point cp) {
		cp.x = -1; cp.y = -1;
		int x1 = p1.x; int y1 = p1.y;
		int x2 = p2.x; int y2 = p2.y;
		if (tuple.meet(x1,y1)) {
			if (x1 == x2) {
				// vertical line
				cp.x = x1;
				if (y1 > y2) {
					cp.y = y1 - Const.POINTRADIUS;	
				} else {
					cp.y = y1 + Const.POINTRADIUS;	
				}
			} else {
				// horizontal line
				cp.y = y1;
				if (x1 > x2) {
					cp.x = x1 - Const.POINTRADIUS;
				} else {
					cp.x = x1 + Const.POINTRADIUS;
				}
			}
		} else if (tuple.meet(x2,y2)) {
			if (x1 == x2) {
				// vertical line
				cp.x = x1;
				if (y1 > y2) {
					cp.y = y2 + Const.POINTRADIUS;
				} else {
					cp.y = y2 - Const.POINTRADIUS;
				}
			} else {
				// horizontal line
				cp.y = y1;
				if (x1 > x2) {
					cp.x = x2 + Const.POINTRADIUS;
				} else {
					cp.x = x2 - Const.POINTRADIUS;
				}
			}
		}
		return;
	}
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.asstionPopup();
	}  
	/** 이 객체를 위한 팝업 메뉴를 display하는 함수이다.
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
		if (_focus.whoAreYou().isEqual(Const.IAMQUALIFICATIONTEXT)) {
			_focus.popup(event);
			return;
		}
		boolean isFocusBoundaryLine = isBoundary(_focusNode);
		if (isFocusBoundaryLine || _lines.nOfList() == 1) {
			((AssTionPopup)_popup).roleB.setEnabled(true);
			((AssTionPopup)_popup).cardinalB.setEnabled(true);
			((AssTionPopup)_popup).qualificationB.setEnabled(true);
		} else {
			((AssTionPopup)_popup).roleB.setEnabled(false);
			((AssTionPopup)_popup).cardinalB.setEnabled(false);
			((AssTionPopup)_popup).qualificationB.setEnabled(false);
		}
		_popup.popup(event);
	}
	/** association의 다이아몬드 표기법 부분을 지우는 함수이다.
	 */
	public void clearModelSpecificSymbol() {
		if (_symbol != null) _controller.clear(_symbol,true);
		return;
	}
	/** parameter로 넘겨진 텍스트 객체가 현재 aggregation에서 어떠한 목적으로 사용되는
	 * 텍스트 객체인가를 판별하는 함수이다.
	 */
	public int whatIsMyRole(SingleLineText text) {
		if (_infoTuples.inlistForRole(text)) {
			return Const.ROLENAME;
		} else if (_infoTuples.inlistForMultiplicity((Figure)text)) {
			return Const.MULTIPLICITYNAME;
		}
		return super.whatIsMyRole(text);
	}
	/** 현재 선분이 경계선인가를 확인하는 함수이다.
	 */
	public boolean isBoundary(LineNode focusNode) {
		LineNode totest;
		if (focusNode == null) {
			totest = _focusNode;
		} else {
			totest = focusNode;
		}
		if (totest.afterList().empty() ||
			totest.beforeList().empty()) 
			return true;
		return false;
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 이 함수를 통하여 GraphicController 객체의
	 * 최대 최소 좌표값에 반영된다.
	 */
	public void minMaxXY() {
		super.minMaxXY();
		if (_symbol != null) _symbol.minMaxXY();
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 인자들 값에 반영되어 돌려진다.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		super.getMinMaxXY(minP,maxP);
		if (_symbol != null) _symbol.getMinMaxXY(minP,maxP);
	}
	/** 이 객체의 중앙에 대한 좌표값을 구하는 함수이다.
	 */
	public Point center() {
		if (_focus != null) {
			return _focus.center();
		}
		return super.center();
	}
	/** 이 관계 객체가 클래스의 이동을 따라갈 필요가 있을때 이동의 시작 시점에서
	 * 따라갈 준비를 하는 함수이다. 주로 클래스를 따라가야 될 선분들을 결정하고
	 * 그 선분들의 상태 값을 초기화 한다.
	 */
	public void followClassProlog(ClassLike aclass) {
		super.followClassProlog(aclass);
		int jX = -1;
		int jY = -1;
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
	/** 이 관계와 연결된 클래스가 움직일 때 그 클래스에 따라서 이동하는 함수이다.
	 */
	public void followClass(Graphics g,int dx,int dy) {
		super.followClass(g,dx,dy);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
				if (tuple.role != null)
					tuple.role.move(g,dx,dy);
				if (tuple.multiplicity != null)
					tuple.multiplicity.move(g,dx,dy);
				if (tuple.qualification != null)
					tuple.qualification.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
}