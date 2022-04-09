package de.mcestasia.estasiamall.inventory;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.ShopModel;
import org.bukkit.entity.Player;

public class ShopAdminOverviewInventory {

    private final EstasiaMallBukkitPlugin plugin;
    private final Player player;
    private ShopModel shopModel;

    public ShopAdminOverviewInventory(Player player, ShopModel shopModel) {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.player = player;
        this.shopModel = shopModel;
    }

    public void open() {

    }

}
