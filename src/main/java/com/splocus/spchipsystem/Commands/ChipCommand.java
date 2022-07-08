package com.splocus.spchipsystem.Commands;

import com.splocus.spchipsystem.Filer.Config;
import com.splocus.spchipsystem.SPChipSystem;
import com.splocus.spchipsystem.Util.DF;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.StringJoiner;

public class ChipCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (!p.hasPermission("sp.chip")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&4&lS&c&lP&8] &7Ingen adgang. Brug &c/help &7for hjælp"));
                return true;
            }
            if (args.length < 1) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lCHIPS SYSTEM SPLOCUS"));
                p.sendMessage("");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&'," &e/chip give <spiller> <antal> &6-&f giver en spiller x antal chips"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&'," &e/chip giveall <antal> &6-&f giver alle spillere x antal chips"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&'," &e/chip set <crate> &6-&f sætter en bestemt crate til en block"));
                return true;
            }

            if (args[0].equals("give")) {
                if (args.length < 3) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Rigtig brug: &a/celle give <spiller> <antal>"));
                    return true;
                }
                Player givePlayer = Bukkit.getPlayer(args[1]);
                if (givePlayer != null) {

                try {
                    Integer.parseInt(args[2]);
                } catch (NumberFormatException nfe) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Rigtig brug: &a/celle give <spiller> <antal>"));
                    return true;
                }
                SPChipSystem.chip.setAmount(Integer.parseInt(args[2]));
                givePlayer.getInventory().addItem(SPChipSystem.chip);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har givet &b" + givePlayer.getName() + " &e" + args[2] + " CHIP(s)"));
                givePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har modtaget &e" + args[2] + " CHIP(s)"));
                return true;
            }
            else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Denne spiller findes ikke!"));
                return true;
            }
            }
            if (args[0].equals("giveall")) {
                if (args.length < 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Rigtig brug: &a/celle giveall <antal>"));
                    return true;
                }
                try {
                    Integer.parseInt(args[2]);
                } catch (NumberFormatException nfe) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Rigtig brug: &a/celle give <spiller> <antal>"));
                    return true;
                }
                SPChipSystem.chip.setAmount(Integer.parseInt(args[2]));
                Collection<? extends Player> listPlayers = Bukkit.getOnlinePlayers();

                for (Player player : listPlayers) {
                    player.getInventory().addItem(SPChipSystem.chip);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har modtaget &e" + args[2] + " CHIP(s)"));
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har givet &balle &e" + args[2] + " CHIP(s)"));

            }

            if (args[0].equals("set")) {
                if (args.length < 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Rigtig brug: &a/celle set <crate>"));
                    return true;
                }
                if (args[1].equals("diamant") || args[1].equals("guld") || args[1].equals("sølv")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har sat en ny location til &a" + args[1]));
                    Config.get().set("locations." + args[1], p.getTargetBlock(null, 5).getState().getLocation());
                    Config.save();
                    return true;
                }
                else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Rigtige typer: &adiamant&7, &aguld &7eller &asølv"));
                }

            }

            else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6&lCHIPS SYSTEM SPLOCUS"));
                p.sendMessage("");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&'," &e/chip give <spiller> <antal> &6-&f giver en spiller x antal chips"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&'," &e/chip giveall <antal> &6-&f giver alle spillere x antal chips"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&'," &e/chip set <crate> &6-&f sætter en bestemt crate til en block"));
                return true;
            }
            }
            else {
            if (args[0].equals("broadcast")) {
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(sendCenteredMessage("§e§m-----§6§m-----§e§m-----§6§m-----§e§m-----§6§m-----§e§m-----"));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(sendCenteredMessage("§b§l" + args[1].toUpperCase() + " §7HAR LIGE VUNDET JACKPOT"));
                Bukkit.broadcastMessage(sendCenteredMessage("§e§l" + args[2] + " CHIPS" + "§7 I " + args[3]));
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(sendCenteredMessage("§e§m-----§6§m-----§e§m-----§6§m-----§e§m-----§6§m-----§e§m-----"));
            }

            if (args[0].equals("give")) {
                Player givePlayer = Bukkit.getPlayer(args[1]);
                if (givePlayer != null) {

                    try {
                        Integer.parseInt(args[2]);
                    } catch (NumberFormatException nfe) {
                        return true;
                    }
                    SPChipSystem.chip.setAmount(Integer.parseInt(args[2]));
                    givePlayer.getInventory().addItem(SPChipSystem.chip);
                    givePlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6&lCHIP&8] &7Du har modtaget &e" + args[2] + " CHIP(s)"));
                }
            }
        }
        return false;
    }



    private final static int CENTER_PX = 154;

    public String sendCenteredMessage(String message){
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DF.DefaultFontInfo dFI = DF.DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DF.DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb + message;
    }


}
