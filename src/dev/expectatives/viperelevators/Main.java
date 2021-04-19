package dev.expectatives.viperelevators;

import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.*;

public class Main extends JavaPlugin implements Listener
{	
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String nombre = ChatColor.GRAY+"["+ChatColor.GOLD+pdffile.getName()+ChatColor.GRAY+"]";
	
    public void onEnable() {
    	Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GRAY+"----------------------------------------");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GOLD+"   ViperElevators Plugin");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GRAY+"");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" Has been enabled (version: "+ChatColor.YELLOW+version+ChatColor.WHITE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" Plugin made by: "+ChatColor.YELLOW+"Expectatives#1157");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GRAY+"");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+" Registering config.yml file...");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GREEN+" Successfully registered config.yml file!");
        Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+" Registering listeners...");
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GREEN+" Successfully registered listeners!");
        Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GREEN+" Plugin load all Elevators config!");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GRAY+"");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+" Discord Server: "+ChatColor.YELLOW+"https://discord.faithcommunity.club/");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GRAY+"----------------------------------------");
    }
    
    public void onDisable() {
    	Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+" Plugin disable!");
    }
    
    @EventHandler
    public void signUpdate(final SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[Elevator]")) {
            if (event.getLine(1).equalsIgnoreCase("Up")) {
                event.setLine(0, "");
                event.setLine(2, "Up");
            }
            else if (event.getLine(1).equalsIgnoreCase("Down")) {
                event.setLine(0, "");
                event.setLine(2, "Down");
            }
            else {
                event.getBlock().breakNaturally();
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ELEVATOR_SIGN.BREAK_MESSAGE")));
            }
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ELEVATOR_SIGN.TITLE")));
        }
    }
    
    @EventHandler
    public void PlayerInteractEvent(final PlayerInteractEvent event) {
        final boolean ret = event.getClickedBlock() == null;
        if (ret) {
            return;
        }
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WALL_SIGN) || event.getClickedBlock().getType() == Material.SIGN_POST) {
            final Sign sing = (Sign)event.getClickedBlock().getState();
            if (sing.getLine(1).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ELEVATOR_SIGN.TITLE")))) {
                if (sing.getLine(2).equalsIgnoreCase("Up")) {
                    final Location loc = event.getClickedBlock().getLocation().add(0.0, 1.0, 0.0);
                    while (loc.getY() < 254.0) {
                        if (loc.getBlock().getType() != Material.AIR) {
                            while (loc.getBlockY() < 254) {
                                if (loc.getBlock().getType() == Material.AIR && loc.add(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                                    final Location pl = event.getPlayer().getLocation();
                                    event.getPlayer().teleport(new Location(pl.getWorld(), loc.getX() + 0.5, loc.getY() - 1.0, loc.getZ() + 0.5, pl.getYaw(), pl.getPitch()));
                                    break;
                                }
                                loc.add(0.0, 1.0, 0.0);
                            }
                            break;
                        }
                        loc.add(0.0, 1.0, 0.0);
                    }
                    if (loc.getY() == 254.0) {
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ELEVATOR_SIGN.NO_FOUND_MESSAGE")));
                    }
                }
                else if (sing.getLine(2).equalsIgnoreCase("Down")) {
                    final Location loc = event.getClickedBlock().getLocation().subtract(0.0, 1.0, 0.0);
                    while (loc.getY() > 2.0) {
                        if (loc.getBlock().getType() != Material.AIR) {
                            while (loc.getY() > 2.0) {
                                if (loc.getBlock().getType() == Material.AIR && loc.subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                                    event.getPlayer().teleport(new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getY(), loc.getZ() + 0.5, event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch()));
                                    break;
                                }
                                loc.subtract(0.0, 1.0, 0.0);
                            }
                            break;
                        }
                        loc.subtract(0.0, 1.0, 0.0);
                    }
                    if (loc.getY() == 2.0) {
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ELEVATOR_SIGN.NO_FOUND_MESSAGE")));
                    }
                }
            }
        }
    }
}
