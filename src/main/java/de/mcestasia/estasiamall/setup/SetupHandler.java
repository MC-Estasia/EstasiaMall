package de.mcestasia.estasiamall.setup;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.ShopModel;
import de.mcestasia.estasiamall.util.HelperMethods;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

import static de.mcestasia.estasiamall.util.Constants.PREFIX;

@Getter
public class SetupHandler {

    private final EstasiaMallBukkitPlugin plugin;

    private final HashMap<Player, SetupStep> playerSetupMap;
    private final HashMap<Player, ShopModel> playerShopCache;

    public SetupHandler() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.playerSetupMap = new HashMap<>();
        this.playerShopCache = new HashMap<>();
    }

    public SetupStep getSetupStep(Player player) {
        return playerSetupMap.get(player);
    }

    public void getNewSetupStep(Player player) {
        if (!(this.isInSetup(player))) {
            this.playerSetupMap.put(player, SetupStep.FIRST_STEP);
            this.playerShopCache.put(player, new ShopModel().setShopId(null).setOwnerUUID(null).setPermitted(HelperMethods.convertStringListToUUIDList(Arrays.asList("00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000"))));
            player.sendMessage(PREFIX + "Willkommen im Estasia Mall §d§lSetup§7!");
            player.sendMessage(" ");
            this.executeNewSetupStep(player, SetupStep.FIRST_STEP);
            return;
        }
        int currentStepOrder = this.playerSetupMap.get(player).getSetupOrder();
        int newStepOrder = currentStepOrder + 1;

        switch (newStepOrder) {
            case 1 -> this.playerSetupMap.replace(player, SetupStep.FIRST_STEP);
            case 2 -> this.playerSetupMap.replace(player, SetupStep.SECOND_STEP);
            case 3 -> this.playerSetupMap.replace(player, SetupStep.THIRD_STEP);
            case 4 -> this.playerSetupMap.replace(player, SetupStep.FOURTH_STEP);
            default -> this.finishSetup(player);
        }

        this.executeNewSetupStep(player, this.playerSetupMap.get(player));

    }

    public void executeNewSetupStep(Player player, SetupStep setupStep) {
        player.sendMessage(setupStep.getSetupStepMessage());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.2F, 1.2F);
    }

    public ShopModel getShopCache(Player player) {
        return playerShopCache.get(player);
    }

    public void finishSetup(Player player) {
        ShopModel shopModel = this.playerShopCache.get(player);
        shopModel.save();
        player.sendMessage(PREFIX + "Der Shop wurde §aerfolgreich §7erstellt!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1.5F);
        this.playerSetupMap.remove(player);
        this.playerShopCache.remove(player);
        this.plugin.reload();
    }

    public boolean isInSetup(Player player) {
        return playerSetupMap.containsKey(player);
    }

}
