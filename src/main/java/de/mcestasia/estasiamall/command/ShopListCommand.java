package de.mcestasia.estasiamall.command;

import de.mcestasia.estasiamall.inventory.ShopListInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.mcestasia.estasiamall.util.Constants.NO_PERMISSION_MESSAGE;
import static de.mcestasia.estasiamall.util.Constants.PREFIX;

public class ShopListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof final Player player)) {
            commandSender.sendMessage("Diesen Befehl kann nur ein Spieler ausführen!");
            return false;
        }

        if (!(player.hasPermission("de.mcestasia.estasiamall.command.shoplist"))) {
            player.sendMessage(NO_PERMISSION_MESSAGE);
            return false;
        }

        player.sendMessage(PREFIX + "Bitte §cgedulde §7dich einen Moment, das Shop Listen Inventar wird vorbereitet!");
        new ShopListInventory(player).open();

        return true;
    }

}
