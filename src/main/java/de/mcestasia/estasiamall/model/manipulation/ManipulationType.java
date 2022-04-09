package de.mcestasia.estasiamall.model.manipulation;

import lombok.Getter;

@Getter
public enum ManipulationType {

    OWNER("owner", "§7Bitte gib nun die §4§lUUID §7des §c§lneuen §7Shop-Inhabers ein!"),
    OWNER_DELETE("owner_delete", "Bitte §crejoine§7!"),
    HOLOGRAM_LOCATION("hologram_location", "§7Bitte §e§lstelle §7dich nun an die Stelle des Hologramms und schreibe §agesetzt §7in den Chat!"),
    SIGN_LOCATION("sign_location", "§7Bitte schlage nun das §e§lVerkaufs-Schild §7des Shops!");

    private final String name;
    private final String command;

    ManipulationType(String name, String command) {
        this.name = name;
        this.command = command;
    }
}
