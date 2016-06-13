
package figure;

import modeler.*;

import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Point;

/** �� Ŭ������ UML ǥ����� aggregation�� ǥ���ϱ� ���� ���̴�.
 * �� Ŭ�������� specialized�� ����� aggregationǥ����� ���� ���̾Ƹ�� ǥ�����
 * �����ϴ� ���̴�.
 */
public final class AggTion extends AnyTion 
{
	Graphics<Integer> sss;
	ArrayList<KMethod> methods;
	int a[];
	int[] x;
	int y[][];
	int [][]z;
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -1098620041014430181L;
	/** aggregationǥ����� ���� ���̾Ƹ�� ǥ����̴�.
	 */
	private Diamond _symbol;
	/** aggregation�ΰ� composition�ΰ��� ��Ÿ���� flag�̴�.
	 * _strongflag == true : composition
	 * _strongflag == false : aggregation
	 */
	private boolean _strongflag;
	/** �پƾƸ���� ����
	 */
	private static int DIAMONDLENGTH = 24;
	/** ���̾Ƹ���� ��
	 */
	private static int DIAMONDOFFSET = DIAMONDLENGTH/3;
	/** Ŭ������ �̵��� ���� ���� aggregation ��ü�� ������ ��, ���̾Ƹ�嵵
	 * ���� �����̰� �� ���ΰ��� ��Ÿ���� flag�̴�.
	 */
	private transient boolean _symbolMoveMark;
	/** Ŭ������ �̵� ���� �� aggregation ��ü�� ���̾Ƹ�� ǥ����� �����
	 * �Լ��̴�.
	 */
	public void redrawForArrow() {
		if (_symbol != null) _controller.clear(_symbol,false);
		Graphics g = _controller.getgraphics();
		Graphics eg = _controller.getgraphics();
		eg.setColor(_controller.getBackground());
		super.draw(g,Const.DRAWING,eg);
		eg.dispose();
		g.dispose();
		return;
	}
	/** aggregation�� composition���� Ȥ�� �� �ݴ�� ���� ��ȭ�� �ϴ� �Լ��̴�.
	 */
	public void changeAggregationStrength() {
		_strongflag = !_strongflag;
		_controller.draw(this);
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
		setPopupPtr(controller.aggtionPopup());
	}
	/** ���߼��� ǥ�ø� ���� ��ǥ�� �ٽ� ����ϴ� �Լ��̴�. �׷��� OMT������ �޸�
	 * UML ǥ��������� �ǹ̰� ����.
	 */
	private void adjustMultiplicity(LineNode node) {
		AnyTionInfoTuple tuple = null;
		Line aline = node.line();
		tuple = getCurrentTuple(aline,0,0);	
		if (tuple == null) {
			return;
		}
		Figure mul =  tuple.multiplicity;
		if (mul == null) return;
		if (mul.whoAreYou().isEqual(Const.IAMPOINT) == false) return;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		aline.coords(p1,p2);
		Point cp = new Point(-1,-1);
		findCenterPositionForAPoint(tuple,p1,p2,cp);
		if (cp.x == -1 || cp.y == -1) return;
		mul.setXY1(cp.x,cp.y);
	}
	/** destructor�� ������ �ϴ� �Լ��̴�. dangling reference�� ���ɼ��� ���̱� ����
	 * ���ȴ�.
	 */
	public void delete() {
		if (_symbol != null) {
			_symbol.delete(); _symbol = null;
		}
		super.delete();
	}
	/** �������̴�.
	 */
	public AggTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
		_symbol = null;
		_symbolMoveMark = false;
		_strongflag = false;
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
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMAGGTION;
	}
	/** aggregation�� ���� ���̾Ƹ�� �ɺ��� �� �����ϴ� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbol(ClassLike classPtr) {
		if (_classes.nOfList() == 1) {
			Line firstLine = _lines.front().line();
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			firstLine.coords(p1,p2);
			ClassLike firstClass = _classes.front();
			AnyTionInfoTuple tuple = new AnyTionInfoTuple(firstClass,p1.x,p1.y);
			_infoTuples.insert(tuple,0);
		}
		Line lastLine = _lines.rear().line();
		Point p = lastLine.last();
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classPtr,p.x,p.y);
		_infoTuples.insert(tuple,0);
		if (_controller.forkFlag) return false;
		Line line = _lines.front().line();
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		line.coords(p1,p2);
		ClassLike clss = _classes.front();
		Point startP = new Point(0,0);
		Point endP = new Point(0,0);
		if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
			endP.x = p2.x;
			endP.y = p2.y;	
		} else /* if  (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y))*/ {
			endP.x = p1.x;
			endP.y = p1.y;	
		}	
//		if (line.length() < DIAMONDLENGTH) {
//			return true;
//		}
		line.findDiamondEndCorners(endP,startP,DIAMONDLENGTH,DIAMONDOFFSET);
		_symbol = new Diamond(_controller,startP.x,startP.y,endP.x,endP.y,null);
		_controller.draw(_symbol);
		return false;
	}
	/** aggregation�� ���� ���̾Ƹ�� �ɺ��� �ϰ������� �� �����ϴ� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbolAll() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.beforeList().empty()) {
				adjustMultiplicity(tmp);
			}
			if (tmp.afterList().empty()) {
				adjustMultiplicity(tmp);
			} 
			tmp = _lines.getNext();
		}
		ClassLike clss = _classes.front();
		tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.afterList().empty() || tmp.beforeList().empty()) {
				tmp.line().coords(p1,p2);
				Point startP = new Point(0,0);
				Point endP = new Point(0,0);
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
					endP.x = p1.x;
					endP.y = p1.y;
				} else if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
					endP.x = p2.x;
					endP.y = p2.y;
				} else {
					tmp = _lines.getNext();
					continue;
				}
//				if (tmp.line().length() < DIAMONDLENGTH) {
//					return true;
//				}
				tmp.line().findDiamondEndCorners(endP,startP,DIAMONDLENGTH,DIAMONDOFFSET);
				if (_symbol == null) {
					_symbol = new Diamond(_controller,startP.x,startP.y,endP.x,endP.y,null);		
				} else {
					_symbol.setXY1(startP.x,startP.y);
					_symbol.setXY2(endP.x,endP.y);
				}
				return false;
			}
			tmp = _lines.getNext();
		}
		return true;
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
			Figure mul = tuple.multiplicity;
			if (mul != null) {
				mul.draw(g,style,specialdcp);
			}
			tuple = _infoTuples.getNext();
		}
		if (style == Const.RUBBERBANDING && _symbolMoveMark == false) return;
		if (_symbolMoveMark) {
			if (_symbol != null) _symbol.draw(g,style,specialdcp);
		} else {
			if (_symbol != null) {
				if (_strongflag) _symbol.drawEmpty(g,style,g);
				else _symbol.drawEmpty(g,style,specialdcp);
			}
		}
	}
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public void move(Graphics g,int dx,int dy) {
		super.move(g,dx,dy);
		if (_moveAllFlag == false) return;
		if (_symbol != null) _symbol.moveCoord(dx,dy);
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
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
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
		AggTion copied;
		if (ptr == null) {
			copied = new AggTion(_controller,null,null);
		} else {
			copied = (AggTion)ptr;
		}
		if (_symbol != null) {
			copied._symbol = (Diamond)_symbol.born(null);
		}
		return(super.born((Figure)copied));
	}
	/** ���� ���콺�� ��ġ�� ���� �ش� ������ ������ ������ ã�Ƴ��� �Լ��̴�.
	 */
	public AnyTionInfoTuple getCurrentTuple(Line aline,int x,int y) {
		Line focusLine;
		if (aline == null) focusLine = (Line)_focus;
		else focusLine = aline;
		if (focusLine == null) {
			_controller.beep("AggTion.getCurrentTuple");
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
		_popup = controller.aggtionPopup();
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
		if (_strongflag) {
			//		((AggTionPopup)_popup).strengthB.setLabel("Weak");
		} else {
			//		((AggTionPopup)_popup).strengthB.setLabel("Strong");
		}
		boolean isFocusBoundaryLine = isBoundary(_focusNode);
		if (isFocusBoundaryLine || _lines.nOfList() == 1) {
			((AggTionPopup)_popup).roleB.setEnabled(true);
			((AggTionPopup)_popup).cardinalB.setEnabled(true);
		} else {
			((AggTionPopup)_popup).roleB.setEnabled(false);
			((AggTionPopup)_popup).cardinalB.setEnabled(false);
		}
		_popup.popup(event);
	}
	/** aggregation�� ���̾Ƹ�� ǥ��� �κ��� ����� �Լ��̴�.
	 */
	public void clearModelSpecificSymbol() {
		_controller.clear(_symbol,true);
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
	/** �� ���� ��ü�� Ŭ������ �̵��� ���� �ʿ䰡 ������ �̵��� ���� ��������
	 * ���� �غ� �ϴ� �Լ��̴�. �ַ� Ŭ������ ���󰡾� �� ���е��� �����ϰ�
	 * �� ���е��� ���� ���� �ʱ�ȭ �Ѵ�.
	 */
	public void followClassProlog(ClassLike aclass) {
		super.followClassProlog(aclass);
		if (aclass == _classes.front()) {
			_symbolMoveMark = true;
		} else {
			_symbolMoveMark = false;
		}
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
		if (_symbolMoveMark) {
			_symbol.move(g,dx,dy);
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
				if (tuple.role != null) 
					tuple.role.move(g,dx,dy);
				if (tuple.multiplicity != null) 
					tuple.multiplicity.move(g,dx,dy);
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ���� ��ü�� �̵��� ��� ���� ��, ���õǴ� flag ���� �����ϴ� �Լ��̴�.
	 */
	public void resetMarkForMove() {
		super.resetMarkForMove();
		_symbolMoveMark = false;
	}
}