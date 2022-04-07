package de.mcestasia.estasiamall.listener;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.ShopModel;
import de.mcestasia.estasiamall.setup.SetupStep;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SetupListener implements Listener {

    private final EstasiaMallBukkitPlugin plugin;

    public SetupListener() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @EventHandler
    public void handlePlayerChat(AsyncPlayerChatEvent event) {
        if (!(this.plugin.getSetupHandler().isInSetup(event.getPlayer()))) return;
        if (!(this.plugin.getSetupHandler().getSetupStep(event.getPlayer()) == SetupStep.FOURTH_STEP)) return;
        final Player player = event.getPlayer();
        this.plugin.getSetupHandler().getShopCache(player).setHologramLocation(player.getLocation());
        this.plugin.getSetupHandler().finishSetup(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerBreak(BlockBreakEvent event) {
        if (!(this.plugin.getSetupHandler().isInSetup(event.getPlayer()))) return;

        final Player player = event.getPlayer();

        SetupStep setupStep = this.plugin.getSetupHandler().getSetupStep(player);
        ShopModel shopModel = this.plugin.getSetupHandler().getShopCache(player);

        switch (setupStep) {
            case FIRST_STEP -> {
                shopModel.setLowerBoundary(event.getBlock().getLocation());
                this.plugin.getSetupHandler().getNewSetupStep(player);
            }
            case SECOND_STEP -> {
                shopModel.setHigherBoundary(event.getBlock().getLocation());
                this.plugin.getSetupHandler().getNewSetupStep(player);
            }
            case THIRD_STEP -> {
                shopModel.setSignLocation(event.getBlock().getLocation());
                this.plugin.getSetupHandler().getNewSetupStep(player);
            }
        }


        event.setCancelled(true);
    }

}
