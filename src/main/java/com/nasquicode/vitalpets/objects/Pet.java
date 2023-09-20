package com.nasquicode.vitalpets.objects;

import com.nasquicode.vitalpets.utils.Console;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.advancedplugins.mobs.impl.customMobs.entityInstance.CustomEntityInstance;
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
        if(customEntityInstance == null) {
            customEntityInstance = new CustomEntityInstance(petType.getCustom_model_name());
            customEntityInstance.spawn(playerHolder.getLocation());
            return;
        }
        customEntityInstance.spawn(playerHolder.getLocation());
    }
}