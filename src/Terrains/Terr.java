/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terrains;

import AbstractObjects.Terrain;
import AbstractObjects.TerrainPermissions;
import Interfaces.Registrable;
import Managers.ProtectionManager;
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
public class Terr implements Registrable{
    
    public static ArrayList<Terr> terrains2;
    
    public static final double PRICE_PER_BLOCK = 5;
    public static final Dimension minimumdDimension = new Dimension(10,10,10);
    private Location point1;
    private Location point2;
    private String world;
    private String name;
    private String owner;
    private ArrayList<TerrainPermissions> permittedPlayers;
    private double value;
    private double sellValue;
    private boolean Protected;
    private Location center;
    
    public static String TABLE_NAME = "terrains2";
    
    public static String OWNER = "owner";
    public static String NAME = "name";
    public static String X1 = "x1";
    public static String Y1 = "y1";
    public static String Z1 = "z1";
    public static String X2 = "x2";
    public static String Y2 = "y2";
    public static String Z2 = "z2";
    public static String WORLD = "world";
    public static String VALUE = "value";
    
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;
    public static final int UP = 5;
    public static final int DOWN = 6;
    
    public static String PROTECTED = "protected";
    
    public Terr(){
        
    }
    
    public Terr(String owner, Location p1, Location p2){         //Player constructor
        name = owner + " UnNamed Land";
        this.owner = owner;
        point1 = p1;
        point2 = p2;
        world = p1.getWorld().getName();
        center = calculateCenter();
        value = calculateValue();
        Protected = true;
    }
    
    public Terr(String owner,String name, Location p1, Location p2, double value,boolean isProt){        //Player constructor
        this.name = name;
        this.owner = owner;
        point1 = p1;
        point2 = p2;
        world = p1.getWorld().getName();
        this.value = value;
        Protected = isProt;
        center = calculateCenter();
    }
    
    public Terr(String name, String owner, Location p1, Location p2){       //New Terrain register
        this.name = name;
        this.owner = owner;
        point1 = p1;
        point2 = p2;
        world = p1.getWorld().getName();
        value = calculateValue();
        Protected = true;
        center = calculateCenter();
    }
    
    
    
    public boolean isOwner(String playerName){
        if(playerName.equalsIgnoreCase(this.owner)){
            return true;
        }
        return false;
    }
    
    public boolean hasBreakPermissions(){
        
        return false;
    }
    
    public Dimension getDimensions(){
        Dimension d;
        int dx = (int) Math.abs(point1.getX() - point2.getX());
        int dy = (int) Math.abs(point1.getY() - point2.getY());
        int dz = (int) Math.abs(point1.getZ() - point2.getZ());
        d = new Dimension(dx,dy,dz);
        return d;
        
    }
    
    public int calculateVolume(){
        int x = Math.abs(point1.getBlockX() - point2.getBlockX())+1;
        int y = Math.abs(point1.getBlockY() - point2.getBlockY())+1;
        int z = Math.abs(point1.getBlockZ() - point2.getBlockZ())+1;
        int vol = x*y*z;
        return vol;
    }
    
    public int getArea(int dir){
        int area = 0;
        if(dir == Terr.NORTH || dir == Terr.SOUTH){
            int dx = Math.abs(this.point1.getBlockX() - this.point2.getBlockX());
            int dy = Math.abs(this.point1.getBlockY() - this.point2.getBlockY());
            area = dx*dy;
        }else if(dir == Terr.WEST || dir == Terr.EAST){
            int dz = Math.abs(this.point1.getBlockZ() - this.point2.getBlockZ());
            int dy = Math.abs(this.point1.getBlockY() - this.point2.getBlockY());
            area = dz*dy;
        }else if(dir == Terr.DOWN || dir == Terr.UP){
            int dz = Math.abs(this.point1.getBlockZ() - this.point2.getBlockZ());
            int dx = Math.abs(this.point1.getBlockX() - this.point2.getBlockX());
            area = dx*dz;
        }else{
            area = -1;
        }
        return area;
    }
    
    public void expand(int dir, int blockAmount){
        
    }
    
    
    private double calculateValue(){
        int x = Math.abs(point1.getBlockX() - point2.getBlockX())+1;
        int y = Math.abs(point1.getBlockY() - point2.getBlockY())+1;
        int z = Math.abs(point1.getBlockZ() - point2.getBlockZ())+1;
        int vol = x*y*z;
        double value = vol * ProtectionManager.protectedBlockCost;
        return value;
    }
    
    public static double calculateValue(Location l1, Location l2){
        int x = Math.abs(l1.getBlockX() - l2.getBlockX());
        int y = Math.abs(l1.getBlockY() - l2.getBlockY());
        int z = Math.abs(l1.getBlockZ() - l2.getBlockZ());
        int vol = x*y*z;
        double value = vol * ProtectionManager.protectedBlockCost;
        return value;
    }
    
    public static ArrayList<Terr> getSqlTerrains() throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select * from " + Terr.TABLE_NAME);
        ArrayList<Terr> list = new ArrayList<Terr>();
        while(r.next()){
            String owner = r.getString(Terr.OWNER);
            String name = r.getString(Terr.NAME);
            World world = Bukkit.getWorld(r.getString(Terr.WORLD));
            int x1 = r.getInt(Terr.X1);
            int x2 = r.getInt(Terr.X2);
            int y1 = r.getInt(Terr.Y1);
            int y2 = r.getInt(Terr.Y2);
            int z1 = r.getInt(Terr.Z1);
            int z2 = r.getInt(Terr.Z2);
            double value = r.getDouble(Terr.VALUE);
            boolean isprot = r.getBoolean(Terr.PROTECTED);
            Location p1 = new Location(world,x1,y1,z1);
            Location p2 = new Location(world,x2,y2,z2);
            Terr t = new Terr(owner,name,p1,p2,value,isprot);
            list.add(t);
        }
        return list;
    }
    
    public static ArrayList<Terr> getTerrains(String Owner) throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select * from " + Terr.TABLE_NAME + " where " + Terr.OWNER + " = '" + Owner + "'");
        ArrayList<Terr> list = new ArrayList<Terr>();
        while(r.next()){
            String owner = r.getString(Terr.OWNER);
            String name = r.getString(Terr.NAME);
            World world = Bukkit.getWorld(r.getString(Terr.WORLD));
            int x1 = r.getInt(Terr.X1);
            int x2 = r.getInt(Terr.X2);
            int y1 = r.getInt(Terr.Y1);
            int y2 = r.getInt(Terr.Y2);
            int z1 = r.getInt(Terr.Z1);
            int z2 = r.getInt(Terr.Z2);
            double value = r.getDouble(Terr.VALUE);
            boolean isprot = r.getBoolean(Terr.PROTECTED);
            Location p1 = new Location(world,x1,y1,z1);
            Location p2 = new Location(world,x2,y2,z2);
            Terr t = new Terr(owner,name,p1,p2,value,isprot);
            list.add(t);
        }
        return list;
    }
    
    public static Terr getTerrain(String Owner, String Name) throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select * from " + Terr.TABLE_NAME + " where " + Terr.OWNER + " = '" + Owner + "' and " + Terr.NAME + " = "
                + "'" + Name + "'");
        ArrayList<Terr> list = new ArrayList<Terr>();
        String owner = r.getString(Terr.OWNER);
        String name = r.getString(Terr.NAME);
        World world = Bukkit.getWorld(r.getString(Terr.WORLD));
        int x1 = r.getInt(Terr.X1);
        int x2 = r.getInt(Terr.X2);
        int y1 = r.getInt(Terr.Y1);
        int y2 = r.getInt(Terr.Y2);
        int z1 = r.getInt(Terr.Z1);
        int z2 = r.getInt(Terr.Z2);
        double value = r.getDouble(Terr.VALUE);
        boolean isprot = r.getBoolean(Terr.PROTECTED);
        Location p1 = new Location(world,x1,y1,z1);
        Location p2 = new Location(world,x2,y2,z2);
        Terr t = new Terr(owner,name,p1,p2,value,isprot);
        list.add(t);
        return t;
        
    }
    
    public static int calculateVolume(Location p1,Location p2){
        int vol = 0;
        int dx = Math.abs(p2.getBlockX() - p1.getBlockX())+1;
        int dy = Math.abs(p2.getBlockY() - p1.getBlockY())+1;
        int dz = Math.abs(p2.getBlockZ() - p1.getBlockZ())+1;
        vol = dx*dy*dz;
        return vol;
    }
    
    public static Terr getStandedTerrain(Location l){
        Terrain terr = new Terrain();
        if(Main.terrains.size()>0){
            for(Terr terrain : Terr.terrains2){
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
    
    public static AbstractObjects.Terrain getClosestTerrain(Location l){
        int i= 0;
        int index = 0;
        double dist = 1000000;
        if(Main.terrains.size()>0){
            for(AbstractObjects.Terrain t : Main.terrains){
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
    
    public static boolean isOnTerrain(Location l, Terr t){
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
    
    private Location calculateCenter(){
        Location center = new Location(point1.getWorld(),0,0,0);
        int xc=0;
        int yc=0;
        int zc=0;
        int x1 = point1.getBlockX();
        int y1 = point1.getBlockY();
        int z1 = point1.getBlockZ();
        int x2 = point2.getBlockX();
        int y2 = point2.getBlockY();
        int z2 = point2.getBlockZ();
        xc = Math.floorDiv((x2+x1),2);
        yc = Math.floorDiv((y2+y1),2);
        zc = Math.floorDiv((z2+z1),2);
        center.setX(xc);
        center.setY(yc);
        center.setZ(zc);
        return center;
    }
    
    public static void initTable() throws SQLException{
        String table = Terr.TABLE_NAME + "("
                + Terr.OWNER + " varchar(50),"
                + Terr.NAME + " varchar(50),"
                + Terr.X1 + " int,"
                + Terr.Y1 + " int,"
                + Terr.Z1 + " int,"
                + Terr.X2 + " int,"
                + Terr.Y2 + " int,"
                + Terr.Z2 + " int,"
                + Terr.VALUE + " double,"
                + Terr.WORLD + " varchar(50),"
                + Terr.PROTECTED + " boolean,"
                + "primary key(" + Terr.OWNER + "," + Terr.NAME + ")"
                + ")";
        DataBase.st.executeUpdate("create table if not exists " + table);
        Terr.terrains2 = Terr.getSqlTerrains();
    }
    
    public Location[] getLocation(){
        Location[] loc = {point1,point2};
        return loc;
    }
    
    public Location getPoint1() {
        return point1;
    }

    public Location getPoint2() {
        return point2;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<TerrainPermissions> getPermittedPlayers() {
        return permittedPlayers;
    }

    public double getValue() {
        return value;
    }

    public double getSellValue() {
        return sellValue;
    }

    public boolean isProtected() {
        return Protected;
    }

    public void setPoint1(Location point1) {
        this.point1 = point1;
    }

    public void setPoint2(Location point2) {
        this.point2 = point2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPermittedPlayers(ArrayList<TerrainPermissions> permittedPlayers) {
        this.permittedPlayers = permittedPlayers;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSellValue(double sellValue) {
        this.sellValue = sellValue;
    }

    public void setProtected(boolean Protected) {
        this.Protected = Protected;
    }

    public Location getCenter() {
        return center;
    }

    @Override
    public boolean isRegistered() throws SQLException {
        ResultSet r = DataBase.st.executeQuery("select count(*) from " + Terr.TABLE_NAME + " where " + Terr.OWNER + " = '" + this.owner + "' and " + Terr.NAME + " = '" + this.name + "'");
        r.first();
        int n = r.getInt(1);
        if(n>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void update(String field, Object value) throws SQLException {
        DataBase.st.executeUpdate("upsate " + Terr.TABLE_NAME + " set " + field + " = " + value.toString() + " where " + Terr.OWNER + " = '" + this.owner + " and "
                + Terr.NAME + " = '" + this.name);
    }

    @Override
    public void register() throws SQLException {
        String query = "insert into " + Terr.TABLE_NAME + " values('" + this.owner + "','" + this.name + "'," + point1.getBlockX() + ","
                + point1.getBlockY() +"," + point1.getBlockZ() + "," + point2.getBlockX() + "," + point2.getBlockY() + "," + point2.getBlockZ() + ","
                + this.value + ",'" + point1.getWorld().getName() + "',1)";
        DataBase.st.executeUpdate(query);
        Terr.terrains2.add(this);
    }
}
