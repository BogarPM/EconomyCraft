/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Managers.PlayerManager;
import Player.PlayerD;
import Utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import src.DataBase;
import src.Main;

/**
 *
 * @author Bogar
 */
public class JoinListener implements Listener{
    private Main plugin;
    
    public JoinListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        PlayerD pd = new PlayerD(p);
        try{
            if(!pd.isRegistered()){
                pd.register();
            }else{
                p.sendMessage("Holis");
                return;
            }
        }catch(SQLException ex){
            plugin.sendConsoleMessage(Utils.chat("&cError at join;&f " + ex.getMessage()));
        }
        
        e.getPlayer().sendMessage(Utils.chat("&2Welcome back to &0Deb&fLin&4Mc Economy-Vanilla Server"));
        Main.updatePlayerDataArray();
        try {
            ResultSet r = DataBase.st.executeQuery("select playername from "+DataBase.PayersTableName+" where playername = '"+e.getPlayer().getName()+"'");
            r.first();
            String name = r.getString("playername");
        } catch (SQLException ex) {
            if(ex.getMessage().contains("empty")){
                try {
                    PlayerManager.registerPlayer(p);
                } catch (SQLException ex1) {
                    plugin.sendConsoleMessage("&cError:&f "+ex1.getMessage());
                    try {DataBase.reload();} catch (SQLException ex2) {}
                }
            }
        }
    }
}
