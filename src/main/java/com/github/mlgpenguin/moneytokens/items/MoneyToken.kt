package com.github.mlgpenguin.moneytokens.items

import com.github.mlgpenguin.moneytokens.MoneyTokens
import com.github.supergluelib.customitem.CustomItem
import com.github.supergluelib.foundation.extensions.format
import com.github.supergluelib.foundation.extensions.removeOne
import com.github.supergluelib.foundation.extensions.send
import com.github.supergluelib.foundation.util.ItemBuilder
import com.github.supergluelib.hooks.Hooks
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class MoneyToken(val amount: Int): CustomItem() {

    private val format = amount.format()
    private val config get() = MoneyTokens.instance.coinVaultConfig

    private fun String.fill() = this.replace("%amount%", format)

    override fun fromItemStack(item: ItemStack, meta: ItemMeta, id: String?) = MoneyToken(id!!.substringAfter(":").toInt())
    override fun isItem(item: ItemStack, meta: ItemMeta, id: String?) = id?.startsWith("token:") == true
    override fun getItem() = ItemBuilder(Material.SUNFLOWER, config.moneyTokens.name.fill())
        .lore(config.moneyTokens.lore.map { it.fill() })
        .identifier("token:$amount")
        .build()

    override fun onRightClickAir(player: Player, item: ItemStack, event: PlayerInteractEvent) {
        Hooks.economy?.deposit(player, amount)
            ?: throw NullPointerException("Economy is null! ${player.name} attempted to redeem a money token but failed")

        item.removeOne()
        player.send("&7Received &a$$format")
    }
}