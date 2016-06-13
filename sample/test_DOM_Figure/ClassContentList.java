package figure;

import modeler.*;

import java.io.*;
import javax.swing.tree.*;
/** �� Ŭ������ �����̳� ������ �ϴ� ����Ʈ Ŭ�����μ� Ŭ���� �������
 * �����ϴ� Ŭ�����̴�.
 */
public final
	class ClassContentList extends List {
	/** ���� ��� ���� �ø��� �ѹ��̴�.
	 */
	public static final long serialVersionUID = -5555398616503540070L;
	/** �������̴�.
	 */
	public ClassContentList() {
		super();
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 */
	public void insert(ClassContent ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** �� ����Ʈ�� �������� ���ο� ��ü�� insert �ϴ� �Լ��̴�.
	 * ���� ����Ʈ�� �̹� ��ü�� ��������� �ߺ��ؼ� ���� �ʴ´�.
	 */
	public void insert(ClassContent ptr) {
		insert(ptr,0);
	}
	/** �� ����Ʈ�� ��ü�� �����ϴ��� Ȯ���ϴ� �Լ��̴�.
	 */
	public boolean inList(ClassContent ptr) {
		return super._inList((Object)ptr);
	}
	/** �� ����Ʈ�� ���� ��ü�� �����ϴ� �Լ��̴�.
	 */
	public void remove(ClassContent ptr) {
		super._remove((Object)ptr,0);
	}
	/** �� ����Ʈ�� ���� �Լ��̴�.
	 */
	public void clear() {
		super._clear();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� �� ù��° ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public ClassContent getFirst() {
		return (ClassContent)super._getFirst();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �ϱ����� �� ������ ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ����ְԵǸ� null ���� �����Ѵ�.
	 */
	public ClassContent getLast() {
		return (ClassContent)super._getLast();
	}
	/** ����Ʈ�� ��ü���� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� ���� �����ϸ� null ���� �����Ѵ�.
	 */
	public ClassContent getNext() {
		return (ClassContent)super._getNext();
	}
	/** ����Ʈ�� ��ü���� �ݴ� �������� traverse �� �� ���� ��ü�� ã�ƿ��� �Լ��̴�.
	 * ���� ����Ʈ�� �տ� �����ϸ� null ���� �����Ѵ�.
	 */
	public ClassContent getPrevious() {
		return (ClassContent)super._getPrevious();
	}
	/** ��Ű�� �̸��� ���� �����ϴ� �Լ��̴�. 
	 */
	public void replacePackageName(String oldName,String newName) {
		ClassContent aContent = getFirst();
		while (aContent != null) {
			aContent.replacePackageName(oldName,newName);
			aContent = getNext();
		}
	}
	/** ���ڿ� �־��� Ŭ���� �̸��� ���� Ŭ������ ������ ã�� �Լ��̴�.
	 */
	public ClassContent classContentFor(String className) {
		int count = 0;
		ClassContent aContent = getFirst();
		while (aContent != null) {
			if (aContent.equalClassName(className)) {
				if (count > 1) {
					remove(aContent);
					super._append((Object)aContent);
				}
				return aContent;
			}
			count++;
			aContent = getNext();
		}
		return null;
	}
	/** �� ����Ʈ�� ���ϴ� Ŭ���� ������� Ʈ�� ������ ��ȯ�ϴ� ���̴�.
	 */
	public DefaultMutableTreeNode makeAsTree(String rootName) {
		StringList packages = new StringList();
		packages.insert(Modeler.TopModeler.getPackageName());
		Modeler aModeler = Modeler.allModelers.getFirst();
		while(aModeler != null) {
			aModeler.controller().collectPackageNames(packages);
			aModeler = Modeler.allModelers.getNext();
		}
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootName);
		String pName = packages.getFirst();
		while (pName != null) {
			rootNode.add(new DefaultMutableTreeNode(pName));
			pName = packages.getNext();
		}
		packages.delete();
		ClassContent aContent = getFirst();
		while (aContent != null) {
			DefaultMutableTreeNode classNode = aContent.convertAsTreeNode();
			addClassNodeToPackageNode(aContent.packageName,rootNode,classNode);
			aContent = getNext();
		}
		return rootNode;
	}
	/** Ŭ���� Ʈ���� ����� �������� Ŭ���� ��带 ��Ű�� ��忡 �߰��ϴ� �Լ��̴�.
	 */	
	private void addClassNodeToPackageNode(String packName,DefaultMutableTreeNode rootNode,DefaultMutableTreeNode classNode) {
		int n = rootNode.getChildCount();
		for(int i = 0; i < n; i++) {
			DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
			Object nodeName = aNode.getUserObject();
			if (packName.equals(nodeName)) {
				aNode.add(classNode);
				return;
			}
		}
	}
}