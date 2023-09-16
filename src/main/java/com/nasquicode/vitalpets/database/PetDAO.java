package com.nasquicode.vitalpets.database;

import com.nasquicode.vitalpets.mappers.PetTypeMapper;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class PetDAO {
    private Connection connection;
    public void setup() {
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE IF NOT EXISTS TABLE `vitalpets_players` (`key` VARCHAR(16) NOT NULL , `pet_slots` INT NOT NULL DEFAULT '1' , `pets` TEXT NOT NULL DEFAULT 'none' , `active_pets` VARCHAR(255) NOT NULL DEFAULT 'none' , PRIMARY KEY (`key`)) ENGINE = InnoDB;");
            stmt.execute();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
    public void playerSetup(Player player) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT IGNORE INTO `vitalpets_players` (`key`,`pet_slots`, `active_pets`,`pets`) VALUES (?, 1, 'none', 'none');");
            stmt.setString(1, player.getName());
            stmt.execute();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
    public PlayerData getPlayerData(Player player) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `vitalpets_players` WHERE `key`=?;");
            stmt.setString(1, player.getName());
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            List<Pet> playerPets = new ArrayList<>();
            String[] pets_data = rs.getString("pets").split("\\|");
            // Storage format: "pet_type_name,level,block_progress" Divisor: "|"
            for(String key : pets_data) {
                String[] pet_data = key.split(",");
                Pet pet = new Pet(
                        PetTypeMapper.getMapper().get(pet_data[0]),
                        player,
                        Integer.parseInt(pet_data[1]),
                        Double.parseDouble(pet_data[2])
                );
                playerPets.add(pet);
            }
            HashMap<Integer, Pet> active_pets = new HashMap<>();
            String[] active_pets_data = rs.getString("active_pets").split("\\|");
            // Format: "active_pet_slot,active_pet_type_name" Divisor: "|"
            for(String key : active_pets_data) {
                String[] active_pet_data = key.split(",");
                for(Pet pet : playerPets) {
                    if(pet.getPetType().getKey().equalsIgnoreCase(active_pet_data[1])) {
                        active_pets.put(Integer.parseInt(active_pet_data[0]), pet);
                        break;
                    }
                }
            }
            return new PlayerData(
                    player,
                    rs.getInt("pet_slots"),
                    active_pets,
                    playerPets
            );
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
    public void savePlayerData(PlayerData playerData) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE `vitalpets_players` SET `pet_slots`=?,`active_pets`=?,`pets`=? WHERE `key`=?;");
            stmt.setInt(1, playerData.getPetSlots());
            stmt.setString(2, playerData.getSerializedActivePetsData());
            stmt.setString(3, playerData.getSerializedPetsData());
            stmt.setString(4, playerData.getPlayer().getName());
            stmt.execute();
        }catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
}