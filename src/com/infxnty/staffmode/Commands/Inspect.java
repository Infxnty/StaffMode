package com.infxnty.staffmode.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import com.infxnty.staffmode.Main;

public class Inspect implements CommandExecutor {
	
	private Main main;
	public Inspect(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		String inspectperm = main.getConfig().getString("inspect");
		if (sender instanceof Player) {
		    if (cmd.getName().equalsIgnoreCase("inspect")) {
		      Player p = (Player)sender;
		      if (p.hasPermission(inspectperm)) {
		         if (args.length == 0) {
		        	 	p.sendMessage(ChatColor.RED + "Invalid usage! Do /inspect <player> instead!");
		         } else if (args.length == 1) {
		        	 	Player t = Bukkit.getPlayerExact(args[0]);
		         if (t == null) {
		        	 	p.sendMessage(ChatColor.RED + "That player isn't online!");
		         	return true;
		         }
		          
		          String inspectTitle = main.getConfig().getString("inspectGUI");
		          
		          Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', inspectTitle));
		          
		          inv.setItem(0, t.getInventory().getHelmet());
		          inv.setItem(1, t.getInventory().getChestplate());
		          inv.setItem(2, t.getInventory().getLeggings());
		          inv.setItem(3, t.getInventory().getBoots());
		          
		          List<String> lore = new ArrayList<>();
		          for (PotionEffect e : t.getActivePotionEffects()) {
		            lore.add(e.toString());
		          }
		          
		          Double health = t.getHealth();
		          String pIP = p.getAddress().getHostString();
		          Integer healthp = health.intValue() * 5;
		          Integer foodp = t.getFoodLevel() * 5;
		          
		          List<String> slore = new ArrayList<>();
		          slore.add(ChatColor.GRAY + "Health: " + healthp + "%");
		          slore.add(ChatColor.GRAY + "Food: " + foodp + "%");
		          slore.add(ChatColor.GRAY + "IP: " + pIP);
		          
		          ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		          SkullMeta sk = (SkullMeta)skull.getItemMeta();
		          sk.setOwner(t.getName());
		          sk.setDisplayName("§bInfo for " + t.getDisplayName());
		          sk.setLore(slore);
		          skull.setItemMeta(sk);
		          
		          ItemStack Potion = new ItemStack(Material.POTION, 1);
		          PotionMeta pt = (PotionMeta)Potion.getItemMeta();
		          pt.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 0, true);
		          pt.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		          pt.setDisplayName("§aEffects of " + t.getDisplayName());
		          pt.setLore(lore);
		          Potion.setItemMeta(pt);
		          
		          inv.setItem(7, Potion);
		          inv.setItem(8, skull);
		          inv.setItem(9, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(10, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(11, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(12, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(13, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(14, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(15, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(16, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          inv.setItem(17, GiveItem(Material.STAINED_GLASS_PANE, "§3", 1, 14));
		          int a = 18;
		          for (int i = 0; i < t.getInventory().getContents().length; i++) {
		            inv.setItem(a, t.getInventory().getItem(i));
		            a++;
		          }
		          
		          p.openInventory(inv);
	
		        } else {
		          p.sendMessage(ChatColor.RED + "You have the inventory of this player open!");
		        }
		      }
		      else {
		        p.sendMessage(ChatColor.RED + "Invalid permission.");
		      }
		    }
		} else {
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
		}
	    return false;
	  }
	  
	  public static ItemStack GiveItem(Material mat, String Name, int amo, int subid) {
	    ItemStack is = new ItemStack(mat, amo, (short)subid);
	    ItemMeta im = is.getItemMeta();
	    im.setDisplayName(Name);
	    is.setItemMeta(im);
	    return is;
	  }
	  
}


