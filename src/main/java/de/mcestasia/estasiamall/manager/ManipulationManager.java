package de.mcestasia.estasiamall.manager;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import de.mcestasia.estasiamall.model.manipulation.ManipulationModel;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ManipulationManager {

    private final EstasiaMallBukkitPlugin plugin;
    private final List<ManipulationModel> currentManipulations;

    public ManipulationManager() {
        this.plugin = EstasiaMallBukkitPlugin.instance;
        this.currentManipulations = new ArrayList<>();
    }

    public void addManipulation(ManipulationModel manipulation) {
        currentManipulations.add(manipulation);
    }

    public void removeManipulation(ManipulationModel manipulation) {
        currentManipulations.remove(manipulation);
    }

    public Optional<ManipulationModel> getCurrentManipulationType(Player player){
        return this.currentManipulations.stream().filter(manipulationModel -> manipulationModel.getPlayer().equals(player)).findFirst();
    }

    public boolean isInManipulation(Player player) {
        return this.currentManipulations.stream().anyMatch(manipulationModel -> manipulationModel.getPlayer().equals(player));
    }

}
