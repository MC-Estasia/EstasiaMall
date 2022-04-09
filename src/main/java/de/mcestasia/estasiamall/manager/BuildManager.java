package de.mcestasia.estasiamall.manager;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static de.mcestasia.estasiamall.util.Constants.PREFIX;
import static de.mcestasia.estasiamall.util.Constants.shopAdminList;

public class BuildManager {

    private EstasiaMallBukkitPlugin plugin;

    public BuildManager() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    public void addPlayerToBuild(Player player) {
        if (this.canBuild(player)) return;

        shopAdminList.add(player);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage(PREFIX + "Du kannst §anun §7bauen!");

        player.getInventory().clear();
    }

    public void removeFromBuild(Player player) {
        if (!(this.canBuild(player))) return;

        shopAdminList.remove(player);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1f, 1f);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(PREFIX + "Du kannst nun §cnicht §7mehr bauen!");

        player.getInventory().clear();
    }

    public boolean canBuild(Player player) {
        return shopAdminList.contains(player);
    }

}
