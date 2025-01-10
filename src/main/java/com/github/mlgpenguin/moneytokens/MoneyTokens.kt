package com.github.mlgpenguin.moneytokens

import com.github.mlgpenguin.moneytokens.items.CashNote
import com.github.mlgpenguin.moneytokens.items.CoinVault
import com.github.mlgpenguin.moneytokens.items.MoneyToken
import com.github.supergluelib.command.LampManager.registerCommands
import com.github.supergluelib.customitem.SuperItems.registerItems
import com.github.supergluelib.foundation.Foundations
import org.bukkit.plugin.java.JavaPlugin

class MoneyTokens: JavaPlugin() {

    companion object {
        lateinit var instance: MoneyTokens
            private set
    }

    lateinit var coinvaultRanges: Map<Int, IntRange>
        private set

    override fun onEnable() {
        instance = this

        Foundations.setup(this)
            .registerItems(MoneyToken(1), CashNote(1, ""), CoinVault(1))
            .registerCommands(Commands(this))

        saveDefaultConfig()

        coinvaultRanges = getRanges()
    }

    override fun onDisable() {
        Foundations.onDisable()
    }

    override fun reloadConfig() {
        super.reloadConfig()
        coinvaultRanges = getRanges()
    }

    private fun getRanges() = config.getConfigurationSection("coinvault-levels")?.getKeys(false)?.associate {
        it.toInt() to config.getInt("coinvault-levels.$it.min", 0)..config.getInt("coinvault-levels.$it.max", 0)
    } ?: mapOf()

}