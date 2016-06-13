class ListIterator
{
	ListNode pHead;
	ListNode ptr;
	ListIterator(ListNode pHead) {
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
	int next() {
		int data = ptr.getData();
		ptr = ptr.getNext();
		return data;
	}
};
