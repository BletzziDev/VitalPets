package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor @Getter
public class Pet {
    private PetType petType;
    private Player playerHolder;
    private int level;
    private double blocks_progress;
}