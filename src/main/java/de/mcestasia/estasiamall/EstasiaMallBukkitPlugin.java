package de.mcestasia.estasiamall;

import de.mcestasia.estasiamall.command.ShopSetupCommand;
import de.mcestasia.estasiamall.listener.SetupListener;
import de.mcestasia.estasiamall.manager.ShopConfigurationManager;
import de.mcestasia.estasiamall.setup.SetupHandler;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

// https://www.youtube.com/watch?v=3nzmy9_5AvI
// Vibe

@Getter
public class EstasiaMallBukkitPlugin extends JavaPlugin {

    public static EstasiaMallBukkitPlugin instance;
    private ShopConfigurationManager shopConfigurationManager;
    private SetupHandler setupHandler;

    @Override
    public void onEnable() {
        System.out.println("==============================");
        System.out.println("Das EstasiaMall Plugin wird geladen...");
        System.out.println("» Version: " + this.getDescription().getVersion());
        System.out.println("==============================");
        this.init(this.getServer().getPluginManager());
    }

    @Override
    public void onDisable() {
        System.out.println("==============================");
        System.out.println("Das EstasiaMall Plugin wird entladen...");
        System.out.println("» Version: " + this.getDescription().getVersion());
        System.out.println("==============================");
        instance = null;
    }

    public void reload() {
        this.shopConfigurationManager.reload();
    }

    private void init(PluginManager pluginManager) {
        // Classes
        instance = this;
        this.shopConfigurationManager = new ShopConfigurationManager();
        this.setupHandler = new SetupHandler();

        // Commands
        Objects.requireNonNull(this.getCommand("shopsetup")).setExecutor(new ShopSetupCommand());

        // Listener
        pluginManager.registerEvents(new SetupListener(), this);

    }
}
