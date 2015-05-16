/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.events.ClickEvent) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 16:33.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api.events;

import fr.schawnndev.api.enums.ClickType;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class ClickEvent {

    @Getter
    private Player player;

    @Getter
    private ClickType clickType;

    @Getter
    private boolean cancelled;

    public ClickEvent(Player player, ClickType clickType){
        this.player = player;
        this.clickType = clickType;
    }

    public void setCancelled(boolean cancel){
        cancelled = cancel;
    }

}
