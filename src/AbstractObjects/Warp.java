/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractObjects;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 *
 * @author Bogar
 */
public class Warp {
    String name;
    Location location;
    Material identifier;
    public Warp(String name, Location loc, String ident){
        this.name = name;
        location = loc;
        identifier = Material.getMaterial(ident);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Material getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Material identifier) {
        this.identifier = identifier;
    }
    
    
    
}
