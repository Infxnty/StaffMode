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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.infxnty.staffmode.Main;
import com.infxnty.staffmode.Util.ActionBar;

public class ModeCommand implements CommandExecutor {
	
	private Main main;
	public ModeCommand(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		String modeperm = main.getConfig().getString("staffMode");
		String seeperm = main.getConfig().getString("seeVanishedPlayers");
		String notifyperm = main.getConfig().getString("notify");
		String reloadm = main.getConfig().getString("reload");
		String prefix = main.getConfig().getString("prefix");
		String smab = main.getConfig().getString("staffModeActionBar");
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("staffmode")) {
				if (p.hasPermission(modeperm)) {
					if (args.length == 0) {
						if (Main.staffmode.contains(p)) {
							Main.staffmode.remove(p);
							p.getInventory().clear();
							p.setGameMode(GameMode.SURVIVAL);
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (pl.hasPermission(notifyperm)) {
									pl.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&a" + p.getDisplayName() + "&7 is no longer in StaffMode!"));
								}
							}
						} else {
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
								if (!(pl.hasPermission(seeperm))) {
									pl.hidePlayer(p);
								}
								if (pl.hasPermission(notifyperm)) {
									pl.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&a" + p.getDisplayName() + "&7 is now in StaffMode!"));
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
					} else {
						if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
							main.reloadConfig();
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + reloadm));
					}
					
				}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
		}
		return false;
	}

}
