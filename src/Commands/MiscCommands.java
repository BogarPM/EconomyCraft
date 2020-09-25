/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import AbstractObjects.Home;
import AbstractObjects.PlayerData;
import AbstractObjects.Terrain;
import AbstractObjects.Warp;
import Managers.EconomyManager;
import Managers.HomesManager;
import Managers.PlayerManager;
import Managers.TerrainsManager;
import Managers.WarpManager;
import Mobs.WarpNpc;
import Player.PlayerD;
import Utils.Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import src.Main;

/**
 *
 * @author Bogar
 */
public class MiscCommands  implements CommandExecutor{
    Main plugin = Main.getPlugin(Main.class);
    public String sethome = "sethome";
    public String home = "home";
    public String tpa = "tpa";
    public String walk = "walk";
    public String spawn = "spawn";
    public String test = "test";
    public String warp = "warp";
    
    
    

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        
        if(cmd.getName().equalsIgnoreCase(warp)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                if(p.hasPermission("economycraft.warp")){
                    try {
                        switch(args.length){
                            case 0:
                                cs.sendMessage(Utils.chat("&2Usage: &b/warp &f<warpName>"));
                                break;
                            default:
                                String name = "";
                                for(int i = 0;i<args.length-1;i++){name+=args[i] + " ";}
                                name+=args[args.length-1];
                                Location loc = p.getLocation();
                                Material item = p.getInventory().getItemInMainHand().getType();
                                Warp warp = new Warp(name,loc,item.toString());
                                WarpManager.registerWarp(warp);
                                Main.updateArrays();
                                cs.sendMessage(Utils.chat("&2Warp succesfully registered"));
                                break;
                        }
                        return true;
                    } catch (SQLException ex) {
                        plugin.sendConsoleMessage("&cError at registering warp:&f " + ex.getMessage());
                    }
                }
            }else{
                cs.sendMessage(Utils.chat("&cThis is an only players command"));
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(spawn)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                p.sendMessage(Utils.chat("&2Teleporting to spawn"));
                p.teleport(p.getWorld().getSpawnLocation());
            }else{
                cs.sendMessage(Utils.chat("&cThis is an only players command"));
            }
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase(sethome)){
                if(cs instanceof Player){
                    Player p = (Player)cs;
                    try {
                        PlayerD pdata = PlayerD.getPlayerData(p.getName());
                        if(pdata.getMoney()>=Home.SETHOME_VALUE){
                            String name = "home1";
                            if(args.length>0){name = args[0];}
                            Home h = new Home(p.getName(),name,p.getLocation());
                            if(h.isRegistered()){
                                h.update(p.getLocation());
                                p.sendMessage(Utils.chat("&2Home &6" + h.getName() + "&2 updated"));
                            }else{
                                h.register();
                                p.sendMessage(Utils.chat("&2Home &6" + h.getName() + "&2 registered"));
                            }
                            pdata.subtractMoney(Home.SETHOME_VALUE);
                        }else{
                            p.sendMessage(Utils.chat("&cYou have no founds to do this"));
                        }
                        return true;
                    } catch (SQLException ex) {
                        plugin.sendConsoleMessage("&cError at register home &f" + ex.getMessage());
                        
                    }
                    return true;
                }
                else{
                    cs.sendMessage(Utils.chat("&cYou cannot use this command"));
                    return false;
                }
            
        }
        
        if(cmd.getName().equalsIgnoreCase(home)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                try {
                    PlayerD pdata = PlayerD.getPlayerData(p.getName());
                    if(pdata.getMoney()>= Home.HOME_VALUE){
                        String name = Home.DEFAULT_HOME_NAME;
                        if(args.length>0){
                            switch(args.length){
                                case 1:
                                    if(!args[0].equalsIgnoreCase("info")){
                                        name = args[0];
                                        break;
                                    }
                                    else{
                                        p.sendMessage(Utils.chat("&2Usage &b/home &f<home-name/edit/delete> "));
                                        p.sendMessage(Utils.chat("&6Homes:"));
                                        ArrayList<Home> homes = Home.getHomes(p.getName());
                                        for(Home h : homes){p.sendMessage(Utils.chat("&5" + h.getName()));}
                                        return true;
                                    }
                                case 2:
                                    if(args[0].equalsIgnoreCase("delete")){
                                        String homeName = args[1];
                                        Home h = Home.getHome(p.getName(), homeName);
                                        h.delete();
                                        p.sendMessage(Utils.chat("&2Home&f: &5" + h.getName() + " &2Succesfully deleted"));
                                    }else{
                                        p.sendMessage(Utils.chat("&2Usage &b/home &f<home-name/edit/delete> "));
                                        p.sendMessage(Utils.chat("&6Homes:"));
                                        ArrayList<Home> homes = Home.getHomes(p.getName());
                                        for(Home h : homes){p.sendMessage(Utils.chat("&5" + h.getName()));}
                                    }
                                    return true;
                                    
                                case 3:
                                    if(args[0].equalsIgnoreCase("rename")){
                                        String homeName = args[1];
                                        Home h = Home.getHome(p.getName(), homeName);
                                        h.update(Home.NAME, args[2]);
                                        p.sendMessage(Utils.chat("&2Home&f: &5" + homeName + " &2renamed to &5" + h.getName()));
                                    }else{
                                        p.sendMessage(Utils.chat("&2Usage &b/home &f<home-name/edit/delete> "));
                                        p.sendMessage(Utils.chat("&6Homes:"));
                                        ArrayList<Home> homes = Home.getHomes(p.getName());
                                        for(Home h : homes){p.sendMessage(Utils.chat("&5" + h.getName()));}
                                    }
                                    return true;
                                default:
                                    p.sendMessage(Utils.chat("&2Usage &b/home &f<home-name/edit/delete> "));
                                    p.sendMessage(Utils.chat("&6Homes:"));
                                    ArrayList<Home> homes = Home.getHomes(p.getName());
                                    for(Home h : homes){p.sendMessage(Utils.chat("&5" + h.getName()));}
                                    return true;
                            }
                            
                            
                        }
                        Home home = Home.getHome(p.getName(), name);
                        Location hloc = home.getLocation();
                        double dist = Utils.calculateDistance(p.getLocation(), hloc);
                        pdata.subtractMoney(Home.HOME_VALUE + dist*Home.COST_PER_BLOCK);
                        p.sendMessage(Utils.chat("&2Teleporting to&f: &6" + home.getName()));
                        p.teleport(home.getLocation());
                        return true;
                    }else{
                        p.sendMessage(Utils.chat("&cYou have no founds to do this"));
                    }
                } catch (SQLException ex) {
                    p.sendMessage(Utils.chat("&ccannot send you there"));
                    plugin.sendConsoleMessage("&cError at register home &f" + ex.getMessage());
                }
                return true;
                
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(walk)){
            if(cs instanceof Player){
                try {
                    Player p = (Player)cs;
                    if(PlayerManager.hasWalkPermission(p.getName())){
                        if(args.length==0){
                            cs.sendMessage(Utils.chat("&2Usage: &b/walk &f<speed(0-2.5)> &cMust relog!"));
                            
                        }else if(args.length==1){
                            double speed = Double.parseDouble(args[0]);
                            if(speed<=2.5){
                                p.setWalkSpeed((float)speed*0.2f);
                                cs.sendMessage(Utils.chat("&2Speed set to &f" + speed*10 + "X"));
                            }else{
                                cs.sendMessage(Utils.chat("&cMax speed is: 2.5"));
                            }
                            
                        }
                        return true;
                    }else{
                        cs.sendMessage(Utils.chat("&cYo must buy your permission to use this command"));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MiscCommands.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(test)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                WarpNpc warp = new WarpNpc(p.getLocation());
                WorldServer world = ((CraftWorld)p.getWorld()).getHandle();
                world.addEntity(warp);
                
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(tpa)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                switch(args.length){
                    case 1:
                        try{
                            Player p2 = Bukkit.getPlayer(args[0]);
                            double money = EconomyManager.getPlayerMoney(p.getName());
                            if(money>=1000){
                                EconomyManager.subtractMoney(p.getName(), 1000);
                                p.sendMessage(Utils.chat("&6Teleporting to&f: &b" + p2.getName()));
                                p.teleport(p2);
                                
                            }
                        }catch(NullPointerException e){
                            
                        } catch (SQLException ex) {
                            
                        }
                        break;
                    default:
                        p.sendMessage(Utils.chat("&2Usage&f: &b/tpa &f<Player name>"));
                        break;
                }
            }
        }
        
        return false;
        
    }
    
    
}
