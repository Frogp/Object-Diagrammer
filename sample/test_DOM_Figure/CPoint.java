package figure;

import java.io.*;
/** 이 클래스는 하나의 좌표 값 (x,y) 객체를 표현하기 위한 클래스이다.
 */
public final class CPoint extends Object implements Serializable {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 1264339498013460085L;
	/** 좌표의 x 값
	 */
	public int x;
	/** 좌표의 y 값
	 */
	public int y;
	/** 생성자이다.
	 */
	public CPoint() {
		super();
		x = 0;
		y = 0;
	}
	/** 생성자이다.
	 */
	public CPoint(int x1,int y1) {
		super();
		x = x1;
		y = y1;
	}
}