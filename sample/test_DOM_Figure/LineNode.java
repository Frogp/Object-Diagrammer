package figure;

import java.awt.*;
import java.awt.Point;
import java.io.*;
/** 이 클래스는 하나의 선분과 연결되는 다른 선분들에 대한 정보를 관리하며 선분의 논리적 정보
 * 를 처리하는 함수이다.
 */
public final 
	class LineNode extends Object implements Serializable {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -2120080165739596885L;
	/** 이 선분의 (_x1,_y1) 좌표쪽에 연결되는 선분들의 리스트
	 */
	private LineNodeList _beforeList;
	/** 이 선분의 (_x2,_y2) 좌표쪽에 연결되는 선분들의 리스트
	 */
	private LineNodeList _afterList;
	/** 선분 그림 객체
	 */
	private Line _line;
	/** 선분의 이동시에 간접적으로 따라가는 가를 나타내는 flag
	 */
	public transient boolean cascade;
	/** 선분의 이동시에 직접적으로 따라가는 가를 나타내는 flag
	 */
	public transient boolean followed;
	/** 선분들을 traverse 하기위한 flag
	 */
	public transient boolean mark;
	/** 선분의 이동시에 이동하는 선분인가를 나타내는 flag
	 */
	public transient boolean move;
	/** 이 선분의 복사 시에 원본 선분을 가리키는 레퍼런스
	 */
	private transient LineNode _mother;
	/** 이 선분으로 부터 복사된 본사본에 대한 레퍼런스
	 */
	private transient LineNode _borned;
	/** 한 선분을 파괴하기 위해 하나의 노드를 추가하는 함수이다.
	 */
	private void insertANodeForTobedeletenode(LineNode me,LineNode tobedelete) {
		if (_afterList.inList(tobedelete)) {
			_afterList.insert(me,0);
		} else if (_beforeList.inList(tobedelete)) {
			_beforeList.insert(me,0);
		}
	}
	/** 생성자이다.
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
	 * 이 함수는 소멸자의 대용이다. Java에서 소멸자가 필요없기는 하지만
	 * 때때로 소멸자의 대용으로서 데이타 멤버의 값을 reset시키는 것이 안전한 경우가 있다.
	 */
	public void delete() {
		_beforeList.delete(); _beforeList = null;
		_afterList.delete(); _afterList = null;
		_line = null;
		_mother = null;
		_borned = null;
	}
	/** 새 선분 노드를 _beforeList에 삽입하는 함수이다.
	 */
	public void insertBefore(LineNode linenode) {
		_beforeList.insert(linenode,0);
	}
	/** 새 선분 노드를 _afterList에 삽입하는 함수이다.
	 */
	public void insertAfter(LineNode linenode) {
		_afterList.insert(linenode,0);
	}
	/** 인자에 명시된 선분 노드를 _beforeList에서 삭제하는 함수이다.
	 */
	public void removeBefore(LineNode linenode) {
		_beforeList.remove(linenode,false);
	}
	/** 인자에 명시된 선분 노드를 _afterList에서 삭제하는 함수이다.
	 */
	public void removeAfter(LineNode linenode) {
		_afterList.remove(linenode,false);
	}
	/** 이 선분노드의 리스트에 이 선분과 연결되는 새로운 선분 노드를 추가하는 함수이다.
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
	/** _beforeList와 _afterList의 내용을 서로 바꾸는 함수이다.
	 */
	public void swapBeforeAfter() {
		LineNodeList tmp = _beforeList;
		_beforeList = _afterList;
		_afterList = tmp;	
	}
	/** 데이타 멤버 _beforeList 에 대한 읽기 access 함수이다.
	 */
	public LineNodeList beforeList() {
		return _beforeList;
	}
	/** 데이타 멤버 _afterList 에 대한 읽기 access 함수이다.
	 */
	public LineNodeList afterList() {
		return _afterList;
	}
	/** 인자에 명시된 선분이 이 선분관 연결되어 있는가를 검사하는 함수이다.
	 */
	public boolean neighbor(LineNode node) {
		if (_beforeList.inList(node)) return true;
		if (_afterList.inList(node)) return true;
		return false;
	}
	/** 데이타 멤버 _line 에 대한 읽기 access 함수이다.
	 */
	public Line line() {
		return _line;
	}
	/** 선분들을 합병할 수 있는가 검사하는 함수이다.
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
	/** 이 선분이 불필요한가 검사하는 함수이다.
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
	/** 이 선분을 인자에 명시된 선분과 합병하는 함수이다.
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
	/** flag 변수들을 초기화하는 함수이다.
	 */
	public void resetFlags() {
		mark = false;
		move = false;
		followed = false;
		cascade = false;
	}
	/** 이동중인 인접 선분에 따라갈 수 있도록 조치하는 함수이다.
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
	/** 이동중인 인접 선분에 따라갈 수 있도록 조치하는 함수이다.
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
	/** 인접 선분의 크기조절 중에 그 선분을 따라가도록 좌표값을 변경해 주는 함수이다.
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
	/** 인접 선분의 크기조절 중에 그 선분을 따라가도록 해주는 함수이다.
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
	/** 이 선분이 초점 선분을 따라가게 좌표값을 변경해주는 함수이다.
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
	/** 이 선분이 초점 선분을 따라가게 그려주는 함수이다.
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
	/** 이 선분이 인접 선분과 겹치는가를 검사하는 함수이다.
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
	/** 이 선분을 이동 중인 선분에 간접적으로 따라가게 만들어주는 함수이다.
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
