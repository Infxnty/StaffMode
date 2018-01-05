package com.infxnty.staffmode.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.infxnty.staffmode.Main;

public class Report implements CommandExecutor {
	
	private Main main;
	public Report(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String reportperm = main.getConfig().getString("reportPerm");
		String prefix = main.getConfig().getString("prefix");
		Player reporter = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("report")) {
				if (reporter.hasPermission(reportperm)) {
					if (args.length == 0) {
						reporter.sendMessage(ChatColor.RED + "Invalid usage. Use /report <player> <reason> instead.");
					} else {
						if (args.length >= 2) {
							Player reported = Bukkit.getPlayerExact(args[0]);
							if (Bukkit.getPlayerExact(args[0]) != null && !(Main.reports.contains(reported.getDisplayName()))) {
								StringBuilder reason = new StringBuilder();
								for (int i = 1; i < args.length; i++) {
									reason.append(args[i]).append(" ");
								}
								reporter.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "You have reported &c" + reported.getDisplayName() + " &7for: &b" + reason.toString()));
								for (Player p : Bukkit.getOnlinePlayers()) {
									if (p.hasPermission("staffmode.notify")) {
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&a" + reporter.getDisplayName() + " &7has reported &c" + reported.getDisplayName() + " &7for: &b" + reason.toString()));
									}
								}
								
								Main.reports.add(reported.getDisplayName());
								
								//Skulls for the GUI
								List<String> sL = new ArrayList<>();
								sL.add(ChatColor.translateAlternateColorCodes('&', "&7Player Name: &c" + reported.getDisplayName()));
								sL.add(ChatColor.translateAlternateColorCodes('&', "&7Reason: " + reason.toString()));
								sL.add(ChatColor.GRAY + "Right-Click to dismiss!");
								ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
								SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
								skullMeta.setOwner(reported.getDisplayName());
								skullMeta.setDisplayName(ChatColor.AQUA + "Report");
								skullMeta.setLore(sL);
								skull.setItemMeta(skullMeta);
								
								Main.reportUI.addItem(skull);
								
							}	
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
