package de.mcestasia.estasiamall.inventory.admin;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.shop.ShopModel;
import de.mcestasia.estasiamall.util.HelperMethods;
import de.mcestasia.estasiamall.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

import static de.mcestasia.estasiamall.util.Constants.SHOP_ID_KEY;

public class ShopAdminOverviewInventory {

    private final EstasiaMallBukkitPlugin plugin;
    private final Player player;
    private final ShopModel shopModel;

    public ShopAdminOverviewInventory(Player player, ShopModel shopModel) {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.player = player;
        this.shopModel = shopModel;
    }

    public void open() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§5§lShop §7- §4§lVerwaltung");

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemFactory(Material.LIME_STAINED_GLASS_PANE, 1).setName("§0").build());
        }

        inventory.setItem(4, new ItemFactory(Material.NETHER_STAR, 1).setName("§a§lInformationen")
                .setLore(this.getLoreList(this.shopModel))
                .setPersistentDataString(SHOP_ID_KEY, shopModel.getShopId().toString()).build());
        inventory.setItem(11, new ItemFactory(Material.ANVIL, 1).setName("§a§lOwner §2§ländern")
                .setLore("§7", "§7- Klicke §ehier §7um den", "§7- Inhaber des Shops zu §6ändern", "§7")
                .setPersistentDataString(SHOP_ID_KEY, shopModel.getShopId().toString()).build());
        inventory.setItem(22, new ItemFactory(Material.LAVA_BUCKET, 1).setName("§c§lOwner §4§llöschen")
                .setLore("§7", "§7- Klicke §chier §7um den", "§7- Inhaber des Shops zu §4löschen", "§7")
                .setPersistentDataString(SHOP_ID_KEY, shopModel.getShopId().toString()).build());

        inventory.setItem(26, new ItemFactory(Material.PLAYER_HEAD, 1).setName("§cZurück").skullFromURL("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY1MmUyYjkzNmNhODAyNmJkMjg2NTFkN2M5ZjI4MTlkMmU5MjM2OTc3MzRkMThkZmRiMTM1NTBmOGZkYWQ1ZiJ9fX0=").setPersistentDataString(SHOP_ID_KEY, shopModel.getShopId().toString()).build());

        player.openInventory(inventory);
    }

    private List<String> getLoreList(ShopModel shopModel) {
        if (this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString())) {
            return Arrays.asList("§7", "§7- Die §2UUID §7des Shops ist", "§7- §e" + shopModel.getShopId(), "§a", "§7- Der §4Inhaber §7heißt", "§7- " + (HelperMethods.getName(shopModel.getOwnerUUID().toString()) == null ? "§c§lKein Inhaber" : "§c" + HelperMethods.getName(shopModel.getOwnerUUID().toString())), "§b", "§7");
        }
        return Arrays.asList("§7- Die §2UUID §7des Shops ist", "§7- §e" + shopModel.getShopId(), "§a", "§7- Der Shop hat im §cMoment", "§7- §4keinen §7Inhaber", "§b", "§7");
    }

}
