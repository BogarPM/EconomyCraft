/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractObjects;

import java.util.ArrayList;

/**
 *
 * @author Bogar
 */
public class ChatRoom {
    private String name;
    private ArrayList<String> members;
    private String type;
    private int maxPlayers;
    private String color;
    
    public ChatRoom(String name){
        this.name = name;
        
    }
    
    public ChatRoom(String name, int maxplayers, ArrayList<String> members, String type){
        
    }
}
