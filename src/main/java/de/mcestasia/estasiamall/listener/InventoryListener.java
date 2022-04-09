package de.mcestasia.estasiamall.listener;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.inventory.ShopListInventory;
import de.mcestasia.estasiamall.inventory.admin.ShopAdminOverviewInventory;
import de.mcestasia.estasiamall.inventory.admin.ShopConfirmationInventory;
import de.mcestasia.estasiamall.model.manipulation.ManipulationModel;
import de.mcestasia.estasiamall.model.manipulation.ManipulationType;
import de.mcestasia.estasiamall.model.shop.ShopModel;
import de.mcestasia.estasiamall.util.HelperMethods;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static de.mcestasia.estasiamall.util.Constants.PREFIX;
import static de.mcestasia.estasiamall.util.Constants.SHOP_ID_KEY;

public class InventoryListener implements Listener {

    private final EstasiaMallBukkitPlugin plugin;

    public InventoryListener() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @EventHandler
    public void handleListInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equals("§a§lEstasiaMall §7- §2§lShops")) || event.getCurrentItem() == null || !(event.getCurrentItem().hasItemMeta()))
            return;
        Player player = (Player) event.getWhoClicked();
        final ItemStack clicked = event.getCurrentItem();
        ShopModel shop = this.plugin.getShopConfigurationManager().getModelByUUID(clicked.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(this.plugin, SHOP_ID_KEY), PersistentDataType.STRING));
        new ShopAdminOverviewInventory(player, shop).open();
        event.setCancelled(true);
    }

    @EventHandler
    public void handleConfirmationInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equals("§7Bist du dir §a§lsicher§7?")) || event.getCurrentItem() == null || !(event.getCurrentItem().hasItemMeta()))
            return;
        Player player = (Player) event.getWhoClicked();
        final ItemStack clicked = event.getCurrentItem();
        ManipulationModel model = this.plugin.getManipulationManager().getCurrentManipulationType(player).get();
        ShopModel shopModel = model.getShopModel();

        if (clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
            shopModel.setOwnerUUID(null);
            shopModel.setPermitted(HelperMethods.convertStringListToUUIDList(Arrays.asList("00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000")));
            shopModel.save();
            player.sendMessage(PREFIX + "Der Shop wurde §aerfolgreich §7zurückgesetzt§7.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1f, 1f);

            this.plugin.getManipulationManager().removeManipulation(model);
            this.plugin.getShopManager().refreshArmorStand(shopModel);
            this.plugin.getShopManager().refreshSign(shopModel);

            event.setCancelled(true);
            event.getView().close();
            return;
        }

        if (clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
            player.sendMessage(PREFIX + "Der Shop wurde §cnicht §7zurückgesetzt§7.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1f, 1f);
            event.setCancelled(true);
            event.getView().close();
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void handleAdminManagementInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equals("§5§lShop §7- §4§lVerwaltung")) || event.getCurrentItem() == null || !(event.getCurrentItem().hasItemMeta()))
            return;
        Player player = (Player) event.getWhoClicked();
        final ItemStack clicked = event.getCurrentItem();
        ShopModel shop = this.plugin.getShopConfigurationManager().getModelByUUID(clicked.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(this.plugin, SHOP_ID_KEY), PersistentDataType.STRING));

        if (clicked.getType().equals(Material.ANVIL)) {
            if (this.plugin.getManipulationManager().isInManipulation(player)) {
                Optional<ManipulationModel> optionalManipulationModel = this.plugin.getManipulationManager().getCurrentManipulationType(player);
                if (optionalManipulationModel.isEmpty()) {
                    event.setCancelled(true);
                    return;
                }
                ManipulationModel manipulationModel = optionalManipulationModel.get();
                player.sendMessage(PREFIX + "Im Moment bearbeitest du §cbereits §7einen Shop.");
                player.sendMessage("");
                player.sendMessage(PREFIX + "Shop-UUID: §d" + manipulationModel.getShopModel().getShopId());
                player.sendMessage(PREFIX + "Deine Aufgabe: §5" + manipulationModel.getManipulationType().getCommand());
                player.sendMessage("");
                event.setCancelled(true);
                event.getView().close();
                return;
            }
            event.getView().close();
            player.sendMessage(PREFIX + "Bitte gib nun die §4§lUUID §7des §c§lneuen §7Shop-Inhabers ein!");
            player.sendMessage(PREFIX + "Solltest du diese nicht haben, so gehe auf §b https://namemc.com§7, gib den Namen des §c§lneuen §7Besitzers ein und kopiere von dort die UUID.");
            this.plugin.getManipulationManager().addManipulation(new ManipulationModel(player, ManipulationType.OWNER, shop));
            event.setCancelled(true);
            return;
        }

        if (clicked.getItemMeta().getDisplayName().equals("§cZurück")) {
            event.getView().close();
            new ShopListInventory(player).open();
            event.setCancelled(true);
            return;
        }

        if (clicked.getType().equals(Material.LAVA_BUCKET)) {
            new ShopConfirmationInventory(player, shop).open();
            this.plugin.getManipulationManager().addManipulation(new ManipulationModel(player, ManipulationType.OWNER_DELETE, shop));
            event.setCancelled(true);
            return;
        }


        event.setCancelled(true);
    }

}
