package figure;

import java.io.*;
import java.lang.*;
import javax.swing.tree.*;

/** �� Ŭ������ Ŭ������ ���뿡 ���� ������ �����ϴ� Ŭ�����̴�.
 * �� ��ü���� ���� �̸��� Ŭ������ ���� ������ �����ϰ� �ϱ� ���� ������
 * ��Ʈ�ѷ��� ShadowClasses ��ü�� ���� ����Ÿ�� ���ȴ�.
 */
public class ClassContent extends Object implements Serializable {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = 2722062376414310904L;
	/** �� Ŭ������ ���ϴ� ��Ű���� �̸��̴�.
	 */
	public String packageName;
	/** Ŭ���� ��ü�� �̸� �κп� �ش��ϴ� �ؽ�Ʈ �����̴�.
	 */
	public TextContent nameContent;
	/** Ŭ���� ��ü�� ����Ÿ ��� �κп� �ش��ϴ� �ؽ�Ʈ �����̴�.
	 */
	public TextContent varContent;
	/** Ŭ���� ��ü�� ��� �Լ� �κп� �ش��ϴ� �ؽ�Ʈ �����̴�.
	 */
	public TextContent methodContent;
	/** �������̴�.
	 */
	ClassContent() {
		packageName = new String("");
		nameContent = null;
		varContent = null;
		methodContent = null;
	}
	/** �������̴�.
	 */
	ClassContent(TextContent name,TextContent var,TextContent method) {
		packageName = new String("");
		nameContent = name;
		varContent = var;
		methodContent = method;
	}
	/** ��Ű�� �̸��� ���� �����ϴ� �Լ��̴�. 
	 */
	public void replacePackageName(String oldName,String newName) {
		if (oldName.equals(packageName)) {
			packageName = new String(newName);
		}
	}
	/** ������ ����°��� �˾Ƴ��� �Լ��̴�.
	 */
	public boolean hasEmptyContent() {
		if (varContent == null) return true;
		if (methodContent == null) return true;
		if (varContent.isEmpty() == false) return true;
		if (methodContent.isEmpty() == false) return true;
		return false;
	}
	/** Ŭ���� �̸��� �˾Ƴ��� access �Լ��̴�.
	 */
	public String getClassName() {
		return nameContent.getName();
	}
	/** Ŭ���� �̸��� �Է����� ��Ʈ���� �������� �����ϴ� �Լ��̴�.  
	 */
	public boolean equalClassName(String className) {
		return className.equals(nameContent.getName());
	}
	/** ��Ű�� �̸��� �Է����� ��Ʈ���� �������� �����ϴ� �Լ��̴�.
	 */
	public boolean equalPackageName(String packName) {
		return packageName.equals(packName);
	}
	/** ��Ű�� �̸��� �����ϴ� �Լ��̴�.
	 */
	public void setPackageName(String pName) {
		packageName = new String(pName);
	}
	/** �� Ŭ������ ������ Ʈ�� ���� ��ȯ�� �ִ� �Լ��̴�.
	 * ��ȯ�� Ʈ�� ���� �������� ���� ���ȴ�.
	 */
	public DefaultMutableTreeNode convertAsTreeNode() {
		DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(nameContent);
		varContent.addMemberAsTreeNode(classNode);
		methodContent.addMemberAsTreeNode(classNode);
		return classNode;
	}
	/** �� Ŭ���� ���뿡�� ���ڿ� ���� ��õ� ������ �����ϴ� �Լ��̴�.
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
	/** �� Ŭ���� ������ ������ ���ο� ���ο� ���� �߰��ϴ� �Լ��̴�.
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
	/** �� Ŭ���� ������ ���ڿ��� ��õ� ��ġ�� ���ο� ���� �߰��ϴ� �Լ��̴�.
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