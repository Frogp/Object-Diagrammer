package figure;


import modeler.*;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.print.*;
import java.awt.geom.*;
import javaparse.ParseTreeNode;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;

/**  �� Ŭ������ �� �ý��ۿ��� ���� �ٽ����� Ŭ�����μ� ǥ����� �׷�����
 * ȭ���� �����ϰ� ����ڿ� ���� �߻��ϴ� ��κ��� �̺�Ʈ�� ó���Ѵ�.
 */
public abstract class GraphicController extends JComponent 
													implements ActionListener,
																	MouseMotionListener,
																	AdjustmentListener, 
																	KeyListener,
																	DocumentListener,
																	Printable
																	 
{
	/** �� ����Ÿ ����� Ŭ������ ������ �����ϱ� ���� ���Ǵ� Ŭ���� ������ ����Ʈ�̴�.
	 * ����� ������ �����⿡���� �� ��ü�� �׳� ����ϰ� �𵨷������� �Ȱ��� �̸��� ��ü��
	 * ���� ����Ѵ�. �� ��ü�� ���� access �Լ��� getShadowClasses() �̴�.
	 */
	public static ClassContentList ShadowClasses = new ClassContentList();
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : fixPointerAbsolute() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FFixPointerAbsolute = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : traceEnterForList() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FTraceEnterForList = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionEditNameText() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionEditNameText = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionEditCardinalText() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionEditCardinalText = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionEditRoleNameText() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionEditRoleNameText = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionContinueDraw() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionContinueDraw = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionStopDraw() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionStopDraw = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionStartDraw() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionStartDraw = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionDrawingHandler() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionDrawingHandler = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionWalkCorridor() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionWalkCorridor = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : anyTionStartForkForTernary() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAnyTionStartForkForTernary = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : assTionSetLinkeSymbol() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	public static boolean FAssTionSetLinkSymbol = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : drawingHandler() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	protected static boolean FDrawingHandler = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : stopDraw() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	protected static boolean FStopDraw = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : startDraw() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	protected static boolean FStartDraw = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : movingHandler() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	protected static boolean FMovingHandler = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : stopSimpleMove() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	protected static boolean FStopSimpleMove = false;
	/** �̺�Ʈ �ڵ鷯�� ����� �����ϱ� ���� flag : linesStopper() �Լ��� ȣ���� enable ��Ű�ų� disable ��Ŵ
	 */
	protected static boolean FLinesStopper = false;
	/** ȭ���� �ʱ� ����
	 */
	protected static int VERTICALINCREMENT = 550;
	/** ȭ���� �ʱ� ��
	 */
    protected static int HORIZONTALINCREMENT = 800;
	/** [Ctrl] Ű�� ������ �����ΰ��� ��Ÿ���� flag
	 */
	protected static boolean _ctrlKeyPressed = false;
	/** �׸� ��ü�� ������ �������°��� ��Ÿ���� flag
	 */
	protected boolean _reallyMovedFlag;
	/** ����� �׸��� ���۷���
	 */
	public static Figure CopiedFigure = null;
	/** ȭ����Һ���
	 */
	protected double _shrinkRatio;
	/** ��Ʈ�� ũ��
	 */
	public static int MyFontSize = 12;
	/** ȭ�� ��ҽÿ� �����ڸ� ����
	 */
	protected int _shrinkMargin;
	/** ����� ���� ���� �ۼ� ���϶� �� ���� ���� ���۷���
	 */
	protected Lines _currentLines;
	/** ���ο� ������ �׸��°� Ȥ�� ������ ���� �� ������ �߰� �ϴ� ���ΰ���
	 * ��Ÿ���� flag
	 */
	protected boolean _isNewLines;
	/** ���콺�� grab�Ǿ��°��� ��Ÿ���� flag
	 */
	protected boolean _isGrabbed;
	/** �˾� ��ư�� �������°��� ��Ÿ���� flag
	 */
	public boolean _popupflag;
	/** ���� �ۼ� ���� �׸��� ����
	 */
	protected int _currentDrawingType;
	/** ���� �ؽ�Ʈ ���� �۾� ���ΰ��� ��Ÿ���� flag
	 */
	public boolean _editingTag;
	/** ���� ȭ���� ��� �����Ѱ��� ��Ÿ���� flag
	 */
	protected boolean _enable;
	/** paint �̺�Ʈ�� �߻���ų ���ΰ��� ��Ÿ���� flag
	 */
	private boolean _paintFlag;
	/** ȭ��ǥ�� ����
	 */
	private int _arrowLength;
	/** ���� ������ ��
	 */
	private int _regionLength;
	/** ���� ������
	 */
	private int _pointRadius;
	/** �ձ� �簢���� �𼭸� ����
	 */
	private int _roundBoxGap;
	/** ȭ�� �� �ִ� �׸��� �߿��� ���콺�� ������ �׸����� ����Ʈ
	 */
	protected FigureList _focusList;
	/** �ܼ��� �˾��� ���۷���
	 */
	protected Popup _simplePopup;
	/** ������ ���� ��ư�� �ִ� �ܼ��� �˾��� ���۷���
	 */
	protected Popup _simpleDCPopup;
	/** ����, ����, ���� ��ư�� �ִ� �ܼ��� �˾��� ���۷���
	 */
	protected Popup _simpleEDCPopup;
	/** �׸��� �̵� ���� x
	 */
	protected int _movedX;
	/** �׸��� �̵� ���� y
	 */
	protected int _movedY;
	/** �׸� ��ġ�� �ּҰ� x
	 */
	protected int _minX;
	/** �׸� ��ġ�� �ּҰ� y
	 */
	protected int _minY;
	/** �׸� ��ġ�� �ִ밪 x
	 */
	protected int _maxX;
	/** �׸� ��ġ�� �ִ밪 y
	 */
	protected int _maxY;
	/** ȭ���� ��
	 */
	private int _width;
	/** ȭ���� ����
	 */
	private int _height;
	/** ȭ���� �ִ� ��
	 */
	protected int _maxWidth;
	/** ȭ���� �ִ� ����
	 */
	protected int _maxHeight;
	/** �׷��� ũ�⸦ �������� �� ���콺�� �̵� ���� x
	 */
	private int _dxForGroupResize;
	/** �׷��� ũ�⸦ �������� �� ���콺�� �̵� ���� y
	 */
	private int _dyForGroupResize;
	/** ���� ������ ���� �ؽ�Ʈ �ʵ�
	 */
	public JTextField activeTextField;
	/** ȭ���� ������ ����Ǿ����� ��Ÿ���� flag
	 */
	public boolean _dirtyFlag;
	/** ���콺�� ���� ��ġ x
	 */
	protected int _currentX;
	/** ���콺�� ���� ��ġ y
	 */
	protected int _currentY;
	/** �˾� ��ġ x
	 */
	protected int _popupX;
	/** �˾� ��ġ y
	 */
	protected int _popupY;
	/** highlight �ؾ��ϴ� Ŭ����
	 */
	ClassTemplate _focusClass;
	/** ȭ�鿡 ���Ե� �׸����� ����Ʈ
	 */
	public FigureList _figures;
	/** ������ �ۼ��ÿ� �ǹ��ִ� Ŭ������ ��Ű�� ��ü ���� ��Ƴ� ����Ʈ
	 */
	private FigureList _activeFigures;
	/** ȭ�鿡�� ���� ������ �Ǵ� �׸��� ���۷���
	 */
	public Figure _currentFocus;
	/** ���� ������ �����س��� ��
	 */
	protected Figure _savedFocus;
	/** ȭ���� ����
	 */
	protected CRgn _canvasRgn;
	/** �� ȭ���� �����ϴ� ������ ��ü
	 */
	protected CommonFrame _frame;
	/** ��ũ�������
	 */
	protected JScrollPane scroller;
	/** ���� ��ũ�ѹ�
	 */
	protected JScrollBar hScrollBar;
	/** ���� ��ũ�ѹ�
	 */
	protected JScrollBar vScrollBar;
	/** ���� ��Ʈ
	 */
	protected Font _font;
	/** ��Ʈ ascent
	 */
	protected int _fontAscent;
	/** ��Ʈ descent
	 */
	protected int _fontDescent;
	/** ��Ʈ ��
	 */
	protected int _fontSizeH;
	/** ��Ʈ ����
	 */
	protected int _fontSizeV;
	/** ���콺�� �����Ǵ� ���� ��Ÿ���� flag
	 */
	public boolean isFixed;
	/** ȭ���� ���� ��ǥ x
	 */
	public int originX;
	/** ȭ���� ���� ��ǥ y
	 */
	public int originY;
	/** ���п����� fork �۾� ���ΰ��� ��Ÿ���� flag
	 */
	public boolean forkFlag = false;
	/** ������ ���� ���⼺
	 */
	public int currentOrient;
	/** �׷��� ��ü�� Ŭ�� ����
	 */
	public Rectangle paintClipBound = null;
	
	/** ���� ����Ʈ ���ΰ��� ��Ÿ���� flag 
	 */
	public boolean printFlag = false;
	/** �������� ����Ʈ ������ ��
	 */
	private int physicalPaperWidth;
	/** �������� ����Ʈ ������ ����
	 */
	private int physicalPaperHeight;
	/** ���� ��
	 */
	private int paperWidth;
	/** ���� ����
	 */
	private int paperHeight;
	/** ��ü �������� ���
	 */
	private int nOfRowOfPages;
	/** ��ü �������� ����
	 */
	private	int nOfColumnOfPages;
	/** ����Ʈ�� ��ü ������ ��
	 */
	private int totalPages;
	/** �� ����Ʈ �������� ���� ��ġ x
	 */
	private int paperOX;
	/** �� ����Ʈ �������� ���� ��ġ y
	 */
	private int paperOY;
	
	
	/** ����Ÿ ��� _dirtyFlag ���� �б� access �Լ�
	 */
	public boolean dirtyFlag() {
		return _dirtyFlag;
	}
	/** ����Ÿ ��� _width ���� �б� access �Լ�
	 */
	public int width() { 
		return _width; 
	}
	/** ����Ÿ ��� _height ���� �б� access �Լ�
	 */
	public int height() { 
		return _height; 
	}
	/** ShadowClasses �� ���� access �Լ�
	*/
	public ClassContentList getShadowClasses() {
		return ShadowClasses;
	}
	/** ���ڿ� �־��� Ŭ���� �̸��� Ŭ���� ������ ã�� �Լ��̴�.
	 */
	public ClassContent classContentFor(String className) {
		return getShadowClasses().classContentFor(className);
	}
	/** ������ Ŭ������ �� Ŭ���� ������ ����ϴ� �Լ��̴�.
	 */
	public void insertClassContent(ClassContent newContent) {
		getShadowClasses().insert(newContent);
	}
	/** ����Ÿ ��� _frame ���� �б� access �Լ�
	 */
	public CommonFrame frame() 
	{
		return _frame;
	}
	/** ���� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup mainPopup() {
		return null;
	}
	/** Ŭ���� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup classPopup() {
		return null;
	}
	/** ��Ű�� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup packagePopup() {
		return null;
	}
	/** �Ϲ�ȭ ���� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup gentionPopup() {
		return null;
	}
	/** ����ȭ ���� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup aggtionPopup() {
		return null;
	}
	/** ���� ���� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup asstionPopup() {
		return null;
	}
	/** ���� ���� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup coltionPopup() {
		return null;
	}
	/** ����Ÿ ��� _simplePopup ���� �б� access �Լ�
	 */
	public figure.Popup simplePopup() {
		return _simplePopup;
	}
	/** ����Ÿ ��� _simpleDCPopup ���� �б� access �Լ�
	 */
	public figure.Popup simpleDCPopup() {
		return _simpleDCPopup;
	}
	/** ����Ÿ ��� _simpleEDCPopup ���� �б� access �Լ�
	 */
	public figure.Popup simpleEDCPopup() {
		return _simpleEDCPopup;
	}
	/** ��� �˾� ���۷����� ���� �����Լ�
	 */
	public figure.Popup memberPopup() {
		return null;
	}
	/** ����Ÿ ��� _font ���� �б� access �Լ�
	 */
	public Font font() {
		return _font;
	}
	/** ����Ÿ ��� _fontSizeH ���� �б� access �Լ�
	 */
	public int fontSizeH() { 
		return _fontSizeH;
	}
	/** ����Ÿ ��� _fontSizeV ���� �б� access �Լ�
	 */
	public int fontSizeV() {
		return _fontSizeV;
	}
	/** ����Ÿ ��� _fontAscent ���� �б� access �Լ�
	 */
	public int fontAscent() {
		return _fontAscent;
	}
	/** ����Ÿ ��� _activeFigures ���� ���� access �Լ�
	 */
	public void setActiveFigures(FigureList figs) {
		_activeFigures = figs;
	}
	/** _activeFigures ���� ���½�Ű�� �Լ��̴�.
	 */
	public void resetActiveFigures() {
		_activeFigures.delete();
		_activeFigures = null;
	}
	/** ����Ÿ ��� _activeFigures ���� �б� access �Լ�
	 */
	public FigureList activeFigures() {
		return _activeFigures;
	}
	/** ���콺�� ��ġ�� �������� �ʵ��� �ϴ� �Լ��̴�. ���� ���콺�� ���������� ��ǥ���� ������� �ʴ´�.
	 */
	public void fixPointerAbsolute(CPoint event) {
		isFixed = true;
		warpPointer(_currentX,_currentY);
	}
	/** ������ �̾ ���� �׸� �� ���̻� �� �߱Ⱑ ������� �ʵ��� �ϴ� �Լ��̴�.
	 */
	protected void linesStopper(CPoint event,boolean stop) {
		int x = event.x;
		int y = event.y;
		int deltax = Math.abs(x - _currentX);
		int deltay = Math.abs(y - _currentY);
		if ((deltax < Const.FIXTOLERANCE) &&
			(deltay < Const.FIXTOLERANCE) &&
			stop == false) {
			return;
		}
		currentOrient = Const.UNDEFINED;
		FLinesStopper = false;
		_isNewLines = true;
		if (_currentLines != null) {
			if (_currentLines.nOfFigures() == 0) {
				_currentLines.delete();
			} else {
				if (_currentDrawingType != 0) {
					Line focus = (Line)_currentLines.focus();
					if (focus != null) {
						Point lp = focus.last();
						focus.toggleHead(true,lp.x,lp.y);
					}
				}

				_currentLines.resetFocus();
				_currentLines.epilog(false);
				_currentLines.makeRegion();
				clear(_currentLines,true);
				draw(_currentLines);
				_figures.insert(_currentLines,0);
			}
			_currentLines = null;
		}
		_currentDrawingType = 0;
	}
	/** �޴��� ���ؼ� ���õ� ����� �׸��� �׸��� ���� �ʱ�ȭ�ϴ� �Լ��̴�. 
	 */
	public void readyDraw(int what) {
		_isNewLines = true;
		_currentDrawingType = what;
	}
	/** ����Ÿ ��� _enable ���� ���� access �Լ�
	 */
	public void setEnable(boolean flag) {
		_enable = flag;
	}
	/** ��Ű���� ������ save�ϴ� �Լ��̴�.
	 */
	public void savePackageContent(String packageName,GraphicController packController) 
	{
		Figure aFig = _figures.getFirst();
		while (aFig != null) 
		{
			if (aFig.whoAreYou().isEqual(Const.IAMPACKAGE)) 
			{
				PackageTemplate aPack = (PackageTemplate) aFig;
				String packName = aPack.getName();
				if (packageName.equals(packName))
				{
					aPack.saveFromController(packController);
					return;
				}
			}
			aFig = _figures.getNext();
		}
	}
	/** ��Ű���� ������ �� ȭ������ �ε��ϴ� �Լ��̴�.
	 */
	public void loadFromPackage(GraphicController oldController,PackageTemplate pack) 
	{
		String packageName = pack.getName();
		Figure aFig = oldController._figures.getFirst();
		while (aFig != null) 
		{
			if (aFig.whoAreYou().isEqual(Const.IAMPACKAGE)) 
			{
				PackageTemplate aPack = (PackageTemplate) aFig;
				String packName = aPack.getName();
				if (packageName.equals(packName) && aPack.havePackageContent == true) 
				{
					aPack.loadContent(this);
					return;
				}
			}
			aFig = oldController._figures.getNext();
		}
	}
	/** ���ڿ� ���� ��õ� �׸� �������� ��� ����Ʈ�� ����� �Լ��̴�.
	 */
	public FigureList makeListOf(FigureID forwhom) {
		FigureList list = new FigureList();
		Figure figptr = _figures.getFirst();
		while (figptr != null) {
			if (figptr.whoAreYou().isIn(forwhom)) {
				list.insert(figptr,0);
			}
			figptr = _figures.getNext();
		}
		return list;
	}
	/** ���콺 ��ġ ���� �ű�� �Լ��̴�.
	 */
	public void warpPointer(int x,int y) {
		_currentX = x;
		_currentY = y;
	}
	/** ���� �޽����� �Բ� �Ҹ��� �߻���Ű�� �Լ��̴�.
	 */
	public void beep(String m) {
		System.out.println(m);
		getToolkit().beep();
	}
	/** �Ҹ��� �߻���Ű�� �Լ��̴�.
	 */
	public void beep() {
		getToolkit().beep();
	}
	/** ȭ�� ������ ����� �Լ��̴�.
	 */
	public CRgn canvasRgn() {
		makeCanvasRegion();
		return _canvasRgn;
	}
	/** ����Ÿ ��� _figures ���� �б� access �Լ�
	 */
	public FigureList figures() {
		return _figures;
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 */
	public void minmaxXY(int x1,int y1) {
		if (_minX > x1) _minX = x1;
		if (_maxX < x1) _maxX = x1;
		if (_minY > y1) _minY = y1;
		if (_maxY < y1) _maxY = y1;
	}
	/** ȭ���� ��� �ÿ� �ʿ��� �ִ�, �ּ� ��ǥ ���� ��� ���� �Լ��̴�.
	 */
	public void minmaxXY(int x1,int y1,int x2,int y2) {
		if (_minX > x1) _minX = x1;
		if (_maxX < x1) _maxX = x1;
		if (_minY > y1) _minY = y1;
		if (_maxY < y1) _maxY = y1;
		if (_minX > x2) _minX = x2;
		if (_maxX < x2) _maxX = x2;
		if (_minY > y2) _minY = y2;
		if (_maxY < y2) _maxY = y2;
	}
	/** ����Ÿ ��� _arrowLength ���� �б� access �Լ�
	 */
	public int arrowLength() {
		return _arrowLength;
	}
	/** ����Ÿ ��� _regionLength ���� �б� access �Լ�
	 */
	public int regionLength() {
		return _regionLength;
	}
	/** ����Ÿ ��� _pointRadius ���� �б� access �Լ�
	 */
	public int pointRadius() {
		return _pointRadius;
	}
	/** ����Ÿ ��� _roundBoxGap ���� �б� access �Լ�
	 */
	public int roundBoxGap() {
		return _roundBoxGap;
	}
	/** ����Ÿ ��� _currentX ���� �б� access �Լ�
	 */
	public int currentX() {
		return _currentX;
	}
	/** ����Ÿ ��� _currentY ���� �б� access �Լ�
	 */
	public int currentY() {
		return _currentY;
	}
	/** ����Ÿ ��� _popupX ���� �б� access �Լ�
	 */
	public int popupX() {
		return _popupX;
	}
	/** ����Ÿ ��� _popupY ���� �б� access �Լ�
	 */
	public int popupY() {
		return _popupY;
	}
	/** ����Ÿ ��� _currentFocus ���� �б� access �Լ�
	 */
	public Figure currentFocus() {
		return _currentFocus;
	}
	/** �� ȭ�鿡 ���Ե� ��� �׸��� ������ ���ϴ� �Լ��̴�.
	 */
	public void makeRegion() {
		Figure ptr = _figures.getFirst();
		while (ptr != null) {
			ptr.makeRegion();
			ptr = _figures.getNext();
		}
	}
	/** ���ڿ� ��õ� �׸��� ������ ���ϴ� �Լ��̴�.
	 */
	public void makeRegion(Figure figure) {
		figure.makeRegion();
	}
	/** ���콺�� ���� ��ǥ ���� ����ϴ� �Լ��̴�.
	 */
	public void setCurrentXY(int x,int y) {
		_currentX = x;
		_currentY = y;
	}
	/** ����Ÿ ��� _currentLines ���� ���� access �Լ�
	 */
	public void setCurrentLines(Lines aLines) {
		_currentLines = aLines;
	}
	/** ����Ÿ ��� _currentLines ���� �б� access �Լ�
	 */
	public Lines currentLines() {
		return _currentLines;
	}
	/** _currentFocus ���� ���½�Ű�� �Լ��̴�.
	 */
	public void resetCurrentFocus() {
		_currentFocus = null;
	}
	/** ����Ÿ ��� _currentFocus ���� ���� access �Լ�
	 */
	public void setCurrentFocus(Figure newfocus) {
		_currentFocus = newfocus;
	}
	/** ���� ���콺 ��ǥ�� ȭ���� ��輱�� �Ѵ°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean checkLimitForFigure(int newx,int newy,CPoint deltaxy) {
		int gap = 10;
		int oX = (int)((double)originX/_shrinkRatio);
		int oY = (int)((double)originY/_shrinkRatio);
		int width = (int)((double)_width/_shrinkRatio);
		int height = (int)((double)_height/_shrinkRatio);
		newx = newx - oX;
		newy = newy - oY;
		if ((oX + newx <= 0) ||
			(oY + newy <= 0)) {
			beep();
			return false;
		}
		
		int onewx = oX + newx + gap;
		int onewy = oY + newy + gap;
		maxBoundaryCheck(onewx,onewy);
		
		int dx = 0;
		int dy = 0;
		int diff = 0;
		if (newx < gap) {
			diff = gap - newx;
			if (diff > Const.SCROLLBARDELTA) {
				dx = -(diff + Const.SCROLLBARDELTA);
			} else {
				dx = -Const.SCROLLBARDELTA;
			}
		}
		if (newy < gap) {
			diff = gap - newy;
			if (diff > Const.SCROLLBARDELTA) {
				dy = -(diff + Const.SCROLLBARDELTA);
			} else {
				dy = -Const.SCROLLBARDELTA;
			}
		}
		if (newx > width - gap) {
			if (dx == 0) {
				diff = newx - width;
				if (diff > Const.SCROLLBARDELTA) {
					dx = (diff + Const.SCROLLBARDELTA);
				} else {
					dx = Const.SCROLLBARDELTA;
				}
			}
		}
		if (newy > height - gap) {
			if (dy == 0) {
				diff = newy - height;
				if (diff > Const.SCROLLBARDELTA) {
					dy = (diff + Const.SCROLLBARDELTA);
				} else {
					dy = Const.SCROLLBARDELTA;
				}
			}
		}
		if (dx != 0 || dy != 0) {
			if (_editingTag) {
//				VTerm scr = ((Text)(_currentFocus.focus())).screen();
//				scr.vanish();
			} else if (_currentFocus != null) {
				rubberbanding(_currentFocus);
			}
		}
		moveScrollB(dx,dy);
		if (dx != 0 || dy != 0) {
			if (_editingTag) {
//				VTerm scr = ((Text)(_currentFocus.focus())).screen();
				draw(_currentFocus);
//				scr.activate();
			}
			// these values are used to reset the position of popup
			if (deltaxy != null) {
				deltaxy.x = dx;
				deltaxy.y = dy;
			}
			return true;
		}
		return false;
	}
	/** ���� ���콺 ��ǥ�� ȭ���� �ִ� �Ѱ踦 ����°� Ȯ���ϴ� �Լ��̴�.
	 */
	public void maxBoundaryCheck(int newx,int newy) {
		if (newx >= Integer.MAX_VALUE || newy >= Integer.MAX_VALUE) {
			beep();
			return;
		}
		boolean flag = false;
		if (newx >= _maxWidth) {
			flag = true;
			_maxWidth = _maxWidth + HORIZONTALINCREMENT;
		}
		if (newy >= _maxHeight) {
			flag = true;
			_maxHeight = _maxHeight + VERTICALINCREMENT;
		}
		if (flag == true) {
			setSize(new Dimension(_maxWidth,_maxHeight));
			setPreferredSize(new Dimension (_maxWidth,_maxHeight));
		}
	}
	/** ���콺�� ȭ���� ��輱�� ������ �ڵ����� ��ũ���ϴ� �Լ��̴�.
	 */
	public void checkToGo(int newx,int newy,int oldx,int oldy) {
		if (CRgn.myPtInRgn(_canvasRgn,newx,newy)) return;
		int dx = newx - oldx;
		int dy = newy - oldy;
		moveScrollB(dx,dy);
	}
	/** ��ũ�� �ٸ� �̵���Ű�� �Լ��̴�.
	 */
	private void moveScrollB(int deltax,int deltay) {
		if (deltax == 0 && deltay == 0) {
			return;
		} else if (deltax != 0 && deltay == 0) {
			originX = originX + deltax;
			hScrollBar.setValue(originX);
		} else if (deltax == 0 && deltay != 0) {
			originY = originY + deltay;
			vScrollBar.setValue(originY);
		} else {
			originX = originX + deltax;
			originY = originY + deltay;
			hScrollBar.setValue(originX);
			vScrollBar.setValue(originY);
		}
	}
	/** �׸��� �̵��� ó���ϴ� �ڵ鷯 �Լ��̴�.
	 */
	private void localMoving(CPoint event) {
		int x = event.x;
		int y = event.y;
		if (_currentX == x && _currentY == y) return;
		int dx = x - _currentX;
		int dy = y - _currentY;
		_currentX = x;
		_currentY = y;
		CPoint sdxy = new CPoint();
		boolean moved = checkLimitForFigure(x,y,sdxy);
		if (moved) {
			_movedX = sdxy.x;
			_movedY = sdxy.y;
			_currentFocus.moveCoord(dx,dy);
			return;
		}
		Graphics rg = getgraphics();
		rg.setXORMode(getBackground());
		_currentFocus.move(rg,dx,dy);
		rg.dispose();
	}
	/** �׸� �׸��⸦ ó���ϴ� �ڵ鷯 �Լ��̴�.
	 */
	private void localDrawing(CPoint event) {
		int x = event.x;
		int y = event.y;
		if (_currentX == x && _currentY == y) return;
		_currentX = x;
		_currentY = y;
		boolean moved = checkLimitForFigure(x,y,null);
		if (moved) {
			_currentFocus.setXY2ForResize(x,y);
			rubberbanding(_currentFocus);
			return;
		}
		Graphics rg = getgraphics();
		rg.setXORMode(getBackground());
		_currentFocus.drawing(rg,x,y,false);
		rg.dispose();
	}
	/** ȭ�� ������ ���� ���� �Լ��̴�.
	 */
	abstract public void doSave(String filename);
	/** ȭ�� �ε带 ���� ���� �Լ��̴�.
	 */
	abstract public void doLoad(String filename);
	/* ȭ���� import�ϱ����� ���� �Լ��̴�. */
	public void importFrom(String filename) {
	}
	/** ȭ���� clear �ϰ� �׸����� �ٽ� �׷��ִ� �Լ��̴�.
	 */
	public void localMakeClear(boolean clearAreaFlag) {
		if (clearAreaFlag == true) clearArea(originX,originY,_width,_height,false);
		FigureList copiedList = new FigureList();
		copiedList.copy(_figures);
		Figure all = copiedList.getFirst();
		while (all != null) {
			all.setInCanvas(all.checkInRegion(_canvasRgn));
			if (all.inCanvas()) {
				all.makeRegion();
			} else {
				all.resetRegion();
			}
			all = copiedList.getNext();
		}

		_currentFocus = null;
		if (clearAreaFlag == false) return;
		FigureList copiedListMore = new FigureList();
		copiedListMore.copy(_figures);
		Figure ptr = copiedListMore.getFirst();
		while(ptr != null) {
			this.draw(ptr);
			ptr = copiedListMore.getNext();
		}
	}
	/** ȭ���� clear �ϰ� ��� �׸� ��ü�� ���ִ� �Լ��̴�.
	 * ȭ���� �ʱ�ȭ �� �� ȣ��ȴ�.
	 */
	public void clearAll(boolean clearScreen) {
		_currentFocus = null;
		_savedFocus = null;
		_focusList.clear();
		_dirtyFlag = false;
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.clearLists();
			ptr.delete();
			ptr = _figures.getNext();
		}
		_figures.clear();	
		if(clearScreen) {
//			repaint(originX,originY,_width,_height);
			super.paintImmediately(originX,originY,_width,_height);
		}
	}
	/** ȭ�� ��ü�� �����ǰ� �� ���Ŀ� �ʱ�ȭ�ؾ� �� �۾��� �����ϴ� �Լ��̴�.
	 */
	public void initAfterRealize() {
		FontMetrics fm;
		fm = super.getGraphics().getFontMetrics(_font);
		_fontAscent = fm.getAscent();
		_fontDescent = fm.getDescent();
		_fontSizeH = fm.charWidth('A');
		_fontSizeV = _fontAscent + _fontDescent;
		
		makeCanvasRegion();
		
		hScrollBar.setEnabled(true);
		vScrollBar.setEnabled(true);
		setBackground(Color.white);
	}
	/** �������̴�.
	 */
	public GraphicController(CommonFrame frame) {
		super();
		_frame = frame;
		
		_width = 0;
		_height = 0;
		_maxWidth = HORIZONTALINCREMENT * 4;
		_maxHeight = VERTICALINCREMENT * 4;

		_font = new Font("Dialog",Font.PLAIN,MyFontSize);
		/* initialize data members */
		_focusClass = null;
		_shrinkRatio = 1.0;
		_currentLines = null;
		_paintFlag = false;
		_isNewLines = true;
		_isGrabbed = false;
		_popupflag = false;
		_currentDrawingType = 0;
		_canvasRgn = null;
		//        _savedDrawingType = 0;
		activeTextField = null;
		_editingTag = false;
		_enable = true;
		_figures = new FigureList();
		_focusList = new FigureList();
		originX = 0;
		originY = 0;
		_movedX = 0;
		_movedY = 0;
		_minX = _maxWidth;
		_minY = _maxHeight;
		_maxX = 0; _maxY = 0;
		_dxForGroupResize = 0;
		_dyForGroupResize = 0;
		_dirtyFlag = false;
		_arrowLength = Const.ARROWLENGTH;
		_regionLength = Const.REGIONLENGTH;
		_pointRadius = Const.POINTRADIUS;
		_roundBoxGap = Const.ROUNDBOXBGAB;
		_popupX = 0;
		_popupY = 0;
		_currentX = 0;
		_currentY = 0;
		isFixed = false;
		_currentFocus = null;
		_savedFocus = null;
		currentOrient = Const.UNDEFINED;

		enableEvents(AWTEvent.MOUSE_EVENT_MASK |
					 AWTEvent.COMPONENT_EVENT_MASK |
					 AWTEvent.KEY_EVENT_MASK |
					 AWTEvent.WINDOW_EVENT_MASK);
		_simplePopup = new SimplePopup(this);
		_simpleDCPopup = new SimpleDCPopup(this);
		_simpleEDCPopup = new SimpleEDCPopup(this);

		setSize(new Dimension(_maxWidth,_maxHeight));
		setPreferredSize(new Dimension (_maxWidth,_maxHeight));
		_frame.getContentPane().setLayout(new BorderLayout());
		scroller = new JScrollPane();
		scroller.setViewportView(this);
		_frame.getContentPane().add("Center",scroller);
		
		hScrollBar = scroller.getHorizontalScrollBar();
		hScrollBar.setUnitIncrement(HORIZONTALINCREMENT/4);
		hScrollBar.setBlockIncrement(HORIZONTALINCREMENT/2);
		hScrollBar.setEnabled(false);
		
  	 	vScrollBar = scroller.getVerticalScrollBar();
		vScrollBar.setUnitIncrement(VERTICALINCREMENT/4);
		vScrollBar.setBlockIncrement(VERTICALINCREMENT/2);
		vScrollBar.setEnabled(false);

		hScrollBar.addAdjustmentListener(this);
		vScrollBar.addAdjustmentListener(this);
		
		addKeyListener(this);
		addMouseMotionListener(this);
		this.setLayout(null);
	}
	/** ȭ���� foreground ������ �����ϴ� �Լ��̴�.
	 */
	public void setForeground(Color c) {
		super.setForeground(c);
	}
	/** ȭ���� background ������ �����ϴ� �Լ��̴�.
	 */
	public void setBackground(Color c) {
		super.setBackground(c);
		scroller.getViewport().setBackground(c); 
//		_frame.getContentPane().setBackground(c);
	}
	/** ȭ���� �ٽñ׸����� �ϴ� �Լ��̴�.
	 */
	public void flushPaintEvent()
	{
		if (_paintFlag == true) {
			repaint();
		}
		_paintFlag = false;
	}
	/** ȭ���� �ٽ� �׸� �ʿ䰡 ������ ��Ÿ������ _paintFlag�� ��Ʈ�ϴ� �Լ��̴�.
	 */
	public void setRepaint() {
		_paintFlag = true;
	}
	/** _paintFlag�� �����ϴ� �Լ��̴�.
	 */
	public void resetRepaint() {
		_paintFlag = false;
	}
	/** ȭ�鿡 �׸��� �׸��� ���� �׷��� ��ü�� �Ҵ��ϴ� �Լ��̴�.
	 */
	public Graphics getgraphics() {
		Graphics og = super.getGraphics();
		((Graphics2D)og).scale(_shrinkRatio,_shrinkRatio);
		Graphics g = og.create();
	
		if (paintClipBound == null) {
			g.setClip((int)(((double)originX)/_shrinkRatio),
					  (int)(((double)originY)/_shrinkRatio),
					  (int)(((double)_width)/_shrinkRatio),
					  (int)(((double)_height)/_shrinkRatio));
		} else {
			g.setClip(new Rectangle(paintClipBound));
		}
		g.setPaintMode();
		g.setFont(_font);
		g.setColor(getForeground());
		return g;
	}
	/** paint �̺�Ʈ�� �߻����� �� ȭ���� �ٽ� �׸��� �Լ��̴�.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).scale(_shrinkRatio,_shrinkRatio);
		paintClipBound = new Rectangle(g.getClipBounds());
		g.setColor(getForeground());
		Graphics eg = g.create();
		Rectangle clipR = g.getClipBounds();
		Rectangle newClipR = new Rectangle(clipR);
		eg.setClip(newClipR);
		eg.setColor(getBackground());

		CPoint pt[] = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			pt[i] = new CPoint();
		}
		pt[0].x = clipR.x;
		pt[0].y = clipR.y;
		pt[1].x = clipR.x+clipR.width;
		pt[1].y = clipR.y;
		pt[2].x = clipR.x+clipR.width;
		pt[2].y = clipR.y+clipR.height;
		pt[3].x = clipR.x;
		pt[3].y = clipR.y+clipR.height;
		CRgn clipRgn = CRgn.myPolygonRgn(pt,4);

		FigureList copiedList = new FigureList();
		copiedList.copy(_figures);
		Figure ptr = copiedList.getFirst();
		while(ptr != null) {
			if (ptr.checkInRegion(clipRgn))
				ptr.draw(g,Const.DRAWING,eg);
			ptr = copiedList.getNext();
		}
		if (_currentFocus != null) {
			if (_editingTag) {
				_currentFocus.draw(g,Const.DRAWING,eg);
			} else if (_popupflag == true) {
				_popupflag = false;
			} else if (_isGrabbed == true) {
				_currentFocus.moveCoord(_movedX,_movedY);
				_currentX = _currentX + _movedX;
				_currentY = _currentY + _movedY;
				g.setXORMode(getBackground());
				_currentFocus.draw(g,Const.RUBBERBANDING,eg);
				g.setPaintMode();
			} else {
				_currentFocus.drawDots(g);
			}
		}
		if (_focusClass != null) {
			g.setColor(Color.red);
			_focusClass.draw(g,Const.HIGHLIGHTING,eg);
		}
		eg.dispose();
		_paintFlag = false;
		_movedX = 0;
		_movedY = 0;
		paintClipBound = null;
	}
	/** ���ڷ� �־��� �׸� ��ü�� highlight �ϴ� �Լ��̴�.
	 */
	public void highlight(Figure figure) {
		_figures.remove(figure);
		_figures.insert(figure,0);
		Graphics eg = this.getGraphics();
		Graphics hg = this.getGraphics();
		eg.setColor(getBackground());
		hg.setColor(Color.red);
		figure.draw(hg,Const.HIGHLIGHTING,eg);
		hg.dispose();
		eg.dispose();
	}
	/** ���ڷ� �־��� �׸� ��ü�� lowlight �ϴ� �Լ��̴�.
	 */
	public void lowlight(ClassLike figure) {
		lowlight((Figure)figure);
	}
	/** ���ڷ� �־��� �׸� ��ü�� lowlight �ϴ� �Լ��̴�.
	 */
	public void lowlight(Figure figure) {
		Graphics g = this.getgraphics();
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.draw(g,Const.LOWLIGHTING,eg);
		eg.dispose();
		g.dispose();
	}
	/** ���ڷ� �־��� �׸� ��ü�� �׷��ִ� �Լ��̴�.
	 */
	public void draw(Figure figure) {
		figure.makeRegion();
		Graphics g = this.getgraphics();
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.draw(g,Const.DRAWING,eg);
		eg.dispose();
		g.dispose();
	}
	/** ���ڷ� �־��� �׸� ��ü�� clear �ϴ� �Լ��̴�.
	 */
	public void clear(Figure figure,boolean expose) {
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.clear(eg,expose);
		eg.dispose();
	}
	/** ���ڷ� �־��� �׸� ��ü�� rubberbanding �ϴ� �Լ��̴�.
	 */
	public void rubberbanding(Figure figure) {
		Graphics eg = this.getgraphics();
		Graphics rg = this.getgraphics();
		eg.setColor(getBackground());
		rg.setXORMode(getBackground());
		figure.draw(rg,Const.RUBBERBANDING,eg);
		rg.dispose();
		eg.dispose();
	}
	/** ���ڷ� �־��� �׸� ��ü�� ����� �Լ��̴�.
	 */
	public void erase(Figure figure) {
		Graphics eg = this.getgraphics();
		eg.setColor(getBackground());
		figure.draw(eg,Const.DRAWING,eg);
		eg.dispose();
	}
	/** �׸� ����Ʈ�� ���ڷ� �־��� ��ü�� �����Ѵ�.
	 */
	public void insert(Figure figure) {
		_figures.insert(figure,0);
	}
	/** �׸� ����Ʈ���� ���ڷ� �־��� ��ü�� �����Ѵ�.
	 */
	public void remove(Figure figure) {
		_figures.remove(figure);
	}
	/** ���ڷ� �־��� ��ü�� ���� ��Ŀ���� �����Ǿ��� �� �𼭸��� �簢���� �׸��� �Լ��̴�.
	 */
	public void drawDots(Figure figure) {
		_figures.remove(figure);
		_figures.insert(figure,0);
		lowlight(figure);
		Graphics g = getgraphics();
		figure.drawDots(g);
		g.dispose();
	}
	/** ���� ������ �Ǵ� �׸� ��ü�� �����ϴ� �Լ��̴�. �� �Լ��� ��ü ��ü�� �ı����� �ʰ�
	 * �� ��ü�� ���� ��ü�� �ı��Ѵ�.
	 */
	public void deleteCurrentFocus() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		Figure focusFig = _currentFocus.focus();
		if (focusFig == null) {
			beep();
			return;
		}
		if (!(focusFig.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT))) {
			beep();
			return;
		}
		clear(focusFig,true);
		focusFig.ensureConsistencyBeforeDelete();
		focusFig.delete();
		_currentFocus = null;
		flushPaintEvent();
	}
	/** ���� ���콺�� ���� ������ ��ü�� ȭ�鿡�� �����Ѵ�.
	 */
	public void deleteCurrentFigure() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		Figure testFocus = _currentFocus;
		// deleteSegmentFocus() resets _currentFocus after delete
		testFocus.deleteSegmentFocus(); 
		if (_currentFocus == null) return;
		_currentFocus.deleteNeighbors();
		lowlight(_currentFocus);
		_figures.remove(_currentFocus);
		clear(_currentFocus,true);
		checkInlist(_currentFocus);
		_currentFocus.delete();
		_currentFocus = null;
		flushPaintEvent();
	}
	/** �׸� ��ü�� �̵��� ������ �� �۾��� �����ϴ� �Լ��̴�.
	 */
	protected void localStopMove() {
		_currentFocus.resetFocus();
		_currentFocus.epilog(false);
		if (_currentFocus.whoAreYou().isIn(Const.WEARECLASSTEMPLATE) &&
			Figure.BackUp != null) {
			Figure.BackUp.delete();
			Figure.BackUp = null;
		}
		if (Figure.BackUp != null) {
			Figure obsoleteFigure = _currentFocus;
			_currentFocus = Figure.BackUp;
			Figure.BackUp = null;
			_currentFocus.makeConsistent(obsoleteFigure);
			obsoleteFigure.delete();
			_currentFocus.setModelSpecificSymbolAll();
			beep("GraphicController.localStopMove");
		} else {
			_currentFocus = _currentFocus.container();
		}
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		draw(_currentFocus);
		_currentFocus = null;
	}
	/** �׸� ��ü�� �ۼ��� ������ �� �۾��� �����ϴ� �Լ��̴�.
	 */
	protected void localStopDraw() {
		/*
		boolean ck = _currentFocus.checkMinWH();
		if (ck) {
			_focusList.clear();
			erase(_currentFocus);
			_currentFocus.delete();
			_currentFocus = null;
			return;
		} else {
			boolean destroyed = _currentFocus.epilog(false);
			if (destroyed) {
				deleteCurrentFigure();
				return;
			}
			if (_currentLines == null) {
				_currentFocus.resetFocus();
				_currentFocus.epilog(false); // epilog for resizing
				if (Figure.BackUp != null) {
					Figure obsoleteFigure = _currentFocus;
					_currentFocus = Figure.BackUp;
					Figure.BackUp = null;
					_currentFocus.makeConsistent(obsoleteFigure);
					obsoleteFigure.delete();
					_currentFocus.setModelSpecificSymbolAll();
					_currentFocus.makeRegion();
					clear(_currentFocus,true);
					beep("GraphicController.localStopDraw");
				} else {
					_currentFocus.makeRegion();
					clear(_currentFocus,true);
				}
				if (_currentFocus.whoAreYou() == Const.IAMTEXT) {
					lowlight(_currentFocus);
					((Text)_currentFocus).adjustScale();
				}
				_figures.insert(_currentFocus,0);
			} else if (!(_currentFocus.whoAreYou().isIn(Const.WEARETION))) {
				FLinesStopper = true;
			}
			_focusList.clear();
			_currentFocus = null;
			return;
		}
		*/
		boolean destroyed = _currentFocus.stopDraw();
		if (destroyed) {
			deleteCurrentFigure();
			return;
		}
		if (_currentLines == null) {
			_currentFocus.resetFocus();
			_currentFocus.epilog(false); // epilog for resizing
			if (Figure.BackUp != null) {
				Figure obsoleteFigure = _currentFocus;
				_currentFocus = Figure.BackUp;
				Figure.BackUp = null;
				_currentFocus.makeConsistent(obsoleteFigure);
				obsoleteFigure.delete();
				_currentFocus.setModelSpecificSymbolAll();
				_currentFocus.makeRegion();
				clear(_currentFocus,true);
				beep("GraphicController.localStopDraw");
			} else {
				_currentFocus.makeRegion();
				clear(_currentFocus,true);
			}
			_figures.insert(_currentFocus,0);
		} else if (!(_currentFocus.whoAreYou().isIn(Const.WEARETION))) {
			FLinesStopper = true;
		}
		_focusList.clear();
		_currentFocus = null;
		return;
	}
	/** ���ڿ� ��õ� ��ü�� ����Ʈ�鿡�� �����ϴ� �Լ��̴�.
	 */
	public void checkInlist(Figure ptr) {
		_focusList.clear();
	}
	/** ���� ���콺�� Ư�� �׸� ��ü�� ����Ű�� ���� �˻��ϴ� �Լ��̴�.
	 */
	protected void localTraceEnter(CPoint event,FigureList list,boolean dohighlight) {
		boolean forFigures;
		FigureList figureList = list;
		if (list == null) {
			forFigures = true;
			figureList = _figures;
		} else {
			forFigures = false;
		}
		if (forFigures && _enable == false) return;
		int x = event.x;
		int y = event.y;
//		int x = (int)(((double)tmpX)/_shrinkRatio);
//		int y = (int)(((double)tmpY)/_shrinkRatio);
		if (CRgn.myPtInRgn(_canvasRgn,x,y) == false) {
			_focusList.clear();
			_currentFocus = null;
			return;
		}
		_focusList.clear();
		if (forFigures && isFixed) {
			_currentFocus = null;
			return;
		}
		if (_currentFocus != null) {
			lowlight(_currentFocus);
		}
		boolean check = false;
		boolean found = false;
		boolean newfound = false;
		Figure newFocus = null;
		Figure figureptr = figureList.getLast();
		while (figureptr != null) {
			check = figureptr.onEnter(x,y);
			found = found || check;
			if ( (check) &&
				 !(_focusList.inList(figureptr)) ) {
				_focusList.push(figureptr);
				newfound = true;
				newFocus = figureptr;
				break;
			}
			figureptr = figureList.getPrevious();
		}
		figureptr = _focusList.getFirst();
		while (figureptr != null) {
			check = figureptr.onEnter(x,y);
			if (check == false) {
				_focusList.remove(figureptr);
				figureptr = _focusList.getFirst();
			} else {
				figureptr = _focusList.getNext();
			}
		}
		if (newfound) {
			if (_currentFocus != null) lowlight(_currentFocus);
			if (dohighlight == true) highlight(newFocus);
			_currentFocus = newFocus;
		} else if (found == false) {
			if (_currentFocus != null) lowlight(_currentFocus);
			_currentFocus = null;
			_focusList.clear();
		}
	}
	/** ���� �׸��� ũ�⸦ �����ϱ� �����ϴ� �Լ��̴�.
	 */
	public void resizeCurrentFigure(boolean doCheckNear) {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		_figures.remove(_currentFocus);
		checkInlist(_currentFocus);
		clear(_currentFocus,true);
		if (doCheckNear) {
			_currentFocus.checkNear(_currentX,_currentY);
			_currentFocus.resizeProlog(true);
		}
		rubberbanding(_currentFocus);
		_currentDrawingType = 0;
		setCrossHairCursor();
		_isGrabbed = true;
		FDrawingHandler = true;
		FStopDraw = true;
	}
	/** ���콺 Ŀ�� ����� ���ڰ� ���·� �ٲٴ� �Լ��̴�.
	 */
	public void setCrossHairCursor() {
		_isGrabbed = true;
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	/** grab�� ���콺�� �����ϴ� �Լ��̴�.
	 */
	public void xUngrabPointer() {
		_isGrabbed = false;
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	/** ���� �׸��� �����ҿ� �����ϴ� �Լ��̴�.
	 */
	public void copyCurrentFigureInMemory() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		CopiedFigure = _currentFocus.born(null);
	}
	/** �����ҿ� ����Ǿ��ִ� �׸��� ȭ���� ���������� ��ġ�� paste �ϴ� �Լ��̴�.
	 */
	public void pasteFigureAtRandomPosFromMemory() {
		if (CopiedFigure == null) {
			beep();
			return;
		}
		double x = Math.random();
		double y = Math.random();
		int x0 = _width / 5;
		int y0 = _height / 5;
		int w = _width*2/5;
		int h = _height*2/5;
		_popupX = originX + x0 + (int)(x*w);
		_popupY = originY + y0 + (int)(y*h);
		pasteFigureFromMemory();
	}
	/** �����ҿ� ����Ǿ��ִ� �׸��� ȭ�鿡 paste �ϴ� �Լ��̴�.
	 */
	public void pasteFigureFromMemory() {
		if (CopiedFigure == null) {
			beep();
			return;
		}
		int pX = _popupX;
		int pY = _popupY;
		_currentFocus = CopiedFigure.born(null);
		_currentFocus.setController(this);
		_currentFocus.replacePopup(this);
		_currentFocus.changeOriginPoint(_popupX,_popupY);
		_currentFocus.resetFocus();
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		_focusList.clear();
		draw(_currentFocus);
		drawDots(_currentFocus);
	}
	/** �׸� ��ü�� ȭ�鿡 �����ϴ� �Լ��̴�.
	 */
	public void copyCurrentFigure() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		if (_currentFocus.whoAreYou().isIn(Const.WEARETION)) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		Figure origin = _currentFocus;
		_currentFocus = _currentFocus.born(null);
		_currentFocus.moveCoord(Const.COPYDELTA,Const.COPYDELTA);
		_currentFocus.resetFocus();
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		_focusList.clear();
		draw(_currentFocus);
		drawDots(_currentFocus);
		flushPaintEvent();
	}
	/** ���� �׸��� ���� ��ü�� �̵��ϱ� �����ϴ� �Լ��̴�.
	 */
	public void moveCurrentFocus() {
		if (_currentFocus == null) {
			beep();
			return;
		}
		lowlight(_currentFocus);
		Figure focusFig = _currentFocus.focus();
		if (focusFig == null) {
			beep();
			return;
		}
		if (!(focusFig.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT))) {
			beep();
			return;
		}
		remove(_currentFocus);
		checkInlist(_currentFocus);
		_currentFocus = focusFig;
		clear(_currentFocus,true);
		rubberbanding(_currentFocus);
		setCrossHairCursor();
		_isGrabbed = true;
		FMovingHandler = true;
		FStopSimpleMove = true;
	}
	/** ���� �׸� ��ü�� �̵��ϱ� �����ϴ� �Լ��̴�.
	 */
	public void moveCurrentFigure(int oneorsomeptr,boolean skipPrologFlag) {
		if (_currentFocus == null) {
			beep();
			return;
		}
		Figure focusFig = _currentFocus.focus();
		if (focusFig != null) {
			if ((focusFig.whoAreYou().isIn(Const.WEAREEDITABLECOMPONENT))) {
				moveCurrentFocus();
				return;
			}
		}
		lowlight(_currentFocus);
		// if skip flag is true, this is second call
		if (skipPrologFlag == false) {
			// this saving blocks new drawing
			//                _savedDrawingType = _currentDrawingType;
			_currentDrawingType = 0;
			boolean continueFlag =
								   _currentFocus.moveProlog(oneorsomeptr);
			if ((_currentFocus.whoAreYou().isIn(Const.WEARETION)) &&
				(continueFlag == false)) {
				beep();
				return; // error case
			}
			if (continueFlag == false) {
				beep();
				return; // SOMEPTR case
			}
		}
		_figures.remove(_currentFocus);
		checkInlist(_currentFocus);
		clear(_currentFocus,true);
		rubberbanding(_currentFocus);
		setCrossHairCursor();
		_isGrabbed = true;
		FMovingHandler = true;
		FStopSimpleMove = true;
	}
	/** ���ڿ� ��õ� �ؽ�Ʈ ��ü�� ���� ���� �۾��� �����ϵ��� �ϴ� �Լ��̴�.
	 */
	public void startEditableObject(Figure obj,Text initText) {
		_editingTag = true;
		setCurrentFocus(obj);
		obj.makeRegion();
		clear(obj,false);
		draw(obj);
		initText.startEdit();
		obj.setThisFocus(initText);
//		FProcessKeys = true;
	}
	/** �ؽ�Ʈ �����⸦ �����ϵ��� �ϴ� �Լ��̴�.
	 */
	public void startEditor(CPoint event) {
		_editingTag = true;
		localTraceEnter(event,null,false);
		int x = event.x;
		int y = event.y;
		if (_currentFocus == null) {
			_currentFocus = (Figure) new Text(this,x,y,_simpleEDCPopup);
			((Text)_currentFocus).startEdit();
		} else if (_currentFocus.whoAreYou().isEqual(Const.IAMTEXT)) {
			lowlight(_currentFocus);
			_figures.remove(_currentFocus);
			checkInlist(_currentFocus);
			clear(_currentFocus,true);
			draw(_currentFocus);
			((Text)_currentFocus).setXY(x,y);
			((Text)_currentFocus).startEdit();
		} else if (_currentFocus.whoAreYou().isIn(Const.WEARECLASSTEMPLATE)) {
			ClassLike focusclass = (ClassLike)_currentFocus;
			_figures.remove(_currentFocus);
			focusclass.localStartEdit(x,y);
		} else if (_currentFocus.whoAreYou().isEqual(Const.IAMNOTE)) {
			Note focusNote = (Note)_currentFocus;
			_figures.remove(_currentFocus);
			focusNote.localStartEdit(x,y);
		} else if ((_currentFocus.whoAreYou().isIn(Const.WEARETION)) &&
				   (_currentFocus.focus().whoAreYou().isEqual(Const.IAMSINGLELINETEXT))) {
			AnyTion focusayntion = (AnyTion) _currentFocus;
			focusayntion.localStartEdit((Text)_currentFocus.focus());
		} else if ((_currentFocus.whoAreYou().isIn(Const.WEARETION)) &&
				   (_currentFocus.focus().whoAreYou().isEqual(Const.IAMQUALIFICATIONTEXT))) {
			AnyTion focusayntion = (AnyTion) _currentFocus;
			((QualificationText)_currentFocus.focus()).setDynamicLine(null);
			focusayntion.localStartEdit((Text)_currentFocus.focus());
		} else {
			_currentFocus = (Figure) new Text(this,x,y,_simpleEDCPopup);
			((Text)_currentFocus).startEdit();
		}
	}
	/** �ؽ�Ʈ�� ���� ���� �۾��� �����ϴ� �Լ��̴�.
	 */
	public void editorFinished(boolean susflag) {
		_editingTag = false;
		Text textfocus = ((Text)_currentFocus.focus());
		textfocus.seeYouLater(susflag);
		FigureID identity = textfocus.whoAreYou();
		if (identity.isEqual(Const.IAMTEXT) && textfocus.isObsolete()) {
			clear(_currentFocus,true);
			_currentFocus = null;
			return;
		}
		_currentFocus.recalcWidthHeight();
		_currentFocus.makeRegion();
		clear(_currentFocus,true);
		_figures.insert(_currentFocus,0);
		if ((identity.isIn(Const.WEAREEDITABLECOMPONENT)) &&
			(textfocus.shouldBeDeleted())) {
			textfocus.delete();
		}
		if (_currentFocus.whoAreYou().isEqual(Const.IAMCLASSTEMPLATE)) {
			ClassTemplate thisClass = (ClassTemplate)_currentFocus;
			String name = thisClass.getName();
			
			if (name.length() == 0) {
				thisClass.setDummyName();
			} else if (name.equals("Undefined") == false) {
				boolean setOK = false;
				if (thisClass.emptyClass()) {
					setOK = thisClass.setClassContentFromShadow();
				}
				if (setOK == false) {
					thisClass.registerClassContent();
				}
				if (_frame instanceof Modeler && Modeler.BrowserDlg != null) {
					Modeler.BrowserDlg.replaceClassContentFor(thisClass.getName());
				}
			}
			if (_frame instanceof Modeler) {
				Modeler aModeler = Modeler.allModelers.getFirst();
				while (aModeler != null) {
					aModeler.controller().repaint();
					aModeler = Modeler.allModelers.getNext();
				}
			}
		} else if (_currentFocus.whoAreYou().isEqual(Const.IAMPACKAGE)) {
			PackageTemplate thisPackage = (PackageTemplate)_currentFocus;
			String name = thisPackage.getName();
			if (name.length() == 0) {
				thisPackage.setDummyName();
			}
			if (name.length() > 0) {
				thisPackage.replacePackageNameIfNeed();
			}
		}
		_currentFocus = null;
		flushPaintEvent();
	}   
	/** ȭ���� ���� ������ ����� �Լ��̴�.
	 */
	private void makeCanvasRegion() {
		if (_canvasRgn != null) {
			CRgn.myDestroyRgn(_canvasRgn);
			_canvasRgn = null;
		}
		originX = hScrollBar.getValue();
		originY = vScrollBar.getValue();
		int oX = (int)(((double)originX)/_shrinkRatio);
		int oY = (int)(((double)originY)/_shrinkRatio);
		Rectangle canvasRect = getVisibleRect();
		_width = canvasRect.width;
		_height = canvasRect.height;
		int actualWidth = (int)(((double)_width)/_shrinkRatio);
		int actualHeight = (int)(((double)_height)/_shrinkRatio);
		CPoint pt[] = new CPoint[4];
		for(int i = 0; i < 4; i++) {
			pt[i] = new CPoint();
		}
		pt[0].x = oX;
		pt[0].y = oY;
		pt[1].x = oX+actualWidth;
		pt[1].y = oY;
		pt[2].x = oX+actualWidth;
		pt[2].y = oY+actualHeight;
		pt[3].x = oX;
		pt[3].y = oY+actualHeight;
		_canvasRgn = CRgn.myPolygonRgn(pt,4);
	}
	/** ȭ���� ũ�Ⱑ ����Ǿ��� �� ȣ��Ǵ� �̺�Ʈ �ڵ鷯�̴�.
	 */
	private void doCanvasResize() {
		makeCanvasRegion();
		Figure all = _figures.getFirst();
		while (all != null) {
			all.setInCanvas(all.checkInRegion(_canvasRgn));
			if (all.inCanvas()) {
				all.makeRegion();
			} else {
				all.resetRegion();
			}
			all = _figures.getNext();
		}
	}
	/** ��ǥ�� �ִ� �ּ� ���� �ʱ�ȭ�ϴ� �Լ��̴�.
	 */
	public void resetMinMaxXY() {
		_maxX = -1; _maxY = -1;
		_minX = _maxWidth;
		_minY = _maxHeight;
	}
	/** ȭ���� ����ϴ� �Լ��̴�.
	 */
	public boolean doZoom(int ratio)
	{
		switch(ratio) {
		case 100:
			_shrinkRatio = 1.0;
			break;
		case 90:
			_shrinkRatio = 0.9;
			break;
		case 80:
			_shrinkRatio = 0.8;
			break;
		case 70:
			_shrinkRatio = 0.7;
			break;
		case 60:
			_shrinkRatio = 0.6;
			break;
		case 50:
			_shrinkRatio = 0.5;
			break;
		case 40:
			_shrinkRatio = 0.4;
			break;
		case 30:
			_shrinkRatio = 0.3;
			break;
		case 20:
			_shrinkRatio = 0.2;
			break;
		case 10:
			_shrinkRatio = 0.1;
			break;
		}
		hScrollBar.setValue(0);
		vScrollBar.setValue(0);
		doCanvasResize();
		repaint();
		return true;
	}
	/** ȭ���� ���� ������ ����� �Լ��̴�.
	 */
	public void clearArea(Graphics erasegc,int x,int y,int w,int h) {
		erasegc.fillRect(x,y,w,h);
	}
	/** ȭ���� ���� ������ ����� �Լ��̴�. �� �Լ��� ��� ������ ���ڿ� ����
	 * paint �̺�Ʈ�� �߻���Ű�⵵ �Ѵ�.
	 */
	public void clearArea(int x,int y,int w,int h,boolean expose) {
		if (expose) {
			/*
			setRepaint();
			flushPaintEvent();
			*/
			Graphics g = this.getgraphics();
			_paintFlag = false;
			g.setClip(x,y,(int)(((double)w)/_shrinkRatio),(int)(((double)h)/_shrinkRatio));
			this.update(g);
		} else {
			Graphics eg = this.getgraphics();
			eg.setColor(getBackground());
			eg.fillRect(x,y,w,h);
			eg.dispose();
		}
	}
	/** ���ڿ��� ȭ�鿡 �׷��ִ� �Լ��̴�. �� �Լ��� �� ������Ʈ�� �׷����� �̿��Ѵ�.
	 */
	public void drawString(int x,int y,char[] str,int s,int count) {
		if (str[s] == '\0' || str[s] == '\n') return;
		// �� �׷��Ƚ� ��ü�� ����� ������ �־��
		Graphics g = this.getgraphics();
		this.drawString(g,x,y,str,s,count);
		g.dispose();
	}
	/** ���ڿ��� ȭ�鿡 �׷��ִ� �Լ��̴�. �� �Լ��� ���ڷ� �Ѱ��� �׷����� �̿��Ѵ�.
	 */
	public void drawString(Graphics g,int x,int y,char[] str,int s,int count) {
		if (str[s] == '\0' || str[s] == '\n') return;
		Rectangle clipR = new Rectangle(g.getClipBounds());
		Rectangle newClipR = clipR.intersection(new Rectangle(x,y-_fontAscent,count*_fontSizeH,_fontSizeV));
		g.setClip(newClipR);
		g.setFont(_font);
		g.drawChars(str,s,count,x,y); 
		g.setClip(clipR);
	}
	/** ȭ���� ������ �����Ǵ� ��� _dirtyFlag�� ��Ʈ�ϴ� �Լ��̴�.
	 */
	public void setDirtyFlag() {
		_dirtyFlag = true;
	}
	/** background ���� ���ϴ� �Լ��̴�.
	 */
	public Color getBackground() {
		if (printFlag == true) {
			return Color.white;
		} else {
			return super.getBackground();
		}
	}
	/** ȭ���� hardcopy ����Ʈ�ϴ� �Լ��̴�.
	 */
	public void print(Graphics g) {
		Graphics eg = g.create();
		Rectangle clipR = g.getClipBounds();
		Rectangle newClipR = new Rectangle(clipR);
		eg.setClip(newClipR);
		eg.setColor(Color.white);
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.setInCanvas(true);
			ptr = _figures.getNext();
		}
		ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.draw(g,Const.DRAWING,eg);
			ptr = _figures.getNext();
		}
		ptr = _figures.getFirst();
		while (ptr != null) {
			ptr.setInCanvas(ptr.checkInRegion(_canvasRgn));
			ptr = _figures.getNext();
		}
		eg.dispose();
	}
	/** ����Ʈ ��ȭ ���ڿ� ���ؼ� ȣ��Ǵ� ����Ʈ ���� �Լ��̴�.
	 */
	public int print(Graphics g,PageFormat pageFormat,int pageIndex) throws PrinterException {
		if (pageIndex >= totalPages) {
			return NO_SUCH_PAGE;
		}
		if (totalPages == 1) {
			// �� ���������ٰ� ����Ʈ �ϴ� ����̱� ������ �׸��� �߾ӿ� ����Ʈ��
			int pI = pageIndex + 1;
			int nPageRow = 1;
			int nPageColumn = 1;
			Graphics2D g2 = (Graphics2D) g;
			g2.setBackground(Color.white);
			g2.setColor(Color.lightGray);
			g2.setFont(new Font("Courier",Font.ITALIC|Font.BOLD,8));
			g2.setClip(0,0,physicalPaperWidth,physicalPaperHeight);
			g2.drawRect(paperOX,paperOY,paperWidth,paperHeight);
			g2.setColor(Color.black);
			g2.drawString("Domain Object "+getFrameName()+" (ETRI-CSTL) ["+nPageRow+","+nPageColumn+"] of ["+nOfRowOfPages+","+nOfColumnOfPages+"]",
					 paperOX+paperWidth-250,paperOY+paperHeight-2);
			g2.setClip(paperOX,paperOY,paperWidth,paperHeight);
			int figureWidth = (_maxX - _minX) * 3 / 5;
			int figureHeight = (_maxY - _minY) * 3 / 5;
			int tranX = (paperWidth - figureWidth) / 2 - _minX * 3 / 5;
			int tranY = (paperHeight - figureHeight) / 2 - _minY * 3 / 5;
			g2.translate(tranX,tranY);
			g2.scale(3./5.,3./5.);
			g2.setFont(new Font("Courier",Font.PLAIN,MyFontSize));
			printFlag = true;
			print(g2); 
			printFlag = false;
			return PAGE_EXISTS;
		}
		int pI = pageIndex + 1;
		int nPageRow;
		int nPageColumn;
		if (pI % nOfColumnOfPages == 0) {
			nPageRow = pI / nOfColumnOfPages;
			nPageColumn = nOfColumnOfPages;
		} else {
			nPageRow = pI / nOfColumnOfPages + 1;
			nPageColumn = pI % nOfColumnOfPages;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.white);
		g2.setColor(Color.lightGray);
		g2.setFont(new Font("Courier",Font.ITALIC|Font.BOLD,8));
		g2.setClip(0,0,physicalPaperWidth,physicalPaperHeight);
		g2.drawRect(paperOX,paperOY,paperWidth,paperHeight);
		g2.setColor(Color.black);
		g2.drawString("Domain Object "+getFrameName()+" (ETRI-CSTL) ["+nPageRow+","+nPageColumn+"] of ["+nOfRowOfPages+","+nOfColumnOfPages+"]",
					 paperOX+paperWidth-250,paperOY+paperHeight-2);
		g2.setClip(paperOX,paperOY,paperWidth,paperHeight);
		g2.translate(-(nPageColumn-1)*paperWidth+(paperOX+1),-(nPageRow-1)*paperHeight+(paperOY+1));
		g2.scale(3./5.,3./5.);
		g2.setFont(new Font("Courier",Font.PLAIN,MyFontSize));
		printFlag = true;
		print(g2); 
		printFlag = false;
		return PAGE_EXISTS;
	}
	/** ����Ʈ �޴� ���ÿ� ���Ͽ� ����Ʈ ��ȭ ���ڸ� ����� ������� �Է��� �޾Ƶ��̴� �Լ��̴�..
	 */
	public void print() {
		if (_figures.empty() == true) {
			this.beep("There is no figure to print.");
			return;
		}

		PrinterJob job = PrinterJob.getPrinterJob();
		if (job == null) {
			System.out.println("Print job fails.");
			return;
		}
		PageFormat pageFormat = job.defaultPage();
		pageFormat.setOrientation(PageFormat.PORTRAIT);
		PageFormat newPageFormat = job.pageDialog(pageFormat);
		if (pageFormat == newPageFormat) {
			return;
		}
		paperOX = 2;
		paperOY = 2;
		if (newPageFormat.getOrientation() == PageFormat.LANDSCAPE) {
			paperOX = 3;
			paperOY = 7;
		}
		job.setPrintable(this,newPageFormat);
		job.validatePage(newPageFormat);
		physicalPaperWidth = (int)newPageFormat.getWidth();
		physicalPaperHeight = (int)newPageFormat.getHeight();
		paperWidth = physicalPaperWidth - 40;
		paperHeight = physicalPaperHeight - 40;
	
		resetMinMaxXY();
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			ptr.minMaxXY();
			ptr = _figures.getNext();
		}
		/* 5/3 �� scale�� ������ ����, ���� scale�� 1/2�� �Ϸ��� 2/1�� ���ؾ��� */
		nOfRowOfPages = _maxY / (5*paperHeight/3) + 1;
		nOfColumnOfPages = _maxX / (5*paperWidth/3) + 1;
		totalPages = nOfRowOfPages * nOfColumnOfPages;
		if (job.printDialog()) {
			try {
				job.print();
			} catch (Exception PrinterException) {
			}
		}
	}
	/** ȭ���� �׸� �߿��� ��Ű�� �̸����� ������ �Լ��̴�.
	 */
	public void collectPackageNames(StringList packages) {
		Figure ptr = _figures.getFirst();
		while(ptr != null) {
			if (ptr.whoAreYou().equals(Const.IAMPACKAGE)) {
				String pName = ((PackageTemplate)ptr).getName();
				if (packages.inList(pName) == false) {
					packages.insert(pName);
					((PackageTemplate)ptr).collectPackageNames(packages);
				}
			}
			ptr = _figures.getNext();
		}
	}
	/** ��ư ���� �̺�Ʈ�� ó���ϴ� �ݹ� �Լ��̴�.
	 */
	public void actionPerformed(ActionEvent event) {
		_popupflag = false;
		String command = event.getActionCommand();
		if (command.equals("Delete")) {
			setDirtyFlag();
			deleteCurrentFigure();
		} else if (command.equals("Copy")) {
			setDirtyFlag();
			copyCurrentFigure();
		} else if (command.equals("Paste")) {
			setDirtyFlag();
			pasteFigureFromMemory();
		} else if (command.equals("EditPaste")) {
			setDirtyFlag();
			pasteFigureAtRandomPosFromMemory();
		} else if (command.equals("CopyMemory")) {
			copyCurrentFigureInMemory();
		} else if (command.equals("Refresh")) {
			localMakeClear(true);
		} else if (command.equals("100")) {
			doZoom(100);
		} else if (command.equals("90")) {
			doZoom(90);
		} else if (command.equals("80")) {
			doZoom(80);
		} else if (command.equals("70")) {
			doZoom(70);
		} else if (command.equals("60")) {
			doZoom(60);
		} else if (command.equals("50")) {
			doZoom(50);
		} else if (command.equals("40")) {
			doZoom(40);
		} else if (command.equals("30")) {
			doZoom(30);
		} else if (command.equals("20")) {
			doZoom(20);
		} else if (command.equals("10")) {
			doZoom(10);
		} else if (command.equals("Print")) {
			print();
		}
	}
	/** ���콺 �̺�Ʈ�� ó���ϴ� �̺�Ʈ �ڵ鷯�̴�.
	 */	
	public void processMouseEvent(MouseEvent event)
	{
		localProcessMouseEvent(event);
	}
	/** ���콺 �̺�Ʈ�� ó���ϴ� �̺�Ʈ �ڵ鷯�� �����Լ��̴�.
	 */	
	abstract public void localProcessMouseEvent(MouseEvent event);
	/** ���� Ŭ������ ���콺 �̺�Ʈ �ڵ鷯�� ȣ���ϴ� �Լ��̴�. 
	 */
	public void superProcessMouseEvent(MouseEvent event)
	{
		super.processMouseEvent(event);
	}
	/** ���콺�� �̵��� ó���ϴ� �̺�Ʈ �ڵ鷯�̴�.
	 */
	public void mouseMoved(MouseEvent e) {
		_reallyMovedFlag = true;
		CPoint ev = new CPoint();
		ev.x = (int)((double)e.getX()/_shrinkRatio);
		ev.y = (int)((double)e.getY()/_shrinkRatio);
		if (FLinesStopper == true) {
			linesStopper(ev,false);
		} else if (FFixPointerAbsolute == true) {
			fixPointerAbsolute(ev);
		} else if (FTraceEnterForList == true) {
			localTraceEnter(ev,_activeFigures,true);
		}
		ev = null;
	}
	/** �巢 ���·� ���콺�� �̵��ϴ� ���� ó���ϴ� �̺�Ʈ �ڵ鷯�̴�.
	 */
	public void mouseDragged(MouseEvent e) {
		_reallyMovedFlag = true;
		CPoint ev = new CPoint();
		ev.x = (int)((double)e.getX()/_shrinkRatio);
		ev.y = (int)((double)e.getY()/_shrinkRatio);
		if (FDrawingHandler == true) {
			localDrawing(ev);
		} else if (FMovingHandler == true) {
			localMoving(ev);
		} else if (FAnyTionDrawingHandler == true) {
			AnyTion.drawingHandler(this,ev);
			if (FTraceEnterForList == true) {
				Point pt = _currentLines.last();
				ev.x = pt.x;
				ev.y = pt.y;
				localTraceEnter(ev,_activeFigures,true);
			}
		}
		ev = null;
	}
	/** Ű�� �������� �� �̺�Ʈ�� ó���ϴ� �ڵ鷯�̴�.
	 */
	public void keyPressed(KeyEvent e) {
		int nCode = e.getKeyCode();
		OnKeyDown(nCode);
	}
	/** Ű�� �������� ������� �� �̺�Ʈ�� ó���ϴ� �ڵ鷯�̴�.
	 */
	public void keyReleased(KeyEvent e) {
		int nCode = e.getKeyCode();
		OnKeyUp(nCode);
	}
	/** Ű����� ���� �� ���ڰ� �ԷµǾ��� �� �̺�Ʈ�� ó���ϴ� �ڵ鷯�̴�.
	 */
	public void keyTyped(KeyEvent e) {
	}
	/** ���� �̺�Ʈ�� ó�� �Լ��̴�.
	 */
	protected void processEvent(AWTEvent event) {
		super.processEvent(event);
	}
	/** ������Ʈ �̺�Ʈ�� ó�� �Լ��̴�.
	 */
	protected void processComponentEvent(ComponentEvent event) {
		switch(event.getID()) {
		case ComponentEvent.COMPONENT_RESIZED:
			doCanvasResize();
			break;
		}
		super.processComponentEvent(event);
	}
	/** ��ũ�� ���� �̵��� ���� �̺�Ʈ �ڵ鷯�̴�.
	 */
	public void adjustmentValueChanged(AdjustmentEvent event) {
		int spx = hScrollBar.getValue();
		int spy = vScrollBar.getValue();
		originX = spx;
		originY = spy;
		makeCanvasRegion();
		if (_currentFocus != null) {
			_currentFocus.makeRegion();
		}
		Figure all = _figures.getFirst();
		while (all != null) {
			all.setInCanvas(all.checkInRegion(_canvasRgn));
			if (all.inCanvas()) {
				all.makeRegion();
			} else {
				all.resetRegion();
			}
			all = _figures.getNext();
		}
	}
	public void deactivateTextField() {
		activeTextField.getDocument().removeDocumentListener(this);
		activeTextField.removeKeyListener(this);
		addKeyListener(this);
		activeTextField.setVisible(false);
		activeTextField = null;
		this.requestFocus();
	}
	public void activateTextField(int ox,int oy,String lineText) {
		int sx = (int)((double)ox*_shrinkRatio);
		int sy = (int)((double)oy*_shrinkRatio);
		checkLimitForFigure(sx,sy,null);
		int textWidth = Text.getStringWidth(getgraphics(),_font,lineText) + 10;
		if (activeTextField == null) {
			activeTextField = new JTextField(lineText);
			activeTextField.setFont(_font);
			activeTextField.setEditable(true);
			activeTextField.setBorder(new BevelBorder(BevelBorder.LOWERED,getBackground(),getBackground()));
			activeTextField.addKeyListener(this);
			activeTextField.getDocument().addDocumentListener(this);
			removeKeyListener(this);
			add(activeTextField);
			activeTextField.setCaretPosition(0);
			activeTextField.setBounds(sx-2,sy+1,textWidth,_fontSizeV);
			activeTextField.requestFocus();
		} else {
			activeTextField.setText(lineText);
			activeTextField.setCaretPosition(0);
			activeTextField.setBounds(sx-2,sy+1,textWidth,_fontSizeV);
			activeTextField.requestFocus();
		}
	}
	private void changeActiveTextFieldSize() {
		if (activeTextField == null) return;
		int x = activeTextField.getX();
		int y = activeTextField.getY();
		int h = activeTextField.getHeight();
		int w = activeTextField.getWidth();
		if (originX+_width-2 < x+w) return;
		String s = activeTextField.getText();
		int textWidth = Text.getStringWidth(getGraphics(),_font,s) + 10;
		activeTextField.setBounds(x,y,textWidth,h);
	}
	public void insertUpdate(DocumentEvent e) {
		changeActiveTextFieldSize();
	}
	public void removeUpdate(DocumentEvent e) {
		changeActiveTextFieldSize();
	}
	public void changedUpdate(DocumentEvent e) {
		changeActiveTextFieldSize();
	}


	/** Ű�� �������� ���� �۾��� �����ϴ� �Լ��̴�.
	 */
	protected void OnKeyDown(int nCode)
	{
		if (_editingTag == true && _currentFocus != null) {
			Text currentText = (Text)_currentFocus.focus();
			switch (nCode) { 
			case KeyEvent.VK_CONTROL :
				_ctrlKeyPressed = true;
				break;
			case KeyEvent.VK_ESCAPE :
				editorFinished(true);
				break;
			case KeyEvent.VK_UP :
				currentText.goup();
				break;
			case KeyEvent.VK_DOWN :
				currentText.godown();
				break;
			case KeyEvent.VK_BACK_SPACE :
				if (_ctrlKeyPressed == true) {
					currentText.delLine();
				}
				break;
			case KeyEvent.VK_ENTER :
				if (currentText.focus().whoAreYou().isEqual(Const.IAMSINGLELINETEXT)) {
					currentText.bye();
					editorFinished(true);
				} else {
					currentText.gonextline();
				}
				break;
			}
		} else if (_currentFocus != null) {
			if (_enable == false) return;
			if (FDrawingHandler == true) return;
			if (FMovingHandler == true) return;
			switch (nCode) {
			case KeyEvent.VK_DELETE :
				deleteCurrentFigure();
				break;
			}
		}
	}
	/** Ű�� �������ٰ� ������� ���� �۾��� �����ϴ� �Լ��̴�.
	 */
	protected void OnKeyUp(int nCode)
	{
		if (_editingTag == true) {
			if (nCode == KeyEvent.VK_CONTROL) _ctrlKeyPressed = false;
		}
	}
	/** ���콺�� ���� ���õ� �׸� ��ü�� ǥ���ϴ� �Լ��̴�.
	 */
	public void setSelectFigure(Figure figure)
	{
		_figures.remove(figure);
		_figures.insert(figure,0);
		Graphics eg = this.getgraphics();
		Graphics hg = this.getgraphics();
		eg.setColor(getForeground());
		hg.setColor(Color.blue);
		figure.draw(hg,Const.DRAWING,eg);
		hg.dispose();	
		eg.dispose();
	}
	/** �� ȭ���� ���ϴ� �������� �̸��� �����ִ� �Լ��̴�. 
	 */	
	abstract public String getFrameName();
	/** paint �̺�Ʈ�� �߻���Ű�� �Լ��̴�.
	 */
	public void dispatchPaintEvent(int x,int y,int w,int h) 
	{
		super.paintImmediately(x,y,w,h);
		/*
		_paintEvent = null;
		Rectangle box = new Rectangle(x,y,w,h);
		_paintEvent = new PaintEvent(this,PaintEvent.UPDATE,box);
		_paintEvent.setUpdateRect(box);
		*/
	}
	/** �Է����� �־��� �̸��� ���� Ŭ���� �����͸� ���ϴ� �Լ��̴�.
	 */
	public ClassTemplate getClassTemplatePtr(String name) {
		Figure ptr = _figures.getFirst();
		while (ptr != null) {
			if (ptr.whoAreYou().isEqual(Const.IAMCLASSTEMPLATE)) {
				ClassTemplate foundClass = (ClassTemplate) ptr;
				if (foundClass.getName().compareTo(name) == 0) {
					return foundClass;
				}
			}
			ptr = _figures.getNext();
		}
		return null;
	}
	/** Ŭ������ highlight�ϴ� �Լ�
	 */
	public void highlightClass(String name) {
		_focusClass = getClassTemplatePtr(name);
		if (_focusClass == null) return;
		Point pt = _focusClass.center();
		int cx = pt.x;
		int cy = pt.y;
		int dx = cx - (originX+_width/2);
		int dy = cy - (originY+_height/2);
		if (dx != 0 || dy != 0) moveScrollB(dx,dy);
	}
	public void resetFocusClass() {
		_focusClass = null;
		repaint();
	}
	/** aggregation ���ε��� �׸��� �Լ� �̴�.
	 */
	public void createAggregation(DependencyInfoList actualAggregationInfoList) {
		DependencyInfoList theList = actualAggregationInfoList;
		DependInfo info = theList.getFirst();
		while(info != null) {
			ClassInfo from = info.getFromClass();
			ClassInfo to = info.getToClass();
			ClassContent fromContent = from.getContentPtr();
			ClassContent toContent = to.getContentPtr();
			if (fromContent != null && toContent != null) {
				String fromName = fromContent.nameContent.getName();
				String toName = toContent.nameContent.getName();
				ClassTemplate fromClass = getClassTemplatePtr(fromName);
				ClassTemplate toClass = getClassTemplatePtr(toName);
				if (fromClass != null && toClass != null) 
					fromClass.drawAggTionTo(this,toClass);
			}
			info = theList.getNext();
		}
	}
	/** dependency ���ε��� �׸��� �Լ� �̴�.
	 */
	public void createDependency(DependencyInfoList actualDependencyInfoList) {
		DependencyInfoList theList = actualDependencyInfoList;
		DependInfo info = theList.getFirst();
		while(info != null) {
			ClassInfo from = info.getFromClass();
			ClassInfo to = info.getToClass();
			ClassContent fromContent = from.getContentPtr();
			ClassContent toContent = to.getContentPtr();
			if (fromContent != null && toContent != null) {
				String fromName = fromContent.nameContent.getName();
				String toName = toContent.nameContent.getName();
				ClassTemplate fromClass = getClassTemplatePtr(fromName);
				ClassTemplate toClass = getClassTemplatePtr(toName);
				if (fromClass != null && toClass != null) 
					fromClass.drawDependTo(this,toClass);
			}
			info = theList.getNext();
		}
	}
	/** ������ ����� �׸����� �׷��ִ� �Լ��̴�.
	 */
	public void createReversedDiagram(ParseTreeNode root,StringList classNames,StrRelation gentions) {
		/*
		ParseTreeNode classesLevel = root;
		while (classesLevel != null) {
			System.out.println(classesLevel.value); // "class" or "interface" string
			ParseTreeNode aClassLevel = classesLevel.children;
			String classNameInfo = aClassLevel.toStringAsClassName();
			ParseTreeNode membersLevel = aClassLevel.siblings.children;
			// �Ʒ� ������ �ι� ���鼭 ó������ ����Ÿ ����� �ι�°�� ��� �Լ��� ����Ʈ�Ѵ�.
			while (membersLevel != null) {
				if (membersLevel.value.equals("Data")) {
					ParseTreeNode aDataMember = membersLevel.children;
					String data = aDataMember.toStringAsDataMember();
					System.out.println(data);
				}
				membersLevel = membersLevel.siblings;
			}
			membersLevel = aClassLevel.siblings.children;
			while (membersLevel != null) {
				if (membersLevel.value.equals("Function")) {
					ParseTreeNode aMemberFunction = membersLevel.children;
					String func = aMemberFunction.toStringAsMemberFunction();
					System.out.println(func);
				}
				membersLevel = membersLevel.siblings;
			}
			classesLevel = classesLevel.siblings;
		}
		*/
		StringList supersOnly = new StringList();
		StrRelItem item = gentions.getFirst();
		while(item != null) {
			supersOnly.insert(new String(item.first()));
			item.setMark(false);
			item = gentions.getNext();
		}
		StringList roots = new StringList();
		String aSuper = supersOnly.getFirst();
		while (aSuper != null) {
			boolean isRoot = true;
			item = gentions.getFirst();
			while(item != null) {
				if (item.second().compareTo(aSuper) == 0) {
					isRoot = false;
					break;
				}
				item = gentions.getNext();
			}
			if (isRoot == true) {
				roots.insert(new String(aSuper));
			}
			aSuper = supersOnly.getNext();
		}
		DefaultMutableTreeNode theRoot = new DefaultMutableTreeNode(new GentionTreeNodeValue("root"));
		constructInheritanceTree(theRoot,roots,gentions,theRoot);
		if (theRoot.getChildCount() > 0)
			setTreeNodePosition((DefaultMutableTreeNode)theRoot.getFirstChild(),0,0);
		Enumeration e = theRoot.breadthFirstEnumeration();
		deployClassTemplates(root,e);
		Modeler.TheProgressBar.setString("Please wait for drawing inheritance tree.");
		Modeler.TheProgressBar.setValue(root.getSiblingCount());
		int nChild = theRoot.getChildCount();
		if (nChild > 0) {
			for (int i = 0; i < nChild; i++) {
				deployGeneralizations((DefaultMutableTreeNode)theRoot.getChildAt(i));
			}
		}
	}
	private void deployGeneralizations(DefaultMutableTreeNode currentNode) {
		int nChild = currentNode.getChildCount();
		if (nChild == 0) return;
		GentionTreeNodeValue value = (GentionTreeNodeValue)currentNode.getUserObject();
		for (int i = 0; i < nChild; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)currentNode.getChildAt(i);
			GentionTreeNodeValue childValue = (GentionTreeNodeValue)aNode.getUserObject();
			value.classPtr.drawGentionTo(this,childValue.classPtr);
		}		
		for (int i = 0; i < nChild; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)currentNode.getChildAt(i);
			deployGeneralizations(aNode);
		}		
	}
	private void deployClassTemplates(ParseTreeNode root,Enumeration e) {
		int startX = 20;
		int startY = 20;
		int gridWidth = 150;
		int gridHeight = 250;
		int gridX = startX;
		int gridY = startY;
		// skip the first node
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)e.nextElement();
		while (e.hasMoreElements()) {
			treeNode = (DefaultMutableTreeNode)e.nextElement();
			GentionTreeNodeValue value = (GentionTreeNodeValue)treeNode.getUserObject();
			int newI = value.i;
			int newJ = value.j;
			gridX = startX + newI * gridWidth;
			gridY = startY + newJ * gridHeight;
			ClassTemplate newClass = 
							  new ClassTemplate(this,gridX,gridY,fontSizeV(),classPopup());
			value.classPtr = newClass;
			newClass.setNameString(value.className);
			newClass.adjustClassSize();
			newClass.makeRegion();
			newClass.registerClassContent();
			if (_frame instanceof Modeler && Modeler.BrowserDlg != null) {
				Modeler.BrowserDlg.replaceClassContentFor(newClass.getName());
			}
			if (_frame instanceof Modeler) {
				Modeler aModeler = Modeler.allModelers.getFirst();
				while (aModeler != null) {
					aModeler.controller().repaint();
					aModeler = Modeler.allModelers.getNext();
				}
			}
			_figures.insert(newClass,0);
		}
		_maxWidth = gridWidth * gridX + 100;
		_maxHeight = gridHeight * gridY + 100;
		setSize(new Dimension(_maxWidth,_maxHeight));
		setPreferredSize(new Dimension (_maxWidth,_maxHeight));
		
		Modeler.TheProgressBar.setMaximum(root.getSiblingCount());
		int count = 0;
					
		ParseTreeNode nodePtr = root;
		while (nodePtr != null) {
			ParseTreeNode nextClassNode = nodePtr.children;
			String classNameInfo = nextClassNode.toStringAsClassName();
			
			Modeler.TheProgressBar.setString(classNameInfo);
			Modeler.TheProgressBar.setValue(count++);
			
			ClassTemplate foundClass = null;
			Figure ptr = _figures.getFirst();
			while(ptr != null) {
				ClassTemplate clss = (ClassTemplate)ptr;
				if (clss.getName().compareTo(classNameInfo) == 0) {
					foundClass = clss;
					break;
				}
				ptr = _figures.getNext();
			}
			if (foundClass == null) {
				nodePtr = nodePtr.siblings;
				continue;
			}
			ParseTreeNode membersLevel = nextClassNode.siblings.children;
			// �Ʒ� ������ �ι� ���鼭 ó������ ����Ÿ ����� �ι�°�� ��� �Լ��� ����Ʈ�Ѵ�.
			while (membersLevel != null) {
				if (membersLevel.value.equals("Data")) {
					ParseTreeNode aDataMember = membersLevel.children;
					String data = aDataMember.toStringAsDataMember();
					foundClass.addDataMemberString(data);
				}
				membersLevel = membersLevel.siblings;
			}
			membersLevel = nextClassNode.siblings.children;
			while (membersLevel != null) {
				if (membersLevel.value.equals("Function")) {
					ParseTreeNode aMemberFunction = membersLevel.children;
					String func = aMemberFunction.toStringAsMemberFunction();
					foundClass.addMemberFunctionString(func);
				}
				membersLevel = membersLevel.siblings;
			}
			
			foundClass.adjustClassSize();
			foundClass.makeRegion();
		
			draw(foundClass);
			String name = foundClass.getName();
			
			foundClass.registerClassContent();
			if (_frame instanceof Modeler && Modeler.BrowserDlg != null) {
				Modeler.BrowserDlg.replaceClassContentFor(foundClass.getName());
			}
			if (_frame instanceof Modeler) {
				Modeler aModeler = Modeler.allModelers.getFirst();
				while (aModeler != null) {
					aModeler.controller().repaint();
					aModeler = Modeler.allModelers.getNext();
				}
			}
			nodePtr = nodePtr.siblings;
		}
		Figure aFig = _figures.getFirst();
		while(aFig != null) {
			draw(aFig);
			aFig = _figures.getNext();
		}
	}
	private void setTreeNodePosition(DefaultMutableTreeNode current,int startIndex,int depth)
	{
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)current.getParent();
		
		int count = parent.getChildCount();
		int totalWidth = startIndex;
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)parent.getChildAt(i);
			GentionTreeNodeValue value = (GentionTreeNodeValue)aNode.getUserObject();
			value.j = depth;
			int width = getMaxWidth(aNode);
			value.i = totalWidth + width/2; 
			totalWidth = totalWidth + width;
			if (aNode.getChildCount() > 0)
				setTreeNodePosition((DefaultMutableTreeNode)aNode.getFirstChild(),totalWidth-width,depth+1);
		}
		
	}
	private int getMaxWidth(DefaultMutableTreeNode current) {
		if (current == null) return 0;
		if (current.getChildCount() == 0) return 1;
		int count = current.getChildCount();
		int w = 0;
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)current.getChildAt(i);
			w = w + getMaxWidth(aNode);
		}		
		return w;
	}
	private void constructInheritanceTree(DefaultMutableTreeNode theRoot,
										  StringList children,
										  StrRelation gentionsOrg,
										  DefaultMutableTreeNode currentNode) {
		if (children.empty() == true) return;
		int count = children.nOfList();
		String childName = children.getFirst();
		for (int i = 0; i < count; i++) {
			currentNode.insert(new DefaultMutableTreeNode(new GentionTreeNodeValue(childName)),i);
			childName = children.getNext();
		}
		count = currentNode.getChildCount();
		for (int i = 0; i < count; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)currentNode.getChildAt(i);
			StringList newChildren = new StringList();
			GentionTreeNodeValue value = (GentionTreeNodeValue)aNode.getUserObject();
			
			StrRelation gentions = new StrRelation();
			gentions.copy(gentionsOrg);
			
			StrRelItem item = gentions.getFirst();
			while(item != null) {
				if (item.mark() == false && item.first().compareTo(value.className) == 0) {
					item.setMark(true);
					newChildren.insert(new String(item.second()));
				}
				item = gentions.getNext();
			}
			constructInheritanceTree(theRoot,newChildren,gentions,aNode);
		}
	}
}