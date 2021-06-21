package dev.expectatives.viperelevators;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import dev.expectatives.viperelevators.file.ConfigFile;
import dev.expectatives.viperelevators.listener.ElevatorsListener;
import dev.expectatives.viperelevators.util.CC;

/**
 * @author Cristhian (Expectatives)
 * lunes, junio 21, 2021
 */

public class Main extends JavaPlugin {
    private static Main plugin;
    PluginDescriptionFile pdffile = getDescription();
    public String version = pdffile.getVersion();
    public String name = CC.translate("&7[&6"+pdffile.getName()+"&7]");
	
    public void onEnable() {
    	CC.log(name+"&7&m----------------------------------------");
    	CC.log(name+"&6&l   ViperElevators Plugin");
    	CC.log(name+"");
    	CC.log(name+" &fHas been enabled (version: &e"+version+"&f)");
    	CC.log(name+" &fPlugin made by: &eExpectatives#1157");
    	CC.log(name+"");
    	Main.plugin = this;
    	CC.log(name+" &eRegistering config.yml file...");
    	new ConfigFile();
    	CC.log(name+" &aSuccessfully registered config.yml!");
    	CC.log(name+" &eRegistering listener...");
    	final PluginManager pm = Bukkit.getServer().getPluginManager();
    	pm.registerEvents((Listener)new ElevatorsListener(), (Plugin)this);
    	CC.log(name+" &aSuccessfully registered listener!");
    	CC.log(name+" &aPlugin load &fall &aelevators signs!");
    	CC.log(name+"");
    	CC.log(name+" &fDiscord Server: &ehttps://dsc.gg/faithcommunity/");
    	CC.log(name+"&7&m----------------------------------------");
    }
    
    public void onDisable() {
    	CC.log(name+" &cSuccessfully disable plugin!");
    }
    
	public static Main getPlugin() {
        return Main.plugin;
    }
}
