package com.infxnty.staffmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.infxnty.staffmode.Commands.ModeCommand;
import com.infxnty.staffmode.Commands.StaffChat;
import com.infxnty.staffmode.Listeners.StaffModeListener;
import com.infxnty.staffmode.Misc.PlayerTeleporter;
import com.infxnty.staffmode.Util.User;

public class Main extends JavaPlugin implements Listener {
		
	public static HashMap<UUID, User> USERS = new HashMap<>();
	
	public static ArrayList<Player> staffmode = new ArrayList<>();
	
	//Permissions
	/*
	 * staffmode.chat
	 * staffmode.use.compass
	 * staffmode.login
	 * staffmode.use
	 * staffmode.notify
	 *  
	 */
	
	@Override
	public void onEnable() {
		
		//Setting up Config
		this.getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		//Printing to console on Start
		String start = "&7[&1Infxnty&7] &StaffMode has been enabled!";
		System.out.println(ChatColor.translateAlternateColorCodes('&', start));
		
		//Registering classes
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new PlayerTeleporter(), this);
		pm.registerEvents(new StaffModeListener(), this);
		getCommand("sc").setExecutor(new StaffChat());
		getCommand("staffmode").setExecutor(new ModeCommand());
		
		//Registering users
		for (Player p : Bukkit.getOnlinePlayers())
			USERS.put(p.getUniqueId(), new User(p));
	
	}
	
}