/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AbstractObjects;

import Interfaces.Registrable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import src.DataBase;


public class Home implements Registrable{
    private String owner;
    private String name;
    private Location location;
    private boolean onTerrain;
    
    public static final double SETHOME_VALUE = 2500;
    public static final double HOME_VALUE = 100;
    public static String DEFAULT_HOME_NAME = "home1";
    public static String TABLE_NAME = "homes2";
    public static String OWNER = "owner";
    public static String NAME = "name";
    public static String X = "x";
    public static String Y = "y";
    public static String Z = "z";
    public static String WORLD = "world";
    public static final double COST_PER_BLOCK = 0.75;
    
    
    
    public Home(){
        owner = "";
        location = null;
        onTerrain = false;
    }
    
    public Home(String owner,Location loc,boolean onTerrain){
        this.owner = owner;
        location = loc;
        this.onTerrain = onTerrain;
    }
    
    public Home(String owner,String name,Location loc,boolean onTerrain){
        this.owner = owner;
        this.name = name;
        location = loc;
        this.onTerrain = onTerrain;
    }
    
    public Home(String owner,String name,Location loc){
        this.owner = owner;
        this.name = name;
        location = loc;
    }
    
    public static ArrayList<String> getFields(){
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(OWNER);
        fields.add(NAME);
        fields.add(X);
        fields.add(Y);
        fields.add(Z);
        fields.add(WORLD);
        return fields;
    }
    
    public static void createTable() throws SQLException{
        String table = TABLE_NAME + "("
                + OWNER + " varchar(50),"
                + NAME + " varchar(50),"
                + X + " int,"
                + Y + " int,"
                + Z + " int,"
                + WORLD + " varchar(50),"
                + "primary key(" + OWNER + "," + NAME + ")"
                + ")";
        DataBase.st.executeUpdate("create table if not exists " + table);
    }
    
    public static ArrayList<Home> getHomes(String playerName) throws SQLException{
        ArrayList<Home> homes = new ArrayList<Home>();
        ResultSet r =DataBase.st.executeQuery("select * from " + TABLE_NAME + " where " + OWNER + " = '" + playerName + "'");
        while(r.next()){
            String owner = r.getString(OWNER);
            String name = r.getString(NAME);
            int x = r.getInt(X);
            int y = r.getInt(Y);
            int z = r.getInt(Z);
            String world =  r.getString(WORLD);
            Location loc = new Location(Bukkit.getWorld(world),x,y,z);
            Home home = new Home(owner,name,loc);
            homes.add(home);
        }
        return homes;
    }
    
    public static Home getHome(String playerName,String HomeName) throws SQLException{
        ResultSet r =DataBase.st.executeQuery("select * from " + TABLE_NAME + " where " + OWNER + " = '" + playerName + "' and " + NAME + " = '" + HomeName + "'");
        r.first();
        String owner = r.getString(OWNER);
        String name = r.getString(NAME);
        int x = r.getInt(X);
        int y = r.getInt(Y);
        int z = r.getInt(Z);
        String world =  r.getString(WORLD);
        World wrld = Bukkit.getWorld(world);
        //Bukkit.broadcastMessage(world);
        Location loc = new Location(wrld,x,y,z);
        Home home = new Home(owner,name,loc);
        return home;
    }
    
    public void update(Location loc) throws SQLException{
        update(X,loc.getBlockX());
        update(Y,loc.getBlockY());
        update(Z,loc.getBlockZ());
        update(WORLD,loc.getWorld().getName());
    }
    
    
    public Object[] getRegisterValues(){
        Object[] data = new Object[7];
        data[0] = owner;
        if(!name.equalsIgnoreCase("")){data[1] = "home1";}
        else{data[1] = name;}
        data[2] = location.getBlockX();
        data[3] = location.getBlockY();
        data[4] = location.getBlockZ();
        data[5] = location.getWorld().getName();
        if(onTerrain){data[6] = 1;}
        else{data[6] = 0;}
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getOwner() {
        return owner;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isOnTerrain() {
        return onTerrain;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setOnTerrain(boolean onTerrain) {
        this.onTerrain = onTerrain;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public void delete() throws SQLException{
        DataBase.st.executeUpdate("delete from " + TABLE_NAME + " where " + OWNER + " = '" + this.owner + "' and " + NAME + " ='" + this.name + "'");
    }

    @Override
    public void register() throws SQLException {
        String query = "insert into " + TABLE_NAME + " values('" + this.owner + "','" + this.name + "'," + this.location.getBlockX() + ","
                + this.location.getBlockY() + ","
                + this.location.getBlockZ() + ",'"
                + this.location.getWorld().getName() + "'"
                + ")";
        DataBase.st.executeUpdate(query);
    }

    @Override
    public boolean isRegistered() throws SQLException {
        ResultSet r = DataBase.st.executeQuery("select count(*) from " + TABLE_NAME + " where " + NAME + " = '" + this.name + "' and " + OWNER + " = '"
                + this.owner +"'");
        r.first();
        int n = r.getInt(1);
        if(n==0){
            return false;
        }else{
            return true;
        }
        
    }

    @Override
    public void update(String field, Object value) throws SQLException {
        DataBase.st.executeUpdate("update " + TABLE_NAME + " set " + field + " = '" + value.toString() + "' where " + OWNER + " = '" + this.getOwner()
                + "' and " + NAME + " = '" + this.name + "'");
        
    }
}
