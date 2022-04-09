package de.mcestasia.estasiamall.listener;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.manipulation.ManipulationModel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

import static de.mcestasia.estasiamall.util.Constants.shopAdminList;

public class PlayerConnectionListener implements Listener {

    private final EstasiaMallBukkitPlugin plugin;

    public PlayerConnectionListener() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        shopAdminList.remove(event.getPlayer());
        this.plugin.getSetupHandler().getPlayerSetupMap().remove(event.getPlayer());
        this.plugin.getSetupHandler().getPlayerShopCache().remove(event.getPlayer());
        Optional<ManipulationModel> optionalManipulationModel = this.plugin.getManipulationManager().getCurrentManipulationType(event.getPlayer());
        if (optionalManipulationModel.isEmpty()) {
            return;
        }
        ManipulationModel manipulationModel = optionalManipulationModel.get();
        this.plugin.getManipulationManager().removeManipulation(manipulationModel);
    }

    @EventHandler
    public void handlePlayerKick(PlayerKickEvent event) {
        shopAdminList.remove(event.getPlayer());
        this.plugin.getSetupHandler().getPlayerSetupMap().remove(event.getPlayer());
        this.plugin.getSetupHandler().getPlayerShopCache().remove(event.getPlayer());
        Optional<ManipulationModel> optionalManipulationModel = this.plugin.getManipulationManager().getCurrentManipulationType(event.getPlayer());
        if (optionalManipulationModel.isEmpty()) {
            return;
        }
        ManipulationModel manipulationModel = optionalManipulationModel.get();
        this.plugin.getManipulationManager().removeManipulation(manipulationModel);
    }

}
