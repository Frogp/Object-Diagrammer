package figure;

import java.awt.*;
import java.awt.Point;
import java.lang.*;
/** 이 클래스는 기본 표기법 중에서 사각형을 표현하기 위한 것이다.
 */
public class Box extends TwoPointFigure {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 1479915935050303368L;
	/** 이 사각형 객체의 영역을 만드는 함수이다.
	 */
	protected void makeRegion(int x,int y,int w,int h) {
		_points[0].x = x - 1;
		_points[0].y = y - 1;
		_points[1].x = x + w + 1;
		_points[1].y = y - 1;
		_points[2].x = x + w + 1;
		_points[2].y = y + h + 1;
		_points[3].x = x - 1;
		_points[3].y = y + h + 1;
		if (_maxRegion != null) CRgn.myDestroyRgn(_maxRegion);
		_maxRegion = CRgn.myPolygonRgn(_points,4);
		_points[0].x = x;
		_points[0].y = y;
		_points[1].x = x + w;
		_points[1].y = y;
		_points[2].x = x + w;
		_points[2].y = y + h;
		_points[3].x = x;
		_points[3].y = y + h;
		if (_region != null) CRgn.myDestroyRgn(_region);
		_region = CRgn.myPolygonRgn(_points,4);
	}
	/** destructor의 역할을 하는 함수이다. dangling reference의 가능성을 줄이기 위해
	 * 사용된다.
	 */
	public void delete() {
		super.delete();
	}
	/** 생성자이다.
	 */
	public Box(GraphicController controller,int x,int y,Popup popup) {
		super(controller,x,y);
		_popup = popup;
	}
	/** 생성자이다.
	 */
	public Box(GraphicController controller,int x1,int y1,int x2,int y2,Popup popup) {
		super(controller,x1,y1,x2,y2);
		_popup = popup;
	}
	/** 이 객체를 화면 상에서 지우는 함수이다. expose 변수가 true이면 그림을 지운뒤
	 * paint 이벤트를 발생시켜서 배경 부분이 다시 그려지도록 한다.
	 */
	public void clear(Graphics g,boolean expose) {
		int w = _x2 - _x1;
		int h = _y2 - _y1;
		this.draw(g,Const.DRAWING,g);
		if (w == 0 || h == 0) return;
		g.fillRect(_x1,_y1,w+1,h+1);
		if (expose == true) {
			_controller.setRepaint();
		}
	}
	/** 이 객체가 현재 포커스로 설정되었을 때 모서리에 사각점을 그리는 함수이다.
	 */
	public void drawDots(Graphics g) {
		_doted = true;
		_dots[0].x = _x1;
		_dots[0].y = _y1;
		_dots[1].x = _x2;
		_dots[1].y = _y1;
		_dots[2].x = _x2;
		_dots[2].y = _y2;
		_dots[3].x = _x1;
		_dots[3].y = _y2;
		MySys.myDrawDots(g,_dots,4);
	}
	/** 현재 마우스가 눌러졌을 때 이 객체의 크기를 조절하기 시작할 것인가를 결정하는 함수이다.
	 */
	public boolean wantResize(CPoint point) {
		Rectangle tocheck = new Rectangle(point.x-4,point.y-4,8,8);
		return tocheck.contains(_x2,_y2);
	}
	/** 그래픽스 객체를 이용하여 이 객체를 그리는 함수이다. 이 함수는 프린트 시에도 호출된다.
	 */
	public void draw(Graphics g,int mode,Graphics specialgc) {
		if (!_inCanvas) return;
		int x,y;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		if (_x1 > _x2 ) x = _x2;
		else x = _x1;
		if (_y1 > _y2 ) y = _y2;
		else y = _y1;
		if (mode == Const.LOWLIGHTING && _doted == true) {
			_doted = false;
			_dots[0].x = _x1;
			_dots[0].y = _y1;
			_dots[1].x = _x2;
			_dots[1].y = _y1;
			_dots[2].x = _x2;
			_dots[2].y = _y2;
			_dots[3].x = _x1;
			_dots[3].y = _y2;
			MySys.myDrawDots(specialgc,_dots,4);
		}
		MySys.myDrawRectangle(g,x,y,width,height);
	}
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 */
	public void drawing(Graphics g,boolean flag) {
		drawing(g,_x2,_y2,flag);
	}
	/** 이 객체 그리기를 rubberbanding을 이용하여 수행하는 함수이다.
	 * 현재 마우스의 새로운 위치 값을 이용하여 rubberbanding 한다.
	 */
	public void drawing(Graphics g,int newx,int newy,boolean flag) {
		int x,y;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		if (_x1 > _x2 ) x = _x2;
		else x = _x1;
		if (_y1 > _y2 ) y = _y2;
		else y = _y1;
		MySys.myDrawRectangle(g,x,y,width,height);
		if (_x1 > newx ) x = newx;
		else x = _x1;
		if (_y1 > newy ) y = newy;
		else y = _y1;
		width = Math.abs(newx - _x1);
		height = Math.abs(newy - _y1);
		MySys.myDrawRectangle(g,x,y,width,height);
		_x2 = newx;
		_y2 = newy;
	}
	/** 이 객체를 이동시키는 함수이다. 이동시 필요한 부분은 rubberbanding을 하며 일부분의
	 * 부속 객체에 대해서는 좌표이동만 한다. 
	 */
	public void move(Graphics g,int dx,int dy) {
		int newx = _x2 + dx;
		int newy = _y2 + dy;
		int x,y;
		int width = Math.abs(_x2 - _x1);
		int height = Math.abs(_y2 - _y1);
		if (_x1 > _x2 ) x = _x2;
		else x = _x1;
		if (_y1 > _y2 ) y = _y2;
		else y = _y1;
		MySys.myDrawRectangle(g,x,y,width,height);
		_x1 = _x1 + dx;
		_y1 = _y1 + dy;
		if (_x1 > newx ) x = newx;
		else x = _x1;
		if (_y1 > newy ) y = newy;
		else y = _y1;
		width = Math.abs(newx - _x1);
		height = Math.abs(newy - _y1);
		MySys.myDrawRectangle(g,x,y,width,height);
		_x2 = newx;
		_y2 = newy;
	}
	/** 객체의 identity를 묻는 함수이다.
	 */
	public FigureID whoAreYou() {
		return Const.IAMBOX; 
	}
	/** 현 객체의 사본을 만들어주는 함수로서 객체의 복사 시에 호출된다.
	 * parameter 값이 null인 경우는 맨 처음 호출되는 경우이므로 생성자를 통해 
	 * 객체의 실제 사본을 만들어야하고, 그렇지 않은 경우는 현 객체에 속하는
	 * 데이타 멤버의 값을 복사해주면 된다. 
	 */
	public Figure born(Figure ptr) {
		Box copied;
		if (ptr == null) {
			copied = new Box(_controller,0,0,null);
		} else {
			copied = (Box)ptr;
		}
		return (super.born((Figure)copied));
	}
	/** 이 표기법 객체의 영역을 만드는 함수이다.
	 */
	public void makeRegion() {
		super.checkPoints();
		makeRegion(_x1,_y1,_x2-_x1,_y2-_y1);
	}
	/** 이 함수의 첫번째 인자인 figures 리스트에 들어있는 그림 중에서 이 사각형 객체
	 * 에 포함되는 그림들을 찾아내어 두번째 인자인 containee 리스트에 모으는 작업을
	 * 하는 함수이다. 아울러 선택된 그림들을 highlight 한다.
	 */
	public void highlightEntries(FigureList figures,FigureList containee) {
		makeRegion(_x1,_y1,_x2-_x1,_y2-_y1);
		FigureList save = new FigureList();
		save.copy(figures);
		Figure tmp = figures.getFirst();
		while(tmp != null) {
			CRgn region = CRgn.myCreateRgn();
			CRgn.myUnionRgn(_region,tmp.region(),region);
			if (CRgn.myEqualRgn(_region,region)) {
				Figure more = save.getFirst();
				while(more != null) {
					more.visited = false;
					more = save.getNext();
				}
				boolean allIncluded = tmp.checkEntries(_region);
				if (allIncluded) {
					_controller.highlight(tmp);
					if (containee != null) {
						containee.insert(tmp,0);
					}
				} else {	
					_controller.lowlight(tmp);
				}
			} else {
				_controller.lowlight(tmp);
			}
			CRgn.myDestroyRgn(region);
			tmp = figures.getNext();
		}
		save.delete();
	}
	/** 입력 인자인 라인의 모서리를 확장시켜서 이 사각형 객체의 중앙까지 길어지게하는
	 * 함수이다.
	 */
	public void expandLineToCenter(Line line,boolean startpoint) {
		int bx1 = _x1;
		int by1 = _y1;
		int bx2 = _x2;
		int by2 = _y2;
		int cx = (bx1+bx2)/2;
		int cy = (by1+by2)/2;
		int x1 = line._x1;
		int y1 = line._y1;
		int x2 = line._x2;
		int y2 = line._y2;
		int orient = line.orient();
		if (x1 == x2 && y1 == y2) return;
		if (contains(x1,y1) && contains(x2,y2)) return;
		if (startpoint) {
			if (orient == Const.NORTH) {
				line._y1 = cy;
			} else { // EAST
				line._x1 = cx;
			}
		} else { // end point
			if (orient == Const.NORTH) {
				line._y2 = cy;
			} else { // EAST
				line._x2 = cx;
			}
		}
	}
	/** 입력 인자인 라인을 사각형의 변경까지 위치하도록 길이를 조절하는 함수이다.
	 */
	public boolean adjustLine(Line line,boolean startpoint) {
		int bx1 = _x1;
		int by1 = _y1;
		int bx2 = _x2;
		int by2 = _y2;
		int x1 = line._x1;
		int y1 = line._y1;
		int x2 = line._x2;
		int y2 = line._y2;
		int orient = line.orient();
		if (x1 == x2 && y1 == y2) return true;
		if (contains(x1,y1) && contains(x2,y2)) return true;
		if (startpoint) {
			if (orient == Const.NORTH) {
				if (y1 >= by1 && y2 <= by1) {
					line._y1 = by1;
				} else if (y1 <= by2 && y2 >= by2) {
					line._y1 = by2;
				} else {
					return true;
				}
			} else { // EAST
				if (x1 >= bx1 && x2 <= bx1) {
					line._x1 = bx1;
				} else if (x1 <= bx2 && x2 >= bx2) {
					line._x1 = bx2;
				} else {
					return true;
				}
			}
		} else { // end point
			if (orient == Const.NORTH) {
				if (y1 <= by1 && y2 >= by1) {
					line._y2 = by1;
				} else if (y1 >= by2 && y2 <= by2) {
					line._y2 = by2;
				} else {
					return true;
				}
			} else { // EAST
				if (x1 <= bx1 && x2 >= bx1) {
					line._x2 = bx1;
				} else if (x1 >= bx2 && x2 <= bx2) {
					line._x2 = bx2;
				} else {
					return true;
				}
			}
		}
		x1 = line._x1;
		y1 = line._y1;
		x2 = line._x2;
		y2 = line._y2;
		if (x1 == x2 && y1 == y2) return true;
		return false;
	}
	/** 이 사각형 객체의 크기를 수평으로 늘리는 함수이다. 이 함수는 qualification의 편집 시에
	 * 호출된다.
	 */
	public void expandHorizontal(int orient1,int orient2,int delta,int width,Line dynamicLine) {
		int x1 = _x1;
		int y1 = _y1;
		int x2 = _x2;
		int y2 = _y2;
		int offset = delta;
		if (offset < 0) {
			_controller.clear(this,true);
		}
		int offset1 = 0;
		int offset2 = 0;
		if (orient1 != 0 && orient2 != 0) {
			if (delta/2*2 != delta) {
				if (delta < 0) width++;
				if (width/2*2 != width) {
					offset1 = offset / 2;
					offset2 = offset / 2 + MySys.sign(offset)*1;
				} else {
					offset1 = offset / 2 + MySys.sign(offset)*1;
					offset2 = offset / 2;
				}
			} else {
				offset1 = offset / 2;
				offset2 = offset / 2;
			}
		} else if (orient1 != 0) {
			offset1 = offset;
			offset2 = 0;
		} else if (orient2 != 0) {
			offset1 = 0;
			offset2 = offset;
		}	
		if (orient1 == Const.WEST) {
			_x1 = _x1 - offset1;
		}
		if (orient2 == Const.EAST) {
			_x2 = _x2 + offset2;
		}	
		Graphics g = _controller.getgraphics();
		if (offset < 0) {
			if (dynamicLine != null) {
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				dynamicLine.coords(p1,p2); 
				if (_x1 == p1.x) {
					MySys.myDrawLine(g,_x2,p1.y,p2.x,p2.y);
				} else if (_x1 == p2.x) {
					MySys.myDrawLine(g,_x2,p2.y,p1.x,p1.y);
				} else if (_x2 == p1.x) {
					MySys.myDrawLine(g,_x1,p1.y,p2.x,p2.y);
				} else if (_x2 == p2.x) {
					MySys.myDrawLine(g,_x1,p2.y,p1.x,p1.y);
				} 
			}
		} else {
			makeRegion();
			_controller.clear(this,false);
		}
		g.dispose();
		_controller.draw(this);
	}
	/** 좌표 x,y 가 이 사각형 내부에 포함되는가를 결정하는 함수이다.
	 */
	public boolean contains(int x,int y) {
		Point p1 = new Point(_x1,_y1);
		Point p2 = new Point(_x2,_y2);
		CRgn.checkPoints(p1,p2);
		if (x >= p1.x && x <= p2.x && y >= p1.y && y <= p2.y) return true;
		return false;
	}
	/** 이 사각형 객체의 크기를 수직으로 늘리는 함수이다. 이 함수는 qualification의 편집 시에
	 * 호출된다.
	 */
	public void expandVertical(int orient1,int orient2,int delta,int height,Line dynamicLine) {
		int x1 = _x1;
		int y1 = _y1;
		int x2 = _x2;
		int y2 = _y2;
		int offset = delta;
		if (offset < 0) {
			_controller.clear(this,true);
		}
		int offset1 = 0;
		int offset2 = 0;
		if (orient1 != 0 && orient2 != 0) {
			if (delta/2*2 != delta) {
				if (delta < 0) height++;
				if (height/2*2 != height) {
					offset1 = offset / 2;
					offset2 = offset / 2 + MySys.sign(offset)*1;
				} else {
					offset1 = offset / 2 + MySys.sign(offset)*1;
					offset2 = offset / 2;
				}
			} else {
				offset1 = offset / 2;
				offset2 = offset / 2;
			}
		} else if (orient1 != 0) {
			offset1 = offset;
			offset2 = 0;
		} else if (orient2 != 0) {
			offset1 = 0;
			offset2 = offset;
		}	
		if (orient1 == Const.NORTH) {
			_y1 = _y1 - offset1;
		}
		if (orient2 == Const.SOUTH) {
			_y2 = _y2 + offset2;
		}	
		Graphics g = _controller.getgraphics();
		if (offset < 0) {
			if (dynamicLine != null) {
				Point p1 = new Point(0,0);
				Point p2 = new Point(0,0);
				dynamicLine.coords(p1,p2); 
				if (_y1 == p1.y) {
					MySys.myDrawLine(g,p1.x,_y2,p2.x,p2.y);
				} else if (_y1 == p2.y) {
					MySys.myDrawLine(g,p2.x,_y2,p1.x,p1.y);
				} else if (_y2 == p1.y) {
					MySys.myDrawLine(g,p1.x,_y1,p2.x,p2.y);
				} else if (_y2 == p2.y) {
					MySys.myDrawLine(g,p2.x,_y1,p1.x,p1.y);
				} 
			}
		} else {
			makeRegion();
			_controller.clear(this,false);
		}
		g.dispose();
		_controller.draw(this);
	}
}