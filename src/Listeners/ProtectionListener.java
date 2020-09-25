/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Managers.ProtectionManager;
import Managers.TerrainsManager;
import Terrains.Terr;
import Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import src.Main;

/**
 *
 * @author Bogar
 */
public class ProtectionListener implements Listener{
    
    private Main plugin;
    
    public ProtectionListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onSelectionInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack iih = p.getInventory().getItemInMainHand();
        
        if(iih.getType()==Material.GOLDEN_AXE){
            int index = Main.getPlayerIndex(p.getName());
            if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
                Location loc = e.getClickedBlock().getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                Main.playerData.get(index).protectionSelect[1] = loc;
                p.sendMessage(Utils.chat("&2Point 1 set on: X&f: " + x + " &2Y&f: " + y + " &2Z&f: " + z));
                if(Main.playerData.get(index).protectionSelect[0]!=null){
                    int vol = TerrainsManager.calculateVolume(loc, Main.playerData.get(index).protectionSelect[0]);
                    p.sendMessage(Utils.chat("&2Claim &f" + vol + " &2blocks for: &f" + Integer.toString((int)(vol*Terr.PRICE_PER_BLOCK)) + "$"));
                }
            }
            if(e.getAction()==Action.LEFT_CLICK_BLOCK){
                Location loc = e.getClickedBlock().getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                Main.playerData.get(index).protectionSelect[0] = loc;
                p.sendMessage(Utils.chat("&2Point 2 set on: X&f: " + x + " &2Y&f: " + y + " &2Z&f: " + z));
                if(Main.playerData.get(index).protectionSelect[1]!=null){
                    int vol = TerrainsManager.calculateVolume(loc, Main.playerData.get(index).protectionSelect[1]);
                    p.sendMessage(Utils.chat("&2Claim &f" + vol + " &2blocks for: &f" + Integer.toString((int)(vol*Terr.PRICE_PER_BLOCK)) + "$"));
                }
            }
        }
        
        if(Terr.terrains2.size()>0){
            if(e.getAction() == Action.LEFT_CLICK_BLOCK){
                Location l = e.getClickedBlock().getLocation();
                for(Terr t : Terr.terrains2){
                    if(Terr.isOnTerrain(l, t)){
                        if(t.isOwner(e.getPlayer().getName()) || e.getPlayer().isOp()){

                        }else{
                            e.getPlayer().sendMessage(Utils.chat("&cYou have no permission for breaking here"));
                            e.setCancelled(true);
                        }

                    }
                }
            }
            
        }
        
        /*if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(e.getClickedBlock().getType()==Material.BEDROCK ){
                Inventory i = plugin.getServer().createInventory(null, 18, ChatColor.BLUE + "Tutorialon");
                e.getPlayer().openInventory(i);
                
            }
            
        }*/
    }
    
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(Terr.terrains2.size()>0){
            Location l = e.getBlock().getLocation();
            for(Terr t : Terr.terrains2){
                if(Terr.isOnTerrain(l, t)){
                    if(t.isOwner(e.getPlayer().getName()) || e.getPlayer().isOp()){
                        
                    }else{
                        e.getPlayer().sendMessage(Utils.chat("&cYou have no permission for breaking here"));
                        e.setCancelled(true);
                    }
                    
                }
            }
        }
        /*int index = Main.getPlayerIndex(e.getPlayer().getName());
        //e.getPlayer().sendMessage(Utils.chat("&cNosepasedeberga puto"));
        if(Main.terrains.size()>0){
            if(TerrainsManager.isOnTerrain(e.getBlock().getLocation(), Main.playerData.get(index).getClosestTerrain())&&!TerrainsManager.isowner(e.getPlayer().getName(), Main.playerData.get(index).getClosestTerrain()) && !e.getPlayer().isOp()){
                e.getPlayer().sendMessage(Utils.chat("&cNosepasedeberga puto"));
                e.setCancelled(true);
            }
        }
        */
        
    }
    
    @EventHandler
    public void onBreak(BlockPlaceEvent e){
        if(Terr.terrains2.size()>0){
            Location l = e.getBlock().getLocation();
            for(Terr t : Terr.terrains2){
                if(Terr.isOnTerrain(l, t)){
                    if(t.isOwner(e.getPlayer().getName()) || e.getPlayer().isOp()){
                        
                    }else{
                        e.getPlayer().sendMessage(Utils.chat("&cYou have no permission for breaking here"));
                        e.setCancelled(true);
                    }
                    
                }
            }
        }
        /*int index = Main.getPlayerIndex(e.getPlayer().getName());
        //e.getPlayer().sendMessage(Utils.chat("&cNosepasedeberga puto"));
        if(Main.terrains.size()>0){
            if(TerrainsManager.isOnTerrain(e.getBlock().getLocation(), Main.playerData.get(index).getClosestTerrain()) &&!TerrainsManager.isowner(e.getPlayer().getName(), Main.playerData.get(index).getClosestTerrain())&& !e.getPlayer().isOp()){
                e.getPlayer().sendMessage(Utils.chat("&cNosepasedeberga puto"));
                e.setCancelled(true);
            }
        }
        */
        
    }
    
    @EventHandler
    public void onExplosion(EntityExplodeEvent e){
        if(Terr.terrains2.size()>0){
            for(Terr t : Terr.terrains2){
                if(Terr.isOnTerrain(e.getLocation(), t)){
                    if(t.isProtected()){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
    
}
