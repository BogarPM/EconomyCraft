/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nations;

import AbstractObjects.District;
import Interfaces.Registrable;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.inventory.Inventory;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class Nation implements Registrable{
    private String name;
    private int level;
    private int power;
    private ArrayList<String> players;
    private ArrayList<String> cities;
    private Inventory Reserv;
    private ArrayList<District> districts;
    
    public Nation(String founder, String name){
        
    }

    @Override
    public void register() {
        
    }

    @Override
    public boolean isRegistered() {
        return true;
    }

    @Override
    public void update(String field, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
