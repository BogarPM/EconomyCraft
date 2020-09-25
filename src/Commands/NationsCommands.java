/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Bogar
 */
public class NationsCommands implements CommandExecutor{

    public String nations = "nations";
    /*Sub commands
    List
    Create
    info / i
    help
    */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
        
        if(cmd.getName().equalsIgnoreCase(nations)){
            switch(args.length){
                
            }
        }
        
        return true;
    }
    
    
}
