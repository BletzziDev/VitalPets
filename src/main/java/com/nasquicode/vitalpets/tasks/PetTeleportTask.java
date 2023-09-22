package com.nasquicode.vitalpets.tasks;

import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.scheduler.BukkitRunnable;

public class PetTeleportTask extends BukkitRunnable {
    @Override
    public void run() {
        for(PlayerData playerData : PlayerDataMapper.getMapper().values()) {
            for(String key : playerData.getActive_pets().values()) {
                Pet pet = playerData.getPet(key);
                if(pet.getCustomEntityInstance() == null || pet.getCustomEntityInstance().getBaseEntity() == null) {
                    pet.spawn();
                }
                pet.getCustomEntityInstance().getBaseEntity().teleport(playerData.getPlayer().getLocation().add(2,0,2));
            }
        }
    }
}