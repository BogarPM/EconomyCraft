/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractObjects;

import Interfaces.Registrable;
import Managers.ProtectionManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Location;

/**
 *
 * @author Bogar
 */
public class Terrain implements Registrable{
    private int id;
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
    
    public Terrain(){
        
    }
    
    public Terrain(String owner, Location p1, Location p2){
        name = owner + " UnNamed Land";
        this.owner = owner;
        point1 = p1;
        point2 = p2;
        world = p1.getWorld().getName();
        center = calculateCenter();
        value = calculateValue();
        Protected = true;
    }
    
    public Terrain(String owner,String name, Location p1, Location p2, double value,boolean isProt){
        this.name = name;
        this.owner = owner;
        point1 = p1;
        point2 = p2;
        world = p1.getWorld().getName();
        this.value = value;
        Protected = isProt;
        center = calculateCenter();
    }
    
    public Terrain(String name, String owner, Location p1, Location p2){
        this.name = name;
        this.owner = owner;
        point1 = p1;
        point2 = p2;
        world = p1.getWorld().getName();
        value = calculateValue();
        Protected = true;
        center = calculateCenter();
    }
    
    public Object[] getRegisterValues(){
        Object[] data = new Object[11];
        data[0] = owner;
        data[1] = name;
        data[2] = world;
        data[3] = point1.getBlockX();
        data[4] = point2.getBlockX();
        data[5] = point1.getBlockY();
        data[6] = point2.getBlockY();
        data[7] = point1.getBlockZ();
        data[8] = point2.getBlockZ();
        data[9] = value;
        if(Protected){data[10] = 1;}
        else{data[10] = 0;}
        return data;
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
    
    
    
    public void register(){             //Register the created object in the dataBase
        
    }
    
    public static ArrayList<Terrain> getSqlTerrains(){      //get all existing terrains registered on the dataBase
        
        return null;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(String field, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
