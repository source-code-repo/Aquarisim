package aquarisim.bank;

import aquarisim.ui.Hud;

public class Account {
	private long balance = 0;
	private static Account singleton;
	
	private Account() { }
	
	public static Account getInstance() {
		if(singleton == null) {
			reset();
		}
		return singleton;
	}
	
	public static void reset() {
		singleton = new Account();
	}
	
	public void add(long toAdd) {
		balance += toAdd;
		Hud.getInstance().setBalance(balance);
	}
	
	public void remove(long toRemove) {
		balance -= toRemove;
		Hud.getInstance().setBalance(balance);
	}
	
	public long getBalance() {
		return balance;
	}
}
