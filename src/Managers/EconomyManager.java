/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class EconomyManager {
    
    
    public static double getPlayerMoney(String playerName) throws SQLException{
        double money = 0.0;
        String[] fields = {"money"};
        //Bukkit.broadcastMessage("asdasd");
        ResultSet r = DataBase.st.executeQuery(Utils.Utils.getSelectQuery(fields, PlayerManager.NAME_FIELD, playerName, DataBase.playersTable));
        r.first();
        money = r.getDouble(PlayerManager.MONEY_FIELD);
        return money;
    }
    
    public static void addMoney(String playerName, double amount) throws SQLException{
        String[] field = {PlayerManager.MONEY_FIELD};
        String query = Utils.Utils.getSelectQuery(field, PlayerManager.NAME_FIELD, playerName, DataBase.playersTable);
        double money = 0;
        ResultSet r = DataBase.st.executeQuery(query);
        r.first();
        money = r.getDouble(PlayerManager.MONEY_FIELD);
        DataBase.st.executeQuery("update " + DataBase.playersTable + " set " + PlayerManager.MONEY_FIELD + " = " + Double.toString(money+=amount));
    }
    
    public static void subtractMoney(String playerName, double amount) throws SQLException{
        String[] field = {PlayerManager.MONEY_FIELD};
        String query = Utils.Utils.getSelectQuery(field, PlayerManager.NAME_FIELD, playerName, DataBase.playersTable);
        double money = 0;
        ResultSet r = DataBase.st.executeQuery(query);
        r.first();
        money = r.getDouble(PlayerManager.MONEY_FIELD);
        DataBase.st.executeQuery("update " + DataBase.playersTable + " set " + PlayerManager.MONEY_FIELD + " = " + Double.toString(money-=amount));
    }
}
