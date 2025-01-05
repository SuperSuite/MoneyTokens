package com.github.mlgpenguin.moneytokens.items

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

class CashNote(val amount: Int, val withdrawnBy: String): CustomItem() {

    override fun fromItemStack(item: ItemStack, meta: ItemMeta, id: String?) = id!!.split(":").let { CashNote(it[1].toInt(), it[2]) }
    override fun isItem(item: ItemStack, meta: ItemMeta, id: String?) = id!!.startsWith("cashnote:")
    override fun getItem() = ItemBuilder(Material.PAPER, "&aCash Note")
        .addLore("&7Worth: &a$$amount", "&7Withdrawn By: &a$withdrawnBy", "", "&7Right-Click to redeem")
        .identifier("cashnote:$amount:$withdrawnBy")
        .build()

    override fun onRightClickAir(player: Player, item: ItemStack, event: PlayerInteractEvent) {
        Hooks.economy?.deposit(player, amount)
            ?: throw NullPointerException("Economy is null! ${player.name} attempted to redeem a cash note but failed")

        item.removeOne()
        player.send("&7Redeemed &a$${amount.format()}")
    }

}