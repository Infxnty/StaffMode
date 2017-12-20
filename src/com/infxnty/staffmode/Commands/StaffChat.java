package com.infxnty.staffmode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		if(args.length >= 1) {
			if (p.hasPermission("staffmode.chat")) {
				
				StringBuilder message = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					message.append(args[i]).append(" ");
				}
				
				String scmessage = ChatColor.translateAlternateColorCodes('&', "&8[&bStaffChat&8] &a"
						 + p.getDisplayName() + " &7» " + message.toString());
			
				for (Player pl : Bukkit.getOnlinePlayers()) 
					if (pl.hasPermission("staffmode.chat")) 
						pl.sendMessage(scmessage);
				
			} else {
				p.sendMessage(ChatColor.RED + "Insufficient permission!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Invalid usage! Use /sc <message>");
		}
		return false;
	}

}
