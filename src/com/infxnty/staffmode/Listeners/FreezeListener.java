package com.infxnty.staffmode.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.infxnty.staffmode.Main;

import me.clip.placeholderapi.PlaceholderAPI;

public class FreezeListener implements Listener {
	
	private Main main;
	public FreezeListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if (Main.frozen.contains(p)) {
			e.setTo(e.getFrom());
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		String bancommand = main.getConfig().getString("frozenLogout");
		String wpholder = PlaceholderAPI.setPlaceholders(e.getPlayer(), bancommand);
		
		if (Main.frozen.contains(p)) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), wpholder);
		}
		
	}
	
	//In case they were PvPing/Falling
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if (e.getEntity() instanceof Player && Main.frozen.contains(e.getEntity())) {
			e.setCancelled(true);
		}
	}
	
}
