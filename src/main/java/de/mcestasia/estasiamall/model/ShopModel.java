package de.mcestasia.estasiamall.model;

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
    private List<UUID> permitted;
    private Location signLocation;

    public ShopModel(UUID shopId, UUID ownerUUID, Location lowerBoundary, Location higherBoundary, Location signLocation, List<UUID> permitted) {
        this.shopId = shopId;
        this.ownerUUID = ownerUUID;
        this.lowerBoundary = lowerBoundary;
        this.higherBoundary = higherBoundary;
        this.signLocation = signLocation;
        this.permitted = permitted;
    }
}
