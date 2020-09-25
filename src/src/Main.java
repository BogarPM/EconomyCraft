/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import AbstractObjects.Home;
import AbstractObjects.PlayerData;
import AbstractObjects.Terrain;
import AbstractObjects.Warp;
import Commands.EconomyCommands;
import Commands.MiscCommands;
import Commands.ProtectionCommands;
import Commands.ShopCommands;
import Commands.TerrainCommands;
import Listeners.ChatFormatter;
import Listeners.DisconnectListener;
import Listeners.InventoryListener;
import Listeners.JoinListener;
import Listeners.LevelListener;
import Listeners.MobKillListener;
import Listeners.NpcListener;
import Listeners.ProtectionListener;
import Listeners.SignsListener;
import Listeners.SpawnListener;
import Listeners.WalkListener;
import Managers.HomesManager;
import Managers.MessagesFileManager;
import Managers.WarpManager;
import Player.PlayerD;
import Terrains.Terr;
import Utils.Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Bogar
 */
public class Main extends JavaPlugin{
    
    private static EconomyCommands economyCommands;
    private static ProtectionCommands protectionCommands;
    private static ShopCommands shopCommands;
    private static MiscCommands miscCommands;
    private static TerrainCommands terrainCommands;
    ////////////////////////////////////////////////////////////////
    
    
    public static String[] onlinePlayers;
    public static ArrayList<PlayerData> playerData;
    public static ArrayList<Terrain> terrains;
    public static ArrayList<Warp> warps;
    
    
    @Override
    public void onEnable() {
        //globalMessage("&2EconomiCraft Enabled");
        init();
        initArrays();
        initListeners();
        initCommands();
        updatePlayerDataArray();
        
        /*for(Player p : Bukkit.getOnlinePlayers()){
            p.setWalkSpeed(0.2f);
            
            //sendConsoleMessage(p.getWalkSpeed() + " ");
        }*/
        //consoleMessage("&casdxd");
    }

    @Override
    public void onDisable() {
        
    }
    
    void initCommands(){
        economyCommands = new EconomyCommands();
        this.getCommand(economyCommands.money).setExecutor(economyCommands);
        this.getCommand(economyCommands.top).setExecutor(economyCommands);
        this.getCommand(economyCommands.pay).setExecutor(economyCommands);
        ////////////////////////////////////////////////////////////////////////////////
        protectionCommands = new ProtectionCommands();
        this.getCommand(protectionCommands.claim).setExecutor(protectionCommands);
        ////////////////////////////////////////////////////////////////////////////////
        shopCommands = new ShopCommands();
        this.getCommand(shopCommands.register).setExecutor(shopCommands);
        this.getCommand(shopCommands.sell).setExecutor(shopCommands);
        this.getCommand(shopCommands.shop).setExecutor(shopCommands);
        this.getCommand(shopCommands.buy).setExecutor(shopCommands);
        this.getCommand(shopCommands.worth).setExecutor(shopCommands);
        
        ////////////////////////////////////////////////////////////////////////////////
        miscCommands = new MiscCommands();
        this.getCommand(miscCommands.sethome).setExecutor(miscCommands);
        this.getCommand(miscCommands.home).setExecutor(miscCommands);
        this.getCommand(miscCommands.walk).setExecutor(miscCommands);
        this.getCommand(miscCommands.spawn).setExecutor(miscCommands);
        this.getCommand(miscCommands.test).setExecutor(miscCommands);
        this.getCommand(miscCommands.warp).setExecutor(miscCommands);
        this.getCommand(miscCommands.tpa).setExecutor(miscCommands);
        ////////////////////////////////////////////////////////////////////////////////
        terrainCommands = new TerrainCommands();
        this.getCommand(terrainCommands.terrains).setExecutor(terrainCommands);
        
    }
    
    void initListeners(){
        new JoinListener(this);
        new DisconnectListener(this);
        new ChatFormatter(this);
        new ProtectionListener(this);
        new WalkListener(this);
        new InventoryListener(this);
        new SpawnListener(this);
        new SignsListener(this);
        new LevelListener(this);
        new NpcListener(this);
        new MobKillListener(this);
    }
    
    
    
    void initDb() throws SQLException{
        String usr = this.getConfig().getString("userdb");
        String pass = this.getConfig().getString("pass");
        //sendConsoleMessage("/2User: /f" + usr);
        //sendConsoleMessage("/2  Pass: /f" + pass);
        
        DataBase.connect(usr, pass);
    }
    
    void initConfig(){
        this.getConfig().options().copyDefaults(true);
        if(!this.getConfig().contains("userdb")){this.getConfig().set("userdb", "");}
        if(!this.getConfig().contains("pass")){this.getConfig().set("pass", "");}
        this.saveConfig();
    }
    
    void UpdateOnPlayersArray(){
        int i = 0;
        onlinePlayers = new String[Bukkit.getOnlinePlayers().size()];
        for(Player p : Bukkit.getOnlinePlayers()){
            onlinePlayers[i] = p.getName();
            sendConsoleMessage(p.getName());
            i++;
        }
    }
    
    public static void updateArrays() throws SQLException{
        updateTerrainsArray();
        updateHomesArray();
        updateWarpsArray();
    }
    
    public static void updateTerrainsArray(){
        
    }
    
    public static void updateHomesArray(){
        
    }
    
    public static void updateWarpsArray() throws SQLException{
        warps = WarpManager.getSqlWarps();
    }
    
    public static void updatePlayerDataArray(){             //Use this every time a chage happens to the players i.e login or disconnect
        //ArrayList<Player> list = (ArrayList<Player>) Bukkit.getOnlinePlayers();
        //////////////////////////////////////////////////////////////////////////////////////
        //
        for(Player p : Bukkit.getOnlinePlayers()){
            boolean contains = false;
            for(PlayerData pd : playerData){
                if(pd.getName().equalsIgnoreCase(p.getName())){contains = true;}
            }
            if(!contains){playerData.add(new PlayerData(p.getName()));}
        }
        int i = 0;
        for(PlayerData pd : playerData){    //for finding an removing not online players from bukkit's online players collection
            boolean contains = false;
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getName().equalsIgnoreCase(pd.getName())){contains = true;}
            }
            if(!contains){playerData.remove(i);}
            else{i++;}
            
        }
        updatePlayerHomes();
        
    }
    
    public static int getPlayerIndex(String playerName){
        int index = 0;
        int i = 0;
        for(PlayerData pd : playerData){
            if(pd.getName().equalsIgnoreCase(playerName)){
                index = i;
                break;
            }
            i++;
        }
        
        return index;
    }
    
    public static int getTerrainIndex(String terrainName){
        int index = 0;
        int i = 0;
        for(Terrain terr : terrains){
            if(terr.getName().equalsIgnoreCase(terrainName)){
                index = i;
                break;
            }
            i++;
        }
        
        return index;
    }
    
    public static int getWarpIndex(String warpName){
        int index = 0;
        int i = 0;
        for(Warp terr : warps){
            if(terr.getName().equalsIgnoreCase(warpName)){
                index = i;
                break;
            }
            i++;
        }
        
        return index;
    }
    
    void initArrays(){
        UpdateOnPlayersArray();
    }
    
    void init(){
        initConfig(); 
        MessagesFileManager.startCfg();
        playerData = new ArrayList<PlayerData>();
        try {
            initDb();
            updatePlayerDataArray();
            PlayerD.initTable();
            Terr.initTable();
            Home.createTable();
            sendConsoleMessage("&2Database Conection stablished");
        }
        catch (SQLException ex) {
            sendConsoleMessage("&cError:&f "+ ex.getMessage());
            sendConsoleMessage("&2Introduce user and password in the config.yml  --Insecure yet--");
        
        }
    }
    
    public static void updatePlayerHomes(){
        //playerData
        for(PlayerData pd : playerData){
            try {
                pd.setHomes(HomesManager.getSqlHomes(pd.getName()));
            } catch (SQLException ex) {
                Main.getPlugin(Main.class).sendConsoleMessage("&Error at updating player homes: &f" + ex.getMessage());
            }
        }
    }
    
    
    
    
    public void globalMessage(String msg){
        Bukkit.broadcastMessage(Utils.chat(msg));
    }
    
    public void sendConsoleMessage(String msg){
        this.getServer().getConsoleSender().sendMessage(Utils.chat(msg));
    }
}
