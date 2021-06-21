package dev.expectatives.viperelevators.file;

import org.bukkit.configuration.file.*;
import org.bukkit.plugin.*;
import dev.expectatives.viperelevators.Main;
import java.io.*;

/**
 * @author Cristhian (Expectatives)
 * lunes, junio 21, 2021
 */

public class ConfigFile extends YamlConfiguration {
    private static ConfigFile config;
    private Plugin plugin;
    private File configFile;
    
    public static ConfigFile getConfig() {
        if (ConfigFile.config == null) {
            ConfigFile.config = new ConfigFile();
        }
        return ConfigFile.config;
    }
    
    private Plugin main() {
        return (Plugin)Main.getPlugin();
    }
    
    public ConfigFile() {
        this.plugin = this.main();
        this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        if (!this.configFile.exists()) {
            this.plugin.saveResource("config.yml", false);
        }
        this.reload();
    }
    
    public void reload() {
        try {
            super.load(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void load() {
        try {
            super.save(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        this.load();
        this.reload();
    }
}
