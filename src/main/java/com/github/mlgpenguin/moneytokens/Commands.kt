package com.github.mlgpenguin.moneytokens

import com.github.mlgpenguin.moneytokens.items.MoneyToken
import com.github.supergluelib.foundation.extensions.format
import com.github.supergluelib.foundation.extensions.giveOrDropItem
import com.github.supergluelib.foundation.extensions.send
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.annotation.CommandPermission

class Commands {

    @Command("moneytoken give", "mt give")
    @CommandPermission("moneytokens.admin")
    fun moneytokenGiveCommand(sender: CommandSender, target: Player, amount: Int) {
        target.giveOrDropItem(MoneyToken(amount).getItem())
        sender.send("&7Gave &a${target.name}&7 a Money Token worth &a$${amount.format()}")
    }

}