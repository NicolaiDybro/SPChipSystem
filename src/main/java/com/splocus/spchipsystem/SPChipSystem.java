package com.splocus.spchipsystem;

import com.splocus.spchipsystem.Commands.ChipCommand;
import com.splocus.spchipsystem.Events.CrateClick;
import com.splocus.spchipsystem.Filer.Config;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public final class SPChipSystem extends JavaPlugin {

    public static ItemStack chip;

    private static SPChipSystem plugin;
    public static SPChipSystem getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        chip = createItem(Material.PAPER, "§e§lCHIP", "§7Bruges til at åbne", "§7forskellige §bcrates", "", "§7Kan indløses i kasinoet");

        // Commands
        getCommand("chip").setExecutor(new ChipCommand());

        // Filer
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveResource("config.yml", false);
        Config.setup();
        Config.get().options().copyDefaults(true);

        getServer().getPluginManager().registerEvents(new CrateClick(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    protected ItemStack createItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }




}
