/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractObjects;

/**
 *
 * @author Bogar
 */
public class TerrainPermissions {
    private String playerName;
    private String terrainName;
    private boolean breakPerm;
    private boolean placePerm;
    private boolean interactPerm;
    
    private boolean allPerm;
    
    public TerrainPermissions(String terrainname, String playername, boolean breakperm, boolean placeperm, boolean interactperm, boolean allperm){
        playerName = playername;
        terrainName = terrainname;
        breakPerm = breakperm;
        placePerm = placeperm;
        interactPerm = interactperm;
        allPerm = allperm;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getTerrainName() {
        return terrainName;
    }

    public boolean isBreakPerm() {
        return breakPerm;
    }

    public boolean isPlacePerm() {
        return placePerm;
    }

    public boolean isAllPerm() {
        return allPerm;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTerrainName(String terrainName) {
        this.terrainName = terrainName;
    }

    public void setBreakPerm(boolean breakPerm) {
        this.breakPerm = breakPerm;
    }

    public void setPlacePerm(boolean placePerm) {
        this.placePerm = placePerm;
    }

    public void setAllPerm(boolean allPerm) {
        this.allPerm = allPerm;
    }

    public boolean isInteractPerm() {
        return interactPerm;
    }

    public void setInteractPerm(boolean interactPerm) {
        this.interactPerm = interactPerm;
    }
    
    
    
    
}
