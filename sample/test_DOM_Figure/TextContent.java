package figure;

import javax.swing.tree.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 텍스트 라인 객체들을
 * 관리하는 클래스이다. 이 클래스의 논리적인 의미는 텍스트 객체의 내용을 
 * 텍스트 라인 단위로 저장하는 것이다.
 */
public 
	class TextContent extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -5739103502529004302L;
	/** 텍스트 라인의 가장 긴 폭 값을 저장하는 데이타 멤버
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
	/** 첫번째 텍스트 라인을 스트링 이름으로 변환하는 함수이다.
	 */
	public String toString() {
		return getName();
	}
	/** 생성자이다.
	 */
	public TextContent() {
	}
	/**
	 * 이 함수는 소멸자의 대용이다. Java에서 소멸자가 필요없기는 하지만
	 * 때때로 소멸자의 대용으로서 데이타 멤버의 값을 reset시키는 것이 안전한 경우가 있다.
	 */
	public void delete() {
		clearContent();
		super.delete();
	}
	/** 이 텍스트 내용에 포함된 라인 수를 구하는 함수이다.
	 */
	public int nOfLines() {
		return nOfList();
	}
	/** 이 텍스트 내용의 폭을 구한다. (좌표 기준)
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
	/** 이 텍스트 내용의 폭을 구한다. (문자수 기준)
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
	/** 내용이 비었는가를 확인하는 함수
	 */
	public boolean isEmpty() {
		if (nOfList() > 1) return false;
		if (width() > 0) return false;
		return true;
	}
	/** 텍스트 내용이 이름인 경우에 파싱을 하는 함수이다.
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
	/** 이 텍스트의 이름을 구하는 함수이다.
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
	/** 이 텍스트 내용을 종류 별로 파싱하는 함수이다.
	 */
	public void doParse(int who) {
		if (who == ClassText.NAMEFIELD) {
//			doParseForName();
			/* 이 줄에 대해서는 에트리랑 예기가 필요함.
			   내 경우는 클래스 이름이나 패키지 이름이 여러줄에 걸쳐서 있는 경우
			   모든 내용을 한 줄의 스트링으로 컨버전 하는게 필요하기 때문에
			   TextContent의 doParseForName()을 이용해야 함.
			   에트리 경우는 가시성 처리를 위해 한 줄만 가지고 작업하는거 같은데 그런 경우에
			   여러 줄에 걸친 클래스 이름의 뒷부분은 짤라버릴 건가 ? 문제가 있음 */
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
	/** 이 텍스트 내용을 복사하는 함수이다.
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
	/** n-번째 텍스트 라인을 구하는 함수이다.
	 */
	public TextLine lineAt(int n) {
		TextLine aLine = getFirst();
		if (n == 0) return aLine;
		for (int i = 0; i < n; i++) {
			aLine = getNext();
		}
		return aLine;
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insertAt(TextLine ptr,int n) {
		super._insertAt((Object)ptr,n);
	}
	/** 이 리스트의 마지막에 새로운 객체를 무조건 insert 하는 함수이다.
	 */
	public void insert(TextLine ptr) {
		super._insert((Object)ptr,Const.ABSOLUTELY);
	}
	/** 리스트의 n-번째에 새로운 텍스트 라인 객체를 삽입하는 함수이다.
	 */
	public TextLine newLineAt(int n) {
		TextLine newLine = new TextLine();
		insertAt(newLine,n);
		return newLine;
	}
	/** 인자에서 명시된 줄의 텍스트 라인을 삭제하는 함수이다.
	 */
	public void removeLineAt(int pos) {
		TextLine aLine = (TextLine )super._removeAt(pos);
		if (aLine != null) aLine.delete();
	}
	/** 텍스트 내용을 비우는 함수이다.
	 */
	public void clearContent() {
		TextLine aLine = getFirst();
		while (aLine != null) {
			aLine.delete();
			aLine = getNext();
		}
		super._clear();
	}
	/** j-번째 라인의 i-번째 문자 위치에 그 라인 텍스트를 둘로 쪼개는 함수이다.
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
	/** 텍스트 내용의 i-번째 줄, j-번째 열에 있는 문자 하나 읽어오는 함수이다.
	 */
	public char valueAt(int i,int j) {
		TextLine aLine = lineAt(j);
		return aLine.valueAt(i);
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public TextLine getFirst() {
		return (TextLine)super._getFirst();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public TextLine getLast() {
		return (TextLine)super._getLast();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public TextLine getNext() {
		return (TextLine)super._getNext();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	public TextLine getPrevious() {
		return (TextLine)super._getPrevious();
	}
}