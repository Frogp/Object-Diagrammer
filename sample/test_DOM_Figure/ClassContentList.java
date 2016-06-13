package figure;

import modeler.*;

import java.io.*;
import javax.swing.tree.*;
/** 이 클래스는 컨테이너 역할을 하는 리스트 클래스로서 클래스 내용들을
 * 관리하는 클래스이다.
 */
public final
	class ClassContentList extends List {
	/** 버전 제어를 위한 시리얼 넘버이다.
	 */
	public static final long serialVersionUID = -5555398616503540070L;
	/** 생성자이다.
	 */
	public ClassContentList() {
		super();
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 */
	public void insert(ClassContent ptr,int tag) {
		super._insert((Object)ptr,tag);
	}
	/** 이 리스트의 마지막에 새로운 객체를 insert 하는 함수이다.
	 * 만약 리스트에 이미 객체가 들어있으면 중복해서 넣지 않는다.
	 */
	public void insert(ClassContent ptr) {
		insert(ptr,0);
	}
	/** 이 리스트에 객체가 존재하는지 확인하는 함수이다.
	 */
	public boolean inList(ClassContent ptr) {
		return super._inList((Object)ptr);
	}
	/** 이 리스트로 부터 객체를 삭제하는 함수이다.
	 */
	public void remove(ClassContent ptr) {
		super._remove((Object)ptr,0);
	}
	/** 이 리스트를 비우는 함수이다.
	 */
	public void clear() {
		super._clear();
	}
	/** 리스트의 객체들을 traverse 할 때 맨 첫번째 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public ClassContent getFirst() {
		return (ClassContent)super._getFirst();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 하기위해 맨 마지막 객체를 찾아오는 함수이다.
	 * 만약 리스트가 비어있게되면 null 값을 리턴한다.
	 */
	public ClassContent getLast() {
		return (ClassContent)super._getLast();
	}
	/** 리스트의 객체들을 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 끝에 도달하면 null 값을 리턴한다.
	 */
	public ClassContent getNext() {
		return (ClassContent)super._getNext();
	}
	/** 리스트의 객체들을 반대 방향으로 traverse 할 때 다음 객체를 찾아오는 함수이다.
	 * 만약 리스트의 앞에 도달하면 null 값을 리턴한다.
	 */
	public ClassContent getPrevious() {
		return (ClassContent)super._getPrevious();
	}
	/** 패키지 이름을 새로 변경하는 함수이다. 
	 */
	public void replacePackageName(String oldName,String newName) {
		ClassContent aContent = getFirst();
		while (aContent != null) {
			aContent.replacePackageName(oldName,newName);
			aContent = getNext();
		}
	}
	/** 인자에 주어진 클래스 이름과 같은 클래스의 내용을 찾는 함수이다.
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
	/** 이 리스트에 속하는 클래스 내용들을 트리 구조로 변환하는 것이다.
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
	/** 클래스 트리를 만드는 과정에서 클래스 노드를 패키지 노드에 추가하는 함수이다.
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