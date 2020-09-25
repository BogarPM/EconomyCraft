/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import java.sql.SQLException;
import org.bukkit.Location;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class LastPosManager {
    public static void updateLastPos(String playerName, Location loc) throws SQLException{
        String query = "update " + DataBase.lastPositionTableName + 
                " set x = " + loc.getX()+ ", " +
                "y = " + loc.getY() + ", " +
                "z = " + loc.getZ() + " " +
                "where " + PlayerManager.NAME_FIELD + " = '"+
                playerName+ "'";
        DataBase.st.executeUpdate(query);
                
    }
}
