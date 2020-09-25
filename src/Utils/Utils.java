/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Bogar
 */
public class Utils {
    
    public static String chat(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    public static int getIndex(String[] arr,String str){
        int index=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i].equalsIgnoreCase(str)){
                index = 1;
                break;
            }
        }
        return index;
    }
    
    public static String getInsertQuery(Object[] values, String table){
        String query = "insert into " + table + " values('";
        
        for(int i=0;i<values.length-1;i++){
            query+= values[i]+"','";
        }
        query+= values[values.length-1] + "')"; 
        
        return query;
    }
    
    public static String getSelectQuery(String[] fields, String where, String value, String table){
        String query = "select " + fields[0];
        if(fields.length>1){
            for(int i = 1;i<fields.length;i++){
                query+= ", "+fields[i]+" ";
            }
        }
        query += " from " + table+" where instr(" + where + ",'"+value+"')";
        //System.out.println(query);
        return query;
    }
    
    public static Location calculateCenter(Location l1, Location l2){
        Location center = new Location(l1.getWorld(),0,0,0);
        int xc=0;
        int yc=0;
        int zc=0;
        int x1 = l1.getBlockX();
        int y1 = l1.getBlockY();
        int z1 = l1.getBlockZ();
        int x2 = l2.getBlockX();
        int y2 = l2.getBlockY();
        int z2 = l2.getBlockZ();
        xc = Math.floorDiv((x2+x1),2);
        yc = Math.floorDiv((y2+y1),2);
        zc = Math.floorDiv((z2+z1),2);
        center.setX(xc);
        center.setX(yc);
        center.setX(zc);
        return center;
    }
    
    public static String getInsertQuery(String[] values, String table){
        String query = "insert into " + table + " values('";
        
        for(int i=0;i<values.length-1;i++){
            query+= values[i]+"','";
        }
        query+= values[values.length-1] + "')"; 
        
        return query;
    }
    
    public static double calculateDistance(Location loc1, Location loc2){
        double dist = 0;
        int x1 = loc1.getBlockX();
        int x2 = loc2.getBlockX();
        int y1 = loc1.getBlockY();
        int y2 = loc2.getBlockY();
        int z1 = loc1.getBlockZ();
        int z2 = loc2.getBlockZ();
        dist = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2) + Math.pow(z2-z1, 2));
        return dist;
    }
    
    public static void displayCoords(CommandSender cs, Location coords){
        int x = coords.getBlockX();
        int y = coords.getBlockY();
        int z = coords.getBlockZ();
        cs.sendMessage("point: X" + Integer.toString(x) + " Y: " + Integer.toString(y) + " Z: " + Integer.toString(z));
    }
}