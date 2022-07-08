package com.splocus.spchipsystem.Events;

import com.splocus.spchipsystem.Filer.Config;
import com.splocus.spchipsystem.SPChipSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class CrateClick implements Listener {

    Inventory inv;

    @EventHandler
    public void onCrateClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getClickedBlock() == null) {
            return;
        }

        ConfigurationSection cs = Config.get().getConfigurationSection("locations");

        for (String key : cs.getKeys(false)) {
            if (Objects.equals(e.getClickedBlock().getLocation().toString(), Objects.requireNonNull(Config.get().getLocation("locations." + key)).toString())) {
                if (key.equals("diamant")) {
                    if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                        inv = Bukkit.createInventory(null, 27, "§b§lDIAMANT CRATE §8Åben");
                        loadKob(10);
                        p.openInventory(inv);
                    }
                    if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "crate preview diamant " + p.getName();
                        Bukkit.dispatchCommand(console, command);
                    }
                }
                if (key.equals("guld")) {
                    if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                        inv = Bukkit.createInventory(null, 27, "§e§lGULD CRATE §8Åben");
                        loadKob(5);
                        p.openInventory(inv);
                    }
                    if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "crate preview guld " + p.getName();
                        Bukkit.dispatchCommand(console, command);
                    }
                }
                if (key.equals("sølv")) {
                    if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                        inv = Bukkit.createInventory(null, 27, "§f§lSØLV CRATE §8Åben");
                        loadKob(3);
                        p.openInventory(inv);
                    }
                    if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "crate preview solv " + p.getName();
                        Bukkit.dispatchCommand(console, command);
                    }
                }
                e.setCancelled(true);
            }


        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().contains(" CRATE §8Åben")) {
            if (e.getSlot() == 11) {
                p.closeInventory();
            }
            if (e.getView().getTitle().contains("DIAMANT")) {

                if (e.getSlot() == 15) {
                        if (p.getInventory().containsAtLeast(SPChipSystem.chip, 10)) {
                            removeInventory(p, 10);
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            String command = "crate forceopen diamant " + p.getName();
                            Bukkit.dispatchCommand(console, command);
                        }
                        else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har ikke nok chips!"));
                            p.closeInventory();
                        }
                    }


                e.setCancelled(true);
            }

            if (e.getView().getTitle().contains("GULD")) {

                if (e.getSlot() == 15) {
                    if (p.getInventory().containsAtLeast(SPChipSystem.chip, 5)) {
                        removeInventory(p, 5);
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "crate forceopen guld " + p.getName();
                        Bukkit.dispatchCommand(console, command);
                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har ikke nok chips!"));
                        p.closeInventory();
                    }
                }


                e.setCancelled(true);
            }

            if (e.getView().getTitle().contains("SØLV")) {

                if (e.getSlot() == 15) {
                    if (p.getInventory().containsAtLeast(SPChipSystem.chip, 3)) {
                        removeInventory(p, 3);
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "crate forceopen solv " + p.getName();
                        Bukkit.dispatchCommand(console, command);
                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har ikke nok chips!"));
                        p.closeInventory();
                    }
                }


                e.setCancelled(true);
            }

        }
    }
    public void removeInventory(Player p, Integer amount) {
        for (ItemStack inventoryItem : p.getInventory().getContents()) {
            if (inventoryItem == null || inventoryItem.getType() == Material.AIR) {
                continue;
            }
            if (inventoryItem.isSimilar(SPChipSystem.chip)) {
                if (inventoryItem.getAmount() > amount) {
                    inventoryItem.setAmount(inventoryItem.getAmount() - amount);
                    return;
                } else if (inventoryItem.getAmount() <= amount) {
                    amount -= inventoryItem.getAmount();
                    p.getInventory().remove(inventoryItem);
                    if (amount <= 0) {
                        return;
                    }
                }
            }
        }
    }

    protected ItemStack createItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void loadKob(Integer pris) {

        inv.setItem(11, createItem(Material.REDSTONE_BLOCK, "§c§lANNULLERE", "§c§nKLIK§r §7for at annullere"));
        inv.setItem(13, createItem(Material.PAPER, "§e§lBEMÆRK", "§7Det koster at åbne:", "§e" + pris + " Chips"));
        inv.setItem(15, createItem(Material.EMERALD_BLOCK, "§a§lÅBEN", "§a§nKLIK§r §7for at åbne"));

        int i;
        inv.setItem(9, createItem(Material.GRAY_STAINED_GLASS_PANE, "§a"));
        inv.setItem(17, createItem(Material.GRAY_STAINED_GLASS_PANE, "§a"));
        for (i = 0; i < 9; i++) {
            inv.setItem(i, createItem(Material.GRAY_STAINED_GLASS_PANE, "§a"));
        }
        for (i = 18; i < 27; i++) {
            inv.setItem(i, createItem(Material.GRAY_STAINED_GLASS_PANE, "§a"));
        }

    }

}
