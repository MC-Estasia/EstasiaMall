package de.mcestasia.estasiamall.manager;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.ShopLocationType;
import de.mcestasia.estasiamall.model.ShopModel;
import de.mcestasia.estasiamall.util.HelperMethods;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter
public class ShopConfigurationManager {

    private final EstasiaMallBukkitPlugin plugin;
    private final File shopDirectory;
    private final File shopFile;
    private final FileConfiguration configuration;
    private final HashMap<String, ShopModel> registeredShops;
    private final List<ShopModel> registeredShopList;

    public ShopConfigurationManager() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.shopDirectory = new File(this.plugin.getDataFolder() + "/shops");
        this.shopFile = new File(this.shopDirectory, "shops.yml");
        this.registeredShops = new HashMap<>();
        this.registeredShopList = new ArrayList<>();
        this.createFile();
        this.configuration = YamlConfiguration.loadConfiguration(this.shopFile);
        this.loadShops();
    }

    public void createFile() {
        if (!(this.shopDirectory.exists())) {
            if (shopFile.mkdirs()) {
                System.out.println("[!] Der Shop Ordner wurde erfolgreich erstellt.");
            }
        } else {
            System.out.println("[»] Der Shop Ordner wurde erfolgreich geladen.");
        }
        if (!(this.shopFile.exists())) {
            try {
                if (this.shopFile.createNewFile()) {
                    System.out.println("[!] Die Shop Datei wurde erfolgreich erstellt.");
                }
            } catch (IOException exception) {
                System.out.println("[»] Die Shop Datei konnte nicht erstellt werden.");
                exception.printStackTrace();
            }
        } else {
            System.out.println("[»] Die Shop Datei wurde erfolgreich geladen.");
        }
    }

    public boolean createShop(String shopId) {
        if (!(this.isShopCreated(shopId))) {
            this.configuration.set(shopId + ".shopid", shopId);
            List<String> shops = this.configuration.getStringList("shops");
            shops.add(shopId);
            this.configuration.set("shops", shops);
            try {
                this.configuration.save(this.shopFile);
            } catch (IOException exception) {
                System.err.println("Der Shop " + shopId + " konnte nicht erstellt werden.");
                exception.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean setShopOwner(String shopId, String shopOwner) {
        if (this.isShopCreated(shopId)) {
            this.configuration.set(shopId + ".owner", shopOwner);
            try {
                this.configuration.save(this.shopFile);
            } catch (IOException exception) {
                System.err.println("Der Inhaber des Shops " + shopId + " konnte nicht gesetzt werden.");
                exception.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean setShopLocation(String shopID, Location location, ShopLocationType shopLocationType) {
        if (this.isShopCreated(shopID)) {
            String world = Objects.requireNonNull(location.getWorld()).getName();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            this.configuration.set(shopID + "." + shopLocationType.getConfigurationName() + ".world", world);
            this.configuration.set(shopID + "." + shopLocationType.getConfigurationName() + ".x", x);
            this.configuration.set(shopID + "." + shopLocationType.getConfigurationName() + ".y", y);
            this.configuration.set(shopID + "." + shopLocationType.getConfigurationName() + ".z", z);

            try {
                this.configuration.save(this.shopFile);
            } catch (IOException e) {
                System.err.println("Die Shop Location des Shops " + shopID + " konnte nicht gesetzt werden.");
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public Location getShopLocation(String shopID, ShopLocationType shopLocationType) {
        if (this.isShopCreated(shopID)) {
            String world = this.configuration.getString(shopID + "." + shopLocationType.getConfigurationName() + ".world");
            if (Bukkit.getWorlds().stream().noneMatch(world1 -> world1.getName().equals(world))) {
                Bukkit.createWorld(new WorldCreator(world));
            }
            double x = this.configuration.getDouble(shopID + "." + shopLocationType.getConfigurationName() + ".x");
            double y = this.configuration.getDouble(shopID + "." + shopLocationType.getConfigurationName() + ".y");
            double z = this.configuration.getDouble(shopID + "." + shopLocationType.getConfigurationName() + ".z");

            return new Location(Bukkit.getWorld(world), x, y, z);
        } else {
            return null;
        }
    }

    public boolean isShopCreated(String shopID) {
        return this.configuration.isSet(shopID + ".shopid");
    }

    public boolean isShopOwned(String shopID) {
        if (this.isShopCreated(shopID)) {
            return this.configuration.isSet(shopID + ".owner") && ! Objects.equals(this.configuration.getString(shopID + ".owner"), "");
        }
        return true;
    }

    public String getOwnerUUID(String shopID) {
        if (this.isShopCreated(shopID)) {
            return this.configuration.getString(shopID + ".owner");
        }
        return "";
    }

    public List<String> getPermittedPlayer(String shopID) {
        if (this.isShopCreated(shopID)) {
            return this.configuration.getStringList(shopID + ".permitted");
        }
        return new ArrayList<>();
    }

    public void loadShops() {
        for (String shop : this.configuration.getStringList("shops")) {
            ShopModel shopModel = new ShopModel(UUID.fromString(shop),
                    UUID.fromString(this.getOwnerUUID(shop)),
                    this.getShopLocation(shop, ShopLocationType.LOWER),
                    this.getShopLocation(shop, ShopLocationType.HIGHER),
                    this.getShopLocation(shop, ShopLocationType.SIGN),
                    HelperMethods.convertStringListToUUIDList(getPermittedPlayer(shop)));
            this.registeredShops.put(shop, shopModel);
            this.registeredShopList.add(shopModel);
        }
    }

}
