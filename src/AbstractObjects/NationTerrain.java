/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractObjects;

import java.util.ArrayList;
import org.bukkit.Location;

/**
 *
 * @author Bogar
 */
public class NationTerrain extends Terrain{
    private int citizens;
    private int districts;
    private int area;
    private double rentValue;
    private ArrayList<District> districtList;
    
    public NationTerrain(String owner, Location p1, Location p2) {
        super(owner, p1, p2);
        
    }
    
    public NationTerrain(String name,String owner, Location p1, Location p2) {
        super(owner, p1, p2);
        
    }
    
}
