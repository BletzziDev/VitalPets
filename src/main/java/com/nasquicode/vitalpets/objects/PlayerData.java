package com.nasquicode.vitalpets.objects;

import com.nasquicode.vitalpets.utils.Console;
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
    private HashMap<Integer, String> active_pets;
    private List<Pet> pets;

    public String getSerializedActivePetsData() {
        if(active_pets.size() == 0) return "none";
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
            if(pets.get(i).getPetType().getKey().equalsIgnoreCase(petKey)) {
                if(getEquippedPetsStringList().contains(pets.get(i).getPetType().getKey())) {
                    pets.get(i).remove();
                }
                pets.remove(i);
            }
        }
    }
    public List<String> getEquippedPetsStringList() {
        List<String> equipped_pets = new ArrayList<>();
        for(String name : active_pets.values()) {
            equipped_pets.add(name);
        }
        return equipped_pets;
    }
    public Pet getPet(String name) {
        for(Pet pet : pets) {
            if(pet.getPetType().getKey().equalsIgnoreCase(name)) return pet;
        }
        return null;
    }
    public Pet matchPetDisplayName(String display) {
        for(Pet pet : pets) {
            if(pet.getPetType().getDisplay_name().equalsIgnoreCase(display)) return pet;
        }
        return null;
    }
    public void unequipePet(Pet pet) {
        Console.log(String.format("Valor init: %s", String.valueOf(active_pets.size())));
        int size = active_pets.size();
        for(int i=1;i<4;i++) {
            Console.log(String.format("PlayerData.java debug 1 | Slot: %s", String.valueOf(i)));
            if(!active_pets.containsKey(i)) continue;
            if(active_pets.get(i).equalsIgnoreCase(pet.getPetType().getKey())) {
                Console.log(String.format("PlayerData.java debug 2 | Slot: %s", String.valueOf(i)));
                pet.remove();
                active_pets.remove(i);
                break;
            }
        }
    }
    public Boolean equipPet(int slot, Pet pet) {
        if(petSlots >= slot) {
            if(active_pets.containsKey(slot)) {
                unequipePet(getPet(active_pets.get(slot)));
            }
            active_pets.put(slot, pet.getPetType().getKey());
            pet.spawn();
            return true;
        }
        return false;
    }
}