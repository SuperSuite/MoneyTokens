package com.github.mlgpenguin.moneytokens.items

import com.github.supergluelib.customitem.CustomItem
import com.github.supergluelib.foundation.Runnables
import com.github.supergluelib.foundation.extensions.format
import com.github.supergluelib.foundation.extensions.send
import com.github.supergluelib.foundation.extensions.toColor
import com.github.supergluelib.foundation.util.ItemBuilder
import com.github.supergluelib.hooks.Hooks
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

class CoinVault(val tier: Int): CustomItem() {

    override fun fromItemStack(
        item: ItemStack,
        meta: ItemMeta,
        id: String?
    ) = CoinVault(id!!.substringAfter(":").toInt())

    override fun getItem(): ItemStack = ItemBuilder(Material.JUKEBOX, "&a&nCoin Vault&7 (Tier $tier)")
        .addLore("&7Right-Click to Redeem")
        .identifier("coinvault:$tier")
        .apply { if (tier > 4) glowing(true) }
        .build()

    override fun isItem(
        item: ItemStack,
        meta: ItemMeta,
        id: String?
    ) = id?.startsWith("coinvault:") == true

    companion object {
        private val inAnimation: HashSet<UUID> = hashSetOf()

        private val ranges = mapOf<Int, IntRange>(
            1 to 25_000..100_000,
            2 to 100_000..500_000,
            3 to 500_000..2_500_000,
            4 to 2_500_000..10_000_000,
            5 to 10_000_000..50_000_000,
        )
    }

    private operator fun IntRange.times(multiplier: Int): IntRange = (start * multiplier)..(endInclusive * multiplier)

    override fun onRightClickAir(player: Player, item: ItemStack, event: PlayerInteractEvent) {
        val eco = Hooks.economy ?: throw NullPointerException("Economy is null! ${player.name} attempted to redeem a coin vault but failed")

        if (player.uniqueId in inAnimation) return player.send("&7Please wait for your current Coin Vault to open.")

        val multiplier = if (player.isSneaking) item.amount else 1
        val range = if (multiplier == 1) ranges[tier]!! else ranges[tier]!! * multiplier
        val prefix = if (tier>3) "&6$" else "&f$"

        inAnimation.add(player.uniqueId)

        item.amount -= multiplier

        var executions = 0
        var amount = 0
        Runnables.runTimerWithClass(4, 0) {
            amount = range.random()
            player.sendTitle("$prefix${amount.format()}".toColor(), "", 0, 60, 10)

            if (executions++ >= 12) { // Finished
                it.cancel()
                eco.deposit(player, amount)
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f)
                player.send("&7Received &a$${amount.format()}");
                inAnimation.remove(player.uniqueId);
            }
        }
    }

}