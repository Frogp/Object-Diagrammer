package figure;

import java.awt.*;
import java.awt.Point;
import java.io.*;
import java.awt.event.*;
/** �� Ŭ������ UML ǥ����� generalization�� ǥ���ϱ� ���� ���̴�.
 * �� Ŭ�������� specialized�� ����� generalization ǥ����� ���� �ﰢ�� ǥ�����
 * �����ϴ� ���̴�.
 */
public final 
	class GenTion extends AnyTion {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -6210313719958032761L;
	/** �ﰢ���� ����
	 */
	private static int TRIANGLELENGTH = 20;
	/** �Ϲ�ȭ ���踦 ���� �ﰢ�� ǥ����̴�.
	 */
	private Triangle _symbol;
	/** ������ �̵��ÿ� �ɺ��� �̵��Ǵ� ���� ��Ÿ���� flag
	 */
	private transient boolean _symbolMoveMark;
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
		setPopupPtr(controller.gentionPopup());
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
	public GenTion(GraphicController controller,Popup popup,Line line) {
		super(controller,popup,line);
		_symbol = null;
		_symbolMoveMark = false;
	}
	/** �ﰢ�� ���� �б� access �Լ�
	 */
	public int symbolLength() {
		return TRIANGLELENGTH;
	}  
	/** Ŭ������ �̵� ���� �� generalization ��ü�� �ﰢ�� ǥ����� �����
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
	/** �ﰢ�� �ɺ��� ���ڿ����� �־��� ��ġ�� �����ϴ� �Լ��̴�.
	 */
	public void setSymbol(int sx,int sy,int ex,int ey) {
		_symbol = new Triangle(_controller,sx,sy,ex,ey,null);
	}
	/** ���� ���� �ݿ��ϴ� AnyTionInfoTuple �� �����ϴ� �Լ��̴�.
	 */
	public void makeATuple(int x,int y,ClassLike classptr) {
		AnyTionInfoTuple tuple = new AnyTionInfoTuple(classptr,x,y);
		_infoTuples.insert(tuple,0);
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMGENTION;
	}
	/** generalization�� ���� �ﰢ�� �ɺ��� �� �����ϴ� �Լ��̴�.
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
		int endX = 0;
		int endY = 0;
		if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
			endX = p1.x;
			endY = p1.y;
		} else if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
			endX = p2.x;
			endY = p2.y;
		} else {
			return true;
		}
		line.findTriangleStartPoints(endX,endY,startP,TRIANGLELENGTH);
		_symbol = new Triangle(_controller,startP.x,startP.y,endX,endY,null);
		return false;
	}
	/** generalization�� ���� �ﰢ�� �ɺ��� �ϰ������� �� �����ϴ� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbolAll() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		ClassLike clss = _classes.front();
		LineNode tmp = _lines.getFirst();
		while(tmp != null) {
			if (tmp.afterList().empty() || tmp.beforeList().empty()) {
				tmp.line().coords(p1,p2);
				Point startP = new Point(0,0);
				int endX,endY;
				if (CRgn.myPtInRgn(clss.maxRegion(),p1.x,p1.y)) {
					endX = p1.x;
					endY = p1.y;
				} else if (CRgn.myPtInRgn(clss.maxRegion(),p2.x,p2.y)) {
					endX = p2.x;
					endY = p2.y;
				} else {
					tmp = _lines.getNext();
					continue;
				}
				tmp.line().findTriangleStartPoints(endX,endY,startP,TRIANGLELENGTH);
				if (_symbol == null) {
					_symbol = new Triangle(_controller,startP.x,startP.y,endX,endY,null);		
				} else {
					_symbol.setXY1(startP.x,startP.y);
					_symbol.setXY2(endX,endY);
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
		if (style == Const.RUBBERBANDING && _symbolMoveMark == false) return;
		if (_symbolMoveMark) {
			if (_symbol != null) _symbol.draw(g,style,specialdcp);
		} else {
			if (_symbol != null) _symbol.drawEmpty(g,style,specialdcp);
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
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public void clear(Graphics g,boolean expose) {
		super.clear(g,expose);
		if (_symbol != null) _symbol.clear(g,true);
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
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		GenTion copied;
		if (ptr == null) {
			copied = new GenTion(_controller,null,null);
		} else {
			copied = (GenTion)ptr;
		}
		if (_symbol != null) {
			copied._symbol = (Triangle)_symbol.born(null);
		}
		return(super.born((Figure)copied));
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
	/** ȭ�鿡 �׷��� ������ �ɺ��� �̻����� �������� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean validForModelSpecific(ClassLike clss) {
		/*
		if (_classes.front().hasAncestor(clss)) return false;
		if (_classes.front().hasChildAlready(clss)) return false;
		*/
		return true;
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.gentionPopup();
	}  
	/** �� ��ü�� ���� �˾� �޴��� display�ϴ� �Լ��̴�.
	 */
	public void popup(MouseEvent event) {
		if (_popup == null) {
			_controller.beep();
			return;
		}
		if (_focus == _name) {
			_name.popup(event);
			return;
		}
		_popup.popup(event);
	}
	/** generalization�� �ﰢ�� ǥ��� �κ��� ����� �Լ��̴�.
	 */
	public void clearModelSpecificSymbol() {
		_controller.clear(_symbol,true);
		return;
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
			}
			tuple = _infoTuples.getNext();
		}
	}
	/** �� ����� ����� Ŭ������ ������ �� �� Ŭ������ ���� �̵��ϴ� �Լ��̴�.
	 */
	public void followClass(int dx,int dy) {
		super.followClass(dx,dy);
		if (_symbolMoveMark) {
			_symbol.moveCoord(dx,dy);
		}
		AnyTionInfoTuple tuple = _infoTuples.getFirst();
		while (tuple != null) {
			if (tuple.mark) {
				tuple.moveCoord(dx,dy);
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
