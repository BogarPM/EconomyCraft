/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import src.Main;

/**
 *
 * @author Bogar
 */
public class SpawnListener implements Listener{
    
    private Main plugin;
    
    public SpawnListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void ondeathSpawn(PlayerRespawnEvent e){
        if(e.isBedSpawn()){
            e.setRespawnLocation(Bukkit.getWorld("world").getSpawnLocation());
        }else{
            
        }
    }
}
