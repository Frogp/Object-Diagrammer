package figure;

import java.awt.*;
import java.io.*;
import java.awt.Point;

/** 이 클래스는 추상 클래스로서 AggTion, GenTion, AssTion, ColTion 클래스가 제공해야 되는 서비스들 중에서
 * 공통적으로 필요한 기능들을 처리하는 클래스이다. 따라서 AggTion, GenTion, AssTion, ColTion 클래스들은 이 클래스
 * 로 부터 파생된다. 일반적인 "관계" 객체를 위한 클래스라고 할 수 있다.
 */
public
	class AnyTion extends Lines {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -7320814502800479715L;
	/** 이 관계가 연결되는 클래스 객체나 패키지 객체들의 리스트를 관리하는 리스트 객체이다.
	 */
	protected ClassLikeList _classes;
	/** 이 관계가 클래스 객체와 만나는 꼭지점 들에 관한 정보를 포함하는 리스트 객체이다.
	 * 꼭지점 정보에 관한 자세한 내용은 AnyTionInfoTuple 클래스를 참조하면 된다. 
	 */
	protected AITList _infoTuples;
	/** 이 관계의 이름을 저장하는 텍스트 객체이다.
	 */
	protected SingleLineText _name;
	/** 현재 관계가 그려지고 있는 경우에 새로이 만들어진 선분들을 기록하는 리스트 객체이다.
	 * 이 리스트는 나중에 그림 그리기가 실패할 경우 새로 생긴 선분들을 없애기 위해 사용된다.
	 */
	protected LineNodeList _activeLines;
	/** 이 관계에서 초점이 설정된 선분에 해당하는 꼭지점 정보를 나타내는 객체이다.
	 */
	protected transient AnyTionInfoTuple _focusTuple;
	/** 이 객체를 위한 팝업 포인터를 재설정하는 함수이다.
	 */
	public void replacePopup(GraphicController controller) {
		_name.replacePopup(controller);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			if (tuple.role != null) tuple.role.replacePopup(controller);
			if (tuple.qualification != null) tuple.qualification.replacePopup(controller);
			if (tuple.multiplicity != null) tuple.multiplicity.replacePopup(controller);
			tuple = _infoTuples.getNext();
		}	
	}  
	/** 이 객체를 그려주는데 사용되는 Component 객체의 포인터를 설정하는 함수이다.
	 */
	public void setController(GraphicController controller) {
		super.setController(controller);
		_infoTuples.setController(controller);
		if (_name != null) _name.setController(controller);
	}
	/** 생성자이다.
	 */
	public AnyTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,Const.STRAIGHT,line);
		_classes = new ClassLikeList();
		_activeLines = new LineNodeList();
		_infoTuples = new AITList();
		_name = null;
		_focusTuple = null;
	}
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		super.drawDots(g);
		if (_name != null) _name.drawDots(g);
	}
	/** 이 관계의 이름을 저장하는 텍스트 객체를 access하는 함수이다.
	 */
	public SingleLineText getNameText() {
		return _name;
	}
	/** 이 관계의 이름을 나타내는 텍스트 객체를 새로 설정하는 함수이다.
	 */
	public void setNameText(SingleLineText name) {
		_name = name;
	}
	/** 현재 마우스가 눌러졌을 때 라인 전체를 움직일 것인가 혹은  이름 부분이나, 다중성 부분을
	 * 움직일 것인가를 결정하는 함수이다.
	 */
	public boolean wantMoveFocus(CPoint point) {
		if (_focus == null) return false;
		if (_focus != _name) return false;
		return true;
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		super.draw(g,style,specialdcp);
		if (_name != null) _name.draw(g,style,specialdcp);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		if (_name != null) {
			_name.delete(); _name = null;
		}
		_infoTuples.delete(); _infoTuples = null;
		ClassLike clss = _classes.getFirst();
		while(clss != null) {
			clss.removeFromAnytionList(this);
			clss = _classes.getNext();
		}
		_classes.delete(); _classes = null;
		_activeLines.delete(); _activeLines = null;
		super.delete();
	}
	/** 이 함수는 가상함수로서 각종 관계에 특별히 추가되는 심볼의 길이를 돌려주는 함수이다.
	 * 예를 들어, GenTion의 경우에는 삼각형의 길이, AggTion의 경우에는 다이아몬드의 길이 값을 돌려준다.
	 * 이 함수의 목적은 그림이 이상하게 그려지는 가를 확인하는 것이다.
	 */
	public int symbolLength() {
		return 0;
	}
	/** 클래스의 이동 전에 현 관계 객체의 심볼을 지우는
	 * 가상 함수이다.
	 */
	public void redrawForArrow() {
		return;
	}
	/** 선분의 끝 부분을 확장시켜서 연관되는 클래스 내부에 선분이 포함되도록하는 함수이다.
	 * 이 함수는 그림 축소와 확대 시에 이상한 그림이 만들어지지 않도록하는 역할을 한다.
	 */
	public void expandEndLineALittle() {
		ClassLike clss = _classes.getFirst();
		while(clss != null) {
			LineNode tmp = _lines.getFirst();
			while(tmp != null) {
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				tmp.line().coords(p1,p2);
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y) && tmp.beforeList().empty()) {
					AnyTionInfoTuple tuple = findTupleFor(p1.x,p1.y);
					if (tuple.classPtr == clss) {
						clss.expandLineToCenter(tmp.line(),true);
						Point q1 = new Point(0,0);
						Point q2 = new Point(0,0);
						tmp.line().coords(q1,q2);
						tuple.setxy(q1.x,q1.y);
					}
				} 
				if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y) && tmp.afterList().empty()) {
					AnyTionInfoTuple tuple = findTupleFor(p2.x,p2.y);
					if (tuple.classPtr == clss) {
						clss.expandLineToCenter(tmp.line(),false);
						Point q1 = new Point(0,0);
						Point q2 = new Point(0,0);
						tmp.line().coords(q1,q2);
						tuple.setxy(q2.x,q2.y);
					}
				}
				tmp = _lines.getNext();
			}
			clss = _classes.getNext();
		}
	}
	/** 선분의 끝 부분을 확장시켜서 연관되는 클래스 내부에 선분이 포함되도록하는 함수이다.
	 * 이 함수의 인자들은 선분을 확장시켜야 하는 목적지에 해당하는 클래스 객체나 패키지 객체이다.
	 */
	public void extendToConFigure(ClassLike conFigure)
	{
		Point ccP = conFigure.center();
		int cx = ccP.x; int cy = ccP.y;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		conFigure.coords(p1,p2);
		AITList alist = new AITList();
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			if (tuple.classPtr == conFigure) {
				alist.insert(tuple,0);
			}
			tuple = _infoTuples.getNext();
		}	
		tuple = alist.getFirst();
		while (tuple != null) {
			int ex = tuple.x();
			int ey = tuple.y();
			if (ex > cx && ey >= p1.y && ey <= p2.y) {
				Line newline = new Line(_controller,cx,ey,ex,ey,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				LineNode newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				tuple.setxy(cx,ey);
			} else if (ey > cy && ex >= p1.x && ex <= p2.x) {
				Line newline = new Line(_controller,ex,cy,ex,ey,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				LineNode newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				tuple.setxy(ex,cy);
			} else if (ex > p2.x && ey > p2.y) {
				Line newline = new Line(_controller,cx,cy,ex,cy,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				LineNode newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				newline = new Line(_controller,ex,cy,ex,ey,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				tuple.setxy(cx,cy);
			}
			tuple = alist.getNext();
		}
		alist.clear();
		alist.delete();
		remake();
		tailorEndLinesForEditing(conFigure,0);
	}
	/** 이 함수는 인자에 명시된 클래스 객체를 편집할 경우에 그 클래스와 접하는
	 * 선분의 끝 부분을 적당한 크기로 조절하는 함수이다.
	 */
	public boolean tailorEndLinesForEditing(ClassLike conFigure,int checkLen) {
		boolean againflag = false;
		do {
			againflag = false;
			LineNode tmp = _lines.getFirst();
			while(tmp != null) {
				boolean obsolete = false;
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				tmp.line().coords(p1,p2);
				if (conFigure.contains(p1.x,p1.y) &&
					tmp.beforeList().empty() == false &&
					tmp.afterList().empty() == false) {
					return false;
				}
				if (conFigure.contains(p2.x,p2.y) &&
					tmp.beforeList().empty() == false &&
					tmp.afterList().empty() == false) {
					return false;
				}
				if (conFigure.contains(p1.x,p1.y) &&
					tmp.beforeList().empty()) {
					AnyTionInfoTuple tuple = _infoTuples.findTupleFor(p1.x,p1.y);
					if (tuple.classPtr == conFigure) {
						obsolete = conFigure.adjustLine(tmp.line(),true);
						if (tmp.line().length() < checkLen &&
							_lines.nOfList() == 1) {
							return false;
						}
						Point q1 = new Point(0,0);
						Point q2 = new Point(0,0);
						tmp.line().coords(q1,q2);
						if (obsolete) {
							tuple.setxy(q2.x,q2.y);
						} else {
							tuple.setxy(q1.x,q1.y);
						}
						if (obsolete) {
							_focusNode = tmp;
							_focus = tmp.line();
							removeFocusLine();
							againflag = true;
							break;
						}
					}
				}
				if (conFigure.contains(p2.x,p2.y) &&
					tmp.afterList().empty()) {
					AnyTionInfoTuple tuple = _infoTuples.findTupleFor(p2.x,p2.y);
					if (tuple.classPtr == conFigure) {
						obsolete = conFigure.adjustLine(tmp.line(),false);
						if (tmp.line().length() < checkLen &&
							_lines.nOfList() == 1) {
							return false;
						}
						Point q1 = new Point(0,0);
						Point q2 = new Point(0,0);
						tmp.line().coords(q1,q2);
						if (obsolete) {
							tuple.setxy(q1.x,q1.y);
						} else {
							tuple.setxy(q2.x,q2.y);
						}
						if (obsolete) {
							_focusNode = tmp;
							_focus = tmp.line();
							removeFocusLine();
							againflag = true;
							break;
						}
					}
				}
				tmp = _lines.getNext();
			}
			if (_lines.nOfList() == 0) {
				return false;
			}
		} while(againflag);
		if (_lines.nOfList() == 0) {
			return false;
		}
		return true;
	}
	/** 현재 관계에 대한 작성이 끝나거자 작성이 실패한 경우 새로히 만들어졌던 선분들을
	 * 관리하는 리스트 객체를 비우는 함수이다.
	 */
	private void clearActiveLines() {
		LineNode tmp = _activeLines.getFirst();
		while(tmp != null) {
			_controller.clear(tmp.line(),false);
			tmp = _activeLines.getNext();
		}
	}
	/** 클래스들을 저장하는 리스트에서 해당 클래스 객체의 레퍼런스를 제거하는 함수이다.
	 */
	public void removeFromClasses(ClassLike clss) {
		_classes.remove(clss,Const.ABSOLUTELY);
	}
	/** 관계을 위한 심볼을 재 설정하는 가상 함수이다.
	 */
	public boolean setModelSpecificSymbol(ClassLike clss) {
		return true; // seems strange
	}
	/** 관계를 위한 심볼을 일괄적으로 재 설정하는 가상 함수이다.
	 */
	public boolean setModelSpecificSymbolAll() {
		return true;
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		if (_moveAllFlag) {
			super.move(g,dx,dy);
			if (_name != null) _name.move(g,dx,dy);
			return;
		}
		if (((Line)_focus).orient() == Const.NORTH) {
			super.move(g,dx,0);
		} else {
			super.move(g,0,dy);
		}
		Point p = ((Line)_focus).center();
		_controller.warpPointer(p.x,p.y);
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		if (_name != null) _name.clear(g,expose);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.clear(g,expose);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.clear(g,expose);
			}
			QualificationText qual = tuple.qualification;
			if (qual != null) {
				qual.clear(g,expose);;
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 관계가 association 인 경우 이진 관계인가 삼진 관계인가를 알려주는 함수이다.
	 */
	public int category() {
		return 0;
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 이 함수를 통하여 GraphicController 객체의
	 * 최대 최소 좌표값에 반영된다.
	 */
	public void minMaxXY() {
		super.minMaxXY();
		if (_name != null) _name.minMaxXY();
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.minMaxXY();
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.minMaxXY();
			}
			QualificationText qual = tuple.qualification;
			if (qual != null) {
				qual.minMaxXY();
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 화면의 축소 시에 필요한 최대, 최소 좌표 값을 얻기 위한 함수이다.
	 * 이 객체의 최대 최소 좌표 값은 인자들 값에 반영되어 돌려진다.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		super.getMinMaxXY(minP,maxP);
		if (_name != null) _name.getMinMaxXY(minP,maxP);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.getMinMaxXY(minP,maxP);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.getMinMaxXY(minP,maxP);
			}
			QualificationText qual = tuple.qualification;
			if (qual != null) {
				qual.getMinMaxXY(minP,maxP);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		super.makeRegion();
		if (_name != null) {
			_name.makeRegion();
			//                CRgn.myUnionRgn(_region,_name.region(),_region);
			//                CRgn.myUnionRgn(_maxRegion,_name.maxRegion(),_maxRegion);
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) {
				roleName.makeRegion();
				//                        CRgn.myUnionRgn(_region,roleName.region(),_region);
				//                        CRgn.myUnionRgn(_maxRegion,roleName.maxRegion(),_maxRegion);
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.makeRegion();
				//                        CRgn.myUnionRgn(_region,mul.region(),_region);
				//                        CRgn.myUnionRgn(_maxRegion,mul.maxRegion(),_maxRegion);
			}
			QualificationText qual = tuple.qualification;
			if (qual != null) {
				qual.makeRegion();
				//                        CRgn.myUnionRgn(_region,qual.region(),_region);
				//                        CRgn.myUnionRgn(_maxRegion,qual.maxRegion(),_maxRegion);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** 현재 마우스의 좌표값 x,y가 이 객체 안에 포함되는가를 알려주는 함수이다.
	 * 이러한 확인은 _region 영역을 이용하여 이루어진다.
	 */
	public boolean onEnter(int x,int y) {
		_focus = null;
		if (_name != null && _name.onEnter(x,y)) {
			_focus = _name;
			return true;
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null && roleName.onEnter(x,y)) {
				_focus = roleName;
				return true;
			}
			Figure mul = tuple.multiplicity;
			if ((mul != null) &&
				(mul.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT)) &&
				(mul.onEnter(x,y))) {
				_focus = mul;
				return true;
			}
			QualificationText qual = tuple.qualification;
			if ((qual != null) &&
				(qual.onEnter(x,y))) {
				_focus = qual;
				return true;
			}
			tuple = _infoTuples.getNext();
		}
		return super.onEnter(x,y);
	}
	/** 이 객체가 현재 화면 상에 나타나는 가를 설정해주는 함수 이다.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		if (_name != null) _name.setInCanvas(flag);
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null) roleName.setInCanvas(flag);
			Figure mul = tuple.multiplicity;
			if (mul != null) mul.setInCanvas(flag);
			QualificationText qual = tuple.qualification;
			if (qual != null) qual.setInCanvas(flag);
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 객체가 주어진 영역안에 포함되는가를 나타내는 함수이다.
	 */
	public boolean checkInRegion(CRgn someregion) {
		if (super.checkInRegion(someregion)) return true;
		if (_name != null && _name.checkInRegion(someregion)) return true;
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while(tuple != null) {
			SingleLineText roleName = tuple.role;
			if (roleName != null && roleName.checkInRegion(someregion))
				return true;
			Figure mul = tuple.multiplicity;
			if (mul != null && mul.checkInRegion(someregion)) return true;
			QualificationText qual = tuple.qualification;
			if (qual != null && qual.checkInRegion(someregion))
				return true;
			tuple = _infoTuples.getNext();
		}
		return false;
	}
	/** 화면에 그려진 관계가 논리적으로 이상하지 않은가를 확인하는 함수이다.
	 */
	public boolean valid(ClassLike clss) {
		// check valid if class names are equal
		// if equal -> false : not valid
		// else -> true : valid
		// this is common for gention,aggtion,coltion
		// but valid for asstion should be made separately
		if (isparentclass(clss)) return false;
		if (validForModelSpecific(clss) == false) return false;
		return true;
	}
	/** 화면에 그려진 관계의 심볼이 이상하지 않은가를 확인하는 함수이다.
	 */
	public boolean validForModelSpecific(ClassLike clss) {
		return true; // OK
	}
	/** 인자에 명시된 클래스가 부모 클래스인가를 확인하는 함수이다.
	 */
	public boolean isparentclass(ClassLike clss) {
		boolean result;
		ClassLike superclass = _classes.top();
		String sname = superclass.getName();
		String name = clss.getName();
		if (superclass == clss || name.equals(sname)) result = true;
		else result = false;
		sname = null;
		name = null;
		return result;
	}
	/** 인자에 명시된 클래스가 선조 클래스인가를 확인하는 함수이다.
	 */
	public boolean hasAncestor(ClassLike tocheck) {
		ClassLike superclass = _classes.top();
		if (superclass != null)
			return superclass.hasAncestor(tocheck);
		else return false;
	}
	/** OMT의 다중성 표현 시에 조그만 원의 가운데 좌표를 찾아내는 함수이다. UML에서는 필요없지만
	 * 만약을 위해 reserve한다.
	 */
	public void findCenterPositionForAPoint(AnyTionInfoTuple tuple,Point p1,Point p2,Point cp) {
		return;
	}
	/** 현재 마우스의 위치에 따른 해당 선분의 꼭지점 정보를 찾아내는 함수이다.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int x,int y) {
		return null;
	}
	/** AITList의 resetRoleNameFor() 함수 참조
	 */
	public void resetRoleNameFor(SingleLineText atext) {
		_infoTuples.resetRoleNameFor(atext);
		return;
	}
	/** AITList의 resetMultiplicityFor() 함수 참조 
	 */
	public void resetMultiplicityFor(SingleLineText atext) {
		_infoTuples.resetMultiplicityFor(atext);
		return;
	}
	/** AITList의 removeTuple() 함수 참조 
	 */
	public void removeTuple(AnyTionInfoTuple tuple) {
		_infoTuples.remove(tuple);
	}
	/** AITList의 replaceClassPtr() 함수 참조 
	 */
	public void replaceClassPtr(ClassLike from,ClassLike to) {
		_classes.replacePtr(from,to);
		_infoTuples.replaceClassPtr(from,to);
	}
	/** AITList의 findTupleFor() 함수 참조 
	 */
	public AnyTionInfoTuple findTupleFor(int x,int y) {
		return _infoTuples.findTupleFor(x,y);
	}
	/** AITList의 findTupleForQual() 함수 참조 
	 */
	public AnyTionInfoTuple findTupleForQual(QualificationText atext) {
		return null;
	}
	/** _classes 리스트의 내용을 비우는 함수이다.
	 */
	public void clearLists() {
		_classes.clear();
	}
	/** 관계에 속하는 심볼 부분을 지우는 가상 함수이다.
	 */
	public void clearModelSpecificSymbol() {
		return;
	}
	/** parameter로 넘겨진 텍스트 객체가 현재 aggregation에서 어떠한 목적으로 사용되는
	 * 텍스트 객체인가를 판별하는 함수이다.
	 */
	public int whatIsMyRole(SingleLineText atext) {
		if (atext == _name) return Const.ANYTIONNAME;
		return 0;
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
		if (totest.afterList().empty() == false &&
			totest.beforeList().empty() == false) return false;
		ClassLike clss = _classes.front();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		totest.line().coords(p1,p2);
		if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y) || 
			CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y))
			return false;
		return true;
	}
	/** 이 관계 객체가 클래스의 이동을 따라갈 필요가 있을때 이동의 시작 시점에서
	 * 따라갈 준비를 하는 함수이다. 주로 클래스를 따라가야 될 선분들을 결정하고
	 * 그 선분들의 상태 값을 초기화 한다.
	 */
	public void followClassProlog(ClassLike clss) {
		resetFlags();
		_focus = null;
		_focusNode = null;
		return;
	}
	/** 이 관계와 연결된 클래스가 움직일 때 그 클래스에 따라서 이동하는 함수이다.
	 */
	public void followClass(Graphics g,int dx,int dy) {
		super.followSomeObject(g,dx,dy);
	}
	/** 이 관계와 연결된 클래스가 움직일 때 그 클래스에 따라서 dx, dy 만큼 이동하는 함수이다.
	 */
	public void followClass(int dx,int dy) {
		super.followSomeObject(dx,dy);
	}
	/** 이 관계 객체의 이동이 모두 끝난 후, 관련되는 flag 값을 리셋하는 함수이다.
	 */
	public void resetMarkForMove() {
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			tuple.mark = false;
			tuple = _infoTuples.getNext();
		}
	}
	/** 이 관계에 대한 편집이 끝난 후에 논리적 일관성을 검사하는 함수이다.
	 */
	public boolean checkConsistency() {
		boolean destroyed = super.epilog(false);
		if (destroyed) return false;
		_controller.makeRegion();
		boolean strange = false;
		strange = tailorEndLinesAll();
		if (strange) {
			return false;
		}
		ClassLike clss = _classes.getFirst();
		while (clss != null) {
			_controller.lowlight(clss);
			clss = _classes.getNext();
		}
		strange = checkIfLinesCrossAnyClassTemplate();
		if (strange) {
			return false;
		}
		strange = setModelSpecificSymbolAll();
		if (strange) {
			return false;
		}
		remake();
		strange = boundaryLineCheck();
		if (strange) { // strange also
			return false;
		}
		return true;
	}
	/** 데이타 멤버 _classes에 대한 access 함수이다.
	 */
	public ClassLikeList classes() {
		return _classes;
	}
	/** 이 관계의 경계선에 위치한 선분들이 인자에 명시된 클래스까지 연결되도록 하는 함수이다.
	 */
	public void extendToClass(ClassLike clss) {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		Point cP = clss.center();
		clss.coords(p1,p2);
		AITList alist = new AITList();
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.classPtr == clss) {
				alist.insert(tuple,0);
			}
			tuple = _infoTuples.getNext();
		}
		tuple = alist.getFirst();
		while (tuple != null) {
			int ex = tuple.x();
			int ey = tuple.y();
			if (ex > cP.x && ey >= p1.y && ey <= p2.y) {
				Line newline = new Line(_controller,cP.x,ey,ex,ey,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				LineNode newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				tuple.setxy(cP.x,ey);
			} else if (ey > cP.y && ex >= p1.x && ex <= p2.x) {
				Line newline = new Line(_controller,ex,cP.y,ex,ey,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				LineNode newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				tuple.setxy(ex,cP.y);
			} else if (ex > p2.x && ey > p2.y) {
				Line newline = new Line(_controller,cP.x,cP.y,ex,cP.y,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				LineNode newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				newline = new Line(_controller,ex,cP.y,ex,ey,null,Const.STRAIGHT);
				newline.setOrient(Const.RESETORIENT);
				newnode = new LineNode(newline,null,null);
				_lines.insert(newnode,0);
				tuple.setxy(cP.x,cP.y);
			} 
			tuple = alist.getNext();
		}
		alist.clear();
		alist.delete();
		remake();
	}
	/** 이 관계에 속하는 경계선 선분이 인자에 명시된 클래스로 부터 떨어지도록 하는 함수이다.
	 */
	public boolean detachFrom(ClassLike clss) {
		AnyTionInfoTuple tuple = _infoTuples.findTupleFor(clss);
		int x = tuple.x();
		int y = tuple.y();
		_focusNode = null;
		_focus = null;
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			if (tmp.beforeList().empty() ||
				tmp.afterList().empty()) {
				Line aLine = tmp.line();
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				aLine.coords(p1,p2);
				if (p1.x == x && p1.y == y) {
					_focusNode = tmp;
					_focus = aLine;
					break;
				}
				if (p2.x == x && p2.y == y) {
					_focusNode = tmp;
					_focus = aLine;
					break;
				}
			}
			tmp = _lines.getNext();
		}
		if (_focus == null || _focusNode == null) {
			_controller.beep("AnyTion.detachFrom");
			return false;
		}
		boolean deleteAll = localDeleteOneLine();
		return deleteAll;
	}
	/** 이 관계에 속하는 선분들 중에서 선택된 한 줄의 선분들을 삭제하는 함수이다.
	 */
	private boolean localDeleteOneLine() {
		LineNode nextNode = null;
		LineNode currentNode = _focusNode;
		Line currentLine = currentNode.line();
		Point tmp1 = new Point(0,0);
		Point tmp2 = new Point(0,0);
		currentLine.coords(tmp1,tmp2);
		int x1 = tmp1.x; int y1 = tmp1.y;
		int x2 = tmp2.x; int y2 = tmp2.y;
		AnyTionInfoTuple tuple = findTupleFor(x1,y1);
		if (tuple != null) {
			SingleLineText text = tuple.role;
			if (text != null) {
				_controller.clear(text,true);
				text.delete();
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				_controller.clear(mul,true);
				mul.delete();
			}
			removeTuple(tuple);
		}
		tuple = findTupleFor(x2,y2);
		if (tuple != null) {
			SingleLineText text = tuple.role;
			if (text != null) {
				_controller.clear(text,true);
				text.delete();
			}
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				_controller.clear(mul,true);
				mul.delete();
			}
			removeTuple(tuple);
		}
		ClassLike clssToRemove = null;
		ClassLike clss = _classes.getFirst();
		while (clss != null) {
			if (CRgn.myPtInRgn(clss.maxRegion(),x1,y1) ||
				CRgn.myPtInRgn(clss.maxRegion(),x2,y2)) {
				clssToRemove = clss;	
				break;
			}
			clss = _classes.getNext();
		}
		while(true) {
			boolean emptyBefore,emptyAfter;
			emptyBefore = currentNode.beforeList().empty();
			emptyAfter = currentNode.afterList().empty();
			if (emptyBefore && emptyAfter) {
				nextNode = null;
				_focusNode = currentNode;
				_focus = currentNode.line();
				_controller.clear(_focus,false);
				removeFocusLine();
				break;
			} else if (emptyBefore == false && emptyAfter) {
				boolean breakflag = false;
				if (currentNode.beforeList().nOfList() > 1) {
					breakflag = true;
				}	
				nextNode = currentNode.beforeList().front();
				_focusNode = currentNode;
				_focus = currentNode.line();
				_controller.clear(_focus,false);
				removeFocusLine();
				if (breakflag) break;
			} else if (emptyBefore && emptyAfter == false) {
				boolean breakflag = false;
				if (currentNode.afterList().nOfList() > 1) {
					breakflag = true;
				}	
				nextNode = currentNode.afterList().front();
				_focusNode = currentNode;
				_focus = currentNode.line();
				_controller.clear(_focus,false);
				removeFocusLine();
				if (breakflag) break;
			} else {
				MySys.myprinterr("Error : unexpected case in AnyTion::localDeleteOneLine().\n");
				break;
			}
			currentNode = nextNode;
		}
		if (nextNode == null) {
			resetFocus();
			clearModelSpecificSymbol();
			_controller.checkInlist(this);
			_controller.remove(this);
			return true;
		} else {
			clssToRemove.removeFromAnytionList(this);
			_classes.remove(clssToRemove,0);
			resetFocus();
			remake();
			_controller.makeRegion(this);
			_controller.lowlight(this);
		}
		return false;
	}
	/** 이 관계의 이름을 명시하는 텍스트 편집 작업을 시작하는 함수이다.
	 */
	public void localMakeName() {
		_controller.lowlight(this);
		_controller._editingTag = true;
		SingleLineText text = getNameText();
		if (text != null) {
			localStartEdit(text);	
			return;
		}
		_controller.setCrossHairCursor();
		GraphicController.FAnyTionEditNameText = true;
		_controller.setEnable(false);
	}
	/** 이 관계의 이름을 명시하는 편집 작업을 시작 하도록 하는 함수이다.
	 */
	public static void editNameText(GraphicController controller,CPoint event) {
		GraphicController.FAnyTionEditNameText = false;
		controller.setEnable(true);
		controller.xUngrabPointer();
		int x = event.x;
		int y = event.y;
		Figure currentFocus = controller.currentFocus();
		controller.remove(currentFocus);
		AnyTion currentTion = (AnyTion)currentFocus;
		Popup popupptr = controller.simplePopup();
		SingleLineText text = new SingleLineText(controller,x,y,popupptr);
		text.setAnytionPtr(currentTion);
		currentTion.setNameText(text);
		currentTion._focus = text;
//		text.screen().activate();
	}
	/** 이 관계의 부속 텍스트들 중에서 인자에 명시된 텍스트가 편집되도록 시작하는 함수이다.
	 */
	public void localStartEdit(Text text) {
		Figure currentFocus = _controller.currentFocus();
		_controller.remove(currentFocus);
		_focus = text;
		_controller.clear(text,false);
		_controller.draw(this);
//		text.screen().activate();
	}
	/** 이 관계에서 선택된 일부분의 선분을 삭제하는 함수이다.
	 */
	public void deleteSegmentFocus() {
		if (whoAreYou().isEqual(Const.IAMASSTION)) return;
		if (_focus == null) return;
		if (_focusNode == null) return;
		if (isBoundary(_focusNode) == false) return;
		_controller.lowlight(this);
		GraphicController controller = _controller;
		boolean deleteAll = localDeleteOneLine();
		if (deleteAll) delete();
		controller.resetCurrentFocus();
	}
	/** 이 관계에서 role name 텍스트 부분을 새로히 만들도록하는 함수이다.
	 */
	public void localMakeRoleName() {
		_controller.lowlight(this);
		Line focusLine = (Line)_focus;
		int popX = _controller.popupX();
		int popY = _controller.popupY();
		AnyTionInfoTuple tuple = getCurrentTuple(focusLine,popX,popY);
		if (tuple == null) {
			_controller.beep("AnyTion.localMakeRoleName");
			return;
		}
		_controller._editingTag = true;
		SingleLineText thisrole = tuple.role;
		if (thisrole != null) {
			localStartEdit(thisrole);
			return;
		}
		_focusTuple = tuple;
		_controller.setCrossHairCursor();
		GraphicController.FAnyTionEditRoleNameText = true;
		_controller.setEnable(false);
	}
	/** 이 관계에서 role name 텍스트 부분을 편집하도록하는 함수이다.
	 */
	public static void editRoleNameText(GraphicController controller,CPoint event) {
		GraphicController.FAnyTionEditRoleNameText = false;
		controller.setEnable(true);
		controller.xUngrabPointer();
		int x = event.x;
		int y = event.y;
		Figure currentFocus = controller.currentFocus();
		controller.remove(currentFocus);
		AnyTion currentTion = (AnyTion)currentFocus;
		Popup popupptr = controller.simplePopup();
		SingleLineText text = new SingleLineText(controller,x,y,popupptr);
		text.setAnytionPtr(currentTion);
		currentTion._focusTuple.role = text;
		currentTion._focusTuple = null;
		currentTion._focus = text;
//		text.screen().activate();
	}
	/** 이 관계에서 다중성 텍스트 부분을 새로히 만들도록하는 함수이다.
	 */
	public void localMakeCardinalText() {
		_controller.lowlight(this);
		Line focusLine = (Line)_focus;
		int popX = _controller.popupX();
		int popY = _controller.popupY();
		AnyTionInfoTuple tuple = getCurrentTuple(focusLine,popX,popY);
		if (tuple == null) {
			_controller.beep("AnyTion.localMakeCardinalText");
			return;
		}
		_controller._editingTag = true;
		SingleLineText thiscardinal = (SingleLineText)tuple.multiplicity;
		if (thiscardinal != null) {
			localStartEdit(thiscardinal);
			return;
		}
		_focusTuple = tuple;
		_controller.setCrossHairCursor();
		GraphicController.FAnyTionEditCardinalText = true;
		_controller.setEnable(false);
	}
	/** 이 관계에서 다중성 텍스트 부분을 편집하도록하는 함수이다.
	 */
	public static void editCardinalText(GraphicController controller,CPoint event) {
		GraphicController.FAnyTionEditCardinalText = false;
		controller.setEnable(true);
		controller.xUngrabPointer();
		int x = event.x;
		int y = event.y;
		Figure currentFocus = controller.currentFocus();
		controller.remove(currentFocus);
		AnyTion currentTion = (AnyTion)currentFocus;
		Popup popupptr = controller.simplePopup();
		SingleLineText text = new SingleLineText(controller,x,y,popupptr);
		text.setAnytionPtr(currentTion);
		currentTion._focusTuple.multiplicity = text;
		currentTion._focusTuple = null;
		currentTion._focus = text;
//		text.screen().activate();
	}
	/** 화면의 확대 이후에 반올림 에러로 인한 이상한 그림이 생기지 않도록 
	 * 이 객체와 관계되는 수치들을 보정해주는 함수이다.
	 */
	/** 3차의 결합 관계 작성시에 세번째 선분들을 그리기 시작하는 함수이다.
	 */
	public static void startForkForTernary(GraphicController controller,CPoint event) {
		AnyTion currentTion = (AnyTion)controller.currentLines();
		if (currentTion == null) {
			controller.beep("AnyTion.startForkForTernary");
			System.exit(0);
		}
		Point point = new Point(0,0);
		int popX = event.x;
		int popY = event.y;
		currentTion.makeRegion();
		if (currentTion.onEnter(popX,popY) == false) {
			controller.beep("Please retry.");
			return;
		}
		GraphicController.FAnyTionWalkCorridor = false;
		GraphicController.FAnyTionStartForkForTernary = false;
		currentTion.calcForkPoint(popX,popY,point,true);
		if (point.x == 0 || point.y == 0) {
			point.x = popX;
			point.y = popY;
			currentTion.setFocus(point.x,point.y);
			if (currentTion._focus == null) 
				currentTion.retrySetFocus(point.x,point.y);
		}
		((AssTion)currentTion).setJoint(point.x,point.y);
		controller.clear(currentTion,false);
		controller.rubberbanding(currentTion);
		controller.warpPointer(point.x,point.y);

		controller.currentOrient = Const.UNDEFINED;
		controller.forkFlag = true;
		GraphicController.FTraceEnterForList = true;
		currentTion.checkNear(point.x,point.y);
		point = currentTion.last();
		Line ptr = new Line(controller,point.x,point.y,null,Const.STRAIGHT);
		currentTion.insert(ptr,true);

		LineNode node = currentTion.node(ptr);
		currentTion._activeLines.insert(node,0);
		ptr.setOrient(Const.UNDEFINED);
		GraphicController.FAnyTionDrawingHandler = true;
		GraphicController.FAnyTionStopDraw = true;
		controller.setCrossHairCursor();
	}
	/** 새로운 관계를 그리기 시작할 때 수행되는 콜백함수이다.
	 */
	public static void makeAnytion(GraphicController controller,int whatTion) {
		controller.readyDraw(whatTion);
		FigureID tmplong = Const.IAMCLASSTEMPLATE.oring(Const.IAMPACKAGE);
		FigureList activeClassList = controller.makeListOf(tmplong);
		int K = 2;
		if (whatTion == Const.ASSTIONPTR2 ||
			whatTion == Const.ASSTIONPTR3) {
			whatTion = Const.ASSTIONPTR;
		}
		if (whatTion == Const.ASSTIONPTR) K = 1;
		if (activeClassList.nOfList() < K) {
			activeClassList.delete();
			activeClassList = null;
			controller.beep("AnyTion.makeAnytion");
			return;
		}
		refineClassList(activeClassList);
		if (activeClassList.nOfList() < K) {
			activeClassList.delete();
			activeClassList = null;
			controller.beep("AnyTion.makeAnytion");
			return;
		}
		controller.setEnable(false);
		controller.currentOrient = Const.UNDEFINED;
		GraphicController.FTraceEnterForList = true;
		controller.setActiveFigures(activeClassList);
		GraphicController.FAnyTionStartDraw = true;
	}
	/** 새로운 관계를 그리기 이전에 관계가 연결될 수 있는 합당한 클래스들만을
	 * 수집하는 함수이다.
	 */
	public static void refineClassList(FigureList activeClassList) {
		ClassLike clss = (ClassLike)activeClassList.getFirst();
		while (clss != null) {
			String name = clss.getName();
			String nullStr = new String("");
			if (name.equals(nullStr)) {
				activeClassList.remove((Figure)clss);
				clss = (ClassLike)activeClassList.getFirst();
				name = null;
				nullStr = null;
				continue;
			}
			clss = (ClassLike)activeClassList.getNext();
			name = null;
			nullStr = null;
		}
	}
	/** 새로운 관계의 작성이 선택된 이후에 처음으로 마우스 버튼이 눌려졌을 때
	 * 호출되는 함수이다. 이 함수에 의하여 그림이 그려지기 시작한다.
	 */
	public static void startDraw(GraphicController controller,CPoint event,int whatTion) {
		if (controller.currentFocus() == null) {
			controller.beep("AnyTion.startDraw");
			return;
		}
		controller.lowlight(controller.currentFocus());
		int x = event.x;
		int y = event.y;
		controller.setCurrentXY(x,y);
		Line ptr = new Line(controller,x,y,null,Const.STRAIGHT);
		if (whatTion == Const.GENTIONPTR) {
			Popup popupptr = controller.gentionPopup();
			GenTion aTion = new GenTion(controller,popupptr,ptr);
			controller.setCurrentLines(aTion);
		} else if (whatTion == Const.AGGTIONPTR) {
			Popup popupptr = controller.aggtionPopup();
			AggTion aTion = new AggTion(controller,popupptr,ptr);
			controller.setCurrentLines(aTion);
		} else if (whatTion == Const.COLTIONPTR) {
			Popup popupptr = controller.coltionPopup();
			ColTion aTion = new ColTion(controller,popupptr,ptr);
			controller.setCurrentLines(aTion);
		} else if (whatTion == Const.ASSTIONPTR2 ||
				   whatTion == Const.ASSTIONPTR3) {
			Popup popupptr = controller.asstionPopup();
			AssTion aTion = new AssTion(controller,popupptr,ptr);
			if (whatTion == Const.ASSTIONPTR2) {
				aTion.setCategory(AssTion.BINARY);
			} else {
				aTion.setCategory(AssTion.TERNARY);
			}
			controller.setCurrentLines(aTion);
		}	
		AnyTion tmpPtr = (AnyTion) controller.currentLines();
		ClassLike clssPtr = (ClassLike)controller.currentFocus();
		clssPtr.attachToAnytionList(tmpPtr);	
		tmpPtr._classes.insert(clssPtr,Const.ABSOLUTELY);

		LineNode node = controller.currentLines().node(ptr);
		((AnyTion)controller.currentLines())._activeLines.insert(node,0);
		ptr.setOrient(Line.invertOrient(controller.currentOrient));
		GraphicController.FAnyTionStopDraw = true;
		GraphicController.FAnyTionDrawingHandler = true;
		GraphicController.FAnyTionStartDraw = false;
		controller.setCrossHairCursor();
	}
	/** 관계를 위한 선분이 그려지기 시작한 이후에 마우스 이동 시에 호출되는 함수이다.
	 * 이 함수는 그리기를 위한 rubberbanding을 수행한다.
	 */
	public static void drawingHandler(GraphicController controller,CPoint event) {
		int x = event.x;
		int y = event.y;
		if (controller.currentX() == x && controller.currentY() == y) return;
		controller.setCurrentXY(x,y);
		Figure saved = controller.currentFocus();
		controller.setCurrentFocus(controller.currentLines());
		boolean moved = controller.checkLimitForFigure(x,y,null);
		controller.setCurrentFocus(saved);
		if (moved) {
			controller.currentLines().setXY2ForResize(x,y);
			controller.rubberbanding(controller.currentLines());
			return;
		}
		Graphics rg = controller.getgraphics();
		rg.setXORMode(controller.getBackground());
		controller.currentLines().drawing(rg,x,y,false);
		rg.dispose();
	}
	/** 선분의 작성 이후에 마우스의 버튼이 release 되면 호출되는 함수이다.
	 * 이 함수는 현재 선분의 그리기를 마친다.
	 */
	public static void stopDraw(GraphicController controller,CPoint event) {
		AnyTion figureptr = (AnyTion)controller.currentLines();
		Line aline = (Line)figureptr.focus();
		aline.setOrient(Const.RESETORIENT);
		controller.currentOrient = aline.orient();
		int x,y;
		Point t = figureptr.last();
		x = t.x; y = t.y;
		controller.warpPointer(x,y);
		controller.xUngrabPointer();
		GraphicController.FAnyTionDrawingHandler = false;	
		GraphicController.FAnyTionStopDraw = false;	
		GraphicController.FFixPointerAbsolute = true;
		AnyTion currentTion = (AnyTion)controller.currentLines();
		if (currentTion.ifAllConstraintSatisfied(x,y) == false) {
			boolean abortAll =
							   currentTion.abortDraw();
			if (abortAll) {
				currentTion.delete();
			}
			controller.setCurrentLines(null);
			controller.setCurrentFocus(null);
			return;
		}

		if (controller.currentFocus() == null) {
			GraphicController.FAnyTionContinueDraw = true;
		} else { // we must stop 
			ClassLike currentclass = (ClassLike)controller.currentFocus();
			controller.lowlight(currentclass);
			currentTion.clearActiveLines();
			if (currentTion.valid(currentclass)) {
				currentTion.tailorEndLines();	
				boolean strange =
								 currentTion.setModelSpecificSymbol(currentclass);
				if (strange) {
					controller.resetActiveFigures();
					boolean abortAll =
									   currentTion.abortDraw();	
					if (abortAll) {
						currentTion.delete();
					}
					controller.setCurrentLines(null);
					controller.setCurrentFocus(null);
					return;
				}
			} else {
				boolean abortAll =
								   currentTion.abortDraw();
				if (abortAll) {
					currentTion.delete();
				}
				controller.setCurrentLines(null);
				controller.setCurrentFocus(null);
				return;
			}
			if (currentTion.whoAreYou().isEqual(Const.IAMASSTION) &&
				currentTion.category() == AssTion.TERNARY &&
				currentTion._classes.nOfList() == 1) {
				// we should continue for ternary relationship
				currentclass.attachToAnytionList(currentTion);
				currentTion._classes.insert(currentclass,Const.ABSOLUTELY);
				currentTion.makeRegion();
				GraphicController.FFixPointerAbsolute = false;
				GraphicController.FTraceEnterForList = false;
				GraphicController.FAnyTionWalkCorridor = true;
				GraphicController.FAnyTionStartForkForTernary = true;
				Point cP = currentTion._activeLines.rear().line().center();
				controller.rubberbanding(currentTion);
				controller.warpPointer(cP.x,cP.y);
				controller.setCrossHairCursor();
				controller.setCurrentFocus(null);
				return;
			}
			controller.resetActiveFigures();
			currentTion._activeLines.clear();
			currentTion.resetFocus();
			currentTion.epilog(true);
			currentclass.attachToAnytionList(currentTion);
			currentTion._classes.insert(currentclass,Const.ABSOLUTELY);
			currentTion.makeRegion();
			controller.insert(currentTion);
			controller.lowlight(currentTion);
			controller.setEnable(true);
			GraphicController.FFixPointerAbsolute = false;
			GraphicController.FTraceEnterForList = false;
			controller.forkFlag = false;
			controller.setCurrentLines(null);
			controller.setCurrentFocus(null);
		}
	}
	/** 이 함수는 그려진 선분에 대하여 모든 제약 조건이 만족되는 가를 확인하는 함수이다.
	 */
	private boolean ifAllConstraintSatisfied(int x,int y) {
		// check if the first line is in the major class 
		if (_lines.nOfList() == 1) {
			if (CRgn.myPtInRgn(_classes.top().maxRegion(),x,y))
				return false;
		}

		// check if this line cross any class template 
		((Line)_focus).makeSmallRegion();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		((Line)_focus).coords(p1,p2);
		int x1,y1,x2,y2;
		x1 = p1.x; y1 = p1.y;
		x2 = p2.x; y2 = p2.y;
		ClassLike clss = (ClassLike)_controller.activeFigures().getFirst();
		while(clss != null) {
			if ((!CRgn.myPtInRgn(clss.region(),x1,y1))
				&& (!CRgn.myPtInRgn(clss.region(),x2,y2))
				&& (CRgn.doesIntersect(clss.region(),_focus.region())))
				return false;
			clss = (ClassLike)_controller.activeFigures().getNext();
		}

		// check if focus line cross other lines of this Tion 
		int orientToCheck = Line.invertOrient(((Line)_focus).orient());
		if (orientToCheck != Const.UNDEFINED) {
			LineNode lineNode = _lines.getFirst();
			while (lineNode != null) {
				Line line = lineNode.line();
				if (line != _focus &&
					orientToCheck == line.orient() &&
					lineNode.neighbor(_focusNode) == false &&	
					((Line)_focus).doesCross(line)) {
					return false;
				}
				lineNode = _lines.getNext();
			}
		}

		return true;
	}
	/** 이 함수는 선분의 그리기 과정에서 제약 조건을 위반했을 때 그림 그리기를
	 * 멈추게하는 함수이다.
	 */
	private boolean abortDraw() {
		_controller.forkFlag = false;
		LineNode tmp = _activeLines.getFirst();
		while(tmp != null) {
			_controller.clear(tmp.line(),false);
			tmp = _activeLines.getNext();
		}
		while((tmp = _activeLines.pop()) != null) {
			Line aline = tmp.line();
			tmp.delete();
			aline.delete();
			_lines.remove(tmp,true);
		}
		_activeLines.clear();
		resetFocus();
		epilog(true);
		_controller.makeRegion(this);
		_controller.lowlight(this);
		_controller.isFixed = false;
		_controller.setEnable(true);
		GraphicController.FAnyTionContinueDraw = false;
		GraphicController.FFixPointerAbsolute = false;
		GraphicController.FTraceEnterForList = false;
		if (_lines.nOfList() == 0) {
			_controller.checkInlist(this);
			_controller.remove(this);
			ClassLike ptr = _classes.getFirst();
			while(ptr != null) {
				ptr.removeFromAnytionList(this);
				ptr = _classes.getNext();
			}
			return true;
		} else {
			if (whoAreYou().isEqual(Const.IAMASSTION)) {
				_controller.clear(this,true);
				_controller.checkInlist(this);
				_controller.remove(this);
				ClassLike ptr = _classes.getFirst();
				while(ptr != null) {
					ptr.removeFromAnytionList(this);
					ptr = _classes.getNext();
				}
				return true;
			}
			return false;
		}
	}
	/** 이 함수는 관계의 작성 시에 다시 버튼을 누름으로써 다음 선분을 그리게하는
	 * 함수이다.
	 */
	public static void continueDraw(GraphicController controller) {
		int x = controller.currentX();
		int y = controller.currentY();
		if (controller.currentLines().focus() == null) {
			controller.currentLines().setFocus(x,y);
			if (controller.currentLines().focus() == null) {
				boolean abortAll =
								   ((AnyTion)controller.currentLines()).abortDraw();
				if (abortAll) {
					controller.currentLines().delete();
				}
				controller.setCurrentLines(null);
				controller.setCurrentFocus(null);
				controller.xUngrabPointer();
				return;
			}
		}
		controller.currentLines().checkNear(x,y);
		Point p = controller.currentLines().last();
		x = p.x; y = p.y;
		Line ptr = new Line(controller,x,y,null,Const.STRAIGHT);
		controller.currentLines().insert(ptr,true);
		LineNode node = controller.currentLines().node(ptr);
		((AnyTion)controller.currentLines())._activeLines.insert(node,0);
		ptr.setOrient(Line.invertOrient(controller.currentOrient));
		GraphicController.FAnyTionContinueDraw = false;
		GraphicController.FFixPointerAbsolute = false;
		controller.isFixed = false;
		GraphicController.FAnyTionDrawingHandler = true;
		GraphicController.FAnyTionStopDraw = true;
		controller.setCrossHairCursor();
	}
	/** 이 함수는 모서리의 경계선들을 클래스의 영역을 고려하여 적당한 길이로
	 * 조정하는 함수이다.
	 */
	protected void tailorEndLines() {
		if (_lines.nOfList() == _activeLines.nOfList()) {
			// first drawing case
			ClassLike majorclass = _classes.front();
			Line startLine = _activeLines.front().line();
			majorclass.adjustLine(startLine,true);
			ClassLike minorclass = (ClassLike)_controller.currentFocus();
			Line lastLine = _activeLines.rear().line();
			boolean obsoleteFlag = minorclass.adjustLine(lastLine,false);
			if (obsoleteFlag) {
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				lastLine.coords(p1,p2);
				lastLine.setXY2(p1.x,p1.y);
			}
		} else {
			// forking case
			ClassLike minorclass = (ClassLike)_controller.currentFocus();
			Line lastLine = _activeLines.rear().line();
			boolean obsoleteFlag = minorclass.adjustLine(lastLine,false);
			if (obsoleteFlag) {
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				lastLine.coords(p1,p2);
				lastLine.setXY2(p1.x,p1.y);
			}
		}
	}
	/** 객체의 이동이나 편집 이후에 모서리의 경계선 길이 모두를 일괄적으로 조정해주는 
	 * 함수이다.
	 */
	public boolean tailorEndLinesAll() {
		ClassLike clss = _classes.getFirst();
		boolean breakflag = false;
		while(clss != null) {
			LineNode tmp = _lines.getFirst();
			while(tmp != null) {
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				boolean obsolete;
				tmp.line().coords(p1,p2);
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y) && tmp.beforeList().empty()) {
					AnyTionInfoTuple tuple = findTupleFor(p1.x,p1.y);
					if (tuple.classPtr == clss) {
						obsolete = clss.adjustLine(tmp.line(),true);
						Point q1 = new Point(0,0);
						Point q2 = new Point(0,0);
						tmp.line().coords(q1,q2);
						if (obsolete) {
							tuple.setxy(q2.x,q2.y);
						} else {
							tuple.setxy(q1.x,q1.y);
						}
						if (obsolete) {
							_focusNode = tmp;
							_focus = tmp.line();
							removeFocusLine();
							breakflag = true;
							break;
						}
					}
				} 
				if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y) && tmp.afterList().empty()) {
					AnyTionInfoTuple tuple = findTupleFor(p2.x,p2.y);
					if (tuple.classPtr == clss) {
						obsolete = clss.adjustLine(tmp.line(),false);
						Point q1 = new Point(0,0);
						Point q2 = new Point(0,0);
						tmp.line().coords(q1,q2);
						if (obsolete) {
							tuple.setxy(q1.x,q1.y);
						} else {
							tuple.setxy(q2.x,q2.y);
						}
						if (obsolete) {
							_focusNode = tmp;
							_focus = tmp.line();
							removeFocusLine();
							breakflag = true;
							break;
						}
					}
				}
				tmp = _lines.getNext();
			}
			if (breakflag) {
				breakflag = false;
				clss = _classes.getFirst();
			} else {
				clss = _classes.getNext();
			}
		}
		if (_lines.nOfList() == 0) return true;
		return false;
	}
	/** 표기법 객체의 편집이 끝난 이후에 호출되는 함수로서 일관성 검사,
	 * 수치 보정들을 수행한다.
	 */
	public boolean epilog(boolean skipflag) {
		Line savedFocus = (Line)_focus;
		boolean destroyed = super.epilog(false);
		if (destroyed) return true;
		if (savedFocus != null) return destroyed;
		if (skipflag) return destroyed;
		_controller.makeRegion();
		boolean strange = false;
		strange = tailorEndLinesAll();
		if (strange) {
			return false;
		}
		ClassLike clss = _classes.getFirst();
		while (clss != null) {
			_controller.lowlight(clss);
			clss = _classes.getNext();
		}
		strange = checkIfLinesCrossAnyClassTemplate();
		if (strange) {
			return false;
		}
		strange = setModelSpecificSymbolAll();
		if (strange) {
			return false;
		}
		remake();
		strange = boundaryLineCheck();
		if (strange) { // strange also
			return false;
		}
		BackUp.delete();
		BackUp = null;
		return false;
	}
	/** 일관성 검사의 한 경우로서 이 관계에 속하는 선분이 클래스 객체를 횡단하는
	 * 가를 검사하는 함수이다.
	 */
	protected boolean checkIfLinesCrossAnyClassTemplate() {
		LineNode tmp = _lines.getFirst();
		while (tmp != null) {
			tmp.line().makeSmallRegion();
			ClassLike clss = _classes.getFirst();
			while (clss != null) {
				CRgn checkRegion = CRgn.myCreateRgn();
				CRgn.myIntersectRgn(tmp.line().maxRegion(),clss.region(),checkRegion);
				if (CRgn.myEmptyRgn(checkRegion) == false) {
					CRgn.myDestroyRgn(checkRegion);
					return true;
				}
				CRgn.myDestroyRgn(checkRegion);
				clss = _classes.getNext();
			}
			tmp = _lines.getNext();
		}
		return false;
	}
	/** 일관성 검사의 한 경우로서 이 관계에 속하는 선분중에서 모서리에 있는 경계선이
	 * 이 정확한가를 검사하는 함수이다.
	 */
	private boolean boundaryLineCheck() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		ClassLike clss;
		int boundaryLines = 0;
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.beforeList().empty()) {
				boundaryLines++;
				tmp.line().coords(p1,p2);
				boolean meetOne = false;
				clss = _classes.getFirst();
				while(clss != null) {
					if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
						meetOne = true;
						break;
					}
					clss = _classes.getNext();
				}
				if (meetOne == false) return true;
			}
			if (tmp.afterList().empty()) {
				boundaryLines++;
				tmp.line().coords(p1,p2);
				boolean meetOne = false;
				clss = _classes.getFirst();
				while(clss != null) {
					if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
						meetOne = true;
						break;
					}
					clss = _classes.getNext();
				}
				if (meetOne == false) return true;
			}
			tmp = _lines.getNext();
		}
		if (boundaryLines != _classes.nOfList()) return true;
		return false;
	}
	/** 화일의 로드 이후에 관련 클래스의 레퍼런스를 일관성있게 조정하는 함수이다.
	 */
	public void makeConsistent(Figure oldfig) {
		ClassLike clss = _classes.getFirst();
		while(clss != null) {
			boolean replaced =
							  clss.replaceAnytionPtr((AnyTion)oldfig,this);
			_controller.lowlight(clss);
			clss = _classes.getNext();
		}	
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		AnyTion copied;
		if (ptr == null) {
			copied = new AnyTion(_controller,null,null);
		} else {
			copied = (AnyTion)ptr;
		}	
		if (_name != null) {
			copied._name = (SingleLineText)_name.born(null);
			copied._name.setAnytionPtr(copied);
		}
		copied._classes.copy(_classes);
		AnyTionInfoTuple tmp = _infoTuples.getFirst();
		while (tmp != null) {
			AnyTionInfoTuple aptr = new AnyTionInfoTuple(tmp);
			if (tmp.role != null) {
				aptr.role = (SingleLineText)tmp.role.born(null);
				aptr.role.setAnytionPtr(copied);
			}
			if (tmp.multiplicity != null) {
				aptr.multiplicity = (SingleLineText)tmp.multiplicity.born(null);
				if (aptr.multiplicity.whoAreYou().isEqual(Const.IAMSINGLELINETEXT)) {
					((SingleLineText)aptr.multiplicity).setAnytionPtr(copied);
				}
			}
			if (tmp.qualification != null) {
				aptr.qualification = (QualificationText)tmp.qualification.born(null);
				aptr.qualification.setAsstionPtr((AssTion)copied);
			}
			copied._infoTuples.insert(aptr,0);
			tmp = _infoTuples.getNext();
		}
		return(super.born((Figure)copied));
	}
	/** 이 관계의 선분 모양을 바꾸기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
	 */
	public void resizeProlog(boolean makeBackUp) {
		if (makeBackUp == false) {
			resetHeadType();
			super.resizeProlog(true);
			return;
		}
		resetFlags();
		resetHeadType();
		if (BackUp != null) {
			BackUp.delete();
		}
		BackUp = born(null);
		_focusNode.makeCascadeFollow(_controller,_lines,_type);
		if (_focusNode.beforeList().empty()) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			((Line)_focus).coords(p1,p2);
			Line newline = new Line(_controller,p1.x,p1.y,null,Const.STRAIGHT);
			newline.setOrient(Line.invertOrient(((Line)_focus).orient()));
			LineNode newnode = new LineNode(newline,null,null);
			newnode.cascade = true;
			newnode.afterList().insert(_focusNode,0);
			_focusNode.beforeList().insert(newnode,0);
			_lines.insert(newnode,0);
		}
		if (_focusNode.afterList().empty()) {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			((Line)_focus).coords(p1,p2);
			Line newline1 = new Line(_controller,p2.x,p2.y,null,Const.STRAIGHT);
			newline1.setOrient(Line.invertOrient(((Line)_focus).orient()));
			LineNode newnode1 = new LineNode(newline1,null,null);
			newnode1.beforeList().insert(_focusNode,0);
			_focusNode.afterList().insert(newnode1,0);
			_lines.insert(newnode1,0);
			Line newline2 = new Line(_controller,p2.x,p2.y,null,Const.STRAIGHT);        
			newline2.setOrient(((Line)_focus).orient());          
			LineNode newnode2 = new LineNode(newline2,null,null);            
			newnode2.cascade = true;             
			newnode2.afterList().insert(newnode1,0);      
			newnode1.beforeList().insert(newnode2,0);      
			_lines.insert(newnode2,0);               
			return;
		}
		LineNodeList afters = _focusNode.afterList();
		LineNode ptr = afters.getFirst();
		while (ptr != null) {
			ptr.makeCascadeFollow(_controller,_lines,_type);
			if (ptr.beforeList().empty()) {
				Point q1 = new Point(0,0);
				Point q2 = new Point(0,0);
				ptr.line().coords(q1,q2);
				Line newline = new Line(_controller,q1.x,q1.y,null,Const.STRAIGHT);
				newline.setOrient(Line.invertOrient(ptr.line().orient()));
				LineNode newnode = new LineNode(newline,null,null);
				newnode.cascade = true;
				newnode.afterList().insert(ptr,0);
				ptr.beforeList().insert(newnode,0);
				_lines.insert(newnode,0);
			}
			ptr = afters.getNext();
		}
	}
	/** 이 관계의 선분 모양을 이동하기 시작할 때 사전 조치로 필요한 상태값을 설정하는 함수이다.
	 */
	public boolean moveProlog(int oneorsomeptr) {
		if (oneorsomeptr == 0) {
			_controller.beep("AnyTion.moveProlog");
			return false;
		}
		_controller.clear(this,true);
		_moveAllFlag = false;
		resetFlags();
		resetHeadType();
		if (BackUp != null) {
			BackUp.delete();
		}
		BackUp = born(null);
		if (oneorsomeptr == Const.ONEPTR) {
			_focusNode.move = true;
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			((Line)_focus).coords(p1,p2);
			_focusNode.checkNearForFollow(_controller,_lines,_type,p1.x,p1.y,Const.ATBEFORE);
			_focusNode.checkNearForFollow(_controller,_lines,_type,p2.x,p2.y,Const.ATAFTER);
			LineNodeList tmpList = new LineNodeList();
			LineNode tmp = _lines.getFirst();
			while (tmp != null) {
				Point q1 = new Point(0,0);
				Point q2 = new Point(0,0);
				tmp.line().coords(q1,q2);
				if (tmp.beforeList().empty()) {
					Line newline = new Line(_controller,q1.x,q1.y,null,Const.STRAIGHT);
					newline.setOrient(Line.invertOrient(tmp.line().orient()));
					LineNode newnode = new LineNode(newline,null,null);
					newnode.cascade = true;
					newnode.afterList().insert(tmp,0);
					tmp.beforeList().insert(newnode,0);
					tmpList.insert(newnode,0);
				} 
				if (tmp.afterList().empty()) {
					Line newline = new Line(_controller,q2.x,q2.y,null,Const.STRAIGHT);
					newline.setOrient(Line.invertOrient(tmp.line().orient()));
					LineNode newnode = new LineNode(newline,null,null);
					newnode.cascade = true;
					newnode.beforeList().insert(tmp,0);
					tmp.afterList().insert(newnode,0);
					tmpList.insert(newnode,0);
				}
				tmp = _lines.getNext();
			}
			tmp = tmpList.getFirst();
			while(tmp != null) {
				_lines.insert(tmp,0);
				tmp = tmpList.getNext();
			}
			tmpList.delete();
		} else { // SOMEPTR
			_focusNode.move = true;
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			((Line)_focus).coords(p1,p2);
			int orient = ((Line)_focus).orient();
			if (orient == Const.NORTH) {
				LineNode tmp = _lines.getFirst();
				while (tmp != null) {
					Point q1 = new Point(0,0);
					Point q2 = new Point(0,0);
					tmp.line().coords(q1,q2);
					if (p1.x == q1.x && orient == tmp.line().orient()) { 
						tmp.move = true;
					}
					tmp = _lines.getNext();
				}
			} else if (orient == Const.EAST) {
				LineNode tmp = _lines.getFirst();
				while (tmp != null) {
					Point q1 = new Point(0,0);
					Point q2 = new Point(0,0);
					tmp.line().coords(q1,q2);
					if (p1.y == q1.y && orient == tmp.line().orient()) { 
						tmp.move = true;
					}
					tmp = _lines.getNext();
				}
			}
			checkNearForFollow();
			LineNodeList tmpList = new LineNodeList();
			LineNode tmp = _lines.getFirst();
			while (tmp != null) {
				Point q1 = new Point(0,0);
				Point q2 = new Point(0,0);
				tmp.line().coords(q1,q2);
				if (tmp.beforeList().empty()) {
					Line newline = new Line(_controller,q1.x,q1.y,null,Const.STRAIGHT);
					newline.setOrient(Line.invertOrient(tmp.line().orient()));
					LineNode newnode = new LineNode(newline,null,null);
					newnode.cascade = true;
					newnode.afterList().insert(tmp,0);
					tmp.beforeList().insert(newnode,0);
					tmpList.insert(newnode,0);
				}
				if (tmp.afterList().empty()) {
					Line newline = new Line(_controller,q2.x,q2.y,null,Const.STRAIGHT);
					newline.setOrient(Line.invertOrient(tmp.line().orient()));
					LineNode newnode = new LineNode(newline,null,null);
					newnode.cascade = true;
					newnode.beforeList().insert(tmp,0);
					tmp.afterList().insert(newnode,0);
					tmpList.insert(newnode,0);
				}
				tmp = _lines.getNext();
			}
			tmp = tmpList.getFirst();
			while(tmp != null) {
				_lines.insert(tmp,0);
				tmp = tmpList.getNext();
			}
			tmpList.delete();
		}
		return true;
	}
	/** 기존 관계에 새로운 선분을 추가할 때 사용되는 콜백 함수이다.
	 */
	public static void doForkCallback(GraphicController controller,int whatTion) {
		AnyTion currentTion = (AnyTion)controller.currentFocus();
		if (currentTion == null) {
			controller.beep("AnyTion.doForkCallback");
			return;
		}
		FigureID tmplong = Const.IAMCLASSTEMPLATE.oring(Const.IAMPACKAGE);
		FigureList activeClassList = controller.makeListOf(tmplong);
		refineClassList(activeClassList);

		controller.lowlight(currentTion);
		Point point = new Point(0,0);
		int popX = controller.popupX();
		int popY = controller.popupY();
		currentTion.calcForkPoint(popX,popY,point,true);
		controller.warpPointer(point.x,point.y);
		ClassLike clss = currentTion._classes.getFirst();
		while(clss != null) {
			if (CRgn.myPtInRgn(clss.maxRegion(),point.x,point.y)) {
				controller.beep("AnyTion.doForkCallback");
				activeClassList.delete();
				activeClassList = null;
				currentTion.resetFocus();
				currentTion.epilog(true);
				return;
			}
			clss = currentTion._classes.getNext();
		}
		controller.setEnable(false);
		controller.currentOrient = Const.UNDEFINED;
		controller.setCurrentLines((Lines)currentTion);
		controller.forkFlag = true;
		GraphicController.FFixPointerAbsolute = true;
		GraphicController.FTraceEnterForList = true;
		controller.setActiveFigures(activeClassList);
		GraphicController.FAnyTionContinueDraw = true;
	}
}