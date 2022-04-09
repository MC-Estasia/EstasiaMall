package de.mcestasia.estasiamall.animation;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.Objects;

@Getter
public class BodyAnimation {

    private final Player player;
    private BukkitTask task;

    public BodyAnimation(Player player, double multiplier) {
        this.player = player;
        this.playSingle(multiplier);
    }

    public void playSingle(double multiplier) {

        Location toSpawn = player.getLocation().add(0.25, 0, 0);
        ArmorStand armorStand = player.getWorld().spawn(toSpawn, ArmorStand.class);

        armorStand.setVisible(false);
        Objects.requireNonNull(armorStand.getEquipment()).setHelmet(new ItemStack(Material.CHEST));
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.setPersistent(false);

        this.task = new BukkitRunnable() {

            float angle = 0f;
            final double RADIUS = 1.5;

            @Override
            public void run() {

                double x = RADIUS * Math.sin(angle);
                double z = RADIUS * Math.cos(angle);

                double z_head = RADIUS * Math.sin(angle);

                angle += 0.1;
                armorStand.setHeadPose(new EulerAngle(z_head, 0, 0));
                Location location = player.getLocation().clone().add(x, multiplier, z);
                armorStand.teleport(location);

                if (angle >= 7 * Math.PI * 1.2) {
                    this.cancel();
                    destroy(armorStand);
                }
            }
        }.runTaskTimer(EstasiaMallBukkitPlugin.instance, 0, 1);
    }

    private void destroy(ArmorStand armorStand) {
        armorStand.remove();
        player.getWorld().getEntities().remove(armorStand);
    }

}
