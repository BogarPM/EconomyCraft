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
 * @author hp
 */
public class PlayerData {
    private String name;
    private Terrain closestTerrain;
    public Location[] protectionSelect;
    public int movCount;
    public boolean enterTerrainLatch;
    public ArrayList<Home> homes;
    
    public PlayerData(String name){
        this.name = name;
        protectionSelect = new Location[2];
        protectionSelect[0] = null;
        protectionSelect[1] = null;
        movCount = 0;
        enterTerrainLatch = true;
        
    }
    
    public void updateHomes(ArrayList<Home> homes){
        this.homes = homes;
    }

    public String getName() {
        return name;
    }

    public Terrain getClosestTerrain() {
        return closestTerrain;
    }

    public Location[] getProtectionSelect() {
        return protectionSelect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClosestTerrain(Terrain closestTerrain) {
        this.closestTerrain = closestTerrain;
    }

    public void setProtectionSelect(Location[] protectionSelect) {
        this.protectionSelect = protectionSelect;
    }

    public void setHomes(ArrayList<Home> homes) {
        this.homes = homes;
    }
    
    
    
    
    
}
