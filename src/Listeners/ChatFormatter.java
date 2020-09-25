/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import src.Main;

/**
 *
 * @author Bogar
 */
public class ChatFormatter implements Listener{
    
    
    private Main plugin;
    
    public ChatFormatter(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        //e.getPlayer().sendMessage(e.getFormat());
        //e.getPlayer().sendMessage(e.getMessage());
        //e.getPlayer().sendMessage(e.getFormat());
        if(e.getPlayer().isOp()){
            e.setFormat(Utils.chat("&4%1$s&f:&7 %2$s"));
        }else{
            e.setFormat(Utils.chat("&2%1$s&f:&7 %2$s"));
        }
        
        
    }
}
