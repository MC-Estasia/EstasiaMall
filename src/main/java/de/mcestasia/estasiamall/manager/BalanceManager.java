package de.mcestasia.estasiamall.manager;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class BalanceManager {

    private final EstasiaMallBukkitPlugin plugin;

    public BalanceManager() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    public boolean removeMoney(Player player, double amount) {
        final EconomyResponse response = this.plugin.getEconomy().withdrawPlayer(player, amount);
        return response.transactionSuccess();
    }

    public Double getBalance(Player player) {
        return this.plugin.getEconomy().getBalance(player);
    }

    public boolean hasBalance(Player player, double amount) {
        return this.getBalance(player) >= amount;
    }

}
