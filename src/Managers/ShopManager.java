/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class ShopManager {
    private static double baseLevelCost = 200;
    private static ArrayList<String> columnNames;
    
    public ShopManager() throws SQLException{
        init();
    }
    
    private void init() throws SQLException{
        columnNames = new ArrayList<String>();
        ResultSet r = DataBase.st.executeQuery("describe " + DataBase.ShopTableName);
        while(r.next()){
            columnNames.add(r.getString("field"));
        }
    }
    
    public static boolean isRegistered(Material item){
        try {
            ResultSet r = DataBase.st.executeQuery("select item from " + DataBase.ShopTableName + " where item = '" + item.toString() + "'");
            r.first();
            String name = r.getString(1);
            return true;
        } catch (SQLException ex) {
            if(ex.getMessage().contains("empty")){
                return false;
            }
            return false;
        }
    }
    
    public static double getItemSellValue(Material item) throws SQLException{
        double value = 0;
        ResultSet r = DataBase.st.executeQuery("select sellvalue from " + DataBase.ShopTableName + " where item = '" + item.toString() + "'");
        r.first();
        value = r.getDouble(1);
        return value;
    }
    
    public static double getItemBuyValue(Material item) throws SQLException{
        double value = 0;
        ResultSet r = DataBase.st.executeQuery("select buyvalue from " + DataBase.ShopTableName + " where item = '" + item.toString() + "'");
        r.first();
        value = r.getDouble(1);
        return value;
    }
    
    public static void setItemSellValue(Material item, double value) throws SQLException{
        DataBase.st.executeUpdate("update " + DataBase.ShopTableName + " set sellvalue = " + value + " where item = '" + item.toString() + "'");
    }
    
    public static boolean isSellable(Material item) throws SQLException{
        boolean isSellable = false;
        ResultSet r = DataBase.st.executeQuery("select sellable from " + DataBase.ShopTableName + " where item = '" + item.toString() + "'");
        r.first();
        isSellable = r.getBoolean(1);
        return isSellable;
    }
    
    public static double getNextLevelCost(int lvl){
        double cost = 0;
        
        //cost = (3.0 + lvl)/3.0*baseLevelCost;
        cost = baseLevelCost*Math.exp(0.1*lvl);
        return cost;
    }
    
    public static void updateShopItem(String itemName, String field, Object value) throws SQLException{
        DataBase.st.executeUpdate("update " + DataBase.ShopTableName + " set " + field + " = '" + value.toString() + "' where item = '" + itemName + "'");
    }
    
    
    
    public static boolean isBuyable(Material item) throws SQLException{
        boolean isBuyable = false;
        ResultSet r = DataBase.st.executeQuery("select buyable from " + DataBase.ShopTableName + " where item = '" + item.toString() + "'");
        r.first();
        isBuyable = r.getBoolean(1);
        return isBuyable;
    }
    
    public static void addSoldItem(Material item, int amount) throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select sold from " + DataBase.ShopTableName + " where item = '" + item.toString().toLowerCase() + "'");
        r.first();
        int actual = r.getInt(1);
        actual+=amount;
        DataBase.st.executeUpdate("update " + DataBase.ShopTableName + " set sold = " + Integer.toString(actual) + " where item = '" + item.toString() + "'");
    }
    
     public static void addBoughtItem(Material item, int amount) throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select bought from " + DataBase.ShopTableName + " where item = '" + item.toString().toLowerCase() + "'");
        r.first();
        int actual = r.getInt(1);
        actual+=amount;
        DataBase.st.executeUpdate("update " + DataBase.ShopTableName + " set bought = " + Integer.toString(actual) + " where item = '" + item.toString() + "'");
    }
    
    
    public static void sellAll(Player p, Material item){
        
    }
    
    public static void sellInventory(Player p){
        
    }
}
