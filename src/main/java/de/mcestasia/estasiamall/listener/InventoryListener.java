package de.mcestasia.estasiamall.listener;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.ShopModel;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static de.mcestasia.estasiamall.util.Constants.SHOP_ID_KEY;

public class InventoryListener implements Listener {

    private final EstasiaMallBukkitPlugin plugin;

    public InventoryListener() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equals("§a§lEstasiaMall §7- §2§lShops")) || event.getCurrentItem() == null || !(event.getCurrentItem().hasItemMeta()))
            return;
        Player player = (Player) event.getWhoClicked();
        final ItemStack clicked = event.getCurrentItem();
        ShopModel shop = this.plugin.getShopConfigurationManager().getModelByUUID(clicked.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(this.plugin, SHOP_ID_KEY), PersistentDataType.STRING));


        //event.setCancelled(true);
    }

}
