class ListNode<Type> {
	private Type data;
	private ListNode<Type> pPrev;
	private ListNode<Type> pNext;
	ListNode() {
		data = null;
		pPrev = this;
		pNext = this;
	}
	ListNode(Type x) {
		data = x;
		pPrev = this;
		pNext = this;
	}
	Type getData() {
		return data;
	}
	void setData(Type x) {
		data = x;
	}
	ListNode getNext() {
		return pNext;
	}
	void setNext(ListNode<Type> p) {
		pNext = p;
	}
	ListNode getPrev() {
		return pPrev;
	}
	void setPrev(ListNode<Type> p) {
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
