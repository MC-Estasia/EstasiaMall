package de.mcestasia.estasiamall.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.Cuboid;
import de.mcestasia.estasiamall.model.shop.ShopModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class HelperMethods {

    public static List<UUID> convertStringListToUUIDList(List<String> originalList) {
        List<UUID> newList = new ArrayList<>();
        originalList.forEach(uuid -> newList.add(UUID.fromString(uuid)));
        return newList;
    }

    public static List<String> convertUUIDListToStringList(List<UUID> originalList) {
        List<String> newList = new ArrayList<>();
        originalList.forEach(uuid -> newList.add(uuid.toString()));
        return newList;
    }

    public static boolean isInShop(Location toCheck) {
        return EstasiaMallBukkitPlugin.instance.getShopConfigurationManager().getShopRegions().values().stream().anyMatch(cuboid -> cuboid.contains(toCheck));
    }

    public static ShopModel getShopByLocation(Location toCheck) {
        AtomicReference<ShopModel> model = new AtomicReference<>();
        Cuboid cuboid = getCuboidByLocation(toCheck);
        EstasiaMallBukkitPlugin.instance.getShopConfigurationManager().getShopRegions().forEach((shopModel, cuboid1) -> {
            if (cuboid1.equals(cuboid)) {
                model.set(shopModel);
            }
        });
        return model.get();
    }

    public static Cuboid getCuboidByLocation(Location toCheck) {
        return EstasiaMallBukkitPlugin.instance.getShopConfigurationManager().getShopRegions().values().stream().filter(cuboid -> cuboid.contains(toCheck)).findFirst().orElse(null);
    }

    public static String getName(String uuid) {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
    }

    public static void sendCentredMessage(Player player, String message) {
        if (message == null || message.equals("")) {
            player.sendMessage("");
            return;
        }
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }
        int CENTER_PX = 154;
        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb + message);
    }

    public static ItemStack getSkullFromURL(String url, String name) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty()) {
            return head;
        }

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
        }
        head.setItemMeta(headMeta);
        head.getItemMeta().setDisplayName(name);
        return head;
    }

}
