package com.infxnty.staffmode.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.infxnty.staffmode.Main;

public class ModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		
		
		if (p.hasPermission("staffmode.use")) {
			
			if (Main.staffmode.contains(p)) {
	
				Main.staffmode.remove(p);
				p.getInventory().clear();
				p.setGameMode(GameMode.SURVIVAL);
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (pl.hasPermission("staffmode.notify")) {
						pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bStaffMode&8] &7» &a" + p.getDisplayName() + "&7 is no longer in StaffMode!"));
					}
				}
				
			} else {
				
				Main.staffmode.add(p);
				
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (pl.hasPermission("staffmode.notify")) {
						pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bStaffMode&8] &7» &a" + p.getDisplayName() + "&7 is now in StaffMode!"));
					}
				}
				
				p.hidePlayer(p);
				p.getInventory().clear();
				p.setGameMode(GameMode.CREATIVE);
				
				//Lores
				List<String> compassLore = new ArrayList<>();
				compassLore.add(ChatColor.GRAY + "Right-Click to teleport to a random player!");
				
				List<String> axeLore = new ArrayList<>();
				axeLore.add(ChatColor.GRAY + "Must have WorldEdit Permissions!");
				
				List<String> bookLore = new ArrayList<>();
				bookLore.add(ChatColor.GRAY + "Right-Click a player to see");
				bookLore.add(ChatColor.GRAY + "their inventory and IP!");
				
				List<String> pickLore = new ArrayList<>();
				pickLore.add(ChatColor.GRAY + "Right-Click to teleport to a");
				pickLore.add(ChatColor.GRAY + "under Y:20!");
				
				List<String> starLore = new ArrayList<>();
				starLore.add(ChatColor.GRAY + "View reports made by a player");
				
				//ItemStacks
				ItemStack compass;
				ItemMeta compassMeta;
				compass = new ItemStack(Material.COMPASS);
				compassMeta = compass.getItemMeta();
				compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dRandom Teleport Tool"));
				compassMeta.setLore(compassLore);
				compass.setItemMeta(compassMeta);
				
				ItemStack axe;
				ItemMeta axeMeta;
				axe = new ItemStack(Material.WOOD_AXE);
				axeMeta = axe.getItemMeta();
				axeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				axeMeta.setLore(axeLore);
				axeMeta.setDisplayName(ChatColor.DARK_PURPLE + "WorldEdit Tool");
				axe.setItemMeta(axeMeta);
				
				ItemStack book;
				ItemMeta bookMeta;
				book = new ItemStack(Material.BOOK);
				bookMeta = book.getItemMeta();
				bookMeta.setDisplayName(ChatColor.YELLOW + "Inspection Tool");
				bookMeta.setLore(bookLore);
				book.setItemMeta(bookMeta);
				
				ItemStack pick;
				ItemMeta pickMeta;
				pick = new ItemStack(Material.WOOD_PICKAXE);
				pickMeta = pick.getItemMeta();
				pickMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				pickMeta.setDisplayName(ChatColor.WHITE + "X-Ray Catcher");
				pickMeta.setLore(pickLore);
				pick.setItemMeta(pickMeta);
				
				ItemStack star;
				ItemMeta starMeta;
				star = new ItemStack(Material.NETHER_STAR);
				starMeta = star.getItemMeta();
				starMeta.setDisplayName(ChatColor.AQUA + "Player Reports");
				starMeta.setLore(starLore);
				
				//Putting the items in inventory (dumbass)
				p.getInventory().setItem(0, compass);
				p.getInventory().setItem(2, axe);
				p.getInventory().setItem(4, book);
				p.getInventory().setItem(6, pick);
				p.getInventory().setItem(8, star);
				p.updateInventory();
				
			}
			
		}
		
		return false;
	}

}
