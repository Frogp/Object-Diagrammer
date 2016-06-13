package Figure;

import javax.swing.*;
import java.awt.*;
import java.io.*;


abstract public class Figure implements Serializable 
{
	private static final long serialVersionUID = 2888873963354499992L;	
	protected static int DOTSIZE = 5;
	protected Color _color;
	public transient Polygon _region;
	protected transient boolean _dotedFlag;
	protected transient JPanel _view;
	Figure(JPanel view, Color color) {
		_view = view;
		_color = color;
		_region = null;
	/*
	static boolean isKindOf(Object obj,String className) {
	// �� �Լ��� obj ��ü�� className Ŭ������ ���� Ŭ���� ��ü�ΰ��� �˻��Ѵ�.
		Class objClass = obj.getClass();
		String objClassName = objClass.getName();
		while(objClass != null) {
			if (objClassName.equals(className)) return true;
			objClass = objClass.getSuperclass();
			if (objClass != null) objClassName = objClass.getName();
		}
		return false;
	}
	*/
	}
	public void doSomethingAfterLoad(JPanel view) {
		setController(view);
		makeRegion();
	}
	public void setController (JPanel view)
	{
		_view = view;
	}
	public abstract void draw(Graphics g);
	public abstract void drawing(Graphics g,int newX,int newY);
	public abstract void makeRegion();
	public abstract void drawDots(Graphics g);
	public void eraseDots(Graphics g) {}
	abstract void move(int dx,int dy);
	public Polygon getRegion()
	{
		return _region;
	}
	public void move(Graphics g,int dx,int dy) {
		draw(g);
		move(dx,dy);
		draw(g);
	}
	public boolean ptInRegion(int x,int y) {
		if (_region != null) {
			return _region.contains(x,y);
		} else {
			return false;
		}
	}
	public void remove()
	{}

}