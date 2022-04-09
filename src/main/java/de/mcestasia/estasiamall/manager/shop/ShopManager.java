package de.mcestasia.estasiamall.manager.shop;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.shop.ShopModel;
import de.mcestasia.estasiamall.util.HelperMethods;
import de.mcestasia.estasiamall.util.ItemFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.HashMap;

import static de.mcestasia.estasiamall.util.Constants.PREFIX;

public class ShopManager {

    private final EstasiaMallBukkitPlugin plugin;
    private final HashMap<ShopModel, ArmorStand> shopHologramMap;
    private final HashMap<ShopModel, Sign> shopSignMap;

    public ShopManager() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.shopHologramMap = new HashMap<>();
        this.shopSignMap = new HashMap<>();

        this.spawnArmorStands();
        this.setSigns();
    }

    public void spawnArmorStands() {
        for (ShopModel shopModel : this.plugin.getShopConfigurationManager().getRegisteredShopList()) {
            if (this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString())) {

                Location toSpawn = shopModel.getHologramLocation().add(0, 0.65, 0);
                ArmorStand stand = toSpawn.getWorld().spawn(toSpawn, ArmorStand.class);
                stand.setVisible(false);
                stand.setCustomName("§5" + HelperMethods.getName(shopModel.getOwnerUUID().toString()) + "'s Shop");
                stand.setCustomNameVisible(true);
                stand.getEquipment().setHelmet(new ItemFactory(Material.PLAYER_HEAD, 1).skullFromUUID(shopModel.getOwnerUUID()).build());
                stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
                stand.setPersistent(false);
                stand.setSmall(true);
                stand.setGravity(false);

                new BukkitRunnable() {

                    float rotation = 0;

                    @Override
                    public void run() {

                        rotation -= 0.05f;
                        stand.setHeadPose(new EulerAngle(0, rotation, 0));

                    }
                }.runTaskTimer(this.plugin, 3 * 20L, 1);

                this.shopHologramMap.put(shopModel, stand);
                continue;
            }

            Location toSpawn = shopModel.getHologramLocation().clone().add(0, 0.65, 0);
            ArmorStand stand = toSpawn.getWorld().spawn(toSpawn, ArmorStand.class);
            stand.setVisible(false);
            stand.setCustomName("§c§lKein §4§lInhaber");
            stand.setCustomNameVisible(true);
            stand.getEquipment().setHelmet(HelperMethods.getSkullFromURL("" + "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJhZGRhZDljYWNlYjA1ZjM0YmRjZjMyOTM3Yjk1ZDJjNDRkZDc0NDRhMTVmNzZhOTM4NWMzMjhmY2FiIn19fQ==", "§c§lNiemand"));
            stand.setSmall(true);
            stand.setGravity(false);

            new BukkitRunnable() {

                float rotation = 0;

                @Override
                public void run() {

                    rotation -= 0.05f;
                    stand.setHeadPose(new EulerAngle(0, rotation, 0));

                }
            }.runTaskTimer(this.plugin, 3 * 20L, 1);
            stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
            stand.setPersistent(false);

            this.shopHologramMap.put(shopModel, stand);

        }
    }

    public void refreshArmorStand(ShopModel shopModel) {
        ArmorStand currentStand = this.shopHologramMap.get(shopModel);

        currentStand.setSmall(true);
        currentStand.setGravity(false);
        currentStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);

        if(this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString())) {
            currentStand.getEquipment().setHelmet(new ItemFactory(Material.PLAYER_HEAD, 1).skullFromUUID(shopModel.getOwnerUUID()).build());
            currentStand.setCustomName("§5" + HelperMethods.getName(shopModel.getOwnerUUID().toString()) + "'s Shop");
            return;
        }

        currentStand.getEquipment().setHelmet(HelperMethods.getSkullFromURL("" + "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJhZGRhZDljYWNlYjA1ZjM0YmRjZjMyOTM3Yjk1ZDJjNDRkZDc0NDRhMTVmNzZhOTM4NWMzMjhmY2FiIn19fQ==", "§c§lNiemand"));
        currentStand.setCustomName("§c§lKein §4§lInhaber");

    }

    public void setSigns() {
        for (ShopModel shopModel : this.plugin.getShopConfigurationManager().getRegisteredShopList()) {
            Location signLocation = shopModel.getSignLocation();
            if (!(signLocation.getBlock().getState() instanceof Sign sign)) continue;

            if (this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString())) {
                sign.setLine(0, "§7§m-------------------");
                sign.setLine(1, "Der §d§lShop §rvon");
                sign.setLine(2, "§5§l" + HelperMethods.getName(shopModel.getOwnerUUID().toString()));
                sign.setLine(3, "§7§m-------------------");
                sign.update();
                this.shopSignMap.put(shopModel, sign);
                continue;
            }

            sign.setLine(0, "§7§m-------------------");
            sign.setLine(1, "§a§lKaufbar");
            sign.setLine(2, "§e§l 10.000 §6§lTaler");
            sign.setLine(3, "§7§m-------------------");
            sign.update();
            this.shopSignMap.put(shopModel, sign);
        }
    }

    public void refreshSign(ShopModel shopModel) {
        Location signLocation = shopModel.getSignLocation();
        if (!(signLocation.getBlock().getState() instanceof Sign sign)) return;

        if (this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString())) {
            sign.setLine(0, "§7§m-------------------");
            sign.setLine(1, "Der §d§lShop §rvon");
            sign.setLine(2, "§5§l" + HelperMethods.getName(shopModel.getOwnerUUID().toString()));
            sign.setLine(3, "§7§m-------------------");
            sign.update();
            this.shopSignMap.put(shopModel, sign);
            return;
        }

        sign.setLine(0, "§7§m-------------------");
        sign.setLine(1, "§a§lKaufbar");
        sign.setLine(2, "§e§l 10.000 §6§lTaler");
        sign.setLine(3, "§7§m-------------------");
        sign.update();
        this.shopSignMap.put(shopModel, sign);
    }

    public void removeAllArmorStands() {
        for (ArmorStand stand : this.shopHologramMap.values()) {
            stand.getWorld().getEntities().remove(stand);
            stand.remove();
        }
    }

    public boolean isSignFromShop(Block block) {
        return block.getState() instanceof Sign && this.shopSignMap.containsValue((Sign) block.getState());
    }

    public ShopModel getShopFromSign(Block block) {
        for (ShopModel shopModel : this.shopSignMap.keySet()) {
            if (this.shopSignMap.get(shopModel).equals(block.getState())) return shopModel;
        }
        return null;
    }

    public void buyShop(ShopModel shopModel, Player player) {
        if (this.plugin.getShopConfigurationManager().isShopOwned(shopModel.getShopId().toString())) return;
        if (!(this.plugin.getBalanceManager().hasBalance(player, 10000))) {
            double balance = this.plugin.getBalanceManager().getBalance(player);
            int distance = (int) (10000 - balance);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            player.sendMessage(PREFIX + "Du hast §cnicht §7ausreichend Taler!");
            player.sendMessage(PREFIX + "Der Shop kostet §e10.000 §7Taler, dir fehlen noch §c" + distance + " §7Taler!");
            return;
        }
        this.plugin.getBalanceManager().removeMoney(player, 10000);
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
        player.sendMessage(PREFIX + "Du hast den Shop §aerfolgreich §7erworben!");
        player.sendMessage(PREFIX + "Dafür wurden dir 10.000 Taler vom Konto §centfernt§7!");
        shopModel.setOwnerUUID(player.getUniqueId());
        shopModel.save();
        this.refreshArmorStand(shopModel);
        this.refreshSign(shopModel);
    }



}
