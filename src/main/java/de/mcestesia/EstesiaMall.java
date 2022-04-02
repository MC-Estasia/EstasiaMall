package de.mcestesia;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class EstesiaMall extends JavaPlugin {

    public static EstesiaMall instance;

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    private void reload(){

    }

    private void init(){
        instance = this;

        // Classes

    }
}
