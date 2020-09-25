/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobs;

import net.minecraft.server.v1_16_R1.EntityTypes;
import net.minecraft.server.v1_16_R1.EntityZombie;
import net.minecraft.server.v1_16_R1.World;

/**
 *
 * @author Bogar
 */
public class CustomZombie extends EntityZombie{

    public CustomZombie(EntityTypes<? extends EntityZombie> entitytypes, World world) {
        super(entitytypes, world);
    }
    
}
