class TestLinkedList 
{
	static int readInt() {
		String s = System.console().readLine();
		return Integer.parseInt(s);
	}
	public static void main(String[] args) 
	{
		LinkedList aList = new LinkedList();
		while(true) {
			int select;
			System.out.print("What do you want ? <1>addFront, <2>addTail, <3>remove, <4>quit : ");
			select = readInt();
			if (select < 1 || select > 3) {
				break;
			}
			System.out.print("Type data : ");
			int data;
			data = readInt();
			switch (select) {
			case 1 :
				aList.addFirst(data);
				System.out.println(aList);
				break;
			case 2 :
				aList.addLast(data);
				System.out.println(aList);
				break;
			case 3 :
				if (aList.remove(data) == true) {
					System.out.println(aList);
				}
				break;
			default :
				System.out.println(aList);
				break;
			}
		}
		// test other operations
		System.out.println("size of the list = " + aList.size());

		System.out.println("The list can be traversed like the following style.");
		ListIterator i = aList.listIterator();
		while(i.hasNext()) {
			int data = i.next();
			System.out.println(data);
		}

		aList.add(2,1000);
		System.out.println(aList);
	}
}
