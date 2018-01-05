package com.infxnty.staffmode.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.infxnty.staffmode.Main;

import net.md_5.bungee.api.ChatColor;

public class InspectListener implements Listener {
	
	private Main main;
	public InspectListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Player target = (Player) e.getRightClicked();
		
		String inspectperm = main.getConfig().getString("useBook");
		
		if (e.getRightClicked() instanceof Player && Main.staffmode.contains(p) && p.hasPermission(inspectperm) && p.getItemInHand().getType().equals(Material.BOOK)) {
			p.performCommand("inspect " + target.getDisplayName());
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		String inspectTitle = main.getConfig().getString("inspect");
		
		if (e.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', inspectTitle))) {
			e.setCancelled(true);
		}
	}
	
}
