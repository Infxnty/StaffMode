package com.infxnty.staffmode.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.infxnty.staffmode.Main;

public class ReportListener implements Listener {
	
	private Main main;
	public ReportListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		String useperm = main.getConfig().getString("useStar");
		
		if (p.getItemInHand().getType().equals(Material.NETHER_STAR) && p.hasPermission(useperm) && Main.staffmode.contains(p)) {
			p.openInventory(Main.reportUI);
		}
		
	}
	
	@EventHandler
	public void invClick(InventoryClickEvent e) {
		
		String useperm = main.getConfig().getString("useStar");
		
		//Getting the reported player
		ItemStack clicked = e.getCurrentItem();
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

		String reported = skullMeta.getDisplayName();
		
		if (e.isRightClick() && e.getWhoClicked().hasPermission(useperm) && Main.staffmode.contains(e.getWhoClicked()) && e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Player Reports")) {
			
			Main.reportUI.removeItem(clicked);
			Main.reports.remove(reported);
		}
			
	}
	
}
