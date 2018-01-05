package com.infxnty.staffmode.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.infxnty.staffmode.Main;

public class ChatListener implements Listener {
	
	private Main main;
	public ChatListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		String chatprefix = main.getConfig().getString("chatPrefix");
		String chatperm = main.getConfig().getString("staffChatPerm");
		
		Player p = e.getPlayer();
		
		if (Main.locked.contains(p)) {
			if (!(p.hasPermission(chatperm))) {	
				e.setCancelled(true);
			}
		}
		
		if (Main.staffchat.contains(p)) {
			String pmessage = e.getMessage();
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.sendMessage(ChatColor.translateAlternateColorCodes('&', chatprefix + "&a" + p.getDisplayName() + " &7» " + pmessage));
			}
			e.setCancelled(true);
		}
		
	}
	
}
