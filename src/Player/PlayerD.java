/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;


import AbstractObjects.Home;
import Nations.Nation;
import Interfaces.Registrable;
import Terrains.Terr;
import Utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import src.DataBase;

/**
 *
 * @author Bogar
 */
public class PlayerD implements Registrable{
    private Player player;
    private double money;
    private int tpTickets;
    private Nation nation = null;
    private ArrayList<Home> homes;
    private String playerName;
        
    public static final String TABLE_NAME = "players2";
    public static final String NAME = "name";
    public static final String MONEY = "money";
    public static final String TELEPORT_TICKETS = "teleporttickets";
    public static final String NATION = "nation";
    public static final double INITIAL_MONEY = 1000;
    //public static final String NAME = "name";
    //public static final String NAME = "name";
    
    
    public PlayerD(Player p){        //For registering new
        this.playerName = p.getName();
        this.player = p;
        this.tpTickets = 1;
        this.money = INITIAL_MONEY;
        
    }
    
    public PlayerD(String playerName, double money, int tptickets, String nation){
        this.playerName = playerName;
        this.money = money;
        this.tpTickets = tptickets;
    }
    
    public static ArrayList<String> getFields(){        //ordered as in sqlTable
        ArrayList<String> fields = new ArrayList<String>();
        fields.add(NAME);
        fields.add(MONEY);
        fields.add(TELEPORT_TICKETS);
        fields.add(NATION);
        //fields.add(NAME);
        return fields;
    }
    
    public static PlayerD getPlayerData(String playerName) throws SQLException{
        ResultSet r = DataBase.st.executeQuery("select * from " + PlayerD.TABLE_NAME + " where " + PlayerD.NAME + " = '" + playerName + "'");
        r.first();
        String name = r.getString(PlayerD.NAME);
        double monet = r.getDouble(PlayerD.MONEY);
        int tpTickets = r.getInt(PlayerD.TELEPORT_TICKETS);
        String nation = r.getString(PlayerD.NATION);
        PlayerD pd = new PlayerD(name,monet,tpTickets,nation);        //continue if more fields needed
        pd.setPlayer(Bukkit.getPlayer(playerName));
        return pd;
    }
    
    public ArrayList<Terr> getTerrains() throws SQLException{
        ArrayList<Terr> list = new ArrayList<Terr>();
        //ResultSet r = DataBase.st.executeQuery("select * from " + Terr.TABLE_NAME + " where " + Terr.OWNER + " = '" + this.getPlayerName() + "'");
        //Terr.getTerrains(this.playerName);
        return Terr.getTerrains(this.playerName);
    }
    
    
    public void addMoney(double amount) throws SQLException{
        money+= amount;
        this.update(PlayerD.MONEY, money);
        if(player!= null){player.sendMessage(Utils.chat("&f" + amount + "$ &2Have been added to your account"));}
    }
    
    public void subtractMoney(double amount) throws SQLException{
        money-= amount;
        this.update(PlayerD.MONEY, money);
        if(player!= null){player.sendMessage(Utils.chat("&f" + amount + "$ &2Have been taken to your account"));}
    }
    
    public void setMoney(double amount) throws SQLException{
        money= amount;
        this.update(PlayerD.MONEY, money);
        if(player!= null){player.sendMessage(Utils.chat("&2You money has been set to: &f" + money + "$"));}
    }
    
    public void clearMoney() throws SQLException{
        money = 0;
        this.update(PlayerD.MONEY, 0);
        if(player!= null){player.sendMessage(Utils.chat("&2You money has been set to: &f0$"));}
    }
    
    
    
    public static void initTable() throws SQLException{
        String table = TABLE_NAME + "("
                + NAME + " varchar(50) primary key not null,"
                + MONEY + " double,"
                + TELEPORT_TICKETS + " int,"
                + NATION + " varchar(50)"
                + ")";
        DataBase.st.executeUpdate("create table if not exists " + table);
    }
    
    

    public Player getPlayer() {
        return player;
    }

    public double getMoney() {
        return money;
    }

    public int getTpTickets() {
        return tpTickets;
    }

    public Nation getNation() {
        return nation;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTpTickets(int tpTickets) {
        this.tpTickets = tpTickets;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    

    @Override
    public void register() throws SQLException {
        String query = "insert into " + PlayerD.TABLE_NAME + " values('" + this.playerName + "'," + this.money + "," + this.tpTickets;
        if(nation != null){
            query += ",'" + "insert nation name" + "')" ;
        }else{
            query += ",'N/A')";
        }
        DataBase.st.executeUpdate(query);
    }

    @Override
    public boolean isRegistered() throws SQLException {
        ResultSet r = DataBase.st.executeQuery("select count(*) from " + PlayerD.TABLE_NAME + " where " + PlayerD.NAME + " = '" + this.getPlayerName() + "'");
        r.first();
        int n = r.getInt(1);
        if(n > 0){return true;}
        return false;
    }

    @Override
    public void update(String field, Object value) throws SQLException {
        DataBase.st.executeUpdate("update " + PlayerD.TABLE_NAME + " set " + field + " = '" + value.toString() + "' where " + PlayerD.NAME + " = '" + this.getPlayerName() + "'");
    }
    
    
    
    
    
}
