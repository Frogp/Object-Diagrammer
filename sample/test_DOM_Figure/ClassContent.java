package figure;

import java.io.*;
import java.lang.*;
import javax.swing.tree.*;

/** 이 클래스는 클래스의 내용에 대한 정보를 저장하는 클래스이다.
 * 이 객체들은 같은 이름의 클래스가 같은 내용을 공유하게 하기 위한 것으로
 * 콘트롤러의 ShadowClasses 객체의 기초 데이타로 사용된다.
 */
public class ClassContent extends Object implements Serializable {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = 2722062376414310904L;
	/** 이 클래스가 속하는 패키지의 이름이다.
	 */
	public String packageName;
	/** 클래스 객체의 이름 부분에 해당하는 텍스트 내용이다.
	 */
	public TextContent nameContent;
	/** 클래스 객체의 데이타 멤버 부분에 해당하는 텍스트 내용이다.
	 */
	public TextContent varContent;
	/** 클래스 객체의 멤버 함수 부분에 해당하는 텍스트 내용이다.
	 */
	public TextContent methodContent;
	/** 생성자이다.
	 */
	ClassContent() {
		packageName = new String("");
		nameContent = null;
		varContent = null;
		methodContent = null;
	}
	/** 생성자이다.
	 */
	ClassContent(TextContent name,TextContent var,TextContent method) {
		packageName = new String("");
		nameContent = name;
		varContent = var;
		methodContent = method;
	}
	/** 패키지 이름을 새로 변경하는 함수이다. 
	 */
	public void replacePackageName(String oldName,String newName) {
		if (oldName.equals(packageName)) {
			packageName = new String(newName);
		}
	}
	/** 내용이 비었는가를 알아내는 함수이다.
	 */
	public boolean hasEmptyContent() {
		if (varContent == null) return true;
		if (methodContent == null) return true;
		if (varContent.isEmpty() == false) return true;
		if (methodContent.isEmpty() == false) return true;
		return false;
	}
	/** 클래스 이름을 알아내는 access 함수이다.
	 */
	public String getClassName() {
		return nameContent.getName();
	}
	/** 클래스 이름이 입력인자 스트링과 같은가를 학인하는 함수이다.  
	 */
	public boolean equalClassName(String className) {
		return className.equals(nameContent.getName());
	}
	/** 패키지 이름이 입력인자 스트링과 같은가를 학인하는 함수이다.
	 */
	public boolean equalPackageName(String packName) {
		return packageName.equals(packName);
	}
	/** 패키지 이름을 지정하는 함수이다.
	 */
	public void setPackageName(String pName) {
		packageName = new String(pName);
	}
	/** 이 클래스의 내용을 트리 노드로 변환해 주는 함수이다.
	 * 변환된 트리 노드는 브라우저에 의해 사용된다.
	 */
	public DefaultMutableTreeNode convertAsTreeNode() {
		DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(nameContent);
		varContent.addMemberAsTreeNode(classNode);
		methodContent.addMemberAsTreeNode(classNode);
		return classNode;
	}
	/** 이 클래스 내용에서 인자에 의해 명시된 라인을 제거하는 함수이다.
	 */
	public TextLine removeLineAt(int whichMember,int pos) {
		if (whichMember == ClassText.VARFIELD) {
			if (varContent.nOfLines() == 1) {
				TextLine copied = new TextLine();
				copied.copy(varContent.lineAt(0));
				varContent.lineAt(0).clear();
				return copied;
			} else {
				TextLine copied = new TextLine();
				copied.copy(varContent.lineAt(pos));
				varContent.removeLineAt(pos);
				return copied;
			}
		} else if (whichMember == ClassText.METHODFIELD) {
			if (methodContent.nOfLines() == 1) {
				TextLine copied = new TextLine();
				copied.copy(methodContent.lineAt(0));
				methodContent.lineAt(0).clear();
				return copied;
			} else {
				TextLine copied = new TextLine();
				copied.copy(methodContent.lineAt(pos));
				methodContent.removeLineAt(pos);
				return copied;
			}
		}
		return null;
	}
	/** 이 클래스 내용의 마지막 라인에 새로운 행을 추가하는 함수이다.
	 */
	public void insertLineAtLast(int whichMember,TextLine newLine) {
		if (whichMember == ClassText.VARFIELD) {
			TextLine lastLine = varContent.lineAt(varContent.nOfLines()-1);
			if (lastLine.hasContent()) {
				varContent.insert(newLine);
			} else {
				lastLine.copy(newLine);
			}
		} else if (whichMember == ClassText.METHODFIELD) {
			TextLine lastLine = methodContent.lineAt(methodContent.nOfLines()-1);
			if (lastLine.hasContent()) {
				methodContent.insert(newLine);
			} else {
				lastLine.copy(newLine);
			}
		}
	}
	/** 이 클래스 내용의 인자에서 명시된 위치에 새로운 행을 추가하는 함수이다.
	 */
	public void insertLineAt(int whichMember,TextLine newLine,int pos) {
		if (whichMember == ClassText.VARFIELD) {
			if (pos > varContent.nOfLines()-1) {
				insertLineAtLast(whichMember,newLine);
			} else {
				TextLine aLine = varContent.lineAt(pos);
				if (aLine.hasContent()) {
					varContent.insertAt(newLine,pos);
				} else {
					aLine.copy(newLine);
				}
			}
		} else if (whichMember == ClassText.METHODFIELD) {
			if (pos > methodContent.nOfLines()-1) {
				insertLineAtLast(whichMember,newLine);
			} else {
				TextLine aLine = methodContent.lineAt(pos);
				if (aLine.hasContent()) {
					methodContent.insertAt(newLine,pos);
				} else {
					aLine.copy(newLine);
				}
			}
		}
	}
}