package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalpets.objects.PlayerData;
import lombok.Getter;

import java.util.HashMap;

public class PlayerDataMapper {
    @Getter
    private static HashMap<String, PlayerData> playerData = new HashMap<>();
}
