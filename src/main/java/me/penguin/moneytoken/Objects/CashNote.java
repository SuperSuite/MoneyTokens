package me.penguin.moneytoken.Objects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Penguin.SuperPenguinCore.API.PenguinEconomyAPI;
import me.Penguin.SuperPenguinCore.Items.Held.Redeemable;
import me.Penguin.SuperPenguinCore.Util.MIB;
import me.Penguin.SuperPenguinCore.Util.NumberFormatter;
import me.Penguin.SuperPenguinCore.Util.m;
import me.Penguin.SuperPenguinCore.Util.u;

public class CashNote extends Redeemable {
	
	private long amount;
	private String ownerName;
	
	public CashNote(long amount, String ownerName) {
		this.amount = amount;
		this.ownerName = ownerName;
	}
	
	@Override
	public void OnRedeem(ItemStack item, Player player) {
		long amount = getAmount(item);
		PenguinEconomyAPI.deposit(player, amount);
		removeOne(item);
		m.sendPrefixed(player, "&aRedeemed a cash note worth $" + u.dc(amount));
	}

	@Override
	public ItemStack getItem() {
		String format = NumberFormatter.getFormatted(amount);
		if (format.contains(".")) {
			String[] parts = format.split("\\.");
			if (parts[1].length() > 1) parts[1] = parts[1].substring(0, 1) + parts[1].substring(parts[1].length()-1);
			format = String.join(".", parts);
		}
		return new MIB(Material.PAPER)
				.setName("&aCash Note")
				.addLores("&6Worth: $" + u.dc(amount) + (amount >= 1000 ? " ($" + format + ")" : ""))
				.addLores("&6Withdrawn By: &e" + ownerName)
				.addLores(" ")
				.addLores("&7Right-Click to Redeem")
				.setLocname("cashnote:" + amount)
				.build();				
	}

	@Override
	public boolean isItem(ItemStack item) {
		return hasLocname(item) && getLocname(item).contains("cashnote:");
	}
	
	public long getAmount(ItemStack item) {
		return Long.parseLong(getLocname(item).split(":")[1]);
	}
	
	

}
