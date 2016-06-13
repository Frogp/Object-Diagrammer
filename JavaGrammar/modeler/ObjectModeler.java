package modeler;

import java.awt.*;
import javax.swing.*;
import Parse.*;

class ObjectModeler extends Modeler{
	private static final long serialVersionUID = -3765225567360066070L;
	public ObjectModeler(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		
		BackPanel backPanel = new BackPanel(this);
		Container contentPane = this.getContentPane();
		contentPane.add(backPanel.getSplitPane());	

		//Modeler로 부터 상속받은 BackPanel의 레퍼런스
		controller = backPanel;
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		setSize(screenWidth, screenHeight);
		setLocation(0, 0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}