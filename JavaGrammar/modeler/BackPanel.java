package modeler;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import Parse.*;
import Figure.*;
 
class BackPanel extends JPanel 
	{
    private static final long serialVersionUID = -1439925679529315781L;
	public ControllPane splitPane;
	public Modeler modeler;	

	BackPanel(Modeler modeler)
	{   
		this.modeler = modeler;
		splitPane = new ControllPane(this, JSplitPane.HORIZONTAL_SPLIT);

	}
	public void setClasses(ArrayList<KClass> loadedClass)
	{
		modeler.setClasses(loadedClass);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		splitPane.repaint();
	}	
	void setNodes(ArrayList<KClass> classes)
	{
		splitPane.setNodes(classes);
	}
	void setNodes(ArrayList<KClass> classes, boolean customClassFlag)
	{
		splitPane.setNodes(classes, customClassFlag);
	}
	public ArrayList<String> getSuperClasses(String aClass) {
		return modeler.getSuperClasses(aClass);
	}
	public void classPairsClear()
	{
		modeler.classPairsClear();
	}
	public KClassPairList getClassPair()
	{
		return modeler.getClassPair();
	}
	public ControllPane getSplitPane()
	{     
		return splitPane;    
	} 
	public void toggleTree()
	{
		splitPane.toggleTree();
	}
	public DrawerModel getModel()
	{
		return splitPane.getModel();
	}
	public void setClassPair(KClassPairList pairClass)
	{
		modeler.setClassPair(pairClass);
	}
	public ArrayList<KClass> getClasses(){
		return modeler.getClasses();
	}
}