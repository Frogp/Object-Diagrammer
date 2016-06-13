package figure;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.Point;
/**
 * �� Ŭ������ ��� ǥ��� ��ü�� ���� �������̽��� �����ϴ� �߻�Ŭ�����̴�.
 */
public abstract
	class Figure extends Object implements Serializable {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -8019524329147825158L;
	/**
	 * �� ����Ʈ �迭�� �׸� ������ �����ϱ� ���� �������� ��ǥ������ �����Ѵ�.
	 * �Ź� ������ �� ������ ������ �������� ��ǥ���� �ٽ� ���ȴ�.
	 * �� ����Ÿ�� �ַ� makeRegion() �Լ����� ���ȴ�.
	 */
	protected CPoint _points[];
	/** �� ����Ʈ �迭�� ���� �׸��� �������� ���õǾ��� �� �𼭸��� �׷��ߵǴ� �簢��
	 * ��ǥ���� �����ϱ� ���� ���̴�.
	 */
	protected CPoint _dots[];
	/** �׸��� ����
	 */
	protected CRgn _region;
	/** �׸��� ���� ���� �� �ȼ� ���̰� ū ����
	 */
	protected CRgn _maxRegion;
	/** �� �׸��� �׷����� �ϴ� Component ��ü�� ���� ���۷��� 
	 */
	protected transient GraphicController _controller;
	/** ���� �׸��� ȭ�� ���� �����ΰ��� ��Ÿ���� flag
	 */
	protected transient boolean _doted;
	/** �� �׸� ��ü�� ȭ�� �ȿ� �ִ� ���� ��Ÿ���� flag
	 */
	protected transient boolean _inCanvas;
	/** �� �׸� ��ü�� �ۼ� ���Ŀ� stacking�� �� ���ΰ��� ��Ÿ���� flag
	 */
	protected transient boolean _stackingFlag;
	/** �� �׸� ��ü�� �ٸ� ��ü�� ���� ����� ��� ���� ��ü�� ����Ű�� ���۷���
	 */
	protected transient Figure _borned;
	/** �� ��ü�� ���� �˾� �޴� ���۷���
	 */
	protected transient Popup _popup;
	/** �׸� ��ü���� ��� traverse �� �� ���Ǵ� flag
	 */
	public transient boolean visited;
	/** �׸� ��ü�� ���ΰ� ������ ä�����°��� ��Ÿ���� flag
	 */
	public transient boolean filled;
	/** �׸� ��ü�� �̵� �ÿ� ���� ������ �ϱ� ���� ���� �׸��� ��� ��ü
	 */
	public static Figure BackUp;
	
	/**
	 * Figure Ŭ������ �����ڷμ� �ڷ� ������ �ʱ�ȭ �Ѵ�.
	 */
	protected Figure(GraphicController controller) {
		super();
		_controller = controller;
		_doted = false;
		_points = null;
		_region = CRgn.myCreateRgn();
		_maxRegion = CRgn.myCreateRgn();
		visited = false;
		_inCanvas = true;
		_stackingFlag = true;
		_borned = null;
		_popup = null;
	}
	/**
	 * �� �Լ��� �Ҹ����� ����̴�. Java���� �Ҹ��ڰ� �ʿ����� ������
	 * ������ �Ҹ����� ������μ� ����Ÿ ����� ���� reset��Ű�� ���� ������ ��찡 �ִ�.
	 */
	public void delete() {
		if (_region != null) {
			CRgn.myDestroyRgn(_region); 
			_region = null;
		}
		if (_maxRegion != null) {
			CRgn.myDestroyRgn(_maxRegion); 
			_maxRegion = null;
		}
		if (_points != null) {
			_points = null;
		}
		_popup = null;
		_borned = null;
	}
	/** ����Ÿ ��� _borned �� ���� �б� access �Լ��̴�.
	 */
	public Figure borned() {
		return _borned; 
	}
	/** ȭ���� �ε��� �� �ϰ����� �����ϴ� �Լ��̴�.
	 */
	public void doSomethingAfterLoad(GraphicController controller) {
		setController(controller);
		setPopupPtr(controller.simplePopup());
		makeRegion();
	}
	/** �� ��ü�� ���� ȭ�� �� ��Ÿ���� ���� �������ִ� �Լ��̴�.
	 */
	public void setInCanvas(boolean flag) {
		_inCanvas = flag;
	}
	/** ����Ÿ ��� _inCanvas �� ���� �б� access �Լ��̴�.
	 */
	public boolean inCanvas() {
		return _inCanvas;
	}
	/** �� ��ü�� �־��� �����ȿ� ���ԵǴ°��� ��Ÿ���� �Լ��̴�.
	 */
	public boolean checkInRegion(CRgn rgn) {
		return true; 
	}
	/** �׸� ��ü�� ������ ��ǥ(x2,y2) ���� ���ڿ� ��õ� ��ǥ ���� ����������� �����ϴ� �Լ��̴�.
	 */
	public boolean checkNear(int nx,int ny) {
		return false;
	}
	/** ���� ���콺�� ��ǥ�� x,y�� �� ��ü �ȿ� ���ԵǴ°��� �˷��ִ� �Լ��̴�.
	 * �̷��� Ȯ���� _region ������ �̿��Ͽ� �̷������.
	 */
	public boolean onEnter(int x,int y) {
		if (!CRgn.myEmptyRgn(_region)) {
			return CRgn.myPtInRgn(_region,x,y);
		} else {
			return false;
		}
	}
	/** �� ��ü�� ���� �˾� �����͸� �缳���ϴ� �Լ��̴�.
	 */
	public void replacePopup(GraphicController controller) {
		_popup = controller.simpleDCPopup();
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
	/** ����Ÿ ��� _popup �� ���� �б� access �Լ��̴�.
	 */
	public figure.Popup popupPtr() {
		return _popup;
	}
	/** �� ��ü�� �纻�� ������ִ� �Լ��μ� ��ü�� ���� �ÿ� ȣ��ȴ�.
	 * parameter ���� null�� ���� �� ó�� ȣ��Ǵ� ����̹Ƿ� �����ڸ� ���� 
	 * ��ü�� ���� �纻�� �������ϰ�, �׷��� ���� ���� �� ��ü�� ���ϴ�
	 * ����Ÿ ����� ���� �������ָ� �ȴ�. 
	 */
	public Figure born(Figure ptr) {
		Figure copied = ptr;
		if (ptr == null) {
			MySys.myprinterr("Error in Figure::born()");
			return this;
		} else {
			copied = ptr;
		}
		_borned = copied;
		copied._controller = _controller;
		copied._popup = _popup;
		copied._stackingFlag = _stackingFlag;
		// need nothing for _region, _maxRegion
		return(copied);
	}
	/** ���ڿ� ��õ� ������ �� ��ü�� ���ԵǴ°��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean checkEntries(CRgn rgn) {
		return true; 
	}
	/** ����Ÿ ��� _region �� ���� �б� access �Լ��̴�.
	 */
	public CRgn region() {
		return _region; 
	}
	/** ����Ÿ ��� _maxRegion �� ���� �б� access �Լ��̴�.
	 */
	public CRgn maxRegion() {
		return _maxRegion; 
	}
	/** ��ü�� identity�� ���� �Լ��̴�.
	 */
	public FigureID whoAreYou() {
		return Const.IAMFIGURE; 
	}
	/** �� ��ü�� �����ϴ� ��ü�� ã�� �Լ��̴�.
	 */
	public Figure container() {
		return this;
	}
	/** �� ��ü�� �����ϴ� ��ü�� ���� ������ �����ϴ� �Լ��̴�.
	 */
	public void remakeRegionForContainer() {
		makeRegion();
	}
	/** �� ��ü�� ���۷����� ���ڿ� ��õ� ����Ʈ���� �����ϴ� �Լ��̴�.
	 */
	public void checkInlist(FigureList alist) {
		if (alist.inList(this)) alist.remove(this);
	}
	/** �� �׸� ��ü�� �ּ� ���� ���̸� �����ϴ°� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean checkMinWH() {
		return(false);
	}
	/** �� ��ü�� ���� ��ü�� ��� �μ� ��ü�� ������ ���ϴ� �Լ��̴�.
	 */
	public int nOfFigures() {
		return 1;
	}
	/** �� ��ü�� ���� ��ü�� ��� �μ� ��ü�� ù��° ��ü�� �����ִ� �Լ��̴�.
	 */
	public Figure pop() {
		MySys.myprinterr("Error : This must not be called. Figure.pop()\n");
		return null;
	}
	/** ǥ��� ��ü�� ������ ���� ���Ŀ� ȣ��Ǵ� �Լ��μ� �ϰ��� �˻�,
	 * ��ġ �������� �����Ѵ�.
	 */
	public boolean epilog(boolean skipflag) {
		return false;
	}
	public boolean stopDraw() {
		return epilog(false);
	}
	/** �� ��ü�� ������ ��ü�ΰ� Ȯ���ϴ� �Լ��̴�. Ư�� �ؽ�Ʈ ��ü�� ��� �Էµ�
	 * ���ڿ��� ������ ������ ��ü�̴�.
	 */
	public boolean isObsolete() {
		return false;
	}
	/** ����Ÿ ��� _focus�� ���� �б� access �Լ�. Figure Ŭ���� ��쿡�� �����Լ��̴�.
	 */
	public Figure focus() {
		return this;
	}
	/** �� ��ü�� �̵��ϱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
	 */
	public boolean moveProlog(int oneorsome) {
		return true;
	}
	/** ���踦 ���� �ʿ��� �ɺ��� �ϰ������� �� �����ϴ� �Լ��̴�.
	 */
	public boolean setModelSpecificSymbolAll() {
		return true;
	}
	/** �׸� ��ü�� ���õǴ� �������� �缳���ϴ� �Լ��̴�.
	 */
	public void resetRegion() {
		if (_region != null) CRgn.myDestroyRgn(_region);
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion); 
		_region = CRgn.myCreateRgn();
		_maxRegion = CRgn.myCreateRgn();
	}
	/** ����Ÿ ��� _controller �� ���� �б� access �Լ��̴�.
	 */
	public GraphicController controller() {
		return _controller;
	}
	/** ����Ÿ ��� _controller �� ���� ���� access �Լ��̴�.
	 */
	public void setController(GraphicController ptr) {
		_controller = ptr;
	}
	/** ����Ÿ ��� _popup �� ���� ���� access �Լ��̴�.
	 */
	public void setPopupPtr(Popup ptr) {
		_popup = ptr;
	}
	/** �� ǥ��� ��ü�� ������ ����� �Լ��̴�.
	 */
	public abstract void makeRegion();
	/** �� ��ü�� �߾ӿ� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public abstract Point center();
	/** �� ��ü�� ������ �𼭸��� ���� ��ǥ���� ���ϴ� �Լ��̴�.
	 */
	public abstract Point last();
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� �� �Լ��� ���Ͽ� GraphicController ��ü��
	 * �ִ� �ּ� ��ǥ���� �ݿ��ȴ�.
	 */
	public abstract void minMaxXY();
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 * �� ��ü�� �ִ� �ּ� ��ǥ ���� ���ڵ� ���� �ݿ��Ǿ� ��������.
	 */
	public abstract void getMinMaxXY(Point minP,Point maxP);
	/** �׷��Ƚ� ��ü�� �̿��Ͽ� �� ��ü�� �׸��� �Լ��̴�. �� �Լ��� ����Ʈ �ÿ��� ȣ��ȴ�.
	 */
	public abstract void draw(Graphics g,int mode,Graphics specialgc);
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 */
	public abstract void drawing(Graphics g,boolean flag);
	/** �� ��ü �׸��⸦ rubberbanding�� �̿��Ͽ� �����ϴ� �Լ��̴�.
	 * ���� ���콺�� ���ο� ��ġ ���� �̿��Ͽ� rubberbanding �Ѵ�.
	 */
	public abstract void drawing(Graphics g,int nx,int ny,boolean flag);
	/** �� ��ü�� �̵���Ű�� �Լ��̴�. �̵��� �ʿ��� �κ��� rubberbanding�� �ϸ� �Ϻκ���
	 * �μ� ��ü�� ���ؼ��� ��ǥ�̵��� �Ѵ�. 
	 */
	public abstract void move(Graphics g,int dx,int dy);
	/** �� ��ü�� ȭ�� �󿡼� ����� �Լ��̴�. expose ������ true�̸� �׸��� �����
	 * paint �̺�Ʈ�� �߻����Ѽ� ��� �κ��� �ٽ� �׷������� �Ѵ�.
	 */
	public abstract void clear(Graphics g,boolean expose);
	/** �� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public abstract void drawDots(Graphics g);
	/** ���� ���콺�� �������� �� �� ��ü�� ũ�⸦ �����ϱ� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public abstract boolean wantResize(CPoint point);
	/** ��ǥ�� �̵� ��Ű�� �Լ��̴�.
	 */
	public abstract void moveCoord(int dx,int dy);
	/** �� ��ü�� �������� ���ο� ��ġ�� �ٲٴ� �Լ��̴�.
	 */
	public void changeOriginPoint(int x,int y) {}
	/** �Է� ������ ������ �簢���� ������� ��ġ�ϵ��� ���̸� �����ϴ� �Լ��̴�.
	 */
	public boolean adjustLine(Line line,boolean startpoint) { return true; }
	/** ���� ���콺�� �������� �� �� ��ü�� �����̱� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantMove(CPoint point) { return false; }
	/** ���� ���콺�� �������� �� �� ��ü�� ������ �����̱� ������ ���ΰ��� �����ϴ� �Լ��̴�.
	 */
	public boolean wantMoveFocus(CPoint point) { return false; }
	/** _x1, _y1 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY1(int x1,int y1) {}
	/** _x2, _y2 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY2(int x2,int y2) {}
	/** �׸� ũ�⸦ �����ϱ� ���� _x2, _y2 ��ǥ���� �����ϴ� �Լ��̴�.
	 */
	public void setXY2ForResize(int x2,int y2) {}
	/** ������ �� �κ��� Ȯ����Ѽ� �����Ǵ� Ŭ���� ���ο� ������ ���Եǵ����ϴ� �Լ��̴�.
	 * �� �Լ��� �׸� ��ҿ� Ȯ�� �ÿ� �̻��� �׸��� ��������� �ʵ����ϴ� ������ �Ѵ�.
	 */
	public void expandEndLineALittle() {}
	/** �׸� ��ü�� ���� �κ� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void bornFocus() {}
	/** �� ��ü�� ���� ���̸� �ٽ� ����Ѵ�. 
	 */
	public void recalcWidthHeight() {}
	/** ���ڷ� �־��� ��ġ���� �̿��� ���� ��ü�� �����ϴ� �Լ��̴�. 
	 */
	public void setFocus(int x,int y) {}
	/** ���ڷ� �־��� ��ü���� ���� ��ü�� �����ϴ� �Լ��̴�. 
	 */
	public void setThisFocus(Figure focus) {}
	/** �� ��ü�� ����� �ٲٱ� ������ �� ���� ��ġ�� �ʿ��� ���°��� �����ϴ� �Լ��̴�.
	 */
	public void resizeProlog(boolean makeBackUp) {}
	/** ȭ���� �ε� ���Ŀ� ���� ��ü�� ���۷����� �ϰ����ְ� �����ϴ� �Լ��̴�.
	 */
	public void makeConsistent(Figure oldfig) {}
	/** ��ü�� �ı� ������ �ϰ��� ������ ������ Ȯ���ϴ� �Լ��̴�.
	 */
	public void ensureConsistencyBeforeDelete() {}
	/** ��ü�� ���ϴ� ����Ʈ�� ������ ���� �Լ��̴�.
	 */
	public void clearLists() {}
	/** �� ��ü�� ����Ǿ� �ִ� ������� �����ϴ� �Լ��̴�.
	 */
	public void deleteNeighbors() {}
	/** �� ��ü�� ������ ��� ���迡�� ���õ� �Ϻκ��� ������ �����ϴ� �Լ��̴�.
	 */
	public void deleteSegmentFocus() {}
	/** ����Ÿ ��� _focus�� null ���� set�ϴ� access �Լ��̴�.
	 */
	public void resetFocus() {}
	/** ȭ���� �ε��� �� Ŭ���� ������ ShadowClasses ����Ʈ�� ����ϴ� �Լ��̴�.
	 */
	public void registerClassContentWhenLoading(String packName) {}
}
