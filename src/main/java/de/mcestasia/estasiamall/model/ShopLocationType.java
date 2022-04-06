package de.mcestasia.estasiamall.model;

import lombok.Getter;

@Getter
public enum ShopLocationType {

    LOWER("lower"), HIGHER("higher"), SIGN("sign");

    private final String configurationName;

    ShopLocationType(String configurationName) {
        this.configurationName = configurationName;
    }
}
