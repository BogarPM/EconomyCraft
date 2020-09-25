/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Terrains.Terr;
import Utils.Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.Main;

/**
 *
 * @author Bogar
 */
public class TerrainCommands implements CommandExecutor{
    
    Main plugin = Main.getPlugin(Main.class);
    public final String terrains = "terrains";
    private String info = "info";
    private String rename = "rename";
    private String expand = "info";
    private String permissions = "permissions";
    /*
    Sub commands:
    list
    info
    rename
    permissions
    help
    */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        if(cmd.getName().equalsIgnoreCase(terrains)){
            switch(args.length){
                case 0:
                    cs.sendMessage(Utils.chat("&2Usage: &b/terrains &f <list/info/help/permissions> <options>"));   //Help command
                    break;
                case 1:
                    if(cs instanceof Player){
                        Player p = (Player)cs;
                        if(args[0].equalsIgnoreCase(info)){
                            try {
                                ArrayList<Terr> terrains = Terr.getTerrains(p.getName());
                                p.sendMessage(Utils.chat("&2You own the next Terrains:"));
                                int totalvolume = 0;
                                for(Terr t : terrains){
                                    int vol = t.calculateVolume();
                                    totalvolume += vol;
                                    p.sendMessage(Utils.chat("&5" + t.getName() + " &2Volume: &f" + vol + ""));
                                }
                                p.sendMessage(Utils.chat("&2Total owned blocks&f: " + totalvolume));
                            } catch (SQLException ex) {

                            }
                        }else if(args[0].equalsIgnoreCase(expand)){
                            cs.sendMessage(Utils.chat("&2Usage: &b/" + terrains + " expand &f<terrain Name> <direction> <blocks> &2for expand"));
                            cs.sendMessage(Utils.chat("&2Usage: &b/" + terrains + " expand &f<terrain Name> <direction> &2for consulting costs"));
                        }else if(args[0].equalsIgnoreCase(rename)){
                            cs.sendMessage(Utils.chat("&2Usage: &b/" + terrains + " rename &f<terrain Name> <new name>"));
                        }
                        
                        
                            
                        
                    }else{return true;}
                    break;
                    
                case 2:
                    
                    break;
                    
                case 3:
                    if(cs instanceof Player){
                        Player p = (Player)cs;
                        if(args[0].equalsIgnoreCase(expand)){
                            String terrname = args[1];
                            String dir = args[2].substring(0, 1).toLowerCase();
                            int diri = getDir(dir);
                            try {
                                Terr terrain = Terr.getTerrain(p.getName(), terrname);
                                int area = terrain.getArea(diri);
                                double value = area*Terr.PRICE_PER_BLOCK;
                                p.sendMessage(Utils.chat("&2Expanding &2" + terrname + "&2 in &b" + args[2] + " &2 direction costs&f: " + value + "$ &2per block"));
                            } catch (SQLException ex) {
                                
                            }
                        }else if(args[0].equalsIgnoreCase(string)){
                            
                        }
                    }
                    
                    break;
                    
                default:
                    cs.sendMessage(Utils.chat("&2Usage: &b/terrains &f <list/info/help/permissions> <options>"));   //Help command
                    break;
            }
        }
        
        return true;
    }
    
    private int getDir(String direc){
        int dir = 0;
        if(direc.equals("u")){
            return Terr.UP;
        }else if(direc.equals("n")){
            return Terr.NORTH;
        }else if(direc.equals("s")){
            return Terr.SOUTH;
        }else if(direc.equals("e")){
            return Terr.EAST;
        }else if(direc.equals("w")){
            return Terr.WEST;
        }else if(direc.equals("d")){
            return Terr.DOWN;
        }else{
            dir = -1;
        }
        return dir;
    }
    
}
