/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import AbstractObjects.Warp;
import Mobs.WarpNpc;
import Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.inventory.InventoryOpenEvent;
import src.Main;

/**
 *
 * @author Bogar
 */
public class InventoryListener implements Listener{
    
    private Main plugin;
    
    public InventoryListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onInventory(InventoryOpenEvent e){
        //e.getPlayer().sendMessage(e.getInventory().getType().toString());
        //e.getInventory().getType();
    }
    
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        //e.getWhoClicked().sendMessage("asdasdas");
        if(e.getInventory()==WarpNpc.getWarpInventory()&&e.getClick()==ClickType.LEFT){
            Material item = e.getCurrentItem().getType();
            for(Warp w : Main.warps){
                if(item == w.getIdentifier()){
                    e.setCancelled(true);
                    e.getWhoClicked().sendMessage(Utils.chat("&2Teleporting to: &b" + w.getName()));
                    e.getWhoClicked().teleport(w.getLocation());
                    break;
                }
            }
            
        }
    }
}
