/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import AbstractObjects.PlayerData;
import AbstractObjects.Terrain;
import Managers.TerrainsManager;
import Terrains.Terr;
import Utils.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import src.Main;

/**
 *
 * @author hp
 */
public class WalkListener implements Listener{
    
    private Main plugin;
    
    public WalkListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onWalk(PlayerMoveEvent e){
        int index = Main.getPlayerIndex(e.getPlayer().getName());
        if(Main.playerData.get(index).movCount>=20){
            PlayerData pd = Main.playerData.get(index);
            Terr t = Terr.getStandedTerrain(e.getPlayer().getLocation());
            if(t!=null){
                if(!pd.enterTerrainLatch){
                    e.getPlayer().sendMessage(Utils.chat("&6Entering into &5" + t.getName()));
                    pd.enterTerrainLatch = true;
                }else{
                    
                }
            }else{
                if(pd.enterTerrainLatch){
                    e.getPlayer().sendMessage(Utils.chat("&2Wilderness"));
                    pd.enterTerrainLatch = false;
                }else{
                    
                }
            }
            
            Main.playerData.get(index).movCount = 0;
        }
        Main.playerData.get(index).movCount++;
    }
    
}
