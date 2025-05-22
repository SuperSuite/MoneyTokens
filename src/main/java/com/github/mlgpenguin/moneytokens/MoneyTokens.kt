package com.github.mlgpenguin.moneytokens

import com.github.mlgpenguin.moneytokens.items.CashNote
import com.github.mlgpenguin.moneytokens.items.CoinVault
import com.github.mlgpenguin.moneytokens.items.MoneyToken
import com.github.supergluelib.command.LampManager.registerCommands
import com.github.supergluelib.command.LampManager.setupCommands
import com.github.supergluelib.customitem.SuperItems.registerItems
import com.github.supergluelib.foundation.Foundations
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MoneyTokens: JavaPlugin() {

    companion object {
        lateinit var instance: MoneyTokens
            private set
    }

    lateinit var coinVaultConfig: CoinVaultConfig private set

    override fun onEnable() {
        instance = this

        saveDefaultConfig()
        loadConfigs()

        Foundations.setup(this)
            .registerItems(MoneyToken(1), CashNote(1, ""), CoinVault(1))
            .setupCommands {
                it.suggestionProviders { providers ->
                    providers.addProvider(Player::class.java) { _ -> Bukkit.getOnlinePlayers().map { it.name } }
                }
            }
            .registerCommands(Commands(this))

    }

    override fun onDisable() {
        Foundations.onDisable()
    }

    override fun reloadConfig() {
        super.reloadConfig()
        loadConfigs()
    }

    private fun loadConfigs() {
        val levels = config.getConfigurationSection("coinvault-levels")?.getKeys(false)?.associate {
            it.toInt() to CoinVaultLevel(
                config.getInt("coinvault-levels.$it.min", 0)..config.getInt("coinvault-levels.$it.max", 0),
                config.getString("coinvault-levels.$it.coinvault-name", null),
                config.getInt("coinvault-levels.$it.max-commands", 1),
                config.getConfigurationSection("coinvault-levels.$it.commands")?.getKeys(false)?.map { cmd ->
                    val path = "coinvault-levels.$it.commands.$cmd"
                    CoinVaultCommand(config.getString("$path.command", "")!!, config.getDouble("$path.chance", 100.0))
                }?.filter { it.cmd.isNotEmpty() } ?: listOf()
            )
        } ?: mapOf()

        coinVaultConfig = CoinVaultConfig(
            config.getString("coinvault-name", "&a&nCoin Vault&7 (Tier %tier%)")!!,
            levels
        )
    }

}