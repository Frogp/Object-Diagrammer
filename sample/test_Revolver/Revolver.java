class Revolver extends LinkedList<String>
{
	static boolean KILLED = true;
	static boolean ALIVE = false; 
	private ListNode<String> pCurrent;
	private int nHoles;
	private int nBullets;
	Revolver(boolean bullets[],int n)
	{
		nHoles = n;
		nBullets = 0;
		for(int i = 0; i < n; i++) {
			if (bullets[i] == true) {
				addLast("*");
				nBullets++;
				nHoles--;
			} else {
				addLast("0");
			}
		}
		pCurrent = pHead;
	}
	boolean bang()
	{
		int number = (int)(Math.random()*10);
		number = number - number / 10 * 10;
		if (number%2 == 0) {
			for(int i = 0; i < number; i++) {
				pCurrent = pCurrent.getPrev();
			}
			System.out.println("Rotate right " + number + " times.");
		} else {
			for(int i = 0; i < number; i++) {
				pCurrent = pCurrent.getNext();
			}
			System.out.println("Rotate left " + number + " times.");
		}
		if (pCurrent.getData().equals("*")) {
			System.out.println("Sorry! You are dead.");
			nBullets--;
			nHoles++;
			return KILLED;
		} else {
			return ALIVE;
		}
	}
	void print() {
		print(false);
	}
	void print(boolean first)
	{
		ListNode<String> tmp = pCurrent;
		if (tmp.getData().equals("*")) {
			if (first == true) {
				System.out.print("* ");
			} else {
				System.out.print("X ");
				tmp.setData("0");
			}
		} else {
			System.out.print("0 ");
		}
		tmp = tmp.getNext();
		for(int i = 1; i < nCount; i++) {
			System.out.print(tmp.getData() + " ");
			tmp = tmp.getNext();
		}
		System.out.println();
	}
}


