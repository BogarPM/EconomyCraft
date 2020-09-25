/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobs;

import AbstractObjects.Warp;
import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.EntityVillager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import src.Main;

/**
 *
 * @author Bogar
 */
public class WarpNpc extends EntityVillager{
    
    Main plugin = Main.getPlugin(Main.class);
    
    private static Inventory warpInventory;
    private static String npcName = ChatColor.BLUE + "Warp Npc";
    private static String inventoryName = ChatColor.BLUE + WarpNpc.getNpcName() + " menu";

    public WarpNpc(Location loc) {
        super(EntityTypes.VILLAGER, ((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setInvulnerable(true);
        this.setCustomName(new ChatComponentText(npcName));
        this.setCustomNameVisible(true);
    }
    
    public static void initInventory(){
        warpInventory = Bukkit.createInventory(null, 9, inventoryName);
        if(Main.warps.size()>0){
            for(Warp w : Main.warps){
                
                ItemStack warpitem = new ItemStack(w.getIdentifier(),1);
                ItemMeta meta = warpitem.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + w.getName());
                warpitem.setItemMeta(meta);
                warpInventory.addItem(warpitem);
            }
        }
        
    }
    
    public static Inventory getWarpInventory(){
        return warpInventory;
    }
    
    public static String getNpcName(){
        return npcName;
    }
    
}


