/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Managers.EconomyManager;
import Managers.ShopManager;
import Player.PlayerD;
import Utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import src.DataBase;
import src.Main;

/**
 *
 * @author Bogar
 */
public class ShopCommands implements CommandExecutor{
    private Main plugin= Main.getPlugin(Main.class);
    
    public String register = "register";
    public String sell = "sell";
    public String buy = "buy";
    public String shop = "shop";
    public String worth = "worth";
    
    
    public String handSubCmd = "hand";
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        
        
        if(cmd.getName().equalsIgnoreCase(register)){
            if(args.length==4 || args.length==5){
                try {
                    String item;
                    int pl = 2;
                    Object[] data = new Object[5 + pl];
                    if(args.length==4){
                        if(cs instanceof Player){
                            Player p = (Player)cs;
                            item = p.getInventory().getItemInMainHand().getType().toString();
                            data[0] = item;
                            for(int i = 0;i<args.length;i++){
                                data[i+1] = args[i];
                            }
                        }else{
                            cs.sendMessage(Utils.chat("&2usage&f: /register itemName sellValue buyValue sellable buyable"));
                        }
                    }else if(args.length==5){
                        for(int i = 0;i<args.length;i++){
                            data[i] = args[i];
                        }
                    }
                    data[5] = 0;
                    data[6] = 0;
                    String query = Utils.getInsertQuery(data, DataBase.ShopTableName);
                    plugin.sendConsoleMessage(query);
                    DataBase.st.executeUpdate(query);
                    //plugin.sendConsoleMessage(query);
                } catch (SQLException ex) {
                    plugin.sendConsoleMessage("&cError at register: &f" + ex.getMessage());
                }
                
                
            }else{
                cs.sendMessage(Utils.chat("&2usage&f: /register itemName sellValue buyValue sellable buyable"));
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(sell)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                switch(args.length){
                    case 0:
                        cs.sendMessage(Utils.chat("&2Usage&f: /sell <hand/itemName/all> <amount>"));
                        break;
                    case 1:
                        if(args[0].equalsIgnoreCase(handSubCmd)){
                            try {
                                PlayerInventory inv = p.getInventory();
                                Material iih = inv.getItemInMainHand().getType();
                                int items = getItemsInInventory(inv,iih);
                                sellItem(p,iih,items);
                                return true;
                            } catch (SQLException ex) {
                                plugin.sendConsoleMessage("&cError at selling: &f" + ex.getMessage());
                                return true;
                            }
                        }
                        break;
                    case 2:
                        if(args[0].equalsIgnoreCase(handSubCmd)){
                            try {
                                int amount = Integer.parseInt(args[1]);
                                PlayerInventory inv = p.getInventory();
                                Material iih = inv.getItemInMainHand().getType();
                                sellItem(p,iih,amount);
                                return true;
                            } catch (SQLException ex) {
                                plugin.sendConsoleMessage("&cError at selling: &f" + ex.getMessage());
                            }
                        }
                        break;
                }
            }else{
                cs.sendMessage(Utils.chat("&conly players can use this command"));
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(worth)){
            switch(args.length){
                case 0:
                    if(cs instanceof Player){
                        Player p = (Player)cs;
                        try{
                            switch(args.length){
                                case 0:
                                    Material iih = p.getInventory().getItemInMainHand().getType();
                                    double value = ShopManager.getItemSellValue(iih);
                                    double buyValue = ShopManager.getItemBuyValue(iih);
                                    boolean isSellable = ShopManager.isSellable(iih);
                                    boolean isBuyable = ShopManager.isBuyable(iih);
                                    String msg = "&2Item:&b " + iih.toString() + "&2   Sell Value: &f";
                                    if(isSellable){msg += value + "$   ";}
                                    else{msg += "N/A    ";}
                                    msg+="&2Buy Value: &f";
                                    if(isBuyable){msg += buyValue + "$";}
                                    else{msg += "N/A";}
                                    cs.sendMessage(Utils.chat(msg));
                                    return true;
                                case 1:
                                    String item = args[0];
                                    try{
                                        Material mat = Material.getMaterial(item);
                                        value = ShopManager.getItemSellValue(mat);
                                        buyValue = ShopManager.getItemBuyValue(mat);
                                        isSellable = ShopManager.isSellable(mat);
                                        isBuyable = ShopManager.isBuyable(mat);
                                        msg = "&2Item:&b " + mat.toString() + "&2   Sell Value: &f";
                                        if(isSellable){msg += value + "$   ";}
                                        else{msg += "N/A    ";}
                                        msg+="&2Buy Value: &f";
                                        if(isBuyable){msg += buyValue + "$";}
                                        else{msg += "N/A";}
                                        cs.sendMessage(Utils.chat(msg));
                                    }catch(NullPointerException e){
                                        p.sendMessage(Utils.chat("&cThis is not a valid item"));
                                    }
                                    return true;
                            }
                        }
                        catch (SQLException ex) {
                            if(!ex.getMessage().contains("empty")){
                                plugin.sendConsoleMessage("&cError at worth cmd&f " + ex.getMessage());
                            }
                            return true;
                        }
                        
                    }else{
                        cs.sendMessage(Utils.chat("&2Usage: &f/worth Item"));
                    }
                    
            }
        }
        
        if(cmd.getName().equalsIgnoreCase(shop)){
            
            switch(args.length){
                case 4:
                    if(cs instanceof Player){
                        Player p = (Player)cs;
                        if(!p.hasPermission("eco.shop.*")){
                            cs.sendMessage(Utils.chat("&cYou have no permission to do that"));
                            return true;
                        }
                    }
                    String item = args[1];
                    String subCmd = args[0];
                    String field = args[2];
                    String value = args[3];
                    try{
                        if(subCmd.equalsIgnoreCase("edit")){
                            ShopManager.updateShopItem(item, field, value);
                            cs.sendMessage(Utils.chat("&2Updated &b " + field + " &2on item: &b" + item));
                        }
                    } catch (SQLException ex) {
                        if(ex.getMessage().contains("empty")){
                            cs.sendMessage(Utils.chat("&cThis item cannot be manipulates"));
                        }else{
                            cs.sendMessage(Utils.chat("&cError at editing item&f: " + ex.getMessage()));
                        }
                    }
                    
                    break;
                
                default:
                    try {
                        ResultSet r = DataBase.st.executeQuery("select * from " + DataBase.ShopTableName);
                        cs.sendMessage(Utils.chat("&2Item     sellValue     buyValue"));
                        while(r.next()){
                            boolean sellable = r.getBoolean("sellable");
                            boolean buyable = r.getBoolean("buyable");
                            String msg = "&b" + r.getString("item") + "     &f";
                            if(sellable){msg+= r.getDouble("sellvalue") + "$          ";}
                            else{msg+= "NA          ";}
                            if(buyable){msg+= r.getDouble("buyvalue") + "$     ";}
                            else{msg+= "NA     ";}
                            cs.sendMessage(Utils.chat(msg));
                        }
                        return true;
                    } catch (SQLException ex) {
                        plugin.sendConsoleMessage("&cError at shop command&f: " +ex.getMessage());
                        return true;
                    }
                
            }
            
            
        }
        
        if(cmd.getName().equalsIgnoreCase(buy)){
            if(cs instanceof Player){
                Player p = (Player)cs;
                switch(args.length){
                    case 2:
                        try{
                            String itemName = args[0];
                            PlayerD pd = PlayerD.getPlayerData(p.getName());
                            int amount = Integer.parseInt(args[1]);
                            Material it = Material.getMaterial(itemName.toUpperCase());
                            double itemCost = ShopManager.getItemBuyValue(it);
                            double total = itemCost*amount;
                            double money = pd.getMoney();
                            if(money>=total){
                                ItemStack item = new ItemStack(it,amount);
                                p.getInventory().addItem(item);
                                //EconomyManager.subtractMoney(p.getName(), total);
                                pd.subtractMoney(total);
                                p.sendMessage(Utils.chat("&2Purchased &f" + amount + " &2units of &b" + itemName + " &2for &f" + total + "$"));
                                //p.getInventory()
                            }else{
                                p.sendMessage(Utils.chat("&cYou have not enough founds"));
                            }
                            
                        }catch(NullPointerException e){
                            cs.sendMessage(Utils.chat("&cThis item cannot be purchased"));
                        } catch (SQLException ex) {
                            if(ex.getMessage().contains("empty")){
                                cs.sendMessage(Utils.chat("&cThis item cannot be purchased"));
                            }
                        }
                        break;
                    
                    default:
                        cs.sendMessage(Utils.chat("&2Usage: &b/buy &fItem-Name amount"));
                        break;
                }
                return true;
            }
            
        }
        
        
        
        return false;
    }
    
    
    
    public static int getItemsInInventory(PlayerInventory inv, Material item){
        int n = 0;
        ItemStack[] cont = inv.getContents();
        for(int i = 0;i<cont.length;i++){
            try{
                if(cont[i].getType()==item){
                    n+=cont[i].getAmount();
                }
            }catch(NullPointerException e){
                
            }
        }
        return n;
        
    }
    
    private static void removeItemsFromInventory(PlayerInventory inv, Material item, int amount){
        //inv.remove(item);
        ItemStack[] cont = inv.getContents();
        int amt = amount;
        int actual = getItemsInInventory(inv,item);
        int diff = actual - amount;
        if(diff < 0){diff=0;}
        inv.remove(item);
        ItemStack is = new ItemStack(item,diff);
        inv.addItem(is);
        //inv.seti
        //inv.seti
    }
    
    private void addItem(PlayerInventory inv, Material item, int amount){
        int iter = Math.floorDiv(amount, 64);
        int res = amount%64;
        ItemStack[] cont = inv.getContents();
        ItemStack is =new ItemStack(item, amount);
        inv.addItem(is);
        /*boolean isSpace = true;
        int amountGiven;
        for(int i = 0;i<iter;i++){
            
            ItemStack it = new ItemStack(item,64);
            for(int j = 0;j<cont.length;j++){
                if(cont[j]==null){
                    inv.setItem(j, it);
                }else{
                    
                }
            }
        }*/
        
    }
    
    public static void sellItem(Player p, Material item, int amount) throws SQLException{
        if(ShopManager.isRegistered(item)){
            PlayerD pd = PlayerD.getPlayerData(p.getName());
            PlayerInventory inv = p.getInventory();
            int actual = getItemsInInventory(inv,item);
            if(actual>=amount){
                double itemval = ShopManager.getItemSellValue(item);
                double totalval = itemval*amount;
                //EconomyManager.addMoney(p.getName(), totalval);
                pd.addMoney(totalval);
                ShopManager.addSoldItem(item, amount);
                p.sendMessage(Utils.chat("&2You sold &f" + amount + "&2 units of &f" + item.toString() + "&2 for &f" + totalval + "$"));
                removeItemsFromInventory(inv,item,amount);
            }else{
                p.sendMessage(Utils.chat("&cYou have not enough items"));
            }
        }
        
        
    }
    
}
