package Figure;

import java.util.Vector;
public class FigureList extends Vector 
{
// Attributes
    private static final long serialVersionUID = 6699984782326613197L;
// Operations
	public FigureList() {
		super();
	}
	public Figure getAt(int index) {
		/*
		Object obj = super.get(index);
		Class theClass = obj.getClass();
		if (Figure.isKindOf(obj,"Figure")) {
			return (Figure)obj;
		} else {
			return null;
		}
		*/
		return (Figure)super.get(index);

	}
	void addTail(Figure ptr) {
		super.add(ptr);
	}
	void removeFigure(Figure ptr) {
		boolean a = super.remove(ptr);
		System.out.println(ptr + "remove" + a);
	}
	public void removeAllElements(){
		super.removeAllElements();
	}
}