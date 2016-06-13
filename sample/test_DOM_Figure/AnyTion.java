package figure;

import java.awt.*;
import java.io.*;
import java.awt.Point;

/** �� Ŭ������ �߻� Ŭ�����μ� AggTion, GenTion, AssTion, ColTion Ŭ������ �����ؾ� �Ǵ� ���񽺵� �߿���
 * ���������� �ʿ��� ��ɵ��� ó���ϴ� Ŭ�����̴�. ���� AggTion, GenTion, AssTion, ColTion Ŭ�������� �� Ŭ����
 * �� ���� �Ļ��ȴ�. �Ϲ����� "����" ��ü�� ���� Ŭ������� �� �� �ִ�.
 */
public
	class AnyTion extends Lines {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -7320814502800479715L;
	/** �� ���谡 ����Ǵ� Ŭ���� ��ü�� ��Ű�� ��ü���� ����Ʈ�� �����ϴ� ����Ʈ ��ü�̴�.
	 */
	protected ClassLikeList _classes;
	/** �� ���谡 Ŭ���� ��ü�� ������ ������ �鿡 ���� ������ �����ϴ� ����Ʈ ��ü�̴�.
	 * ������ ������ ���� �ڼ��� ������ AnyTionInfoTuple Ŭ������ �����ϸ� �ȴ�. 
	 */
	protected AITList _infoTuples;
	/** �� ������ �̸��� �����ϴ� �ؽ�Ʈ ��ü�̴�.
	 */
	protected SingleLineText _name;
	/** ���� ���谡 �׷����� �ִ� ��쿡 ������ ������� ���е��� ����ϴ� ����Ʈ ��ü�̴�.
	 * �� ����Ʈ�� ���߿� �׸� �׸��Ⱑ ������ ��� ���� ���� ���е��� ���ֱ� ���� ���ȴ�.
	 */
	protected LineNodeList _activeLines;
	/** �� ���迡�� ������ ������ ���п� �ش��ϴ� ������ ������ ��Ÿ���� ��ü�̴�.
	 */
	protected transient AnyTionInfoTuple _focusTuple;
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
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
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController controller) {
		super.setController(controller);
		_infoTuples.setController(controller);
		if (_name != null) _name.setController(controller);
	}
	/** �������̴�.
	 */
	public AnyTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,Const.STRAIGHT,line);
		_classes = new ClassLikeList();
		_activeLines = new LineNodeList();
		_infoTuples = new AITList();
		_name = null;
		_focusTuple = null;
	}
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public void drawDots(Graphics g) {
		super.drawDots(g);
		if (_name != null) _name.drawDots(g);
	}
	/** �� ������ �̸��� �����ϴ� �ؽ�Ʈ ��ü�� access�ϴ� �Լ��̴�.
	 */
	public SingleLineText getNameText() {
		return _name;
	}
	/** �� ������ �̸��� ��Ÿ���� �ؽ�Ʈ ��ü�� ���� �����ϴ� �Լ��̴�.
	 */
	public void setNameText(SingleLineText name) {
		_name = name;
	}
	/** ���� ���콺�� �������� �� ���� ��ü�� ������ ���ΰ� Ȥ��  �̸� �κ��̳�, ���߼� �κ���
	 * ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantMoveFocus(CPoint point) {
		if (_focus == null) return false;
		if (_focus != _name) return false;
		return true;
	}
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public void draw(Graphics g,int style,Graphics specialdcp) {
		super.draw(g,style,specialdcp);
		if (_name != null) _name.draw(g,style,specialdcp);
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
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
	/** �� �Լ��� �����Լ��μ� ���� ���迡 Ư���� �߰��Ǵ� �ɺ��� ���̸� �����ִ� �Լ��̴�.
	 * ���� ���, GenTion�� ��쿡�� �ﰢ���� ����, AggTion�� ��쿡�� ���̾Ƹ���� ���� ���� �����ش�.
	 * �� �Լ��� ������ �׸��� �̻��ϰ� �׷����� ���� Ȯ���ϴ� ���̴�.
	 */
	public int symbolLength() {
		return 0;
	}
	/** Ŭ������ �̵� ���� �� ���� ��ü�� �ɺ��� �����
	 * ���� �Լ��̴�.
	 */
	public void redrawForArrow() {
		return;
	}
	/** ������ �� �κ��� Ȯ����Ѽ� �����Ǵ� Ŭ���� ���ο� ������ ���Եǵ����ϴ� �Լ��̴�.
	 * �� �Լ��� �׸� ��ҿ� Ȯ�� �ÿ� �̻��� �׸��� ��������� �ʵ����ϴ� ������ �Ѵ�.
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
	/** ������ �� �κ��� Ȯ����Ѽ� �����Ǵ� Ŭ���� ���ο� ������ ���Եǵ����ϴ� �Լ��̴�.
	 * �� �Լ��� ���ڵ��� ������ Ȯ����Ѿ� �ϴ� �������� �ش��ϴ� Ŭ���� ��ü�� ��Ű�� ��ü�̴�.
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
	/** �� �Լ��� ���ڿ� ��õ� Ŭ���� ��ü�� ������ ��쿡 �� Ŭ������ ���ϴ�
	 * ������ �� �κ��� ������ ũ��� �����ϴ� �Լ��̴�.
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
	/** ���� ���迡 ���� �ۼ��� �������� �ۼ��� ������ ��� ������ ��������� ���е���
	 * �����ϴ� ����Ʈ ��ü�� ���� �Լ��̴�.
	 */
	private void clearActiveLines() {
		LineNode tmp = _activeLines.getFirst();
		while(tmp != null) {
			_controller.clear(tmp.line(),false);
			tmp = _activeLines.getNext();
		}
	}
	/** Ŭ�������� �����ϴ� ����Ʈ���� �ش� Ŭ���� ��ü�� ���۷����� �����ϴ� �Լ��̴�.
	 */
	public void removeFromClasses(ClassLike clss) {
		_classes.remove(clss,Const.ABSOLUTELY);
	}
	/** ������ ���� �ɺ��� �� �����ϴ� ���� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbol(ClassLike clss) {
		return true; // seems strange
	}
	/** ���踦 ���� �ɺ��� �ϰ������� �� �����ϴ� ���� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbolAll() {
		return true;
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
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
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
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
	/** �� ���谡 association �� ��� ���� �����ΰ� ���� �����ΰ��� �˷��ִ� �Լ��̴�.
	 */
	public int category() {
		return 0;
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� �� �Լ��� ���Ͽ� GraphicController ��ü��
	 * �ִ� �ּ� ��ǥ���� �ݿ��ȴ�.
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
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� ���ڵ� ���� �ݿ��Ǿ� ��������.
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
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
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
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
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
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
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
	/** �� ��ü�� �־��� �����ȿ� ���ԵǴ°��� ��Ÿ���� �Լ��̴�.
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
	/** ȭ�鿡 �׷��� ���谡 �������� �̻����� �������� Ȯ���ϴ� �Լ��̴�.
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
	/** ȭ�鿡 �׷��� ������ �ɺ��� �̻����� �������� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean validForModelSpecific(ClassLike clss) {
		return true; // OK
	}
	/** ���ڿ� ��õ� Ŭ������ �θ� Ŭ�����ΰ��� Ȯ���ϴ� �Լ��̴�.
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
	/** ���ڿ� ��õ� Ŭ������ ���� Ŭ�����ΰ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean hasAncestor(ClassLike tocheck) {
		ClassLike superclass = _classes.top();
		if (superclass != null)
			return superclass.hasAncestor(tocheck);
		else return false;
	}
	/** OMT�� ���߼� ǥ�� �ÿ� ���׸� ���� ��� ��ǥ�� ã�Ƴ��� �Լ��̴�. UML������ �ʿ������
	 * ������ ���� reserve�Ѵ�.
	 */
	public void findCenterPositionForAPoint(AnyTionInfoTuple tuple,Point p1,Point p2,Point cp) {
		return;
	}
	/** ���� ���콺�� ��ġ�� ���� �ش� ������ ������ ������ ã�Ƴ��� �Լ��̴�.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int x,int y) {
		return null;
	}
	/** AITList�� resetRoleNameFor() �Լ� ����
	 */
	public void resetRoleNameFor(SingleLineText atext) {
		_infoTuples.resetRoleNameFor(atext);
		return;
	}
	/** AITList�� resetMultiplicityFor() �Լ� ���� 
	 */
	public void resetMultiplicityFor(SingleLineText atext) {
		_infoTuples.resetMultiplicityFor(atext);
		return;
	}
	/** AITList�� removeTuple() �Լ� ���� 
	 */
	public void removeTuple(AnyTionInfoTuple tuple) {
		_infoTuples.remove(tuple);
	}
	/** AITList�� replaceClassPtr() �Լ� ���� 
	 */
	public void replaceClassPtr(ClassLike from,ClassLike to) {
		_classes.replacePtr(from,to);
		_infoTuples.replaceClassPtr(from,to);
	}
	/** AITList�� findTupleFor() �Լ� ���� 
	 */
	public AnyTionInfoTuple findTupleFor(int x,int y) {
		return _infoTuples.findTupleFor(x,y);
	}
	/** AITList�� findTupleForQual() �Լ� ���� 
	 */
	public AnyTionInfoTuple findTupleForQual(QualificationText atext) {
		return null;
	}
	/** _classes ����Ʈ�� ������ ���� �Լ��̴�.
	 */
	public void clearLists() {
		_classes.clear();
	}
	/** ���迡 ���ϴ� �ɺ� �κ��� ����� ���� �Լ��̴�.
	 */
	public void clearModelSpecificSymbol() {
		return;
	}
	/** parameter�� �Ѱ��� �ؽ�Ʈ ��ü�� ���� aggregation���� ��� �������� ���Ǵ�
	 * �ؽ�Ʈ ��ü�ΰ��� �Ǻ��ϴ� �Լ��̴�.
	 */
	public int whatIsMyRole(SingleLineText atext) {
		if (atext == _name) return Const.ANYTIONNAME;
		return 0;
	}
	/** ���� ������ ��輱�ΰ��� Ȯ���ϴ� �Լ��̴�.
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
	/** �� ���� ��ü�� Ŭ������ �̵��� ���� �ʿ䰡 ������ �̵��� ���� ��������
	 * ���� �غ� �ϴ� �Լ��̴�. �ַ� Ŭ������ ���󰡾� �� ���е��� �����ϰ�
	 * �� ���е��� ���� ���� �ʱ�ȭ �Ѵ�.
	 */
	public void followClassProlog(ClassLike clss) {
		resetFlags();
		_focus = null;
		_focusNode = null;
		return;
	}
	/** �� ����� ����� Ŭ������ ������ �� �� Ŭ������ ���� �̵��ϴ� �Լ��̴�.
	 */
	public void followClass(Graphics g,int dx,int dy) {
		super.followSomeObject(g,dx,dy);
	}
	/** �� ����� ����� Ŭ������ ������ �� �� Ŭ������ ���� dx, dy ��ŭ �̵��ϴ� �Լ��̴�.
	 */
	public void followClass(int dx,int dy) {
		super.followSomeObject(dx,dy);
	}
	/** �� ���� ��ü�� �̵��� ��� ���� ��, ���õǴ� flag ���� �����ϴ� �Լ��̴�.
	 */
	public void resetMarkForMove() {
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			tuple.mark = false;
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ���迡 ���� ������ ���� �Ŀ� ���� �ϰ����� �˻��ϴ� �Լ��̴�.
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
	/** ����Ÿ ��� _classes�� ���� access �Լ��̴�.
	 */
	public ClassLikeList classes() {
		return _classes;
	}
	/** �� ������ ��輱�� ��ġ�� ���е��� ���ڿ� ��õ� Ŭ�������� ����ǵ��� �ϴ� �Լ��̴�.
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
	/** �� ���迡 ���ϴ� ��輱 ������ ���ڿ� ��õ� Ŭ������ ���� ���������� �ϴ� �Լ��̴�.
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
	/** �� ���迡 ���ϴ� ���е� �߿��� ���õ� �� ���� ���е��� �����ϴ� �Լ��̴�.
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
	/** �� ������ �̸��� ����ϴ� �ؽ�Ʈ ���� �۾��� �����ϴ� �Լ��̴�.
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
	/** �� ������ �̸��� ����ϴ� ���� �۾��� ���� �ϵ��� �ϴ� �Լ��̴�.
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
	/** �� ������ �μ� �ؽ�Ʈ�� �߿��� ���ڿ� ��õ� �ؽ�Ʈ�� �����ǵ��� �����ϴ� �Լ��̴�.
	 */
	public void localStartEdit(Text text) {
		Figure currentFocus = _controller.currentFocus();
		_controller.remove(currentFocus);
		_focus = text;
		_controller.clear(text,false);
		_controller.draw(this);
//		text.screen().activate();
	}
	/** �� ���迡�� ���õ� �Ϻκ��� ������ �����ϴ� �Լ��̴�.
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
	/** �� ���迡�� role name �ؽ�Ʈ �κ��� ������ ���鵵���ϴ� �Լ��̴�.
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
	/** �� ���迡�� role name �ؽ�Ʈ �κ��� �����ϵ����ϴ� �Լ��̴�.
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
	/** �� ���迡�� ���߼� �ؽ�Ʈ �κ��� ������ ���鵵���ϴ� �Լ��̴�.
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
	/** �� ���迡�� ���߼� �ؽ�Ʈ �κ��� �����ϵ����ϴ� �Լ��̴�.
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
	/** ȭ���� Ȯ�� ���Ŀ� �ݿø� ������ ���� �̻��� �׸��� ������ �ʵ��� 
	 * �� ��ü�� ����Ǵ� ��ġ���� �������ִ� �Լ��̴�.
	 */
	/** 3���� ���� ���� �ۼ��ÿ� ����° ���е��� �׸��� �����ϴ� �Լ��̴�.
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
	/** ���ο� ���踦 �׸��� ������ �� ����Ǵ� �ݹ��Լ��̴�.
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
	/** ���ο� ���踦 �׸��� ������ ���谡 ����� �� �ִ� �մ��� Ŭ�����鸸��
	 * �����ϴ� �Լ��̴�.
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
	/** ���ο� ������ �ۼ��� ���õ� ���Ŀ� ó������ ���콺 ��ư�� �������� ��
	 * ȣ��Ǵ� �Լ��̴�. �� �Լ��� ���Ͽ� �׸��� �׷����� �����Ѵ�.
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
	/** ���踦 ���� ������ �׷����� ������ ���Ŀ� ���콺 �̵� �ÿ� ȣ��Ǵ� �Լ��̴�.
	 * �� �Լ��� �׸��⸦ ���� rubberbanding�� �����Ѵ�.
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
	/** ������ �ۼ� ���Ŀ� ���콺�� ��ư�� release �Ǹ� ȣ��Ǵ� �Լ��̴�.
	 * �� �Լ��� ���� ������ �׸��⸦ ��ģ��.
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
	/** �� �Լ��� �׷��� ���п� ���Ͽ� ��� ���� ������ �����Ǵ� ���� Ȯ���ϴ� �Լ��̴�.
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
	/** �� �Լ��� ������ �׸��� �������� ���� ������ �������� �� �׸� �׸��⸦
	 * ���߰��ϴ� �Լ��̴�.
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
	/** �� �Լ��� ������ �ۼ� �ÿ� �ٽ� ��ư�� �������ν� ���� ������ �׸����ϴ�
	 * �Լ��̴�.
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
	/** �� �Լ��� �𼭸��� ��輱���� Ŭ������ ������ ����Ͽ� ������ ���̷�
	 * �����ϴ� �Լ��̴�.
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
	/** ��ü�� �̵��̳� ���� ���Ŀ� �𼭸��� ��輱 ���� ��θ� �ϰ������� �������ִ� 
	 * �Լ��̴�.
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
	/** ǥ��� ��ü�� ������ ���� ���Ŀ� ȣ��Ǵ� �Լ��μ� �ϰ��� �˻�,
	 * ��ġ �������� �����Ѵ�.
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
	/** �ϰ��� �˻��� �� ���μ� �� ���迡 ���ϴ� ������ Ŭ���� ��ü�� Ⱦ���ϴ�
	 * ���� �˻��ϴ� �Լ��̴�.
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
	/** �ϰ��� �˻��� �� ���μ� �� ���迡 ���ϴ� �����߿��� �𼭸��� �ִ� ��輱��
	 * �� ��Ȯ�Ѱ��� �˻��ϴ� �Լ��̴�.
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
	/** ȭ���� �ε� ���Ŀ� ���� Ŭ������ ���۷����� �ϰ����ְ� �����ϴ� �Լ��̴�.
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
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** �� ������ ���� ����� �ٲٱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
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
	/** �� ������ ���� ����� �̵��ϱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
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
	/** ���� ���迡 ���ο� ������ �߰��� �� ���Ǵ� �ݹ� �Լ��̴�.
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