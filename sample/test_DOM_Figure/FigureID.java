package figure;

import java.io.*;
/** �� Ŭ������ �׸� ��ü�� �ĺ��ڸ� �����ϱ� ���� ������ 64 ��Ʈ ����Ÿ
 * ó�� �̿�ȴ�.
 */
public final 
	class FigureID extends Object implements Serializable {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 721228677340452646L;
	/** ��Ʈ�ϰ��� �ϴ� ��Ʈ�� ��ġ, 0<=_n<=63 
	 */
	protected int _n;
	/** 64 ��Ʈ ����Ÿ
	 */
	protected byte _bytes[];
	/** �������̴�.
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
	/** �� ��ü�� ���� ���ڿ� �������� ���ϴ� �Լ��̴�. 
	 */
	public boolean isEqual(FigureID from) {
		for(int i = 0; i < 8; i++) {
			if (_bytes[i] != from._bytes[i]) return false;
		}
		return true;
	}
	/** �� ��ü�� ���� ���ڿ� �ٸ����� ���ϴ� �Լ��̴�. 
	 */
	public boolean isNotEqual(FigureID from) {
		if (isEqual(from)) return false;
		return true;
	}
	/** �� ��ü�� ���� ������ ���� ���ԵǴ°��� �˻��ϴ� �Լ��̴�. 
	 */
	public boolean isIn(FigureID from) {
		for(int i = 0; i < 8; i++) {
			if ((_bytes[i] & from._bytes[i]) != 0) return true;
		}
		return false;
	}
	/** �� ��ü�� ���ڰ��� ��Ʈ���� OR ������ �����ϴ� �Լ��̴�.
	 */
	public FigureID oring(FigureID from) {
		FigureID tmp = new FigureID(-1);
		for(int i = 0; i < 8; i++) {
			tmp._bytes[i] = (byte)(_bytes[i] | from._bytes[i]);
		}
		return tmp;
	}
}
