/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import src.Main;

/**
 *
 * @author Bogar
 */
public class MobKillListener implements Listener{
    
    private Main plugin;
    public static ItemStack coin;
    
    public MobKillListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        coin = new ItemStack(Material.GOLD_NUGGET,1);
        ItemMeta meta = coin.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Ancient Coin");
        coin.setItemMeta(meta);
        //coin.addEnchantment(Enchantment.KNOCKBACK, 1);
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Monster){
            //e.getEntity().getKiller().sendMessage("puto");
            Player p = e.getEntity().getKiller();
            if(p!=null){
                double ran = Math.random()*1000;
                if(ran<=25){
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), coin);
                }
                
            }
            
        }
    }
    
}
