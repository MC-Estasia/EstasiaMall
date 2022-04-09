package de.mcestasia.estasiamall.model.shop;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.manager.shop.ShopConfigurationManager;
import de.mcestasia.estasiamall.util.HelperMethods;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ShopModel {

    private UUID shopId;
    private UUID ownerUUID;
    private Location lowerBoundary;
    private Location higherBoundary;
    private Location hologramLocation;
    private List<UUID> permitted;
    private Location signLocation;

    public ShopModel() {
    }

    public ShopModel setLowerBoundary(Location lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
        return this;
    }

    public ShopModel setHigherBoundary(Location higherBoundary) {
        this.higherBoundary = higherBoundary;
        return this;
    }

    public ShopModel setSignLocation(Location signLocation) {
        this.signLocation = signLocation;
        return this;
    }

    public ShopModel setPermitted(List<UUID> permitted) {
        this.permitted = permitted;
        return this;
    }

    public ShopModel setShopId(UUID shopId) {
        if (shopId == null) {
            this.shopId = UUID.randomUUID();
            return this;
        }
        this.shopId = shopId;
        return this;
    }

    public ShopModel setOwnerUUID(UUID ownerUUID) {
        if (ownerUUID == null) {
            this.ownerUUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
            return this;
        }
        this.ownerUUID = ownerUUID;
        return this;
    }

    public ShopModel setHologramLocation(Location hologramLocation) {
        this.hologramLocation = hologramLocation;
        return this;
    }

    public ShopModel save() {
        ShopConfigurationManager shopConfigurationManager = EstasiaMallBukkitPlugin.instance.getShopConfigurationManager();

        if(!(shopConfigurationManager.isShopCreated(this.shopId.toString()))) {
            shopConfigurationManager.createShop(String.valueOf(this.shopId));
            shopConfigurationManager.setShopOwner(String.valueOf(this.shopId), String.valueOf(this.ownerUUID));
            shopConfigurationManager.setPermittedPlayers(String.valueOf(this.shopId), HelperMethods.convertUUIDListToStringList(this.permitted));
            shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.lowerBoundary, ShopLocationType.LOWER);
            shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.higherBoundary, ShopLocationType.HIGHER);
            shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.signLocation, ShopLocationType.SIGN);
            shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.hologramLocation, ShopLocationType.HOLOGRAM);
            return this;
        }

        shopConfigurationManager.setShopOwner(String.valueOf(this.shopId), String.valueOf(this.ownerUUID));
        shopConfigurationManager.setPermittedPlayers(String.valueOf(this.shopId), HelperMethods.convertUUIDListToStringList(this.permitted));
        shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.lowerBoundary, ShopLocationType.LOWER);
        shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.higherBoundary, ShopLocationType.HIGHER);
        shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.signLocation, ShopLocationType.SIGN);
        shopConfigurationManager.setShopLocation(String.valueOf(this.shopId), this.hologramLocation, ShopLocationType.HOLOGRAM);
        return this;
    }

}
