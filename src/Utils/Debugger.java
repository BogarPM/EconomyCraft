/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import src.Main;

/**
 *
 * @author Bogar
 */
public class Debugger {
    private static Main plugin = Main.getPlugin(Main.class);
    public static void log(String msg){
        plugin.sendConsoleMessage(msg);
    }
}
