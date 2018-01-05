package com.infxnty.staffmode.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.infxnty.staffmode.Main;

public class PlayerTeleporter implements Listener {
	
	private Main main;
	public PlayerTeleporter(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		String useperm = main.getConfig().getString("useCompass");
		
		if (p.getItemInHand().getType().equals(Material.COMPASS) && p.hasPermission(useperm) && Main.staffmode.contains(p)) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tp " + p.getDisplayName() + " @r");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Teleporting you to a random player..."));
		}
	}
	
}
