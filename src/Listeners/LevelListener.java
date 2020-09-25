/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import src.Main;

/**
 *
 * @author Bogar
 */
public class LevelListener implements Listener{
    
    private Main plugin;
    
    public LevelListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onLevelUp(PlayerLevelChangeEvent e){
        Player p = e.getPlayer();
        int lvl = e.getNewLevel();
        int level = e.getPlayer().getLevel();
        int puntos = 7 + 2*level;
        
        //p.sendMessage(Double.toString(e.getPlayer().getExp()));
        double extrahearts = Math.floorDiv(lvl, 10);
        p.setMaxHealth(20 + extrahearts*2);
    }
    
}
