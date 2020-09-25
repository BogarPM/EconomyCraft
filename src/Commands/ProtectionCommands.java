/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import AbstractObjects.PlayerData;
import AbstractObjects.Terrain;
import Managers.EconomyManager;
import Player.PlayerD;
import Terrains.Terr;
import Utils.Utils;
import java.sql.SQLException;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.DataBase;
import src.Main;

/**
 *
 * @author hp
 */
public class ProtectionCommands implements CommandExecutor{
    Main plugin = Main.getPlugin(Main.class);
    public String claim = "claim";      //Claims a terrain
    public String terrain = "terrain";
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        if(cmd.getName().equalsIgnoreCase(claim)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                try {
                    PlayerD playData = PlayerD.getPlayerData(p.getName());
                    double money = playData.getMoney();
                    int index = Main.getPlayerIndex(p.getName());
                    PlayerData pd = Main.playerData.get(index);
                    if(pd.protectionSelect[0]!=null && pd.protectionSelect[0]!=null){
                        Location p1 = pd.protectionSelect[0];
                        Location p2 = pd.protectionSelect[1];
                        String name = "terrain_1";
                        if(args.length>0){name = args[0];}
                        Terr terr = new Terr(name,p.getName(),p1,p2);
                        int diff = Math.abs(p1.getBlockX() - p2.getBlockX());
                        if(diff<10){cs.sendMessage(Utils.chat("&cyour terrain is too small&f: &2Minimum size&f: 10x10x10"));return true;}
                        diff = Math.abs(p1.getBlockY() - p2.getBlockY());
                        if(diff<10){cs.sendMessage(Utils.chat("&cyour terrain is too small&f: &2Minimum size&f: 10x10x10"));return true;}
                        diff = Math.abs(p1.getBlockZ() - p2.getBlockZ());
                        if(diff<10){cs.sendMessage(Utils.chat("&cyour terrain is too small&f: &2Minimum size&f: 10x10x10"));return true;}
                        if(!terr.isRegistered()){
                            int vol = terr.calculateVolume();
                            double total = vol*Terr.PRICE_PER_BLOCK;
                            if(money>=total){
                                terr.register();
                                playData.subtractMoney(total);
                                cs.sendMessage(Utils.chat("&2Terrain &5" + name + " &2registered!"));
                            }else{
                                
                            }
                            
                            return true;
                        }
                        
                    }
                } catch (SQLException ex) {
                    plugin.sendConsoleMessage("&cError&f: " + ex.getMessage());
                }
                /*int index = Main.getPlayerIndex(p.getName());
                p.sendMessage(Integer.toString(index));
                PlayerData pd = Main.playerData.get(index);
                Location[] selection = pd.getProtectionSelect();
                Terrain terr;
                if(args.length>0){terr = new Terrain(args[0],p.getName(),selection[0],selection[1]);}
                else{terr = new Terrain(p.getName(),selection[0],selection[1]);}
                //p.sendMessage(Double.toString(terr.getValue()));
                String query = Utils.getInsertQuery(terr.getRegisterValues(), DataBase.TerrainsTableName);
                try {
                    if(EconomyManager.getPlayerMoney(p.getName())>=terr.getValue()){
                        DataBase.st.executeUpdate(query);
                        Main.terrains.add(terr);
                        EconomyManager.subtractMoney(p.getName(), terr.getValue());
                        p.sendMessage(Utils.chat("&2Terrain Succsefully founded"));
                    }else{
                        p.sendMessage(Utils.chat("&cYou have not enough money"));
                    }
                } catch (SQLException ex) {
                    plugin.sendConsoleMessage("&cError at claim command &f" + ex.getMessage());
                    p.sendMessage(Utils.chat("&cSomething went wrong"));
                }
                //p.sendMessage(query);*/
                return true;
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(terrain)){
            
        }
        
        
        return false;
    }
    
}
