package Figure;

import java.awt.*;
import java.util.*;
import Parse.*;
import javax.swing.*;


public class SlaveBox extends MyBox
{
	private static final long serialVersionUID = -6176960686316921339L;
	ClassBox master;
	String name;
	String type;
	String subName;
	String subType;
	transient JLabel nameLabel;
	transient JLabel typeLabel;
	Font font;
	FontMetrics fontFm;
	int fontSizeV;
	KField field;

	public SlaveBox(JPanel view, Color color,int x1,int y1,int x2,int y2) {
		super(view, color,x1,y1,x2,y2);
	}
/*
	public SlaveBox(KField field, int x1, int y1, int x2, int y2) {
		super(Color.BLACK,x1,y1,x2,y2);
		name = field.getName();
		type = field.getType();
		if(name.length() > 5)
			subName = name.substring(0,5);
		subName=name;
		if(type.length() > 5)
			subType = type.substring(0,5);
		subType=type;
	}
*/
	public SlaveBox(JPanel view, KField field, Color color, int x1, int y1, int x2, int y2) {
		super(view, color,x1,y1,x2,y2);
		name = field.getName();
		type = field.getType();
		this.field = field;
		if(name.length() > 5)
			subName = name.substring(0,5);
		else 
			subName=name;
		if(type.length() > 5)
			subType = type.substring(0,5);
		else
			subType=type;
		makeCenterPoint();
/////////////////////////////////////////////////////////////////////////////////////////////////LABEL/////////////////////////////////////////////////////////////////
		font = _view.getFont();
		fontFm = _view.getFontMetrics(font);
		fontSizeV = fontFm.getHeight();

		nameLabel = new JLabel(name);
		nameLabel.setToolTipText(type+ " " + name);
		nameLabel.setBackground(_view.getBackground());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(font);
		view.add(nameLabel);
		typeLabel = new JLabel(type);
		typeLabel.setToolTipText(type+ " " + name);
		typeLabel.setBackground(_view.getBackground());
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setFont(font);
		view.add(typeLabel);
		if(field.isInherited()){
			nameLabel.setForeground(Color.LIGHT_GRAY    );
		}


	}
	public void draw(Graphics g) {
		g.setColor(_color);
		g.drawRect(_x1,_y1,_x2-_x1,_y2-_y1);
	//	g.drawString(subName,_x1,_y1);
	//	g.drawString(subType,_x1,_y2 + 10);
		nameLabel.setBounds(_x1 + 5, _y1 - (fontSizeV - fontFm.getDescent()), _x2 - _x1 - 10, fontSizeV - fontFm.getDescent());
		typeLabel.setBounds(_x1 + 5, _y2 , _x2 - _x1 - 10, fontSizeV - fontFm.getDescent());
	}
	public Point getCenterPoint()
	{
		return centerPoint;
	}
	public void makeCenterPoint()
	{
		centerPoint.x = (_x1+_x2)/2;
		centerPoint.y = (_y1+_y2)/2;
	}
	public void removeLabel()
	{
		_view.remove(nameLabel);
		_view.remove(typeLabel);

	}

	public void doSomethingAfterLoad(JPanel view) {
		super.doSomethingAfterLoad(view);
		nameLabel = new JLabel(name);
		nameLabel.setToolTipText(type+ " " + name);
		nameLabel.setBackground(_view.getBackground());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(font);
		if(field.isInherited()){
			nameLabel.setForeground(Color.LIGHT_GRAY    );
		}

		_view.add(nameLabel);
		typeLabel = new JLabel(type);
		typeLabel.setToolTipText(type+ " " + name);
		typeLabel.setBackground(_view.getBackground());
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setFont(font);
		_view.add(typeLabel);
		/*
		if (boxes == null)
			return;
		for(int i = 0; i < boxes.length; i++)
		{
			boxes[i].doSomethingAfterLoad(view);
		}
		*/
	}

}
