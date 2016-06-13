package modeler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import Parse.*;
import Figure.*;

public class ControllPane extends JSplitPane 
{
	private static final long serialVersionUID = -2306356800769814579L;
	public BackPanel backPanel;
	public TreeView tree;
	public DrawView draw;
	public UnPrintalbleClassTree UnPTree;
	boolean toogleTreeFlag;
	ControllPane(BackPanel backPanel, int style)
	{
		super(style);
		this.backPanel = backPanel;
        draw = new DrawView(this);
		tree = new TreeView(this); 
		UnPTree = new UnPrintalbleClassTree(this);
		JScrollPane scrolledUnPTree = new JScrollPane(UnPTree);
        JScrollPane scrolledTreeView = new JScrollPane(tree);		
		JSplitPane splitTreePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitTreePane.setBottomComponent(scrolledUnPTree);
		splitTreePane.setTopComponent(scrolledTreeView);
        splitTreePane.setDividerLocation(300); 
		setLeftComponent(splitTreePane);		

		JScrollPane scrolledDrawView = new JScrollPane(draw);
        setRightComponent(scrolledDrawView); 
		

		toogleTreeFlag = true;           
        Dimension minimumSize = new Dimension(100, 100);
        scrolledDrawView.setMinimumSize(minimumSize);
        scrolledTreeView.setMinimumSize(minimumSize);
        setDividerLocation(300); 
        setPreferredSize(new Dimension(100, 400)); 

	}
	public ArrayList<String> getSuperClasses(String aClass) {
		return backPanel.getSuperClasses(aClass);
	}

	public void setClasses(ArrayList<KClass> loadedClass)
	{
		backPanel.setClasses(loadedClass);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
    	tree.repaint();
		draw.repaint();
	}
	public void setUpTree(JTree tv)
	{
		tree = (TreeView)tv;
	}
	public DrawerModel getModel()
	{
		return draw.getModel();
	}
	public void toggleTree()
	{
		if(toogleTreeFlag == true){
			remove(getLeftComponent());
			toogleTreeFlag = false;
		}else if (toogleTreeFlag == false){
			JScrollPane scrolledTreeView = new JScrollPane(tree);
			JScrollPane scrolledUnPTree = new JScrollPane(UnPTree);
			JSplitPane splitTreePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitTreePane.setBottomComponent(scrolledUnPTree);
			splitTreePane.setTopComponent(scrolledTreeView);
			splitTreePane.setDividerLocation(300); 
			setLeftComponent(splitTreePane);
	        setDividerLocation(300); 
			toogleTreeFlag = true;
		}
	}
	void setNodes(ArrayList<KClass> classes, boolean customClassFlag)
	{
		tree.setNodes(classes);
		if(customClassFlag == true)
			return;
///////////UnPrintableClassTree//////////////////////////////////////////////////////
		UnPTree.setNodes(classes);
	}
	void setNodes(ArrayList<KClass> classes)
	{
		tree.setNodes(classes);
///////////UnPrintableClassTree//////////////////////////////////////////////////////
		UnPTree.setNodes(classes);
	}

	void addFigure(Figure tmp)
	{
		draw.addFigure(tmp);
	}
	public TreeView getTree()
	{
		return tree;
	}
	public UnPrintalbleClassTree getUnPrintableClassTree()
	{
		return UnPTree;
	}
	public ArrayList<KClass> getClasses(){
		return backPanel.getClasses();
	}
	public void classesClear()
	{
		if(getClasses() == null)
			return; 
		getClasses().clear();
	}
	public void classPairsClear()
	{
		backPanel.classPairsClear();
	}
	public KClassPairList getClassPair()
	{
		return backPanel.getClassPair();
	}
	public void setClassPair(KClassPairList pairClass)
	{
		backPanel.setClassPair(pairClass);
	}



}