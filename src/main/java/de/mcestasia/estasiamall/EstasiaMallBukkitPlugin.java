package de.mcestasia.estasiamall;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class EstasiaMallBukkitPlugin extends JavaPlugin {

    public static EstasiaMallBukkitPlugin instance;

    @Override
    public void onEnable() {
        System.out.println("==============================");
        System.out.println("Das EstasiaMall Plugin wird geladen...");
        System.out.println("» Version: " + this.getDescription().getVersion());
        System.out.println("==============================");
        this.init();
    }

    @Override
    public void onDisable() {
        System.out.println("==============================");
        System.out.println("Das EstasiaMall Plugin wird entladen...");
        System.out.println("» Version: " + this.getDescription().getVersion());
        System.out.println("==============================");
        instance = null;
    }

    private void reload() {

    }

    private void init() {
        instance = this;

        // Classes

    }
}
