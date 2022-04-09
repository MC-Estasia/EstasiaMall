package de.mcestasia.estasiamall.command;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.animation.BodyAnimation;
import de.mcestasia.estasiamall.util.HelperMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static de.mcestasia.estasiamall.util.Constants.NO_PERMISSION_MESSAGE;

public class AnimationFlexCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof final Player player)) {
            commandSender.sendMessage("Diesen Befehl kann nur ein Spieler ausführen!");
            return false;
        }

        if (!(player.hasPermission("de.mcestasia.estasiamall.command.animationflex"))) {
            player.sendMessage(NO_PERMISSION_MESSAGE);
            return false;
        }

        this.playMore(player, 6 * 2);

        EstasiaMallBukkitPlugin.instance.getServer().getOnlinePlayers().forEach(onlinePlayer -> {
            HelperMethods.sendCentredMessage(onlinePlayer, "§7§m-----------------------------------------------------");
            HelperMethods.sendCentredMessage(onlinePlayer, "§5§l" + player.getName() + " §7ist gerade dabei eine der §dbesten §7Animationen zu zeigen!");
            HelperMethods.sendCentredMessage(onlinePlayer, "§7§m-----------------------------------------------------");
        });

        return true;
    }

    private void playMore(Player player, int times){
        new BukkitRunnable(){
            int i = 0;

            @Override
            public void run() {
                i++;

                new BodyAnimation(player, -.85);
                new BodyAnimation(player, 0);
                new BodyAnimation(player, .85);

                if(i >= times){
                    this.cancel();
                }
            }

        }.runTaskTimer(EstasiaMallBukkitPlugin.instance, 0, 5);
    }

}
