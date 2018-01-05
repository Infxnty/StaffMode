package com.infxnty.staffmode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.infxnty.staffmode.Main;
import com.infxnty.staffmode.Util.ActionBar;

public class Freeze implements CommandExecutor {
	
	private Main main;
	public Freeze(Main main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		String prefix = main.getConfig().getString("prefix");
		String freezeperm = main.getConfig().getString("freeze");
		String notifyperm = main.getConfig().getString("notify");
		String frozenmessage = main.getConfig().getString("frozen");
		String frozenAB = main.getConfig().getString("frozenActionBar");
		String unfreeze = main.getConfig().getString("unFreeze");
		if (sender instanceof Player) {
		if (cmd.getName().equalsIgnoreCase("freeze")) {
				if (p.hasPermission(freezeperm)) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "Invalid usage! Do /freeze <player> instead!");
					} else {
						if (Bukkit.getPlayerExact(args[0]) != null) {
							Player target = (Player) Bukkit.getPlayerExact(args[0]);
							if (Main.frozen.contains(target)) {
								Main.frozen.remove(target);
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + unfreeze));
							} else {
								Main.frozen.add(target);
								ActionBar instaffmode = new ActionBar(ChatColor.translateAlternateColorCodes('&', frozenAB));
								Bukkit.getScheduler().scheduleAsyncRepeatingTask(main, new Runnable() {
									public void run() {
										if (Main.frozen.contains(target)) {
											instaffmode.sendToPlayer(target);
										}
									}
								}, 30L, 0L);
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + frozenmessage));
								for (Player pl : Bukkit.getOnlinePlayers()) {
									if (pl.hasPermission(notifyperm)) {
										pl.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&a" + p.getDisplayName() + "&7 has frozen &c" + target.getDisplayName() + "&7 for inspection!"));
									}
								}
							}
						} else {
							p.sendMessage(ChatColor.RED + "That player is not online!");
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "Invalid permission!");
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
		}
		return false;
	}

}