/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import AbstractObjects.Terrain;
import AbstractObjects.TerrainPermissions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import src.DataBase;
import src.Main;

/**
 *
 * @author Bogar
 */
public class TerrainsManager {
    
    public static ArrayList<String> permissionsColumns;
    
    public TerrainsManager() throws SQLException{
        init();
    }
    
    private static void init() throws SQLException{
        permissionsColumns = new ArrayList<String>();
        ResultSet r = DataBase.st.executeQuery("describe " + DataBase.TerrainPermissions);
        while(r.next()){
            permissionsColumns.add(r.getString("field"));
        }
        
        
    }
    
    public static void setPermissions() throws SQLException{
        ArrayList<TerrainPermissions> perms = getPermissions();
        for(TerrainPermissions perm : perms){
            String terrain = perm.getTerrainName();
            int index = Main.getTerrainIndex(terrain);
            
        }
    }
    
    public static int getTerrainAmount(){
        return 0;
    }
    
    public static void registerTerrain(Terrain terr){
        
    }
    
    public static boolean hasPermission(String playerName){
        
        return false;
    }
    
    
    
    public static TerrainPermissions getPermissions(String playerName){
        if(hasPermission(playerName)){
            
        }else{
            
        }
        
        return null;
    }
    
    public static ArrayList<TerrainPermissions> getPermissions() throws SQLException{
        ArrayList<TerrainPermissions> perms = new ArrayList<TerrainPermissions>();
        ResultSet r = DataBase.st.executeQuery("select * from " + DataBase.TerrainPermissions);
        while(r.next()){
            String player = r.getString("playername");
            String terrain = r.getString("terrainname");
            boolean breakperm  = r.getBoolean("breakperm");
            boolean placeperm  = r.getBoolean("placeperm");
            boolean interactperm  = r.getBoolean("interactperm");
            boolean allperm  = r.getBoolean("allperm");
            TerrainPermissions perm = new TerrainPermissions(player,terrain,breakperm,placeperm,interactperm,allperm);
            perms.add(perm);
        }
        return perms;
    }
    
    public static ArrayList<Terrain> getSqlTerrains() throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select * from " + DataBase.TerrainsTableName);
        ArrayList<Terrain> list = new ArrayList<Terrain>();
        while(r.next()){
            String owner = r.getString("owner");
            String name = r.getString("name");
            World world = Bukkit.getWorld(r.getString("world"));
            int x1 = r.getInt("x1");
            int x2 = r.getInt("x2");
            int y1 = r.getInt("y1");
            int y2 = r.getInt("y2");
            int z1 = r.getInt("z1");
            int z2 = r.getInt("z2");
            double value = r.getDouble("value");
            boolean isprot = r.getBoolean("isprotected");
            Location p1 = new Location(world,x1,y1,z1);
            Location p2 = new Location(world,x2,y2,z2);
            Terrain t = new Terrain(owner,name,p1,p2,value,isprot);
            list.add(t);
        }
        return list;
    }
    
    public static int calculateVolume(Location p1,Location p2){
        int vol = 0;
        int dx = Math.abs(p2.getBlockX() - p1.getBlockX())+1;
        int dy = Math.abs(p2.getBlockY() - p1.getBlockY())+1;
        int dz = Math.abs(p2.getBlockZ() - p1.getBlockZ())+1;
        vol = dx*dy*dz;
        return vol;
    }
    
    public static Terrain getClosestTerrain(Location l){
        int i= 0;
        int index = 0;
        double dist = 1000000;
        if(Main.terrains.size()>0){
            for(Terrain t : Main.terrains){
                Location p1 = t.getPoint1();
                Location p2 = t.getPoint2();
                int x1 = p1.getBlockX();
                int x2 = p2.getBlockX();
                int y1 = p1.getBlockY();
                int y2 = p2.getBlockY();
                int z1 = p1.getBlockZ();
                int z2 = p2.getBlockZ();
                
                int xl = l.getBlockX();
                int yl = l.getBlockY();
                int zl = l.getBlockZ();
                
                int distx1 = Math.abs(x1-xl);
                int distx2 = Math.abs(x2-xl);
                
                int disty1 = Math.abs(y1-yl);
                int disty2 = Math.abs(y2-yl);
                
                int distz1 = Math.abs(z1-zl);
                int distz2 = Math.abs(z2-zl);
                
                int distx = distx1;
                if(distx2<distx1){distx = distx2;}
                int disty = disty1;
                if(disty2<disty1){disty = disty2;}
                int distz = distz1;
                if(distz2<distz1){distz = distz2;}
                
                double d = Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2) + Math.pow(distz, 2));
                if(d<dist){
                    dist = d;
                    index = i;
                }
                i++;
            }
            return Main.terrains.get(index);
        }else{
            return null;
        }
        
    }
    
    public static boolean isOnTerrain(Location l, Terrain t){
        Location[] loc = t.getLocation();
        int xp = l.getBlockX();
        int yp = l.getBlockY();
        int zp = l.getBlockZ();
        int x1 = loc[0].getBlockX();
        int y1 = loc[0].getBlockY();
        int z1 = loc[0].getBlockZ();
        int x2 = loc[1].getBlockX();
        int y2 = loc[1].getBlockY();
        int z2 = loc[1].getBlockZ();
        boolean ch1=false,ch2=false,ch3=false;
        if((xp>=x1&&xp<=x2) || (xp<=x1&&xp>=x2)){ch1 = true;}
        if((yp>=y1&&yp<=y2) || (yp<=y1&&yp>=y2)){ch2 = true;}
        if((zp>=z1&&zp<=z2) || (zp<=z1&&zp>=z2)){ch3 = true;}
        if(ch1&&ch2&&ch3){return true;}
        return false;
    }
    
    public static Terrain getStandedTerrain(Location l){
        Terrain terr = new Terrain();
        if(Main.terrains.size()>0){
            for(Terrain terrain : Main.terrains){
                Location[] loc = terrain.getLocation();
                int xp = l.getBlockX();
                int yp = l.getBlockY();
                int zp = l.getBlockZ();
                int x1 = loc[0].getBlockX();
                int y1 = loc[0].getBlockY();
                int z1 = loc[0].getBlockZ();
                int x2 = loc[1].getBlockX();
                int y2 = loc[1].getBlockY();
                int z2 = loc[1].getBlockZ();
                boolean ch1=false,ch2=false,ch3=false;
                if((xp>=x1&&xp<=x2) || (xp<=x1&&xp>=x2)){ch1 = true;}
                if((yp>=y1&&yp<=y2) || (yp<=y1&&yp>=y2)){ch2 = true;}
                if((zp>=z1&&zp<=z2) || (zp<=z1&&zp>=z2)){ch3 = true;}
                if(ch1&&ch2&&ch3){return terrain;}
            }
        }
        return null;
    }
    
    public static boolean hasPermissions(){
        
        return false;
    }
    
    public static void registerPermissions(TerrainPermissions perm){
        Object[] perms = new Object[permissionsColumns.size()];
        perms[0] = perm.getPlayerName();
        perms[1] = perm.getTerrainName();
        perms[2] = perm.isBreakPerm();
        perms[3] = perm.isPlacePerm();
        perms[4] = perm.isInteractPerm();
        perms[5] = perm.isAllPerm();
        String query = Utils.Utils.getInsertQuery(perms, DataBase.TerrainPermissions);
        
    }
    
    public static ArrayList<TerrainPermissions> getTerrainPermissions(String terrainName) throws SQLException{
        ArrayList<TerrainPermissions> perms = new ArrayList<TerrainPermissions>();
        ResultSet r = DataBase.st.executeQuery("select * from " + DataBase.TerrainPermissions + " where terrainname = '" + terrainName + "'");
        while(r.next()){
            String name = r.getString("playername");
            String terrname = r.getString("terrainname");
            boolean brperm = r.getBoolean("breakperm");
            boolean plperm = r.getBoolean("placeperm");
            boolean intperm = r.getBoolean("interactperm");
            boolean allperm = r.getBoolean("allperm");
            TerrainPermissions permission = new TerrainPermissions(name,terrname,brperm,plperm,intperm,allperm);
            perms.add(permission);
        }
        return perms;
    }
    
    
    
    public static boolean isowner(String playername, Terrain t){
        if(t.getOwner().equalsIgnoreCase(playername)){
            return true;
        }else{
            return false;
        }
    }
    
}
