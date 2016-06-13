package Figure;

import Parse.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import modeler.*;
import javax.swing.text.*;
import java.awt.font.*;


public class EditableBox extends SlaveBox  implements KeyListener
{
	private static final long serialVersionUID = -891962973228746272L;
	transient JTextField textField;
	String text = "";
	String displayText = "";
	Font font;
	FontMetrics fm;
	boolean descripFlag = false;
	public EditableBox(JPanel view, KField field, int x1, int y1, int x2, int y2) {
		super(view, field, Color.black, x1,y1,x2,y2);
		textField = new JTextField(){
					 public void setBorder(Border border) {
						// No!
					}
				};
		textField.setEditable(true);
		//textField.setBorder(new  BevelBorder(BevelBorder.LOWERED,_view.getBackground(),_view.getBackground()));
		Graphics g = view.getGraphics();
		font = g.getFont();		
		textField.setFont(font);
		fm = view.getFontMetrics(font);
		System.out.println("-----------" + descripFlag);
	}
	public EditableBox(JPanel view, KField field, int x1, int y1, int x2, int y2, boolean descripFlag) {
		this(view, field, x1,y1,x2,y2);
		this.descripFlag = descripFlag;
	}
	public EditableBox(JPanel view, KField field, int x1, int y1, int x2, int y2, String set) {
		this(view, field, x1,y1,x2,y2);
		text = set;
		//this.descripFlag = descripFlag;
	}

	public JTextField getTextField()
	{
		return textField;
	}
	public void draw(Graphics g) {
		//System.out.println(descripFlag);
		if(descripFlag == true)
		{
			if (fm.stringWidth(text) > _x2 - _x1){
				displayText = text.substring(0,10);
				displayText = "\" " + displayText + "..."  + " \"";
				g.drawString(displayText,centerPoint.x - fm.stringWidth(displayText)/2 , centerPoint.y + (fm.getHeight() - fm.getAscent()) );
				return;
			}
			g.drawString("\" " + text  + " \"",centerPoint.x - fm.stringWidth("\" " + text  + " \"")/2  , centerPoint.y + (fm.getHeight() - fm.getAscent()) );
			return;
		}
		super.draw(g);
		if (text == null) return;

		if (fm.stringWidth(text) > _x2 - _x1){
			displayText = text.substring(0,4);
			displayText = displayText + "...";
			g.drawString(displayText,centerPoint.x - fm.stringWidth(displayText)/2 , centerPoint.y + (fm.getHeight() - fm.getAscent()) );
			return;
		}
		g.drawString(text,centerPoint.x - fm.stringWidth(text)/2  , centerPoint.y + (fm.getHeight() - fm.getAscent()) );
	}
	public void startEdit() {
		textField = new JTextField(){
					 public void setBorder(Border border) {
						// No!
					}
				};
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(true);
		//textField.setFont(font);
		//System.out.println(textField.getFont());
		//System.out.println(font);
		//textField.setBorder(new  BevelBorder(BevelBorder.LOWERED,_view.getBackground(),_view.getBackground()));
		_view.add(textField);
		System.out.println(_view.getFont());
		textField.requestFocus();
		textField.setText(text);
		textField.setCaretPosition(0);
		textField.setBounds(_x1+1,_y1+2,_x2-_x1-1,_y2-_y1-2);
		textField.addKeyListener(this);
		textField.setBackground(_view.getBackground());
	}
	public void stopEdit() {
		if(textField == null)
			return;
		text = textField.getText();
		textField.removeKeyListener(this);
		textField.setVisible(false);
		_view.remove(textField);
		_view.requestFocus();
	}
	public void keyPressed(KeyEvent e) {
		int nCode = e.getKeyCode();
		if (nCode == KeyEvent.VK_ESCAPE)
		{
			((DrawView)_view).stopEdit();
		}
	}
	/** 키가 눌러졌다 띄어졌을 때 이벤트를 처리하는 핸들러이다.
	 */
	public void keyReleased(KeyEvent e) {
	}
	/** 키보드로 부터 한 문자가 입력되었을 때 이벤트를 처리하는 핸들러이다.
	 */
	public void keyTyped(KeyEvent e) {
	}
}

