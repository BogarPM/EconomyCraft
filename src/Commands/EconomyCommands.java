/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Managers.EconomyManager;
import java.sql.SQLException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import src.Main;

/**
 *
 * @author Bogar
 */
public class EconomyCommands implements CommandExecutor{
    public String money = "money";
    public Main plugin = Main.getPlugin(Main.class);
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        if(cmd.getName().equalsIgnoreCase(money)){
            if(cs instanceof Player){
                try {
                //If sender is a player
                Player p = (Player) cs;
                switch(args.length){
                    case 0:
                        double money = EconomyManager.getPlayerMoney(p.getName());
                        p.sendMessage(Utils.Utils.chat("&2Your current money is: &f" + Double.toString(money)));
                        return true;
                    case 1:
                        
                        break;
                }
                }catch (SQLException ex) {
                     plugin.consoleMessage(Utils.Utils.chat("&cError at /money command &f " + ex.getMessage()));
                     //cs.sendMessage(Utils.Utils.chat("&cError at /money command &f " + ex.getMessage()));
                }
            }
            else{      //If sender is not a player
                
            }
        }
        return false;
    }
    
}
