package dev.expectatives.viperelevators.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Cristhian (Expectatives)
 * lunes, junio 21, 2021
 */

public class CC {
	
    public static String translate(String i) {
        return ChatColor.translateAlternateColorCodes('&', i);
    }
    public static void log(String i) {
        Bukkit.getConsoleSender().sendMessage(translate(i));
    }
}
