package com.github.mlgpenguin.moneytokens

data class CoinVaultConfig(
    val coinVaultName: String,
    val coinVaultLevels: Map<Int, CoinVaultLevel>,
    val moneyTokens: MoneyTokenConfig
)

data class CoinVaultLevel(
    val range: IntRange,
    val levelName: String? = null,
    val maxCommands: Int = 1,
    val commands: List<CoinVaultCommand> = listOf()
)

data class CoinVaultCommand(
    val cmd: String,
    val chance: Double
)

data class MoneyTokenConfig(
    val name: String,
    val lore: List<String>
)