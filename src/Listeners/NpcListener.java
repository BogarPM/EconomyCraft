/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Mobs.WarpNpc;
import Utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import src.Main;

/**
 *
 * @author Bogar
 */
public class NpcListener implements Listener{
    
    private Main plugin;
    
    public NpcListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onNpcInteract(PlayerInteractEntityEvent e){
        //e.getPlayer().sendMessage(e.getRightClicked().getType().toString());
        //e.getPlayer().sendMessage(EntityType.VILLAGER.toString());
        if(e.getRightClicked().getType().equals(EntityType.VILLAGER)){
            if(e.getRightClicked().getCustomName().equalsIgnoreCase(WarpNpc.getNpcName())){
                e.getPlayer().sendMessage(Utils.chat("&2Select your destination"));
                e.setCancelled(true);
                e.getPlayer().openInventory(WarpNpc.getWarpInventory());
                
            }
            //e.getPlayer().openInventory(WarpNpc.getWarpInventory());
        }
    }
}
