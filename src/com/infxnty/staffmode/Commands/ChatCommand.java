package com.infxnty.staffmode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.infxnty.staffmode.Main;

public class ChatCommand implements CommandExecutor {
	
	private Main main;
	public ChatCommand (Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		String chatperm = main.getConfig().getString("staffChatPerm");
		String lockperm = main.getConfig().getString("lockChatPerm");
		String clearperm = main.getConfig().getString("clearChatPerm");
		String prefix = main.getConfig().getString("prefix");
		String chatClear = main.getConfig().getString("chatClear");
		String chatUnlock = main.getConfig().getString("chatUnlock");
		String chatLock = main.getConfig().getString("chatLock");
		String staffchatOn = main.getConfig().getString("staffchatOn");
		String staffchatOff = main.getConfig().getString("staffchatOff");
		
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("chat") && p.hasPermission(chatperm)) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "Invalid usage! Do /chat <clear, lock, or staff> instead!");
				} else {
					if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c") && p.hasPermission(clearperm))  {
						for (int x = 0; x < 1000; x++){
							Bukkit.broadcastMessage("");
						}
						Bukkit.broadcastMessage((ChatColor.translateAlternateColorCodes('&', prefix + chatClear + p.getDisplayName() + "&7.")));
					} else if (args[0].equalsIgnoreCase("clear")) {
						p.sendMessage(ChatColor.RED + "Invalid Permission!");
					}
					
					if (args[0].equalsIgnoreCase("lock") || args[0].equalsIgnoreCase("l") && p.hasPermission(lockperm)) {
						if (Main.locked.contains(p)) {
							for (Player pl : Bukkit.getOnlinePlayers()) {
								Main.locked.remove(pl);							
								Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + chatUnlock + p.getDisplayName() + "&7."));
							}
							} else {
								for (Player pl : Bukkit.getOnlinePlayers()) {
									Main.locked.add(pl);
									Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + chatLock + p.getDisplayName() + "&7."));
								}
							}
						
					} else if (args[0].equalsIgnoreCase("lock")) {
						p.sendMessage(ChatColor.RED + "Invalid Permission!");
					}
					
					if (args[0].equalsIgnoreCase("staff") || args[0].equalsIgnoreCase("s")) {
				 		if (Main.staffchat.contains(p)) {
							Main.staffchat.remove(p);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + staffchatOff));
						} else {
							Main.staffchat.add(p);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + staffchatOn));
						}
					}
					
				}
			} else {
				p.sendMessage(ChatColor.RED + "Invalid permission.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
		}
		return false;
	}

}
