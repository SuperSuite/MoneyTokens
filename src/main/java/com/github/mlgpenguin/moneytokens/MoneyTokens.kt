package com.github.mlgpenguin.moneytokens

import com.github.mlgpenguin.moneytokens.items.CashNote
import com.github.mlgpenguin.moneytokens.items.CoinVault
import com.github.mlgpenguin.moneytokens.items.MoneyToken
import com.github.supergluelib.command.LampManager.registerCommands
import com.github.supergluelib.customitem.SuperItems.registerItems
import com.github.supergluelib.foundation.Foundations
import org.bukkit.plugin.java.JavaPlugin

class MoneyTokens: JavaPlugin() {

    override fun onEnable() {
        Foundations.setup(this)
            .registerItems(MoneyToken(1), CashNote(1, ""), CoinVault(1))
            .registerCommands(Commands(this))

        saveDefaultConfig()
    }

    override fun onDisable() {
        Foundations.onDisable()
    }

}