package me.penguin.moneytoken;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.Penguin.SuperPenguinCore.API.PenguinPlugin;
import me.Penguin.SuperPenguinCore.Util.Range;
import me.penguin.moneytoken.Commands.CommandCoinVault;
import me.penguin.moneytoken.Commands.CommandMoneyToken;
import me.penguin.moneytoken.Commands.CommandWithdraw;
import me.penguin.moneytoken.Objects.CashNote;
import me.penguin.moneytoken.Objects.CoinPouch;
import me.penguin.moneytoken.Objects.Token;

public class MoneyTokens extends PenguinPlugin {
	
	private List<Player> inAnimation;
	
	private static MoneyTokens instance;
	public static MoneyTokens getInstance() { return instance; }

	@Override
	public void OnEnable() {
		inAnimation = new ArrayList<>();
		instance = this;
	}

	@Override
	public void onDisable() {}

	@Override
	public void register() {
		new Token(0);
		new CoinPouch(0);
		new CashNote(0, null);

		new CommandMoneyToken();
		new CommandCoinVault();
		new CommandWithdraw();
	}

	private final HashMap<Integer, Range> coinVaultLevels = new HashMap<>() {{
		put(1, new Range(25_000, 100_000));
		put(2, new Range(100_000, 500_000));
		put(3, new Range(500_000, 2_500_000));
		put(4, new Range(2_500_000, 10_000_000));
		put(5, new Range(10_000_000, 50_000_000));
	}};
	
	public List<Player> getInAnimation() {return inAnimation;}
	public HashMap<Integer, Range> getCoinVaultLevels() {return coinVaultLevels;}




}
