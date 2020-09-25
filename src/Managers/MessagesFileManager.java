/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Utils.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import src.DataBase;
import src.Main;

/**
 *
 * @author Bogar
 */
public class MessagesFileManager {
    private static Main plugin = Main.getPlugin(Main.class);
    public static String fileName = "Messages.yml";
    private static FileConfiguration messagesCfg;
    private static File messagesFile;
    private int nMsg = 6;
    
    public MessagesFileManager(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new MessagesTimer(), 200, 12000);
    }
    
    
    public static void setup(){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        messagesFile = new File(plugin.getDataFolder(),fileName);
        if(!messagesFile.exists()){
            try {
                messagesFile.createNewFile();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "terrains.yml file created!");
            } catch (IOException ex) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create terrains.yml file");
            }
        }
        messagesCfg = YamlConfiguration.loadConfiguration(messagesFile);
        
        //Set default Fields
        messagesCfg.set("default.firstJoinGlobal", "&6Welcome &5<Player> &6 to &0Deb&4Lin&fMc");
        saveCfg();
    }
    
    public static void saveCfg(){
        try {
            messagesCfg.save(messagesFile);
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "terrains.yml file saved!");
        } catch (IOException ex) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save terrains.yml file!");
        }
    }
    
    public static FileConfiguration getMessagesConfig(){
        return messagesCfg;
    }
    
    public static void startCfg(){
        setup();
        saveCfg();
    }
    
    public static void broadcastMessage(int id){
        //messagesCfg.get
    }
    
    private class MessagesTimer extends TimerTask{

        @Override
        public void run() {
            int ran = (int) (Math.random()*nMsg) + 1;
            String msg = "msg" + ran;
            Bukkit.broadcastMessage(Utils.chat(messagesCfg.get(msg).toString()));
            try {DataBase.reload();} catch (SQLException ex) {}
        }
        
    }
    
    
}
