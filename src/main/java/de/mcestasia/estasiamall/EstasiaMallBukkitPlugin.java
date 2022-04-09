package de.mcestasia.estasiamall;

import de.mcestasia.estasiamall.command.ShopAdminBuildCommand;
import de.mcestasia.estasiamall.command.ShopListCommand;
import de.mcestasia.estasiamall.command.ShopSetupCommand;
import de.mcestasia.estasiamall.command.AnimationFlexCommand;
import de.mcestasia.estasiamall.listener.InventoryListener;
import de.mcestasia.estasiamall.listener.SetupListener;
import de.mcestasia.estasiamall.listener.ShopListener;
import de.mcestasia.estasiamall.manager.BalanceManager;
import de.mcestasia.estasiamall.manager.BuildManager;
import de.mcestasia.estasiamall.manager.ShopConfigurationManager;
import de.mcestasia.estasiamall.manager.ShopManager;
import de.mcestasia.estasiamall.setup.SetupHandler;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

// https://www.youtube.com/watch?v=3nzmy9_5AvI
// Vibe

@Getter
public class EstasiaMallBukkitPlugin extends JavaPlugin {

    public static EstasiaMallBukkitPlugin instance;
    private ShopConfigurationManager shopConfigurationManager;
    private SetupHandler setupHandler;
    private ShopManager shopManager;
    private BuildManager buildManager;

    // Economy
    private Economy economy;
    private RegisteredServiceProvider<Economy> registeredEconomyProvider;
    private BalanceManager balanceManager;

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
        this.shopManager.removeAllArmorStands();
    }

    public void reload() {
        this.shopConfigurationManager.reload();
    }

    private void init(PluginManager pluginManager) {
        // Classes
        instance = this;
        this.shopConfigurationManager = new ShopConfigurationManager();
        this.setupHandler = new SetupHandler();
        this.shopManager = new ShopManager();
        this.buildManager = new BuildManager();

        // Economy
        this.registeredEconomyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        assert this.registeredEconomyProvider != null;
        this.economy = this.registeredEconomyProvider.getProvider();
        this.balanceManager = new BalanceManager();

        // Commands
        Objects.requireNonNull(this.getCommand("shopsetup")).setExecutor(new ShopSetupCommand());
        Objects.requireNonNull(this.getCommand("shoplist")).setExecutor(new ShopListCommand());
        Objects.requireNonNull(this.getCommand("shopadminbuild")).setExecutor(new ShopAdminBuildCommand());
        Objects.requireNonNull(this.getCommand("animationflex")).setExecutor(new AnimationFlexCommand());

        // Listener
        pluginManager.registerEvents(new SetupListener(), this);
        pluginManager.registerEvents(new ShopListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);

    }
}
