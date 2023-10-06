package com.nasquicode.vitalpets.objects;

import com.nasquicode.vitalpets.api.EdPrisonAPI;
import com.nasquicode.vitalpets.enums.PowerType;
import com.nasquicode.vitalpets.utils.Console;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class PlayerData {
    private Player player;
    @Setter
    private int petSlots;
    private HashMap<Integer, String> active_pets;
    private List<Pet> pets;
    private HashMap<String, Double> multipliers = new HashMap<>();

    public PlayerData(Player player, Integer petSlots, HashMap<Integer, String> activePets, List<Pet> pets) {
        this.player = player;
        this.petSlots = petSlots;
        this.active_pets = activePets;
        this.pets = pets;
    }

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
                    unequipePet(pets.get(i));
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
        int size = active_pets.size();
        for(int i=1;i<4;i++) {
            if(!active_pets.containsKey(i)) continue;
            if(active_pets.get(i).equalsIgnoreCase(pet.getPetType().getKey())) {
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
            for(PetPower power : pet.getPetType().getPowers()) {
                if(power.getType() == PowerType.EDP_CURRENCIES_CRYSTAL) {
                    multipliers.put("crystals", multipliers.get("crystals")+power.getPercentage());
                    continue;
                }
                if(power.getType() == PowerType.EDP_CURRENCIES_GEMS) {
                    multipliers.put("gems", multipliers.get("gems")+power.getPercentage());
                    continue;
                }
                if(power.getType() == PowerType.EDP_CURRENCIES_TOKENS) {
                    multipliers.put("tokens", multipliers.get("tokens")+power.getPercentage());
                    continue;
                }
            }
            return true;
        }
        return false;
    }
}