package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
        if(active_pets.size() == 0) return "none";
        String serializedActivePetsData = "";
        int key = 0;
        for(Integer slot : active_pets.keySet()) {
            key++;
            serializedActivePetsData = serializedActivePetsData + String.format("%s,%s", String.valueOf(slot), active_pets.get(slot).getPetType().getKey());
            if(key != active_pets.keySet().size()) serializedActivePetsData = serializedActivePetsData + "|";
        }
        return serializedActivePetsData;
    }
    public String getSerializedPetsData() {
        if(pets.size() == 0) return "none";
        String serializedPetsData = "";
        int key = 0;
        for(Pet pet : pets) {
            key++;
            serializedPetsData = serializedPetsData + String.format("%s,%s,%s", pet.getPetType().getKey(), String.valueOf(pet.getLevel()), String.valueOf(pet.getBlocks_progress()));
            if(key != pets.size()) serializedPetsData = serializedPetsData + "|";
        }
        return serializedPetsData;
    }
    public Boolean containsPet(String petKey) {
        for(Pet pet : pets) {
            if(pet.getPetType().getKey().equalsIgnoreCase(petKey)) return true;
        }
        return false;
    }
    public void removePet(String petKey) {
        for(int i=0;i<pets.size();i++) {
            if(pets.get(i).getPetType().getKey().equalsIgnoreCase(petKey)) pets.remove(i);
        }
    }
    public List<String> getEquippedPetsStringList() {
        List<String> equipped_pets = new ArrayList<>();
        for(Pet pet : pets) {
            equipped_pets.add(pet.getPetType().getKey());
        }
        return equipped_pets;
    }
}