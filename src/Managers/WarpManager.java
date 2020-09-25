/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import AbstractObjects.Warp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class WarpManager {
    
    public static void registerWarp(Warp warp) throws SQLException{
        String name = warp.getName();
        Location loc = warp.getLocation();
        Material item = warp.getIdentifier();
        DataBase.st.executeUpdate("insert into " + DataBase.WarpsTableName + " values('"
                + name + "',"
                + loc.getX() + ","
                + loc.getY() + ","
                + loc.getZ() + ",'"
                + loc.getWorld().getName() + "','"
                + item.toString() + "'"
                + ")");
    }
    
    public static ArrayList<Warp> getSqlWarps() throws SQLException{
        ArrayList<Warp> warps = new ArrayList<Warp>();
        ResultSet r = DataBase.st.executeQuery("select * from warps");
        while(r.next()){
            
            String name = r.getString("name");
            int x = r.getInt("x");
            int y = r.getInt("y");
            int z = r.getInt("z");
            String world= r.getString("world");
            Location loc = new Location(Bukkit.getWorld(world),x,y,z);
            String ident = r.getString("item");
            Warp warp = new Warp(name, loc, ident);
            warps.add(warp);
        }
        return warps;
    }
    
}
