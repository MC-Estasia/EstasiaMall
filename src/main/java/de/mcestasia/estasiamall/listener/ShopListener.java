package de.mcestasia.estasiamall.listener;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.ShopModel;
import de.mcestasia.estasiamall.util.HelperMethods;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static de.mcestasia.estasiamall.util.Constants.shopAdminList;

public class ShopListener implements Listener {

    private final EstasiaMallBukkitPlugin plugin;

    public ShopListener() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handleBreak(BlockBreakEvent event) {
        if (!(event.getBlock().getWorld().equals(this.plugin.getShopConfigurationManager().getMallWorld()))) return;
        if ((HelperMethods.isInShop(event.getBlock().getLocation()))) return;
        event.setCancelled(!(shopAdminList.contains(event.getPlayer())));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handlePlace(BlockPlaceEvent event) {
        if (!(event.getBlock().getWorld().equals(this.plugin.getShopConfigurationManager().getMallWorld()))) return;
        if ((HelperMethods.isInShop(event.getBlock().getLocation()))) return;
        event.setCancelled(!(shopAdminList.contains(event.getPlayer())));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void handleShopBreak(BlockBreakEvent event) {
        if (!(event.getBlock().getWorld().equals(this.plugin.getShopConfigurationManager().getMallWorld()))) return;
        if (!(HelperMethods.isInShop(event.getBlock().getLocation()))) return;
        final Player player = event.getPlayer();
        final ShopModel shopModel = HelperMethods.getShopByLocation(event.getBlock().getLocation());

        boolean cancel = true;

        if (shopModel.getOwnerUUID().equals(player.getUniqueId())) {
            cancel = false;
        } else if (shopModel.getPermitted().stream().anyMatch(uuid -> uuid.equals(player.getUniqueId()))) {
            cancel = false;
        } else if (shopAdminList.contains(player)) {
            cancel = false;
        }

        event.setCancelled(cancel);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void handleShopPlace(BlockPlaceEvent event) {
        if (!(event.getBlock().getWorld().equals(this.plugin.getShopConfigurationManager().getMallWorld()))) return;
        if (!(HelperMethods.isInShop(event.getBlock().getLocation()))) return;
        final Player player = event.getPlayer();
        final ShopModel shopModel = HelperMethods.getShopByLocation(event.getBlock().getLocation());

        boolean cancel = true;

        if (shopModel.getOwnerUUID().equals(player.getUniqueId())) {
            cancel = false;
        } else if (shopModel.getPermitted().stream().anyMatch(uuid -> uuid.equals(player.getUniqueId()))) {
            cancel = false;
        } else if (shopAdminList.contains(player)) {
            cancel = false;
        }

        event.setCancelled(cancel);
    }

    @EventHandler
    public void handlePlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || !(event.getClickedBlock().getWorld().equals(this.plugin.getShopConfigurationManager().getMallWorld())))
            return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;
        if (!(this.plugin.getShopManager().isSignFromShop(event.getClickedBlock()))) return;
        ShopModel shopModel = this.plugin.getShopManager().getShopFromSign(event.getClickedBlock());
        this.plugin.getShopManager().buyShop(shopModel, event.getPlayer());
        event.setCancelled(true);
    }

}
