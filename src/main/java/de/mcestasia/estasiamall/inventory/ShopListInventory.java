package de.mcestasia.estasiamall.inventory;

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

public class ShopListInventory {

    private final EstasiaMallBukkitPlugin plugin;
    private final Player player;

    public ShopListInventory(Player player) {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.player = player;
    }

    public void open() {
        Inventory shopInventory = Bukkit.createInventory(null, 9 * 6, "§a§lEstasiaMall §7- §2§lShops");


        for (int i = this.plugin.getShopConfigurationManager().getRegisteredShopList().size(); i < shopInventory.getSize(); i++) {
            shopInventory.setItem(i, new ItemFactory(Material.LIME_STAINED_GLASS_PANE, 1).setName("§0").build());
        }

        for (ShopModel shopModel : this.plugin.getShopConfigurationManager().getRegisteredShopList()) {
            shopInventory.addItem(new ItemFactory(Material.PAPER, 1)
                    .setName("§7» §dShop")
                    .setLore(this.getLoreList(shopModel))
                    .setPersistentDataString(SHOP_ID_KEY, shopModel.getShopId().toString())
                    .build());
        }

        if (this.player.hasPermission("de.mcestasia.estasiamall.admininventory"))
            this.player.openInventory(shopInventory);
    }

    private List<String> getLoreList(ShopModel shopModel){
        if(!(this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString()))){
            return Arrays.asList(
                    "§7- Die §2UUID §7des Shops ist",
                    "§7- §e" + shopModel.getShopId(),
                    "§a",
                    "§7- Der Shop hat im §cMoment",
                    "§7- §4keinen §7Inhaber",
                    "§b",
                    "§7- Um mehr §5Details §7zu sehen",
                    "§7- §dklicke §7auf dieses Item!",
                    "§7");
        }
        return Arrays.asList(
                "§7",
                "§7- Die §2UUID §7des Shops ist",
                "§7- §e" + shopModel.getShopId(),
                "§a",
                "§7- Der §4Inhaber §7heißt",
                "§7- " + (HelperMethods.getName(shopModel.getOwnerUUID().toString()) == null ? "§c§lKein Inhaber" : "§c" + HelperMethods.getName(shopModel.getOwnerUUID().toString())),
                "§b",
                "§7- Um mehr §5Details §7zu sehen",
                "§7- §dklicke §7auf dieses Item!",
                "§7"
        );
    }

}
