/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import Managers.LastPosManager;
import Managers.PlayerManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import src.DataBase;
import src.Main;

/**
 *
 * @author Bogar
 */
public class DisconnectListener implements Listener{
    
    private Main plugin;
    
    public DisconnectListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        String[] field = {PlayerManager.NAME_FIELD};
        String query = Utils.Utils.getSelectQuery(field, PlayerManager.NAME_FIELD, e.getPlayer().getName(), DataBase.lastPositionTableName);
        try {
            ResultSet r = DataBase.st.executeQuery(query);
            r.first();
            String name = r.getString(PlayerManager.NAME_FIELD);
            LastPosManager.updateLastPos(e.getPlayer().getName(), e.getPlayer().getLocation());
            //Bukkit.broadcastMessage("asdasd pito");
        } catch (SQLException ex) {
            if(ex.getMessage().contains("empty")){
                try {
                    Object[] values = new Object[4];
                    values[0] = e.getPlayer().getName();
                    values[1] = e.getPlayer().getLocation().getX();
                    values[2] = e.getPlayer().getLocation().getY();
                    values[3] = e.getPlayer().getLocation().getZ();
                    String update = Utils.Utils.getInsertQuery(values, DataBase.lastPositionTableName);
                    DataBase.st.executeUpdate(update);
                } catch (SQLException ex1) {
                    plugin.sendConsoleMessage("&cError at registering last position: &f" + ex1.getMessage());
                }
                
            }else{
                plugin.sendConsoleMessage("&cError at registering last position: &f" + ex.getMessage());
            }
        }
    }
}
