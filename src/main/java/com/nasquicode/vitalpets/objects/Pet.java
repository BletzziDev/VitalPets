package com.nasquicode.vitalpets.objects;

import com.nasquicode.vitalpets.utils.Console;
import lombok.Getter;
import net.advancedplugins.mobs.AdvancedMobsAPI;
import net.advancedplugins.mobs.impl.customMobs.entityInstance.CustomEntityInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class Pet {
    private PetType petType;
    private Player playerHolder;
    private int level;
    private double blocks_progress;
    private CustomEntityInstance customEntityInstance;
    public Pet(PetType petType, Player playerHolder, int level, double blocks_progress) {
        this.petType = petType;
        this.playerHolder = playerHolder;
        this.level = level;
        this.blocks_progress = blocks_progress;
    }
    public void spawn() {
        customEntityInstance = AdvancedMobsAPI.spawnCustomMob(petType.getCustom_model_name(), playerHolder.getLocation());
        customEntityInstance.getBase().setHostile(false);
        customEntityInstance.getBaseEntity().setInvulnerable(true);
        customEntityInstance.setOwnerIGN(playerHolder.getName());
        customEntityInstance.getBaseEntity().setCustomName(String.format("%s §e(%s)", petType.getName() ,playerHolder.getName()));
        customEntityInstance.getBaseEntity().setCustomNameVisible(true);
    }
    public void remove()  {
        assert customEntityInstance != null;
        Console.log("Pet.java debug 1");
        customEntityInstance.kill();
        customEntityInstance.getBaseEntity().remove();
        customEntityInstance.remove();
        customEntityInstance = null;
    }
}