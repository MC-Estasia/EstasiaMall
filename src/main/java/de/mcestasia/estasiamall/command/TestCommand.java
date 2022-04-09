package de.mcestasia.estasiamall.command;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.shop.ShopModel;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TestCommand implements CommandExecutor {

    private EstasiaMallBukkitPlugin plugin;

    public TestCommand() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        final Player player = (Player) commandSender;
        ShopModel shopModel = this.plugin.getShopConfigurationManager().getRegisteredShops().get(UUID.fromString("4aab93ba-314e-497b-bf3c-2c3cb93b68d3").toString());
        shopModel.setHologramLocation(new Location(player.getWorld(), -5.5, -60, -0.5));
        shopModel.save();
        return true;
    }

}
