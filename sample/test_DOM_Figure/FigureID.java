package figure;

import java.io.*;
/** 이 클래스는 그림 객체의 식별자를 제공하기 위한 것으로 64 비트 데이타
 * 처럼 이용된다.
 */
public final 
	class FigureID extends Object implements Serializable {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 721228677340452646L;
	/** 세트하고자 하는 비트의 위치, 0<=_n<=63 
	 */
	protected int _n;
	/** 64 비트 데이타
	 */
	protected byte _bytes[];
	/** 생성자이다.
	 */
	public FigureID(int n) {
		super();
		_n = n;
		_bytes = new byte[8];
		for(int i = 0; i < 8; i++) {
			_bytes[i] = 0;
		}
		if (n == -1) return;
		int byteIndex = 0;
		int bitIndex = 0;
		while(n >= 8) {
			byteIndex++;
			n = n - 8;
		}
		bitIndex = n;
		byte one = 1;
		_bytes[byteIndex] = (byte)(_bytes[byteIndex] | (one<<bitIndex));	
	}
	/** 이 객체의 값이 인자와 같은가를 비교하는 함수이다. 
	 */
	public boolean isEqual(FigureID from) {
		for(int i = 0; i < 8; i++) {
			if (_bytes[i] != from._bytes[i]) return false;
		}
		return true;
	}
	/** 이 객체의 값이 인자와 다른가를 비교하는 함수이다. 
	 */
	public boolean isNotEqual(FigureID from) {
		if (isEqual(from)) return false;
		return true;
	}
	/** 이 객체의 값이 인자의 값에 포함되는가를 검사하는 함수이다. 
	 */
	public boolean isIn(FigureID from) {
		for(int i = 0; i < 8; i++) {
			if ((_bytes[i] & from._bytes[i]) != 0) return true;
		}
		return false;
	}
	/** 이 객체와 인자간에 비트단위 OR 연산을 수행하는 함수이다.
	 */
	public FigureID oring(FigureID from) {
		FigureID tmp = new FigureID(-1);
		for(int i = 0; i < 8; i++) {
			tmp._bytes[i] = (byte)(_bytes[i] | from._bytes[i]);
		}
		return tmp;
	}
}
