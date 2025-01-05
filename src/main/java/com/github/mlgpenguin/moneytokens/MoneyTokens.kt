package com.github.mlgpenguin.moneytokens

import com.github.supergluelib.command.LampManager.registerCommands
import com.github.supergluelib.foundation.Foundations
import org.bukkit.plugin.java.JavaPlugin

class MoneyTokens: JavaPlugin() {

    override fun onEnable() {
        Foundations.setup(this).registerCommands(Commands())
    }

}