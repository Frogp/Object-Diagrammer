class ListIterator<Type> 
{
	ListNode<Type> pHead;
	ListNode<Type> ptr;
	ListIterator(ListNode<Type> pHead) {
		this.pHead = pHead;
		ptr = null;
	}
	boolean hasNext() {
		if (ptr == null) {
			if (pHead == null)
				return false;
			ptr = pHead;
			return true;
		}
		if (ptr == pHead)
			return false;
		else
			return true;
	}
	Type next() {
		Type data = ptr.getData();
		ptr = ptr.getNext();
		return data;
	}
};

