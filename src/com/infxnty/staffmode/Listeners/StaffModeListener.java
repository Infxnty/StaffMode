package com.infxnty.staffmode.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.infxnty.staffmode.Main;
import com.infxnty.staffmode.Util.ActionBar;

public class StaffModeListener implements Listener {
	
	private Main main;
	public StaffModeListener(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Boolean loginTrue = main.getConfig().getBoolean("staffModeLogin");
		String loginperm = main.getConfig().getString("login");
		String showperm = main.getConfig().getString("seeVanishedPlayers");
		String smab = main.getConfig().getString("staffModeActionBar");
		
		Player p = e.getPlayer();
		if (p.hasPermission(loginperm) && loginTrue.equals(true)) {
			Main.staffmode.add(p);
			ActionBar instaffmode = new ActionBar(ChatColor.translateAlternateColorCodes('&', smab));
			Bukkit.getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
				public void run() {
					if (Main.staffmode.contains(p)) {
						instaffmode.sendToPlayer(p);
					}
				}
			}, 30L, 0L);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (!(pl.hasPermission(showperm))) {
					pl.hidePlayer(p);
				}
			}
			
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
			starLore.add(ChatColor.GRAY + "View reports made by a player!");
			
			//ItemStacks
			
			String compassname = main.getConfig().getString("compass");
			String axename = main.getConfig().getString("axe");
			String bookname = main.getConfig().getString("book");
			String pickname = main.getConfig().getString("pick");
			String starname = main.getConfig().getString("star");
			
			ItemStack compass;
			ItemMeta compassMeta;
			compass = new ItemStack(Material.COMPASS);
			compassMeta = compass.getItemMeta();
			compassMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			compassMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			compassMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', compassname));
			compassMeta.setLore(compassLore);
			compass.setItemMeta(compassMeta);
			
			ItemStack axe;
			ItemMeta axeMeta;
			axe = new ItemStack(Material.WOOD_AXE);
			axeMeta = axe.getItemMeta();
			axeMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			axeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			axeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			axeMeta.setLore(axeLore);
			axeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', axename));
			axe.setItemMeta(axeMeta);
			
			ItemStack book;
			ItemMeta bookMeta;
			book = new ItemStack(Material.BOOK);
			bookMeta = book.getItemMeta();
			bookMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			bookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', bookname));
			bookMeta.setLore(bookLore);
			book.setItemMeta(bookMeta);
			
			ItemStack pick;
			ItemMeta pickMeta;
			pick = new ItemStack(Material.WOOD_PICKAXE);
			pickMeta = pick.getItemMeta();
			pickMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			pickMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			pickMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			pickMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', pickname));
			pickMeta.setLore(pickLore);
			pick.setItemMeta(pickMeta);
			
			ItemStack star;
			ItemMeta starMeta;
			star = new ItemStack(Material.NETHER_STAR);
			starMeta = star.getItemMeta();
			starMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', starname));
			starMeta.setLore(starLore);
			star.setItemMeta(starMeta);
			
			//Putting the items in inventory (dumbass)
			p.getInventory().setItem(0, compass);
			p.getInventory().setItem(2, axe);
			p.getInventory().setItem(4, book);
			p.getInventory().setItem(6, pick);
			p.getInventory().setItem(8, star);
			p.updateInventory();
			
		}
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		
		if (Main.staffmode.contains(p)) {
			e.setCancelled(true);
		}
	}
	
	public void onPickUp(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (Main.staffmode.contains(p)) {
			e.setCancelled(true);
		}
	}
	
}
