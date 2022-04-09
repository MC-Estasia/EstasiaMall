package de.mcestasia.estasiamall.util;

import de.mcestasia.estasiamall.EstasiaMallBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemFactory {

    private ItemStack item;

    public ItemFactory(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemFactory(ItemStack item) {
        this.item = item;
    }

    public ItemFactory removeLore() {
        ItemMeta m = this.item.getItemMeta();
        if (m.hasLore()) {
            m.setLore(new ArrayList<>());
        }
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setPersistentDataString(String key, String value) {
        ItemMeta m = this.item.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(EstasiaMallBukkitPlugin.instance, key);
        m.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory removeName() {
        ItemMeta m = this.item.getItemMeta();
        if (m.hasDisplayName()) {
            m.setDisplayName(null);
        }
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setMaterial(Material m) {
        this.item.setType(m);
        return this;
    }

    public ItemFactory setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemFactory setName(String name) {
        ItemMeta m = this.item.getItemMeta();
        m.setDisplayName(name);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setLore(String... lore) {
        ItemMeta m = this.item.getItemMeta();
        m.setLore(Arrays.asList(lore));
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setLore(List<String> lore) {
        ItemMeta m = this.item.getItemMeta();
        m.setLore(lore);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setLore(ArrayList<String> lore) {
        ItemMeta m = this.item.getItemMeta();
        m.setLore(lore);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setUnbreakable() {
        ItemMeta m = this.item.getItemMeta();
        m.setUnbreakable(true);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory enchant(Enchantment ench, int lvl) {
        this.item.addUnsafeEnchantment(ench, lvl);
        return this;
    }

    public ItemFactory addFlags(ItemFlag... flag) {
        ItemMeta m = this.item.getItemMeta();
        m.addItemFlags(flag);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setLeatherColor(Color color) {
        LeatherArmorMeta m = (LeatherArmorMeta) this.item.getItemMeta();
        m.setColor(color);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory skullFromUUID(UUID uuid) {
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemFactory setPotionType(PotionType type) {
        PotionMeta m = (PotionMeta) this.item.getItemMeta();
        m.setBasePotionData(
                new PotionData(type, false, false)
        );
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setBookAuthor(String author) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setAuthor(author);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setBookContent(String... pages) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setPages(pages);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setBookTitle(String title) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setTitle(title);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemFactory setBookMeta(String title, String author, String... pages) {
        BookMeta m = (BookMeta) this.item.getItemMeta();
        m.setTitle(title);
        m.setAuthor(author);
        m.setPages(pages);
        this.item.setItemMeta(m);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }

}
