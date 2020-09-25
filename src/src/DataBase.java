/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import AbstractObjects.Terrain;
import Managers.MessagesFileManager;
import Managers.NpcManager;
import Managers.PlayerManager;
import Managers.ProtectionManager;
import Managers.ShopManager;
import Managers.TerrainsManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimerTask;
import org.bukkit.Bukkit;

/**
 *
 * @author Bogar
 */
public class DataBase {
    public static String databaseName = "economycraft";
    public static String lastPositionTableName = "lastposition";
    public static String PayersTableName = "players";
    public static String TerrainsTableName = "terrains";
    public static String ShopTableName = "shopitems";
    public static String TerrainPermissions = "terrainpermissions";
    public static String HomesTableName = "homes";
    public static String WarpsTableName = "warps";
    
    private static String urlDb = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    private static Connection Con;
    public static Statement st;
    private static crcTimer timer;
    
    private static Main plugin = Main.getPlugin(Main.class);    
    
    public static boolean connect(String user, String pass) throws SQLException{
        Con = DriverManager.getConnection(urlDb, user, pass);
        st = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        initDb();
        timer = new crcTimer();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, timer, 10000, 10000);
        return true;
    }
    
    private static void initDb() throws SQLException{
        st.executeUpdate("create database if not exists " + databaseName);
        st.executeUpdate("use " + databaseName);
        String table = "";
        String playersTable = PayersTableName + "("
                + "playername varchar(30),"
                + "uuid varchar(60),"
                + "money double,"
                + "walkperm boolean,"
                + "homes int,"
                + "homepermissions int"
                + ")";
        st.executeUpdate("create table if not exists " + playersTable);
        
        table = lastPositionTableName + "("
                + PlayerManager.NAME_FIELD + " varchar(50),"
                + "x int,"
                + "y int,"
                + "z int,"
                + "world varchar(50)"
                + ")";
        st.executeUpdate("create table if not exists " + table);
        
        table = TerrainsTableName + "("
                + "owner varchar(50),"
                + "name varchar(50),"
                + "world varchar(50),"
                + "x1 int,"
                + "x2 int,"
                + "y1 int,"
                + "y2 int,"
                + "z1 int,"
                + "z2 int,"
                + "value double,"
                + "isprotected boolean)";
        st.executeUpdate("create table if not exists " + table);
        
        table = ShopTableName + "("
                + "item varchar(50),"
                + "sellvalue double,"
                + "buyvalue double,"
                + "sellable boolean,"
                + "buyable boolean,"
                + "sold int,"
                + "bought int"
                + ")";
        st.executeUpdate("create table if not exists " + table);
        
        table = TerrainPermissions + "("
                + "playername varchar(50),"
                + "terrainname varchar(50),"
                + "breakperm boolean,"
                + "placeperm boolean,"
                + "interactperm boolean,"
                + "allperm boolean,"
                + "primary key(playername,terrainname)"
                + ")";
        st.executeUpdate("create table if not exists " + table);
        
        table = HomesTableName + "("
                + "playername varchar(50),"
                + "x int,"
                + "y int,"
                + "z int,"
                + "world varchar(50),"
                + "onterrain boolean,"
                + "primary key(playername)"
                + ")";
        st.executeUpdate("create table if not exists " + table);
        
        table = WarpsTableName + "("
                + "name varchar(50),"
                + "x int,"
                + "y int,"
                + "z int,"
                + "world varchar(50),"
                + "item varchar(50),"
                + "primary key(name)"
                + ")";
        st.executeUpdate("create table if not exists " + table);
        
        updateTerrains();
        initManagers();
    }
    
    public static void initManagers() throws SQLException{
        new ShopManager();
        new ProtectionManager();
        new MessagesFileManager();
        Main.updateWarpsArray();
        new NpcManager();
    }
    
    public static void reload() throws SQLException{
        st = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
    
    public static void updateTerrains() throws SQLException{
        Main.terrains = new ArrayList<Terrain>();
        Main.terrains = TerrainsManager.getSqlTerrains();
        for(Terrain t : Main.terrains){
            plugin.sendConsoleMessage("&6" + t.getName());
        }
    }
    
    private static class crcTimer extends TimerTask{
        @Override
        public void run() {
            try {
                if(st.isClosed()){
                    st = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
            } catch (SQLException ex) {
                if(ex.getMessage().contains("closed")){
                    try {
                        st = Con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    } catch (SQLException ex1) {
                        //plugin.sendConsoleMessage("&cError at crc: &f" + ex.getMessage());
                    }
                }
            }
        }
        
    }
}


