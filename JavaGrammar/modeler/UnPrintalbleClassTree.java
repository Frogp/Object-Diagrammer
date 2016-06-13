package modeler;

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.util.*;
import Parse.*;
import javax.swing.tree.*;


public class UnPrintalbleClassTree extends TreeView
{
    private static final long serialVersionUID = 7497708947080486946L;
	ControllPane splitControllPane;
	UnPrintalbleClassTree(ControllPane splitControllPane)
	{
		super(splitControllPane);
		rootNode = new DefaultMutableTreeNode("Built-in type Classes");
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		addTreeSelectionListener(this);
		this.splitControllPane = splitControllPane;
		setModel(treeModel) ; 
//		ToolTipManager.sharedInstance().registerComponent(this); 
		this.setCellRenderer(new MyRenderer());
		
		DragSource dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(this,DnDConstants.ACTION_COPY_OR_MOVE,
			this);

	}
	public void dragGestureRecognized(DragGestureEvent dge)
	{
		System.out.println("HELLO");
		String stringdata = "UnPrintableClassTreeView";
		try{
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
				this);
		}catch(Exception e){}
	}
	public void setNodes(ArrayList<KClass> classes)
	{
		ArrayList<KClass> refTypeClasses = new ArrayList<KClass>();
		for(int i = 0; i < classes.size(); i++){
			ArrayList<KField> fields = classes.get(i).getFields();
			for(int j = 0; j < fields.size(); j++ ){
				if(fields.get(j).isPrimitive() == true){
					continue;
				}
				KClass tmp = new KClass(fields.get(j).getType());
				if(classes.contains(tmp) == false && refTypeClasses.contains(tmp) == false){
					refTypeClasses.add(tmp);
				}
			}
		}
		super.setNodes(refTypeClasses);
	}
	public void inintTree(){
		rootNode = new DefaultMutableTreeNode("Built-in type Classes");
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		setModel(treeModel) ; 		
	}

}
