package de.mcestasia.estasiamall.command;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.mcestasia.estasiamall.util.Constants.*;

public class ShopSetupCommand implements CommandExecutor {

    private final EstasiaMallBukkitPlugin plugin;

    public ShopSetupCommand() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof final Player player)) {
            commandSender.sendMessage("Diesen Befehl kann nur ein Spieler ausführen!");
            return false;
        }

        if (!(player.hasPermission("de.mcestasia.estasiamall.command.shopsetup"))) {
            player.sendMessage(NO_PERMISSION_MESSAGE);
            return false;
        }

        if (this.plugin.getSetupHandler().isInSetup(player)) {
            player.sendMessage(PREFIX + "Du bist §cbereits §7in der Shop-Erstellung!");
            this.plugin.getSetupHandler().executeNewSetupStep(player, this.plugin.getSetupHandler().getSetupStep(player));
            return true;
        }

        this.plugin.getSetupHandler().getNewSetupStep(player);

        return true;
    }

}
