# SuperMoneyTokens
SuperMoneyTokens is a plugin that allows you to give out redeemable money, integrating with almost all economy plugins automatically.

### Money is given out in three ways:
- A simple token: right click to redeem <br>
<img src="https://cdn.modrinth.com/data/cached_images/fd2d369c7309e4c01992bab9a871ea98c8bc127d.png">

- A Player-generated bank note with /withdraw <br>
<img src="https://cdn.modrinth.com/data/cached_images/4583377d9a3ad57933f756aef246984ddb06c0dd.png">

- A Coin Vault which contains a random amount of money and plays an animation <br>
<img src="https://cdn.modrinth.com/data/cached_images/e15fb0ab4eda2b035b6df6eb0ed67600f153c830.gif">

# Commands and Permissions
### Players:
**/withdraw** <amount> - moneytokens.withdraw 
<br>Players can withdraw a bank note from their balance.

### Admins:
**/moneytoken** (/mt) give <player> <money> - moneytokens.admin.give
<br>Give a player a redeemable money token.

**/coinvault** (/cv) give <player> <tier> - moneytokens.admin.give
<br>Give a player a coin vault containing random amounts of money


# Configuration
The plugin can be configured using the `config.yml` file that will be located in your plugins/SuperMoneyTokens folder. This file allows you to customize various aspects of the money tokens and coin vaults, such as names, lore, and tier and level-specific settings.

<details>
<summary>Example config.yml</summary>

```yaml
coinvault-name: "&a&nCoin Vault&7 (Tier %tier%)"
coinvault-levels:
  1:
    # All of these settings are optional except for 'min' and 'max'
    coinvault-name: "&a&nCoin Vault&7 (Tier %tier%)"
    min: 25_000
    max: 100_000
    max-commands: 1
    # You can add as many commands as you like, 
    # or remove the section entirely to ignore them.
    commands:
      1:
        command: give %player% diamond 1
        chance: 50
  2:
    min: 100_000
    max: 500_000
  3:
    min: 500_000
    max: 2_500_000
  4:
    min: 2_500_000
    max: 10_000_000
  5:
    min: 10_000_000
    max: 50_000_000

money-token:
  # %amount% is a placeholder for the value of the token; (1,000)
  name: "&a$%amount%"
  lore: ["&7Right-Click to redeem &a$%amount%"]
```
</details>

# Bugs and Suggestions
If you require support or would like to make a suggestion for the plugin, please join the [Discord](https://discord.gg/cAtj5Ue2mC) where support will be provided.