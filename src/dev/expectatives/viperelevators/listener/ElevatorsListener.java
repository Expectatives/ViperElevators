package dev.expectatives.viperelevators.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import dev.expectatives.viperelevators.file.ConfigFile;
import dev.expectatives.viperelevators.util.CC;

/**
 * @author Cristhian (Expectatives)
 * lunes, junio 21, 2021
 */

public class ElevatorsListener implements Listener {
    FileConfiguration config = ConfigFile.getConfig();
  
    @EventHandler
    public void onSignChange(final SignChangeEvent event) {
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
                event.getPlayer().sendMessage(CC.translate(config.getString("ELEVATOR-SIGN.BREAK-MESSAGE")));
            }
            event.setLine(1, CC.translate(config.getString("ELEVATOR-SIGN.TITLE")));
        }
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
    	if ((event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.WALL_SIGN) || (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SIGN_POST)) {
        	final Block block = event.getClickedBlock();
        	final Sign sign = (Sign)event.getClickedBlock().getState();
            if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
            	if (sign.getLine(1).equalsIgnoreCase(CC.translate(config.getString("ELEVATOR-SIGN.TITLE")))) {
                    if (sign.getLine(2).equalsIgnoreCase("Up")) {
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
                            event.getPlayer().sendMessage(CC.translate(config.getString("ELEVATOR-SIGN.NO-FOUND-MESSAGE")));
                        }
                    }
                    else if (sign.getLine(2).equalsIgnoreCase("Down")) {
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
                            event.getPlayer().sendMessage(CC.translate(config.getString("ELEVATOR-SIGN.NO-FOUND-MESSAGE")));
                        }
                    }
                }
            }
            
        }
    }
}
