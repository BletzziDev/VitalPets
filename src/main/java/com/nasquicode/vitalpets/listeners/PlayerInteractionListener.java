package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.BoxMapper;
import com.nasquicode.vitalpets.mappers.CandyMapper;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.menus.CandyUseMenu;
import com.nasquicode.vitalpets.menus.PetBoxPreviewMenu;
import com.nasquicode.vitalpets.objects.Candy;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PetBox;
import com.nasquicode.vitalpets.objects.PlayerData;
import com.nasquicode.vitalpets.utils.Console;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerInteractionListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack stack = e.getItem();
        Action action = e.getAction();

        if (stack == null || stack.getAmount() < 1 || !stack.hasItemMeta()) return;

        NBTItem nbtItem = new NBTItem(stack);
        if(nbtItem.hasCustomNbtData() && BoxMapper.getMapper().containsKey(nbtItem.getString("vitalpets-box"))) {
            e.setCancelled(true);
            PetBox box = BoxMapper.getMapper().get(nbtItem.getString("vitalpets-box"));
            if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if(stack.getAmount() == 1) player.getInventory().remove(stack);
                else stack.setAmount(stack.getAmount()-1);
                player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 3);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 100, 0);
                Random random = new Random();
                int reward = random.nextInt(box.getRewards().size());
                PlayerData playerData = PlayerDataMapper.getMapper().get(player.getName());
                if(playerData.getPet(box.getRewards().get(reward).getKey()) != null) {
                    player.sendMessage(Color.string(Terminal.messageFile.getString("you_already_has_this_pet").replace("{pet}",box.getRewards().get(reward).getDisplay_name())));
                    return;
                }
                
                playerData.getPets().add(new Pet(
                        box.getRewards().get(reward), player, 1, 0
                ));
                player.sendMessage(Color.string(Terminal.messageFile.getString("new_pet_unlocked").replace("{pet}",box.getRewards().get(reward).getDisplay_name())));
                return;
            }

            PetBoxPreviewMenu.open(player, box);
            return;
        }
        if(nbtItem.hasCustomNbtData() && CandyMapper.getMapper().containsKey(nbtItem.getString("vitalpets-candy"))) {
            e.setCancelled(true);
            Candy candy = CandyMapper.getMapper().get(nbtItem.getString("vitalpets-candy"));
            if(candy == null) return;

            CandyUseMenu.open(player);
        }
    }
}
