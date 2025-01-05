package me.penguin.moneytoken.Objects;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Penguin.SuperPenguinCore.Core;
import me.Penguin.SuperPenguinCore.API.PenguinEconomyAPI;
import me.Penguin.SuperPenguinCore.Items.Held.Redeemable;
import me.Penguin.SuperPenguinCore.Util.MIB;
import me.Penguin.SuperPenguinCore.Util.NumberFormatter;
import me.Penguin.SuperPenguinCore.Util.Range;
import me.Penguin.SuperPenguinCore.Util.m;
import me.Penguin.SuperPenguinCore.Util.u;
import me.penguin.moneytoken.MoneyTokens;

public class CoinPouch extends Redeemable {

	private int tier;

	public CoinPouch(int tier) {
		this.tier = tier;
	}

	@Override
	public ItemStack getItem() {
		MIB m = new MIB(Material.JUKEBOX)
				.setName("&a&nCoin Vault&7 (Tier " + tier + ")")
				.addLores("&7Right-Click to Redeem")
				.setLocname("cp:" + tier);
		if (tier >= 4) m.setGlowing(true);
		return m.build();
	}

	@Override
	public void OnRedeem(ItemStack item, Player player) {
		List<Player> inAnimation = MoneyTokens.getInstance().getInAnimation();
		if (inAnimation.contains(player)) {
			player.sendMessage(m.msg("&7Wait for your current coin vault to finish opening"));
			return;
		}
		int tier = getTier(item);		
		Range level = MoneyTokens.getInstance().getCoinVaultLevels().get(tier);
		boolean sneaking = player.isSneaking();
		final int amount = sneaking ? new Range(level.getMin() * item.getAmount(), level.getMax() * item.getAmount()).getRandom() : level.getRandom();		
		String view = (tier>3?"&6":"&f")+ "$" + NumberFormatter.addDelimiters(amount);		
		inAnimation.add(player);
		new BukkitRunnable() {			
			int pointer = 2;
			@Override
			public void run() {
				if (pointer == view.length()) {
					cancel();
					PenguinEconomyAPI.deposit(player, amount);
					player.sendMessage(u.cc("&7Received &a$" + u.dc(amount)));
					inAnimation.remove(player);
				}
				String title = u.cc(view.substring(0, pointer) + "&k" +  view.substring(pointer));
				player.sendTitle(title, "", 0, 60, 10);
				pointer++;
			}
		}.runTaskTimer(Core.getPlugin(Core.class), 0, 20);	
		item.setAmount(sneaking ? 0 : item.getAmount()-1);
	}

	@Override
	public boolean isItem(ItemStack item) {
		return getLocname(item).startsWith("cp:");
	}

	private int getTier(ItemStack item) {
		return Integer.parseInt(getLocname(item).split(":")[1]);
	}

}
