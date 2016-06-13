package figure;

import javax.swing.tree.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� �ؽ�Ʈ ���� ��ü����
 * �����ϴ� Ŭ�����̴�. �� Ŭ������ ������ �ǹ̴� �ؽ�Ʈ ��ü�� ������ 
 * �ؽ�Ʈ ���� ������ �����ϴ� ���̴�.
 */
public 
	class TextContent extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -5739103502529004302L;
	/** �ؽ�Ʈ ������ ���� �� �� ���� �����ϴ� ����Ÿ ���
	 */
	public void addMemberAsTreeNode(DefaultMutableTreeNode classNode) {
		TextLine aLine = getFirst();
		while (aLine != null) {
			if (aLine.name.length() != 0) {
				DefaultMutableTreeNode memberNode = new DefaultMutableTreeNode(aLine);
				memberNode.setAllowsChildren(false);
				classNode.add(memberNode);
			}
			aLine = getNext();
		}
	}
	/** ù��° �ؽ�Ʈ ������ ��Ʈ�� �̸����� ��ȯ�ϴ� �Լ��̴�.
	 */
	public String toString() {
		return getName();
	}
	/** �������̴�.
	 */
	public TextContent() {
	}
	/**
	 * �� �Լ��� �Ҹ����� ����̴�. Java���� �Ҹ��ڰ� �ʿ����� ������
	 * ������ �Ҹ����� ������μ� ����Ÿ ����� ���� reset��Ű�� ���� ������ ��찡 �ִ�.
	 */
	public void delete() {
		clearContent();
		super.delete();
	}
	/** �� �ؽ�Ʈ ���뿡 ���Ե� ���� ���� ���ϴ� �Լ��̴�.
	 */
	public int nOfLines() {
		return nOfList();
	}
	/** �� �ؽ�Ʈ ������ ���� ���Ѵ�. (��ǥ ����)
	 */
	public int width(Font f,FontRenderContext frc) {
		int w = 0;
		TextLine aLine = getFirst();
		while (aLine != null) {
			Rectangle2D r = f.getStringBounds(aLine.theLine,frc);
			if (w < r.getWidth()) {
				w = (int)r.getWidth();
			}
			aLine = getNext();
		}
		return w;
	}
	/** �� �ؽ�Ʈ ������ ���� ���Ѵ�. (���ڼ� ����)
	 */
	public int width() {
		int w = 0;
		TextLine aLine = getFirst();
		while (aLine != null) {
			if (w < aLine.getLength()) {
				w = aLine.getLength();
			}
			aLine = getNext();
		}
		return w;
	}
	/** ������ ����°��� Ȯ���ϴ� �Լ�
	 */
	public boolean isEmpty() {
		if (nOfList() > 1) return false;
		if (width() > 0) return false;
		return true;
	}
	/** �ؽ�Ʈ ������ �̸��� ��쿡 �Ľ��� �ϴ� �Լ��̴�.
	 */
	private void doParseForName() {
//		System.out.println("TextContent.doParseForName()");
		char name[] = new char[TextLine.LINEBUFSIZ];
		for(int i = 0; i < TextLine.LINEBUFSIZ; i++) {
			name[i] = '\0';
		}
		int ptr = 0;
		boolean finishFlag = false;
		TextLine aLine = getFirst();
		while (aLine != null) {
			int from = 0;
			while (aLine.valueAt(from) != '\0') {
				if (aLine.valueAt(from) != ' ') {
					name[ptr] = aLine.valueAt(from);
					ptr++;
					if (ptr >= TextLine.LINEBUFSIZ-2) {
						finishFlag = true;
						break;
					}
				}
				from++;
			}
			if (finishFlag) break;
			aLine = getNext();
		}
		TextLine firstLine = getFirst();
		firstLine.name = new String(name,0,MySys.strlen(name,0));
	}
	/** �� �ؽ�Ʈ�� �̸��� ���ϴ� �Լ��̴�.
	 */
	public String getName() {
		String s = null;
		TextLine firstLine = getFirst();
		if (firstLine.name == null) {
			s = new String("");
		} else {
			s = new String(firstLine.name);
		}
		return s;
	}
	/** �� �ؽ�Ʈ ������ ���� ���� �Ľ��ϴ� �Լ��̴�.
	 */
	public void doParse(int who) {
		if (who == ClassText.NAMEFIELD) {
//			doParseForName();
			/* �� �ٿ� ���ؼ��� ��Ʈ���� ���Ⱑ �ʿ���.
			   �� ���� Ŭ���� �̸��̳� ��Ű�� �̸��� �����ٿ� ���ļ� �ִ� ���
			   ��� ������ �� ���� ��Ʈ������ ������ �ϴ°� �ʿ��ϱ� ������
			   TextContent�� doParseForName()�� �̿��ؾ� ��.
			   ��Ʈ�� ���� ���ü� ó���� ���� �� �ٸ� ������ �۾��ϴ°� ������ �׷� ��쿡
			   ���� �ٿ� ��ģ Ŭ���� �̸��� �޺κ��� ©����� �ǰ� ? ������ ���� */
			TextLine aLine = getFirst();
			aLine.doParseForName();
		} else if (who == ClassText.METHODFIELD) {
			TextLine aLine = getFirst();
			while (aLine != null) {
				aLine.dataMemberFlag = false;
				aLine.doParseForMethod();
//				aLine.printAsMethod();
				aLine = getNext();
			}
		} else if (who == ClassText.VARFIELD) {
			TextLine aLine = getFirst();
			while (aLine != null) {
				aLine.dataMemberFlag = true;
				aLine.doParseForVar();
//				aLine.printAsVar();
				aLine = getNext();
			}
		}
	}
	/** �� �ؽ�Ʈ ������ �����ϴ� �Լ��̴�.
	 */
	public TextContent born() {
		TextContent copied = new TextContent();
		TextLine aLine = getFirst();
		while (aLine != null) {
			copied._insert((Object)aLine.born(),Const.ABSOLUTELY);
			aLine = getNext();
		}
		return copied;
	}
	/** n-��° �ؽ�Ʈ ������ ���ϴ� �Լ��̴�.
	 */
	public TextLine lineAt(int n) {
		TextLine aLine = getFirst();
		if (n == 0) return aLine;
		for (int i = 0; i < n; i++) {
			aLine = getNext();
		}
		return aLine;
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insertAt(TextLine ptr,int n) {
		super._insertAt((Object)ptr,n);
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� ������ insert �ϴ� �Լ��̴�.
	 */
	public void insert(TextLine ptr) {
		super._insert((Object)ptr,Const.ABSOLUTELY);
	}
	/** ����Ʈ�� n-��°�� ���ο� �ؽ�Ʈ ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public TextLine newLineAt(int n) {
		TextLine newLine = new TextLine();
		insertAt(newLine,n);
		return newLine;
	}
	/** ���ڿ��� ��õ� ���� �ؽ�Ʈ ������ �����ϴ� �Լ��̴�.
	 */
	public void removeLineAt(int pos) {
		TextLine aLine = (TextLine )super._removeAt(pos);
		if (aLine != null) aLine.delete();
	}
	/** �ؽ�Ʈ ������ ���� �Լ��̴�.
	 */
	public void clearContent() {
		TextLine aLine = getFirst();
		while (aLine != null) {
			aLine.delete();
			aLine = getNext();
		}
		super._clear();
	}
	/** j-��° ������ i-��° ���� ��ġ�� �� ���� �ؽ�Ʈ�� �ѷ� �ɰ��� �Լ��̴�.
	 */
	public TextLine splitLineAt(int i,int j) {
		TextLine thisLine = lineAt(j);
		TextLine beforeLine = newLineAt(j);
		beforeLine.copy(thisLine);
		if (beforeLine.getLength() == i) {
			thisLine.clear();
		} else {
			thisLine.clear();
			thisLine.copySubstring(beforeLine,i,beforeLine.getLength());
			beforeLine.truncateStringFrom(i);
		}
		return thisLine;
	}
	/** �ؽ�Ʈ ������ i-��° ��, j-��° ���� �ִ� ���� �ϳ� �о���� �Լ��̴�.
	 */
	public char valueAt(int i,int j) {
		TextLine aLine = lineAt(j);
		return aLine.valueAt(i);
	}
	/** ����Ʈ�� ��ü���� traverse �� �� �� ù��° ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public TextLine getFirst() {
		return (TextLine)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public TextLine getLast() {
		return (TextLine)super._getLast();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public TextLine getNext() {
		return (TextLine)super._getNext();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	public TextLine getPrevious() {
		return (TextLine)super._getPrevious();
	}
}