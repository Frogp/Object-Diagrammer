package figure;

import java.awt.*;
import java.lang.*;
/** �� Ŭ������ ������ ���� ������ ������ ���� ������ �����ϴ� Ŭ�����̴�.
 */
public final 
	class CRgn extends Polygon {
	/** �⺻ �������̴�.
	 */
	public CRgn() {
		super();
		npoints = 4;
		xpoints = new int[npoints];
		ypoints = new int[npoints];
		for(int i = 0; i < npoints; i++) {
			xpoints[i] = 0;
			ypoints[i] = 0;
		}
		bounds = null;
		bounds = getBounds();
	}
	/** �ٰ��� ������ ���� �������̴�.
	 */
	public CRgn(int xps[],int yps[],int n) {
		super();
		npoints = n;
		xpoints = new int[npoints];
		ypoints = new int[npoints];
		for(int i = 0; i < npoints; i++) {
			xpoints[i] = xps[i];
			ypoints[i] = yps[i];
		}
		bounds = null;
		bounds = getBounds();
	}
	/** �� ������ �����ϴ� �Լ��̴�.
	 */
	public static CRgn myCreateRgn() {
		return new CRgn();
	}
	/** �簢�� ������ �����ϴ� �Լ��̴�.
	 */
	public static CRgn makeRectRegion(int x,int y,int w,int h)
	{
		int xps[] = new int[4];
		int yps[] = new int[4];
		xps[0] = x;
		yps[0] = y;
		xps[1] = x + w;
		yps[1] = y;
		xps[2] = x + w;
		yps[2] = y + h;
		xps[3] = x;
		yps[3] = y + h;
		return new CRgn(xps,yps,4);
	}
	/** �ٰ��� ������ �����ϴ� �Լ��̴�.
	 */
	public static CRgn myPolygonRgn(CPoint data[],int n) {
		int xps[] = new int[n];
		int yps[] = new int[n];
		for (int i = 0; i < n; i++) {
			xps[i] = data[i].x;
			yps[i] = data[i].y;
		}
		return new CRgn(xps,yps,n);
	}
	/** ���� ���� �����ϴ� �Լ��̴�.
	 */
	public void mySetRgn(CRgn from) {
		npoints = from.npoints;
		xpoints = new int[npoints];
		ypoints = new int[npoints];
		for (int i = 0; i < npoints; i++) {
			xpoints[i] = from.xpoints[i];
			ypoints[i] = from.ypoints[i];
		}
		bounds = null;
		bounds = getBounds();
	}
	/** ���ڿ� ��õ� ������ �� �������� �˻��ϴ� �Լ��̴�.
	 */
	public static boolean myEmptyRgn(CRgn rgn) {
		return rgn.bounds.isEmpty();
	}
	/** ���ڿ� ��õ� (x,y) ��ǥ�� �־��� ������ ���ϴ� ���� �˻��ϴ� �Լ��̴�.
	 */
	public static boolean myPtInRgn(CRgn rgn,int x,int y) {
		return rgn.bounds.contains(x,y);
	}
	/** �ΰ��� ������ �������� �˻��ϴ� �Լ��̴�.
	 */
	public static boolean myEqualRgn(CRgn rgn1,CRgn rgn2) {
		return rgn1.bounds.equals(rgn2.bounds);
	}
	/** �ΰ��� �������� ���� �����տ� �ش��ϴ� ������ ���ϴ� �Լ��̴�.
	 */
	public static void myIntersectRgn(CRgn rgn1,CRgn rgn2,CRgn rgn3) {
		rgn3.bounds = null;
		rgn3.bounds = rgn1.bounds.intersection(rgn2.bounds);
	}
	/** �ΰ��� �������� ���� �����տ� �ش��ϴ� ������ ���ϴ� �Լ��̴�.
	 */
	public static void myUnionRgn(CRgn rgn1,CRgn rgn2,CRgn rgn3) {
		if (rgn1 != rgn3) {
			MySys.myprinterr("Something wrong in CRgn::myUnionRgn()");
			return;
		}
		rgn1.bounds.add(rgn2.bounds);
	}
	/** �׷��Ƚ� ��ü�� Ŭ�� ������ �����ϴ� �Լ��̴�.
	 */
	public static void mySetClipRgn(Graphics g,CRgn rgn) {
		g.setClip(rgn.bounds);
	}
	/** �� ������ �����տ� �ش��ϴ� ������ ���ϴ� �Լ��̴�.
	 */
	public static void mySubtractRgn(CRgn rgn1,CRgn rgn2,CRgn rgn3) {
		return;
	}
	/** �ΰ��� ������ ���� �����ϴ� ���� �˻��ϴ� �Լ��̴�.
	 */
	public static boolean doesIntersect(CRgn rgn1,CRgn rgn2) {
		CRgn rgn3 = myCreateRgn();
		myIntersectRgn(rgn1,rgn2,rgn3);
		if (myEmptyRgn(rgn3) == true) {
			myDestroyRgn(rgn3);
			return false;
		} else {
			myDestroyRgn(rgn3);
			return true;
		}
	}
	/** ���� ��ü�� �ı��ϴ� �Լ��̴�. ���� ��ü�� ���� �Ҹ����� ������ �Ѵ�.
	 */
	public static void myDestroyRgn(CRgn rgn) {
		rgn.npoints = 0;
		rgn.xpoints = null;
		rgn.ypoints = null;
		rgn.bounds = null;
	}
	/** ���ڿ� ��õ� �� ��ǥ�� Ư�� �����ȿ� ���ԵǴ°��� �˻��ϴ� �Լ��̴�.
	 */
	public static boolean checkInRegion(CRgn someregion,int x1,int y1,int x2,int y2)
	{
		if (someregion == null) return(false);
		if (someregion.bounds.isEmpty()) return(false);
		int ax = x1;  int bx = x2;
		int ay = y1;  int by = y2;
		int w = Math.abs(ax - bx);
		int h = Math.abs(ay - by);
		if (w == 0) w = 1;
		if (h == 0) h = 1;
		if (ax > bx) {
			int tmpx = ax; ax = bx; bx = tmpx;
		}
		if (ay > by) {
			int tmpy = ay; ay = by; by = tmpy;
		}
		Rectangle tmp = new Rectangle(ax,ay,w,h);
		if (tmp.intersects(someregion.bounds)) {
			return(true);
		} else {
			return(false);
		}
	}
	/** �ΰ��� ��ǥ ���� ������� ��ġ�ϵ��� ������ �ִ� �Լ��̴�.
	 */
	public static void checkPoints(Point p1,Point p2)
	{
		if (p1.x>p2.x) {
			int tmpx = p1.x; p1.x = p2.x; p2.x = tmpx;
		}
		if (p1.y>p2.y) {
			int tmpy = p1.y; p1.y = p2.y; p2.y = tmpy;
		}
	}
}