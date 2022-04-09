package de.mcestasia.estasiamall.model.shop;

import lombok.Getter;

@Getter
public enum ShopLocationType {

    LOWER("lower"), HIGHER("higher"), SIGN("sign"), HOLOGRAM("hologram");

    private final String configurationName;

    ShopLocationType(String configurationName) {
        this.configurationName = configurationName;
    }
}
