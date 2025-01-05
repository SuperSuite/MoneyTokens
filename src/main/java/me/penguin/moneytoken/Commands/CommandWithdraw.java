package me.penguin.moneytoken.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import me.Penguin.SuperPenguinCore.API.PenguinEconomyAPI;
import me.Penguin.SuperPenguinCore.Commands.Handlers.CustomCommand;
import me.Penguin.SuperPenguinCore.Util.m;
import me.Penguin.SuperPenguinCore.Util.u;
import me.penguin.moneytoken.Objects.CashNote;

public class CommandWithdraw extends CustomCommand {

	public CommandWithdraw() {
		super("withdraw", null);
	}

	@Override
	public void run() {
		if (!checker.isSenderPlayer()) return;
		if (!checker.checkLength(1)) return;
		if (!checker.isLong(0)) return;
		
		Player player = (Player) sender;
		long amount = helper.getLong(0);
		
		if (!PenguinEconomyAPI.has(player, amount)) {
			m.sendPrefixed(player, "&cYou do not have enough money.");
			return;
		}
		
		PenguinEconomyAPI.withdraw(player, amount);
		CashNote c = new CashNote(amount, player.getName());
		player.getInventory().addItem(c.getItem());
		m.sendPrefixed(player, "&7Successfully withdrawn $" + u.dc(amount));
		
	}

	@Override
	public List<String> getTabCompleteOptions(int arg) {
		if (arg == 0) return Arrays.asList("1", "100");
		return null;
	}
	
}
	