package com.github.mlgpenguin.moneytokens

import com.github.mlgpenguin.moneytokens.items.CashNote
import com.github.mlgpenguin.moneytokens.items.MoneyToken
import com.github.supergluelib.foundation.extensions.format
import com.github.supergluelib.foundation.extensions.giveOrDropItem
import com.github.supergluelib.foundation.extensions.send
import com.github.supergluelib.hooks.Hooks
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.annotation.CommandPermission

class Commands {

    private val eco = Hooks.economy ?: throw NullPointerException("Economy provider cannot be found, all operations will fail!")

    @Command("moneytoken give", "mt give")
    @CommandPermission("moneytokens.admin.give")
    fun moneytokenGiveCommand(sender: CommandSender, target: Player, amount: Int) {
        target.giveOrDropItem(MoneyToken(amount).getItem())
        sender.send("&7Gave &a${target.name}&7 a Money Token worth &a$${amount.format()}")
    }

    @Command("withdraw")
    @CommandPermission("moneytokens.withdraw")
    fun withdrawCmd(sender: Player, amount: Int) {
        if (eco.withdrawIfPossible(sender, amount)) {
            sender.giveOrDropItem(CashNote(amount, sender.name).getItem())
            sender.send("&7Withdrawn &a$${amount.format()}")
        } else sender.send("&7You do not have enough money")
    }

}