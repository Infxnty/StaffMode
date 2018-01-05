package com.infxnty.staffmode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.infxnty.staffmode.Main;

public class StaffChat implements CommandExecutor {
	
	private Main main;
	public StaffChat(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String chatprefix = main.getConfig().getString("chatPrefix");
		String chatperm = main.getConfig().getString("staffChatPerm");
		
		Player p = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("sc")) {
				if (p.hasPermission(chatperm)) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "Invalid usage! Do /staffchat <message> instead!");
						return true;
					} else {
						if (args.length >= 1) {
								
							StringBuilder message = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								message.append(args[i]).append(" ");
							}
							
							for (Player pl : Bukkit.getOnlinePlayers()) {
								if (pl.hasPermission(chatperm)) 
									pl.sendMessage(ChatColor.translateAlternateColorCodes('&', chatprefix + "&a"
											+ p.getDisplayName() + " &7» " + message.toString()));
							}
							return true;
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
		}
		return true;
	}	
}
