package modeler;

import Figure.*;
import Parse.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.tree.*;
import java.util.*;


public class DrawView extends JPanel implements MouseMotionListener, DropTargetListener, KeyListener
{
    private static final long serialVersionUID = 6174730296775253784L;
	DrawerModel model;
	Figure _selectedFigure;
	static int NOTHING = 0;
	static int MOVING = 1;
	static int LINE_DRAWING = 2;
	int _actionMode;
	int _currentX;
	int _currentY;
	ControllPane controllSplitPane;
	Line currentLine;

	DrawView(ControllPane controllSplitPane){
		this.controllSplitPane = controllSplitPane;
		model = new DrawerModel(this);
		addMouseMotionListener(this);
		_actionMode = NOTHING;
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		setDropTarget(new DropTarget(this,this));
		addKeyBinding(this);
		addKeyListener(this);
		requestFocus();
	}
	public ControllPane getControllSplitPane()
	{
		return controllSplitPane;
	}
	public void setClasses(ArrayList<KClass> loadedClass)
	{
		controllSplitPane.setClasses(loadedClass);
	}

	void addKeyBinding(JComponent jc) {
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false), "DELETE pressed");
        jc.getActionMap().put("DELETE pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
				if(_selectedFigure != null){
					if(_selectedFigure instanceof ClassBox){
						ClassBox cBox = (ClassBox)_selectedFigure;
						ArrayList<Line> lines = cBox.getSlaveConnectedLine();
						for(int i = 0; i < lines.size(); i++)
						{
							model.removeFigure(lines.get(i));
							lines.get(i).remove();
						}
						ArrayList<Line> lines2 = cBox.getClassConnectedLine();
						for(int j = 0; j < lines2.size(); j++)
						{
							model.removeFigure(lines2.get(j));
							lines2.get(j).remove();
						}
						cBox.removeLabel();
						model.removeFigure(_selectedFigure);						
						repaint();
					}else if(_selectedFigure instanceof Line){
						Line line = (Line)_selectedFigure;
						line.remove();
						model.removeFigure(_selectedFigure);
						repaint();
					}
					_selectedFigure = null;
				}
            }
        });
		/*
        jc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "A released");
        jc.getActionMap().put("A released", new AbstractAction() {
            Override
            public void actionPerformed(ActionEvent ae) {
                model.doSave();
            }
        });
		*/
		
	}
	//public boolean isFocusTraversable(){return true;}
	public void keyPressed(KeyEvent e) {
		/*
		if(e.getKeyCode() == KeyEvent.VK_S){
			System.out.println("S Pressed");
			model.doSave();
		}
		else if(e.getKeyCode() == KeyEvent.VK_O){
			System.out.println("O Pressed");
			model.onFileOpen();
		}
		*/
		System.out.println("KEYPRESSED");
	}
	public void keyReleased(KeyEvent e) {}	 
	public void keyTyped(KeyEvent e) {}
	public void processMouseEvent(MouseEvent e) {
		if (e.isPopupTrigger()) {
			onContextMenu(e);
		} else if (e.getID() == MouseEvent.MOUSE_PRESSED &&
			e.getModifiers() == MouseEvent.BUTTON1_MASK) {
			if (e.getClickCount() == 2) {
				startEdit(new java.awt.Point(e.getX(),e.getY()));
			} else {
				onLButtonDown(new java.awt.Point(e.getX(),e.getY()));
			}
		} else if (e.getID() == MouseEvent.MOUSE_RELEASED &&
			e.getModifiers() == MouseEvent.BUTTON1_MASK) {
			onLButtonUp(new java.awt.Point(e.getX(),e.getY()));
		} else {
			super.processMouseEvent(e);
		}
    }
	private void onContextMenu(MouseEvent e) {
		System.out.println("ContextMenu");
	}
	public void stopEdit() {
		if (_selectedFigure == null) return;
		_selectedFigure.eraseDots(getGraphics());
		if (!(_selectedFigure instanceof ClassBox)) return;
		ClassBox cbox = (ClassBox)_selectedFigure;
		if (!(cbox.getFocusedSlaveBox() instanceof EditableBox)) return;
		cbox.stopEdit();
		_selectedFigure.draw(getGraphics());
		_selectedFigure = null;
	}
	private void startEdit(java.awt.Point point) {
		stopEdit();
		// 새 그림 객체를 선택 한다.
		FigureList figures = getModel().getFigures();
		for(int i = 0; i < figures.size(); i++) {
			Figure ptr = figures.getAt(i);
			if (ptr.ptInRegion(point.x,point.y)) {
				_selectedFigure = ptr;
				break;
			}
		}
		if (_selectedFigure == null) return;
		if (!(_selectedFigure instanceof ClassBox)) return;
		ClassBox cbox = (ClassBox)_selectedFigure;
		if (!(cbox.getFocusedSlaveBox() instanceof EditableBox)) return;
		cbox.startEdit();
	}
	public void resetSelectedFigure() {
		_selectedFigure = null;
	}

	private void onLButtonDown(java.awt.Point point) {
		    stopEdit();
			if (_selectedFigure != null) {
				if (_selectedFigure instanceof ClassBox){
					System.out.println("ClassBox is selected");
					ClassBox cbox = (ClassBox)_selectedFigure;
					if(cbox.ptInSlaveRegion(point.x,point.y)){
						System.out.println("SlaveBox is selected");
						if(cbox.getFocusedSlaveBox() instanceof PointableBox){
							Point p = cbox.getFocusedSlaveCenterPoint();
							if(cbox.focusedSlaveHasLine() == true){
								model.removeFigure(cbox.getFocusedSlaveLine());
								cbox.getFocusedSlaveLine().remove();
							}
							currentLine = new Line(this, Color.black,p.x,p.y,p.x,p.y);
							_actionMode = LINE_DRAWING;
						//	repaint();
							return;
						}
					}
				}
				if (_selectedFigure.ptInRegion(point.x,point.y)) {
					_actionMode = MOVING;
					_currentX = point.x;
					_currentY = point.y;
					Graphics g = getGraphics();
					g.setXORMode(getBackground());
					_selectedFigure.eraseDots(g);
					//getModel().removeFigure(_selectedFigure);
					return;
				}
				if(_selectedFigure instanceof ClassBox){
					ClassBox cbox = (ClassBox)_selectedFigure;
					cbox.releaseFocusedSlaveBox();
				}
				_selectedFigure = null;

			}
			// 새 그림 객체를 선택 한다.
			FigureList figures = getModel().getFigures();
			for(int i = 0; i < figures.size(); i++) {
				Figure ptr = figures.getAt(i);
				if (ptr.ptInRegion(point.x,point.y)) {
					_selectedFigure = ptr;
					break;
				}
			}
			repaint();
			return;
	}
	private void onMouseMove(java.awt.Point point) {
		Graphics g = getGraphics();
		if (_actionMode == MOVING){
			g.setXORMode(getBackground());
			_selectedFigure.move(g,point.x-_currentX,point.y-_currentY);
			if(_selectedFigure instanceof ClassBox){				
				ClassBox cBox = (ClassBox)_selectedFigure;
				ArrayList<Line> slaveLine = cBox.getSlaveConnectedLine();
				for(int i = 0; i < slaveLine.size(); i++)
				{
					slaveLine.get(i).mySideBoxMove(g, point.x-_currentX,point.y-_currentY);
				}
				ArrayList<Line> classLine = cBox.getClassConnectedLine();
				for(int i = 0; i < classLine.size(); i++)
				{
					classLine.get(i).classSideBoxMove(g, point.x-_currentX,point.y-_currentY);
				}
			}			
			_currentX = point.x;
			_currentY = point.y;
		}else if(_actionMode == LINE_DRAWING){
			g.setXORMode(getBackground());
			ClassBox cbox = (ClassBox)_selectedFigure;
			Point p = cbox.getFocusedSlaveCenterPoint();
			currentLine.drawing(g,point.x,point.y);
			_currentX = point.x;
			_currentY = point.y;
		}
	}
	private void onLButtonUp(java.awt.Point point) {
		if (_selectedFigure == null || _actionMode == NOTHING) {
			return;
		}
		Graphics g = getGraphics();
		if (_actionMode == NOTHING) {
			g.setXORMode(getBackground());
			_selectedFigure.drawing(g,point.x,point.y);
		}else if(_actionMode == LINE_DRAWING){
			FigureList figures = getModel().getFigures();
			ClassBox selectedClassBox = (ClassBox)_selectedFigure;
			for(int i = 0; i < figures.size(); i++) {
				Figure ptr = figures.getAt(i);
				if (ptr instanceof ClassBox && ptr.ptInRegion(point.x,point.y) && selectedClassBox != ptr ) {
					ClassBox target = (ClassBox)ptr;
					if(selectedClassBox.focusedSlaveHasLine() == true)
					{
						getModel().removeFigure(selectedClassBox.getFocusedSlaveLine());
					}
					//System.out.println(selectedClassBox.getFocusedSlave());
					currentLine.setMySideBox((PointableBox)(selectedClassBox.getFocusedSlave()));
					currentLine.setClassSideBox(target);
					addFigure(currentLine);
					selectedClassBox.setFocusedSlaveLine(currentLine);
					target.setLine(currentLine);
					currentLine.makeRegion();
					//System.out.println(currentLine.classSideBox.getName());
					break;
				}
			}
			_actionMode = NOTHING;
			repaint();
			return;
		}
		_selectedFigure.makeRegion();
		//getModel().addFigure(_selectedFigure);
		_actionMode = NOTHING;
		repaint();
	}
	public void mouseDragged(MouseEvent e) 
	{
		onMouseMove(new java.awt.Point(e.getX(),e.getY()));
	}
	public void mouseMoved(MouseEvent e) 
	{
		_actionMode = NOTHING;
	}

	public DrawerModel getModel() {
		return model;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		DrawerModel model = getModel();
		FigureList figures = model.getFigures();

		for(int i = 0; i < figures.size(); i++) {
			Figure ptr = figures.getAt(i);
			if (ptr != null) {
				ptr.draw(g);
			}
		}
		if (_selectedFigure != null && _actionMode == NOTHING){ 
			_selectedFigure.drawDots(g);	
		}
    }
	public void addFigure(Figure tmp)
	{
		model.addFigure(tmp);
	}


	public void dragEnter(DropTargetDragEvent dtde){}
	public void dragOver(DropTargetDragEvent dtde){}
	public void dropActionChanged(DropTargetDragEvent dtde){}
	public void dragExit(DropTargetEvent dte){}
	public void drop(DropTargetDropEvent dtde) {
		//Transferable object = dtde.getTransferable();
		//object.getTransferData(DataFlavor.stringFlavor);
		//model.addFigure(tmp);
		Object s = "";
		Transferable tf = dtde.getTransferable();
		try{
			s = tf.getTransferData(DataFlavor.stringFlavor);
		}catch(Exception e){}

		System.out.println(s);
		System.out.println("BYE");
		DefaultMutableTreeNode node;
		if(s.equals("TreeView"))
			node = (DefaultMutableTreeNode)controllSplitPane.getTree().getLastSelectedPathComponent();
		else if(s.equals("UnPrintableClassTreeView"))
			node = (DefaultMutableTreeNode)controllSplitPane.getUnPrintableClassTree().getLastSelectedPathComponent();
		else 
			return;
		if(node == null)
			return;
		if(!(node.getUserObject() instanceof Parse.KClass))
			return;
		Object nodeInfo = node.getUserObject();
	   // if (node.isLeaf()) {
			//Figure tmp = new ClassBox(Color.BLACK,0,0,100,50);
		Point p = dtde.getLocation();
		KClass extendNode  = new KClass(((KClass)nodeInfo));
		
		ArrayList<KClass> classes = controllSplitPane.getClasses();
		ArrayList<String> supers = controllSplitPane.getSuperClasses(extendNode.getName());
		System.out.println(supers.size());
		for(int i = 0; i < 	supers.size(); i++){
			for(int j = 0; j < classes.size(); j++){
				if(classes.get(j).getName().equals(supers.get(i))){
					for(int k = 0; k < classes.get(j).getFields().size(); k++)
					{
						classes.get(j).getFields().get(k).setInherited();
						extendNode.addField(classes.get(j).getFields().get(k));
					}
				}
			}
		}
		//extendsNode.addField(KField field)
	

		Figure tmp = new ClassBox(this, extendNode,Color.BLACK,(int)p.getX(),(int)p.getY(),0,0);

		model.addFigure(tmp);
		tmp.makeRegion();
		repaint();
	}
	public TreeView getUpTree()
	{
		return controllSplitPane.getTree();
	}
	public UnPrintalbleClassTree getDownTree()
	{
		return controllSplitPane.getUnPrintableClassTree();
	}
	public void classesClear()
	{
		controllSplitPane.classesClear();
	}
	public void classPairsClear()
	{
		controllSplitPane.classPairsClear();
	}

	public ArrayList<KClass> getClasses(){
		return controllSplitPane.getClasses();
	}
	public KClassPairList getClassPair()
	{
		return controllSplitPane.getClassPair();
	}
	public void setClassPair(KClassPairList pairClass)
	{
		controllSplitPane.setClassPair(pairClass);
	}

}