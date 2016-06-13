package modeler;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

import java.lang.*;

import Parse.*;

class MyRenderer extends DefaultTreeCellRenderer {
	ImageIcon rootIcon;
	ImageIcon classIcon;
	ImageIcon fieldIcon;
	ImageIcon methodIcon;
	ImageIcon addClassIcon;
    private static final long serialVersionUID = -1705235819446852707L;
    public MyRenderer() {
		String names[] = {  "image\\class.gif",
                      "image\\root.gif",
                      "image\\field.gif",
					  "image\\method.gif",
					  "image\\addclass.gif"};
		classIcon = new ImageIcon (createImage(names[0]));
		rootIcon = new ImageIcon (createImage(names[1]));
		fieldIcon = new ImageIcon (createImage(names[2]));
		methodIcon = new ImageIcon (createImage(names[3]));
		addClassIcon = new ImageIcon (createImage(names[4]));

    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		Object currentCell = node.getUserObject();

		if(currentCell.getClass().getName().equals("java.lang.String")){
			setIcon(rootIcon);
		}else if(currentCell.getClass().getName().equals("Parse.KClass")){
			if(((KClass)currentCell).isCustom()){
				setIcon(addClassIcon);
			}else{
				setIcon(classIcon);
				setToolTipText("This is class.");
			}
		}else{
			DefaultMutableTreeNode nodeInfo2 = (DefaultMutableTreeNode)currentCell;
			if(nodeInfo2.getUserObject().getClass().getName().equals("Parse.KField")){
				setIcon(fieldIcon);
				setToolTipText("This is field.");
			}else if(nodeInfo2.getUserObject().getClass().getName().equals("Parse.KMethod")){
				setIcon(methodIcon);
				setToolTipText("This is method.");
			}
		}
        return this;
    }
	
	protected Image createImage(String path) {
		Image image = Toolkit.getDefaultToolkit().getImage(path);
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(image, 0);
		try { tracker.waitForID(0); } 
		catch (InterruptedException e) {}
		return image;
    }
}
