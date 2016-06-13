package figure;

import modeler.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.Point;
/** �� Ŭ������ UML ǥ����� association�� ǥ���ϱ� ���� ���̴�.
 * �� Ŭ�������� specialized�� ����� associationǥ����� ���� ���̾Ƹ�� ǥ�����
 * �����ϴ� ���̴�.
 */
public final 
	class AssTion extends AnyTion {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -8245544830885157214L;
	/** �پƾƸ���� ����
	 */
	private static int DIAMONDLENGTH = 18;
	/** ���̾Ƹ���� ��
	 */
	private static int DIAMONDOFFSET = DIAMONDLENGTH*2/3;
	/** ���� ���� ���踦 ��Ÿ���� ��� ��
	 */
	public static int BINARY = 2;
	/** ���� ���� ���踦 ��Ÿ���� ��� ��
	 */
	public static int TERNARY = 3;
	/** ��ũ�� Ŭ������ �ִ°��� ��Ÿ���� flag
	 */
	private boolean _linkSymbolFlag;
	/** ��ũ�� Ŭ������ ���� ���۷���
	 */
	private ClassTemplate _linkedClass;
	/** ���� ���� �ΰ� ���� ���� �ΰ��� ��Ÿ���� ����Ÿ ���
	 */
	private int _category;
	/** ���� ������ ��� ���̾Ƹ�� �ɺ��� �׷����� ��ǥ�� x ��
	 */
	private int _jointX;
	/** ���� ������ ��� ���̾Ƹ�� �ɺ��� �׷����� ��ǥ�� y ��
	 */
	private int _jointY;
	/** ���� ���� ���踦 ���� ���̾Ƹ�� ǥ����̴�.
	 */
	private Diamond _symbol;
	/** Ŭ�������� �����ϴ� ����Ʈ���� �ش� Ŭ���� ��ü�� ���۷����� �����ϴ� �Լ��̴�.
	 */
	public void removeFromClasses(ClassLike clss) {
		super.removeFromClasses(clss);
		resetLinkSymbol();
	}
	/** _classes ����Ʈ�� ������ ���� �Լ��̴�.
	 */
	public void clearLists() {
		super.clearLists();
		if (_linkedClass != null) {
			_linkedClass.setLinkAssTion(null);
		}
		_linkedClass = null;
		_linkSymbolFlag = false;
	}
	/** ��ũ�� Ŭ������ ���ִ� �Լ��̴�.
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
	/** �� ��ü�� �׷��ִµ� ���Ǵ� Component ��ü�� �����͸� �����ϴ� �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		super.setController(ptr);
		if (_symbol != null) _symbol.setController(ptr);
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		super.doSomethingAfterLoad(controller);
		setPopupPtr(controller.asstionPopup());
	}
	/** ����Ÿ��� _linkedClass�� ���� access �Լ�
	 */
	public ClassTemplate linkedClass() {
		return _linkedClass;
	}
	/** qualification �ؽ�Ʈ�� ����� ���� �Լ��̴�.
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
	/** ���� ���� ������ ��ġ ��ȭ�� ���� qualification �ؽ�Ʈ�� ��ġ�� �����ϴ� �Լ��̴�.
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
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
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
	/** �������̴�.
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
			Figure mul = tuple.multiplicity;
			if (_focus == mul) {
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
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.drawDots(g);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** ����Ÿ ��� _category�� ���� access �Լ�
	 */
	public int category() {
		return _category;
	}
	/** ����Ÿ ��� _category ���� �����ϴ� access �Լ�
	 */
	public void setCategory(int what) {
		_category = what;
	}
	/** ����Ÿ ��� _jointX, _jointY ���� �����ϴ� access �Լ�
	 */
	public void setJoint(int x,int y) {
		_jointX = x; _jointY = y;
	}
	/** ���߼��� ǥ�ø� ���� ��ǥ�� �ٽ� ����ϴ� �Լ��̴�. �׷��� OMT������ �޸�
	 * UML ǥ��������� �ǹ̰� ����.
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
	/** ���� ���콺 ��ǥ ���� ������ ������ ã���ִ� �Լ��̴�.
	 */
	public Line getLineFor(int x,int y) {
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.line().doesMeet(x,y)) return tmp.line();
			tmp = _lines.getNext();
		}
		return null;
	}
	/** AITList�� findTupleForQual() �Լ� ���� 
	 */
	public AnyTionInfoTuple findTupleForQual(QualificationText qual) {
		return _infoTuples.findTupleFor(qual);
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMASSTION;
	}
	/** ȭ�鿡 �׷��� ���谡 �������� �̻����� �������� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean valid(ClassLike clss) {
		if (validForModelSpecific(clss) == false) return false;
		return true;
	}
	/** ȭ�鿡 �׷��� ������ �ɺ��� �̻����� �������� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean validForModelSpecific(ClassLike clss) {
		if (_category == BINARY) {
			return true;
		}
		if (_classes.nOfList() < 3) return true;
		return true;
	}
	/** ���� ������ ���� �ɺ��� �� �����ϴ� �Լ��̴�.
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
	/** ���踦 ���� �ɺ��� �ϰ������� �� �����ϴ� �Լ��̴�.
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
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
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
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
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
	/** �� ������ ���� ����� �̵��ϱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
	 */
	public boolean moveProlog(int oneorsomeptr) {
		Graphics g = _controller.getgraphics();
		drawLinkSymbol(g,false);
		g.dispose();
		return super.moveProlog(oneorsomeptr);
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		drawLinkSymbol(g,false);
		if (_symbol != null) _symbol.clear(g,expose);
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public void makeRegion() {
		super.makeRegion();
		if (_symbol != null) {
			_symbol.makeRegion();
			CRgn.myUnionRgn(_region,_symbol.region(),_region);
			CRgn.myUnionRgn(_maxRegion,_symbol.maxRegion(),_maxRegion);
		}
	}
	/** �� ��ü�� �־��� �����ȿ� ���ԵǴ°��� ��Ÿ���� �Լ��̴�.
	 */
	public boolean checkInRegion(CRgn someregion) {
		if (super.checkInRegion(someregion)) return true;
		if (_symbol != null && _symbol.checkInRegion(someregion)) return true;
		return false;
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ� �̴�.
	 */
	public void setInCanvas(boolean flag) {
		super.setInCanvas(flag);
		if (_symbol != null) _symbol.setInCanvas(flag);
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
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
	/** �� ���հ��迡 ����� ��ũ�� Ŭ������ ��ũ �ɺ��� �����ϰ� �ϴ� �Լ��̴�.
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
	/** ��ũ �ɺ��� �׸��� ���� �������� ã�Ƴ��� �Լ��̴�.
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
	/** ��ũ �ɺ��� �׸��� �Լ��̴�.
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
	/** �� ���� ���迡 ��ũ �ɺ��� �����ϴ� �ݹ� �Լ��̴�.
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
	/** ���� ���콺�� ��ġ�� ���� �ش� ������ ������ ������ ã�Ƴ��� �Լ��̴�.
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
	/** AITList�� resetQualificationFor() �Լ� ���� 
	 */
	public void resetQualificationFor(QualificationText text) {
		_infoTuples.resetQualificationFor(text);
	}
	/** OMT�� ���߼� ǥ�� �ÿ� ���׸� ���� ��� ��ǥ�� ã�Ƴ��� �Լ��̴�. UML������ �ʿ������
	 * ������ ���� reserve�Ѵ�.
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
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.asstionPopup();
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
	/** association�� ���̾Ƹ�� ǥ��� �κ��� ����� �Լ��̴�.
	 */
	public void clearModelSpecificSymbol() {
		if (_symbol != null) _controller.clear(_symbol,true);
		return;
	}
	/** parameter�� �Ѱ��� �ؽ�Ʈ ��ü�� ���� aggregation���� ��� �������� ���Ǵ�
	 * �ؽ�Ʈ ��ü�ΰ��� �Ǻ��ϴ� �Լ��̴�.
	 */
	public int whatIsMyRole(SingleLineText text) {
		if (_infoTuples.inlistForRole(text)) {
			return Const.ROLENAME;
		} else if (_infoTuples.inlistForMultiplicity((Figure)text)) {
			return Const.MULTIPLICITYNAME;
		}
		return super.whatIsMyRole(text);
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
		if (totest.afterList().empty() ||
			totest.beforeList().empty()) 
			return true;
		return false;
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� �� �Լ��� ���Ͽ� GraphicController ��ü��
	 * �ִ� �ּ� ��ǥ���� �ݿ��ȴ�.
	 */
	public void minMaxXY() {
		super.minMaxXY();
		if (_symbol != null) _symbol.minMaxXY();
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� ���ڵ� ���� �ݿ��Ǿ� ��������.
	 */
	public void getMinMaxXY(Point minP,Point maxP) {
		super.getMinMaxXY(minP,maxP);
		if (_symbol != null) _symbol.getMinMaxXY(minP,maxP);
	}
	/** �� ��ü�� �߾ӿ� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public Point center() {
		if (_focus != null) {
			return _focus.center();
		}
		return super.center();
	}
	/** �� ���� ��ü�� Ŭ������ �̵��� ���� �ʿ䰡 ������ �̵��� ���� ��������
	 * ���� �غ� �ϴ� �Լ��̴�. �ַ� Ŭ������ ���󰡾� �� ���е��� �����ϰ�
	 * �� ���е��� ���� ���� �ʱ�ȭ �Ѵ�.
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
	/** �� ����� ����� Ŭ������ ������ �� �� Ŭ������ ���� �̵��ϴ� �Լ��̴�.
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