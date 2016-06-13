package modeler;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;
import Parse.*;
import Figure.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class TreeView extends JTree implements TreeSelectionListener, DragGestureListener, DragSourceListener
{
	DefaultMutableTreeNode rootNode;
	DefaultTreeModel treeModel;
	ControllPane splitControllPane;
	Image image;
	static DataFlavor[] FV = new DataFlavor[] { DataFlavor.stringFlavor };	
    private static final long serialVersionUID = 5499430202825424411L;

	TreeView(ControllPane splitControllPane)
	{
		//rootNode = new DefaultMutableTreeNode("Reversed Classes");
		//treeModel = new DefaultTreeModel(rootNode);
		//treeModel.addTreeModelListener(new MyTreeModelListener());
		addTreeSelectionListener(this);
		inintTree();
		this.splitControllPane = splitControllPane;
		//setModel(treeModel) ; 
		ToolTipManager.sharedInstance().registerComponent(this); 
		this.setCellRenderer(new MyRenderer());
		
		DragSource dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(this,DnDConstants.ACTION_COPY_OR_MOVE,
			this);
	}
	public void inintTree(){
		rootNode = new DefaultMutableTreeNode("Reversed Classes");
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		setModel(treeModel) ; 		
	}
	public DefaultTreeModel getTreeModel()
	{
		return treeModel;
	}

//Drag and Drop
	public void dragGestureRecognized(DragGestureEvent dge)
	{
		System.out.println("HELLO");
		String stringdata = "TreeView";
		dge.startDrag(null,
				null,
				null,
				new Transferable() {
					//
					public Object getTransferData(DataFlavor flavor) {
						return stringdata;
					}
					public DataFlavor[] getTransferDataFlavors() {
						return FV;
					}
					public boolean isDataFlavorSupported(DataFlavor flavor) {
						return flavor.equals(DataFlavor.stringFlavor);
					}		
				},
				this
			);
	}
	public void dragEnter(DragSourceDragEvent dsde){}
	public void dragOver(DragSourceDragEvent dsde){}
	public void dropActionChanged(DragSourceDragEvent dsde){}
	public void dragExit(DragSourceEvent dse){}
	public void dragDropEnd(DragSourceDropEvent dsde) {
		System.out.println("11111111111111111");
	}

//////////////////////////////////////////////////////////////////////////////////////////
/*Tree에 Node 입력하기*/
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = getSelectionPath();

		parentNode = rootNode;
		/*//선택한 노드에 추가
		if (parentPath == null) {
			//There is no selection. Default to the root node.
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
		}
		*/
		return addObject(parentNode, child, true);
	}
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		if (shouldBeVisible) {
			scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}
//////////////////////////////////////////////////////////////////////////////////////////

	public void setNodes(ArrayList<KClass> classes)
	{	
		//root row is 1
		if(getRowCount() != 1){
			System.out.println("99999999999999999999999999999");
			setModel(null);
			inintTree();
		}
		for(int i = 0 ; i < classes.size(); i++)
		{
			DefaultMutableTreeNode classNode = this.addObject(classes.get(i));
			for(int j = 0; j < classes.get(i).getFields().size(); j++)
			{
				DefaultMutableTreeNode fieldNode = new DefaultMutableTreeNode(classes.get(i).getFields().get(j));
				addObject(classNode,fieldNode,false);
			}
			for(int j = 0; j < classes.get(i).getMethods().size(); j++)
			{
				DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(classes.get(i).getMethods().get(j));
				addObject(classNode,methodNode,false);
			}
		}

		repaint();
	}
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getLastSelectedPathComponent();
		if(node == null)
			return;
		if(!(node.getUserObject() instanceof Parse.KClass))
			return;
		Object nodeInfo = node.getUserObject();
	   // if (node.isLeaf()) {
			//Figure tmp = new ClassBox(Color.BLACK,0,0,100,50);
			/*
			Figure tmp = new ClassBox(nodeInfo,Color.BLACK,0,0,100,50);

			splitControllPane.addFigure(tmp);
			tmp.makeRegion();
	    	splitControllPane.repaint();
			*/
	  //  } else {

	 //   }
	}

}