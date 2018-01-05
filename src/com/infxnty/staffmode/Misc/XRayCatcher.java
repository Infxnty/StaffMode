package com.infxnty.staffmode.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.infxnty.staffmode.Main;

public class XRayCatcher implements Listener {
	
	List<Player> underTwenty = new ArrayList<>();
	
	private Main main;
	public XRayCatcher(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		String xrayperm = main.getConfig().getString("usePick");
		
		if (!(underTwenty.isEmpty())) {
			Player chosen = underTwenty.get(ThreadLocalRandom.current().nextInt(underTwenty.size()));
			if (p.getItemInHand().getType().equals(Material.WOOD_PICKAXE) && p.hasPermission(xrayperm) && Main.staffmode.contains(p)) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tp " + p.getDisplayName() + " " + chosen.getDisplayName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Teleporting you to a random player under Y:20..."));
			}
		} else if (p.getItemInHand().getType().equals(Material.WOOD_PICKAXE) && Main.staffmode.contains(p) && p.hasPermission(xrayperm)) {
			p.sendMessage(ChatColor.RED + "There are no players under Y:20 right now!");
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().getY() <= 20) {
			underTwenty.add(p);
		}
		if (underTwenty.contains(p) && p.getLocation().getY() > 20) {
			underTwenty.remove(p);
		}
	}
	
}
