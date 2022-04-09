package de.mcestasia.estasiamall.model.manipulation;

import de.mcestasia.estasiamall.model.shop.ShopModel;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class ManipulationModel {

    private final Player player;
    private final ManipulationType manipulationType;
    private final ShopModel shopModel;

    public ManipulationModel(Player player, ManipulationType manipulationType, ShopModel shopModel) {
        this.player = player;
        this.manipulationType = manipulationType;
        this.shopModel = shopModel;
    }
}
