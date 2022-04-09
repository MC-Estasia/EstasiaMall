package de.mcestasia.estasiamall.command;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static de.mcestasia.estasiamall.util.Constants.NO_PERMISSION_MESSAGE;

public class ShopAdminBuildCommand implements CommandExecutor {

    private EstasiaMallBukkitPlugin plugin;

    public ShopAdminBuildCommand() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof final Player player)) {
            commandSender.sendMessage("Diesen Befehl kann nur ein Spieler ausführen!");
            return false;
        }

        if (!(player.hasPermission("de.mcestasia.estasiamall.command.shopadminbuild"))) {
            player.sendMessage(NO_PERMISSION_MESSAGE);
            return false;
        }

        if(!(this.plugin.getBuildManager().canBuild(player))) {
            this.plugin.getBuildManager().addPlayerToBuild(player);
            return true;
        }

        this.plugin.getBuildManager().removeFromBuild(player);

        return true;
    }

}
