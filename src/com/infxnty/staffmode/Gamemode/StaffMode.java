package com.infxnty.staffmode.Gamemode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffMode {
	
	//JUST A PLACEHOLDER
	
	public void staffMode(Player p) {
		
			
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
			compass = new ItemStack(Material.DISPENSER);
			compassMeta = compass.getItemMeta();
			compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&dRandom Teleport Tool"));
			compassMeta.setLore(compassLore);
			compass.setItemMeta(compassMeta);
			
			ItemStack axe;
			ItemMeta axeMeta;
			axe = new ItemStack(Material.WOOD_AXE);
			axeMeta = axe.getItemMeta();
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
