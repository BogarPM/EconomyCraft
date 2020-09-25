/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Managers.EconomyManager;
import Managers.ShopManager;
import Utils.Utils;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import src.Main;

/**
 *
 * @author Bogar
 */
public class SignsListener implements Listener{
    private Main plugin;
    
    public SignsListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onSignInteract(PlayerInteractEvent e){
        if(e.getAction()==Action.RIGHT_CLICK_BLOCK){
            Block b = e.getClickedBlock();
            BlockState state = b.getState();
            if(state instanceof Sign){
                Sign s = (Sign) state;
                if(s.getLine(0).equalsIgnoreCase("[BuyLvl]")){
                    try {
                        double cost = ShopManager.getNextLevelCost(e.getPlayer().getLevel());
                        if(EconomyManager.getPlayerMoney(e.getPlayer().getName())>=cost){
                            EconomyManager.subtractMoney(e.getPlayer().getName(), cost);
                            e.getPlayer().setLevel(e.getPlayer().getLevel()+1);
                            NumberFormat nf = new DecimalFormat("#0.00");
                            e.getPlayer().sendMessage(Utils.chat("&2You bought 1 level for &f" + nf.format(cost) + "$"));
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
                        }else{
                            e.getPlayer().sendMessage(Utils.chat("&cYou have not enough founds"));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(SignsListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
                
        }
        
    }
}
