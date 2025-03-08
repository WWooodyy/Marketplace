package com.woodycreations.marketplace.spigot.event;

import com.woodycreations.marketplace.spigot.database.dao.marketplace.entity.MarketEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerListItemEvent extends PlayerEvent {
    private final MarketEntity entity;

    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerListItemEvent(@NotNull Player player, @NotNull MarketEntity entity) {
        super(player, true);

        this.entity = entity;
    }

    public @NotNull MarketEntity getEntity() {
        return this.entity;
    }

    @Override
    public final @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
