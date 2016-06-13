package figure;


import java.lang.*;

public class StrRelItem extends Object {
	private String _first;
	private String _second;
	private boolean _mark;
	public StrRelItem() {
		_first = null;
		_second = null;
		_mark = false;
	}
	public StrRelItem(String first,String second) {
		this();
		if (first != null) _first = new String(first);
		if (second != null) _second = new String(second);
	}
	public String first() {
		return new String(_first);
	}
	public String second() {
		return new String(_second);
	}
	public boolean mark() {
		return _mark;
	}
	public void setMark(boolean mark) {
		_mark = mark;
	}
	public boolean equals(StrRelItem item) {
		if (this == item) return true;
		if (_first == null) return false;
		if (_second == null) return false;
		if (_first.compareTo(item._first) == 0 && 
			_second.compareTo(item._second) == 0) return true;
		return false;
	}
};