package com.infxnty.staffmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.infxnty.staffmode.Commands.ChatCommand;
import com.infxnty.staffmode.Commands.Freeze;
import com.infxnty.staffmode.Commands.Inspect;
import com.infxnty.staffmode.Commands.ModeCommand;
import com.infxnty.staffmode.Commands.Report;
import com.infxnty.staffmode.Commands.StaffChat;
import com.infxnty.staffmode.Listeners.ChatListener;
import com.infxnty.staffmode.Listeners.FreezeListener;
import com.infxnty.staffmode.Listeners.InspectListener;
import com.infxnty.staffmode.Listeners.ReportListener;
import com.infxnty.staffmode.Listeners.StaffModeListener;
import com.infxnty.staffmode.Misc.PlayerTeleporter;
import com.infxnty.staffmode.Misc.XRayCatcher;
import com.infxnty.staffmode.Util.User;

public class Main extends JavaPlugin implements Listener {
	
	//Arrays
	public static HashMap<UUID, User> USERS = new HashMap<>();
	public HashMap<Player, Long> chatCooldown = new HashMap<>();
	public static ArrayList<String> reports = new ArrayList<>();
	public static ArrayList<Player> staffchat = new ArrayList<>();
	public static ArrayList<Player> staffmode = new ArrayList<>();
	public static ArrayList<Player> frozen = new ArrayList<>();
	public static ArrayList<Player> locked = new ArrayList<>();
	
	//Variables for slowchat
	public boolean slowchat;
	
	//Inventories
	public static Inventory reportUI = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&bPlayer Reports"));
	
	//Permissions
	/*
	 * staffmode.chat
	 * staffmode.chat.lock
	 * staffmode.chat.clear
	 * staffmode.chat.slow
	 * staffmode.see
	 * staffmode.use
	 * staffmode.use.compass
	 * staffmode.use.star
	 * staffmode.use.xray
	 * staffmode.use.inspect
	 * staffmode.login
	 * staffmode.notify
	 * staffmode.freeze
	 * staffmode.report
	 * staffmode.inspect
	 *  
	 */
	
	//Command errors To-Do
	/*
	 * Donezoed beatch
	 * 
	 */
	
	//To-Do List
	/*
	 * If a player breaks 10+ diamond ore, sends an alert to staff
	 * Stop clearing array lists on reload/disable
	 * Add /staffmode help
	 * Redo certain messages with PAPI
	 * 
	 */
	
	@Override
	public void onEnable() {
		
		//Setting up Config
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		//Printing to console on Start
		String start = "&7[ &1Infxnty &7] &aStaffMode has been enabled!";
		System.out.println(ChatColor.translateAlternateColorCodes('&', start));
		
		//Registering classes
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			PluginManager pm = Bukkit.getPluginManager();
			pm.registerEvents(this, this);
			pm.registerEvents(new PlayerTeleporter(this), this);
			pm.registerEvents(new StaffModeListener(this), this);
			pm.registerEvents(new FreezeListener(this), this);
			pm.registerEvents(new ChatListener(this), this);
			pm.registerEvents(new XRayCatcher(this), this);
			pm.registerEvents(new ReportListener(this), this);
			pm.registerEvents(new InspectListener(this), this);
			getCommand("sc").setExecutor(new StaffChat(this));
			getCommand("staffmode").setExecutor(new ModeCommand(this));
			getCommand("freeze").setExecutor(new Freeze(this));
			getCommand("chat").setExecutor(new ChatCommand(this));
			getCommand("report").setExecutor(new Report(this));
			getCommand("inspect").setExecutor(new Inspect(this));
		} else {
			throw new RuntimeException("Cound not find PlaceholderAPI! StaffMode will not work properly without it.");
		}
		
		//Adding PAPI expansion
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			public void run() {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "papi ecloud download player");
			}
		}, 1000L);
		
		//Registering users
		for (Player p : Bukkit.getOnlinePlayers())
			USERS.put(p.getUniqueId(), new User(p));
	
	}
	
	@Override
	public void onDisable() {
	}
	
	
}