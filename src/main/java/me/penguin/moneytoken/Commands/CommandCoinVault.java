package me.penguin.moneytoken.Commands;

import java.util.List;

import org.bukkit.entity.Player;

import me.Penguin.SuperPenguinCore.Commands.Handlers.CustomCommand;
import me.Penguin.SuperPenguinCore.Util.m;
import me.penguin.moneytoken.MoneyTokens;
import me.penguin.moneytoken.Objects.CoinPouch;

public class CommandCoinVault extends CustomCommand {

	public CommandCoinVault() {
		super("coinvault", "moneytokens.admin");
	}

	@Override
	public void run() {
		if (!checker.isLabel("give")) return;
		if (!checker.checkLength(3)) return;
		if (!checker.isPlayer(1)) return;
		if (!checker.isInt(2)) return;

		int tier = helper.getInt(2);
		Player target = helper.getPlayer(1);

		if (!MoneyTokens.getInstance().getCoinVaultLevels().containsKey(tier)) {
			sender.sendMessage(m.invalid("Tier", String.valueOf(tier)));
			return;
		}

		target.getInventory().addItem(new CoinPouch(tier).getItem());
		sender.sendMessage(m.Given(target, "a Tier " + tier + " Coin Vault!"));

	}

	@Override
	public List<String> getTabCompleteOptions(int arg) {
		switch(arg) {
		case 0: return list("give");
		case 2: return list("1", "2", "3", "4", "5");
		}
		return null;
	}


}
