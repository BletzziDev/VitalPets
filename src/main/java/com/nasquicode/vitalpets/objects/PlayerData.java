package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor @Getter
public class PlayerData {
    private Player player;
    @Setter
    private int petSlots;
    private HashMap<Integer, Pet> active_pets;
    private List<Pet> pets;

    public String getSerializedActivePetsData() {
        String serializedActivePetsData = "";
        int key = 0;
        for(Integer slot : active_pets.keySet()) {
            key++;
            serializedActivePetsData = serializedActivePetsData + String.format("%s,%s", String.valueOf(slot), active_pets.get(slot));
            if(key != active_pets.keySet().size()) serializedActivePetsData = serializedActivePetsData + "|";
        }
        return serializedActivePetsData;
    }
    public String getSerializedPetsData() {
        String serializedPetsData = "";
        int key = 0;
        for(Pet pet : pets) {
            key++;
            serializedPetsData = serializedPetsData + String.format("%s,%s,%s", pet.getPetType().getName(), String.valueOf(pet.getLevel()), String.valueOf(pet.getBlocks_progress()));
            if(key != pets.size()) serializedPetsData = serializedPetsData + "|";
        }
        return serializedPetsData;
    }
}