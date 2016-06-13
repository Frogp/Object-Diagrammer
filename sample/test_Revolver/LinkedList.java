class LinkedList<Type> 
{
	protected ListNode<Type> pHead;
	protected int nCount;
	LinkedList()
	{
		pHead = null;
		nCount = 0;
	}
	boolean isEmpty() {
		if (pHead == null) return true;
		else return false;
	}
	int size() {
		return nCount;
	}
	void addFirst(Type data)
	{
		addLast(data);
		pHead = pHead.getPrev();
	}
	void addLast(Type data)
	{
		ListNode<Type> pNewNode = new ListNode<Type>(data);
		nCount++;
		if (pHead == null) {
			pHead = pNewNode;
			return;
		}
		pNewNode.insert(pHead);
	}
	void add(int index,Type data)
	{
		if (index < 0 || index > nCount) {
			System.out.println("index out of bound error - add(index,data) failed.");
			return;
		}
		if (index == 0) {
			addFirst(data);
			return;
		}
		int count = 1;
		ListNode<Type> pFollow = pHead;
		ListNode<Type> pTraverse = pHead.getNext();
		while(pTraverse != null) {
			if (index == count) break;
			count++;
			pFollow = pTraverse;
			pTraverse = pTraverse.getNext();
		}
		ListNode<Type> pNewNode = new ListNode<Type>(data);
		nCount++;
		pNewNode.append(pFollow);
	}
	boolean remove(Type data)
	{
		if (isEmpty() == true) {
			System.out.println("The list is empty. No data removed.");
			return false;
		}
		if (pHead != null && pHead.getData().equals(data)) {
			ListNode<Type> pNextNode = pHead.getNext();
			pHead.remove();
			nCount--;
			if (pNextNode == pHead) pHead = null;
			else pHead = pNextNode;
			return true;
		}
		ListNode<Type> pFollow = pHead;
		ListNode<Type> pTraverse = pHead.getNext();
		while(pTraverse != pHead) {
			if (pTraverse.getData().equals(data)) {
				pTraverse.remove();
				nCount--;
				return true;
			}
			pFollow = pTraverse;
			pTraverse = pTraverse.getNext();
		}
		System.out.println(data + " is not found. No data removed.");
		return false;
	}
	ListIterator listIterator() {
		return new ListIterator<Type>(pHead);
	}
	public String toString()
	{
		if (isEmpty() == true) {
			return "<>";
		}
		String tmp = "< ";
		ListNode<Type> pNode = pHead;
		for(int i = 0; i < nCount; i++) {
			tmp = tmp + pNode.getData();
			if (i < nCount-1) {
				tmp = tmp + ", ";
			} else {
				tmp = tmp + " >";
			}	
			pNode = pNode.getNext();
		}
		return tmp;
	}
}

