package de.mcestasia.estasiamall.inventory.admin;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.shop.ShopModel;
import de.mcestasia.estasiamall.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopConfirmationInventory {

    private final EstasiaMallBukkitPlugin plugin;
    private final Player player;
    private final ShopModel shopModel;

    public ShopConfirmationInventory(Player player, ShopModel shopModel) {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.player = player;
        this.shopModel = shopModel;
    }

    public void open() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§7Bist du dir §a§lsicher§7?");

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemFactory(Material.BLACK_STAINED_GLASS_PANE, 1).setName("§0").build());
        }

        for (int i = inventory.getSize() - 9; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemFactory(Material.BLACK_STAINED_GLASS_PANE, 1).setName("§0").build());
        }

        for (int i = 9; i < 13; i++) {
            inventory.setItem(i, new ItemFactory(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setName("§a§lAkzeptieren")
                    .build());
        }

        for (int i = 14; i < 18; i++) {
            inventory.setItem(i, new ItemFactory(Material.RED_STAINED_GLASS_PANE, 1)
                    .setName("§4§lAbbrechen")
                    .build());
        }
        
        inventory.setItem(13, new ItemFactory(Material.NETHER_STAR, 1)
                .setName("§4§lACHTUNG")
                .setLore("§7",
                        "§7- Diese §cAktion §7ist",
                        "§7- §4§lirreversibel§7!",
                        "§a",
                        "§7- Überlege lieber §4zweimal",
                        "§7- ob du das §cwirklich §7ausführen willst!",
                        "§7")
                .build());

        player.openInventory(inventory);
    }

}
