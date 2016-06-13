package figure;

import java.awt.*;
import java.awt.Point;
import java.io.*;
/** �� Ŭ������ �ϳ��� ���а� ����Ǵ� �ٸ� ���е鿡 ���� ������ �����ϸ� ������ ���� ����
 * �� ó���ϴ� �Լ��̴�.
 */
public final 
	class LineNode extends Object implements Serializable {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -2120080165739596885L;
	/** �� ������ (_x1,_y1) ��ǥ�ʿ� ����Ǵ� ���е��� ����Ʈ
	 */
	private LineNodeList _beforeList;
	/** �� ������ (_x2,_y2) ��ǥ�ʿ� ����Ǵ� ���е��� ����Ʈ
	 */
	private LineNodeList _afterList;
	/** ���� �׸� ��ü
	 */
	private Line _line;
	/** ������ �̵��ÿ� ���������� ���󰡴� ���� ��Ÿ���� flag
	 */
	public transient boolean cascade;
	/** ������ �̵��ÿ� ���������� ���󰡴� ���� ��Ÿ���� flag
	 */
	public transient boolean followed;
	/** ���е��� traverse �ϱ����� flag
	 */
	public transient boolean mark;
	/** ������ �̵��ÿ� �̵��ϴ� �����ΰ��� ��Ÿ���� flag
	 */
	public transient boolean move;
	/** �� ������ ���� �ÿ� ���� ������ ����Ű�� ���۷���
	 */
	private transient LineNode _mother;
	/** �� �������� ���� ����� ���纻�� ���� ���۷���
	 */
	private transient LineNode _borned;
	/** �� ������ �ı��ϱ� ���� �ϳ��� ��带 �߰��ϴ� �Լ��̴�.
	 */
	private void insertANodeForTobedeletenode(LineNode me,LineNode tobedelete) {
		if (_afterList.inList(tobedelete)) {
			_afterList.insert(me,0);
		} else if (_beforeList.inList(tobedelete)) {
			_beforeList.insert(me,0);
		}
	}
	/** �������̴�.
	 */
	public LineNode(Line line,LineNode before,LineNode after) {
		_beforeList = new LineNodeList();
		_afterList = new LineNodeList();
		_line = line;
		_mother = null;
		_borned = null;
		resetFlags();
		if (before != null) {
			before.insert(this,Const.ATBEFORE);
			insertBefore(before);
		}
		if (after != null) {
			after.insert(this,Const.ATAFTER);
			insertAfter(after);
		}
	}
	/**
	 * �� �Լ��� �Ҹ����� ����̴�. Java���� �Ҹ��ڰ� �ʿ����� ������
	 * ������ �Ҹ����� ������μ� ����Ÿ ����� ���� reset��Ű�� ���� ������ ��찡 �ִ�.
	 */
	public void delete() {
		_beforeList.delete(); _beforeList = null;
		_afterList.delete(); _afterList = null;
		_line = null;
		_mother = null;
		_borned = null;
	}
	/** �� ���� ��带 _beforeList�� �����ϴ� �Լ��̴�.
	 */
	public void insertBefore(LineNode linenode) {
		_beforeList.insert(linenode,0);
	}
	/** �� ���� ��带 _afterList�� �����ϴ� �Լ��̴�.
	 */
	public void insertAfter(LineNode linenode) {
		_afterList.insert(linenode,0);
	}
	/** ���ڿ� ��õ� ���� ��带 _beforeList���� �����ϴ� �Լ��̴�.
	 */
	public void removeBefore(LineNode linenode) {
		_beforeList.remove(linenode,false);
	}
	/** ���ڿ� ��õ� ���� ��带 _afterList���� �����ϴ� �Լ��̴�.
	 */
	public void removeAfter(LineNode linenode) {
		_afterList.remove(linenode,false);
	}
	/** �� ���г���� ����Ʈ�� �� ���а� ����Ǵ� ���ο� ���� ��带 �߰��ϴ� �Լ��̴�.
	 */
	public void insert(LineNode linenode,int hotSpot) {
		Line line = linenode._line;
		int where = _line.whereToMeet(line);
		if (where == Const.ATBEFORE) {
			LineNode node = _beforeList.getFirst();
			while(node != null) {
				where = node._line.whereToMeet(line);
				if (where == Const.ATBEFORE) {
					node.insertBefore(linenode);
				} else if (where == Const.ATAFTER) {
					node.insertAfter(linenode);
				}
				if (hotSpot == Const.ATBEFORE) {
					linenode.insertBefore(node);
				} else /* Const.ATAFTER */ {
					linenode.insertAfter(node);
				}
				node = _beforeList.getNext();
			}
			insertBefore(linenode);
		} else if (where == Const.ATAFTER) {
			LineNode node = _afterList.getFirst();
			while(node != null) {
				where = node._line.whereToMeet(line);
				if (where == Const.ATBEFORE) {
					node.insertBefore(linenode);
				} else if (where == Const.ATAFTER) {
					node.insertAfter(linenode);
				}
				if (hotSpot == Const.ATBEFORE) {
					linenode.insertBefore(node);
				} else /* Const.ATAFTER */ {
					linenode.insertAfter(node);
				}
				node = _afterList.getNext();
			}
			insertAfter(linenode);
		} else {
			MySys.myprinterr("Fatal error : unexpected case in LineNode::insert().");
		}
	}
	/** _beforeList�� _afterList�� ������ ���� �ٲٴ� �Լ��̴�.
	 */
	public void swapBeforeAfter() {
		LineNodeList tmp = _beforeList;
		_beforeList = _afterList;
		_afterList = tmp;	
	}
	/** ����Ÿ ��� _beforeList �� ���� �б� access �Լ��̴�.
	 */
	public LineNodeList beforeList() {
		return _beforeList;
	}
	/** ����Ÿ ��� _afterList �� ���� �б� access �Լ��̴�.
	 */
	public LineNodeList afterList() {
		return _afterList;
	}
	/** ���ڿ� ��õ� ������ �� ���а� ����Ǿ� �ִ°��� �˻��ϴ� �Լ��̴�.
	 */
	public boolean neighbor(LineNode node) {
		if (_beforeList.inList(node)) return true;
		if (_afterList.inList(node)) return true;
		return false;
	}
	/** ����Ÿ ��� _line �� ���� �б� access �Լ��̴�.
	 */
	public Line line() {
		return _line;
	}
	/** ���е��� �պ��� �� �ִ°� �˻��ϴ� �Լ��̴�.
	 */
	public boolean checkToMerge(GraphicController controller,LineNodeList lines,boolean rubberbandingflag) {
		if (mark) return false;
		boolean changed = false;
		mark = true;
		if (_line.isObsolete()) return changed;
		if (_afterList.nOfList() == 1) {
			// possible to merge
			LineNode linenode = _afterList.getFirst();
			Line candidate = linenode._line;
			if (candidate.isObsolete() == false) {
				boolean tomerge = _line.checkToMerge(candidate);
				if (tomerge) {
					controller.clear(_line,false);
					controller.clear(candidate,false);
					_line.merge(candidate);
					merge(linenode,_afterList);
					if (rubberbandingflag) {
						controller.rubberbanding(_line);
					} else {
						controller.lowlight(_line);
					}
					candidate.delete();
					linenode.delete();
					lines.remove(linenode,true);
					changed = true;
					mark = false;
				}
			}
			if (changed) return true;
		}
		if (_beforeList.nOfList() == 1) {
			// possible to merge
			LineNode linenode = _beforeList.getFirst();
			Line candidate = linenode._line;
			if (candidate.isObsolete()) return changed;
			boolean tomerge = _line.checkToMerge(candidate);
			if (tomerge) {
				controller.clear(_line,false);
				controller.clear(candidate,false);
				_line.merge(candidate);
				merge(linenode,_beforeList);
				if (rubberbandingflag) {
					controller.rubberbanding(_line);
				} else {
					controller.lowlight(_line);
				}
				candidate.delete();
				linenode.delete();
				lines.remove(linenode,true);
				changed = true;
			}
		}
		return changed;
	}
	/** �� ������ ���ʿ��Ѱ� �˻��ϴ� �Լ��̴�.
	 */
	public boolean checkIfObsolete() {
		if (mark) return false;
		mark = true;
		if (_line.isObsolete()) {
			LineNode ptr = _afterList.getFirst();
			while (ptr != null) {
				LineNode tmp = _beforeList.getFirst();
				while (tmp != null) {
					if (tmp._beforeList.inList(this) &&
						(ptr != tmp)) {
						tmp._beforeList.insert(ptr,0);
					} else if (tmp._afterList.inList(this) &&
							   (ptr != tmp)) {
						tmp._afterList.insert(ptr,0);
					}
					tmp = _beforeList.getNext();
				}
				ptr = _afterList.getNext();
			}
			ptr = _beforeList.getFirst();
			while (ptr != null) {
				LineNode tmp = _afterList.getFirst();
				while (tmp != null) {
					if (tmp._beforeList.inList(this) &&
						(ptr != tmp)) {
						tmp._beforeList.insert(ptr,0);
					} else if (tmp._afterList.inList(this) &&
							   (ptr != tmp)) {
						tmp._afterList.insert(ptr,0);
					}
					tmp = _afterList.getNext();
				}
				ptr = _beforeList.getNext();
			}
			_line.delete();
			return true;
		} else {
			return false;
		}
	}
	/** �� ������ ���ڿ� ��õ� ���а� �պ��ϴ� �Լ��̴�.
	 */
	public void merge(LineNode node,LineNodeList wheretoput) {
		if (node._afterList.inList(this)) {
			LineNode ptr = node._beforeList.getFirst();
			while(ptr != null) {
				wheretoput.insert(ptr,0);
				ptr.insertANodeForTobedeletenode(this,node);
				ptr = node._beforeList.getNext();
			}
		} else /* in _beforelist */ {
			LineNode ptr = node._afterList.getFirst();
			while(ptr != null) {
				wheretoput.insert(ptr,0);
				ptr.insertANodeForTobedeletenode(this,node);
				ptr = node._afterList.getNext();
			}
		}
	}
	/** flag �������� �ʱ�ȭ�ϴ� �Լ��̴�.
	 */
	public void resetFlags() {
		mark = false;
		move = false;
		followed = false;
		cascade = false;
	}
	/** �̵����� ���� ���п� ���� �� �ֵ��� ��ġ�ϴ� �Լ��̴�.
	 */
	public void checkNearForFollow(GraphicController controller,LineNodeList lines,int type,int x,int y,int where) {
		if (where == Const.ATAFTER) {
			LineNode node = _afterList.getFirst();
			while (node != null) {
				node.checkNearForFollow(controller,lines,type,x,y);
				node = _afterList.getNext();
			}
		} else if (where == Const.ATBEFORE) {
			LineNode node = _beforeList.getFirst();
			while (node != null) {
				node.checkNearForFollow(controller,lines,type,x,y);
				node = _beforeList.getNext();
			}
		}
	}
	/** �̵����� ���� ���п� ���� �� �ֵ��� ��ġ�ϴ� �Լ��̴�.
	 */
	public void checkNearForFollow(GraphicController controller,LineNodeList lines,int type,int x,int y) {
		if (move) return;
		boolean swaped = _line.checkNear(x,y);
		if (swaped) {
			swapBeforeAfter();
		}
		if (type == Const.ORDINARY) return;
		makeCascadeFollow(controller,lines,type);
	}
	/** ���� ������ ũ������ �߿� �� ������ ���󰡵��� ��ǥ���� ������ �ִ� �Լ��̴�.
	 */
	public void followResizing() {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_line.coords(p1,p2);
		LineNode node = _beforeList.getFirst();
		while (node != null) {
			node.followMe(p1.x,p1.y);
			node = _beforeList.getNext();
		}
		node = _afterList.getFirst();
		while (node != null) {
			node.followMe(p2.x,p2.y);
			node = _afterList.getNext();
		}
	}
	/** ���� ������ ũ������ �߿� �� ������ ���󰡵��� ���ִ� �Լ��̴�.
	 */
	public void followResizing(Graphics g) {
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_line.coords(p1,p2);
		LineNode node = _beforeList.getFirst();
		while (node != null) {
			node.followMe(g,p1.x,p1.y);
			node = _beforeList.getNext();
		}
		node = _afterList.getFirst();
		while (node != null) {
			node.followMe(g,p2.x,p2.y);
			node = _afterList.getNext();
		}
	}
	/** �� ������ ���� ������ ���󰡰� ��ǥ���� �������ִ� �Լ��̴�.
	 */
	public void followMe(int newx,int newy) {
		if (move || followed) {
			return;
		}
		followed = true;
		_line.following(newx,newy);
		if (_line.type() == Const.ORDINARY) return;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_line.coords(p1,p2);
		LineNode node = _beforeList.getFirst();
		while (node != null) {
			if (node.move == false && node.followed == false) {
				node.followed = true;
				node._line.setXY2(p1.x,p1.y);
			}
			node = _beforeList.getNext();
		}
	}
	/** �� ������ ���� ������ ���󰡰� �׷��ִ� �Լ��̴�.
	 */
	public void followMe(Graphics g,int newx,int newy) {
		if (move || followed) {
			return;
		}
		followed = true;
		_line.following(g,newx,newy);
		if (_line.type() == Const.ORDINARY) return;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_line.coords(p1,p2);
		LineNode node = _beforeList.getFirst();
		while (node != null) {
			if (node.move == false && node.followed == false) {
				node.followed = true;
				node._line.drawing(g,p1.x,p1.y,true);
			}
			node = _beforeList.getNext();
		}
	}
	/** �� ������ ���� ���а� ��ġ�°��� �˻��ϴ� �Լ��̴�.
	 */
	public void checkIfZammed() {
		boolean zammed = false;
		LineNode node = _beforeList.getFirst();
		while (node != null) {
			if (node.move) {
				zammed = true;
				break;
			}
			node = _beforeList.getNext();
		}
		if (zammed == false) return;
		zammed = false;
		node = _afterList.getFirst();
		while (node != null) {
			if (node.move) {
				zammed = true;
				break;
			}
			node = _afterList.getNext();
		}
		if (zammed == false) return;
		move = true;
	}
	/** �� ������ �̵� ���� ���п� ���������� ���󰡰� ������ִ� �Լ��̴�.
	 */
	public void makeCascadeFollow(GraphicController controller,LineNodeList lines,int type) {
		if (move || cascade) return;
		if (_beforeList.empty()) return;
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		_line.coords(p1,p2);
		Line newline = new Line(controller,p1.x,p1.y,null,type);	
		newline.setOrient(Line.invertOrient(_line.orient()));
		LineNode newnode = new LineNode(newline,null,null);
		newnode.cascade = true;
		newnode._beforeList.copy(_beforeList);
		newnode._afterList.insert(this,0);
		LineNode node = _beforeList.getFirst();
		while(node != null) {
			node.afterList().replacePtr(this,newnode);
			node.beforeList().replacePtr(this,newnode);
			node = _beforeList.getNext();
		}
		_beforeList.clear();
		_beforeList.insert(newnode,0);
		lines.insert(newnode,0);
	}
}
