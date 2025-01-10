package com.github.mlgpenguin.moneytokens

import com.github.mlgpenguin.moneytokens.items.CashNote
import com.github.mlgpenguin.moneytokens.items.CoinVault
import com.github.mlgpenguin.moneytokens.items.MoneyToken
import com.github.supergluelib.foundation.extensions.format
import com.github.supergluelib.foundation.extensions.giveOrDropItem
import com.github.supergluelib.foundation.extensions.send
import com.github.supergluelib.hooks.Hooks
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Suggest
import revxrsal.commands.annotation.SuggestWith
import revxrsal.commands.autocomplete.SuggestionProvider
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.node.ExecutionContext

class Commands(val plugin: MoneyTokens) {

    private val eco = Hooks.economy ?: throw NullPointerException("Economy provider cannot be found, all operations will fail!")

    @Command("monkeytoken reload", "mt reload")
    @CommandPermission("moneytokens.admin.reload")
    fun reloadMoneyTokens(sender: CommandSender) {
        plugin.reloadConfig()
        sender.send("&7Reloaded the MoneyToken config.")
    }

    @Command("moneytoken give", "mt give")
    @CommandPermission("moneytokens.admin.give")
    fun moneytokenGiveCommand(sender: CommandSender, target: Player, @Suggest("10") amount: Int) {
        target.giveOrDropItem(MoneyToken(amount).getItem())
        sender.send("&7Gave &a${target.name}&7 a Money Token worth &a$${amount.format()}")
    }

    @Command("withdraw")
    @CommandPermission("moneytokens.withdraw")
    fun withdrawCmd(sender: Player, @Suggest("10") amount: Int) {
        if (eco.withdrawIfPossible(sender, amount)) {
            sender.giveOrDropItem(CashNote(amount, sender.name).getItem())
            sender.send("&7Withdrawn &a$${amount.format()}")
        } else sender.send("&7You do not have enough money")
    }

    @Command("coinvault give", "cv give")
    @CommandPermission("moneytokens.admin.give")
    fun coinvaultGiveCmd(sender: CommandSender, target: Player, @SuggestWith(CoinVaultProvider::class) level: Int) {
        if (level !in plugin.coinvaultRanges.keys)
            return sender.send("&c$level is not a valid CoinVault. Valid levels are: [${plugin.coinvaultRanges.keys.joinToString()}]")

        target.giveOrDropItem(CoinVault(level).getItem())
        sender.send("&7Gave &a${target.name}&7 a level &a$level&7 Coin Vault")
    }

    class CoinVaultProvider(): SuggestionProvider<BukkitCommandActor> {
        override fun getSuggestions(ex: ExecutionContext<BukkitCommandActor?>): Collection<String?> {
            return MoneyTokens.instance.coinvaultRanges.keys.map(Int::toString)
        }
    }

}