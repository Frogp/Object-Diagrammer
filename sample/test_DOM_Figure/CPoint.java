package figure;

import java.io.*;
/** �� Ŭ������ �ϳ��� ��ǥ �� (x,y) ��ü�� ǥ���ϱ� ���� Ŭ�����̴�.
 */
public final class CPoint extends Object implements Serializable {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 1264339498013460085L;
	/** ��ǥ�� x ��
	 */
	public int x;
	/** ��ǥ�� y ��
	 */
	public int y;
	/** �������̴�.
	 */
	public CPoint() {
		super();
		x = 0;
		y = 0;
	}
	/** �������̴�.
	 */
	public CPoint(int x1,int y1) {
		super();
		x = x1;
		y = y1;
	}
}