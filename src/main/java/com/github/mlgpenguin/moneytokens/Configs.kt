package com.github.mlgpenguin.moneytokens

data class CoinVaultConfig(
    val coinVaultName: String,
    val coinVaultLevels: Map<Int, CoinVaultLevel>
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