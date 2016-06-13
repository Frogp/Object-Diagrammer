class RussianRoulette 
{
	public static void main(String[] args) 
	{
		int MAX = 8;
		System.out.println("--- Welcome to Hell (Russian Roulette) ---");
		System.out.println("Load Bullets : You can load up to " + MAX + " bullets.");
		boolean bullets[] = new boolean[MAX];
		int i;
		for(i = 0; i < MAX; i++) {
			String c;

			System.out.print("Do you want to load a bullet at hole[" + i + "] ? (y/n) ");
			c = System.console().readLine();
			if (c.equals("y")) {
				bullets[i] = true;
			} else {
				bullets[i] = false;
			}	
		}	
		System.out.print("Type the number of players: ");
		int num;

		num = Integer.parseInt(System.console().readLine());;

		Revolver aGun = new Revolver(bullets,MAX);
		System.out.print("Loaded bullets: ");
		aGun.print(true);
		i = 1;
		while(true) {
			String c;
			
			if (i > num) {
				i = 1;
			}
			System.out.print(i + "th man's turn : do you want to continue ? (y/n) ");
			c = System.console().readLine();;
			if (!c.equals("y")) System.exit(0);
			if (aGun.bang()) System.out.println("\007");
			aGun.print();
			i++;
		}
	}
}
