/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import AbstractObjects.Home;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class HomesManager {
    
    private static double setHomeValue = 2500;
    private static double HomeValue = 1000;
    
    public static void registerHome(Home home) throws SQLException{
        Object values[] = home.getRegisterValues();
        String query = Utils.Utils.getInsertQuery(values, DataBase.HomesTableName);
        DataBase.st.executeUpdate(query);
    }
    
    public static void updateHome(Home home, Location loc) throws SQLException{
        String query = "update " + DataBase.HomesTableName + " set x = " + loc.getBlockX() + ", "
                + "y = " + loc.getBlockY() + ", z = " + loc.getBlockZ() + ", world = '" + loc.getWorld().getName() + "' "
                + "where playername = '" + home.getOwner() + "' and name = '" + home.getName() + "'";
        DataBase.st.executeUpdate(query);
    }
    
    public static Home getMainArrayHome(String homeName){
        Home home = new Home();
        //for()
        return home;
    }
    
    public static ArrayList<Home> getPlayerHomes(String playername) throws SQLException{
        ArrayList<Home> homes = new ArrayList<Home>();
        ResultSet r = DataBase.st.executeQuery("select name from " + DataBase.HomesTableName + " where"
                + " owner = '" + playername + "'");
        while(r.next()){
            
        }
        
        return homes;
    }
    
    public static ArrayList<Home> getSqlHomes(String playerName) throws SQLException{
        ArrayList<Home> list = new ArrayList<Home>();
        ResultSet r = DataBase.st.executeQuery("select * from " + DataBase.HomesTableName + " where playername = '" + playerName + "'");
        while(r.next()){
            String owner = r.getString("playername");
            int x = r.getInt("x");
            int y = r.getInt("y");
            int z = r.getInt("z");
            World world = Bukkit.getWorld(r.getString("world"));
            Location loc = new Location(world,x,y,z);
            boolean isonterr = r.getBoolean("onterrain");
            Home home = new Home(owner,loc,isonterr);
            list.add(home);
        }
        return list;
    }
    
    public static boolean isRegistered(Home home) throws SQLException{
        boolean registered = false;
        ResultSet r = DataBase.st.executeQuery("select count(*) from " + DataBase.HomesTableName + " where playername = '"
                + home.getOwner() + "' and name = '" + home.getName() + "'");
        r.first();
        int amount = r.getInt(1);
        if(amount>0){return true;}
        return false;
    }

    public static double getSetHomeValue() {
        return setHomeValue;
    }

    public static void setSetHomeValue(double setHomeValue) {
        HomesManager.setHomeValue = setHomeValue;
    }

    public static double getHomeValue() {
        return HomeValue;
    }
    
    
    
}
