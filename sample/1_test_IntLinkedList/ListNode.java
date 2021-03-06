class ListNode {
	private int data;
	private ListNode pPrev;
	private ListNode pNext;
	ListNode() {
		data = 99999;
		pPrev = this;
		pNext = this;
	}
	ListNode(int x) {
		data = x;
		pPrev = this;
		pNext = this;
	}
	int getData() {
		return data;
	}
	void setData(int x) {
		data = x;
	}
	ListNode getNext() {
		return pNext;
	}
	void setNext(ListNode p) {
		pNext = p;
	}
	ListNode getPrev() {
		return pPrev;
	}
	void setPrev(ListNode p) {
		pPrev = p;
	}
	void insert(ListNode pNode) {
		// this node is inserted before pNode
		// pNode must be understood as a chain
		pPrev = pNode.pPrev;
		pNext = pNode;
		pNode.pPrev.pNext = this;
		pNode.pPrev = this;
	}
	void append(ListNode pNode) {
		// this node is appended just after pNode
		// pNode must be understood as a chain
		pPrev = pNode;
		pNext = pNode.pNext;
		pNode.pNext.pPrev = this;
		pNode.pNext = this;
	}
	void remove() {
		this.pNext.pPrev = this.pPrev;
		this.pPrev.pNext = this.pNext;
	}
};




